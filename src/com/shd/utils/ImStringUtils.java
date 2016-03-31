package com.shd.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import com.shd.constants.DirectionConstants;

/**
 * about string util
 * 
 * @author Grandyu
 */
public class ImStringUtils {
	
	/**
	 * <pre>
	 * 取固定長度字串，超出長度以 ... 取代。 如 "測試字串" will be replaced to "測試字.." 
	 * </pre>
	 * @param str
	 * @param length
	 * @return
	 * @author Grandy
	 */
	public static String subString(String str, int length){
		if( StringUtils.isBlank(str) )
			return null;
		
		if( str.length() <= length )
			return str;
		
		StringBuilder builder = new StringBuilder();
		builder.append(str.substring(0, length));
		builder.append("...");
		
		return builder.toString();
	}

	/**
	 * 身份證驗證程式
	 * 
	 * @param str 要驗證的身份證字串
	 * @return 如果驗證正確, 傳回 true, 否則傳回 false
	 */
	public static boolean checkIdentity(String str) {
		Validate.notEmpty(str, "The value of identity can not be empty");

		// check length
		if (str.length() != 10)
			return false;

		String[] codes = { "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "X", "Y", "O", "I" };

		int[] numbers = { 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 35, 34 };

		int total = 0;
		str = str.toUpperCase();
		String strFirst = str.substring(0, 1);
		String strNumber = str.substring(1, 10);

		// check format of first character
		if (!isLetter(strFirst))
			return false;

		// check format of number
		if (!isDigital(strNumber))
			return false;

		// check rule
		int lengthOfCodes = codes.length;
		for (int i = 0; i < lengthOfCodes; i++) {
			if (strFirst.equals(codes[i])) {
				str = String.valueOf(numbers[i]) + strNumber;
				break;
			}
		}

		for (int i = 10; i >= 1; i--) {
			if (i == 10)
				total += Integer.parseInt(str.substring(10 - i, 10 - i + 1));
			else
				total += (Integer.parseInt(str.substring(10 - i, 10 - i + 1)) * i);
		}

		int value = 10 - total % 10;
		if (value == 10)
			value = 0;

		return (value == Integer.parseInt(strNumber.substring(8, 9)));
	}

	/**
	 * 將字串陣列以分隔符號來組合, 如: computer,desk,room
	 * 
	 * @param source 字串陣列
	 * @param delim 分隔符號
	 * @return
	 */
	public static <T> String composeByArray(T[] source, String delim) {
		return composeByArray(source, delim, null);
	}

	/**
	 * 將字串陣列以分隔符號來組合, 並用引號包住, 如(使用雙引號): "computer","desk","room"
	 * 
	 * @param sources 字串陣列
	 * @param delim 分隔符號
	 * @param quote 引號
	 * @return
	 */
	public static <T> String composeByArray(T[] sources, String delim, String quote) {
		boolean isAddQuote = false;

		if (sources == null)
			return null;

		if (sources.length == 0)
			return "";

		if (delim == null)
			throw new IllegalArgumentException("delim is null");

		if (quote != null)
			isAddQuote = true;

		StringBuffer buffer = new StringBuffer();

		if (isAddQuote)
			buffer.append(quote);

		buffer.append(sources[0].toString());

		if (isAddQuote)
			buffer.append(quote);

		for (int i = 1; i < sources.length; i++) {
			buffer.append(delim);

			if (isAddQuote)
				buffer.append(quote);

			buffer.append(sources[i].toString());

			if (isAddQuote)
				buffer.append(quote);
		}

		return buffer.toString();
	}


	public static String getSexString(String value) {
		if (StringUtils.isEmpty(value))
			return "";

		if (value.equals("M"))
			return "男";
		else if (value.equals("F"))
			return "女";
		else
			return "錯誤";
	}

	/**
	 * get the string
	 * 
	 * @author Grandy
	 * @param value source
	 * @return when a value is null or empty, it will return null.Other will return the string included "%".
	 */
	public static String getStringForLike(String value) {
		if (StringUtils.isBlank(value))
			return null;

		return "%" + value + "%";
	}

	/**
	 * 在字串前後加 %
	 * 
	 * @param value a source value
	 * @return a string with "%", if value is blank, will return null
	 */
	public static String getStringWithLike(String value) {
		if (StringUtils.isBlank(value))
			return null;

		return "%" + value + "%";
	}

	/**
	 * indexOf() 加強版,不檢查被引號括起來的字串, ex:<br>
	 * 1. source = "abc'de'fg", chekcValue = "d", return false<br>
	 * 2. source = "abcdefg", checkValue = "d", return true
	 * 
	 * @param source
	 * @param checkValue
	 * @param quote
	 * @return
	 */
	public static int indexOfExcludeQuote(String source, String checkValue, char quote) {
		if (source == null)
			throw new IllegalArgumentException("source is null");

		if (checkValue == null)
			throw new IllegalArgumentException("source is null");

		String strTemp = removeQuoteString(source, quote);

		int idx = strTemp.indexOf(checkValue);

		if (idx != -1)
			return idx + (source.length() - strTemp.length());
		else
			return -1;
	}

	/**
	 * 檢查字串是否為<code>c</cdoe> 字元所組成
	 * 
	 * @param source
	 * @param c
	 * @return
	 */
	public static boolean isAllSameChars(String source, char c) {
		int length = source.length();

		for (int i = 0; i < length; i++)
			if (source.charAt(i) != c)
				return false;

		return true;
	}

	/**
	 * 檢查字串的每個字元，是否為數字 0 到 9<br>
	 * 如: "520" return true<br>
	 * "5A0" return false
	 * 
	 * @param str 要檢查的字串
	 * @return false 表字串含有非數字 0 到 9 的字元
	 */
	public static boolean isDigital(String str) {
		if (str == null)
			throw new IllegalArgumentException("str is null");

		if (str.length() == 0) {
			return false;
		}

		int lenghtoOfStr = str.length();

		for (int i = 0; i < lenghtoOfStr; i++) {
			if (str.charAt(i) < '0' || str.charAt(i) > '9') {
				return false;
			}
		}

		return true;
	}

	/**
	 * 檢查字串是否都為英文字母
	 * 
	 * @param str 要檢查的字串
	 * @return 如果是, 傳回 true, 否則傳 false
	 */
	public static boolean isLetter(String str) {
		if (str == null)
			throw new IllegalArgumentException("str is null");

		char ch;

		str = str.toUpperCase();

		int length = str.length();

		for (int i = 0; i < length; i++) {
			ch = str.charAt(i);
			if (ch < 'A' || ch > 'Z') {
				return false;
			}
		}

		return true;
	}

	/**
	 * 
	 * @author TonyChou
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return org.apache.commons.lang.StringUtils.isNotBlank(str);
	}

	/**
	 * 在字串前或後補上空白
	 * 
	 * @param source 要補空白的字串
	 * @param length 補完空白之後的字串長度
	 * @param pendingDirection 補上字元的位置(前方或後方)
	 * @return 已補完空白的字串
	 */
	public static String pendingSpace(String source, int length, int pendingDirection) {
		return pendingString(source, length, ' ', pendingDirection);
	}

	/**
	 * 在字串前或後補上指定的字元
	 * 
	 * @param source 要補指定字元的字串
	 * @param length 補完指定字元的字串的長度
	 * @param pendingValue 指定字元,長度要為 1
	 * @param pendingDirection 補上字元的位置(前方或後方)
	 * @return 已補上字元的字串
	 */
	public static String pendingString(String source, int length, char pendingValue, int pendingDirection) {
		if (source == null)
			throw new IllegalArgumentException("source is null");

		if (length < 0)
			throw new IllegalArgumentException("the value of the length less than and equal to 0");

		if (!(pendingDirection == DirectionConstants.FRONT || pendingDirection == DirectionConstants.BACK))
			throw new IllegalArgumentException("the value of the direction is error");

		int sourceLength = source.length();

		if (length < sourceLength)
			return source;

		for (int i = 0; i < length - sourceLength; i++) {
			if (pendingDirection == DirectionConstants.FRONT)
				source = pendingValue + source;
			else
				source += pendingValue;
		}

		return source;
	}

	/**
	 * 在字串前或後補上 zero
	 * 
	 * @param str 要補 zero 的字串
	 * @param length 補完 zero 之後的字串長度
	 * @param pendingDirection 補上字元的位置(前方或後方)
	 * @return 已補完 zero 的字串
	 */
	public static String pendingZero(String str, int length, int pendingDirection) {
		return pendingString(str, length, '0', pendingDirection);
	}

	/**
	 * when flag = "Y", 才會顯示 value, 不然就會顯示 "***"
	 * 
	 * @author Grandy
	 * @param flag
	 * @param value
	 * @return
	 */
	public static String processShowOrHide(String flag, String value) {
		if (flag == null || flag.equals("N"))
			return "***";

		return value;
	}

	/**
	 * 移除被<code>quote</code> 包起來的字串, 如 123'ab'45'cd' 變為 12345
	 * 
	 * @param source
	 * @param quote
	 * @return
	 */
	public static String removeQuoteString(String source, char quote) {
		StringBuffer buffer = new StringBuffer(source);

		int index = -1;

		while ((index = buffer.indexOf(String.valueOf(quote), index + 1)) != -1) {
			int secondIndex = buffer.indexOf(String.valueOf(quote), index + 1);

			if (secondIndex != -1)
				buffer.delete(index, secondIndex + 1);
		}

		return buffer.toString();
	}

	/**
	 * 將字串倒過來,在這裏間隔符號使用 " "
	 * 
	 * @param source 字串來源, ex:"This is a test"
	 * @return reverse 後的字串, ex:"test a is This"
	 */
	public static String reverse(String source) {
		return reverse(source, " ");
	}

	/**
	 * 將字串倒過來,在這裏間隔符號使用指定的符號 <code>delim</code>
	 * 
	 * @param source 字串來源, ex:"This<delim>is<delim>a<delim>test"
	 * @param delim 指定的間隔符號
	 * @return reverse 後的字串, ex:"test<delim>a<delim>is<delim>This"
	 */
	public static String reverse(String source, String delim) {
		if (source == null)
			throw new IllegalArgumentException("source is null");

		if (source.trim().length() == 0)
			return "";

		if (delim == null || delim.length() == 0)
			throw new IllegalArgumentException("The argument of the delim is error");

		Stack stack = new Stack();

		StringTokenizer tokenizer = new StringTokenizer(source, delim);

		while (tokenizer.hasMoreTokens()) {
			stack.push(tokenizer.nextToken());
		}

		// 因為 stack 至少會有一個 element
		StringBuffer buffer = new StringBuffer((String) stack.pop());

		while (!stack.empty()) {
			buffer.append(delim);
			buffer.append((String) stack.pop());
		}

		return buffer.toString();
	}

	/**
	 * 分離字串, 如: source="t123tt45ttt6'tt'78tt, delim="t"<br>
	 * 1. if isIncludeDelim is true: return { "t", "123", "tt", "45", "ttt", "6'tt'78'", "tt" }<br>
	 * 2. if isIncludeDelim is false: return { "123", 45", "6'tt'78'" }
	 * 
	 * @param source
	 * @param delim
	 * @return
	 */
	public static String[] split(String source, char delim, boolean isIncludeDelim) {
		if (source == null)
			throw new IllegalArgumentException("source is null");

		List<String> list = new ArrayList<String>();

		int length = source.length();

		StringBuffer buffer = new StringBuffer();

		boolean isQuoteScope = false;

		String str;

		String nextStr;

		for (int i = 0; i < length; i++) {
			str = source.substring(i, i + 1);

			buffer.append(str);

			if ((i + 2) > length)
				nextStr = null;
			else
				nextStr = source.substring(i + 1, i + 2);

			if (str.equals("'"))
				isQuoteScope ^= true;

			// t12tt34'tt'tt
			// t->1: trigger
			// 2->t: trigger
			// t->3: trigger
			// '->t: triger
			if (nextStr != null && !isQuoteScope) {
				if ((str.equals(String.valueOf(delim)) && !nextStr.equals(String.valueOf(delim)))
						|| (!str.equals(String.valueOf(delim)) && nextStr.equals(String.valueOf(delim))))
					trigger(buffer, delim, list, isIncludeDelim);
			}
		}

		if (buffer.length() != 0)
			trigger(buffer, delim, list, isIncludeDelim);

		return list.toArray(new String[0]);
	}

	/**
	 * <pre>
	 * 將阿拉伯數字轉為中文數字, 如: 12==> 十二,  56==> 五十六
	 * 目前只顯示到 99
	 * </pre>
	 * 
	 * @param number
	 * @return
	 */
	public static String transferCipherToChinses(int number) {
		String[] chineseIndex = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };

		if (number <= 10)
			return chineseIndex[number - 1];
		else if (number < 20)
			return chineseIndex[9] + chineseIndex[number % 10 - 1];
		else {
			if (number % 10 == 0)
				return chineseIndex[number / 10 - 1] + chineseIndex[9];
			else
				return chineseIndex[number / 10 - 1] + chineseIndex[9] + chineseIndex[number % 10 - 1];
		}
	}

