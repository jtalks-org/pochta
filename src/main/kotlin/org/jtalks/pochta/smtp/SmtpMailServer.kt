package org.jtalks.pochta.smtp

import org.jtalks.pochta.config.SmtpConfig
import org.subethamail.smtp.server.SMTPServer
import org.jtalks.pochta.smtp.Authenticator
import org.subethamail.smtp.auth.EasyAuthenticationHandlerFactory
import java.net.Socket
import java.net.InetSocketAddress
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.SSLSocket
import org.subethamail.smtp.MessageHandlerFactory
import org.subethamail.smtp.MessageContext
import org.subethamail.smtp.MessageHandler

/**
 * SMTP mail server implementation.
 * Basically, we can launch any number of these servers on different ports.
 * <p> Call start() to launch the server and start listening for connections
 * <p> Call stop() to disable the server (it's not supposed to be started again)
 */
open class SmtpMailServer(val configuration: SmtpConfig) : SMTPServer(null), MessageHandlerFactory{

    {
        setPort(configuration.port)
        setupAuthentication()
        setupStarttls()
        setSoftwareName("Intellij Idea Server")
        setMessageHandlerFactory(this)
        /*
         Disable "Received:" header construction. It involves network activity
         and may take significant amount of time in some networks.
         */
        setDisableReceivedHeaders(true);
    }

    private fun setupAuthentication() {
        if (configuration.authType != SmtpConfig.AuthType.DISABLED) {
            val authenticator = Authenticator(configuration.login, configuration.password)
            setAuthenticationHandlerFactory(EasyAuthenticationHandlerFactory(authenticator))
            if (configuration.authType == SmtpConfig.AuthType.ENFORCED) {
                setRequireAuth(true)
            }
        }
    }

    private fun setupStarttls() {
        if (configuration.transportSecurity is SmtpConfig.TransportSecurity.STARTTLS_SUPPORTED) {
            setEnableTLS(true)
        }
        if (configuration.transportSecurity is SmtpConfig.TransportSecurity.STARTTLS_ENFORCED) {
            setEnableTLS(true)
            setRequireTLS(true)
        }
    }

    public override fun createSSLSocket(socket : Socket?) : SSLSocket {
        val remoteAddress =  socket!!.getRemoteSocketAddress() as InetSocketAddress
        val sf = SSLSocketFactory.getDefault() as SSLSocketFactory
        val s = sf.createSocket(socket, remoteAddress.getHostName(), socket.getPort(), true) as SSLSocket
        // we are the server
        s.setUseClientMode(false);
        return s;
    }

    /**
     * Captures all incoming e-mails and forwards it into MailStoreComponent implementation
     */
    override fun create(ctx: MessageContext?): MessageHandler? {
        return MailSession(ctx)
    }
}
