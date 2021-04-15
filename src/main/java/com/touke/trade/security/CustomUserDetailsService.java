package com.touke.trade.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.touke.trade.mapper.TkUserMapper;
import com.touke.trade.pojo.TkUser;

/**
 * 认证和授权
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private TkUserMapper tkUserMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		//1 用户认证
		TkUser tkUser =  tkUserMapper.selectByUsername(username);
		if (tkUser == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
		
		//权限授权
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		
		return null;
	}

}
