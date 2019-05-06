package com.rmm.services.serverapp.config;

import com.fasterxml.classmate.TypeResolver;
import com.rmm.services.serverapp.controler.response.ErrorInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rmm.services.serverapp"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                .code(HttpStatus.NOT_FOUND.value())
                                .responseModel(new ModelRef("ErrorInfo"))
                                .build()))
                .globalResponseMessage(RequestMethod.POST,
                        newArrayList(
                                new ResponseMessageBuilder()
                                        .code(HttpStatus.NOT_FOUND.value())
                                        .responseModel(new ModelRef("ErrorInfo"))
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .responseModel(new ModelRef("ErrorInfo"))
                                        .build()
                        )
                )
                .enableUrlTemplating(true)
                .apiInfo(apiInfo())
                .additionalModels(typeResolver.resolve(ErrorInfo.class));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "RMM API",
                "Provides services that monitor and manage devices",
                "1.0",
                null,
                null,
                null,
                null,
                new ArrayList<>()
        );
    }
}
