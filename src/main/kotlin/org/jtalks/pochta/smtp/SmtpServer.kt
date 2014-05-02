package org.jtalks.pochta.smtp

import org.jtalks.pochta.config.Config.Smtp.TransportSecurity.SSL
import org.jtalks.pochta.config.ConfigLoader
import org.jtalks.pochta.util.LifeCycleAware
import org.jtalks.pochta.util.logInfo

/**
 *
 */
public object SmtpServer : LifeCycleAware {

    {
        val config = ConfigLoader.config;
        val server: SmtpMailServer = if (config.smtp.transportSecurity == SSL)
            SmtpsMailServer(config)
        else
            SmtpMailServer(config)
        server.start()
        addShutdownHook { server.stop() }
        logInfo("SMTP server listening on port ${config.smtp.port}")
    }
}