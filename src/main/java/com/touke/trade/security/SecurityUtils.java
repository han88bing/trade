package com.touke.trade.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import com.touke.trade.dto.User;

public class SecurityUtils {

	
	public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
	
	/**
	 * 获取
	 * @return
	 */
	public static Collection<? extends GrantedAuthority> getAllPermission(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities;
    }
	
	/**
	 * 判断是否拥有权限
	 * @param permission
	 * @return
	 */
	public static boolean hasPermission(String permission){
        if(StringUtils.isEmpty(permission)){
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasPermission = false;
        for(GrantedAuthority grantedAuthority : authorities){
            String authority = grantedAuthority.getAuthority();
            if(authority.equals(permission)){
                hasPermission =true;
            }
        }
        return hasPermission;
    }
	
	
	/**
	 * 获取用户信息
	 * @return
	 */
	public static User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }


	/**
	 * 退出
	 */
    public static void logout(){
        SecurityContextHolder.clearContext();
    }
	
	
}
