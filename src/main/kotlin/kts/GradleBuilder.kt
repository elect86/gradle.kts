package kts

import java.io.File

// https://docs.gradle.org/current/userguide/command_line_interface.html

class GradleBuilder(override val gradle: Gradle) : DebuggingOptions,
                                                   PerformanceOptions,
                                                   DaemonOptions,
                                                   LoggingOptions,
                                                   ExecutionOptions,
                                                   EnvironmentOptions {

    inline fun debugging(block: DebuggingOptions.() -> Unit) = block()

    /** Try these options when optimizing build performance. Learn more about [improving performance](https://docs.gradle.org/current/userguide/performance.html#performance_gradle)
     *  of Gradle builds here.
     *  Many of these options can be specified in gradle.properties so command-line flags are not necessary. See the
     *  []configuring build environment guide](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_configuration_properties). */
    inline fun performance(block: PerformanceOptions.() -> Unit) = block()

    /** You can manage the [Gradle Daemon](https://docs.gradle.org/current/userguide/gradle_daemon.html#gradle_daemon)
     *  through the following command line options. */
    inline fun daemon(block: DaemonOptions.() -> Unit) = block()

    /** You can customize the verbosity of Gradle logging with the following options, ordered from least verbose to most
     *  verbose. Learn more in the [logging documentation](https://docs.gradle.org/current/userguide/logging.html#logging). */
    inline fun logging(block: LoggingOptions.() -> Unit) = block()

    /** The following options affect how builds are executed, by changing what is built or how dependencies are resolved. */
    inline fun execution(block: ExecutionOptions.() -> Unit) = block()

    /** You can customize many aspects about where build scripts, settings, caches, and so on through the options below.
     *  Learn more about customizing your [build environment](https://docs.gradle.org/current/userguide/build_environment.html#build_environment). */
    inline fun environment(block: EnvironmentOptions.() -> Unit) = block()

    /** Enables the configuration cache. Gradle will try to reuse the build configuration from previous builds.
     *  [incubating] */
    var configurationCache: Boolean
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.configurationCache = value
        }

    /** Configures how the configuration cache handles problems (fail or warn). Defaults to fail. [incubating] */
    var configurationCacheProblems: Gradle.Configuration
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.configurationCacheProblems = value
        }

    /** Continue task execution after a task failure. */
    val `continue`: Unit
        get() {
            gradle.`continue` = true
        }

    //    /** Uses the Gradle Daemon to run the build. Starts the Daemon if not running. */
    //    fun daemon() {
    //        gradle.daemon = true
    //    }

    /** Exports the public keys used for dependency verification. */
    val exportKeys: Unit
        get() {
            gradle.exportKeys = true
        }

    /** Configures the dependency verification mode (strict, lenient or off) */
    var dependencyVerification: Gradle.DependencyVerification
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.dependencyVerification = value
        }

    /** Generates checksums for dependencies used in the project (comma-separated list) */
    val writeVerificationMetadata: ArrayList<String> by gradle::writeVerificationMetadata

    /** Refresh the public keys used for dependency verification. */
    val refreshKeys: Unit
        get() {
            gradle.refreshKeys = true
        }

    /** Ignore previously cached task results. */
    val rerunTasks: Unit
        get() {
            gradle.rerunTasks = true
        }

    /** Enables continuous build. Gradle does not exit and will re-execute tasks when task file inputs change. */
    val continuous: Unit
        get() {
            gradle.continuous = true
        }

    /** Specify a task to be excluded from execution. */
    var excludeTask: String
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.excludeTask = value
        }
}

interface DebuggingOptions {

    val gradle: Gradle

    /** Shows a help message with all available CLI options. */
    val help: Unit
        get() {
            gradle.help = true
        }

    /** Prints Gradle, Groovy, Ant, JVM, and operating system version information. */
    val version: Unit
        get() {
            gradle.version = true
        }

    /** Print out the full (very verbose) stacktrace for any exceptions. See also logging options.
     *  @see [LoggingOptions] */
    val fullStacktrace: Unit
        get() {
            gradle.fullStacktrace = true
        }

