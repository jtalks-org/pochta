package org.jtalks.pochta.config

/**
 *
 */
 public object ConfigFactory {

     public fun createConfig(cmdLineArgs : Array<String>): Config {
        return Config()
     }
 }