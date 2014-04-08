package org.jtalks.pochta.config

import java.util.Properties
import java.io.InputStreamReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.BufferedReader
import java.nio.charset.StandardCharsets

/**
 *
 */
public object ConfigLoader {

    private val configFile = File("${System.getProperty("user.home")}/.pochta/config.properties")
    public val config: Config;

    {
        if (!configFile.exists()) {
            generateDefaultConfig()
        }
        val reader = FileReader(configFile)
        val props = Properties()
        props.load(reader)
        config = Config(props)
        println("Config file at ${configFile.getAbsolutePath()} loaded")
    }

    private fun generateDefaultConfig() {
        println("No configuration file found at ${configFile.getAbsolutePath()}, generating default config...")
        configFile.getParentFile()?.mkdirs()
        val defaultStream = javaClass.getResourceAsStream("default.properties")
        val reader = BufferedReader(InputStreamReader(defaultStream!!, StandardCharsets.UTF_8))
        val writer = FileWriter(configFile)
        try {
            reader.copyTo(writer)
        } finally {
            reader.close()
            writer.close()
        }
        println("Default configuration file generated")
    }
}