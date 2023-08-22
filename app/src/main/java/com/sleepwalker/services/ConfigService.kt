package com.sleepwalker.services

import android.content.Context
import com.sleepwalker.Config
import com.sleepwalker.decodeConfigFromJson
import com.sleepwalker.encodeConfigToJson
import com.sleepwalker.utils.fileExists
import com.sleepwalker.utils.readFile
import com.sleepwalker.utils.writeToFile

const val CONFIG_NAME = "config.json"

class ConfigService(private val context: Context) {
    private val filesDir = context.filesDir

    fun loadConfig(): Config {
        var config = Config()

        if (fileExists(filesDir, CONFIG_NAME)) {
            config = decodeConfigFromJson(readFile(filesDir, CONFIG_NAME))
        } else {
            writeConfig(config)
        }

        return config
    }

    fun writeConfig(config: Config) {
        writeToFile(filesDir, CONFIG_NAME, encodeConfigToJson(config))
    }
}