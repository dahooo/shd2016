package com.shd.utils;

import org.apache.commons.lang.StringUtils;

import com.shd.constants.CarryConstants;

public class ImLongUtils {

	/**
	 * Get an instance of Long by a string
	 * 
	 * @param value a string
	 * @return an instance of Long, but when a string is blank, return null
	 */
	public static Long getLong(String value) {
		if (StringUtils.isBlank(value))
			return null;

		return new Long(value);
	}

	/**
	 * "12,34,56" ==> 會得到三個 Long 的 instance
	 * 
	 * @param value a string
	 * @param delim 分隔符號
	 * @return
	 */
	public static Long[] getLongArray(String value, String delim) {
		if (StringUtils.isBlank(value))
			return new Long[0];

		String[] values = value.split(delim);

		Long[] longs = new Long[values.length];

		for (int i = 0; i < values.length; i++)
			longs[i] = getLong(values[i]);

		return longs;
	}

	/**
	 * Get a value of long primitve by a String
	 * 
	 * @param value a string
	 * @return a value of long primitvie, But when a string is blank, return 0
	 */
	public static long getLongPrimitive(String value) {
		if (StringUtils.isBlank(value))
			return 0;

		return Long.parseLong(value);
	}

	/**
	 * Get a value of long primitve by an Integer
	 * 
	 * @param obj an instance of Long
	 * @return a value of long primitve, But when an instance of Long is null, return 0
	 */
	public static long longValue(Long obj) {
		if (obj == null)
			return 0;

		return obj.longValue();
	}

	/**
	 * Parse String to be Long.<br>
	 * If the instance of the String(source) is null or its length is zero, we will return null.
	 * 
	 * @param source to be parsed
	 * @param carry carry rule, reference:{@link com.Im.constant.util.CarryConstants}
	 * @return a Long
	 * @throws ImException can't pase string to be Long
	 */
	public static Long parseString2Long(String source, int carry) throws Exception {
		if (source == null || source.length() == 0)
			return null;

		if (!(carry == CarryConstants.ROUND_OFF || carry == CarryConstants.UNCONDITIONAL_CARRY || carry == CarryConstants.UNCONDITIONAL_DISCARD))
			throw new IllegalArgumentException("carry is error");

		try {
			if (source.indexOf(".") != -1) {
				long intTemp = 0;

				double d = Double.parseDouble(source);

				if (carry == CarryConstants.UNCONDITIONAL_DISCARD)
					intTemp = (long) d;
				else if (carry == CarryConstants.UNCONDITIONAL_CARRY)
					intTemp = (long) d + 1;
				else
					intTemp = (long) (d + 0.5);

				return new Long(intTemp);
			}

			return new Long(source);
		} catch (NumberFormatException e) {
			throw new Exception("Can't parse string to be Long:" + source);
		}
	}

	/**
	 * Parse String to be primitive long.<br>
	 * If the instance of the String(source) is null or its length is zero, we will return zero.
	 * 
	 * @param source to be parsed
	 * @param carry carry rule, {@link com.Im.constant.util.CarryConstants}
	 * @return a primitive long
	 * @throws ImException can't pase source to be primitive long
	 */
	public static long parseString2Primitive(String source, int carry) throws Exception {
		return longValue(parseString2Long(source, carry));
	}
}
