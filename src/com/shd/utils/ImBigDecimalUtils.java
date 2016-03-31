package com.shd.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

public class ImBigDecimalUtils {

	private static String pattern = "#,###.##";

	private static String patternR4 = "#,###.####";
	
	private static String patternR6 = "#,###.######";

	private static int defaultScale = 2;

	public static BigDecimal getBigDecimal(BigDecimal value) {
		if (value == null)
			return new BigDecimal(0);

		return value;
	}

	public static BigDecimal getBigDecimal(String value) {
		if (StringUtils.isBlank(value))
			return null;

		return new BigDecimal(value);
	}

	public static BigDecimal getBigDecimal(Double value) {
		if (value == null)
			return null;

		return new BigDecimal(value.doubleValue());
	}

	public static String format(BigDecimal value) {
		return format(value, false);
	}

	public static String format(BigDecimal value, boolean isReturnZero) {
		if (value == null) {
			if (isReturnZero)
				return "0";
			else
				return "";
		}

		return new DecimalFormat(pattern).format(value.doubleValue());
	}

	/**
	 * 數字格式至小數點後四位 ( #,###.#### )
	 * 
	 * @param value
	 */
	public static String formatR4(BigDecimal value) {
		return formatR4(value, false);
	}
	
	
	/**
	 * 數字格式至小數點後六位 ( #,###.###### )
	 * 
	 * @param value
	 */
	public static String formatR6(BigDecimal value) {
		return formatR6(value, false);
	}

	/**
	 * 數字格式至小數點後四位 ( #,###.#### )
	 * 
	 * @param value
	 * @param isReturnZero 為 true 時, 傳入數字為 null 時會傳 0, 反之回傳空白
	 */
	public static String formatR4(BigDecimal value, boolean isReturnZero) {
		if (value == null) {
			if (isReturnZero)
				return "0";
			else
				return "";
		}

		return new DecimalFormat(patternR4).format(value.doubleValue());
	}
	
	/**
	 * 數字格式至小數點後六位 ( #,###.##### )
	 * 
	 * @param value
	 * @param isReturnZero 為 true 時, 傳入數字為 null 時會傳 0, 反之回傳空白
	 */
	public static String formatR6(BigDecimal value, boolean isReturnZero) {
		if (value == null) {
			if (isReturnZero)
				return "0";
			else
				return "";
		}

		return new DecimalFormat(patternR6).format(value.doubleValue());
	}
	

	/**
	 * 相加( value1 + value2 )
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static String addString(BigDecimal value1, BigDecimal value2) {
		BigDecimal value = add(value1, value2);

		if (value == null)
			return "";
		else
			return format(value);
	}

	/**
	 * 相加( value1 + value2 )
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
		return getBigDecimal(value1).add(getBigDecimal(value2));
	}

	/**
	 * 相減( value1 - value2 )
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static String subtractString(BigDecimal value1, BigDecimal value2) {
		BigDecimal value = subtract(value1, value2);

		if (value == null)
			return "";
		else
			return format(value);
	}

	/**
	 * 相減( value1 - value2 )
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static BigDecimal subtract(BigDecimal value1, BigDecimal value2) {
		return getBigDecimal(value1).subtract(getBigDecimal(value2));
	}

	/**
	 * 相乘( value1 * value2 )
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static String multiplyString(BigDecimal value1, BigDecimal value2) {
		BigDecimal value = multiply(value1, value2);

		if (value == null)
			return "";
		else
			return format(value);
	}

	/**
	 * 相乘( value1 * value2 )
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static BigDecimal multiply(BigDecimal value1, BigDecimal value2) {
		return getBigDecimal(value1).multiply(getBigDecimal(value2));
	}

	public static String divideString(BigDecimal value1, BigDecimal value2) {
		return divideString(value1, value2, defaultScale);
	}

	/**
	 * 相除( value1 / value2 )
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static String divideString(BigDecimal value1, BigDecimal value2, int scale) {
		BigDecimal value = divide(value1, value2, scale);

		if (value == null)
			return "";
		else
			return format(value);
	}

	/**
	 * 相除( value1 / value2 ) 預設小數第二位 四捨五入
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static BigDecimal divide(BigDecimal value1, BigDecimal value2) {
		return divide(value1, value2, defaultScale);
	}

	/**
	 * 相除( value1 / value2 )
	 * 
	 * @param value1
	 * @param value2
	 * @param scale
	 * @return
	 */
	public static BigDecimal divide(BigDecimal value1, BigDecimal value2, int scale) {
		Validate.isTrue(scale >= 0, "The value can't be less than 0!");

		if (value2 == null)
			return null;

		return getBigDecimal(value1).divide(value2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 小數位四捨五入
	 * 
	 * @param value 需要四捨五入的BigDecimal
	 * @param scale 小數點後保留幾位
	 * @return 四捨五入後的結果
	 */
	public static BigDecimal round(BigDecimal value, int scale) {
		Validate.isTrue(scale >= 0, "The value can't be less than 0!");

		if (value == null)
			return null;

		BigDecimal one = new BigDecimal("1");
		return value.divide(one, scale, BigDecimal.ROUND_HALF_UP);

	}

	/**
	 * 小數位無條件進位
	 * 
	 * @param value 需要無條件進位的BigDecimal
	 * @param scale 小數點後保留幾位
	 * @return 無條件進位後的結果
	 */
	public static BigDecimal ceil(BigDecimal value, int scale) {
		Validate.isTrue(scale >= 0, "The value can't be less than 0!");

		if (value == null)
			return null;

		BigDecimal one = new BigDecimal("1");
		return value.divide(one, scale, BigDecimal.ROUND_CEILING);

	}
	
	/**
	 * 將字串 "1,2,3" 轉成 BigDecimal Array 物件
	 * 
	 * @param value
	 * @param delim
	 * @return
	 */
	public static BigDecimal[] getBigDecimalArray(String value, String delim) {
		if (StringUtils.isBlank(value))
			return new BigDecimal[0];

		String[] strs = value.split(delim);
		BigDecimal[] bigDecimals = new BigDecimal[strs.length];

		for (int i = 0; i < strs.length; i++)
			bigDecimals[i] = new BigDecimal(strs[i]);

		return bigDecimals;
	}
}
