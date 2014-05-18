package org.jtalks.pochta.store

import java.util.Date
import java.util.ArrayList
import javax.mail.internet.MimeMessage

/**
 *
 */
public trait Email {

    val id : Int
    var receivedDate: Date?
    var envelopeFrom: String?
    var envelopeRecipients: ArrayList<String>
    var message: MimeMessage?
    val ip: String
    var subject: String?

    fun getRawMessage(): String
}