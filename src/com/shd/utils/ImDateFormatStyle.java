package com.shd.utils;

public class ImDateFormatStyle {

	/**
	 * 19740331010203
	 */
	public static String CENTURY_NUMBER_LONG = new String("yyyyMMddHHmmss");

	/**
	 * 19740331
	 */
	public static String CENTURY_NUMBER_SHORT = new String("yyyyMMdd");

	/**
	 * 西元1974年03月31日 01時02分03秒
	 */
	public static String CENTURY_PREFIX_STRING_LONG = new String("西元yyyy年MM月dd日 HH時mm分ss秒");

	/**
	 * 西元1974年03月31日
	 */
	public static String CENTURY_PREFIX_STRING_SHORT = new String("西元yyyy年MM月dd日");

	/**
	 * 1974-03-31 01:02:03
	 */
	public static String CENTURY_DASH_LONG = new String("yyyy-MM-dd HH:mm:ss");

	/**
	 * 1974-03-31
	 */
	public static String CENTURY_DASH_SHORT = new String("yyyy-MM-dd");
	
	/**
	 * 1974/03/31
	 */
	public static String CENTURY_SLASH_SHORT = new String("yyyy/MM/dd");

	/**
	 * 1974年03月31日 01時02分03秒
	 */
	public static String CENTURY_STRING_LONG = new String("yyyy年MM月dd日 HH時mm分ss秒");

	/**
	 * 1974年03月31日
	 */
	public static String CENTURY_STRING_SHORT = new String("yyyy年MM月dd日");

	/**
	 * 630331010203, or 1050331010203
	 */
	public static String ROC_NUMBER_LONG = new String("tttMMddHHmmss");

	/**
	 * 630331, or 1050331
	 */
	public static String ROC_NUMBER_SHORT = new String("tttMMdd");

	/**
	 * 民國63年03月31日 01時02分03秒, or 民國105年03月31日 01時02分03秒
	 */
	public static String ROC_PREFIX_STRING_LONG = new String("民國ttt年MM月dd日 HH時mm分ss秒");

	/**
	 * 63年03月31日, or 105年03月31日
	 */
	public static String ROC_PREFIX_STRING_SHORT = new String("民國ttt年MM月dd日");

	/**
	 * 63年03月31日 01時02分03秒, or 105年03月31日 01時02分03秒
	 */
	public static String ROC_STRING_LONG = new String("ttt年MM月dd日 HH時mm分ss秒");

	/**
	 * 63年03月31日, or 105年03月31日
	 */
	public static String ROC_STRING_SHORT = new String("ttt年MM月dd日");

	/**
	 * 63/03/31 01:02:03, 105/03/31 01:02:03
	 */
	public static String ROC_SLASH_LONG = new String("ttt/MM/dd HH:mm:ss");

	/**
	 * 63/03/31, 105/03/31
	 */
	public static String ROC_SLASH_SHORT = new String("ttt/MM/dd");
}
