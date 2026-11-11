package com.google.android.gms.internal.ads;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.facebook.R;
import com.google.android.gms.c.a;
import com.google.android.gms.c.b;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzqt extends zzek implements zzqs {
    public zzqt() {
        super("com.google.android.gms.ads.internal.formats.client.INativeCustomTemplateAd");
    }

    public static zzqs zzk(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.formats.client.INativeCustomTemplateAd");
        return iInterfaceQueryLocalInterface instanceof zzqs ? (zzqs) iInterfaceQueryLocalInterface : new zzqu(iBinder);
    }

    @Override // com.google.android.gms.internal.ads.zzek
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                String strZzao = zzao(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(strZzao);
                return true;
            case 2:
                zzpw zzpwVarZzap = zzap(parcel.readString());
                parcel2.writeNoException();
                zzel.zza(parcel2, zzpwVarZzap);
                return true;
            case 3:
                List<String> availableAssetNames = getAvailableAssetNames();
                parcel2.writeNoException();
                parcel2.writeStringList(availableAssetNames);
                return true;
            case 4:
                String customTemplateId = getCustomTemplateId();
                parcel2.writeNoException();
                parcel2.writeString(customTemplateId);
                return true;
            case 5:
                performClick(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 6:
                recordImpression();
                parcel2.writeNoException();
                return true;
            case 7:
                zzlo videoController = getVideoController();
                parcel2.writeNoException();
                zzel.zza(parcel2, videoController);
                return true;
            case 8:
                destroy();
                parcel2.writeNoException();
                return true;
            case 9:
                a aVarZzkh = zzkh();
                parcel2.writeNoException();
                zzel.zza(parcel2, aVarZzkh);
                return true;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                boolean zZzh = zzh(b.p(parcel.readStrongBinder()));
                parcel2.writeNoException();
                zzel.zza(parcel2, zZzh);
                return true;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                a aVarZzka = zzka();
                parcel2.writeNoException();
                zzel.zza(parcel2, aVarZzka);
                return true;
            default:
                return false;
        }
    }
}
