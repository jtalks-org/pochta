package org.jtalks.pochta.http.controllers

import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpExchange

trait Controller : HttpHandler {

    override fun handle(exchange: HttpExchange?) = process(exchange!!)

    fun process(exchange: HttpExchange)
}