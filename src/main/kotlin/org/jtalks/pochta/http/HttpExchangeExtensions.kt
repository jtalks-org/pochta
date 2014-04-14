package org.jtalks.pochta.http

import com.sun.net.httpserver.HttpExchange
import java.nio.charset.StandardCharsets

/**
 *  Extension functions to ease http response output
 */
fun HttpExchange.writeResponse(code: Int, content: String) {
    val charset = StandardCharsets.UTF_8
    sendResponseHeaders(code, content.getBytes(charset).size.toLong())
    val os = getResponseBody()
    os?.write(content.getBytes(charset))
    os?.close()
}

fun HttpExchange.writeResponse(code: Int) {
    sendResponseHeaders(code, 0)
    close()
}