package org.jtalks.pochta.smtp

import org.jtalks.pochta.config.SmtpConfig
import java.net.ServerSocket
import java.net.InetSocketAddress
import javax.net.ssl.SSLServerSocketFactory

/**
 * TLS-secured SMTP server implementation.
 * It requires client connections to start with a handshake.
 */
public class SmtpsMailServer(configuration: SmtpConfig) : SmtpMailServer(configuration){

    {
        setEnableTLS(false); // disable STARTTLS, it makes no sense here
    }

    protected override fun createServerSocket(): ServerSocket {
        val isa = InetSocketAddress(getBindAddress(), getPort())
        val factory = SSLServerSocketFactory.getDefault() as SSLServerSocketFactory
        val serverSocket = factory.createServerSocket(getPort(), getBacklog(), isa.getAddress())
        if (getPort() == 0) setPort(serverSocket.getLocalPort())
        return serverSocket
    }
}
