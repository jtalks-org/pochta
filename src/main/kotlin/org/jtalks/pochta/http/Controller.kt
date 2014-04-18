package org.jtalks.pochta.http

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange

/**
 * Base trait for all http controllers
 */
trait Controller : HttpHandler {

    override fun handle(exchange: HttpExchange?) = process(exchange!!)

    fun process(exchange: HttpExchange)
}