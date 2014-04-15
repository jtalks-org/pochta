package org.jtalks.pochta.store

import org.jtalks.pochta.smtp.MailSession
import java.util.concurrent.ArrayBlockingQueue
import org.jtalks.pochta.util.Context
import org.jtalks.pochta.config.ConfigLoader
import org.jtalks.pochta.config.Config
import java.util.LinkedHashMap

/**
 * Mailbox is an incoming mail in-memory storage. Each mailbox has
 * a limit on how many mails it can hold. When overflowed mailbox
 * acts like FIFO-cache: oldest entries are removed first.
 */
object Mailboxes: Iterable<Mailbox> {

    private val mailboxes: Map<String, Mailbox>;

    {
        val mboxes = LinkedHashMap<String, Mailbox>();
        val config = ConfigLoader.config.mailboxes
        config.forEach {(mbox) ->
            mboxes.put(mbox.password, Mailbox(mbox)
            )
        }
        mailboxes = mboxes
    }

    public fun byContextPassword(): Mailbox? = mailboxes.get(Context.get(Context.PASSWORD))

    override fun iterator(): Iterator<Mailbox> = mailboxes.values().iterator()
}

class Mailbox(val config: Config.MailboxConfig) : Iterable<MailSession> {

    private val mails = ArrayBlockingQueue<MailSession>(config.size)
    public var size: Int = 0

    public fun add(message: MailSession) {
        synchronized(mails) {
            if (mails.remainingCapacity() == 0) mails.take()
            mails.add(message)
            size = mails.size
        }
    }
    fun size() = mails.size()

    override fun iterator(): Iterator<MailSession> = mails.iterator()
}