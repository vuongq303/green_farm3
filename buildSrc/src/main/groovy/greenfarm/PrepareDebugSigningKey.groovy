package greenfarm

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

import javax.inject.Inject

/** Keep one local debug identity across clean builds and Android Studio runs. */
abstract class PrepareDebugSigningKey extends DefaultTask {
    @OutputFile
    abstract RegularFileProperty getKeystore()

    @Internal
    abstract Property<String> getKeytoolExecutable()

    @Inject
    abstract ExecOperations getExecOperations()

    @TaskAction
    void prepare() {
        File key = keystore.get().asFile
        if (key.isFile()) {
            return
        }
        key.parentFile.mkdirs()
        execOperations.exec {
            commandLine keytoolExecutable.get(), '-genkeypair', '-keystore', key,
                    '-storepass', 'android', '-alias', 'androiddebugkey',
                    '-keypass', 'android', '-keyalg', 'RSA', '-keysize', '2048',
                    '-validity', '10000', '-dname', 'CN=Android Debug,O=Android,C=US'
        }.assertNormalExitValue()
    }
}
