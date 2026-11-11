package com.gameloft.android2d.iap.utils;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;
import javax.crypto.Cipher;

/* JADX INFO: compiled from: CRMUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    public static String Sy() {
        Random random = new Random();
        String str = "";
        for (int i = 0; i < 64; i++) {
            str = str + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-._~".charAt(Math.abs(random.nextInt()) % "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-._~".length());
        }
        return str;
    }

    public static String ac(String str, String str2) {
        try {
            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(com.gameloft.android2d.iap.a.a.a.a.decode(str2.trim().getBytes("utf-8"))));
            byte[] bArrDecode = com.gameloft.android2d.iap.a.a.a.a.decode(str);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(2, publicKeyGeneratePublic);
            return new String(cipher.doFinal(bArrDecode));
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean l(String str, String str2, String str3, String str4) {
        return v.gd(new StringBuilder().append(str).append(str2).toString()).equals(ac(str3, str4));
    }

    public static String fz(String str) {
        String str2 = new String();
        if (str == null) {
            return "";
        }
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (('0' <= cCharAt && cCharAt <= '9') || (('A' <= cCharAt && cCharAt <= 'Z') || (('a' <= cCharAt && cCharAt <= 'z') || cCharAt == '-' || cCharAt == '.' || cCharAt == '_' || cCharAt == '~'))) {
                str2 = str2 + cCharAt;
            } else {
                char[] cArrK = k(cCharAt);
                str2 = ((str2 + "%") + cArrK[0]) + cArrK[1];
            }
        }
        return str2;
    }

    public static char[] k(char c) {
        char[] cArr = {(char) ((c & 240) >> 4), (char) (c & 15)};
        if (cArr[0] >= 0 && cArr[0] <= '\t') {
            cArr[0] = (char) (cArr[0] + '0');
        }
        if ('\n' <= cArr[0] && cArr[0] <= 15) {
            cArr[0] = (char) (cArr[0] + '7');
        }
        if (cArr[1] >= 0 && cArr[1] <= '\t') {
            cArr[1] = (char) (cArr[1] + '0');
        }
        if ('\n' <= cArr[1] && cArr[1] <= 15) {
            cArr[1] = (char) (cArr[1] + '7');
        }
        return cArr;
    }
}
