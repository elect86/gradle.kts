package kts

import java.io.File

inline fun gradle(block: GradleBuilder.() -> Unit) {
    val gr = Gradle()
    GradleBuilder(gr).block()
    gr()
}

class Gradle {

    var configurationCache: Boolean? = null
    var configurationCacheProblems: Configuration? = null
    var `continue` = false

    //    var daemon = false
    var exportKeys = false
    var dependencyVerification: DependencyVerification? = null
    val writeVerificationMetadata = ArrayList<String>()
    var refreshKeys = false
    var rerunTasks = false
    var continuous = false
    var excludeTask = ""

    // debugging
    var help = false
    var version = false
    var fullStacktrace = false
    var stacktrace = false
    var scan: Boolean? = null
    var clientDebug = false
    var daemonDebug = false

    // performance
    var buildCache: Boolean? = null
    var configureOnDemand: Boolean? = null
    var maxWorkers = -1
    var parallel: Boolean? = null
    var priority: Priority? = null
    var profile = false

    //    var scan = false
    var watchFS: Boolean? = null

    // daemon
    var daemon: Boolean? = null
    var foreground = false
    var status = false
    var stop = false
    var idleTimeout = -1
    var loggingLevel: LoggingLevel? = null
    var quiet = false
    var warn = false
    var info = false
    var debug = false
    var console: Console? = null
    var warningMode: WarningMode? = null

    // execution
    var includeBuild = false
    var offline = false
    var refreshDependencies = false
    var dryRun = false
    var writeLocks = false
    val updateLocks = ArrayList<String>()
    var noRebuild = false
    var buildFile = ""
    var settingsFile: File? = null
    var gradleUserHome: File? = null
    var projectDir: File? = null
    var projectCacheDir: File? = null
    val systemProps = mutableMapOf<String, String>()
    var initScript = ""
    val projectProps = mutableMapOf<String, String>()
    val jvmArgs = ArrayList<String>()
    var javaHome: File? = null

    operator fun invoke(): String {
        val cmd = buildString {
            fun Boolean.ap(s: String) = append(when {
                                                   this -> " --$s"
                                                   else -> " --no-$s"
                                               })
            append("gradle")
            configurationCache?.ap(" configuration-cache")
            configurationCacheProblems?.let { append(" --configuration-cache-problems $it") }
            if (`continue`) append(" --continue")
            if (exportKeys) append(" --export-keys")
            dependencyVerification?.let { append(" -F $it") }
            if (writeVerificationMetadata.isNotEmpty()) append(" -M ${writeVerificationMetadata.joinToString(",")}")
            if (refreshKeys) append(" --refresh-keys")
            if (rerunTasks) append(" --rerun-tasks")
            if (continuous) append(" -t")
            if (excludeTask.isNotEmpty()) append(" -x $excludeTask")

            if (help) append(" -h")
            if (version) append(" -v")
            if (fullStacktrace) append(" -S")
            if (stacktrace) append(" -s")
            scan?.ap(" scan")
            if (clientDebug) append(" -Dorg.gradle.debug=true")
            if (daemonDebug) append(" -Dorg.gradle.daemon.debug=true")
            buildCache?.ap(" build-cache")
            configureOnDemand?.ap(" configure-on-demand")
            if (maxWorkers != -1) append(" --max-workers $maxWorkers")
            parallel?.ap("parallel")
            priority?.let { append(" --priority $it") }
            if (profile) append(" --profile")
            watchFS?.ap("watch-fs")
            daemon?.ap("daemon")
            if (foreground) append(" --foreground")
            if (status) append(" --status")
            if (stop) append(" --stop")
            if (idleTimeout != -1) append(" -Dorg.gradle.daemon.idletimeout=$idleTimeout")
            loggingLevel?.let { append(" -Dorg.gradle.logging.level=$it") }
            if (quiet) append(" -q")
            if (warn) append(" -w")
            if (info) append(" -i")
            if (debug) append(" -d")
            console?.let { append(" --console=$it") }
            warningMode?.let { append(" --warning-mode=$it") }
            if (includeBuild) append(" --include-build")
            if (offline) append(" --offline")
            if (refreshDependencies) append(" --refresh-dependencies")
            if (dryRun) append(" --dry-run")
            if (writeLocks) append(" --write-locks")
            if (updateLocks.isNotEmpty()) append(" --update-locks ${updateLocks.joinToString(",")}")
            if (noRebuild) append(" --no-rebuild")
            if (buildFile.isNotEmpty()) append(" -b $buildFile")
            settingsFile?.run { append(" -c $absolutePath") }
            gradleUserHome?.run { append(" -g $absolutePath") }
            projectDir?.run { append(" -p $absolutePath") }
            projectCacheDir?.run { append(" --projectCacheDir $absolutePath") }
            for ((key, value) in systemProps) append(" -D$key=$value")
            if (initScript.isNotEmpty()) append(" -I $initScript")
            for ((key, value) in projectProps) append(" -P$key=$value")
            for (arg in jvmArgs) append(" -Dorg.gradle.jvmargs=$arg")
            javaHome?.run { append(" -Dorg.gradle.java.home=$absolutePath") }
        }
        print(cmd)
        return cmd()
    }

    enum class Priority { normal, low }

    enum class LoggingLevel { quiet, warn, lifecycle, info, debug }

    enum class Console {
        /** The default. To enable color and other rich output in the console output when the build process is attached
         *  to a console, or to generate plain text only when not attached to a console. *This is the default when
         *  Gradle is attached to a terminal*. */
        auto,

        /** To generate plain text only. This option disables all color and other rich output in the console output.
         *  This is the default when Gradle is *not* attached to a terminal. */
        plain,

        /** To enable color and other rich output in the console output, regardless of whether the build process is not
         *  attached to a console. When not attached to a console, the build output will use ANSI control characters to
         *  generate the rich output. */
        rich,

        /** To enable color and other rich output like the [rich], but output task names and outcomes at the lifecycle
         *  log level, as is done by default in Gradle 3.5 and earlier. */
        verbose
    }

    enum class WarningMode {
        /** To log all warnings. */
        all,

        /** To log all warnings and fail the build if there are any warnings. */
        fail,

        /** To suppress all warnings, including the summary at the end of the build. */
        none,

        /** To suppress all warnings and log a summary at the end of the build. */
        summary
    }

    enum class Configuration { fail, warn }

    enum class DependencyVerification { strict, lenient, off }
}