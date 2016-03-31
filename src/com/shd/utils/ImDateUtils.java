package com.shd.utils;

import static com.shd.constants.DirectionConstants.BACK;
import static com.shd.constants.DirectionConstants.FRONT;
import static com.shd.constants.OrderConstants.ASCEND;
import static com.shd.constants.OrderConstants.DESCEND;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.shd.constants.DirectionConstants;

public class ImDateUtils {

	public static int FIELD_YEAR = 7;

	public static int FIELD_MONTH = 6;

	public static int FIELD_DAY = 5;

	public static int FIELD_HOUR = 4;

	public static int FIELD_MINUTE = 3;

	public static int FIELD_SECOND = 2;

	public static int FIELD_MILLISECOND = 1;

	public static Date doAfterDate(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);

		return calendar.getTime();
	}

	public static Date doBeforeDate(Date date, int field, int amount) {
		return doAfterDate(date, field, 0 - amount);
	}

	/**
	 * 取得一個月的天數<br>
	 * 陽歷有潤年, 每四年潤一次(4的倍數), 所以潤年可以到達 29 天
	 * 
	 * @param year 年份
	 * @param month　月份
	 * @return
	 */
	public static int fetchDaysInMonth(int year, int month) {
		if (year < 1900)
			throw new IllegalArgumentException("the value of the year less than 1900");

		if (month < 0)
			throw new IllegalArgumentException("the value of the month less than 1");

		if (month > 11)
			throw new IllegalArgumentException("the value of the month great than 12");

		// 單月
		if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11)
			return 31;
		else {
			// 二月
			if (month == 1) {
				// 每四年潤一次
				if (year % 4 == 0)
					return 29;
				else
					return 28;
			} else
				return 30;
		}
	}

	/**
	 * 當傳進去的日期時間, 超過系統時間, 將會回傳系統時間, 如果沒有超過, 則回傳回來的日期時間
	 * 
	 * @param source 傳進去的日期時間
	 * @return a Date
	 */
	public static Date fetchLessThanOrEqualCurrentDate(Date date) {
		Date currentDate = new Date();

		if (date == null)
			throw new IllegalArgumentException("dae is null");

		if (date.after(currentDate))
			return currentDate;

		return date;
	}

	/**
	 * @see com.Im.util.date.ImDateUtils#fetchYears(int, int, String, int)
	 */
	public static int[] fetchYears(Date date, int amount, String order, int direction) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(date);

		return fetchYears(calendar.get(Calendar.YEAR), amount, order, direction);
	}

	/**
	 * 取得年的陣列, 如傳入的年份為 2008 年, 要取五個(往前計算), 由大至小, 那麼結果為 2008, 2007, 2006, 2005, 2004
	 * 
	 * @param year 要計算的起始年份
	 * @param amount 取得數量
	 * @param order 取得年份的排序, 請使用 DateUtil.ASCEND or DateUtil.DESCEND
	 * @param direction 往前或往後計算, front: 往前計算, 年份愈來愈小
	 * @return 年份的陣列
	 */
	public static int[] fetchYears(int year, int amount, String order, int direction) {
		if (year < 1900)
			throw new IllegalArgumentException("year less than 1900");

		if (amount < 1)
			throw new IllegalArgumentException("amount less than 1");

		if (order == null)
			throw new IllegalArgumentException("order is null");

		if (!(order.equals(ASCEND) || order.equals(DESCEND)))
			throw new IllegalArgumentException("the value of the order is error");

		if (!(direction == FRONT || direction == BACK))
			throw new IllegalArgumentException("the value of the direction is error");

		if ((year - (amount - 1)) < 1900)
			throw new IllegalArgumentException("return value less than 1900");

		int[] years = new int[amount];

		years[0] = year;

		for (int i = 1; i < amount; i++) {
			if (direction == DirectionConstants.FRONT)
				years[i] = --year;
			else
				years[i] = ++year;
		}

		ImArrayUtils.sort(years, order);

		return years;
	}

	public static Date getDate(int year, int month, int day) {
		return getDate(year, month, day, 0, 0, 0);
	}

	public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
		return getDate(year, month, day, hour, minute, second, 0);
	}

	public static Date getDate(int year, int month, int day, int hour, int minute, int second, int millisecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, millisecond);

		return calendar.getTime();
	}


	/**
	 * Adds or subtracts the specified amount of time to the given calendar field, based on the calendar's rules. For example, to subtract 5 days from the current time of
	 * the calendar, you can achieve it by calling: add(Calendar.DAY_OF_MONTH, -5).
	 * 
	 * @author Grandy
	 * @param date a date
	 * @param field the calendar field.
	 * @param amount the amount of date or time to be added to the field
	 */
	public static Date add(Date date, int field, Integer amount) {
		Validate.notNull(date);

		if (amount == null)
			return date;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(field, amount);
		

		return calendar.getTime();
	}

	/**
	 * <pre>
	 * 這個用於查詢時, 取得 "迄" 的日期
	 * 已知日期字串(yyyy-MM-dd), 要取得 Date
	 * </pre>
	 * 
	 * @param dateString a string
	 * @return an instaice of Date (yyyy-mm-dd 23:59:59)
	 * @throws ImException when parsing date to string, occurs error
	 */
	public static Date getEndDate(String dateString) throws Exception {
		if (StringUtils.isBlank(dateString))
			return null;

		return ImDateStringUtils.transString2Date(dateString + " 23:59:59", ImDateFormatStyle.CENTURY_DASH_LONG);
	}

	public static Date getEndDate(Date date) throws Exception {
		return getEndDate(ImDateStringUtils.transDate2String(date, ImDateFormatStyle.CENTURY_DASH_SHORT));
	}

	/**
	 * <pre>
	 * 這個用於查詢時, 取得 "起" 的日期
	 * 已知日期字串(yyyy-MM-dd), 要取得 Date
	 * </pre>
	 * 
	 * @param dateString a string
	 * @return an instaice of Date, (yyyy-mm-dd 00:00:00)
	 * @throws ImException when parsing date to string, occurs error
	 */
	public static Date getStartDate(String dateString) throws Exception {
		if (StringUtils.isBlank(dateString))
			return null;

		return ImDateStringUtils.transString2Date(dateString + " 00:00:00", ImDateFormatStyle.CENTURY_DASH_LONG);
	}

	public static Date getStartDate(Date date) throws Exception {
		return getStartDate(ImDateStringUtils.transDate2String(date, ImDateFormatStyle.CENTURY_DASH_SHORT));
	}

	/**
	 * <pre>
	 * 依 <code>field</code>來取得二個日期的相差值( endDate - startDate )
	 * 1. field=FIELD_YEAR, 傳回二個日期相差的年數
	 * 2. field=FIELD_MONTH, 傳回二個日期相差的月數
	 * 3. field=FIELD_DAY, 傳回二個日期相差的天數 
	 * 4. field=FIELD_HOUR, 傳回二個日期相差的時數
	 * 5. field=FIELD_MINUTE, 傳回二個日期相差的分數
	 * 6. field=FIELD_SECOND, 傳回二個日期相差的秒數
	 * 7. field=FIELD_MILLISECOND, 傳回二個日期相差的毫秒數
	 * </pre>
	 * 
	 * @param startDate
	 * @param endDate
	 * @param field
	 * @return
	 */
	public static long minusDate(Date startDate, Date endDate, int field) {
		Validate.notNull(startDate, "The argument of startDate must be not null.");
		Validate.notNull(endDate, "The argument of endDate must be not null.");
		Validate.isTrue(field >= 1 && field <= 7, "The value of the field is error");

		long startLong = startDate.getTime();
		long endLong = endDate.getTime();

		Validate.isTrue(endLong >= startLong, "The endDate must be great than startDate.");

		long minusLong = endLong - startLong;
		long divisor = 1;

		if (field == FIELD_YEAR || field == FIELD_MONTH)
			return minusDateByYearOrMonth(startDate, endDate, field);

		if (field >= FIELD_MILLISECOND)
			divisor *= 1;

		if (field >= FIELD_SECOND)
			divisor *= 1000;

		if (field >= FIELD_MINUTE)
			divisor *= 60;

		if (field >= FIELD_HOUR)
			divisor *= 60;

		if (field >= FIELD_DAY)
			divisor *= 24;

		return minusLong / divisor;
	}

	/**
	 * <pre>
	 * 將毫秒依 <code>pattern</code>來顯示, 目前的 pattern 有
	 *  d:天
	 *  H:時
	 *  m:分
	 *  s:秒
	 *  S:毫秒
	 *  
	 * example, pattern="d天H時m分s秒-S", 會取得 "1天10時59分2秒-1"
	 * 如果沒有設成分，如：d天H時s秒-S，則會回傳 "1天10時3542秒-1", <== 59分 * 60 + 2 = 3542
	 * </pre>
	 * 
	 * @param startDate
	 * @param endDate
	 * @param pattern
	 * @return
	 */
	public static String minusDate(Date startDate, Date endDate, String pattern) {
		Validate.notNull(startDate, "The date of startDate must not be null");
		Validate.notNull(endDate, "The date of endDate must not be null");
		Validate.notNull(pattern, "The value of pattern must not be null");

		long startLong = startDate.getTime();
		long endLong = endDate.getTime();

		Validate.isTrue(endLong >= startLong, "The endDate must be great than startDate.");

		return parseMillisecond2Unit(endLong - startLong, pattern);
	}

	private static int minusDateByYearOrMonth(Date startDate, Date endDate, int field) {
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();

		startCalendar.setTime(startDate);
		endCalendar.setTime(endDate);

		int startYear = startCalendar.get(Calendar.YEAR);
		int startMonth = startCalendar.get(Calendar.MONTH);

		int endYear = endCalendar.get(Calendar.YEAR);
		int endMonth = endCalendar.get(Calendar.MONTH);

		int minusYear = endYear - startYear;

		if (field == FIELD_YEAR) {
			startCalendar.set(Calendar.YEAR, 1974);
			endCalendar.set(Calendar.YEAR, 1974);

			if (endCalendar.compareTo(startCalendar) < 0)
				minusYear--;

			return minusYear;
		} else {
			int minusMonth = minusYear * 12 + (endMonth - startMonth);

			startCalendar.set(Calendar.YEAR, 1974);
			startCalendar.set(Calendar.MONTH, 2);

			endCalendar.set(Calendar.YEAR, 1974);
			endCalendar.set(Calendar.MONTH, 2);

			if (endCalendar.compareTo(startCalendar) < 0)
				minusMonth--;

			return minusMonth;
		}
	}

	/**
	 * <pre>
	 * 將毫秒依 <code>pattern</code>來顯示, 目前的 pattern 有
	 *  d:天
	 *  H:時
	 *  m:分
	 *  s:秒
	 *  S:毫秒
	 *  
	 * example, pattern="d天H時m分s秒-S", 會取得 "1天10時59分2秒-1".
	 * 如果沒有設成分，如：d天H時s秒-S，則會回傳 "1天10時3542秒-1", <== 59分 * 60 + 2 = 3542
	 * 
	 * </pre>
	 * 
	 * @param millisecond
	 * @param pattern
	 * @return
	 */
	public static String parseMillisecond2Unit(long millisecond, String pattern) {
		String[] letters = { "d", "H", "m", "s", "S" };

		Validate.notNull(pattern, "The value of pattern must not be null");
		Validate.isTrue(StringUtils.indexOfAny(pattern, letters) != -1, "The value of pattern is error");

		long[] values = processMillisecond(letters, millisecond, pattern);

		StringBuilder builder = new StringBuilder();

		String totalFind = "";
		String previousLetter = "";
		for (int i = 0; i < pattern.length(); i++) {
			String letter = "";
			for (int j = 0; j < letters.length; j++) {
				if (pattern.substring(i, i + 1).equals(letters[j])) {
					letter = letters[j];
					break;
				}
			}

			// 找不到 letters 其中的一個值
			if (StringUtils.isEmpty(letter) && StringUtils.isEmpty(totalFind))
				builder.append(pattern.substring(i, i + 1));
			else {
				if (StringUtils.isEmpty(totalFind) || previousLetter.equals(letter))
					totalFind += letter;
				else {
					builder.append(processPattern(values, letters, totalFind));

					if (StringUtils.isEmpty(letter)) {
						builder.append(pattern.substring(i, i + 1));
						totalFind = "";
					} else
						totalFind = letter;
				}
			}

			previousLetter = letter;
		}

		if (StringUtils.isNotEmpty(totalFind))
			builder.append(processPattern(values, letters, totalFind));

		return builder.toString();
	}

	private static long[] processMillisecond(String[] letters, long millisecond, String pattern) {
		long[] values = new long[5];

		long divisor;

		for (int i = 0; i < letters.length; i++) {
			if (StringUtils.indexOf(pattern, letters[i]) != -1) {
				if (letters[i] == letters[0]) {
					divisor = 24 * 60 * 60 * 1000;
					values[0] = millisecond / divisor;
					millisecond -= values[0] * divisor;
				} else if (letters[i] == letters[1]) {
					divisor = 60 * 60 * 1000;
					values[1] = millisecond / divisor;
					millisecond -= values[1] * divisor;
				} else if (letters[i] == letters[2]) {
					divisor = 60 * 1000;
					values[2] = millisecond / divisor;
					millisecond -= values[2] * divisor;
				} else if (letters[i] == letters[3]) {
					divisor = 1000;
					values[3] = millisecond / divisor;
					millisecond -= values[3] * divisor;
				} else if (letters[i] == letters[4]) {
					values[4] = millisecond;
				}
			}
		}

		return values;
	}

	/**
	 * @param values
	 * @param letters
	 * @param totalFind
	 * @return
	 */
	private static String processPattern(long[] values, String[] letters, String totalFind) {
		for (int i = 0; i < letters.length; i++)
			if (StringUtils.indexOf(totalFind, letters[i]) != -1)
				return new DecimalFormat(StringUtils.leftPad("", totalFind.length(), "0")).format(values[i]);

		throw new IllegalArgumentException("The value of totalFind is error");
	}

	/**
	 * <pre>
	 * 比較二個日期
	 * 1. date1 = date2: return 0
	 * 2. date1 > date2: return 1
	 * 3. date1 < date2: return -1
	 * </pre>
	 * 
	 * @author Grandy
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDate(Date date1, Date date2) {
		Validate.notNull(date1);
		Validate.notNull(date2);

		long long1 = date1.getTime();
		long long2 = date2.getTime();

		if (long1 == long2)
			return 0;
		else if (long1 > long2)
			return 1;
		else
			return -1;
	}

	/**
	 * 取得<code>amount</code> 年前的日期
	 * 
	 * @author Grandy
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date doBeforeDateByYear(Date date, int amount) {
		return doBeforeDate(date, Calendar.YEAR, amount);
	}

	/**
	 * 取得<code>amount</code> 月前的日期
	 * 
	 * @author Grandy
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date doBeforeDateByMonth(Date date, int amount) {
		return doBeforeDate(date, Calendar.MONTH, amount);
	}

	/**
	 * 取得<code>amount</code> 日前的日期
	 * 
	 * @author Grandy
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date doBeforeDateByDay(Date date, int amount) {
		return doBeforeDate(date, Calendar.DAY_OF_MONTH, amount);
	}
	/**
	 * 取得<code>amount</code> 日前的日期
	 * 
	 * @author Grandy
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date doBeforeDateByHour(Date date, int amount) {
		return doBeforeDate(date, Calendar.HOUR, amount);
	}
	
	/**
	 * 取得<code>amount</code> 分後的日期
	 * 
	 * @author Grandy
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date doBeforeDateByMinute(Date date, int amount) {
		return doBeforeDate(date, Calendar.MINUTE, amount);
	}
	

	/**
	 * 取得<code>amount</code> 年後的日期
	 * 
	 * @author Grandy
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date doAfterDateByYear(Date date, int amount) {
		return doAfterDate(date, Calendar.YEAR, amount);
	}

	/**
	 * 取得<code>amount</code> 月後的日期
	 * 
	 * @author Grandy
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date doAfterDateByMonth(Date date, int amount) {
		return doAfterDate(date, Calendar.MONTH, amount);
	}

	/**
	 * 取得<code>amount</code> 日後的日期
	 * 
	 * @author Grandy
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date doAfterDateByDay(Date date, int amount) {
		return doAfterDate(date, Calendar.DAY_OF_MONTH, amount);
	}
	
	/**
	 * 取得<code>amount</code> 小時後的日期
	 * 
	 * @author Grandy
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date doAfterDateByHour(Date date, int amount) {
		return doAfterDate(date, Calendar.HOUR, amount);
	}
	

	public static Timestamp getSysDateTime() {
		return new Timestamp(new Date().getTime());
	}
	
	public static Timestamp parseDateTimeString(String date, String hour, String minute) throws Exception{
		return new Timestamp(ImDateStringUtils.transString2Date(date + " " + hour + ":" + minute, "yyyy-MM-dd HH:mm" ).getTime());
	}
	
	public static Timestamp parseDateTimeString(String date, String hour, String minute,String seconds) throws Exception{
		return new Timestamp(ImDateStringUtils.transString2Date(date + " " + hour + ":" + minute + ":" + seconds , "yyyy-MM-dd HH:mm:ss" ).getTime());
	}
}
