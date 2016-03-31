package com.shd.utils;

import java.text.*;

import java.util.*;
import java.sql.Timestamp;

public class DateUtils {
	
	
	public static long diffDay(String fromDateStr,String toDateStr) {
		try {
			SimpleDateFormat df=new SimpleDateFormat("yyyy/MM/dd");  
			Calendar calStart=Calendar.getInstance();
			Calendar calEnd=Calendar.getInstance();
			Date startDate=df.parse(fromDateStr);
			Date endDate=df.parse(toDateStr);
			calStart.setTime(startDate);
			calEnd.setTime(endDate);
			long beforeDateTime=calStart.getTimeInMillis();
			long endDateTime=calEnd.getTimeInMillis();
			long theday =(endDateTime-beforeDateTime)/(1000*60*60*24);
			return theday;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public static java.util.Date getFullDate(String dateStr, String dateFormat,
			Locale locale) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, locale);
			formatter.setLenient(false);
			return formatter.parse(dateStr, new ParsePosition(0));
		} catch (Exception e) {
			return null;
		}
	}

	public static java.util.Date getFullDate(String dateStr, String dateFormat) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			formatter.setLenient(false);
			return formatter.parse(dateStr, new ParsePosition(0));
		} catch (Exception e) {
			return null;
		}
	}

	public static java.util.Date getFullDate(String dateStr) {
		return getFullDate(dateStr, "yyyy/MM/dd HH:mm:ss");
	}

	public static java.util.Date getFullDate(String dateStr, int hour, int minute) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			formatter.setLenient(false);
			return formatter.parse(dateStr + " " + hour + ":" + minute + ":00",
					new ParsePosition(0));
		} catch (Exception e) {
			return null;
		}
	}

	public static String getFullDateStr(java.util.Date date) {
		return getFullDateStr(date, "yyyy/MM/dd HH:mm:ss");
	}

	public static String getFullDateStr(java.util.Date date, String dateFormat) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			formatter.setLenient(false);
			return formatter.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	// 加入Locale
	public static String getFullDateStr(java.util.Date date, String dateFormat,
			Locale locale) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, locale);
			formatter.setLenient(false);
			return formatter.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public static String getTimeStampStr(java.util.Date date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			formatter.setLenient(false);
			return formatter.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/* =================================================== */
	public static String subStringOut(String target, String param) {
		String result = "";
		int start = target.indexOf(param);
		System.out.println("start = " + start);
		result = target.substring(0, start) + " "
				+ target.substring(start + param.length());
		System.out.println("result = " + result);

		return result;
	}
	
	/**
	 * 傳String(yyyy-MM-dd),回傳Timestamp
	 * @param yyyy-MM-dd 
	 * @return Timestamp
	 * 
	*/	
	public static java.sql.Timestamp getTimestampe(String date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsedDate = dateFormat.parse(date);
			java.sql.Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			return timestamp;
		}catch(Exception e) {
			return null;
		}
	}	
	
	
	/**
	 * 傳Timestampe,回傳yyyy/MM/dd
	 * @param Timestamp 
	 * @return Stirng 
	 * @throws DAOException
	 * @throws MessageException
	*/
	
	public static String getDate(Timestamp time) {		
		Date date = new Date(time.getTime());
 		SimpleDateFormat datetimeDf = new SimpleDateFormat("yyyy/MM/dd"); 
 		datetimeDf.setLenient(false);
		return datetimeDf.format(date);
	}
	
	/**
	 * 傳Timestampe,回傳yyyy/MM/dd HH:mm:ss
	 * @param Timestamp 
	 * @return Stirng 
	 * @throws DAOException
	 * @throws MessageException
	*/
	
	public static String getDate2(Timestamp time) {		
		Date date = new Date(time.getTime());
 		SimpleDateFormat datetimeDf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
 		datetimeDf.setLenient(false);
		return datetimeDf.format(date);
	}
	
	/**
	 * 傳Timestampe,回傳 Date
	 * @param Timestamp 
	 * @return Stirng 
	 * @throws DAOException
	 * @throws MessageException
	*/
	
	public static Date getByDate(Timestamp time) {		
		Date date = new Date(time.getTime()); 		
		return date;
	}
	

	/* =================================================== */

	public static void main(String[] args) {
		String value1="2011-10-28 19:30:00.0";
		//String value1="2010/10/10 41:12:13";
		System.out.println("Time  ="+value1.substring(11,16));	
		System.out.println("day   ="+value1.substring(0,10));
		
		String phone="041-12345678";
		String phone1=phone.replace("-", "");
		System.out.println("phone1="+phone1);
		
		String value = "Tue Oct 22 05:45:53 2002";		

		try {
			Timestamp currentTime=new Timestamp(System.currentTimeMillis());
			SimpleDateFormat sDateFromter = new SimpleDateFormat("yyyy-MM-dd");
			String sDateFrom=sDateFromter.format(currentTime);
			System.out.println(" fromDate="+sDateFrom);
			
			System.out.println("********************");
			Date newdate=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //格式化日期
			String today=sdf.format(newdate);
			System.out.println("today="+today);
			int days=-10;
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, days);
			String tenDaysAfterDate=sdf.format(cal.getTime());
			System.out.println("before day="+tenDaysAfterDate);			
			System.out.println("*********************");
			
			String s1="(02)23121231，1234122231";
			s1=s1.substring(0, s1.indexOf("，"));
			System.out.println("s1="+s1);
			String dateStr1 ="2009/13/13 00:00:00";
			Date date_1=DateUtils.getFullDate(dateStr1);
			System.out.println("date_1="+date_1);
			
			
			//Timestamp currentTime=new Timestamp(System.currentTimeMillis());
			String date1=DateUtils.getDate(currentTime);
			System.out.println(" date1="+date1);
			System.out.println("year ="+date1.substring(2,4));
			System.out.println("month="+date1.substring(5,7));
			
			
			Date date = DateUtils.getFullDate(value, "EEE MMM dd HH:mm:ss yyyy",
					new Locale("en"));
			System.out.println("date.toString()=" + date.toString());

			String rec = DateUtils.getFullDateStr(date, "yyyy-MM-dd'T'HH:mm:ss");
			System.out.println("rec.toString()=" + rec.toString());
			
			
			String dateStr = "2007/08/08 12:00:00";
			Date date_o = DateUtils.getFullDate(dateStr);
			System.out.println("date_o = " + date_o);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String result = DateUtils.getFullDateStr(new Date(),
				"EEE MMM dd HH:mm:ss yyyy");
		System.out.println("result=" + result);

		DateUtils.subStringOut("2005-09-09T16:26:03+08:00", "T");

		System.out.println("##########");
		String sDate = "2005/12/12 16:48:00";
		Date dateFirst = DateUtils.getFullDate(sDate, "yyyy/MM/dd HH:mm:ss");
		System.out.println("the date = " + dateFirst.toString());

		Date dateSecond = new Date();
		int duration = (int) (dateSecond.getTime() - dateFirst.getTime()) / 1000;
		System.out.println("duration = " + duration);
	}
}
