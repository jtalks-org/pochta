package org.jtalks.pochta.config

import java.util.Properties
import java.util.ArrayList

/**
 *
 */
public class Config(props: Properties) {

    val smtp: Smtp = Smtp(props)
    val http: Http = Http(props)
    val mailboxes: Mailboxes = Mailboxes(props)

    class Http(props: Properties) {
        public val port: Int = Integer.parseInt(props.getProperty("jtalks.pochta.http.port")!!)
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
        public val port: Int = Integer.parseInt(props.getProperty("jtalks.pochta.smtp.port")!!)
        public val authType: AuthType = AuthType.ENFORCED
        public val transportSecurity: TransportSecurity = TransportSecurity.PLAINTEXT
    }

    class MailboxConfig(val login: String, val password: String, val size: Int)

    class Mailboxes(props: Properties) : Iterable<MailboxConfig> {

        private val mailboxes = ArrayList<MailboxConfig>();

        {
            props.getProperty("jtalks.pochta.mailboxes")?.split(" ")?.forEach {(mbox) ->
                val login = props.getProperty("jtalks.pochta.mailbox.$mbox.login")
                val password = props.getProperty("jtalks.pochta.mailbox.$mbox.password")
                val size = props.getProperty("jtalks.pochta.mailbox.$mbox.size")
                if (login != null && password != null && size != null) {
                    mailboxes.add(MailboxConfig(login, password, Integer.parseInt(size)))
                }
            }
        }



        override fun iterator(): Iterator<MailboxConfig> = mailboxes.iterator()
    }
}