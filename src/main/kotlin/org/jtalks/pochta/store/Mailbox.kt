package org.jtalks.pochta.store

/**
 *
 */
public trait Mailbox : Iterable<Email>{

    var size: Int

    fun add(message: Email) : Unit

    fun byId(id : Int) : Email?

    fun size() : Int
}