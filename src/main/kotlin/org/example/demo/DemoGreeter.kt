package org.example.demo

class DemoGreeter(private val featureInfoService: FeatureInfoService) {
    fun message(name: String) =
        "Hello $name. This app demonstrates: ${featureInfoService.features().joinToString()}"
}