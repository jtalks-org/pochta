package org.jtalks.pochta.http

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange
import java.io.BufferedInputStream

/**
 * Gives away static content from classpath resources.
 * If nothing can be found in classpath for a given URL, then 404 is returned.
 */
object StaticController : Controller {

    override fun process(exchange: HttpExchange) {
        val path = exchange.getRequestURI()?.getPath().toString()
        val stream = javaClass.getResourceAsStream(path.substring(1))
        if (stream == null) {
            exchange.writeResponse(404)
        } else {
            exchange.writeResponse(200, BufferedInputStream(stream))
        }
    }
}