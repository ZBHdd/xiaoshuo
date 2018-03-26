package com.example.demo;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

public class Utils {

    /**
     * md5加密
     *
     * @param src
     * @return
     */
    public static String md5Hex(String pwd, String src) {
        byte[] bs = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bs = md5.digest((pwd + src).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(new Hex().encode(bs));
    }
}
