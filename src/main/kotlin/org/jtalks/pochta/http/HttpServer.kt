package org.jtalks.pochta.http

import java.net.InetSocketAddress
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
import org.jtalks.pochta.util.logInfo
import org.jtalks.pochta.config.Config
import org.springframework.stereotype.Component
import org.jtalks.pochta.config.ConfigProvider
import org.jtalks.pochta.store.MailStore
import org.springframework.beans.factory.annotation.Autowired

/**
 * Simple HTTP server used for web UI and REST service endpoints
 */
Component class HttpServer [Autowired] (configProvider: ConfigProvider, val store: MailStore) {

    val server = com.sun.net.httpserver.HttpServer.create()!!

    {
        val config = configProvider.config;
        server.bind(InetSocketAddress(config.http.port), 0)
        setupResourceHandlers(configProvider.config)
        server.setExecutor(Executors.newFixedThreadPool(config.http.threads))
        server.start()
        Runtime.getRuntime().addShutdownHook(Thread(Runnable { server.stop(1) }))
        logInfo("HTTP server listening on port ${config.http.port}")
    }

    fun setupResourceHandlers(config: Config) {
        val authFilter = TokenAuthenticationFilter(config)
        createContext("/inboxes/", RestMailboxController(store), authFilter)
        createContext("/mails/", RestMailController(store), authFilter)
        createContext("/webinboxes/", MailboxController(store), authFilter)
        createContext("/css/", StaticController("text/css"))
        createContext("/fonts/", StaticController("application/x-woff"))
        createContext("/img/", StaticController("image/jpeg"))
        createContext("/js/", StaticController("application/javascript"))
        createContext("/", MainPageController(config, store))
    }

    private fun createContext(path: String, controller: Controller, vararg filters: Filter = array()) {
        val context = server.createContext(path, controller)
        context?.getFilters()?.addAll(filters)
        context?.getFilters()?.add(ExceptionHandlingFilter)
    }
}