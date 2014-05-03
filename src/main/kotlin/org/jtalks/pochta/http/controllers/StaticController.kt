package org.jtalks.pochta.http.controllers

import com.sun.net.httpserver.HttpExchange
import java.io.BufferedInputStream
import java.util.Calendar
import java.util.Date

/**
 * todo: autodetect mime-type, at least by file extension
 */
class StaticController(val mime: String) : Controller {

    override fun process(exchange: HttpExchange) {
        if (exchange.getRequestHeaders()?.containsKey("If-Modified-Since")!!) {
            exchange.writeResponse(304)
        } else {
            // resources are fingerprinted with some digits at the end of the URL to control caching
            val path = exchange.getRequestURI()?.getPath().toString().replaceAll("\\d*$", "")
            val stream = javaClass.getClassLoader()?.getResourceAsStream("org/jtalks/pochta/http$path")
            if (stream == null) {
                // we have no suitable resource matching this request
                exchange.writeResponse(404)
            } else {
                exchange.setContentType(mime)
                exchange.setHeader("Expires", gmtFormattedOneYearInFuture())
                exchange.setHeader("Last-Modified", Date(0).toString())
                exchange.setHeader("Cache-Control", "public, max-age=31536000")
                exchange.writeResponse(200, BufferedInputStream(stream))
            }
        }
    }

    private fun gmtFormattedOneYearInFuture() : String{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, 1)
        return calendar.getTime().toString()
    }
}