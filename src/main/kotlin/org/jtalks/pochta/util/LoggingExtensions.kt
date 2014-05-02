package org.jtalks.pochta.util

import org.slf4j.LoggerFactory
import ch.qos.logback.classic.Level

/**
 * Provides common logging facilities for all application classes
 * to remove the need of explicit logger declarations
 */
fun Any.logDebug(message: String, vararg args: Any) = LoggerFactory.getLogger(javaClass)?.debug(message, args)

fun Any.logInfo(message: String, vararg args: Any) = LoggerFactory.getLogger(javaClass)?.info(message, args)

fun Any.logWarn(message: String, vararg args: Any) = LoggerFactory.getLogger(javaClass)?.warn(message, args)

fun Any.logError(message: String, vararg args: Any) = LoggerFactory.getLogger(javaClass)?.error(message, args)

fun Any.logError(e: Throwable) = LoggerFactory.getLogger(javaClass)?.error("Unexpected exception", e)


