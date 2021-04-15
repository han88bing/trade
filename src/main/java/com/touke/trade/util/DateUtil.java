package com.touke.trade.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;

/**
 * 工具类-日期处理
 * 
 */
public class DateUtil extends DateUtils{
	
	 /** 
	   * 常用变量 
	   */
	  public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
	  public static final String DATE_FORMAT_FULL_MS = "yyyy-MM-dd HH:mm:ss:SSS"; 
	  public static final String DATE_FORMAT_YMD = "yyyy-MM-dd"; 
	  public static final String DATE_FORMAT_HMS = "HH:mm:ss"; 
	  public static final String DATE_FORMAT_HM = "HH:mm"; 
	  public static final String DATE_FORMAT_YMDHM = "yyyy-MM-dd HH:mm"; 
	  public static final String DATE_FORMAT_YMDHMS = "yyyyMMddHHmmss";
	  public static final String DATE_FORMAT_YMDHM2 = "yyyyMMddHHmm";
	  public static final String DATE_FORMAT_YMDHM3 = "yyyyMMddHH";
	  public static final String DATE_FORMAT_YM = "yyyy-MM"; 
	  public static final String DATE_FORMAT_YMDHMS_INDIA = "yyyyMMddHHmmss";
	  public static final String DATE_FORMAT_YMD_INDIA = "dd-MM-yyyy"; 
	  public static final String DATE_FORMAT_YMDHMS_INDIA2 = "dd-MM-yyyy HH:mm:ss"; 
	  public static final long ONE_DAY_MILLS = 3600000 * 24L; 


	    
	  
	  /**
		 * 获得当前日期
		 * @return
		 */
		public static Date getNow() {
			Calendar cal = Calendar.getInstance();
			Date currDate = cal.getTime();
			return currDate;
		}
		
