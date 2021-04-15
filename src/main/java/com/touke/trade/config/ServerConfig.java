package com.touke.trade.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.touke.trade.aspect.AuthInterceptor;



@Configuration
public class ServerConfig implements WebMvcConfigurer {

	
	@Autowired
	AuthInterceptor authInterceptor; 
	



	
	
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //自定义配置...
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        converter.setFastJsonConfig(config);
        converters.add(0, converter);
    }
	
	

	@Bean
    public CharacterEncodingFilter filterCharacterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }
	   
	
	  
	public void addInterceptors(InterceptorRegistry registry) {
		
		 //参数的拦截器
		 registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**");
		 
		//数据拦截器 
//		 registry.addInterceptor(null)
//         .addPathPatterns("/api/**","/anon/**")
//         .excludePathPatterns("/gateway/**","/anon/test/**","/anon/h5Repayment/**");

	}
	


    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
	

}
