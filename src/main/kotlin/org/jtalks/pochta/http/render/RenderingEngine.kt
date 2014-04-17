package org.jtalks.pochta.http.render

import com.floreysoft.jmte.Engine

/**
 *
 */
object RenderingEngine {

    private val engine = Engine.createCompilingEngine()!!

    fun render(template: String, model: Map<String, Any>) = engine.transform(template, model)!!
}