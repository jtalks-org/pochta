package org.jtalks.pochta.http

import java.util.HashMap
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import com.floreysoft.jmte.Engine

/**
 *
 */
class ModelAndView(val view: String) {

    private val map = HashMap<String, Any>()

    fun put(key: String, value: Any) = map.put(key, value)

    fun render(): String {
        return RenderingEngine.render(readTemplate(view), map)
    }

    fun renderWithCommonPageTemplate(title: String) : String {
        val map = HashMap<String, Any>()
        map.put("title", title)
        map.put("body", render())
        return RenderingEngine.render(readTemplate("pageTemplate"), map)
    }

    private fun readTemplate(name : String) : String {
        val file = "$name.jmte"
        val stream = javaClass.getResourceAsStream(file)
        val reader = BufferedReader(InputStreamReader(stream!!, StandardCharsets.UTF_8))
        val template = reader.readText()
        reader.close()
        return template
    }

    object RenderingEngine {
        private val engine = Engine.createCompilingEngine()!!
        fun render(template: String, model: Map<String, Any>) = engine.transform(template, model)!!
    }
}