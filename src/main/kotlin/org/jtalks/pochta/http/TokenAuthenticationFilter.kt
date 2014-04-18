package org.jtalks.pochta.http

import com.sun.net.httpserver.Filter
import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.util.Context
import org.jtalks.pochta.config.ConfigLoader

object TokenAuthenticationFilter : Filter() {

    override fun description() = "Token qutentication filter"

    override fun doFilter(exchange: HttpExchange?, chain: Filter.Chain?) {
        Context.remove(Context.PASSWORD)
        val uri = exchange?.getRequestURI().toString()
        ConfigLoader.config.mailboxes.filter {(mbox) -> uri.contains("token=${mbox.password}") }
                .forEach {(mbox) -> Context.put(Context.PASSWORD, mbox.password); }
        if (Context.contains(Context.PASSWORD)) {
            chain?.doFilter(exchange)
        } else {
            exchange?.writeResponse(403, ModelAndView("error403").renderWithCommonPageTemplate("Forbidden"))
        }
    }
}