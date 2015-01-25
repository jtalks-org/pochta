package org.jtalks.pochta.store

import org.apache.commons.mail.util.MimeMessageParser

class Base64DecodedEmail(val delegate : Email) : Email by delegate {

    override fun getMessage(): String {
        val header = message?.getHeader("Content-Transfer-Encoding")
        return when {
            header != null && "base64".equals(header.first()) -> decodeMessage();
            else -> delegate.getMessage();
        }
    }

    fun decodeMessage(): String{
        val parser = MimeMessageParser(message)
        parser.parse()
        return parser.getPlainContent()
    }
}