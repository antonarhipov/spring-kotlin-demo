package org.example.demo

import org.springframework.ai.chat.model.ChatModel

class DemoAiGreeter(
    private val chatModelProvider: ChatModel,
    private val featureInfoService: FeatureInfoService
) {
    fun message(name: String): String {
        val prompt = """
            You are a helpful assistant for a Spring demo.
            Features: ${featureInfoService.features().joinToString()}.
            Greet $name in one short sentence and tell a dad joke.
        """.trimIndent()
        return chatModelProvider.call(prompt) ?: "Hello $name."
    }
}
