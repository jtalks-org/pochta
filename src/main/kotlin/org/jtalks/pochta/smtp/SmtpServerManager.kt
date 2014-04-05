package org.jtalks.pochta.smtp

import org.subethamail.smtp.server.SMTPServer
import org.jtalks.pochta.config.SmtpConfig

/**
 *
 */
public object SmtpServerManager {

    private var server: SMTPServer? = null
    var running: Boolean = false
    var configuration: SmtpConfig = SmtpConfig()

    public fun start() {
        if (configuration.transportSecurity == SmtpConfig.TransportSecurity.SSL) {
            server = SmtpsMailServer(configuration)
        } else {
            server = SmtpMailServer(configuration)
        }
        try  {
            server?.start()
            running = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    public fun stop() {
        try {
            server?.stop()
            running = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}