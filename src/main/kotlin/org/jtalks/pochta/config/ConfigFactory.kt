package org.jtalks.pochta.config

import java.util.Properties
import java.io.InputStreamReader
import java.io.File
import java.io.FileReader

/**
 *
 */
public object ConfigFactory {

    public fun createConfig(cmdLineArgs: Array<String>): Config = Config(loadProperties(cmdLineArgs))

    private fun loadProperties(cmdLineArgs: Array<String>): Properties {
        val defaultStream = javaClass.getResourceAsStream("default.properties")
        val defaultProps = Properties()
        defaultProps.load(InputStreamReader(defaultStream!!))
        return loadConfigFile(cmdLineArgs, defaultProps)
    }

    private fun loadConfigFile(cmdLineArgs: Array<String>, defaults: Properties): Properties {
        val props = Properties(defaults)
        return when(cmdLineArgs.size) {
            0 -> props
            else -> {
                val file = File(cmdLineArgs.get(0))
                if (file.exists()) {
                    props.load(FileReader(file))
                    props
                } else {
                    println("Cannot read config file at ${file.getAbsolutePath()}, exiting")
                    System.exit(1)
                    props
                }
            }
        }
    }
}