package org.jtalks.pochta.http.controllers

import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.config.ConfigLoader
import org.jtalks.pochta.http.ModelAndView
import org.jtalks.pochta.store.Mailboxes

object MailboxController : Controller {

    override fun process(exchange: HttpExchange) {
        val mailbox = Mailboxes.byContextPassword()!!
        exchange.writeResponse(200,
                ModelAndView("mailbox")
                        .put("mailbox", mailbox)
                        .renderWithCommonPageTemplate("Pochta service"))
    }
}