package org.jtalks.pochta.config

import java.util.Properties
import org.jtalks.pochta.config.SmtpConfig.AuthType.ENFORCED

public class SmtpConfig(props: Properties) {

    public enum class AuthType {
        DISABLED
        SUPPORTED
        ENFORCED
    }

    public enum class TransportSecurity {
        PLAINTEXT
        STARTTLS_SUPPORTED
        STARTTLS_ENFORCED
        SSL
    }

    public val connectionTimeout : Int = 60000 // 1 minute
    public var port: Int = Integer.parseInt(props.getProperty("jtalks.pochta.smtp.port")!!);
    public var authType: AuthType = AuthType.ENFORCED;
    public var transportSecurity: TransportSecurity = TransportSecurity.PLAINTEXT;
    public var login: String = props.getProperty("jtalks.pochta.smtp.login")!!;
    public var password: String = props.getProperty("jtalks.pochta.smtp.password")!!;
}