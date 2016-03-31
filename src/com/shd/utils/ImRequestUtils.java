package com.shd.utils;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

public class ImRequestUtils {


	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉為 BigDecimal 物件
	 * 
	 * 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @return an instaoce of BigDecimal, but a value is blank, return null
	 */
	public BigDecimal getBigDecimal(String name) {
		return ImBigDecimalUtils.getBigDecimal(getString(name));
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉為 BigDecimal 物件
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @param isValidate true: 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException, false: 不會檢查
	 * @return an instaoce of BigDecimal, but a value is blank, return null
	 */
	public BigDecimal getBigDecimal(String name, boolean isValidate) {
		return ImBigDecimalUtils.getBigDecimal(getString(name, isValidate));
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉成 BigDecimal[] 的物件
	 * 
	 * 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @return an array of BigDecimal
	 */
	public BigDecimal[] getBigDecimalArray(String name) {
		return getBigDecimalArray(name, true);
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉成 BigDecimal[] 的物件
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @param isValidate true:如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException, false: 不檢查
	 * @return an array of BigDecimal
	 */
	public BigDecimal[] getBigDecimalArray(String name, boolean isValidate) {
		String[] values = getStringArray(name, isValidate);

		BigDecimal[] bigDecimals = new BigDecimal[values.length];

		for (int i = 0; i < values.length; i++)
			bigDecimals[i] = ImBigDecimalUtils.getBigDecimal(values[i]);

		return bigDecimals;
	}

	/**
	 * <pre>
	 * 取得到目前的頁碼
	 * 
	 * 如果在 jsp 沒有指定這個 "currentPage", 就會丟出 IllegalArgumentException
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @return a value of current page.
	 */
	public Integer getCurrentPage() {
		return getInteger("currentPage");
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉為 Date 物件(yyyy/mm/dd)
	 * 
	 * 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @return an instaoce of Date, but a value is blank, return null
	 * @throws ImException when parsing date to string, occurs error
	 */
	public Date getDate(String name) throws Exception {
		return ImDateStringUtils.transString2Date(getString(name), ImDateFormatStyle.CENTURY_DASH_SHORT);
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉為 Date 物件(yyyy/mm/dd)
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @param isValidate true: 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException, false: 不檢查
	 * @return an instaoce of Date, but a value is blank, return null
	 * @throws ImException when parsing date to string, occurs error
	 */
	public Date getDate(String name, boolean isValidate) throws Exception {
		return ImDateStringUtils.transString2Date(getString(name, isValidate), ImDateFormatStyle.CENTURY_DASH_SHORT);
	}

	/**
	 * <pre>
	 * 這個用於查詢時, 取得 "迄" 的日期
	 * 已知日期字串(yyyy/MM/dd), 要取得 Date
	 * </pre>
	 * 
	 * @param dateString a string
	 * @return an instaice of Date (yyyy/mm/dd 23:59:59)
	 * @throws ImException when parsing date to string, occurs error
	 */
	public Date getEndDate(String name) throws Exception {
		return ImDateUtils.getEndDate(getString(name));
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉為 Integer 物件
	 * 
	 * 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @return an instaoce of Integer, but a value is blank, return null
	 */
	public Integer getInteger(String name) {
		return ImIntegerUtils.getInteger(getString(name));
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉為 Integer 物件
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @param isValidate true: 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException, false: 不檢查
	 * @return an instaoce of Integer, but a value is blank, return null
	 */
	public Integer getInteger(String name, boolean isValidate) {
		return ImIntegerUtils.getInteger(getString(name, isValidate));
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉成 Integer[] 的物件
	 * 
	 * 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @return an array of Integer
	 */
	public Integer[] getIntegerArray(String name) {
		return getIntegerArray(name);
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉成 Integer[] 的物件
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @param isValidate true:如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException, false: 不檢查
	 * @return an array of Integer
	 */
	public Integer[] getIntegerArray(String name, boolean isValidate) {
		String[] values = getStringArray(name, isValidate);

		Integer[] integers = new Integer[values.length];

		for (int i = 0; i < values.length; i++)
			integers[i] = ImIntegerUtils.getInteger(values[i]);

		return integers;
	}

	/**
	 * <pre>
	 * 當畫面編輯和瀏灠共用同一個畫面時, 就要用到這個參數 "jspAction", 
	 * 使用這個參數來判定目前面是要當瀏灠用, 還是當編輯使用.
	 * 
	 * 並搭配 jquery, 來實現編輯和瀏灠共用同一個畫面
	 * 
	 * 在 jsp 我們可以設定二個 value:
	 * 1. edit: 表示編輯
	 * 2. browse: 表示瀏灠
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @return "edit": 編輯, "browse":瀏灠
	 */
	public String getJspAction() {
		return getString("jspAction");
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉為 Long 物件
	 * 
	 * 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @return an instaoce of Long, but a value is blank, return null
	 */
	public Long getLong(String name) {
		return ImLongUtils.getLong(getString(name));
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉為 Long 物件
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @param isValidate true: 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException, false: 不會檢查
	 * @return an instaoce of Long, but a value is blank, return null
	 */
	public Long getLong(String name, boolean isValidate) {
		return ImLongUtils.getLong(getString(name, isValidate));
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉成 Long[] 的物件
	 * 
	 * 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @return an array of Long
	 */
	public Long[] getLongArray(String name) {
		return getLongArray(name, true);
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 並轉成 Long[] 的物件
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @param isValidate true:如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException, false: 不檢查
	 * @return an array of Long
	 */
	public Long[] getLongArray(String name, boolean isValidate) {
		String[] values = getStringArray(name, isValidate);

		Long[] longs = new Long[values.length];

		for (int i = 0; i < values.length; i++)
			longs[i] = ImLongUtils.getLong(values[i]);

		return longs;
	}

	/**
	 * <pre>
	 * 取得每頁的筆數
	 * 
	 * 如果在 jsp 沒有指定這個 "pageSize", 就會丟出 IllegalArgumentException
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @return a size per page
	 */
	public Integer getPageSize() {
		return getInteger("pageSize");
	}

	/**
	 * <pre>
	 * 這個用於查詢時, 取得 "起" 的日期
	 * 已知日期字串(yyyy/MM/dd), 要取得 Date
	 * </pre>
	 * 
	 * @param dateString a string
	 * @return an instaice of Date (yyyy/mm/dd 00:00:00)
	 * @throws ImException when parsing date to string, occurs error
	 */
	public Date getStartDate(String name) throws Exception {
		return ImDateUtils.getStartDate(getString(name));
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameter( name )
	 * 
	 * 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @return a value from but a value is blank, return null
	 */
	public String getString(String name) {
		return getString(name, true);
	}

	/**
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameter( name )
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @param isValidate true: 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException, false: 就不做檢查
	 * @return a value from Request,but a value is blank, return null
	 */
	public String getString(String name, boolean isValidate) {
		//String value = request.getParameter(name);

		
		return StringUtils.trimToNull("");
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 
	 * 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @return an array of String
	 */
	public String[] getStringArray(String name) {
		return getStringArray(name, true);
	}

	/**
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameterValues( name )
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @param isValidate true:如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException, false: 不檢查
	 * @return an array of String
	 */
	public String[] getStringArray(String name, boolean isValidate) {
		

		return null;
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameter( name )
	 * 並在字串前後加上 "%"
	 * 
	 * 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @return 前後加 % 的字串, 但如果從 Request 取得到的字串為 Blank, 這樣會回傳 null
	 */
	public String getStringWithLike(String name) {
		return ImStringUtils.getStringWithLike(getString(name));
	}

	/**
	 * <pre>
	 * 從 request 物件取得到指定 name 的 value, 就是 request.getParameter( name )
	 * 並在字串前後加上 "%"
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @param name a name in the Request
	 * @param isValidate, true: 如果在 Reqesut 物件中, 找不到這個 name, 會丟出 IllegalArgumentException, false: 不會
	 * @return 前後加 % 的字串, 但如果從 Request 取得到的字串為 Blank, 這樣會回傳 null
	 */
	public String getStringWithLike(String name, boolean isValidate) {
		return ImStringUtils.getStringWithLike(getString(name, isValidate));
	}

	/**
	 * <pre>
	 * 取得是否以 window open 的方式開啟網頁.
	 * 
	 * 以 window open 的方式開啟網頁時, 我們不想要把 Header 和 Footer 加入, 
	 * 這時就可以使用這個方式, 來決定
	 * 
	 * 在 jsp, 我們可以設定二個 value:
	 * 1. true: 以 window open 的方式開啟
	 * 2. false: 不是以 window open 的方式開啟
	 * </pre>
	 * 
	 * @param request an instance of HttpServletRequest
	 * @return true: 以 window open 的方式開啟, false: 反之
	 */
	public boolean isOpenWindow() {
		return Boolean.parseBoolean(getString("openWindow"));
	}
}
