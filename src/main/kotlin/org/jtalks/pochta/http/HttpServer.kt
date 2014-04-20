package org.jtalks.pochta.http

import java.net.InetSocketAddress
import org.jtalks.pochta.config.ConfigLoader
import java.util.concurrent.Executors
import org.jtalks.pochta.http.controllers.RestMailboxController
import org.jtalks.pochta.http.controllers.MainPageController
import org.jtalks.pochta.http.controllers.MailboxController
import org.jtalks.pochta.http.controllers.StaticController
import org.jtalks.pochta.http.filters.TokenAuthenticationFilter
import org.jtalks.pochta.http.filters.ExceptionHandlingFilter
import org.jtalks.pochta.http.controllers.Controller
import com.sun.net.httpserver.Filter
import org.jtalks.pochta.http.controllers.RestMailController

/**
 * Simple HTTP server used for web UI and REST service endpoints
 */
 public object HttpServer {

     val server = com.sun.net.httpserver.HttpServer.create()!!
     val anonymousFilterStack = array(ExceptionHandlingFilter)
     val authenticatedFilterStack = array(TokenAuthenticationFilter, ExceptionHandlingFilter);

     {
         val config = ConfigLoader.config;
         server.bind(InetSocketAddress(config.http.port), 0)
         setupResourceHandlers()
         server.setExecutor(Executors.newFixedThreadPool(config.http.threads))
         server.start()
         println("HTTP server listening on port ${config.http.port}")
     }

     fun setupResourceHandlers(){
         createContext("/inboxes/", RestMailboxController, TokenAuthenticationFilter)
         createContext("/mails/", RestMailController, TokenAuthenticationFilter)
         createContext("/webinboxes/", MailboxController, TokenAuthenticationFilter)
         createContext("/css/", StaticController("text/css"))
         createContext("/fonts/", StaticController("application/x-woff"))
         createContext("/img/", StaticController("image/jpeg"))
         createContext("/js/", StaticController("application/javascript"))
         createContext("/", MainPageController)
     }

     private fun createContext(path: String, controller: Controller, vararg filters: Filter = array()){
         val context = server.createContext(path, controller)
         context?.getFilters()?.addAll(filters)
         context?.getFilters()?.add(ExceptionHandlingFilter)
     }
 }