package org.jtalks.pochta.http.controllers

import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.store.MailStore

/**
 *
 */
class RestMailController(val store: MailStore): Controller {

    override fun process(exchange: HttpExchange) {
        val mail = store.byContextPassword()?.byId(exchange.getRequestedId())
        if (mail == null) {
            exchange.writeResponse(404);
        } else {
            exchange.setContentType("application/json")
            exchange.writeResponse(200, mail.asJson().toString());
        }
    }
}