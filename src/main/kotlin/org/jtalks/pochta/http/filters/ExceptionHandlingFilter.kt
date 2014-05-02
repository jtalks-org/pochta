package org.jtalks.pochta.http.filters

import com.sun.net.httpserver.Filter
import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.http.controllers.writeResponse
import org.jtalks.pochta.http.ModelAndView
import org.jtalks.pochta.util.logError

/**
 * Catches any unhandled exception, logs it and forwards the user
 */
object ExceptionHandlingFilter: Filter() {

    override fun description() = "Filter for handling uncaught exceptions"

    override fun doFilter(exchange: HttpExchange?, chain: Filter.Chain?) {
        try {
            chain?.doFilter(exchange)
        } catch(e: Exception) {
            logError(e)
            val cause = e.getRootCause()
            exchange?.writeResponse(500, ModelAndView("error500")
                    .put("class", cause.javaClass.getCanonicalName() ?: "<Exception class is unknown>")
                    .put("message", cause.getMessage() ?: "<Exception message is not defined>")
                    .put("stackTrace", cause.getStackTrace())
                    .renderWithCommonPageTemplate("Server error"))
        }
    }

    private fun Throwable.getRootCause(): Throwable = getCause()?.getRootCause() ?: this
}