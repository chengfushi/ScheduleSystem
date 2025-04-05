package com.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author Cheng fu
 * @Date 2025/4/5 22:18
 */
public final class MD5Util {
    public static String encrypt(String strSrc) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = strSrc.getBytes();
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            char[] hexChars = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！！");
        }
    }
}
