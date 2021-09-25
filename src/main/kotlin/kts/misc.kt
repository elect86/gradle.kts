package kts

@DslMarker
annotation class KotlinMarker

operator fun String.invoke(): String {
    val process = ProcessBuilder(*split(Regex("\\s+(?=[^\"]*(?:\"[^\"]*\"[^\"]*)*\$)")).toTypedArray())
        .inheritIO()
        //        .directory(workingDir)
        //        .redirectOutput(Redirect.INHERIT)
        //        .redirectError(Redirect.INHERIT)
        .start()
    //        .waitFor(60, TimeUnit.MINUTES)
    return String(process.inputStream.readAllBytes())
}

//fun main() {
//    kotlin { help() }
//}