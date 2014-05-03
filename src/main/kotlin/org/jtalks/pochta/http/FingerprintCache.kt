package org.jtalks.pochta.http

import java.util.concurrent.ConcurrentHashMap
import org.apache.commons.io.IOUtils

/**
 *  Generates and maintains fingerprints for static web resources.
 *  Fingerprints change file name when the content is changed to force browser cache update
 */
object FingerprintCache {

    private val fingerprints: MutableMap<String, Int> = ConcurrentHashMap()

    fun get(key: String): Int = fingerprints.getOrPut(key, {
        val stream = javaClass.getClassLoader()?.getResourceAsStream("org/jtalks/pochta/http$key")
        val fingerprint = Math.abs(IOUtils.toString(stream)!!.hashCode())
        IOUtils.closeQuietly(stream)
        fingerprint
    })

}