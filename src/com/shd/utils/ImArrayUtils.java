package com.shd.utils;

import static com.shd.constants.OrderConstants.ASCEND;
import static com.shd.constants.OrderConstants.DESCEND;

import java.util.Arrays;

public class ImArrayUtils {

	/**
	 * reverse array
	 * 
	 * @param sources
	 */
	public static void reverse(int[] sources) {
		if (sources == null)
			throw new IllegalArgumentException("sources is null");

		int[] copies = sources.clone();

		int length = copies.length;

		for (int i = 0; i < length; i++)
			sources[i] = copies[length - i - 1];
	}

	/**
	 * array serach<br>
	 * 因為 {@link java.util.Arrays#binarySearch(int[], int)} 其傳進去的陣列的要排序(API 有說明), <br>
	 * 所以這個 method 就是解決這個問題
	 * 
	 * @param source 被尋找的陣列
	 * @param key 要尋找的 key value
	 * @return 如果找到, 傳回找到的位置, 如果找不到, 傳回 -1
	 */
	public static int serach(int[] source, int key) {
		if (source == null)
			throw new IllegalArgumentException("source is null");

		int length = source.length;

		for (int i = 0; i < length; i++)
			if (source[i] == key)
				return i;

		// not found
		return -1;
	}

	/**
	 * int 陣列做排序, 可由大至小, 或由小至大
	 * 
	 * @param sources int 陣列
	 * @param order ref com.e885.constant.util.OrderConstants
	 */
	public static void sort(int[] sources, String order) {
		if (sources == null)
			throw new IllegalArgumentException("source is null");

		if (order == null)
			throw new IllegalArgumentException("order is null");

		if (!(order.equals(ASCEND) || order.equals(DESCEND)))
			throw new IllegalArgumentException("the value of the order is error");

		// order by ascending
		Arrays.sort(sources);

		if (order.equals(DESCEND))
			reverse(sources);
	}
}
