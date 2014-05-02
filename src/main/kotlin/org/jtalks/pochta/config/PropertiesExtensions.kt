package org.jtalks.pochta.config

import java.util.Properties

/**
 *  Extension functions to ease properties file handling
 */
fun Properties.getString(key: String): String {
    val value = getProperty(key)
    if (value == null) {
        throw ConfigurationException("Configuration property $key is missing")
    }
    return value
}

fun Properties.getInt(key: String): Int {
    val value = this.getString(key)
    try {
        return Integer.parseInt(value)
    } catch(e: NumberFormatException) {
        throw ConfigurationException("Configuration property $key has value $value, should be an integer")
    }
}

fun Properties.getBoolean(key: String): Boolean = java.lang.Boolean.parseBoolean(this.getString(key))

class ConfigurationException(message: String) : Exception(message)