package org.jtalks.pochta.http.render

import com.floreysoft.jmte.Engine
import com.floreysoft.jmte.NamedRenderer
import com.floreysoft.jmte.RenderFormatInfo
import java.util.Locale
import java.net.URLEncoder
import org.apache.commons.lang3.StringEscapeUtils
import com.floreysoft.jmte.TemplateContext
import com.floreysoft.jmte.token.Token
import com.floreysoft.jmte.DefaultModelAdaptor
import org.jtalks.pochta.http.FingerprintCache

/**
 *  Fills HTML page templates with necessary dynamic data
 */
object RenderingEngine {

    private val engine = Engine.createCompilingEngine()
            .registerNamedRenderer(HtmlEscapeRenderer)
            .registerNamedRenderer(FingerprintedRenderer)
            .registerNamedRenderer(UrlEncodeRenderer);
    {
        engine.setModelAdaptor(FallbackModelAdaptor)
    }

    fun render(template: String, model: Map<String, Any>) = engine.transform(template, model)!!
}

/**
 * If some template variable value is missing from the model map substitute the key instead as a fallback.
 */
object FallbackModelAdaptor : DefaultModelAdaptor() {
    override fun getValue(context: TemplateContext, token: Token?, segments: List<String>, expression: String?) =
        super.getValue(context, token, segments, expression) ?: token.toString()

}

