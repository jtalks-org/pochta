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
import java.util.concurrent.atomic.AtomicInteger
import org.jtalks.pochta.store.Email
import org.jtalks.pochta.store.MailStore

/**
 *  Represents a single mail transfer conversation. This includes email itself,
 *  SMTP envelope information and client data (ip, etc). For every mail received
 *  one MailSession object is created.
 */
public class MailSession(val context: MessageContext?, val store: MailStore) : MessageHandler, Email {

    override val id = IdGenerator.next()
    override var receivedDate: Date? = null
    override var envelopeFrom: String? = null
    override var envelopeRecipients = ArrayList<String>()
    override var message: MimeMessage? = null
    override val ip = context?.getRemoteAddress().toString()
    override var subject: String? = null

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
        store.byContextPassword()?.add(this)
    }

    override fun getMessage(): String {
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