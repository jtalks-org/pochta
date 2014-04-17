package org.jtalks.pochta.http.render

import com.floreysoft.jmte.NamedRenderer
import com.floreysoft.jmte.RenderFormatInfo
import java.util.Locale

/**
 *
 */
object UrlEncodeRenderer : NamedRenderer{

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
