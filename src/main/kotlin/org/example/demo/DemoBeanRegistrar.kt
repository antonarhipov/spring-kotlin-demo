package org.example.demo

import org.springframework.beans.factory.BeanRegistrarDsl

class DemoBeanRegistrar : BeanRegistrarDsl({
    registerBean<FeatureInfoService>()
    registerBean { DemoGreeter(bean()) }

    profile("demo") {
        registerBean { DemoModeBanner("demo profile is active") }
    }

    val aiEnabled = env.getProperty("demo.ai.enabled", Boolean::class.java, false)
    val apiKeyProperty = env.getProperty("spring.ai.openai.api-key")?.trim()
    val resolvedApiKey = apiKeyProperty?.let { env.resolvePlaceholders(it).trim() }
    if (aiEnabled && !resolvedApiKey.isNullOrBlank()) {
        registerBean { DemoAiGreeter(bean(), bean()) }
    }
})
