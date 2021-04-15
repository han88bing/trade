package com.touke.trade.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义密码比较器 3
 * 在此 密码我就不加密了
 */
public class Md5PasswordEncoder  implements PasswordEncoder{

	@Override
	public String encode(CharSequence rawPassword) {
		 return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		 return encodedPassword.equals(rawPassword);
	}

}
