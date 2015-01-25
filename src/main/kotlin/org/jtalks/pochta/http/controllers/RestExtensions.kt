package org.jtalks.pochta.http.controllers

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.jtalks.pochta.store.Email
import org.jtalks.pochta.store.Base64DecodedEmail

/**
 * Extension functions to ease rest data output
 */
fun <T> Iterable<T>.asJsonArray(): JSONArray {
    return this.fold(JSONArray()){(array, address) -> array.add(address); array }
}

fun Email.asJson(): JSONObject {
    val result = JSONObject()
    result.put("server_id", id)
    result.put("envelope_from", envelopeFrom)
    result.put("envelope_recipients", envelopeRecipients.asJsonArray())
    result.put("delivery_date", "$receivedDate")
    result.put("sender_ip", ip)
    result.put("mail_body", Base64DecodedEmail(this).getMessage())
    result.put("mail_body_raw", getMessage())
    return result
}