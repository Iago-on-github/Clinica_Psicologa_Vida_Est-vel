package br.com.psicoclinic.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomOpenAPI {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Psycho_Clinic")
                        .version("v1")
                        .description("For 5 years, Psycho_Clinic has been helping many patients of all ages.")
                        .license(new License().name("Apache 20")
                                .url("http://localhost:8080/teste")));

    }
}
