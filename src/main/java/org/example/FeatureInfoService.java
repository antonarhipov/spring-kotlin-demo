package org.example;

import java.util.List;

public class FeatureInfoService {
    public List<String> features() {
        return List.of(
            "Kotlin router DSL (WebMvc.fn)",
            "Spring Framework 7 BeanRegistrarDsl",
            "Spring AI 2.x ChatModel (optional)"
        );
    }
}
