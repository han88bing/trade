package com.touke.trade.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据校验工具类
 */
public class ValidateUtil {


	
	/**
	 * 校验用户名 规则：数字与字母组合，字母，汉字，4-16位(?![a-zA-Z]+$)
	 * 
	 * @param userName 用户名
	 * @return 校验结果 true：通过 false：不通过
	 */
	public static boolean isUserName(String userName) {
	  
		Pattern p = Pattern.compile("^(?![0-9]+$)[0-9A-Za-z_\u0391-\uFFE5]{3,20}$");
		Matcher m = p.matcher(userName);
		return m.matches();
	}
	
	/**
	 * 密码格式校验
	 * @param pwd
	 * @return
	 */
	public static String isPwd(String pwd) {
		String result = "";
	    if (pwd.length() < 8 || pwd.length() >16) {
	    	result = "密码长度必须是8-16位！";
	    	return result;
	    }
		boolean b = Pattern.compile("(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,16}").matcher(pwd).find();
		if(b){
			result = "";
		}else{
			result = "密码由大小字母、数字组成";
		}
		return result;
	}
	


	/**
	 * 校验Email格式  并且常用邮箱格式校验
	 * 
	 * @param email 输入邮箱
	 * @return 校验结果 true：通过 false：不通过
	 */
	public static boolean isEmail(String email) {
		Pattern p1 = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		
		Matcher m1 = p1.matcher(email);
		if (!m1.matches()) {
			return false;
		} 
		
		String suffix = email.substring(email.indexOf("@")+1).trim();
		if(suffix.startsWith("g") || suffix.startsWith("G")) {
			if(("GMAIL.COM").equals(suffix.toUpperCase())) {
				return true ;
			}else {
				return false;
			}
		}
		return false;
	}

	


	
	/**
	 * 字符全是字母
	 * @param str
	 * @return
	 */
   public static boolean isPhonticName(String str) {
        char[] chars=str.toCharArray();
        boolean isPhontic = false;
        for(int i = 0; i < chars.length; i++) {
            isPhontic = (chars[i] >= 'a' && chars[i] <= 'z') || (chars[i] >= 'A' && chars[i] <= 'Z');
            if (!isPhontic) {
                return false;
            }
        }
        return true;
    }



	/**
	 * 判断是否是整数
	 * @param obj
	 * @return
	 */
	public static boolean isInteger(Object obj) {
		if(obj == null){
			return false;
		}
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");    
	    return pattern.matcher(obj.toString()).matches();  
	}

}
