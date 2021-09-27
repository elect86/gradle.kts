package kts

@DslMarker
annotation class KotlinMarker

operator fun String.invoke(): String {
    val process = ProcessBuilder(*split(Regex("\\s+(?=[^\"]*(?:\"[^\"]*\"[^\"]*)*\$)")).toTypedArray())
        .inheritIO()
        //        .directory(workingDir)
//                .redirectOutput(ProcessBuilder.Redirect.INHERIT)
//                .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
    process.waitFor() // mandatory for Gradle
    //        .waitFor(60, TimeUnit.MINUTES)
    return String(process.inputStream.readAllBytes())
}

fun main() {
    gradle { version }
}