package br.com.takeshi.spring_boot_rest.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("REST API's RESTful from 0 with Java, Spring boot, Kubernetes and Docker")
                        .version("V1")
                        .description("REST API's RESTful from 0 with Java, Spring boot, Kubernetes and Docker")
                        .termsOfService("https://github.com/AldenirTakeshi")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://github.com/AldenirTakeshi")));
    }
}
