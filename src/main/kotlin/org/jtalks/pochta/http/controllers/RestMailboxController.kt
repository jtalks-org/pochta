package org.jtalks.pochta.http.controllers

import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.smtp.MailSession
import org.json.simple.JSONObject
import org.jtalks.pochta.http.controllers
import org.jtalks.pochta.store.Mailboxes

object RestMailboxController : Controller {

    override fun process(exchange: HttpExchange) {
        exchange.setContentType("application/json")
        exchange.writeResponse(200, getMailListJson())
    }

    private fun getMailListJson(): String =
            Mailboxes.byContextPassword()?.map{(e) -> e.asJson() }?.asJsonArray().toString()
}