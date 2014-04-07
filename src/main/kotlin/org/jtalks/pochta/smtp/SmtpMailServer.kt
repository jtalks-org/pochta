package org.jtalks.pochta.smtp

import org.subethamail.smtp.server.SMTPServer
import org.subethamail.smtp.auth.EasyAuthenticationHandlerFactory
import java.net.Socket
import java.net.InetSocketAddress
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.SSLSocket
import org.subethamail.smtp.MessageHandlerFactory
import org.subethamail.smtp.MessageContext
import org.subethamail.smtp.MessageHandler
import org.subethamail.smtp.auth.UsernamePasswordValidator
import org.subethamail.smtp.auth.LoginFailedException
import org.jtalks.pochta.config.Config
import org.jtalks.pochta.config.Config.Smtp.AuthType.*
import org.jtalks.pochta.config.Config.Smtp.TransportSecurity.*

/**
 * SMTP mail server implementation.
 * Basically, we can launch any number of these servers on different ports.
 * <p> Call start() to launch the server and start listening for connections
 * <p> Call stop() to disable the server (it's not supposed to be started again)
 */
open class SmtpMailServer(val configuration: Config.Smtp) : SMTPServer(null), MessageHandlerFactory {

    {
        setPort(configuration.port)
        setupAuthentication()
        setupStarttls()
        setSoftwareName("Pochta SMTP server")
        setMessageHandlerFactory(this)
        // Disable "Received:" header assembly. It involves DNS resolution and may take a lot of time
        setDisableReceivedHeaders(true)
    }

    private fun setupAuthentication() {
        if (configuration.authType != DISABLED) {
            setAuthenticationHandlerFactory(EasyAuthenticationHandlerFactory(object: UsernamePasswordValidator {
                override fun login(username: String?, password: String?): Unit =
                        if (!configuration.login.equals(username) || !configuration.password.equals(password))
                            throw LoginFailedException()
            }))
            if (configuration.authType == ENFORCED) {
                setRequireAuth(true)
            }
        }
    }

    private fun setupStarttls() {
        when (configuration.transportSecurity) {
            STARTTLS_SUPPORTED -> setEnableTLS(true)
            STARTTLS_ENFORCED -> {
                setEnableTLS(true)
                setRequireTLS(true)
            }
        }
    }

    public override fun createSSLSocket(socket: Socket?): SSLSocket {
        val remoteAddress = socket!!.getRemoteSocketAddress() as InetSocketAddress
        val sf = SSLSocketFactory.getDefault() as SSLSocketFactory
        val s = sf.createSocket(socket, remoteAddress.getHostName(), socket.getPort(), true) as SSLSocket
        // we are the server
        s.setUseClientMode(false)
        return s
    }

    /**
     * Captures all incoming e-mails and forwards it into MailStoreComponent implementation
     */
    override fun create(ctx: MessageContext?): MessageHandler? {
        return MailSession(ctx)
    }
}
