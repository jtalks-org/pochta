package org.jtalks.pochta

import org.jtalks.pochta.config.ConfigFactory
import org.jtalks.pochta.rest.HttpServer
import org.jtalks.pochta.smtp.SmtpServer
import org.jtalks.pochta.store.Mailboxes


/**
 *
 */
fun main(args: Array<String>): Unit {
    println("Starting Pochta service...")
    ConfigFactory.initConfig(args)
    println("Configuration initialized")
    Mailboxes.configure()
    SmtpServer.start()
    HttpServer.start()
    println("Ready to rumble!")
}