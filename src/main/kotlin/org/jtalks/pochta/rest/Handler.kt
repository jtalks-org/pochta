package org.jtalks.pochta.rest

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.config.ConfigFactory
import org.jtalks.pochta.util.Context
import org.subethamail.smtp.auth.LoginFailedException

/**
 *
 */
trait Handler : HttpHandler {

    override fun handle(exchange: HttpExchange?) {
        Context.remove(Context.PASSWORD)
        val uri = exchange?.getRequestURI().toString()
        ConfigFactory.config!!.mailboxes.filter {(mbox) -> uri.contains("token=${mbox.password}") }
                .forEach {(mbox) -> Context.put(Context.PASSWORD, mbox.password); }
        if (Context.contains(Context.PASSWORD)) {
            process(exchange!!)
        } else {
            exchange?.writeResponse(403, "Rest access token is missing or invalid")
        }
    }

    fun process(exchange: HttpExchange)
}