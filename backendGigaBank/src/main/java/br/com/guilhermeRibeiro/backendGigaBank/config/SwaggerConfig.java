package br.com.guilhermeRibeiro.backendGigaBank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        docket
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.guilhermeRibeiro.backendGigaBank.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo())
                .consumes(new HashSet<>(Arrays.asList("application/json")))
                .produces(new HashSet<String>(Arrays.asList("application/json")));

        return docket;
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("API GigaBank")
                .description("API para sistemma simples de banco")
                .version("1.0.0")
                .build();
    }
}
