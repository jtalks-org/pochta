package org.jtalks.pochta.http

import java.net.InetSocketAddress
import org.jtalks.pochta.config.ConfigLoader

/**
 *
 */
 public object HttpServer {

     val server = com.sun.net.httpserver.HttpServer.create()!!

     {
         val config = ConfigLoader.config;
         server.bind(InetSocketAddress(config.http.port), 0)
         setupResourceHandlers()
         server.setExecutor(null) // creates a default executor
         server.start()
         println("HTTP server listening on port ${config.http.port}")
     }

     fun setupResourceHandlers(){
         server.createContext("/inboxes/", MailListHandler())
         server.createContext("/", MainPageHandler())
     }
 }