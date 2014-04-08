package org.jtalks.pochta.smtp

import org.jtalks.pochta.config.Config.Smtp.TransportSecurity.SSL
import org.jtalks.pochta.config.ConfigLoader

/**
 *
 */
public object SmtpServer {

    {
        val config = ConfigLoader.config;
        if (config.smtp.transportSecurity == SSL) {
            SmtpsMailServer(config).start()
        } else {
            SmtpMailServer(config).start()
        }
        println("SMTP server listening on port ${config.smtp.port}")
    }
}