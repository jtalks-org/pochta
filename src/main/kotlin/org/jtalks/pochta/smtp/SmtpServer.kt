package org.jtalks.pochta.smtp

import org.jtalks.pochta.config.Config.Smtp.TransportSecurity.SSL
import org.jtalks.pochta.config.ConfigFactory

/**
 *
 */
public object SmtpServer {

    public fun start() {
        val config = ConfigFactory.config!!;
        if (config.smtp.transportSecurity == SSL) {
            SmtpsMailServer(config).start()
        } else {
            SmtpMailServer(config).start()
        }
        println("SMTP server listening on port ${config.smtp.port}")
    }
}