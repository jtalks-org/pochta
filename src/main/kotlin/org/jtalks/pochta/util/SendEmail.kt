package org.jtalks.pochta.util

import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.PasswordAuthentication
import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress
import javax.mail.Message
import javax.mail.Transport
import java.io.StringReader
import java.io.ByteArrayInputStream

/**
 *  Simple testing utility to send mails
 */
public fun main(args: Array<String>) {
    Transport.send(multipartMail())
    System.out.println("Sent message successfully....")
}

fun plainMail(): MimeMessage {
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
    val message = MimeMessage(session)
    message.setFrom(InternetAddress(from))
    message.addRecipient(Message.RecipientType.TO, InternetAddress(to))
    message.setSubject("This is the long  long long long long long long longSubject Line!")
    message.setText("This is actual message")
    return message
}

fun b64Mail(): MimeMessage {
    val message = plainMail()
    message.setHeader("Content-Transfer-Encoding", "base64");
    return message
}

fun multipartMail(): MimeMessage {
    val properties = System.getProperties()
    properties?.put("mail.smtp.host", "localhost")
    properties?.put("mail.smtp.port", "9025")
    properties?.put("mail.smtp.auth", "true")
    val session = Session.getDefaultInstance(properties, object : javax.mail.Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication("user", "secret")
        }
    })
    val stream = ByteArrayInputStream(("From: info@autotests.jtalks.org \n" +
            "To: jtXhEOxN8o2aVYDxNm3YIyDOO@jtalks.org \n" +
            "Message-ID: <1799061474.01422213640062.JavaMail.i_autotests@jtalks-infra-1> \n" +
            "Subject: =?UTF-8?B?0JDQutGC0LjQstCw0YbQuNGPINCw0LrQutCw0YPQvdGC0LAgSlRhbGtz?= \n" +
            "MIME-Version: 1.0 \n" +
            "Content-Type: multipart/mixed; \n" +
            "boundary=\"----=_Part_0_980719463.1422213640036\" \n" +
            "Date: Sun, 25 Jan 2015 20:20:40 +0100 (CET) \n" +
            "\n" +
            "------=_Part_0_980719463.1422213640036 \n" +
            "Content-Type: multipart/related; \n" +
            "boundary=\"----=_Part_1_2012376838.1422213640046\" \n" +
            "\n" +
            "------=_Part_1_2012376838.1422213640046 \n" +
            "Content-Type: multipart/alternative; \n" +
            "boundary=\"----=_Part_2_1297559663.1422213640047\" \n" +
            "\n" +
            "------=_Part_2_1297559663.1422213640047 \n" +
            "Content-Type: text/plain; charset=UTF-8 \n" +
            "Content-Transfer-Encoding: base64 \n" +
            "\n" +
            "CtCf0YDQuNCy0LXRgiwganRYaEVPeE44bzJhVllEeE5tM1lJeURPTyEKCtCt0YLQviDQv9C40YHR\n" +
            "jNC80L4g0LTQu9GPINC/0L7QtNGC0LLQtdGA0LbQtNC10L3QuNGPINGA0LXQs9C40YHRgtGA0LDR\n" +
            "htC40Lgg0L3QsCBKVGFsa3Mg0YTQvtGA0YPQvNC1LgrQn9C+0LbQsNC70YPQudGB0YLQsCwg0L/R\n" +
            "gNC+0YHQu9C10LTRg9C50YLQtSDQv9C+INGB0YHRi9C70LrQtSDQvdC40LbQtSDQtNC70Y8g0LDQ\n" +
            "utGC0LjQstCw0YbQuNC4INCS0LDRiNC10LPQviDQsNC60LrQsNGD0L3RgtCwLiDQrdGC0LAg0YHR\n" +
            "gdGL0LvQutCwINC00LXQudGB0YLQstC40YLQtdC70YzQvdCwIDI0INGH0LDRgdCwLgrQktC90LjQ\n" +
            "vNCw0L3QuNC1LCDQsNC60LrQsNGD0L3RgiDQsdGD0LTQtdGCINCw0LLRgtC+0LzQsNGC0LjRh9C1\n" +
            "0YHQutC4INGD0LTQsNC70LXQvSDRh9C10YDQtdC3IDI0INGH0LDRgdCwLCDQtdGB0LvQuCDQvdC1\n" +
            "INCx0YPQtNC10YIg0LDQutGC0LjQstC40YDQvtCy0LDQvS4K0JDQutGC0LjQstCw0YbQuNC+0L3Q\n" +
            "vdCw0Y8g0YHRgdGL0LvQutCwOiBodHRwOi8vYXV0b3Rlc3RzLmp0YWxrcy5vcmc6ODAvamNvbW11\n" +
            "bmUvdXNlci9hY3RpdmF0ZS83ODQxNmRmYi1lM2FjLTRkOGEtYWIzMy03OWM4MGQwZjFjMGUKCtCh\n" +
            "INC90LDQuNC70YPRh9GI0LjQvNC4INC/0L7QttC10LvQsNC90LjRj9C80LgsCtCk0L7RgNGD0Lwg\n" +
            "SlRhbGtzLg== \n" +
            "------=_Part_2_1297559663.1422213640047 \n" +
            "Content-Type: text/html;charset=UTF-8 \n" +
            "Content-Transfer-Encoding: base64 \n" +
            "\n" +
            "CjxwPtCf0YDQuNCy0LXRgiwganRYaEVPeE44bzJhVllEeE5tM1lJeURPTyE8L3A+Cjxici8+Cjxw \n" +
            "PtCt0YLQviDQv9C40YHRjNC80L4g0LTQu9GPINC/0L7QtNGC0LLQtdGA0LbQtNC10L3QuNGPINGA\n" +
            "0LXQs9C40YHRgtGA0LDRhtC40Lgg0L3QsCBKVGFsa3Mg0YTQvtGA0YPQvNC1LjwvcD4KPHA+0J/Q\n" +
            "vtC20LDQu9GD0LnRgdGC0LAsINC/0YDQvtGB0LvQtdC00YPQudGC0LUg0L/QviDRgdGB0YvQu9C6\n" +
            "0LUg0L3QuNC20LUg0LTQu9GPINCw0LrRgtC40LLQsNGG0LjQuCDQktCw0YjQtdCz0L4g0LDQutC6\n" +
            "0LDRg9C90YLQsC4g0K3RgtCwINGB0YHRi9C70LrQsCDQtNC10LnRgdGC0LLQuNGC0LXQu9GM0L3Q\n" +
            "sCAyNCDRh9Cw0YHQsC48L3A+CjxwPtCS0L3QuNC80LDQvdC40LUsINCw0LrQutCw0YPQvdGCINCx\n" +
            "0YPQtNC10YIg0LDQstGC0L7QvNCw0YLQuNGH0LXRgdC60Lgg0YPQtNCw0LvQtdC9INGH0LXRgNC1\n" +
            "0LcgMjQg0YfQsNGB0LAsINC10YHQu9C4INC90LUg0LHRg9C00LXRgiDQsNC60YLQuNCy0LjRgNC+\n" +
            "0LLQsNC9LjwvcD4KPHA+0JDQutGC0LjQstCw0YbQuNC+0L3QvdCw0Y8g0YHRgdGL0LvQutCwOiA8\n" +
            "YSBocmVmPSJodHRwOi8vYXV0b3Rlc3RzLmp0YWxrcy5vcmc6ODAvamNvbW11bmUvdXNlci9hY3Rp\n" +
            "dmF0ZS83ODQxNmRmYi1lM2FjLTRkOGEtYWIzMy03OWM4MGQwZjFjMGUiPmh0dHA6Ly9hdXRvdGVz\n" +
            "dHMuanRhbGtzLm9yZy9qY29tbXVuZS91c2VyL2FjdGl2YXRlLzc4NDE2ZGZiLWUzYWMtNGQ4YS1h\n" +
            "YjMzLTc5YzgwZDBmMWMwZTwvYT48L3A+CjxiciAvPgo8cD7QoSDQvdCw0LjQu9GD0YfRiNC40LzQ\n" +
            "uCDQv9C+0LbQtdC70LDQvdC40Y/QvNC4LDwvcD4KPHA+0KTQvtGA0YPQvCBKVGFsa3MuPC9wPg==\n" +
            "------=_Part_2_1297559663.1422213640047-- \n" +
            "\n" +
            "------=_Part_1_2012376838.1422213640046-- \n" +
            "\n" +
            "------=_Part_0_980719463.1422213640036-- \n" +
            "").getBytes("UTF-8"))
    return MimeMessage(session, stream)
}