	/**
	 * 
	 * @author Grandy
	 * @param str
	 * @return
	 */
	public static String trimToEmpty(String str) {
		return org.apache.commons.lang.StringUtils.trimToEmpty(str);
	}
	
	/**
	 * 由 request.getRequestURI() and request.getQueryString() 來組合 uri with parameter
	 * 
	 * @param uri
	 * @param queryString
	 * @return
	 */
	public static String getUriWithParameter( String uri, String queryString )
	{
		if( StringUtils.isEmpty( queryString ) )
			return uri;

		if( uri.endsWith( "/" ) )
			uri = uri.substring( 0, uri.length() - 1 );

		return uri + "?" + queryString;
	}

	/**
	 * 參考:{@link #getUriWithParameter(HttpSession, String, String[], String[])}
	 * 
	 * @param uri
	 * @param parameterName
	 * @param parameterValue
	 * @return
	 */
	public static String getUriWithParameter( String uri, String parameterName, String parameterValue )
	{
		Validate.notEmpty( parameterName );
		Validate.notEmpty( parameterValue );

		return getUriWithParameter( uri, new String[]{ parameterName }, new String[]{ parameterValue } );
	}

	/**
	 * <pre>
	 * 取得含有參數的 uri, ex: /e885/test?name=grandy&sex=m
	 * </pre>
	 * 
	 * @param uri 可接受已含有 paramter 的 uri, ex: /e885/test?name=grandy, or /e885/test
	 * @param parameterNames 參數名稱
	 * @param parameterValues 值
	 * @return
	 */
	public static String getUriWithParameter( String uri, String[] parameterNames, String[] parameterValues )
	{
		Validate.notEmpty( parameterNames );
		Validate.notEmpty( parameterValues );
		Validate.isTrue( parameterNames.length == parameterValues.length );

		if( uri.endsWith( "/" ) )
			uri = uri.substring( 0, uri.length() - 1 );

		StringBuilder builder = new StringBuilder();
		builder.append( uri );

		if( uri.indexOf( "?" ) == -1 )
			builder.append( "?" );
		else
			builder.append( "&" );

		builder.append( parameterNames[ 0 ] );
		builder.append( "=" );
		builder.append( parameterValues[ 0 ] );

		for( int i = 1; i < parameterNames.length; i++ )
		{
			builder.append( "&" );
			builder.append( parameterNames[ i ] );
			builder.append( "=" );
			builder.append( parameterValues[ i ] );
		}

		return builder.toString();
	}

	/**
	 * 以 isIncludeDeim 和 mehtod isAllSameChars() 來決定是否把 buffe 的內容加到 list.
	 * 
	 * @param buffer
	 * @param delim
	 * @param list
	 * @param isIncludeDelim
	 */
	private static void trigger(StringBuffer buffer, char delim, List<String> list, boolean isIncludeDelim) {
		if (isIncludeDelim || !isAllSameChars(buffer.toString(), delim))
			list.add(buffer.toString());

		buffer.delete(0, buffer.length());
	}
}
