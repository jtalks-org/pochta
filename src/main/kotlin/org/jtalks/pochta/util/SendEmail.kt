package org.jtalks.pochta.util

import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.PasswordAuthentication
import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress
import javax.mail.Message
import javax.mail.Transport

/**
 *  Simple testing utility to send mails
 */
public fun main(args: Array<String>) {
    val to = "abcd@gmail.com"
    val from = "web@gmail.com"
    val properties = System.getProperties()
    properties?.put("mail.smtp.host", "localhost")
    properties?.put("mail.smtp.port", "9025")
    properties?.put("mail.smtp.auth", "true")
    val session = Session.getDefaultInstance(properties, object : javax.mail.Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication("user", "secret")
        }
    })
    try {
        val message = MimeMessage(session)
        message.setFrom(InternetAddress(from))
        message.addRecipient(Message.RecipientType.TO, InternetAddress(to))
        message.setSubject("This is the long  long long long long long long longSubject Line!")
        message.setText("This is actual message")
        Transport.send(message)
        System.out.println("Sent message successfully....")
    } catch (mex: MessagingException) {
        mex.printStackTrace()
    }

}