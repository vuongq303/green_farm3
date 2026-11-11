package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.IInterface;
import com.google.android.gms.c.a;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface zzyc extends IInterface {
    String getAdvertiser();

    String getBody();

    String getCallToAction();

    Bundle getExtras();

    String getHeadline();

    List getImages();

    boolean getOverrideClickHandling();

    boolean getOverrideImpressionRecording();

    zzlo getVideoController();

    void recordImpression();

    void zzb(a aVar, a aVar2, a aVar3);

    void zzj(a aVar);

    void zzk(a aVar);

    a zzke();

    zzps zzkf();

    zzpw zzkg();

    void zzl(a aVar);

    a zzmv();

    a zzmw();
}
