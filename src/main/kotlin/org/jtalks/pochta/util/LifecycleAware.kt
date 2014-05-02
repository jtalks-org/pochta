package org.jtalks.pochta.util

/**
 * Allows to execute something on application exit.
 *
 * Callbacks passed here are not guaranteed to be executed
 * if JVM crashes or is killed in a severe (-9) way.
 */
public trait LifeCycleAware {

    fun addShutdownHook(shutdownHook: () -> Unit) {
        Runtime.getRuntime().addShutdownHook(object: Thread() {
            override fun run() = shutdownHook()
        })
    }
}