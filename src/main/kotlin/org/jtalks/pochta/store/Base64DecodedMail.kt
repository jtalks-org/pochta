package org.jtalks.pochta.store

import org.apache.commons.mail.util.MimeMessageParser

class Base64DecodedEmail(val delegate : Email) : Email by delegate {

    override fun getMessage(): String {
        val parser = MimeMessageParser(message)
        parser.parse()
        val builder = StringBuilder("  From: ${parser.getFrom()}\n")
        for (address in parser.getTo()){
            builder.append("  To: $address\n")
        }
        for (address in parser.getCc()){
            builder.append("  Cc: $address\n")
        }
        for (address in parser.getBcc()){
            builder.append("  Bcc: $address\n")
        }
        builder.append("  Subject: ${parser.getSubject()}\n")
        val plainTextContent =  parser.getPlainContent();
        if (plainTextContent == null){
            builder.append("No plain text content to display(")
        } else {
            builder.append(plainTextContent)
        }
        return builder.toString()
    }
}