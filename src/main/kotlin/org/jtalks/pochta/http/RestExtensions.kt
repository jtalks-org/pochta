package org.jtalks.pochta.http

import com.sun.net.httpserver.HttpExchange
import java.nio.charset.StandardCharsets
import org.json.simple.JSONArray

/**
 * Extension functions to ease rest data output
 */
fun <T> Iterable<T>.asJsonArray(): JSONArray {
    return this.fold(JSONArray()){(array, address) -> array.add(address); array }
}