package org.jtalks.pochta.config

import java.util.Properties

/**
 *
 */
  public class Config(props : Properties){
      val smtp: SmtpConfig = SmtpConfig(props)
      val http: HttpConfig = HttpConfig(props)
  }