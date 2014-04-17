package org.jtalks.pochta.http

import java.net.InetSocketAddress
import org.jtalks.pochta.config.ConfigLoader
import java.util.concurrent.Executors

/**
 *
 */
 public object HttpServer {

     val server = com.sun.net.httpserver.HttpServer.create()!!

     {
         val config = ConfigLoader.config;
         server.bind(InetSocketAddress(config.http.port), 0)
         setupResourceHandlers()
         server.setExecutor(Executors.newFixedThreadPool(5))
         server.start()
         println("HTTP server listening on port ${config.http.port}")
     }

     fun setupResourceHandlers(){
         server.createContext("/inboxes/", MailListHandler)
         server.createContext("/css/", StaticHandler)
         server.createContext("/fonts/", StaticHandler)
         server.createContext("/img/", StaticHandler)
         server.createContext("/", MainPageHandler)
     }
 }