package com.touke.trade.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.touke.trade.dto.User;

/**
 * 登录
 * @author allah
 *
 */
@Controller
public class LoginController {

	@Autowired
    private AuthenticationManager myAuthenticationManager;

	
	
	@RequestMapping(value = "/userLogin")
    public String userLogin(HttpServletRequest request) {

        User userInfo = new User();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        userInfo.setUsername(username);
        userInfo.setPassword(password);

        //        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
        		new UsernamePasswordAuthenticationToken(username, password);


        try{
            //使用SpringSecurity拦截登陆请求 进行认证和授权
            Authentication authenticate = myAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authenticate);
            //使用redis session共享
           
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:login-error?error=2";
        }


        return "redirect:index";
    }
	
}
