package com.shd.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import com.shd.constants.DirectionConstants;

/**
 * about Date Util
 */
public class ImDateStringUtils {

	private Logger logger = Logger.getLogger(getClass());

	private static String patternWithYearMonth = "yyyy-MM";

	private static String patternWithMonthDay = "MM月dd日";

	private static String pattern = "yyyy-MM-dd";

	private static String fullPattern = "yyyy-MM-dd HH:mm:ss";

	private static String patternWithHourAndMin = pattern + " HH:mm";

	/**
	 * 已知日期字串(yyyy-MM-dd), 要取得 Date
	 * 
	 * @author Grandy
	 * @param dateString 日期字串, ex: "2011-05-11"
	 * @return a Date
	 * @throws ParseException 傳進來的格式錯誤, 無法 parse
	 */
	public static Date parseDate(String dateString) throws ParseException {
		return parseDate(dateString, pattern);
	}

	/**
	 * <pre>
	 * 這個用於查詢時, 取得 "迄" 的日期
	 * 已知日期字串(yyyy-MM-dd), 要取得 Date
	 * </pre>
	 * 
	 * @author Grandy
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateByEndDate(String dateString) throws ParseException {
		if (StringUtils.isBlank(dateString))
			return null;

		return parseFullDate(dateString + " 23:59:59");
	}

	/**
	 * 已知日期字串(yyyy-MM-dd hh:mm:ss), 要取得 Date
	 * 
	 * @author Grandy
	 * @param dateString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseFullDate(String dateString) throws ParseException {
		return parseDate(dateString, fullPattern);
	}

	/**
	 * 已知日期字串(yyyy-MM-dd hh:mm), 要取得 Date
	 * 
	 * @author Grandy
	 * @param dateString 日期字串, ex: "2011-05-11 17:25"
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateWithHourAndMin(String dateString) throws ParseException {
		return parseDate(dateString, patternWithHourAndMin);
	}

	/**
	 * 已知日期字串(yyyy-MM-dd), 要取得 Date
	 * 
	 * @author Grandy
	 * @param dateString 日期字串, ex: "2011-05-11"
	 * @param pattern a pattern to format
	 * @return a Date, return null when dateString is empty
	 * @throws ParseException
	 */
	public static Date parseDate(String dateString, String pattern) throws ParseException {
		if (StringUtils.isEmpty(dateString))
			return null;

		return new SimpleDateFormat(pattern).parse(dateString);
	}

	/**
	 * 已知日期, 要取得 Date字串(yyyy-MM-dd)
	 * 
	 * @author Grandy
	 * @param date 日期
	 * @return a String, ex: 2011-05-11
	 * @throws ParseException
	 */
	public static String formatDate(Date date) {
		return formatDate(date, pattern);
	}

	/**
	 * 已知日期, 要取得 Date字串(yyyy-MM-dd hh:mm:ss)
	 * 
	 * @author Grandy
	 * @param date
	 * @return
	 */
	public static String formatFullDate(Date date) {
		return formatDate(date, fullPattern);
	}

	/**
	 * 已知日期, 要取得 Date字串(yyyy-MM-dd hh:mm)
	 * 
	 * @author Grandy
	 * @param date
	 * @return a String, ex: 2011-05-11 17:20
	 */
	public static String formatDateWithHourAndMin(Date date) {
		return formatDate(date, patternWithHourAndMin);
	}

	/**
	 * 已知日期, 要取得 Date 字串(yyyy-MM)
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateWithYearMonth(Date date) {
		return formatDate(date, patternWithYearMonth);
	}

	
	/**
	 * 已知日期, 要取得星期幾
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfWeek (Date date){
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		return cd.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 已知日期, 要取得 Date 字串(MM/dd (五))
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateWithDateDay(Date date) {

		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		int mydate = cd.get(Calendar.DAY_OF_WEEK);

		String showDate = "";
		switch (mydate) {
		case 1:
			showDate = "(日)";
			break;
		case 2:
			showDate = "(一)";
			break;
		case 3:
			showDate = "(二)";
			break;
		case 4:
			showDate = "(三)";
			break;
		case 5:
			showDate = "(四)";
			break;
		case 6:
			showDate = "(五)";
			break;
		default:
			showDate = "(六)";
			break;
		}
		return formatDate(date, patternWithMonthDay) + " " + showDate;
	}

	/**
	 * 已知日期, 要取得 Date 字串
	 * 
	 * @author Grandy
	 * @param date a date
	 * @param pattern a pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null)
			return "";

		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static String formatDate(Date date, String pattern, Locale locale) {
		if (date == null)
			return "";

		return new SimpleDateFormat(pattern, locale).format(date);
	}

	/**
	 * 取得到時
	 * 
	 * @author Grandy
	 * @param date
	 * @return
	 */
	public static String getHour(Date date) {
		return formatDate(date, "HH");
	}

