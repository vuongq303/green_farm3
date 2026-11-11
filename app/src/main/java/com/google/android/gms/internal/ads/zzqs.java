package com.google.android.gms.internal.ads;

import android.os.IInterface;
import com.google.android.gms.c.a;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface zzqs extends IInterface {
    void destroy();

    List<String> getAvailableAssetNames();

    String getCustomTemplateId();

    zzlo getVideoController();

    void performClick(String str);

    void recordImpression();

    String zzao(String str);

    zzpw zzap(String str);

    boolean zzh(a aVar);

    a zzka();

    a zzkh();
}
