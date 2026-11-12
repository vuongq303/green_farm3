package greenfarm

import com.android.build.api.artifact.ArtifactTransformationRequest
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.Directory
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFile
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

import groovy.io.FileType
import java.nio.charset.StandardCharsets
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import javax.inject.Inject

/** Transform AGP's core carrier into the complete, signed application artifact. */
abstract class MergeOriginalSdkApk extends DefaultTask {
    @InputDirectory
    @PathSensitive(PathSensitivity.RELATIVE)
    abstract DirectoryProperty getInputApks()

    @OutputDirectory
    abstract DirectoryProperty getOutputApks()

    @OutputDirectory
    abstract DirectoryProperty getWorkDirectory()

    @InputFile
    @PathSensitive(PathSensitivity.NONE)
    abstract RegularFileProperty getOriginalApk()

    @InputFile
    @PathSensitive(PathSensitivity.NONE)
    abstract RegularFileProperty getKeystore()

    @InputFiles
    @PathSensitive(PathSensitivity.RELATIVE)
    abstract ConfigurableFileCollection getPackagingSources()

    @InputFiles
    @PathSensitive(PathSensitivity.RELATIVE)
    abstract ConfigurableFileCollection getBuildToolsFiles()

    @Classpath
    abstract ConfigurableFileCollection getMergeToolClasspath()

    // Consume classfiles before AGP's device-specific dexing. Dependency classes
    // are lookup-only and must never become overlay program inputs.
    @InputFiles
    @PathSensitive(PathSensitivity.RELATIVE)
    abstract ListProperty<RegularFile> getProjectClassJars()

    @InputFiles
    @PathSensitive(PathSensitivity.RELATIVE)
    abstract ListProperty<Directory> getProjectClassDirectories()

    @Classpath
    abstract ConfigurableFileCollection getOverlayCompileClasspath()

    @Classpath
    abstract ConfigurableFileCollection getAndroidBootClasspath()

    @InputFile
    @PathSensitive(PathSensitivity.NONE)
    abstract RegularFileProperty getD8Jar()

    @InputFile
    @PathSensitive(PathSensitivity.NONE)
    abstract RegularFileProperty getApkSignerJar()

    @InputFile
    @PathSensitive(PathSensitivity.NONE)
    abstract RegularFileProperty getZipalignExecutable()

    @Input
    abstract Property<Integer> getOverlayMinSdk()

    @Input
    abstract Property<String> getPythonExecutable()

    @Input
    abstract Property<String> getBackendUrl()

    @Internal
    abstract DirectoryProperty getProjectDirectory()

    @Internal
    abstract DirectoryProperty getSdkDirectory()

    @Internal
    abstract Property<ArtifactTransformationRequest<MergeOriginalSdkApk>> getTransformationRequest()

    @Inject
    abstract ExecOperations getExecOperations()

    @Inject
    abstract FileSystemOperations getFileSystemOperations()

    @TaskAction
    void merge() {
        File incoming = inputApks.get().asFile.canonicalFile
        File outgoing = outputApks.get().asFile.canonicalFile
        File workspace = workDirectory.get().asFile.canonicalFile
        logger.lifecycle('{} carrier APK input: {}', path, incoming)
        logger.lifecycle('{} merged APK output: {}', path, outgoing)
        logger.lifecycle('{} merge work: {}', path, workspace)
        String conflicts = ''
        if (overlaps(incoming, outgoing)) {
            conflicts += '\ncarrier APK input overlaps merged APK output'
        }
        if (overlaps(incoming, workspace)) {
            conflicts += '\ncarrier APK input overlaps merge work'
        }
        if (overlaps(outgoing, workspace)) {
            conflicts += '\nmerged APK output overlaps merge work'
        }
        if (conflicts) {
            throw new GradleException('Carrier APKs, merged APKs and merge work must have separate directories' +
                    "\ncarrier APK input: ${incoming}\nmerged APK output: ${outgoing}\nmerge work: ${workspace}" +
                    conflicts)
        }
        fileSystemOperations.delete { delete outgoing, workspace }
        outgoing.mkdirs()
        workspace.mkdirs()

        // Studio can use API 24 for its carrier DEX even when minSdk is 16.
        // Compile from classfiles with the declared minSdk instead of attempting
        // to downgrade already-compiled DEX or weakening MergeCoreDex's guard.
        File overlayDexDirectory = compileOverlay(workspace)

        // toTransformMany saves AGP metadata and redirects its IDE APK listing.
        transformationRequest.get().submit(this) { artifact ->
            File carrier = new File(artifact.outputFile)
            File work = new File(workspace, carrier.name.replaceFirst(/\.apk$/, ''))
            File compiled = prepareCarrier(carrier, overlayDexDirectory, work)
            File mergedDex = new File(work, 'merged/classes.dex')
            File output = new File(outgoing, carrier.name)
            File original = originalApk.get().asFile
            String endpoint = backendUrl.get().trim()
            boolean useBackend = endpoint && endpoint != 'original'
            File base = useBackend ? new File(work, 'base/merged-base.apk') : output

            runJava('MergeCoreDex', original, compiled, mergedDex)
            runPython('build_core_source.py', '--original', original,
                    '--compiled', compiled, '--merged-dex', mergedDex,
                    '--sdk', sdkDirectory.get().asFile, '--keystore', keystore.get().asFile,
                    '--output', base)

            if (useBackend) {
                File patchedDex = new File(work, 'backend/classes.dex')
                runJava('PatchBackendEndpoint', mergedDex, patchedDex, endpoint)
                runPython('build_local_backend.py', '--original', original,
                        '--base', base, '--patched-dex', patchedDex,
                        '--sdk', sdkDirectory.get().asFile, '--keystore', keystore.get().asFile,
                        '--output', output)
            }
            if (!output.isFile()) {
                throw new GradleException('The complete APK was not produced: ' + output)
            }
            runPython('verify_runtime_apk.py', '--apk', output,
                    '--original', original, '--sdk', sdkDirectory.get().asFile)
            return output
        }
    }

