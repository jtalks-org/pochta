package org.jtalks.pochta.smtp

import org.jtalks.pochta.config.SmtpConfig

/**
 *
 */
public object SmtpServer {

    public fun start(configuration: SmtpConfig) {
        if (configuration.transportSecurity == SmtpConfig.TransportSecurity.SSL) {
            SmtpsMailServer(configuration).start()
        } else {
            SmtpMailServer(configuration).start()
        }
        println("SMTP server listening on port ${configuration.port}")
    }
}