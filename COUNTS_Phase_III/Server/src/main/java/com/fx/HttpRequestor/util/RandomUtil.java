package com.fx.HttpRequestor.util;

import java.util.Random;

/**
 * 用于生成随机数或随机字符串的工具
 * @author guoweiwei gww0426@163.com
 *
 */
public class RandomUtil {
	
	private static char[] randomStrChar = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2',
        '3', '4', '5', '6', '7', '8', '9', '0' };
	
	private static char[] randomNumberChar = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

	private static Random r = new Random();
	
	/**
	 * 生成随机字符串
	 * @param length 随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateRandomString(int length) {
	    StringBuilder sb = new StringBuilder();
	    while (length-- > 0) {
	        sb.append(randomStrChar[r.nextInt(randomStrChar.length)]);
	    }
	    return sb.toString();
	}
	
	/**
	 * 生成随机数
	 * @param length 随机数长度
	 * @return 随机数字符串
	 */
	public static String generateRandomNumberStr(int length) {
	    StringBuilder sb = new StringBuilder();
	    while (length-- > 0) {
	        sb.append(randomNumberChar[r.nextInt(randomNumberChar.length)]);
	    }
	    return sb.toString();
	}
	
	/**
	 * 生成随机数
	 * @param length  随机数长度
	 * @return 随机数
	 */
	public static int generateRandomInteger(int length) {
	    return Integer.valueOf(generateRandomNumberStr(length));
	}
	
	/**
	 * 生成随机数
	 * @param length  随机数长度
	 * @return 随机数
	 */
	public static long generateRandomLong(int length) {
	    return Long.valueOf(generateRandomNumberStr(length));
	}

}