    File compileOverlay(File workspace) {
        File stage = new File(workspace, 'core-overlay')
        File program = new File(stage, 'project-classes.jar')
        File dexDirectory = new File(stage, 'dex')
        dexDirectory.mkdirs()
        writeProjectClasses(projectClassJars.get().collect { it.asFile },
                projectClassDirectories.get().collect { it.asFile }, program)

        int minApi = overlayMinSdk.get()
        if (minApi < 1) throw new GradleException('Invalid overlay minSdk: ' + minApi)
        List<String> arguments = ['--debug', '--min-api', minApi.toString(),
                                  '--output', dexDirectory.absolutePath]
        List<File> libraries = androidBootClasspath.files.toList().sort { it.absolutePath }
        if (libraries.isEmpty()) throw new GradleException('Android boot classpath is empty')
        libraries.each { arguments.addAll(['--lib', it.absolutePath]) }
        overlayCompileClasspath.files.toList().sort { it.absolutePath }.each {
            arguments.addAll(['--classpath', it.absolutePath])
        }
        arguments.add(program.absolutePath)
        logger.lifecycle('{} compiling core overlay with D8 --min-api {}', path, minApi)
        execOperations.javaexec {
            classpath(d8Jar.get().asFile)
            mainClass.set('com.android.tools.r8.D8')
            args(arguments)
        }.assertNormalExitValue()

        List<File> dexFiles = findDexFiles(dexDirectory)
        dexFiles.each { dex ->
            byte[] magic = new byte[8]
            dex.withInputStream { input ->
                new DataInputStream(input).readFully(magic)
            }
            String header = new String(magic, StandardCharsets.US_ASCII)
            if (!(header ==~ /dex\n[0-9]{3}\x00/)) {
                throw new GradleException('D8 produced an invalid DEX header: ' + dex)
            }
            int version = Integer.parseInt(header.substring(4, 7))
            if (minApi < 24 && version != 35) {
                throw new GradleException("D8 --min-api ${minApi} produced DEX ${version}; expected 035: ${dex}")
            }
            logger.lifecycle('{} overlay {}: DEX {}, minSdk {}', path, dex.name, version, minApi)
        }
        return dexDirectory
    }

    File prepareCarrier(File carrier, File dexDirectory, File work) {
        work.mkdirs()
        File unsigned = new File(work, 'carrier-api-unsigned.apk')
        File aligned = new File(work, 'carrier-api-aligned.apk')
        File compiled = new File(work, 'carrier-api.apk')
        // The same normalized APK is supplied to the merger AND Python packager,
        // preserving the overlay_apk_sha256/report contract and all resources.
        replaceCarrierDex(carrier, dexDirectory, unsigned)
        execOperations.exec {
            commandLine zipalignExecutable.get().asFile, '-f', '4', unsigned, aligned
        }.assertNormalExitValue()
        // The copy's old signatures are no longer valid. Sign with the existing
        // project debug key so packagers may also verify their compiled input.
        execOperations.javaexec {
            classpath(apkSignerJar.get().asFile)
            mainClass.set('com.android.apksigner.ApkSignerTool')
            args('sign', '--ks', keystore.get().asFile.absolutePath,
                    '--ks-key-alias', 'androiddebugkey', '--ks-pass', 'pass:android',
                    '--key-pass', 'pass:android', '--min-sdk-version', overlayMinSdk.get().toString(),
                    '--v4-signing-enabled', 'false', '--out', compiled.absolutePath, aligned.absolutePath)
        }.assertNormalExitValue()
        return compiled
    }

    private static void addClass(Map<String, byte[]> classes, String name, byte[] bytes) {
        if (classes.containsKey(name) && !Arrays.equals(classes.get(name), bytes)) {
            throw new GradleException('Conflicting project class inputs: ' + name)
        }
        classes.put(name, bytes)
    }

