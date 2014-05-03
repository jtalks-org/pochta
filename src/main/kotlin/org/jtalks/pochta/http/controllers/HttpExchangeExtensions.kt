package org.jtalks.pochta.http.controllers

import com.sun.net.httpserver.HttpExchange
import java.nio.charset.StandardCharsets
import java.io.InputStream
import org.apache.commons.io.IOUtils

/**
 *  Extension functions to ease http response output
 */
fun HttpExchange.setContentType(mime: String) {
    getResponseHeaders()?.set("Content-Type", mime);
}

fun HttpExchange.setHeader(name: String, value: String) {
    getResponseHeaders()?.set(name, value);
}

fun HttpExchange.writeResponse(code: Int, content: String) {
    val charset = StandardCharsets.UTF_8
    sendResponseHeaders(code, content.getBytes(charset).size.toLong())
    val os = getResponseBody()
    os?.write(content.getBytes(charset))
    close()
}

fun HttpExchange.writeResponse(code: Int, content: InputStream) {
    sendResponseHeaders(code, content.available().toLong())
    IOUtils.copy(content, getResponseBody())
    IOUtils.closeQuietly(content)
    close()
}

fun HttpExchange.writeResponse(code: Int) {
    sendResponseHeaders(code, if (code == 304) -1 else 0)
    close()
}

/**
 * REST-like convention: id is the last element in the path
 */
fun HttpExchange.getRequestedId() = Integer.parseInt(getRequestURI()!!.getPath().toString().split('/').last())