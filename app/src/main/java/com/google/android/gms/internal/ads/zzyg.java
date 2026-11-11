package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.Parcel;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.c.a;
import com.google.android.gms.c.b;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzyg extends zzek implements zzyf {
    public zzyg() {
        super("com.google.android.gms.ads.internal.mediation.client.IUnifiedNativeAdMapper");
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
                zzpw zzpwVarZzjz = zzjz();
                parcel2.writeNoException();
                zzel.zza(parcel2, zzpwVarZzjz);
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
                double starRating = getStarRating();
                parcel2.writeNoException();
                parcel2.writeDouble(starRating);
                return true;
            case 9:
                String store = getStore();
                parcel2.writeNoException();
                parcel2.writeString(store);
                return true;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                String price = getPrice();
                parcel2.writeNoException();
                parcel2.writeString(price);
                return true;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                zzlo videoController = getVideoController();
                parcel2.writeNoException();
                zzel.zza(parcel2, videoController);
                return true;
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                zzps zzpsVarZzkf = zzkf();
                parcel2.writeNoException();
                zzel.zza(parcel2, zzpsVarZzkf);
                return true;
            case 13:
                a aVarZzmv = zzmv();
                parcel2.writeNoException();
                zzel.zza(parcel2, aVarZzmv);
                return true;
            case 14:
                a aVarZzmw = zzmw();
                parcel2.writeNoException();
                zzel.zza(parcel2, aVarZzmw);
                return true;
            case 15:
                a aVarZzke = zzke();
                parcel2.writeNoException();
                zzel.zza(parcel2, aVarZzke);
                return true;
            case 16:
                Bundle extras = getExtras();
                parcel2.writeNoException();
                zzel.zzb(parcel2, extras);
                return true;
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                boolean overrideImpressionRecording = getOverrideImpressionRecording();
                parcel2.writeNoException();
                zzel.zza(parcel2, overrideImpressionRecording);
                return true;
            case 18:
                boolean overrideClickHandling = getOverrideClickHandling();
                parcel2.writeNoException();
                zzel.zza(parcel2, overrideClickHandling);
                return true;
            case 19:
                recordImpression();
                parcel2.writeNoException();
                return true;
            case 20:
                zzj(b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 21:
                zzb(b.p(parcel.readStrongBinder()), b.p(parcel.readStrongBinder()), b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 22:
                zzl(b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            default:
                return false;
        }
    }
}
