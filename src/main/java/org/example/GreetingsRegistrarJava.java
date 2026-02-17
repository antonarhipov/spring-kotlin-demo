package org.example;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.core.env.Environment;

public class GreetingsRegistrarJava implements BeanRegistrar {

    @Override
    public void register(BeanRegistry registry, Environment env) {
        registry.registerBean(FeatureInfoService.class);
        
        registry.registerBean(Greeter.class, spec -> spec.supplier(context -> 
            new Greeter(context.bean(FeatureInfoService.class))
        ));

        if (env.matchesProfiles("demo")) {
            registry.registerBean(ModeBanner.class, spec -> spec.supplier(_ ->
                new ModeBanner("demo profile is active")
            ));
        }

        boolean aiEnabled = env.getProperty("demo.ai.enabled", Boolean.class, false);
        String apiKeyProperty = env.getProperty("spring.ai.openai.api-key");
        
        String resolvedApiKey = null;
        if (apiKeyProperty != null) {
            resolvedApiKey = env.resolvePlaceholders(apiKeyProperty.trim()).trim();
        }

        if (aiEnabled && resolvedApiKey != null && !resolvedApiKey.isEmpty()) {
            registry.registerBean(AiGreeter.class, spec -> spec.supplier(context -> 
                new AiGreeter(
                    context.bean(ChatModel.class),
                    context.bean(FeatureInfoService.class)
                )
            ));
        }
    }
}
