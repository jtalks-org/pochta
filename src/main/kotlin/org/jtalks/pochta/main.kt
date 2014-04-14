package org.jtalks.pochta

import org.jtalks.pochta.config.ConfigLoader
import org.jtalks.pochta.http.HttpServer
import org.jtalks.pochta.smtp.SmtpServer
import org.jtalks.pochta.store.Mailboxes


/**
 *
 */
fun main(args: Array<String>): Unit {
    println("Starting Pochta service...")
    Mailboxes
    HttpServer
    SmtpServer
    println("Ready to rumble!")
}