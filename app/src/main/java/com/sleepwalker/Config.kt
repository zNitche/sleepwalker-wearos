package com.sleepwalker

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Serializable
data class Config(
    val apiKey: String = "",
    val apiAddress: String = "",
)

fun encodeConfigToJson(dataClass: Config): String {
    val json = Json { encodeDefaults = true }
    return json.encodeToString(dataClass)
}

fun decodeConfigFromJson(string: String): Config {
    return Json.decodeFromString<Config>(string)
}
