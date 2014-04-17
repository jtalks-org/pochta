package org.jtalks.pochta.http.render

import com.floreysoft.jmte.NamedRenderer
import java.util.Locale
import com.floreysoft.jmte.RenderFormatInfo

/**
 *
 */
object HtmlEscapeRenderer : NamedRenderer{

    override fun render(o: Any?, format: String?, locale: Locale?): String? {
        throw UnsupportedOperationException()
    }
    override fun getName(): String? {
        throw UnsupportedOperationException()
    }
    override fun getFormatInfo(): RenderFormatInfo? {
        throw UnsupportedOperationException()
    }
    override fun getSupportedClasses(): Array<Class<out Any?>>? {
        throw UnsupportedOperationException()
    }
}