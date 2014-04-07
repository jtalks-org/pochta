package org.jtalks.pochta.smtp

import org.jtalks.pochta.config.Config
import org.jtalks.pochta.config.Config.Smtp.TransportSecurity.SSL

/**
 *
 */
public object SmtpServer {

    public fun start(configuration: Config.Smtp) {
        if (configuration.transportSecurity == SSL) {
            SmtpsMailServer(configuration).start()
        } else {
            SmtpMailServer(configuration).start()
        }
        println("SMTP server listening on port ${configuration.port}")
    }
}