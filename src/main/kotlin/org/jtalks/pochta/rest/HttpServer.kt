package org.jtalks.pochta.rest

import java.net.InetSocketAddress
import org.jtalks.pochta.config.Config
import org.jtalks.pochta.config.ConfigFactory

/**
 *
 */
 public object HttpServer {

     val server = com.sun.net.httpserver.HttpServer.create()!!

     fun start(){
         val config = ConfigFactory.config!!;
         server.bind(InetSocketAddress(config.http.port), 0)
         setupResourceHandlers(config.http)
         server.setExecutor(null) // creates a default executor
         server.start()
         println("HTTP server listening on port ${config.http.port}")
     }

     fun setupResourceHandlers(config : Config.Http){
         server.createContext("/pochta/inbox/mail", MailListHandler(config))
     }
 }