    /** Print out the stacktrace also for user exceptions (e.g. compile error). See also logging options.
     *  @see [LoggingOptions] */
    val stacktrace: Unit
        get() {
            gradle.stacktrace = true
        }

    /** Create a [build scan](https://gradle.com/build-scans?_ga=2.88752987.1129026144.1632232721-388024276.1611139718)
     *  with fine-grained information about all aspects of your Gradle build.  */
    var scan: Boolean
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.scan = value
        }

    /** Debug Gradle client (non-Daemon) process. Gradle will wait for you to attach a debugger at `localhost:5005`
     *  by default. */
    val clientDebug: Unit
        get() {
            gradle.clientDebug = true
        }

    /** Debug [Gradle Daemon](https://docs.gradle.org/current/userguide/gradle_daemon.html#gradle_daemon) process. */
    val daemonDebug: Unit
        get() {
            gradle.daemonDebug = true
        }
}

interface PerformanceOptions {

    val gradle: Gradle

    /** Toggles the [Gradle build cache](https://docs.gradle.org/current/userguide/build_cache.html#build_cache).
     *  Gradle will try to reuse outputs from previous builds. *Default is off*. */
    fun buildCache(enable: Boolean = true) {
        gradle.buildCache = enable
    }

    /** Toggles [Configure-on-demand](https://docs.gradle.org/current/userguide/multi_project_configuration_and_execution.html#sec:configuration_on_demand).
     *  Only relevant projects are configured in this build run. *Default is off*. */
    var configureOnDemand: Boolean
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.configureOnDemand = value
        }

    /** Sets maximum number of workers that Gradle may use. *Default is number of processors*. */
    var maxWorkers: Int
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.maxWorkers = value
        }

    /** Build projects in parallel. For limitations of this option, see [Parallel Project Execution](https://docs.gradle.org/current/userguide/multi_project_configuration_and_execution.html#sec:parallel_execution).
     *  *Default is off*. */
    var parallel: Boolean
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.parallel = value
        }

    /** Specifies the scheduling priority for the Gradle daemon and all processes launched by it. Values are `normal` or
     *  `low`. *Default is normal*. */
    var priority: Gradle.Priority
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.priority = value
        }

    /** Generates a high-level performance report in the `$buildDir/reports/profile` directory. `--scan` is preferred. */
    val profile: Unit
        get() {
            gradle.profile = true
        }

    //    /** Generate a build scan with detailed performance diagnostics. */
    //    fun scan() {
    //        gradle.scan = true
    //    }

    /** Toggles [watching the file system](https://docs.gradle.org/current/userguide/gradle_daemon.html#sec:daemon_watch_fs).
     *  When enabled Gradle re-uses information it collects about the file system between builds. *Enabled by default on
     *  operating systems where Gradle supports this feature*. */
    var watchFS: Boolean
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.watchFS = value
        }
}

interface DaemonOptions {

    val gradle: Gradle

    /** Use the [Gradle Daemon](https://docs.gradle.org/current/userguide/gradle_daemon.html#gradle_daemon) to run the
     *  build. Starts the daemon if not running or existing daemon busy. *Default is on*. */
    var daemon: Boolean
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.daemon = value
        }

    /** Starts the Gradle Daemon in a foreground process. */
    fun foreground() {
        gradle.foreground = true
    }

    /** Run `gradle --status` to list running and recently stopped Gradle daemons. Only displays daemons of the same
     *  Gradle version. */
    fun status() {
        gradle.status = true
    }

    /** Run `gradle --stop` to stop all Gradle Daemons of the same version. */
    fun stop() {
        gradle.stop = true
    }

    /** Gradle Daemon will stop itself after this number of milliseconds of idle time.
     *  *Default is 10800000 (3 hours)*. */
    var idleTimeout: Int
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.idleTimeout = value
        }
}

interface LoggingOptions {

    val gradle: Gradle

    /** Set logging level via Gradle properties. Lifecycle is the default log level. */
    var loggingLevel: Gradle.LoggingLevel
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.loggingLevel = value
        }

    /** Log errors only. */
    val quiet: Unit
        get() {
            gradle.quiet = true
        }

    /** Set log level to warn. */
    val warn: Unit
        get() {
            gradle.warn = true
        }

    /** Set log level to info. */
    val info: Unit
        get() {
            gradle.info = true
        }

    /** Log in debug mode (includes normal stacktrace). */
    val debug: Unit
        get() {
            gradle.debug = true
        }

    /** Specifies which type of console output to generate. */
    var console: Gradle.Console
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.console = value
        }

}

