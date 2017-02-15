package com.lujiachao.support.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cyril on 17-2-15.
 */
public class LjcStringUtils {
    private static final String chineseRegEx = "[\u4e00-\u9fa5]";
    private static final Pattern patChinese = Pattern.compile(chineseRegEx);

    public static String filterChinese(String text) {
        StringBuilder builder = new StringBuilder();
        char[] toCharArray = text.toCharArray();
        for (char c : toCharArray) {
            if (c >= '\u4e00' && c <= '\u9fa5') {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public static boolean containsChinese(String text) {
        Matcher matcher = patChinese.matcher(text);
        boolean hasChinese = false;
        if (matcher.find()) {
            hasChinese = true;
        }
        return hasChinese;
    }

    public static boolean isTrimEmpty(String str) {
        return str == null || str.isEmpty() || str.trim().isEmpty();
    }

    public static boolean isNotTrimEmpty(String str) {
        return !isTrimEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String getHexStringFromByteArray(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1) {
            throw new IllegalArgumentException("byteArray must not be null or empty");
        }

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10) {
                hexString.append("0");
            }
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }

    public static String getHexStringFromByteArray(byte[] byteArray, int offset, int length) {
        if (byteArray == null || byteArray.length < 1) {
            throw new IllegalArgumentException("byteArray must not be null or empty");
        }
        if (offset < 0 || offset > length || offset > byteArray.length || length > byteArray.length || length < 0) {
            throw new IllegalArgumentException("offset and length error");
        }
        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if ((byteArray[i + offset] & 0xff) < 0x10) {
                hexString.append("0");
            }
            hexString.append(Integer.toHexString(0xFF & byteArray[i + offset]));
        }
        return hexString.toString().toLowerCase();

    }

    public static byte[] getByteArrayFromHexString(String hexString) {
        if (isEmpty(hexString)) {
            throw new IllegalArgumentException("hexString must not be empty");
        }
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) Integer.parseInt(hexString.substring(2 * i, 2 * i + 2), 16);
            /*
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
             */
        }
        return byteArray;
    }

    public static Date getDateFromString(String text,String pattern){
        if (isEmpty(text)) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(text);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date getDefaultDateFromString(String text){
        return getDateFromString(text,"yyyy-MM-dd");
    }

    public static String getStringFromDate(Date date,String pattern) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String getDefaultStringFromDate(Date date) {
       return getStringFromDate(date,"yyyy-MM-dd");
    }


}
