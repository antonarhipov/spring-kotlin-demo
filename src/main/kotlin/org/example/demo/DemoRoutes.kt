package org.example.demo

import org.springframework.beans.factory.ObjectProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router

@Configuration
class DemoRoutes(
    private val demoGreeter: DemoGreeter,
    private val featureInfoService: FeatureInfoService,
    private val demoModeBanner: ObjectProvider<DemoModeBanner>,
    private val demoAiGreeter: ObjectProvider<DemoAiGreeter>
) {

    @Bean
    fun routes() = router {
        GET("/api/features") {
            ServerResponse.ok().body(featureInfoService.features())
        }
        GET("/api/hello/{name}") { request: ServerRequest ->
            val name = request.pathVariable("name")
            ServerResponse.ok().body(mapOf("message" to demoGreeter.message(name)))
        }
        GET("/api/mode") {
            val banner = demoModeBanner.ifAvailable?.text ?: "default mode"
            ServerResponse.ok().body(mapOf("mode" to banner))
        }
        GET("/api/ai/hello/{name}") { request: ServerRequest ->
            val name = request.pathVariable("name")
            val aiGreeter = demoAiGreeter.ifAvailable
            if (aiGreeter == null) {
                ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(mapOf("message" to "AI not configured"))
            } else {
                ServerResponse.ok().body(mapOf("message" to aiGreeter.message(name)))
            }
        }
    }
}
