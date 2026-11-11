package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.IInterface;
import com.google.android.gms.c.a;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface zzxq extends IInterface {
    void destroy();

    Bundle getInterstitialAdapterInfo();

    zzlo getVideoController();

    a getView();

    boolean isInitialized();

    void pause();

    void resume();

    void setImmersiveMode(boolean z);

    void showInterstitial();

    void showVideo();

    void zza(a aVar, zzaic zzaicVar, List<String> list);

    void zza(a aVar, zzjj zzjjVar, String str, zzaic zzaicVar, String str2);

    void zza(a aVar, zzjj zzjjVar, String str, zzxt zzxtVar);

    void zza(a aVar, zzjj zzjjVar, String str, String str2, zzxt zzxtVar);

    void zza(a aVar, zzjj zzjjVar, String str, String str2, zzxt zzxtVar, zzpl zzplVar, List<String> list);

    void zza(a aVar, zzjn zzjnVar, zzjj zzjjVar, String str, zzxt zzxtVar);

    void zza(a aVar, zzjn zzjnVar, zzjj zzjjVar, String str, String str2, zzxt zzxtVar);

    void zza(zzjj zzjjVar, String str, String str2);

    void zzc(zzjj zzjjVar, String str);

    void zzi(a aVar);

    zzxz zzmo();

    zzyc zzmp();

    Bundle zzmq();

    Bundle zzmr();

    boolean zzms();

    zzqs zzmt();

    zzyf zzmu();
}
