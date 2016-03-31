package com.shd.utils;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.apache.commons.lang.Validate;

import com.shd.constants.DirectionConstants;

public class ImDateFormat extends SimpleDateFormat {

	private String pattern;

	private Calendar calendar = Calendar.getInstance();

	private Vector<String> patternSegment = new Vector<String>();

	/**
	 * @param pattern
	 */
	public ImDateFormat(String pattern) {
		this.pattern = pattern;

		segmentPattern();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.text.SimpleDateFormat#applyPattern(java.lang.String)
	 */
	@Override
	public void applyPattern(String pattern) {
		this.pattern = pattern;

		segmentPattern();
	}

	/**
	 * 格式化日期, 如果是使用 ROC 規格, 請用小寫 't', 或大寫 'T'<br>
	 * 小寫 t, 沒有三位數字的年份, 不會補 0<br>
	 * 大寫 T, 會補 0<br>
	 * 
	 * @param date
	 * @return
	 */
	public String formatDate(Date date) {
		Validate.notNull(date, "date is null");

		if (isIncludeROCPattern())
			return formatForROCPattern(date);
		else {
			super.applyPattern(pattern);
			return format(date);
		}
	}

	/**
	 * 格式化民國年的 pattern
	 * 
	 * @return
	 */
	private String formatForROCPattern(Date date) {
		StringBuffer buffer = new StringBuffer();

		calendar.setTime(date);

		SimpleDateFormat dateFormat = new SimpleDateFormat();
		DecimalFormat decimalFormat = new DecimalFormat();

		// 先分解小寫 "t"
		String[] patternsForLowercase = ImStringUtils.split(pattern, 't', true);

		for (int i = 0; i < patternsForLowercase.length; i++) {
			// 再分解大寫 "t"
			String[] patternsForUppercase = ImStringUtils.split(patternsForLowercase[i], 'T', true);

			for (int j = 0; j < patternsForUppercase.length; j++) {
				String temp = patternsForUppercase[j];

				// 有含中華民國年的 pattern
				if (ImStringUtils.indexOfExcludeQuote(temp, "T", '\'') != -1 || ImStringUtils.indexOfExcludeQuote(temp, "t", '\'') != -1) {
					int year = calendar.get(Calendar.YEAR) - 1911;

					// 民國沒有0年, 所以 1911: -1 年, 1910: -2 年, 以此類推
					if (year <= 0) {
						year -= 1;

						// 檢查前二個字元是否含有"民國"字串, 如果有, 當年份小於0時, 改為 "前XX"
						if (buffer.length() >= 2 && buffer.substring(buffer.length() - 2, buffer.length()).equals("民國")) {
							year = 0 - year;
							buffer.append("前");
						}
					}

					char decimalFormatChar;

					// 處理大寫
					if (temp.indexOf("T") != -1)
						decimalFormatChar = '0';
					else
						decimalFormatChar = '#';

					decimalFormat.applyPattern(ImStringUtils.pendingString(String.valueOf(decimalFormatChar), temp.length(), decimalFormatChar,
							DirectionConstants.FRONT));

					buffer.append(decimalFormat.format(year));

				} else {
					dateFormat.applyPattern(temp);
					buffer.append(dateFormat.format(date));
				}
			}
		}

		return buffer.toString();
	}

	/**
	 * 檢查是否為中華民國年的 pattern<br>
	 * patter 中有小寫 t, 或大寫的 T, 但不包含單引號內的字串, 即為中華民國年的 pattern
	 * 
	 * @return
	 */
	public boolean isIncludeROCPattern() {
		// 先去除單引號位置
		String temp = ImStringUtils.removeQuoteString(pattern, '\'');

		if (temp.indexOf("t") != -1 || temp.indexOf("T") != -1)
			return true;
		else
			return false;
	}

	public Date parse(String text, ParsePosition pos) {
		String parsedText = text;
		String parsedPattern = "";
		int size = patternSegment.size();

		for (int i = 0; i < size; i = i + 2) {
			String sp = (String) patternSegment.elementAt(i);
			String word = (String) patternSegment.elementAt(i + 1);
			parsedPattern += sp;
			if (parsedText.indexOf(word) == -1) {
				throw new RuntimeException("Illegal Text:" + text);
			}
			parsedText = parsedText.replaceFirst(word, "");
		}

		boolean rocHit = false;

		int rocDifference = 1911;

		if (parsedPattern.indexOf("TTT") != -1) {
			rocHit = true;
			parsedPattern = parsedPattern.replaceAll("TTT", "yyy");
		}
		if (parsedPattern.indexOf("tt") != -1) {
			rocHit = true;
			parsedPattern = parsedPattern.replaceAll("tt", "yy");
			rocDifference = 11;
		}

		SimpleDateFormat format = new SimpleDateFormat(parsedPattern);
		Date parsedDate = format.parse(parsedText, pos);

		if (rocHit) {
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(parsedDate);
			cal.add(Calendar.YEAR, rocDifference);
			parsedDate = cal.getTime();
		}

		return parsedDate;
	}

	private void segmentPattern() {
		patternSegment.clear();

		int index1;
		int index2;
		int previousIndex = 0;
		while ((index1 = pattern.indexOf("'", previousIndex)) != -1) {
			index2 = pattern.indexOf("'", index1 + 1);
			if (index2 == -1) {
				throw new RuntimeException("Illegal Pattern:" + pattern);
			}
			String prePattern = pattern.substring(previousIndex, index1);

			String staticWord = pattern.substring(index1 + 1, index2);
			previousIndex = index2 + 1;
			patternSegment.addElement(prePattern);
			patternSegment.addElement(staticWord);

		}

		String prePattern = pattern.substring(previousIndex, pattern.length());
		String staticWord = "";
		patternSegment.addElement(prePattern);
		patternSegment.addElement(staticWord);
	}
}
