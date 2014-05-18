package org.jtalks.pochta.http.controllers

import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.store.MailStore

class RestMailboxController(val store: MailStore) : Controller {

    override fun process(exchange: HttpExchange) {
        exchange.setContentType("application/json")
        exchange.writeResponse(200, mailListJson())
    }

    private fun mailListJson() = store.byContextPassword()?.map{(e) -> e.asJson() }?.asJsonArray().toString()
}