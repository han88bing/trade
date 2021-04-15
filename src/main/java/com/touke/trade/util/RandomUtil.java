package com.touke.trade.util;

import java.security.SecureRandom;


/**
 * 工具类-随机数
 * 
 * @author xx
 * @version 2.0
 * @since 2014年1月28日
 */
public class RandomUtil {

	public static final String BASE_NUMBER = "0123456789";
	/**
	 * 获取从a至z，长度为length随机数
	 * 
	 * @return
	 */
	public static String getRandomStr(int length) {
		String base = "abcdefghijklmnopqrstuvwxyz";
		return getRandom(length, base);	
	}


	/**
	 * 获取随机长度随机字符
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		return getRandom(length, base);	
	}
	

 	
	/**
	 * 获取随机长度随机字符
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandom6Str(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		return getRandom(length, base);	
	}

	/**
	 * 获取随机长度随机数字
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumString(int length) { // length表示生成字符串的长度
		String base = "0123456789";
		return getRandom(length, base);
	}


	/**
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
 	public static int getRandom(int min, int max) {
 		 byte[] salt = new byte[128];
		 SecureRandom secureRandom = new SecureRandom();
		 secureRandom.setSeed(System.currentTimeMillis());  //使用系统时间作为种子
		 secureRandom.nextBytes(salt);
  		 return secureRandom.nextInt(max)%(max-min+1) + min;
  	}
	/**
	 * 基础方法
	 * @param length
	 * @param numberFormat
	 * @return
	 */
	private static String getRandom(int length,String numberFormat) {
		 byte[] salt = new byte[128];
		 SecureRandom secureRandom = new SecureRandom();
		 secureRandom.setSeed(System.currentTimeMillis());  //使用系统时间作为种子
		 secureRandom.nextBytes(salt);
		 StringBuffer sb = new StringBuffer();
		 for (int i = 0; i < length; i++) {
			int number = secureRandom.nextInt(numberFormat.length());
			sb.append(numberFormat.charAt(number));
		 }
		 return sb.toString(); 
	}


	


}
