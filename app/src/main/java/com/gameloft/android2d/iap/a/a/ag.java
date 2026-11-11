package com.gameloft.android2d.iap.a.a;

import android.text.TextUtils;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;

/* JADX INFO: compiled from: Security.java */
/* JADX INFO: loaded from: classes.dex */
public class ag {
    private static final SecureRandom bmN = new SecureRandom();
    private static HashSet<Long> bmO = new HashSet<>();

    public static boolean C(String str, String str2, String str3) {
        if (str2 == null) {
            return false;
        }
        return TextUtils.isEmpty(str3) || a(fy(str), str2, str3);
    }

    public static PublicKey fy(String str) {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(com.gameloft.android2d.iap.a.a.a.a.decode(str)));
        } catch (com.gameloft.android2d.iap.a.a.a.b e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException(e2);
        } catch (InvalidKeySpecException e3) {
            throw new IllegalArgumentException(e3);
        }
    }

    public static boolean a(PublicKey publicKey, String str, String str2) {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initVerify(publicKey);
            signature.update(str.getBytes());
            return signature.verify(com.gameloft.android2d.iap.a.a.a.a.decode(str2));
        } catch (com.gameloft.android2d.iap.a.a.a.b e) {
            return false;
        } catch (InvalidKeyException e2) {
            return false;
        } catch (NoSuchAlgorithmException e3) {
            return false;
        } catch (SignatureException e4) {
            return false;
        }
    }
}
