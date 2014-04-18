package org.jtalks.pochta.http

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.config.ConfigLoader
import org.jtalks.pochta.store.Mailboxes
import java.util.concurrent.TimeUnit

/**
 *
 */
object MainPageHandler : HttpHandler {

    override fun handle(exchange: HttpExchange?) {
        val config = ConfigLoader.config
        exchange?.writeResponse(200,
                ModelAndView("main")
                        .put("smtpPort", config.smtp.port)
                        .put("smtpThreads", config.smtp.threads)
                        .put("httpPort", config.http.port)
                        .put("httpThreads", config.http.threads)
                        .put("mailsTotal", Mailboxes.fold(0) { summ, elem -> summ + elem.size })
                        .put("mailsLimit", config.mailboxes.fold(0) {(summ, elem) -> summ + elem.size })
                        .put("uptime", uptimeInHours())
                        .put("mailboxes", Mailboxes)
                        .renderWithCommonPageTemplate("Pochta service")
        )
    }

    private fun uptimeInHours() = TimeUnit.HOURS.convert(
                System.currentTimeMillis() - ConfigLoader.config.initTime.getTime(), TimeUnit.MILLISECONDS)
}