		/**
		 * 时间戳转换成字符串
		 * @return
		 */
		public static String formatStampToString(String stamp,String format) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			long timeLong = Long.valueOf(stamp);
			if(stamp.length() < 11) {
				timeLong = timeLong * 1000L;
			}
			return sdf.format(timeLong);
		}
		
		
		
	    
	  /** 
	   * 日期转换为制定格式字符串 
	   * 
	   * @param time 
	   * @param format 
	   * @return 
	   */
	  public static String formatDateToString(Date time, String format) { 
	    SimpleDateFormat sdf = new SimpleDateFormat(format); 
	    return sdf.format(time); 
	  } 
	  
	  /** 
	   * 字符串转换为制定格式日期 
	   * (注意：当你输入的日期是2014-12-21 12:12，format对应的应为yyyy-MM-dd HH:mm 
	   * 否则异常抛出) 
	   * @param date 
	   * @param format 
	   * @return 
	   * @throws ParseException 
	   *       @ 
	   */
	  public static Date formatStringToDate(String date, String format) { 
	    SimpleDateFormat sdf = new SimpleDateFormat(format); 
	    try { 
	      return sdf.parse(date); 
	    } catch (ParseException ex) { 
	      ex.printStackTrace(); 
	      return null ;
	    } 
	  } 
	  
	  
	  public static Date  formatDateToDate(Date olddate ,String pattern){
			
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date resultDate = null;
			try {
				resultDate =sdf.parse(formatDateToString(olddate, pattern));
			}catch (Exception e) {
				throw new RuntimeException("转换日期异常");
			}
			return resultDate;
		}
	  
	  
	  /**
		 * 日期转换成字符串
		 * @param date
		 * @return
		 */
		public static String dateToString(Date date,String formatPattern){
			SimpleDateFormat format = new SimpleDateFormat(formatPattern);
			return format.format(date);
		}
	  
	
	  /** 
	   * 判断一个日期是否属于两个时段内 
	   * @param time 
	   * @param timeRange 
	   * @return 
	   */
	  public static boolean isTimeInRange(Date time, Date[] timeRange) { 
	    return (!time.before(timeRange[0]) && !time.after(timeRange[1])); 
	  } 
	  
	
	  
	  /** 
	   * 计算个两日期的天数 
	   * 
	   * @param startDate 
	   *      开始日期字串 
	   * @param endDate 
	   *      结束日期字串 
	   * @return 
	   * @throws ParseException 
	   */
	  public static int getDaysBetween(String startDate, String endDate) { 
	    int dayGap = 0; 
	    if (startDate != null && startDate.length() > 0 && endDate != null
	        && endDate.length() > 0) { 
	      Date end = formatStringToDate(endDate, DATE_FORMAT_YMD); 
	      if(null == end) {
	    	  throw new RuntimeException("endtime dateutil exception");
	      }
	      Date start = formatStringToDate(startDate, DATE_FORMAT_YMD); 
	      if(null == start) {
	    	  throw new RuntimeException("starttime dateutil exception");
	      }
	      dayGap = getDaysBetween(start, end); 
	    } 
	    return dayGap; 
	  } 
	  
	  private static int getDaysBetween(Date startDate, Date endDate) { 
	    return (int) ((endDate.getTime() - startDate.getTime()) / ONE_DAY_MILLS); 
	  } 
	  
	  /** 
	   * 计算两个日期之间的天数差 
	   * @param startDate 
	   * @param endDate 
	   * @return 
	   */
	  public static int getDaysGapOfDates(Date startDate, Date endDate) { 
	    int date = 0; 
	    if (startDate != null && endDate != null) { 
	      date = getDaysBetween(startDate, endDate); 
	    } 
	    return date; 
	  } 
	  
	
		public static int secondsBetween(Date startTime,Date endTime){
			
			long diff=endTime.getTime()-startTime.getTime();
			
			long diffSeconds = diff / 1000 % 60;
	        long diffMinutes = diff / (60 * 1000) % 60;
	        long diffHours = diff / (60 * 60 * 1000) % 24;
	        long diffDays = diff / (24 * 60 * 60 * 1000);
	        
	        System.out.println("两个时间相差：");
	        System.out.println(diffDays + " 天, ");
	        System.out.println(diffHours + " 小时, ");
	        System.out.println(diffMinutes + " 分钟, ");
	        System.out.println(diffSeconds + " 秒.");
	        
	        int totalSecons= Integer.valueOf(String.valueOf(diff/1000));
	        System.out.println("两个时间相差总秒数："+totalSecons);
	        return totalSecons;
		}
	  
		/**
		 * 前/后?分钟
		 * 
		 * @param d
		 * @param minute
		 * @return
		 */
		public static Date rollMinute(Date d, int minute) {
			return new Date(d.getTime() + minute * 60 * 1000);
		}

		/**
		 * 前/后?天
		 * 
		 * @param d
		 * @param day
		 * @return
		 */
		public static Date rollDay(Date d, int day) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DAY_OF_MONTH, day);
			return cal.getTime();
		}

		/**
		 * 前/后?月
		 * 
		 * @param d
		 * @param mon
		 * @return
		 */
		public static Date rollMon(Date d, int mon) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.MONTH, mon);
			return cal.getTime();
		}

		/**
		 * 前/后?年
		 * 
		 * @param d
		 * @param year
		 * @return
		 */
		public static Date rollYear(Date d, int year) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.YEAR, year);
			return cal.getTime();
		}

		public static Date rollDate(Date d, int year, int mon, int day) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.YEAR, year);
			cal.add(Calendar.MONTH, mon);
			cal.add(Calendar.DAY_OF_MONTH, day);
			return cal.getTime();
		}
		
	
		
		/**
		 * 日期加上天数得到新的日期
		 * @param olddate
		 * @param day
		 * @return
		 * @throws ParseException 
		 */
		public static Date  getDateplusDay(Date olddate ,int day,String pattern){
			Calendar cal = Calendar.getInstance();
			cal.setTime(olddate);   
			cal.add(Calendar.DATE,day); 
			Date newdate=cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date resultDate = null;
			try {
				resultDate =sdf.parse(sdf.format(newdate));
			}catch (Exception e) {
				throw new RuntimeException("转换日期异常");
			}
			return resultDate;
		}
		
		
		
		/**
		 * 日期加上天数得到新的日期
		 * @param olddate
		 * @param day
		 * @return
		 * @throws ParseException 
		 */
		public static Date  getDateplusYear(Date olddate ,int year){
			Calendar cal = Calendar.getInstance();
			cal.setTime(olddate);   
			cal.add(Calendar.YEAR,year); 
			Date newdate=cal.getTime();
			return newdate;
		}
		
		
	    /**
	     * 获取一个月的第一天
	     * 
	     * @param date
	     * @return
	     */
	    public static Date getFristDateForMonth(Date date) {
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.set(Calendar.DAY_OF_MONTH, 1);
	        return c.getTime();
	    }
		
	    /**
	     * 获取某天的开始时间
	     * 
	     * @return
	     */
	    public static Date getSomeDayStartTimes(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.set(Calendar.HOUR_OF_DAY, 00);
	        calendar.set(Calendar.MINUTE, 0);
	        calendar.set(Calendar.SECOND, 0);
	        return calendar.getTime();
	    }
	    
		
		/**
		 * 日期加上天数得到新的日期
		 * @param olddate
		 * @param day
		 * @return
		 * @throws ParseException 
		 */
		public static String  getDateplusDay2(Date olddate ,int day,String pattern){
			Calendar cal = Calendar.getInstance();
			cal.setTime(olddate);   
			cal.add(Calendar.DATE,day); 
			Date newdate=cal.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(newdate);
		}
		
		
		/**
		 * 日期加上分钟得到新的日期
		 * @param olddate
		 * @param day
		 * @return
		 * @throws ParseException 
		 */
		public static Date  getDateplusMinute(Date olddate ,int minute){
			Calendar cal = Calendar.getInstance();
			cal.setTime(olddate);   
			cal.add(Calendar.MINUTE,minute); 
			Date newdate=cal.getTime();
			return newdate;
		}
		
		/**
	     * 获得昨天
	     * 
	     * @param date
	     * @return Date
	     */
	    public static Date getPreviousDate() {
	        Calendar calendar = Calendar.getInstance();
	        calendar.add(Calendar.DATE, -1);
	        return calendar.getTime();
	    }
	    
		public static int getDay(Date d) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			return cal.get(Calendar.DAY_OF_MONTH);
		}
	    

		/**
		 * 获取19位的格式时间
		 * 
		 * @param dateStr
		 * @return
		 * @throws ParseException
		 */
		public static Date getDateByFullDateStr(String dateStr) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return sdf.parse(dateStr);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		/**
		* 获取当前时间到指定时刻前的毫秒数
		* @param hour 指定时刻的小时
		* @param min 指定时刻的分钟
		* @param sec 指定时刻的秒
		* @param mill 指定时刻的毫秒
		* @return
		*/
		public static long getMillsecBeforeMoment(int hour,int min,int sec,int mill){
		  return getMillisecBetweenDate(new Date(),getMoment(hour,min,sec,mill));
		}
		
		
		/**
		* 获取两个日期之间的毫秒数
		 * @param before
		 * @param after
		 * @return
		 */
		public static long getMillisecBetweenDate(Date before, Date after){
		 long beforeTime = before.getTime();
		 long afterTime = after.getTime();
		 return afterTime - beforeTime;
		}
		
		/**
		* 获取当天的某一时刻Date
		 * @param hour 24小时
		 * @param min 分钟
		 * @param sec 秒
		 * @param mill 毫秒
		 * @return
		 */
		public static Date getMoment(int hour,int min,int sec,int mill){
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(new Date());
		 calendar.set(Calendar.HOUR_OF_DAY,hour);
		 calendar.set(Calendar.MINUTE,min);
		 calendar.set(Calendar.SECOND,sec);
		 calendar.set(Calendar.MILLISECOND,mill);
		 return calendar.getTime();
		}
		
		
		 public static Date MongoDbTimeToDate(Object mongodbTime) {
			  SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			  Date returnD=null;
			  try {
			   if (mongodbTime instanceof Date) {
			    Date d = (Date) mongodbTime;
			    returnD= d;
			   } else {
			    returnD= format1.parse(mongodbTime.toString());
			   }
			  } catch (ParseException e) {
			   try {
			    returnD=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(mongodbTime.toString());
			   } catch (ParseException e1) {
			    try {
			     returnD= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(mongodbTime.toString());
			    } catch (ParseException e2) {
			     e2.printStackTrace();
			    }
			   }
			  }
			  return returnD;
	}
		
		 
	 public static String getNowIndiaTime(String pattern) {
		 TimeZone timeZone = TimeZone.getTimeZone("GMT+5:30");
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		 simpleDateFormat.setTimeZone(timeZone);
		 return simpleDateFormat.format(new Date());
	 }
	 
	 
	 public static String getFullTimeWithMs(Date date) {
		 return dateToString(date, DATE_FORMAT_FULL_MS);
	 }
	 
		/**
		 * s - 表示 "yyyy-mm-dd" 形式的日期的 String 对象
		 * 
		 * @param f
		 * @return
		 */
		public static Date valueOf(String s) {
			final int YEAR_LENGTH = 4;
			final int MONTH_LENGTH = 2;
			final int DAY_LENGTH = 2;
			final int MAX_MONTH = 12;
			final int MAX_DAY = 31;
			int firstDash;
			int secondDash;
			int threeDash = 0;
			int fourDash = 0;
			Date d = null;

			if (s == null) {
				throw new java.lang.IllegalArgumentException();
			}

			firstDash = s.indexOf('-');
			secondDash = s.indexOf('-', firstDash + 1);
			if (s.contains(":")) {
				threeDash = s.indexOf(':');
				fourDash = s.indexOf(':', threeDash + 1);
			}
			if ((firstDash > 0) && (secondDash > 0) && (secondDash < s.length() - 1)) {
				String yyyy = s.substring(0, firstDash);
				String mm = s.substring(firstDash + 1, secondDash);
				String dd = "";
				String hh = "";
				String MM = "";
				String ss = "";
				if (s.contains(":")) {
					dd = s.substring(secondDash + 1, threeDash - 3);
					hh = s.substring(threeDash - 2, threeDash);
					MM = s.substring(threeDash + 1, fourDash);
					ss = s.substring(fourDash + 1);
				} else {
					dd = s.substring(secondDash + 1);
				}
				if (yyyy.length() == YEAR_LENGTH && mm.length() == MONTH_LENGTH && dd.length() == DAY_LENGTH) {
					int year = Integer.parseInt(yyyy);
					int month = Integer.parseInt(mm);
					int day = Integer.parseInt(dd);
					int hour = 0;
					int minute = 0;
					int second = 0;
					if (s.contains(":")) {
						hour = Integer.parseInt(hh);
						minute = Integer.parseInt(MM);
						second = Integer.parseInt(ss);
					}
					if (month >= 1 && month <= MAX_MONTH) {
						int maxDays = MAX_DAY;
						switch (month) {
						// February determine if a leap year or not
							case 2:
								if ((year % 4 == 0 && !(year % 100 == 0)) || (year % 400 == 0)) {
									maxDays = MAX_DAY - 2; // leap year so 29 days in
															// February
								} else {
									maxDays = MAX_DAY - 3; // not a leap year so 28 days
															// in February
								}
								break;
							// April, June, Sept, Nov 30 day months
							case 4:
							case 6:
							case 9:
							case 11:
								maxDays = MAX_DAY - 1;
								break;
						}
						if (day >= 1 && day <= maxDays) {
							Calendar cal = Calendar.getInstance();
							cal.set(year, month - 1, day, hour, minute, second);
							cal.set(Calendar.MILLISECOND, 0);
							d = cal.getTime();
						}
					}
				}
			}
			if (d == null) {
				throw new java.lang.IllegalArgumentException();
			}
			return d;
		}
	 
		
		
	 
	public static void main(String[] args) {
		System.out.println(getNowIndiaTime(DATE_FORMAT_FULL));
		System.out.println(dateToString(rollDay(DateUtil.getNow(), -1), DATE_FORMAT_YMD));
		int time  = DateUtil.secondsBetween(DateUtil.getNow(), DateUtil.getSomeDayStartTimes(DateUtil.rollDay(DateUtil.getNow(), 1)));
		System.out.println("当天结束还剩余 ： "+time);
		
	}
}
	
