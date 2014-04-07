package org.jtalks.pochta.store

import org.jtalks.pochta.smtp.MailSession
import java.util.concurrent.ArrayBlockingQueue
import org.jtalks.pochta.config.Config

/**
 * Mailbox is an incoming mail in-memory storage. Each folder has a limit
 * on how many mails it can hold. When overflowed folder acts like FIFO-cache;
 * oldest entries are removed.
 */
public object Mailbox : Iterable<MailSession> {

    private var mails = ArrayBlockingQueue<MailSession>(500)

    public fun configure(config : Config.Smtp) {
        mails = ArrayBlockingQueue<MailSession>(config.mailboxLimit)
    }

    public fun add(message: MailSession) {
        synchronized(mails) {
            if (mails.remainingCapacity() == 0) {
                mails.take()
            }
            mails.add(message)
        }
    }

    override fun iterator(): Iterator<MailSession> = mails.iterator()
}