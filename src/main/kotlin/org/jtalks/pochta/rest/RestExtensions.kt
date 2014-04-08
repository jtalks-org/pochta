package org.jtalks.pochta.rest

import com.sun.net.httpserver.HttpExchange
import java.nio.charset.StandardCharsets
import org.json.simple.JSONArray

/**
 * Extension functions to ease rest data output
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

fun <T> Iterable<T>.asJsonArray(): JSONArray {
    return this.fold(JSONArray(), {(array, address) -> array.add(address); array })
}