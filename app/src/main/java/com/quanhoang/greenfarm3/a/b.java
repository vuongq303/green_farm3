package com.quanhoang.greenfarm3.a;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: compiled from: Encrypter.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    private static String enrypt_key = getValue();

    public static String crypt(String str) {
        byte[] bArrDoFinal = null;
        try {
            String strPadString = PadString(str);
            SecretKeySpec secretKeySpec = new SecretKeySpec(a.decodeLines(enrypt_key), "ECB");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, secretKeySpec);
            bArrDoFinal = cipher.doFinal(strPadString.getBytes());
        } catch (Exception e) {
        }
        return new String(a.encode(bArrDoFinal));
    }

    public static String PadString(String str) {
        int length = str.length() % 16;
        int i = 16 - length;
        if (i > 0 && i < 16) {
            StringBuffer stringBuffer = new StringBuffer(i + str.length());
            stringBuffer.insert(0, str);
            for (int i2 = 16 - length; i2 > 0; i2--) {
                stringBuffer.append(" ");
            }
            return stringBuffer.toString();
        }
        return str;
    }

    private static String getValue() {
        byte[] bArr = new byte[25];
        byte[] bArr2 = {-48, -37, 4, 31, -39, -27, 52, -36, 41, 13, -32, 32, -35, 20, 5, 11, -8, -33, -5, -9, 5, -6, -4, 0, 0};
        bArr[0] = 118;
        for (int i = 1; i < 24; i++) {
            bArr[i] = (byte) (bArr[i - 1] + bArr2[i]);
        }
        return new String(bArr, 0, 24);
    }
}
