//package com.abdoatiia542.GraduationProject.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig implements WebMvcConfigurer {
//
//    @Bean
//    public Docket productApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .securityContexts(Arrays.asList(securityContext()))
//                .securitySchemes(Arrays.asList(apiKey()))
//                .select().apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .consumes(getAllConsumeContentTypes())
//                .produces(getAllProduceContentTypes())
//                ;
//
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("JWT", "Authorization", "header");
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//    }
//
//    private Set<String> getAllConsumeContentTypes() {
//        Set<String> consumes = new HashSet<>();
//        // Add other media types if required in future
//        consumes.add(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        return consumes;
//    }
//
//    private Set<String> getAllProduceContentTypes() {
//        Set<String> produces = new HashSet<>();
//        // Add other media types if required in future
//        produces.add(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        return produces;
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title("SVIS API")
//                .description("SVIS API reference for FrontEnd developers")
//                .license("SVIS License").version("1.0").build();
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//}
