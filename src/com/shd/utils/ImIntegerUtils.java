package com.shd.utils;

import org.apache.commons.lang.StringUtils;

import com.shd.constants.CarryConstants;

public class ImIntegerUtils {

	/**
	 * Get an instance of Integer by a string
	 * 
	 * @param value a string
	 * @return an instance of Integer, but when a string is blank, return null
	 */
	public static Integer getInteger(String value) {
		if (StringUtils.isBlank(value))
			return null;

		return new Integer(value);
	}

	/**
	 * Get a value of int primitve by a String
	 * 
	 * @param value a string
	 * @return a value of int primitvie, But when a string is blank, return 0
	 */
	public static int getIntPrimitive(String value) {
		if (StringUtils.isBlank(value))
			return 0;

		return Integer.parseInt(value);
	}

	/**
	 * Get a value of int primitve by an Integer
	 * 
	 * @param obj an instance of Integer
	 * @return a value of int primitve, But when an instance of Integer is null, return 0
	 */
	public static int intValue(Integer obj) {
		if (obj == null)
			return 0;

		return obj.intValue();
	}

	/**
	 * Parse String to be Integer.<br>
	 * If the instance of the String(source) is null or its length is zero, we will return null.
	 * 
	 * @param source to be parsed
	 * @param carry carry rule, reference:{@link com.Im.constant.util.CarryConstants}
	 * @return an Integer
	 * @throws ImException can't pase string to be Integer
	 */
	public static Integer parseString2Integer(String source, int carry) throws Exception {
		if (source == null || source.length() == 0)
			return null;

		if (!(carry == CarryConstants.ROUND_OFF || carry == CarryConstants.UNCONDITIONAL_CARRY || carry == CarryConstants.UNCONDITIONAL_DISCARD))
			throw new IllegalArgumentException("carry is error");

		try {
			if (source.indexOf(".") != -1) {
				int intTemp = 0;

				double d = Double.parseDouble(source);

				if (carry == CarryConstants.UNCONDITIONAL_DISCARD)
					intTemp = (int) d;
				else if (carry == CarryConstants.UNCONDITIONAL_CARRY)
					intTemp = (int) d + 1;
				else
					intTemp = (int) (d + 0.5);

				return new Integer(intTemp);
			}

			return new Integer(source);
		} catch (NumberFormatException e) {
			throw new Exception("Can't parse string to be Integer:" + source);
		}
	}

	/**
	 * Parse String to be primitive int.<br>
	 * If the instance of the String(source) is null or its length is zero, we will return zero.
	 * 
	 * @param source to be parsed
	 * @param carry carry rule, {@link com.Im.constant.util.CarryConstants}
	 * @return a primitive int
	 * @throws ImException can't pase source to be primitive int
	 */
	public static int parseString2Primitive(String source, int carry) throws Exception {
		return intValue(parseString2Integer(source, carry));
	}
}
