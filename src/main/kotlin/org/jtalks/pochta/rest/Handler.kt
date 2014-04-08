package org.jtalks.pochta.rest

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.util.Context
import org.subethamail.smtp.auth.LoginFailedException
import org.jtalks.pochta.config.ConfigLoader

/**
 *
 */
trait Handler : HttpHandler {

    override fun handle(exchange: HttpExchange?) {
        Context.remove(Context.PASSWORD)
        val uri = exchange?.getRequestURI().toString()
        ConfigLoader.config.mailboxes.filter {(mbox) -> uri.contains("token=${mbox.password}") }
                .forEach {(mbox) -> Context.put(Context.PASSWORD, mbox.password); }
        if (Context.contains(Context.PASSWORD)) {
            process(exchange!!)
        } else {
            exchange?.writeResponse(403,
                    "<html><body>Rest access token is missing or invalid. Make sure your HTTP request has ?token=&lt;password&gt; " +
                    "parameter specified, it should match mailbox password. For additional information please " +
                    "refer to <a href='https://github.com/jtalks-org/pochta'>Pochta project site</a>.</body></html>")
        }
    }

    fun process(exchange: HttpExchange)
}