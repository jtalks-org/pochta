package org.jtalks.pochta.smtp

import org.subethamail.smtp.auth.EasyAuthenticationHandlerFactory
import org.subethamail.smtp.auth.UsernamePasswordValidator
import org.jtalks.pochta.config.Config
import org.subethamail.smtp.auth.LoginFailedException
import org.jtalks.pochta.util.Context

/**
 *
 */
class AuthenticatorFactory(config: Config.Mailboxes) : EasyAuthenticationHandlerFactory(
        object: UsernamePasswordValidator {
            override fun login(username: String?, password: String?) {
                Context.remove(Context.PASSWORD)
                config.filter {(mbox) -> mbox.login.equals(username) && mbox.password.equals(password) }
                        .forEach {(mbox) -> Context.put(Context.PASSWORD, mbox.password); }
                if (!Context.contains(Context.PASSWORD)) throw LoginFailedException()
            }
        }
)