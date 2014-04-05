package org.jtalks.pochta.rest

import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.config.HttpConfig

/**
 *
 */
  public class HttpHandler(config : HttpConfig) : com.sun.net.httpserver.HttpHandler{

      override fun handle(exchange: HttpExchange?) {
          val response = "This is the response"
          exchange?.sendResponseHeaders(200, response.length().toLong());
          val os = exchange?.getResponseBody();
          os?.write(response.getBytes());
          os?.close();
      }
  }