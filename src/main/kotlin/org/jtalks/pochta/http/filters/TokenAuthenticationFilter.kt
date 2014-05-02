package org.jtalks.pochta.http.filters

import com.sun.net.httpserver.Filter
import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.util.Context
import org.jtalks.pochta.config.ConfigLoader
import org.jtalks.pochta.http.ModelAndView
import org.jtalks.pochta.http.controllers.writeResponse
import org.jtalks.pochta.util.logWarn

object TokenAuthenticationFilter : Filter() {

    override fun description() = "Token autentication filter"

    override fun doFilter(exchange: HttpExchange?, chain: Filter.Chain?) {
        Context.remove(Context.PASSWORD)
        val uri = exchange?.getRequestURI().toString()
        ConfigLoader.config.mailboxes.filter {(mbox) -> uri.contains("token=${mbox.password}") }
                .forEach {(mbox) -> Context.put(Context.PASSWORD, mbox.password); }
        if (Context.contains(Context.PASSWORD)) {
            chain?.doFilter(exchange)
        } else {
            logWarn("Unathorized access to ${exchange?.getRequestURI()} from ${exchange?.getRemoteAddress()}")
            exchange?.writeResponse(403, ModelAndView("error403").renderWithCommonPageTemplate("Forbidden"))
        }
    }
}