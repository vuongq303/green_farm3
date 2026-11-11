package com.gameloft.android.wrapper;

import com.gameloft.glads.AndroidWebView;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: compiled from: DefReader.java */
/* JADX INFO: loaded from: classes.dex */
public class h {
    private static h bfu = null;
    private final String bfv = "AES";

    public static h Oz() {
        if (bfu == null) {
            bfu = new h();
        }
        return bfu;
    }

    public String T(String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(ed(str2), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, secretKeySpec);
            return new String(cipher.doFinal(c.decode(str)));
        } catch (Exception e) {
            String str3 = "getPlainDef err:" + e.toString();
            return null;
        }
    }

    private byte[] ed(String str) {
        return ee(str).getBytes();
    }

    private String ee(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            messageDigest.update(str.getBytes(AndroidWebView.UTF_8));
            byte[] bArrDigest = messageDigest.digest();
            for (byte b : bArrDigest) {
                stringBuffer.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
        } catch (Exception e) {
            String str2 = "hf16 err:" + e.toString();
        }
        return stringBuffer.toString().substring(0, 16);
    }
}
