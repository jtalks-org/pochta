package org.jtalks.pochta

import org.jtalks.pochta.config.ConfigFactory
import org.jtalks.pochta.rest.HttpServer
import org.jtalks.pochta.smtp.SmtpServer


/**
 *
 */
fun main(args: Array<String>): Unit {
    val config = ConfigFactory.createConfig(args)
    println("Starting Pochta service...")
    SmtpServer.start(config.smtp)
    HttpServer.start(config.http)
    println("Ready to rumble!")
}