package org.jtalks.pochta.util

import java.util.HashMap

/**
 *  Thread local storage for intermediate data during processing
 */
public object Context {

    class Key
    public val PASSWORD: Key = Key()

    private val values = object: ThreadLocal<MutableMap<Key, String>>() {
        override fun initialValue(): MutableMap<Key, String> = HashMap()
    }

    fun put(key: Key, value: String): String? = values.get()?.put(key, value)

    fun get(key: Key): String? = values.get()?.get(key)

    fun contains(key: Key): Boolean = values.get()!!.containsKey(key)

    fun remove(key: Key): String? = values.get()!!.remove(key)
}