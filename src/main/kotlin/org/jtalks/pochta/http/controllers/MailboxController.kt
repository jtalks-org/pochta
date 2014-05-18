package org.jtalks.pochta.http.controllers

import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.http.ModelAndView
import org.jtalks.pochta.store.MailStore

class MailboxController(val mailboxes: MailStore) : Controller {

    override fun process(exchange: HttpExchange) {
        val mailbox = mailboxes.byContextPassword()!!
        exchange.writeResponse(200,
                ModelAndView("mailbox")
                        .put("mailbox", mailbox)
                        .renderWithCommonPageTemplate("Pochta service"))
    }
}