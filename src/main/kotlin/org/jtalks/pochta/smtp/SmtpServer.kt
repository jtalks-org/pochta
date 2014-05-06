package org.jtalks.pochta.smtp

import org.jtalks.pochta.config.Config.Smtp.TransportSecurity.SSL
import org.jtalks.pochta.config.ConfigLoader
import org.jtalks.pochta.util.logInfo

/**
 *
 */
public object SmtpServer  {

    {
        val config = ConfigLoader.config;
        val server: SmtpMailServer = if (config.smtp.transportSecurity == SSL)
            SmtpsMailServer(config)
        else
            SmtpMailServer(config)
        server.start()
        Runtime.getRuntime().addShutdownHook(Thread(Runnable { server.stop() }))
        logInfo("SMTP server listening on port ${config.smtp.port}")
    }
}