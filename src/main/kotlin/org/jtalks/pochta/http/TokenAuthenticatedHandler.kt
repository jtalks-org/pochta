package org.jtalks.pochta.http

import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.util.Context
import org.jtalks.pochta.config.ConfigLoader
import com.sun.net.httpserver.HttpHandler

/**
 *
 */
trait TokenAuthenticatedHandler : HttpHandler {

    override fun handle(exchange: HttpExchange?) {
        Context.remove(Context.PASSWORD)
        val uri = exchange?.getRequestURI().toString()
        ConfigLoader.config.mailboxes.filter {(mbox) -> uri.contains("token=${mbox.password}") }
                .forEach {(mbox) -> Context.put(Context.PASSWORD, mbox.password); }
        if (Context.contains(Context.PASSWORD)) {
            handleRequest(exchange!!)
        } else {
            try {
                exchange?.writeResponse(403, ModelAndView("error403").renderWithCommonPageTemplate("Forbidden"))
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun handleRequest(exchange: HttpExchange)
}