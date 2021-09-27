package kts

@DslMarker
annotation class KotlinMarker

operator fun String.invoke(args: ArrayList<String>): String {
    args.add(0, this)
    val process = ProcessBuilder(args)
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

fun ArrayList<String>.add(key: String, value: Any) {
    add(key)
    add(value.toString())
}