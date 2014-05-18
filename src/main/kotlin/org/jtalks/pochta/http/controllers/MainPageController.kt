package org.jtalks.pochta.http.controllers

import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.http.ModelAndView
import java.util.concurrent.TimeUnit
import org.jtalks.pochta.config.Config
import org.jtalks.pochta.store.MailStore

class MainPageController(val config: Config, val store: MailStore) : Controller {

    override fun process(exchange: HttpExchange) {
        exchange.writeResponse(200,
                ModelAndView("main")
                        .put("smtpPort", config.smtp.port)
                        .put("smtpThreads", config.smtp.threads)
                        .put("httpPort", config.http.port)
                        .put("httpThreads", config.http.threads)
                        .put("mailsTotal", store.fold(0) { summ, elem -> summ + elem.size })
                        .put("mailsLimit", config.mailboxes.fold(0) {(summ, elem) -> summ + elem.size })
                        .put("uptime", uptimeInHours())
                        .put("mailboxes", store)
                        .renderWithCommonPageTemplate("Pochta service")
        )
    }

    private fun uptimeInHours() = TimeUnit.HOURS.convert(
            System.currentTimeMillis() - config.initTime.getTime(), TimeUnit.MILLISECONDS)
}