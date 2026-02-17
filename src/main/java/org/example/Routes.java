package org.example;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.Map;

import static org.springframework.web.servlet.function.RequestPredicates.GET;

@Configuration
public class Routes {
    private final Greeter greeter;
    private final FeatureInfoService featureInfoService;
    private final ObjectProvider<ModeBanner> demoModeBanner;
    private final ObjectProvider<AiGreeter> demoAiGreeter;

    public Routes(Greeter greeter, FeatureInfoService featureInfoService,
                  ObjectProvider<ModeBanner> demoModeBanner,
                  ObjectProvider<AiGreeter> demoAiGreeter) {
        this.greeter = greeter;
        this.featureInfoService = featureInfoService;
        this.demoModeBanner = demoModeBanner;
        this.demoAiGreeter = demoAiGreeter;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(GET("/api/features"), request ->
                ServerResponse.ok().body(featureInfoService.features()))
            .andRoute(GET("/api/hello/{name}"), request -> {
                String name = request.pathVariable("name");
                return ServerResponse.ok().body(Map.of("message", greeter.message(name)));
            })
            .andRoute(GET("/api/mode"), request -> {
                ModeBanner bannerBean = demoModeBanner.getIfAvailable();
                String banner = bannerBean != null ? bannerBean.text() : "default mode";
                return ServerResponse.ok().body(Map.of("mode", banner));
            })
            .andRoute(GET("/api/ai/hello/{name}"), request -> {
                String name = request.pathVariable("name");
                AiGreeter aiGreeter = demoAiGreeter.getIfAvailable();
                if (aiGreeter == null) {
                    return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(Map.of("message", "AI not configured"));
                } else {
                    return ServerResponse.ok().body(Map.of("message", aiGreeter.message(name)));
                }
            });
    }
}
