package org.example.demo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class SpringKotlinDemoApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `router DSL endpoint returns features`() {
        mockMvc.get("/api/features")
            .andExpect {
                status { isOk() }
                content { string(org.hamcrest.Matchers.containsString("Kotlin router DSL")) }
            }
    }

    @Test
    fun `dynamic bean registration default mode when demo profile is not active`() {
        mockMvc.get("/api/mode")
            .andExpect {
                status { isOk() }
                content { string(org.hamcrest.Matchers.containsString("default mode")) }
            }
    }
}

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("demo")
class SpringKotlinDemoDemoProfileTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `dynamic bean registration with demo profile`() {
        mockMvc.get("/api/mode")
            .andExpect {
                status { isOk() }
                content { string(org.hamcrest.Matchers.containsString("demo profile is active")) }
            }
    }
}
