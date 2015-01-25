package org.jtalks.pochta.store

import org.jtalks.pochta.smtp.MailSession
import java.util.concurrent.ArrayBlockingQueue
import org.jtalks.pochta.util.Context
import org.jtalks.pochta.config.ConfigProvider
import org.jtalks.pochta.config.Config
import java.util.LinkedHashMap
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired

/**
 * Mailbox is an incoming mail in-memory storage. Each mailbox has
 * a limit on how many mails it can hold. When overflowed mailbox
 * acts like FIFO-cache: oldest entries are removed first.
 */
Component class Mailboxes [Autowired] (val configProvider: ConfigProvider): MailStore {

    private val mailboxes: Map<String, Mailbox>;

    {
        val mboxes = LinkedHashMap<String, Mailbox>();
        configProvider.config.mailboxes.forEach {(mbox) ->
            mboxes.put(mbox.password, InMemoryMailbox(mbox))
        }
        mailboxes = mboxes
    }

    override fun byContextPassword(): Mailbox? = mailboxes.get(Context.get(Context.PASSWORD))

    override fun iterator(): Iterator<Mailbox> = mailboxes.values().iterator()
}

class InMemoryMailbox(val config: Config.MailboxConfig) : Mailbox {

    private val mails = ArrayBlockingQueue<Email>(config.size)
    override var size: Int = 0

    override fun add(message: Email) {
        synchronized(mails) {
            if (mails.remainingCapacity() == 0) mails.take()
            mails.add(message)
            size = mails.size()
        }
    }

    override fun byId(id : Int) = mails.filter{(mail) -> mail.id == id}.first()

    override fun size() = mails.size()

    override fun iterator() = mails.iterator()
}