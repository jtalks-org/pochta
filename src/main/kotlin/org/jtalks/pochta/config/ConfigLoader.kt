package org.jtalks.pochta.config

import java.util.Properties
import java.io.InputStreamReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.BufferedReader
import java.nio.charset.StandardCharsets
import org.jtalks.pochta.util.logInfo
import org.slf4j.LoggerFactory
import ch.qos.logback.classic.Level
import org.slf4j.Logger

/**
 * Loads application configuration from .properties file
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
        configureLoggers()
        logInfo("Config file at ${configFile.getAbsolutePath()} loaded")
    }

    private fun generateDefaultConfig() {
        logInfo("No configuration file found at ${configFile.getAbsolutePath()}, generating default config...")
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
        logInfo("Default configuration file generated")
    }

    /**
     * Alter static logger configuration in accordance with user-provided settings
     */
    private fun configureLoggers() {
        if (config.log.enabled) {
            if (!config.log.consoleEnabled) {
                LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)?.detachAppender("STDOUT");
            }
            if (config.log.verbose) {
                LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)?.setLevel(Level.DEBUG)
                LoggerFactory.getLogger("org.jtalks.pochta")?.setLevel(Level.DEBUG)
            }
            if (config.log.logSmtpSessions) {
                LoggerFactory.getLogger("org.subethamail")?.setLevel(Level.DEBUG)
            }
        } else {
            LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)?.setLevel(Level.OFF)
            LoggerFactory.getLogger("org.jtalks.pochta")?.setLevel(Level.OFF)
            LoggerFactory.getLogger("org.subethamail")?.setLevel(Level.OFF)
        }
    }

    fun org.slf4j.Logger.setLevel(level: Level) = (this as ch.qos.logback.classic.Logger).setLevel(level)
    fun org.slf4j.Logger.detachAppender(app: String) = (this as ch.qos.logback.classic.Logger).detachAppender(app)
}

