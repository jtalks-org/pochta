package org.jtalks.pochta.rest

import java.net.InetSocketAddress
import org.jtalks.pochta.config.HttpConfig

/**
 *
 */
 public object HttpServer {

     val server = com.sun.net.httpserver.HttpServer.create()!!

     fun start(config : HttpConfig){
         server.bind(InetSocketAddress(8000), 0)
         server.createContext("/test", HttpHandler(config))
         server.setExecutor(null) // creates a default executor
         server.start()
     }
 }