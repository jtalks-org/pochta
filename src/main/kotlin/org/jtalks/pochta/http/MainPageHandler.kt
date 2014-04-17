package org.jtalks.pochta.http

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.config.ConfigLoader
import org.jtalks.pochta.store.Mailboxes

/**
 *
 */
object MainPageHandler : HttpHandler {

    override fun handle(exchange: HttpExchange?) {
        exchange?.writeResponse(200,
                ModelAndView("main")
                        .put("smtpPort", ConfigLoader.config.smtp.port)
                        .put("httpPort", ConfigLoader.config.http.port)
                        .put("mailboxes", Mailboxes)
                        .renderWithCommonPageTemplate("Pochta service")
        )
    }
}