package com.sudreeshya.sms.config;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author Manjit Shakya
 * @email manjit.shakya@f1soft.com
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    Predicate<RequestHandler> predicate = (RequestHandler input) -> {
        Class<?> declaringClass = input.declaringClass();
        if (declaringClass.getPackage().getName().startsWith("com.sudreeshya")) {
            if (declaringClass == BasicErrorController.class) {
                return false;
            }
            if (declaringClass.isAnnotationPresent(RestController.class)) {
                return true;
            }
            if (input.isAnnotatedWith(ResponseBody.class)) {
                return true;
            }
        }
        return false;
    };

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(predicate)
                .build()
                .apiInfo(apiInfo());
//                .securityContexts(Lists.newArrayList(securityContext()))
//                .securitySchemes(Lists.newArrayList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Bankxp").description("Web portal for mobile banking")
                .termsOfServiceUrl("https://f1soft.com").contact(new Contact("f1soft", "https://wwww.f1soft.com", "banksmart@f1soft.com"))
                .license("JavaInUse License")
                .licenseUrl("bankxp@f1soft.com").version("1.0").build();
    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("Authorization", "Authorization", "header");
//    }

//    @Bean
//    SecurityConfiguration security() {
//        return new SecurityConfiguration(
//                null,
//                null,
//                null, // realm Needed for authenticate button to work
//                null, // appName Needed for authenticate button to work
//                "",// apiKeyValue
//                ApiKeyVehicle.HEADER,
//                "Authorization", //apiKeyName
//                "");
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .forPaths(PathSelectors.regex("/anyPath.*"))
//                .build();
//    }
//
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope
//                = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Lists.newArrayList(
//                new SecurityReference("Authorization", authorizationScopes));
//    }

}