    static void writeProjectClasses(List<File> jars, List<File> directories, File destination) {
        Map<String, byte[]> classes = new TreeMap<>()
        jars.each { jar ->
            ZipFile archive = new ZipFile(jar)
            try {
                Collections.list(archive.entries()).each { entry ->
                    if (!entry.isDirectory() && entry.name.endsWith('.class')) {
                        archive.getInputStream(entry).withCloseable { input ->
                            addClass(classes, entry.name, input.bytes)
                        }
                    }
                }
            } finally { archive.close() }
        }
        directories.each { directory ->
            if (!directory.isDirectory()) {
                throw new GradleException('Compiled class directory is missing: ' + directory)
            }
            directory.eachFileRecurse(FileType.FILES) { file ->
                if (file.name.endsWith('.class')) {
                    String name = directory.toPath().relativize(file.toPath()).toString().replace('\\', '/')
                    addClass(classes, name, file.bytes)
                }
            }
        }
        if (classes.isEmpty()) throw new GradleException('No compiled project classes were supplied for the overlay')
        destination.parentFile.mkdirs()
        new ZipOutputStream(new FileOutputStream(destination)).withCloseable { zip ->
            classes.each { name, bytes ->
                ZipEntry entry = new ZipEntry(name)
                entry.time = 0L
                zip.putNextEntry(entry)
                zip.write(bytes)
                zip.closeEntry()
            }
        }
    }

    private static boolean isDexEntry(String name) {
        name ==~ /classes(?:[0-9]+)?\.dex/
    }

    static List<File> findDexFiles(File directory) {
        List<File> files = (directory.listFiles() ?: new File[0]).findAll {
            it.isFile() && isDexEntry(it.name)
        }.sort { it.name }
        if (!files.any { it.name == 'classes.dex' }) {
            throw new GradleException('D8 did not produce classes.dex in ' + directory)
        }
        return files
    }

    static void replaceCarrierDex(File carrier, File dexDirectory, File destination) {
        List<File> dexFiles = findDexFiles(dexDirectory)
        destination.parentFile.mkdirs()
        ZipFile archive = new ZipFile(carrier)
        try {
            Set<String> seen = new HashSet<>()
            new ZipOutputStream(new FileOutputStream(destination)).withCloseable { zip ->
                Collections.list(archive.entries()).sort { it.name }.each { entry ->
                    String name = entry.name
                    if (!seen.add(name)) throw new GradleException('Duplicate carrier ZIP entry: ' + name)
                    String upper = name.toUpperCase(Locale.ROOT)
                    boolean signature = upper == 'META-INF/MANIFEST.MF' ||
                            (upper ==~ /META-INF\/[^\/]+\.(SF|RSA|DSA|EC)/) ||
                            upper.startsWith('META-INF/SIG-')
                    if (!isDexEntry(name) && !signature) {
                        ZipEntry copy = new ZipEntry(entry)
                        if (copy.method == ZipEntry.DEFLATED) copy.compressedSize = -1L
                        zip.putNextEntry(copy)
                        if (!entry.isDirectory()) {
                            archive.getInputStream(entry).withCloseable { input ->
                                byte[] buffer = new byte[8192]
                                int count
                                while ((count = input.read(buffer)) != -1) zip.write(buffer, 0, count)
                            }
                        }
                        zip.closeEntry()
                    }
                }
                dexFiles.each { dex ->
                    ZipEntry entry = new ZipEntry(dex.name)
                    entry.time = 0L
                    zip.putNextEntry(entry)
                    dex.withInputStream { input ->
                        byte[] buffer = new byte[8192]
                        int count
                        while ((count = input.read(buffer)) != -1) zip.write(buffer, 0, count)
                    }
                    zip.closeEntry()
                }
            }
        } finally { archive.close() }
    }

    private static boolean overlaps(File left, File right) {
        // Path compares components (not string prefixes) and uses the native
        // Windows filesystem's case rules. Call only after canonicalFile above.
        left.toPath().startsWith(right.toPath()) || right.toPath().startsWith(left.toPath())
    }

    void runJava(String entryPoint, Object... arguments) {
        execOperations.javaexec {
            classpath = mergeToolClasspath
            mainClass.set(entryPoint)
            args(arguments.toList())
        }.assertNormalExitValue()
    }

    void runPython(String script, Object... arguments) {
        File tool = new File(projectDirectory.get().asFile, 'tools/' + script)
        execOperations.exec {
            environment 'JAVA_HOME', System.getProperty('java.home')
            environment 'PYTHONUTF8', '1'
            environment 'PYTHONIOENCODING', 'utf-8'
            commandLine([pythonExecutable.get(), tool] + arguments.toList())
        }.assertNormalExitValue()
    }
}
