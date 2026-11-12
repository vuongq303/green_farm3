package greenfarm

import com.android.build.api.artifact.ArtifactTransformationRequest
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFileProperty
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

        // toTransformMany saves AGP metadata and redirects its IDE APK listing.
        transformationRequest.get().submit(this) { artifact ->
            File carrier = new File(artifact.outputFile)
            File work = new File(workspace, carrier.name.replaceFirst(/\.apk$/, ''))
            File mergedDex = new File(work, 'merged/classes.dex')
            File output = new File(outgoing, carrier.name)
            File original = originalApk.get().asFile
            String endpoint = backendUrl.get().trim()
            boolean useBackend = endpoint && endpoint != 'original'
            File base = useBackend ? new File(work, 'base/merged-base.apk') : output

            runJava('MergeCoreDex', original, carrier, mergedDex)
            runPython('build_core_source.py', '--original', original,
                    '--compiled', carrier, '--merged-dex', mergedDex,
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
