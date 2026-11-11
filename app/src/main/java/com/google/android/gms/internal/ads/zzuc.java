package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.util.Base64;
import com.gameloft.glads.AndroidWebView;
import com.google.android.gms.common.internal.safeparcel.b;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
@zzadh
final class zzuc {
    final zzjj zzaao;
    final int zzbop;
    final String zzye;

    private zzuc(zzjj zzjjVar, String str, int i) {
        this.zzaao = zzjjVar;
        this.zzye = str;
        this.zzbop = i;
    }

    zzuc(zzty zztyVar) {
        this(zztyVar.zzlf(), zztyVar.getAdUnitId(), zztyVar.getNetworkType());
    }

    static zzuc zzba(String str) {
        String[] strArrSplit = str.split("\u0000");
        if (strArrSplit.length != 3) {
            throw new IOException("Incorrect field count for QueueSeed.");
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            try {
                String str2 = new String(Base64.decode(strArrSplit[0], 0), AndroidWebView.UTF_8);
                int i = Integer.parseInt(strArrSplit[1]);
                byte[] bArrDecode = Base64.decode(strArrSplit[2], 0);
                parcelObtain.unmarshall(bArrDecode, 0, bArrDecode.length);
                parcelObtain.setDataPosition(0);
                zzuc zzucVar = new zzuc(zzjj.CREATOR.createFromParcel(parcelObtain), str2, i);
                parcelObtain.recycle();
                return zzucVar;
            } catch (b | IllegalArgumentException | IllegalStateException e) {
                com.google.android.gms.ads.internal.zzbv.zzeo().zza(e, "QueueSeed.decode");
                throw new IOException("Malformed QueueSeed encoding.", e);
            }
        } catch (Throwable th) {
            parcelObtain.recycle();
            throw th;
        }
    }

    final String zzlu() {
        String string;
        Parcel parcelObtain = Parcel.obtain();
        try {
            try {
                String strEncodeToString = Base64.encodeToString(this.zzye.getBytes(AndroidWebView.UTF_8), 0);
                String string2 = Integer.toString(this.zzbop);
                this.zzaao.writeToParcel(parcelObtain, 0);
                String strEncodeToString2 = Base64.encodeToString(parcelObtain.marshall(), 0);
                string = new StringBuilder(String.valueOf(strEncodeToString).length() + 2 + String.valueOf(string2).length() + String.valueOf(strEncodeToString2).length()).append(strEncodeToString).append("\u0000").append(string2).append("\u0000").append(strEncodeToString2).toString();
                parcelObtain.recycle();
            } catch (UnsupportedEncodingException e) {
                zzakb.e("QueueSeed encode failed because UTF-8 is not available.");
                parcelObtain.recycle();
                string = "";
            }
            return string;
        } catch (Throwable th) {
            parcelObtain.recycle();
            throw th;
        }
    }
}
