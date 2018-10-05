package com.koskou.demo.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

//Configuration
//Enable Swagger
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final Contact DEFAULT_CONTACT = new Contact(
            "Banner Gonzales","http://www.google.com","bannergz1999@gmail.com"
    );

    private static final Collection<VendorExtension> DEFAULT_VENDOR = new ArrayList<>();
    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "Hey wasup","Banner Gocumentation", "1.0",
            "urn:tos", DEFAULT_CONTACT,
            "Apache 2.0", "http://www.google.com.pe", DEFAULT_VENDOR);
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
            new HashSet<String>(Arrays.asList("application/json", "application/xml"));

    //Bean - Docket
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(DEFAULT_API_INFO)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES);
    }
    //Swagger 2
    //All the Paths
    //All the Apis
}
