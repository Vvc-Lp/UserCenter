package com.vvc.user_center.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 加密算法
 *
 * @author Vvc
 */
public class PasswordEncryptor {
    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // handle exception
        }
        return null;
    }
}
