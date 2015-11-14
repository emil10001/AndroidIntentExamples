package io.ejf.intentexamples.utils;

import java.security.MessageDigest;

/**
 * Created by ejf3 on 11/14/15.
 */
public class Hasher {
    public static String hashIt(String sIn) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.reset();
            byte[] bytes = md.digest(sIn.getBytes());
            return bytesToString(bytes);
        } catch (Exception ex) {
            return null;
        }
    }

    private static String bytesToString(byte[] bytes) {
        StringBuffer retVal = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            // Integer.toHexString cuts off leading zeros. grrr!
            String hex = Integer.toHexString(0xFF & bytes[i]);
            // Special thanks to SO for a fix here
            // http://stackoverflow.com/questions/332079/in-java-how-do-i-convert-a-byte-array-to-a-string-of-hex-digits-while-keeping-l
            if (1 == hex.length()){
                retVal.append('0');
            }
            retVal.append(hex);
        }
        return retVal.toString();
    }


}
