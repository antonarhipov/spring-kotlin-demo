package org.example.demo

import org.example.AiGreeter
import org.example.Greeter
import org.example.ModeBanner
import org.example.FeatureInfoService
import org.springframework.beans.factory.BeanRegistrarDsl
import org.springframework.core.env.getProperty

class GreetingsRegistrar : BeanRegistrarDsl({
    registerBean<FeatureInfoService>()
    registerBean { Greeter(bean()) }

    profile("demo") {
        registerBean { ModeBanner("demo profile is active") }
    }

    val aiEnabled = env.getProperty<Boolean>("demo.ai.enabled", false)
    val apiKeyProperty = env.getProperty("spring.ai.openai.api-key")?.trim()
    val resolvedApiKey = apiKeyProperty?.let { env.resolvePlaceholders(it).trim() }
    if (aiEnabled && !resolvedApiKey.isNullOrBlank()) {
        registerBean { AiGreeter(bean(), bean()) }
    }
})
