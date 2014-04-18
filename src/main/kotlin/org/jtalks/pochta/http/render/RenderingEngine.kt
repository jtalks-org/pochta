package org.jtalks.pochta.http.render

import com.floreysoft.jmte.Engine
import com.floreysoft.jmte.NamedRenderer
import com.floreysoft.jmte.RenderFormatInfo
import java.util.Locale
import java.net.URLEncoder
import org.apache.commons.lang3.StringEscapeUtils

/**
 *  Fills HTML page templates with necessary dynamic data
 */
object RenderingEngine {

    private val engine = Engine.createCompilingEngine()
    ?.registerNamedRenderer(HtmlEscapeRenderer)
    ?.registerNamedRenderer(UrlEncodeRenderer)!!

    fun render(template: String, model: Map<String, Any>) = engine.transform(template, model)!!
}

/**
 * User-supplied html templates variable need escaping to prevent
 * injection attacks and page markup inconsistencies.
 *
 * Usage: ${order.total;htmlEscape}
 */
object HtmlEscapeRenderer : StringRenderer {

    override fun render(data: Any?, format: String?, locale: Locale?) = StringEscapeUtils.escapeHtml4(data.toString())

    override fun getName() = "htmlEscape"
}

/**
 * Performs url encoding for dynamic parts of URLs in page templates
 *
 * Usage: ${order.total;urlEncode}
 */
object UrlEncodeRenderer : StringRenderer {

    override fun render(data: Any?, format: String?, locale: Locale?) = URLEncoder.encode(data.toString())

    override fun getName() = "urlEncode"
}

trait StringRenderer : NamedRenderer {

    override fun getFormatInfo(): RenderFormatInfo = object: RenderFormatInfo {
    }

    override fun getSupportedClasses(): Array<Class<out Any?>> = array(javaClass<String>());
}