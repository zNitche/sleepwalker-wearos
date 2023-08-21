package com.sleepwalker.utils

import java.io.File
import java.nio.charset.Charset


fun writeToFile(filesPath: File, filename: String, text: String) {
    val file = File(filesPath, filename)
    file.writeText(text, Charset.forName("UTF-8"))
}

fun fileExists(filesPath: File, filename: String): Boolean {
    val file = File(filesPath, filename)

    return file.exists()
}

fun readFile(filesPath: File, filename: String): String {
    val file = File(filesPath, filename)

    return file.readText()
}
