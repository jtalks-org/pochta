package org.jtalks.pochta.http

import com.sun.net.httpserver.HttpExchange
import java.nio.charset.StandardCharsets
import java.io.InputStream
import org.apache.commons.io.IOUtils

/**
 *  Extension functions to ease http response output
 */
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
    sendResponseHeaders(code, 0)
    close()
}