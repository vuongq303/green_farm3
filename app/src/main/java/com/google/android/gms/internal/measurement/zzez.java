package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzez extends zzo implements zzey {
    public zzez() {
        super("com.google.android.gms.measurement.internal.IMeasurementService");
    }

    @Override // com.google.android.gms.internal.measurement.zzo
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zza((zzeu) zzp.zza(parcel, zzeu.CREATOR), (zzec) zzp.zza(parcel, zzec.CREATOR));
                parcel2.writeNoException();
                return true;
            case 2:
                zza((zzjs) zzp.zza(parcel, zzjs.CREATOR), (zzec) zzp.zza(parcel, zzec.CREATOR));
                parcel2.writeNoException();
                return true;
            case 3:
            case 8:
            default:
                return false;
            case 4:
                zza((zzec) zzp.zza(parcel, zzec.CREATOR));
                parcel2.writeNoException();
                return true;
            case 5:
                zza((zzeu) zzp.zza(parcel, zzeu.CREATOR), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 6:
                zzb((zzec) zzp.zza(parcel, zzec.CREATOR));
                parcel2.writeNoException();
                return true;
            case 7:
                List<zzjs> listZza = zza((zzec) zzp.zza(parcel, zzec.CREATOR), zzp.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeTypedList(listZza);
                return true;
            case 9:
                byte[] bArrZza = zza((zzeu) zzp.zza(parcel, zzeu.CREATOR), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeByteArray(bArrZza);
                return true;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                zza(parcel.readLong(), parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                String strZzc = zzc((zzec) zzp.zza(parcel, zzec.CREATOR));
                parcel2.writeNoException();
                parcel2.writeString(strZzc);
                return true;
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                zza((zzef) zzp.zza(parcel, zzef.CREATOR), (zzec) zzp.zza(parcel, zzec.CREATOR));
                parcel2.writeNoException();
                return true;
            case 13:
                zzb((zzef) zzp.zza(parcel, zzef.CREATOR));
                parcel2.writeNoException();
                return true;
            case 14:
                List<zzjs> listZza2 = zza(parcel.readString(), parcel.readString(), zzp.zza(parcel), (zzec) zzp.zza(parcel, zzec.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(listZza2);
                return true;
            case 15:
                List<zzjs> listZza3 = zza(parcel.readString(), parcel.readString(), parcel.readString(), zzp.zza(parcel));
                parcel2.writeNoException();
                parcel2.writeTypedList(listZza3);
                return true;
            case 16:
                List<zzef> listZza4 = zza(parcel.readString(), parcel.readString(), (zzec) zzp.zza(parcel, zzec.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(listZza4);
                return true;
            case FacebookRequestErrorClassification.EC_USER_TOO_MANY_CALLS /* 17 */:
                List<zzef> listZze = zze(parcel.readString(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeTypedList(listZze);
                return true;
            case 18:
                zzd((zzec) zzp.zza(parcel, zzec.CREATOR));
                parcel2.writeNoException();
                return true;
        }
    }
}
