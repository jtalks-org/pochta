package org.jtalks.pochta.config

import java.util.Properties

/**
 *
 */
public class HttpConfig(props : Properties){
  public val port: Int = Integer.parseInt(props.getProperty("jtalks.pochta.http.port")!!);;
  public val secretToken: String = props.getProperty("jtalks.pochta.http.token")!!
}