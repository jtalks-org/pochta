package org.jtalks.pochta.http.controllers

import org.json.simple.JSONArray
import org.jtalks.pochta.smtp.MailSession
import org.json.simple.JSONObject

/**
 * Extension functions to ease rest data output
 */
fun <T> Iterable<T>.asJsonArray(): JSONArray {
    return this.fold(JSONArray()){(array, address) -> array.add(address); array }
}

fun MailSession.asJson(): JSONObject {
    val result = JSONObject()
    result.put("server_id", id)
    result.put("envelope_from", envelopeFrom)
    result.put("envelope_recipients", envelopeRecipients.asJsonArray())
    result.put("delivery_date", "$receivedDate")
    result.put("sender_ip", ip)
    result.put("mail_body", getRawMessage())
    return result
}