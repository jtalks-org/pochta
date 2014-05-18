package org.jtalks.pochta.http.controllers

import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.smtp.MailSession
import org.json.simple.JSONObject
import org.jtalks.pochta.http.controllers
import org.jtalks.pochta.store.Mailboxes

class RestMailboxController(val store: Mailboxes) : Controller {

    override fun process(exchange: HttpExchange) {
        exchange.setContentType("application/json")
        exchange.writeResponse(200, mailListJson())
    }

    private fun mailListJson() = store.byContextPassword()?.map{(e) -> e.asJson() }?.asJsonArray().toString()
}