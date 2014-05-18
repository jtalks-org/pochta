package org.jtalks.pochta

import org.jtalks.pochta.util.logInfo
import org.jtalks.pochta.util.logError
import org.springframework.context.annotation.AnnotationConfigApplicationContext


/**
 * Application launcher. Command line params are ignored.
 */
object Application

fun main(args: Array<String>) = try {
    Application.logInfo("Starting Pochta service...")
    AnnotationConfigApplicationContext("org.jtalks.pochta")
    Application.logInfo("Ready to rumble!")
} catch(e: Exception) {
    Application.logError(e)
}


