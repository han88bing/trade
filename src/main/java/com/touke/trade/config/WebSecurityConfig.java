package com.touke.trade.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.touke.trade.security.CustomUserDetailsService;
import com.touke.trade.security.Md5PasswordEncoder;

/**
 * Spring Security Config
 * @author zhangzechi
 * @date 2020/4/2
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //全局
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService hrService;
    
    /**
     *  配置userDetails的数据源，密码加密格式
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(hrService)
         .passwordEncoder(new Md5PasswordEncoder());
       // .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        http.cors().and().csrf().disable()
    		.authorizeRequests()
    		// 跨域预检请求
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // 首页和登录页面
            .antMatchers("/login","/login/sendOtp").permitAll()
            .antMatchers("/actuator/**").permitAll()
            // 其他所有请求需要身份认证
            .anyRequest().authenticated();
        
        http.logout().logoutSuccessUrl("/login");
       
//        .antMatchers("/decision/**","/govern/**","/employee/*").hasAnyRole("EMPLOYEE","ADMIN")//对decision和govern 下的接口 需要 USER 或者 ADMIN 权限
//        .antMatchers("/employee/login").permitAll()///employee/login 不限定
//        .antMatchers("/admin/**").hasRole("ADMIN")//对admin下的接口 需要ADMIN权限
//        .antMatchers("/oauth/**").permitAll()//不拦截 oauth 开放的资源
//        .anyRequest().permitAll()//其他没有限定的请求，允许访问
//        .and().anonymous()//对于没有配置权限的其他请求允许匿名访问
//        .and().formLogin()//使用 spring security 默认登录页面
//        .and().httpBasic();//启用http 基础验证
        
    }

    
    /**
     * 放行的静态资源
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**")
                // 给 swagger 放行；不需要权限能访问的资源
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/images/**", "/webjars/**", "/v2/api-docs", "/configuration/ui", "/configuration/security");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
    	return super.authenticationManager();
    }
    
    /**
     * 设置用户密码的加密方式
     * @return
     */
    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }
    
  
    
}