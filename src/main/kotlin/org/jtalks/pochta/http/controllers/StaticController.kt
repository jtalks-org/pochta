package org.jtalks.pochta.http.controllers

import com.sun.net.httpserver.HttpExchange
import java.io.BufferedInputStream

/**
 * todo: autodetect mime-type, at least by file extension
 */
class StaticController(val mime: String) : Controller {

    override fun process(exchange: HttpExchange) {
        val path = exchange.getRequestURI()?.getPath().toString()
        val stream = javaClass.getClassLoader()?.getResourceAsStream("org/jtalks/pochta/http/${path.substring(1)}")
        if (stream == null) {
            exchange.writeResponse(404)
        } else {
            exchange.setContentType(mime)
            exchange.writeResponse(200, BufferedInputStream(stream))
        }
    }
}