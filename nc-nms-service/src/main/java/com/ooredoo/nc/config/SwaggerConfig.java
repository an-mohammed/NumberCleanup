package com.ooredoo.nc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Value("${service.swagger.documentation.publish}")
	private String publishDoc;
	
	@Bean
    public Docket productApi() {
		
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ooredoo.dd.directdebitservice.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData())
                //.ignoredParameterTypes(StackTraceElement.class, Throwable.class)
                .enable(Boolean.valueOf(publishDoc));
    }
	
	private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfoBuilder()
        		.title("Direct-Debit Service")
        		.description("This document describes the REST service specification exposed by Direct-Debit Application")
        		.version("1.0.0")
        		.contact(new Contact("Rangan Banerjee Krishna", null, "R.Krishna@diyarme.com"))
        		.build();
        return apiInfo;
    }
}
