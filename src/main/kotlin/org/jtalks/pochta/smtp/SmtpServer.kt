package org.jtalks.pochta.smtp

import org.jtalks.pochta.config.Config.Smtp.TransportSecurity.SSL
import org.jtalks.pochta.config.ConfigLoader

/**
 *
 */
public object SmtpServer {

    private val server : SmtpMailServer

    {
        val config = ConfigLoader.config;
        if (config.smtp.transportSecurity == SSL) {
            server = SmtpsMailServer(config)
        } else {
            server = SmtpMailServer(config)
        }
        server.start()
        Runtime.getRuntime().addShutdownHook(shutdownHook)
        println("SMTP server listening on port ${config.smtp.port}")
    }

    private object shutdownHook: Thread() {
        override fun run() = server.stop()
    }
}