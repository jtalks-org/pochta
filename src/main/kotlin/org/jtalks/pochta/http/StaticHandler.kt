package org.jtalks.pochta.http

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange
import java.io.BufferedInputStream

/**
 *
 */
object StaticHandler : HttpHandler {

    override fun handle(exchange: HttpExchange?) {
        val path = exchange?.getRequestURI()?.getPath().toString()
        val stream = javaClass.getResourceAsStream(path.substring(1))
        if (stream == null) {
            exchange?.writeResponse(404)
        } else {
            exchange?.writeResponse(200, BufferedInputStream(stream))
        }
    }
}