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
import org.jtalks.pochta.store.Mailboxes
import java.util.concurrent.atomic.AtomicInteger

/**
 *  Represents a single mail transfer conversation. This includes email itself,
 *  SMTP envelope information and client data (ip, etc). For every mail received
 *  one MailSession object is created.
 */
public class MailSession(val context: MessageContext?) : MessageHandler {

    public val id : Int = IdGenerator.next()
    public var receivedDate: Date? = null
    public var envelopeFrom: String? = null
    public var envelopeRecipients: ArrayList<String> = ArrayList<String>()
    public var message: MimeMessage? = null
    public val ip: String = context?.getRemoteAddress().toString()
    public var subject: String? = null

    override fun from(from: String?) {
        envelopeFrom = from
    }

    override fun recipient(recipient: String?) {
        envelopeRecipients.add(recipient as String)
    }

    override fun data(data: InputStream?) {
        message = MimeMessage(Session.getInstance(Properties()), data!!)
        subject = message?.getSubject()
    }

    override fun done() {
        receivedDate = Date()
        Mailboxes.byContextPassword()?.add(this)
    }

    public fun getRawMessage(): String {
        val stream = ByteArrayOutputStream()
        message?.writeTo(stream)
        return String(stream.toByteArray())
    }

    /**
     * Generates unique ids for incoming mails
     */
    object IdGenerator{
        val counter = AtomicInteger()

        fun next() = counter.incrementAndGet()
    }
}