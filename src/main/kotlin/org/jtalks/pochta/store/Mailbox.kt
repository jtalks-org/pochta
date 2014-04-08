package org.jtalks.pochta.store

import org.jtalks.pochta.smtp.MailSession
import java.util.concurrent.ArrayBlockingQueue
import java.util.HashMap
import org.jtalks.pochta.util.Context
import org.jtalks.pochta.config.ConfigLoader

/**
 * Mailbox is an incoming mail in-memory storage. Each mailbox has
 * a limit on how many mails it can hold. When overflowed mailbox
 * acts like FIFO-cache: oldest entries are removed first.
 */
object Mailboxes {

    private val mailboxes: Map<String, Mailbox>;

    {
        val mboxes = HashMap<String, Mailbox>();
        val config = ConfigLoader.config.mailboxes
        config.forEach {(mbox) ->
            mboxes.put(mbox.password, Mailbox(mbox.size)
            ) }
        mailboxes = mboxes
    }

    public fun byContextPassword(): Mailbox? = mailboxes.get(Context.get(Context.PASSWORD))
}

class Mailbox(size: Int) : Iterable<MailSession> {

    private val mails = ArrayBlockingQueue<MailSession>(size)

    public fun add(message: MailSession) {
        synchronized(mails) {
            if (mails.remainingCapacity() == 0) mails.take()
            mails.add(message)
        }
    }

    override fun iterator(): Iterator<MailSession> = mails.iterator()
}