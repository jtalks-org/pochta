package org.jtalks.pochta.rest

import java.net.InetSocketAddress
import org.jtalks.pochta.config.Config

/**
 *
 */
 public object HttpServer {

     val server = com.sun.net.httpserver.HttpServer.create()!!

     fun start(config : Config.Http){
         server.bind(InetSocketAddress(config.port), 0)
         setupResourceHandlers(config)
         server.setExecutor(null) // creates a default executor
         server.start()
         println("HTTP server listening on port ${config.port}")
     }

     fun setupResourceHandlers(config : Config.Http){
         server.createContext("/pochta/inbox/mail", MailListHandler(config))
     }
 }