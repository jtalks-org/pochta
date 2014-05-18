package org.jtalks.pochta.store

/**
 *
 */
public trait MailStore : Iterable<Mailbox> {

    fun byContextPassword(): Mailbox?
}