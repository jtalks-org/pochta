package org.jtalks.pochta

import org.jtalks.pochta.config.ConfigLoader
import org.jtalks.pochta.http.HttpServer
import org.jtalks.pochta.smtp.SmtpServer
import org.jtalks.pochta.store.Mailboxes
import org.jtalks.pochta.util.logInfo
import org.jtalks.pochta.util.logError


/**
 * Application launcher.
 * Command line params are ignored.
 */
object Main
fun main(args: Array<String>): Unit {
    try {
        Main.logInfo("Starting Pochta service...")
        Mailboxes
        HttpServer
        SmtpServer
        Main.logInfo("Ready to rumble!")
    } catch(e: Exception) {
        Main.logError(e)
    }
}


