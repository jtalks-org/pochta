package org.jtalks.pochta.store

import org.jtalks.pochta.smtp.MailSession
import java.util.ArrayList
import java.util.Collections

/**
 *
 */
public object InboxFolder {

    private val mails = ArrayList<MailSession>()

    public fun add(message: MailSession) {
        InboxFolder.mails.add(message)
    }

    public fun clear() {
        InboxFolder.mails.clear()
    }

    public fun getMessages(): List<MailSession> = Collections.unmodifiableList(mails)

    public fun messageCount(): Int = mails.size

    public fun get(i: Int): MailSession = mails.get(i)
}