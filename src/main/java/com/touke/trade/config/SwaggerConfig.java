package com.touke.trade.config;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * Springfox Swagger configuration.
 *
 * Warning! When having a lot of REST endpoints, Springfox can become a performance issue. In that
 * case, you can use a specific Spring profile for this class, so that only front-end developers
 * have access to the Swagger view.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


	@Value("${swagger.enable}")
	private boolean swaggerEnable;
	
	
	
	
    @Bean  
    public Docket createRestApi() {// 鍒涘缓API鍩烘湰淇℃伅  
		//娣诲姞head鍙傛暟start  
        
        List<Parameter> pars = new ArrayList<Parameter>();  
        ParameterBuilder tokenPar1 = new ParameterBuilder();  
        
        tokenPar1.name("token").description("token").modelRef(new ModelRef("string"))
	        .parameterType("header").required(true).defaultValue("4102920580658966").build();  
                    
        pars.add(tokenPar1.build()); 
      
		return new Docket(DocumentationType.SWAGGER_2).enable(swaggerEnable)
				.globalOperationParameters(pars) 
                .apiInfo(apiInfo())
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(java.sql.Date.class)
                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())  
                .build()
                .forCodeGeneration(false);
    }  
    
  
    private ApiInfo apiInfo() {
    	return new ApiInfoBuilder()  
                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")// API 标题  
                .description("前台接口列表")// API描述  
                .version("1.0")// 版本号  
                .build();  
    }
    
}
