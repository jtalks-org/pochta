package org.jtalks.pochta.http

import java.util.HashMap
import org.jtalks.pochta.http.controllers.Controller
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange

/**
 *
 */
object GenericHttpHandler : HttpHandler {

    private val controllers = HashMap<String, Controller>()


    override fun handle(exchange: HttpExchange?) {
        val path = exchange?.getRequestURI()?.getPath()

    }
}