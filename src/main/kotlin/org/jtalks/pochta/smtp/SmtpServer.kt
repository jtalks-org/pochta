package org.jtalks.pochta.smtp

import org.jtalks.pochta.config.Config.Smtp.TransportSecurity.SSL
import org.jtalks.pochta.config.ConfigProvider
import org.jtalks.pochta.util.logInfo
import org.jtalks.pochta.store.Mailboxes
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired

/**
 *
 */
Component class SmtpServer [Autowired](configProvider: ConfigProvider, store: Mailboxes)  {

    {
        val config = configProvider.config;
        val server: SmtpMailServer = if (config.smtp.transportSecurity == SSL)
            SmtpsMailServer(config, store)
        else
            SmtpMailServer(config, store)
        server.start()
        Runtime.getRuntime().addShutdownHook(Thread(Runnable { server.stop() }))
        logInfo("SMTP server listening on port ${config.smtp.port}")
    }
}