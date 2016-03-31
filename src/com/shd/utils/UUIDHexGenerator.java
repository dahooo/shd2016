package com.shd.utils;

import java.net.InetAddress;

public class UUIDHexGenerator {

	private static final int ip;

	private static final int jvm = (int) (System.currentTimeMillis() >>> 8);

	private static short counter = (short) 0;

	static {
		int ipadd;

		try {
			ipadd = bytesArray2Int(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}

		ip = ipadd;
	}

	private static int bytesArray2Int(byte[] bytes) {
		int result = 0;

		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	private String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	private String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	/**
	 * 產生 uuid
	 * 
	 * @return uuid
	 */
	public String generate() {
		return new StringBuffer().append(format(getIp())).append(format(getJvm())).append(format(getHiTime())).append(format(getLoTime())).append(format(getCount()))
				.toString();
	}

	/**
	 * 亂數取得密碼
	 * 
	 * @return 取得密碼
	 */
	public String generateRandomPassword() {
		return new StringBuffer().append(format(getLoTime())).toString();
	}

	private short getCount() {
		synchronized (UUIDHexGenerator.class) {
			if (counter < 0)
				counter = 0;

			return counter++;
		}
	}

	private short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	private int getIp() {
		return ip;
	}

	private int getJvm() {
		return jvm;
	}

	private int getLoTime() {
		return (int) System.currentTimeMillis();
	}
}
