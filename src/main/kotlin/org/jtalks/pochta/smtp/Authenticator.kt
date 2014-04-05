package org.jtalks.pochta.smtp

import org.subethamail.smtp.auth.LoginFailedException
import org.subethamail.smtp.auth.UsernamePasswordValidator

/**
 * Performs authentication with a simple login-password pair
 */
public class Authenticator(val username: String, val password : String) : UsernamePasswordValidator{

    public override fun login(username: String?, password: String?) {
        if (!this.username.equals(username) || !this.password.equals(password)) {
            throw LoginFailedException()
        }
    }
}