package org.jtalks.pochta

import org.jtalks.pochta.config.ConfigFactory

/**
 *
 */
fun main(args: Array<String>): Unit {
    val config = ConfigFactory.createConfig(args)
    println("It works!")
}