	/**
	 * 取得分
	 * 
	 * @author Grandy
	 * @param date
	 * @return
	 */
	public static String getMinute(Date date) {
		return formatDate(date, "mm");
	}
	
	
	/**
	 * 取得秒
	 * 
	 * @author Roy
	 * @param date
	 * @return
	 */
	public static String getSeconds(Date date) {
		return formatDate(date, "ss");
	}

	/**
	 * 取得系統日期的字串(yyyy-MM-dd)
	 * 
	 * @return
	 */
	public static String getSysDateString() {
		return formatDate(new Date());
	}

	/**
	 * 取得系統日期
	 * 
	 * @return
	 */
	public static Timestamp getSysDateTime() {
		return new Timestamp(new Date().getTime());
	}

	
	public static Timestamp transDateToTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}
	
	/**
	 * 取得系統日期的字串(yyyy-MM)
	 * 
	 * @return
	 */
	public static String getSysDateStringWithYearMonth() {
		return formatDate(new Date(), patternWithYearMonth);
	}

	/**
	 * 取得目前系統日期時間, 並格式我們要的格式
	 * 
	 * @param formatSytle 格式化的格式
	 * @return 系統日期時間字串
	 */
	public static String fetchCurrentDateString(String pattern) {
		return transDate2String(new Date(), pattern);
	}
	
	/**
	 * 取得日期 , 格式我們要的格式
	 * @param date 日期
	 * @param pattern 格式化的格式
	 * @return 日期時間字串
	 */
	public static String fetchCurrentDateString(Date date,String pattern) {
		return transDate2String(date, pattern);
	}

	/**
	 * pase 2006/01/01, or 2006/1/1, or 20060101 to 20060101
	 * 
	 * @param source 來源的日期字串
	 * @return String 整合後的字串
	 */
	public static String integrateCenturyDateString(String source) {
		return integrateCenturyDateString(source, null);
	}

	/**
	 * <pre>
	 * parse 2006/01/01, or 2006/1/1, or 20060101 to 20060101.
	 * 當 separator 不是 null, if separator = "/", it will return "2006/01/01";
	 * </pre>
	 * 
	 * @param source
	 * @param separator
	 * @return
	 */
	public static String integrateCenturyDateString(String source, String separator) {
		Validate.notNull(source, "The source can not be null.");

		String tempReturn;

		String[] strs = source.split("/");

		int length = strs.length;

		if (length == 1)
			tempReturn = source;
		else {
			if (length != 3)
				throw new IllegalStateException("source fomrat is error");

			tempReturn = strs[0] + ImStringUtils.pendingZero(strs[1], 2, DirectionConstants.FRONT) + ImStringUtils.pendingZero(strs[2], 2, DirectionConstants.FRONT);
		}

		if (separator == null)
			return tempReturn;

		StringBuilder builder = new StringBuilder();
		builder.append(tempReturn.substring(0, 4));
		builder.append(separator);
		builder.append(tempReturn.subSequence(4, 6));
		builder.append(separator);
		builder.append(tempReturn.subSequence(6, 8));

		return builder.toString();
	}

	/**
	 * integrate 970302 or 0970302, or 97/03/02, or 97/3/2 to 0970302
	 * 
	 * @param source
	 * @return
	 */
	public static String integrateTWDateString(String source) {
		return integrateTWDateString(source, null);
	}

	/**
	 * if separator is "/", then integrate 970302 or 0970302, or 97/03/02, or 97/3/2 to "097/03/02"
	 * 
	 * @param source
	 * @param separator
	 * @return
	 */
	public static String integrateTWDateString(String source, String separator) {
		Validate.notNull(source, "The source can not be null");

		String returnString;

		String[] strs = source.split("/");

		int length = strs.length;

		if (length == 1)
			returnString = ImStringUtils.pendingZero(source, 7, DirectionConstants.FRONT);
		else {
			if (length != 3) {
				throw new IllegalStateException("source fomrat is error");
			}

			StringBuilder builder = new StringBuilder();

			builder.append(ImStringUtils.pendingZero(strs[0], 3, DirectionConstants.FRONT));
			builder.append(ImStringUtils.pendingZero(strs[1], 2, DirectionConstants.FRONT));
			builder.append(ImStringUtils.pendingZero(strs[2], 2, DirectionConstants.FRONT));

			returnString = builder.toString();
		}

		if (separator == null)
			return returnString;

		StringBuilder builder = new StringBuilder();
		builder.append(returnString.substring(0, 3));
		builder.append(separator);
		builder.append(returnString.substring(3, 5));
		builder.append(separator);
		builder.append(returnString.substring(5, 7));

		return builder.toString();
	}

	/**
	 * 將 Date 物件 format 成指定 <code>ImDateFormat</code> 的字串.<br>
	 * 可自動轉成台灣日期的字串
	 * 
	 * @param date
	 * @param formatStyle
	 * @return
	 */
	public static String transDate2String(Date date, ImDateFormat format) {
		if (date == null)
			return "";

		if (format == null)
			throw new IllegalArgumentException("format is null");

		return format.formatDate(date);
	}

	/**
	 * see transDate2String( date:Date, format:ImDateFormat )
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String transDate2String(Date date, String pattern) {
		return transDate2String(date, new ImDateFormat(pattern));
	}

	/**
	 * 將字串 format 成指定 <code>ImDateFormat</code> 的 Date 物件.<br>
	 * 目前只有轉換西元日期<br>
	 * 
	 * @param value
	 * @param formatStyle
	 * @return
	 * @throws ImException
	 */
	public static Date transString2Date(String value, ImDateFormat format) throws Exception {
		if (value == null || value.trim().length() == 0)
			return null;

		try {
			return format.parse(value);
		} catch (ParseException ex) {
			throw new Exception(ex);
		}
	}

	/**
	 * see transString2Date(value:String, format:ImDateFormat)
	 * 
	 * @param value
	 * @param pattern
	 * @return
	 * @throws ImException
	 */
	public static Date transString2Date(String value, String pattern) throws Exception {
		return transString2Date(value, new ImDateFormat(pattern));
	}
	
	
	/**<pre>
	 * 傳Timestampe,回傳yyyy/MM/dd or yyyy-MM-dd or yyyy/MM/dd HH:mm:ss , or yyyy-MM-dd HH:mm:ss
	 * </pre>
	 * @param Timestamp
	 * @param Format yyyy/MM/dd , yyyy/MM/dd HH:mm:ss , yyyy-MM-dd , yyyy-MM-dd HH:mm:ss 
	 * @return Stirng 
	 * @throws DAOException
	 * @throws MessageException
	*/
	
	public static String getDate(Timestamp time,String format) {		
		Date date = new Date(time.getTime());
 		SimpleDateFormat datetimeDf = new SimpleDateFormat(format); 
 		datetimeDf.setLenient(false);
		return datetimeDf.format(date);
	}	
	
	
	/**
	/* <pre>
	 * 回傳星期幾
	 * </pre>
	 * @return
	 */
	public static String getDayOfWeekInAsiaTerm(Date date){
		
		String[] strDays = new String[]{"", "日", "一", "二",  "三", "四", "五", "六"};
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		
		return strDays[calendar.get(Calendar.DAY_OF_WEEK)];
	}
	
	/**
	 * 傳Timestampe,回傳 Date
	 * @param Timestamp 
	 * @return Stirng 
	 * @throws DAOException
	 * @throws MessageException
	 * @author Johnson
	*/
	
	public static Date getByDate(Timestamp time) {		
		Date date = new Date(time.getTime()); 		
		return date;
	}
	
	
	
	/**
	 * String 轉換成Date
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 * @author Johnson.Chen
	 */
	public static java.util.Date getFullDate(String dateStr, String dateFormat) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			formatter.setLenient(false);
			return formatter.parse(dateStr, new ParsePosition(0));
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Date String 轉換成 Timestamp 
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 * @throws ParseException
	 * @author Johnson.Chen
	 */
	public static Timestamp transStringToTimestamp(String dateStr, String dateFormat) throws ParseException {
		return (transDateToTimestamp(parseDate(dateStr,dateFormat)));
	}
	
	/**
	 * 
	 * @param dateStr(yyyy-MM-dd HH:mm:ss)
	 * @param dateFormat(yyyyMMddHHmmss)
	 * @return
	 * @author Johnson.Chen 
	 * 
	 */
	public static String getFullDateByFormat(String dateStr,String dateFormat) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);		
		return (formatter.format(parseFullDate(dateStr)));		
	}
	
	
	/**
	 * 取得日期中當周最後一天的日期
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * int first = getWeekLastDay(2012,9,1);
	 * System.out.println(first);
	 * System.out.println(getWeekLastDay(2012,9,first+1));
	 * System.out.println(getWeekLastDay(2012,9,first+15));
	 * 
	 * 
	 */
	public static int getWeekLastDay(int year,int month,int day){
		Calendar cal  = Calendar.getInstance();
		cal.set(year, month+1, day);

		int currentDay = cal.get(Calendar.DAY_OF_WEEK);
		int leftDays= Calendar.SATURDAY - currentDay;
		cal.add(Calendar.DATE, leftDays);
		return Integer.valueOf(formatDate(cal.getTime(),"d"));
	}
	
	/**
	 * 已知日期字串 , 跟據format 轉成Timestamp
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 * @throws ParseException
	 * @author Johnson.Chen
	 */
	public static Timestamp transDateStrToTimestamp(String dateStr,String dateFormat) throws ParseException {
		Date date=ImDateStringUtils.parseDate(dateStr,dateFormat);
		Timestamp transStamp= ImDateStringUtils.transDateToTimestamp(date);
		return transStamp;
	}
	
	
	public static void main(String[] args) {
		//String dateString="2006/11/11";
		try {
//			//String formatString=(new SimpleDateFormat("yyyy-MM-dd").parse(dateString)).toString();
//			//System.out.println("formationg="+formatString);
//			//System.out.println("currentTimeString="+ImDateStringUtils.getSysDateTime().toString());
//			/*
//			int first = getWeekLastDay(2012,9,1);
//			System.out.println(first);
//			System.out.println(getWeekLastDay(2012,9,first+1));
//			System.out.println(getWeekLastDay(2012,9,first+8));
//			System.out.println(getWeekLastDay(2012,9,first+15));
//			System.out.println(getWeekLastDay(2012,9,first+22));
//			String dateStr="2012-10-11 09:00:00";
//			dateStr=dateStr.replaceAll("-", "");
//			dateStr=dateStr.replaceAll(":", "");
//			dateStr=dateStr.substring(0,8)+dateStr.substring(9,dateStr.length());
//			System.out.println("dateStr="+dateStr);
//			String dateString="2012-10-01";
//			Date date=ImDateStringUtils.parseDate(dateString);
//			*/
//			//String pubdate="Sun, 14 Jul 2013 15:30:01 GMT";
//			//Locale locale = new Locale("en", "US");
//			
//			//SimpleDateFormat ev_carplus_sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", locale);
//				
			//System.out.println("value ="+ImDateStringUtils.getDate(currentTime, EvConstants.MSGPC_TIME_PATTERN));
////			Timestamp currentTime = new Timestamp(System.currentTimeMillis());
////			System.out.println("currentTime ="+currentTime);
////			System.out.println("value="+ImDateStringUtils.getDate(currentTime, "yyyy-MM-dd"));
////		
			
			
			
		    
//		}catch (ParseException e) {
//			System.out.println(" into parseExceptoin");
//			
		}catch (Exception e) {
			System.out.println("exception");
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		
	}
}
