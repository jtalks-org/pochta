package org.jtalks.pochta.http.controllers

import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.store.Mailboxes
import org.jtalks.pochta.smtp.MailSession
import org.json.simple.JSONObject

/**
 *
 */
class RestMailController(val store: Mailboxes): Controller {

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