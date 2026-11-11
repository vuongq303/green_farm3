package com.google.firebase.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.c;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/* JADX INFO: loaded from: classes.dex */
final class zzaa {
    private final SharedPreferences zzbrz;
    private final Context zzqs;

    public zzaa(Context context) {
        this(context, "com.google.android.gms.appid");
    }

    private zzaa(Context context, String str) {
        this.zzqs = context;
        this.zzbrz = context.getSharedPreferences(str, 0);
        String strValueOf = String.valueOf(str);
        String strValueOf2 = String.valueOf("-no-backup");
        File file = new File(c.f(this.zzqs), strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf));
        if (file.exists()) {
            return;
        }
        try {
            if (!file.createNewFile() || isEmpty()) {
                return;
            }
            Log.i("FirebaseInstanceId", "App restored, clearing state");
            zztd();
            FirebaseInstanceId.getInstance().zzsk();
        } catch (IOException e) {
            if (Log.isLoggable("FirebaseInstanceId", 3)) {
                String strValueOf3 = String.valueOf(e.getMessage());
                Log.d("FirebaseInstanceId", strValueOf3.length() != 0 ? "Error creating file in no backup dir: ".concat(strValueOf3) : new String("Error creating file in no backup dir: "));
            }
        }
    }

    private final synchronized boolean isEmpty() {
        return this.zzbrz.getAll().isEmpty();
    }

    private static String zzi(String str, String str2, String str3) {
        return new StringBuilder(String.valueOf(str).length() + 4 + String.valueOf(str2).length() + String.valueOf(str3).length()).append(str).append("|T|").append(str2).append("|").append(str3).toString();
    }

    private static String zzv(String str, String str2) {
        return new StringBuilder(String.valueOf(str).length() + 3 + String.valueOf(str2).length()).append(str).append("|S|").append(str2).toString();
    }

    public final synchronized void zza(String str, String str2, String str3, String str4, String str5) {
        String strZza = zzab.zza(str4, str5, System.currentTimeMillis());
        if (strZza != null) {
            SharedPreferences.Editor editorEdit = this.zzbrz.edit();
            editorEdit.putString(zzi(str, str2, str3), strZza);
            editorEdit.commit();
        }
    }

    public final synchronized void zzev(String str) {
        String string = this.zzbrz.getString("topic_operaion_queue", "");
        this.zzbrz.edit().putString("topic_operaion_queue", new StringBuilder(String.valueOf(string).length() + 1 + String.valueOf(str).length()).append(string).append(",").append(str).toString()).apply();
    }

    public final synchronized boolean zzez(String str) {
        boolean z;
        String string = this.zzbrz.getString("topic_operaion_queue", "");
        String strValueOf = String.valueOf(",");
        String strValueOf2 = String.valueOf(str);
        if (string.startsWith(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf))) {
            String strValueOf3 = String.valueOf(",");
            String strValueOf4 = String.valueOf(str);
            this.zzbrz.edit().putString("topic_operaion_queue", string.substring((strValueOf4.length() != 0 ? strValueOf3.concat(strValueOf4) : new String(strValueOf3)).length())).apply();
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public final synchronized long zzfa(String str) {
        long j;
        String string = this.zzbrz.getString(zzv(str, "cre"), null);
        if (string != null) {
            try {
                j = Long.parseLong(string);
            } catch (NumberFormatException e) {
                j = 0;
            }
        } else {
            j = 0;
        }
        return j;
    }

    final synchronized KeyPair zzfb(String str) {
        KeyPair keyPairZzsc;
        keyPairZzsc = zza.zzsc();
        long jCurrentTimeMillis = System.currentTimeMillis();
        SharedPreferences.Editor editorEdit = this.zzbrz.edit();
        editorEdit.putString(zzv(str, "|P|"), Base64.encodeToString(keyPairZzsc.getPublic().getEncoded(), 11));
        editorEdit.putString(zzv(str, "|K|"), Base64.encodeToString(keyPairZzsc.getPrivate().getEncoded(), 11));
        editorEdit.putString(zzv(str, "cre"), Long.toString(jCurrentTimeMillis));
        editorEdit.commit();
        return keyPairZzsc;
    }

    public final synchronized void zzfc(String str) {
        String strConcat = String.valueOf(str).concat("|T|");
        SharedPreferences.Editor editorEdit = this.zzbrz.edit();
        for (String str2 : this.zzbrz.getAll().keySet()) {
            if (str2.startsWith(strConcat)) {
                editorEdit.remove(str2);
            }
        }
        editorEdit.commit();
    }

    public final synchronized KeyPair zzfd(String str) {
        KeyPair keyPair;
        String string = this.zzbrz.getString(zzv(str, "|P|"), null);
        String string2 = this.zzbrz.getString(zzv(str, "|K|"), null);
        if (string == null || string2 == null) {
            keyPair = null;
        } else {
            try {
                byte[] bArrDecode = Base64.decode(string, 8);
                byte[] bArrDecode2 = Base64.decode(string2, 8);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                keyPair = new KeyPair(keyFactory.generatePublic(new X509EncodedKeySpec(bArrDecode)), keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bArrDecode2)));
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                String strValueOf = String.valueOf(e);
                Log.w("FirebaseInstanceId", new StringBuilder(String.valueOf(strValueOf).length() + 19).append("Invalid key stored ").append(strValueOf).toString());
                FirebaseInstanceId.getInstance().zzsk();
                keyPair = null;
            }
        }
        return keyPair;
    }

    public final synchronized zzab zzj(String str, String str2, String str3) {
        return zzab.zzfe(this.zzbrz.getString(zzi(str, str2, str3), null));
    }

    public final synchronized void zzk(String str, String str2, String str3) {
        String strZzi = zzi(str, str2, str3);
        SharedPreferences.Editor editorEdit = this.zzbrz.edit();
        editorEdit.remove(strZzi);
        editorEdit.commit();
    }

    public final synchronized String zztc() {
        String str = null;
        synchronized (this) {
            String string = this.zzbrz.getString("topic_operaion_queue", null);
            if (string != null) {
                String[] strArrSplit = string.split(",");
                if (strArrSplit.length > 1 && !TextUtils.isEmpty(strArrSplit[1])) {
                    str = strArrSplit[1];
                }
            }
        }
        return str;
    }

    public final synchronized void zztd() {
        this.zzbrz.edit().clear().commit();
    }
}
