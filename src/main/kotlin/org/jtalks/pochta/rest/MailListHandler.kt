package org.jtalks.pochta.rest

import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.config.HttpConfig
import org.jtalks.pochta.smtp.MailSession
import org.json.simple.JSONObject
import org.json.simple.JSONArray
import org.jtalks.pochta.store.InboxFolder

/**
 *
 */
public class MailListHandler(val config: HttpConfig) : com.sun.net.httpserver.HttpHandler {

    override fun handle(exchange: HttpExchange?) {
        if (exchange?.getRequestURI().toString().endsWith(config.secretToken)) {
            val response = getMailListJson()
            exchange?.sendResponseHeaders(200, response.getBytes("UTF-8").size.toLong());
            val os = exchange?.getResponseBody();
            os?.write(response.getBytes());
            os?.close()
        } else {
            exchange?.sendResponseHeaders(403, 0);
            exchange?.close()
        }
    }

    private fun getMailListJson(): String =
            InboxFolder.getMessages().fold(JSONArray(), {
                (array, mail) ->
                array.add(asJson(mail))
                array
            }).toString()


    private fun asJson(session: MailSession): JSONObject {
        val result = JSONObject()
        result.put("envelope_from", session.envelopeFrom)
        result.put("envelope_recipients",
                session.envelopeRecipients.fold (JSONArray(), {(array, address) -> array.add(address); array }))
        result.put("delivery_date", session.receivedDate)
        result.put("sender_ip", session.ip)
        result.put("mail_body", session.message)
        return result
    }
}