interface ExecutionOptions {

    val gradle: Gradle

    /** Run the build as a composite, including the specified build. See [Composite Builds](https://docs.gradle.org/current/userguide/composite_builds.html#composite_builds). */
    val includeBuild: Unit
        get() {
            gradle.includeBuild = true
        }

    /** Specifies that the build should operate without accessing network resources. Learn more about
     *  [options to override dependency caching](https://docs.gradle.org/current/userguide/dynamic_versions.html#sec:controlling_dependency_caching_command_line). */
    val offline: Unit
        get() {
            gradle.offline = true
        }

    /** Refresh the state of dependencies. Learn more about how to use this in the [dependency management docs](https://docs.gradle.org/current/userguide/dynamic_versions.html#sec:controlling_dependency_caching_command_line). */
    val refreshDependencies: Unit
        get() {
            gradle.refreshDependencies = true
        }

    /** Run Gradle with all task actions disabled. Use this to show which task would have executed. */
    val dryRun: Unit
        get() {
            gradle.dryRun = true
        }

    /** Indicates that all resolved configurations that are lockable should have their lock state persisted.
     *  Learn more about this in [dependency locking](https://docs.gradle.org/current/userguide/dependency_locking.html#dependency-locking). */
    val writeLocks: Unit
        get() {
            gradle.writeLocks = true
        }

    /** Indicates that versions for the specified modules have to be updated in the lock file. This flag also implies
     *  --write-locks. Learn more about this in [dependency locking](https://docs.gradle.org/current/userguide/dependency_locking.html#dependency-locking). */
    fun updateLocks(vararg groupName: String) {
        gradle.updateLocks += groupName
    }

    /** Do not rebuild project dependencies. Useful for [debugging and fine-tuning](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources)
     *  `buildSrc`, but can lead to wrong results. Use with caution! */
    val dontRebuild: Unit
        get() {
            gradle.noRebuild = true
        }
}

interface EnvironmentOptions {

    val gradle: Gradle

    /** Specifies the build file. For example: `gradle --build-file=foo.gradle`. The default is `build.gradle`,
     *  then `build.gradle.kts`. */
    @Deprecated("")
    var buildFile: String
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.buildFile = value
        }

    /** Specifies the settings file. For example: `gradle --settings-file=somewhere/else/settings.gradle` */
    @Deprecated("")
    var settingsFile: File
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.settingsFile = value
        }

    /** Specifies the Gradle user home directory. The default is the `.gradle` directory in the user’s home directory. */
    var gradleUserHome: File
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.gradleUserHome = value
        }

    /** Specifies the start directory for Gradle. Defaults to current directory. */
    var projectDir: File
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.projectDir = value
        }

    /** Specifies the project-specific cache directory. Default value is `.gradle` in the root project directory. */
    var projectCacheDir: File
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.projectCacheDir = value
        }

    /** Sets a system property of the JVM, for example `-Dmyprop=myvalue`. See [System Properties](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_system_properties). */
    fun systemProps(vararg props: Pair<String, String>) {
        gradle.systemProps += props
    }

    /** Specifies an initialization script. See [Init Scripts](https://docs.gradle.org/current/userguide/init_scripts.html#init_scripts). */
    var initScript: String
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.initScript = value
        }

    /** Sets a project property of the root project, for example `-Pmyprop=myvalue`. See [Project Properties](https://docs.gradle.org/current/userguide/build_environment.html#sec:project_properties). */
    fun projectProps(vararg props: Pair<String, String>) {
        gradle.projectProps += props
    }

    /** Set JVM arguments. */
    fun jvmArgs(vararg args: String) {
        gradle.jvmArgs += args
    }

    /** Set JDK home dir. */
    var javaHome: File
        @Deprecated(message = "Write only property", level = DeprecationLevel.HIDDEN) get() = error("")
        set(value) {
            gradle.javaHome = value
        }
}