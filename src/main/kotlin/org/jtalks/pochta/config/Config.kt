package org.jtalks.pochta.config

import java.util.Properties
import java.util.ArrayList
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 *
 */
public class Config(props: Properties) {

    val smtp: Smtp = Smtp(props)
    val http: Http = Http(props)
    val mailboxes: Mailboxes = Mailboxes(props)

    class Http(props: Properties) {
        public val port: Int = props.getInt("jtalks.pochta.http.port")
    }

    class Smtp(props: Properties) {

        public enum class AuthType {
            DISABLED
            SUPPORTED
            ENFORCED
        }

        public enum class TransportSecurity {
            PLAINTEXT
            STARTTLS_SUPPORTED
            STARTTLS_ENFORCED
            SSL
        }

        public val connectionTimeout: Int = 60000 // 1 minute
        public val port: Int = props.getInt("jtalks.pochta.smtp.port")
        public val authType: AuthType = AuthType.ENFORCED
        public val transportSecurity: TransportSecurity = TransportSecurity.PLAINTEXT
    }

    class MailboxConfig(val id: Int, val name : String, val login: String, val password: String, val size: Int) {
        val loginEscaped = URLEncoder.encode(login, StandardCharsets.UTF_8.toString())
    }

    class Mailboxes(props: Properties) : Iterable<MailboxConfig> {

        private val mailboxes = ArrayList<MailboxConfig>();

        {
            var i = 1;
            props.getString("jtalks.pochta.mailboxes").split(" ").forEach {(mbox) ->
                val login = props.getString("jtalks.pochta.mailbox.$mbox.login")
                val password = props.getString("jtalks.pochta.mailbox.$mbox.password")
                val size = props.getString("jtalks.pochta.mailbox.$mbox.size")
                mailboxes.add(MailboxConfig(i++, mbox, login, password, Integer.parseInt(size)))
            }
        }


        override fun iterator(): Iterator<MailboxConfig> = mailboxes.iterator()
    }
}