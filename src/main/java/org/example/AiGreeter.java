package org.example;

import org.springframework.ai.chat.model.ChatModel;

public class AiGreeter {
    private final ChatModel chatModelProvider;
    private final FeatureInfoService featureInfoService;

    public AiGreeter(ChatModel chatModelProvider, FeatureInfoService featureInfoService) {
        this.chatModelProvider = chatModelProvider;
        this.featureInfoService = featureInfoService;
    }

    public String message(String name) {
        String features = String.join(", ", featureInfoService.features());
        String prompt = "You are a helpful assistant for a Spring demo.\n" +
                        "Features: " + features + ".\n" +
                        "Greet " + name + " in one short sentence and tell a dad joke.";
        String response = chatModelProvider.call(prompt);
        return response != null ? response : "Hello " + name + ".";
    }
}
