package org.example;

public class Greeter {
    private final FeatureInfoService featureInfoService;

    public Greeter(FeatureInfoService featureInfoService) {
        this.featureInfoService = featureInfoService;
    }

    public String message(String name) {
        String features = String.join(", ", featureInfoService.features());
        return "Hello " + name + ". This app demonstrates: " + features;
    }
}
