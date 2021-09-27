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
        val cmd = "gradle"
        val args = arrayListOf<String>()
        fun Boolean.ap(s: String) {
            args += when {
                this -> "--$s"
                else -> "--no-$s"
            }
        }

        configurationCache?.ap("configuration-cache")
        configurationCacheProblems?.let { args.add("--configuration-cache-problems", it) }
        if (`continue`) args += "--continue"
        if (exportKeys) args += "--export-keys"
        dependencyVerification?.let { args.add("-F", it) }
        if (writeVerificationMetadata.isNotEmpty()) args.add("-M", writeVerificationMetadata.joinToString(","))
        if (refreshKeys) args += "--refresh-keys"
        if (rerunTasks) args += "--rerun-tasks"
        if (continuous) args += "-t"
        if (excludeTask.isNotEmpty()) args.add("-x", excludeTask)

        if (help) args += "-h"
        if (version) args += "-v"
        if (fullStacktrace) args += "-S"
        if (stacktrace) args += "-s"
        scan?.ap("scan")
        if (clientDebug) args += "-Dorg.gradle.debug=true"
        if (daemonDebug) args += "-Dorg.gradle.daemon.debug=true"
        buildCache?.ap("build-cache")
        configureOnDemand?.ap("configure-on-demand")
        if (maxWorkers != -1) args.add("--max-workers", maxWorkers)
        parallel?.ap("parallel")
        priority?.let { args.add("--priority", it) }
        if (profile) args += "--profile"
        watchFS?.ap("watch-fs")
        daemon?.ap("daemon")
        if (foreground) args += "--foreground"
        if (status) args += "--status"
        if (stop) args += "--stop"
        if (idleTimeout != -1) args += "-Dorg.gradle.daemon.idletimeout=$idleTimeout"
        loggingLevel?.let { args += "-Dorg.gradle.logging.level=$it" }
        if (quiet) args+="-q"
        if (warn) args += "-w"
        if (info) args += "-i"
        if (debug) args += "-d"
        console?.let { args += "--console=$it" }
        warningMode?.let { args += "--warning-mode=$it" }
        if (includeBuild) args += "--include-build"
        if (offline) args += "--offline"
        if (refreshDependencies) args += "--refresh-dependencies"
        if (dryRun) args += "--dry-run"
        if (writeLocks) args += "--write-locks"
        if (updateLocks.isNotEmpty()) args.add("--update-locks", updateLocks.joinToString(","))
        if (noRebuild) args += "--no-rebuild"
        if (buildFile.isNotEmpty()) args.add("-b", buildFile)
        settingsFile?.run { args.add("-c", absolutePath) }
        gradleUserHome?.run { args.add("-g", absolutePath) }
        projectDir?.run { args.add("-p", absolutePath) }
        projectCacheDir?.run { args.add("--projectCacheDir", absolutePath) }
        for ((key, value) in systemProps) args += "-D$key=$value"
        if (initScript.isNotEmpty()) args.add("-I", initScript)
        for ((key, value) in projectProps) args += "-P$key=$value"
        for (arg in jvmArgs) args += "-Dorg.gradle.jvmargs=$arg"
        javaHome?.run { args += "-Dorg.gradle.java.home=$absolutePath" }

        //        print(cmd)
        return cmd(args)
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