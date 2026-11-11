package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.IInterface;
import com.google.android.gms.c.a;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface zzyf extends IInterface {
    String getAdvertiser();

    String getBody();

    String getCallToAction();

    Bundle getExtras();

    String getHeadline();

    List getImages();

    boolean getOverrideClickHandling();

    boolean getOverrideImpressionRecording();

    String getPrice();

    double getStarRating();

    String getStore();

    zzlo getVideoController();

    void recordImpression();

    void zzb(a aVar, a aVar2, a aVar3);

    void zzj(a aVar);

    zzpw zzjz();

    a zzke();

    zzps zzkf();

    void zzl(a aVar);

    a zzmv();

    a zzmw();
}
