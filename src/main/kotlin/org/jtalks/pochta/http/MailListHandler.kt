package org.jtalks.pochta.http

import com.sun.net.httpserver.HttpExchange
import org.jtalks.pochta.smtp.MailSession
import org.json.simple.JSONObject
import org.jtalks.pochta.config.Config
import org.jtalks.pochta.store.Mailboxes
import com.sun.net.httpserver.HttpHandler

/**
 *  Forms JSON list from all known mails in a mailbox. TO access this REST resource
 *  client must provide a configurable secret token as a query parameter
 */
class MailListHandler() : TokenAuthenticatedHandler {

    override fun handleRequest(exchange: HttpExchange) {
        exchange.writeResponse(200, getMailListJson())
    }

    private fun getMailListJson(): String =
            Mailboxes.byContextPassword()?.map({(e) -> asJson(e) })?.asJsonArray().toString()

    private fun asJson(session: MailSession): JSONObject {
        val result = JSONObject()
        result.put("envelope_from", session.envelopeFrom)
        result.put("envelope_recipients", session.envelopeRecipients.asJsonArray())
        result.put("delivery_date", "${session.receivedDate}")
        result.put("sender_ip", session.ip)
        result.put("mail_body", session.getRawMessage())
        return result
    }
}