package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.Parcel;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.c.a;
import com.google.android.gms.c.b;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzyd extends zzek implements zzyc {
    public zzyd() {
        super("com.google.android.gms.ads.internal.mediation.client.INativeContentAdMapper");
    }

    @Override // com.google.android.gms.internal.ads.zzek
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 2:
                String headline = getHeadline();
                parcel2.writeNoException();
                parcel2.writeString(headline);
                return true;
            case 3:
                List images = getImages();
                parcel2.writeNoException();
                parcel2.writeList(images);
                return true;
            case 4:
                String body = getBody();
                parcel2.writeNoException();
                parcel2.writeString(body);
                return true;
            case 5:
                zzpw zzpwVarZzkg = zzkg();
                parcel2.writeNoException();
                zzel.zza(parcel2, zzpwVarZzkg);
                return true;
            case 6:
                String callToAction = getCallToAction();
                parcel2.writeNoException();
                parcel2.writeString(callToAction);
                return true;
            case 7:
                String advertiser = getAdvertiser();
                parcel2.writeNoException();
                parcel2.writeString(advertiser);
                return true;
            case 8:
                recordImpression();
                parcel2.writeNoException();
                return true;
            case 9:
                zzj(b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                zzk(b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                boolean overrideImpressionRecording = getOverrideImpressionRecording();
                parcel2.writeNoException();
                zzel.zza(parcel2, overrideImpressionRecording);
                return true;
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                boolean overrideClickHandling = getOverrideClickHandling();
                parcel2.writeNoException();
                zzel.zza(parcel2, overrideClickHandling);
                return true;
            case 13:
                Bundle extras = getExtras();
                parcel2.writeNoException();
                zzel.zzb(parcel2, extras);
                return true;
            case 14:
                zzl(b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 15:
                a aVarZzmv = zzmv();
                parcel2.writeNoException();
                zzel.zza(parcel2, aVarZzmv);
                return true;
            case 16:
                zzlo videoController = getVideoController();
                parcel2.writeNoException();
                zzel.zza(parcel2, videoController);
                return true;
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
            case 18:
            default:
                return false;
            case 19:
                zzps zzpsVarZzkf = zzkf();
                parcel2.writeNoException();
                zzel.zza(parcel2, zzpsVarZzkf);
                return true;
            case 20:
                a aVarZzmw = zzmw();
                parcel2.writeNoException();
                zzel.zza(parcel2, aVarZzmw);
                return true;
            case 21:
                a aVarZzke = zzke();
                parcel2.writeNoException();
                zzel.zza(parcel2, aVarZzke);
                return true;
            case 22:
                zzb(b.p(parcel.readStrongBinder()), b.p(parcel.readStrongBinder()), b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
        }
    }
}
