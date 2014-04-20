package org.jtalks.pochta.http

import java.util.HashMap
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import org.jtalks.pochta.http.render.RenderingEngine

/**
 *
 */
class ModelAndView(val view: String) {

    private val map = HashMap<String, Any>()

    fun put(key: String, value: Any): ModelAndView {
        map.put(key, value);
        return this
    }

    fun render(): String {
        return RenderingEngine.render(readTemplate(view), map)
    }

    fun renderWithCommonPageTemplate(title: String): String {
        map.put("title", title)
        map.put("body", render())
        return RenderingEngine.render(readTemplate("pageTemplate"), map)
    }

    private fun readTemplate(name: String): String {
        val file = "$name.jmte"
        val stream = javaClass.getResourceAsStream(file)
        val reader = BufferedReader(InputStreamReader(stream!!, StandardCharsets.UTF_8))
        val template = reader.readText()
        reader.close()
        return template
    }
}