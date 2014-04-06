package org.jtalks.pochta.smtp

import org.subethamail.smtp.MessageHandler
import java.util.Date
import java.util.ArrayList
import org.subethamail.smtp.MessageContext
import javax.mail.internet.MimeMessage
import java.io.InputStream
import javax.mail.Session
import java.util.Properties
import java.io.ByteArrayOutputStream
import org.jtalks.pochta.store.InboxFolder

/**
 *
 */
public class MailSession(val context: MessageContext?) : MessageHandler {

    public var receivedDate: Date? = null
    public var envelopeFrom: String? = null
    public var envelopeRecipients: ArrayList<String> = ArrayList<String>()
    public var message: MimeMessage? = null
    public val ip: String = context?.getRemoteAddress().toString()

    override fun from(from: String?) {
        envelopeFrom = from
    }

    override fun recipient(recipient: String?) {
        envelopeRecipients.add(recipient as String)
    }

    override fun data(data: InputStream?) {
        message = MimeMessage(Session.getInstance(Properties()), data)
    }

    override fun done() {
        receivedDate = Date()
        InboxFolder.add(this)
    }

    public fun getRawMessage(): String {
        val stream = ByteArrayOutputStream()
        message?.writeTo(stream)
        return String(stream.toByteArray())
    }

}