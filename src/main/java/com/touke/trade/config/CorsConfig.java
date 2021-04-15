package com.touke.trade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.google.common.collect.Lists;


/**
 * 跨域拦截器
 * @author allah
 *
 */
@Configuration
public class CorsConfig {


    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许向该服务器提交请求的URI，*表示全部允许，如果设成*，会自动转成当前请求头中的Origin
        corsConfiguration.addAllowedOrigin("*");

        //#允许访问的头信息
//        corsConfiguration.setAllowedHeaders(Lists.newArrayList("Origin", "token","X-Requested-With", "Content-Type",
//                "Accept", "Authorization","timestamp","deviceId","sign","CAPTCHA_COOKIE_NAME"));
       corsConfiguration.addAllowedHeader("*"); // 允许任何头
        corsConfiguration.setAllowedMethods(Lists.newArrayList("GET","PUT", "POST", "OPTIONS", "HEAD","PATCH"));
        //#允许Cookie跨域，在做登录校验的时候有用
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
