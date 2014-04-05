package org.jtalks.pochta.config

public class SmtpConfig {

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
    public var port: Int = 25;
    public var authType: AuthType = AuthType.DISABLED;
    public var transportSecurity: TransportSecurity = TransportSecurity.PLAINTEXT;
    public var login: String = "";
    public var password: String = "";
}