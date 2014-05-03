package org.jtalks.pochta.http.render

import org.apache.commons.lang3.StringEscapeUtils
import java.net.URLEncoder
import org.jtalks.pochta.http.FingerprintCache
import com.floreysoft.jmte.NamedRenderer
import com.floreysoft.jmte.RenderFormatInfo
import java.util.Locale

/**
 * User-supplied html templates variable need escaping to prevent
 * injection attacks and page markup inconsistencies.
 *
 * Usage: ${order.total;htmlEscape}
 */
object HtmlEscapeRenderer : AbstractBaseRenderer("htmlEscape") {

    override fun render(data: String): String = StringEscapeUtils.escapeHtml4(data)!!
}

/**
 * Performs url encoding for dynamic parts of URLs in page templates
 *
 * Usage: ${order.total;urlEncode}
 */
object UrlEncodeRenderer : AbstractBaseRenderer("urlEncode") {

    override fun render(data: String) = URLEncoder.encode(data.toString())!!
}

/**
 * Adds fingerprint mark at the end of the string. This renderer is used for static web resources
 * to control browser caching. Once file has been changed fingerprint will be updated to force
 * browser download a new version instead of a cached one.
 *
 * Usage: <script src="${/js/jquery.min.js;fingerprint}"></script>
 */
object FingerprintedRenderer : AbstractBaseRenderer("fingerprint") {

    override fun render(data: String) = "$data${FingerprintCache.get(data)}"
}

abstract class AbstractBaseRenderer(val keyword: String) : NamedRenderer {

    override fun getFormatInfo(): RenderFormatInfo = object: RenderFormatInfo {
    }

    override fun getSupportedClasses(): Array<Class<out Any?>> = array(javaClass<String>())

    override fun getName() = keyword

    override fun render(data: Any?, format: String?, locale: Locale?) = render(data.toString())

    abstract fun render(data: String): String
}