package com.touke.trade.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

	public static Long getOrder(String prefix,Integer length) {
		StringBuffer buffer =new StringBuffer();
		buffer.append(prefix);
		buffer.append(DateUtil.formatDateToString(new Date(),DateUtil.DATE_FORMAT_YMDHMS));
		buffer.append(RandomUtil.getRandomNumString(length));
		BigDecimal number = new BigDecimal(buffer.toString());
		return number.longValue();
	}

	public static String getNumber(String prefix,Integer length) {
		StringBuffer buffer =new StringBuffer();
		buffer.append(prefix);
		buffer.append(DateUtil.formatDateToString(new Date(),DateUtil.DATE_FORMAT_YMDHMS));
		buffer.append(RandomUtil.getRandomNumString(length));
		return buffer.toString();
	}
	
	/**
  	 * 获取特定范围内的随机数
  	 * @param min
  	 * @param max
  	 * @return
  	 */
  	public static String englishDateFormat(Date date) {
  	  SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd,yyyy", Locale.ENGLISH);
  	  return sdf.format(date);
  	}
  	
	
	
  
	/**
	 * 订单号成功规则
	 * @param prefix
	 * @param memberId
	 * @param repayMentId
	 * @param length
	 * @return
	 */
	public static String getOrderNo(String prefix,String date,int number) {
		StringBuffer buffer =new StringBuffer();
		buffer.append(prefix);
		buffer.append(date);
		int num = number- buffer.length() ;
		buffer.append(RandomUtil.getRandomNumString(num));
		
		return buffer.toString();
	}
	
	
	//创建文件Name
    public static String getFileName(String prefix,Integer length){
    	StringBuffer buffer =new StringBuffer();
		buffer.append(prefix);
		buffer.append(System.currentTimeMillis());
		buffer.append(RandomUtil.getRandomNumString(length));
		return buffer.toString();
    }



	/**
	 * 出生日期转换   dd/MM/yyyy 修改 yyyy-MM-dd
	 * @param birthday
	 * @return
	 */
	public static String birthdayFormat(String birthday) {
		String[] str = birthday.split("/");
		if(str.length == 1) {
			return str[0] + "-01-01";
		}
		if(str.length != 3) {
			return "";
		}
		return str[2] +"-" + str[1] +"-" + str[0]; 
	}
	
	/**
	 * 出生日期转换   dd-MM-yyyy 修改 yyyy-MM-dd
	 * @param birthday
	 * @return
	 */
	public static String birthdayFormat2(String birthday) {
		String[] str = birthday.split("-");
		if(str.length != 3) {
			return "";
		}
		return str[2] +"-" + str[1] +"-" + str[0]; 
	}

	/**
	 * 判断是否为日期类型
	 * @param birthday
	 * @return
	 */
	public static boolean isDate(String birthday) {
		if(StringUtils.isBlank(birthday)) {
			return false;
		}
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
		  df.parse(birthday);
		  System.out.println(df.parse(birthday));
		  return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	/***
     * 判断字符串是否是yyyyMMdd格式
     * @param mes 字符串
     * @return boolean 是否是日期格式
     */
    public static boolean isRqFormat(String mes){
    	String temp = StringUtils.remove(mes, "/");
        if(temp.length() != 8) {
        	return false;
        }
        //1 日  2 月  3 年  19121991
        String format = "(0[1-9]|[12][0-9]|3[01])(0[1-9]|1[012])([0-9]{4})";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(temp);
        if (matcher.matches()) {
            matcher = pattern.matcher(temp);
            if (matcher.matches()) {
                int d = Integer.valueOf(matcher.group(1));
                int m = Integer.valueOf(matcher.group(2));
                int y = Integer.valueOf(matcher.group(3));
                if (d > 28) {
                    Calendar c = Calendar.getInstance();
                    c.set(y, m-1, 1);
                    //每个月的最大天数
                    int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                    return (lastDay >= d);
                }
                if(y > 2200 || y < 1950) {
                	return false;
                }
                if(m > 12) {
                	return false;
                }
            }else {
            	 return false;
            }
            return true;
        }
        return false;
    
    }
	

}
