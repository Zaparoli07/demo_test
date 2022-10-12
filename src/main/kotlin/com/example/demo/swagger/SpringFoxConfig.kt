package com.example.demo.swagger;

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors.any
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiInfo.DEFAULT_CONTACT
import springfox.documentation.service.VendorExtension
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SpringFoxConfig {

    @Value("\${application.name}")
    val appName: String? = null

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.OAS_30)
            .apiInfo(getApiInfo())
            .select()
            .paths(any())
            .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
            .build()
    }

    private fun getApiInfo(): ApiInfo {
        return ApiInfo(
            appName,
            "Demo Application",
            "1.0",
            "urn:tos",
            DEFAULT_CONTACT,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            ArrayList<VendorExtension<*>>()
        )
    }
}
