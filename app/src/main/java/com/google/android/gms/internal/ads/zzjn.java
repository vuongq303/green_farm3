package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public class zzjn extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzjn> CREATOR = new zzjo();
    public final int height;
    public final int heightPixels;
    public final int width;
    public final int widthPixels;
    public final String zzarb;
    public final boolean zzarc;
    public final zzjn[] zzard;
    public final boolean zzare;
    public final boolean zzarf;
    public boolean zzarg;

    public zzjn() {
        this("interstitial_mb", 0, 0, true, 0, 0, null, false, false, false);
    }

    public zzjn(Context context, AdSize adSize) {
        this(context, new AdSize[]{adSize});
    }

    /* JADX WARN: Code duplicated, block: B:37:0x00d2  */
    public zzjn(Context context, AdSize[] adSizeArr) {
        int i;
        AdSize adSize = adSizeArr[0];
        this.zzarc = false;
        this.zzarf = adSize.isFluid();
        if (this.zzarf) {
            this.width = AdSize.BANNER.getWidth();
            this.height = AdSize.BANNER.getHeight();
        } else {
            this.width = adSize.getWidth();
            this.height = adSize.getHeight();
        }
        boolean z = this.width == -1;
        boolean z2 = this.height == -2;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (z) {
            zzkb.zzif();
            if (zzamu.zzbi(context)) {
                zzkb.zzif();
                if (zzamu.zzbj(context)) {
                    int i2 = displayMetrics.widthPixels;
                    zzkb.zzif();
                    this.widthPixels = i2 - zzamu.zzbk(context);
                } else {
                    this.widthPixels = displayMetrics.widthPixels;
                }
            } else {
                this.widthPixels = displayMetrics.widthPixels;
            }
            double d = this.widthPixels / displayMetrics.density;
            int i3 = (int) d;
            i = d - ((double) ((int) d)) >= 0.01d ? i3 + 1 : i3;
        } else {
            int i4 = this.width;
            zzkb.zzif();
            this.widthPixels = zzamu.zza(displayMetrics, this.width);
            i = i4;
        }
        int iZzd = z2 ? zzd(displayMetrics) : this.height;
        zzkb.zzif();
        this.heightPixels = zzamu.zza(displayMetrics, iZzd);
        if (z || z2) {
            this.zzarb = new StringBuilder(26).append(i).append("x").append(iZzd).append("_as").toString();
        } else if (this.zzarf) {
            this.zzarb = "320x50_mb";
        } else {
            this.zzarb = adSize.toString();
        }
        if (adSizeArr.length > 1) {
            this.zzard = new zzjn[adSizeArr.length];
            for (int i5 = 0; i5 < adSizeArr.length; i5++) {
                this.zzard[i5] = new zzjn(context, adSizeArr[i5]);
            }
        } else {
            this.zzard = null;
        }
        this.zzare = false;
        this.zzarg = false;
    }

    public zzjn(zzjn zzjnVar, zzjn[] zzjnVarArr) {
        this(zzjnVar.zzarb, zzjnVar.height, zzjnVar.heightPixels, zzjnVar.zzarc, zzjnVar.width, zzjnVar.widthPixels, zzjnVarArr, zzjnVar.zzare, zzjnVar.zzarf, zzjnVar.zzarg);
    }

    zzjn(String str, int i, int i2, boolean z, int i3, int i4, zzjn[] zzjnVarArr, boolean z2, boolean z3, boolean z4) {
        this.zzarb = str;
        this.height = i;
        this.heightPixels = i2;
        this.zzarc = z;
        this.width = i3;
        this.widthPixels = i4;
        this.zzard = zzjnVarArr;
        this.zzare = z2;
        this.zzarf = z3;
        this.zzarg = z4;
    }

    public static int zzb(DisplayMetrics displayMetrics) {
        return displayMetrics.widthPixels;
    }

    public static int zzc(DisplayMetrics displayMetrics) {
        return (int) (zzd(displayMetrics) * displayMetrics.density);
    }

    private static int zzd(DisplayMetrics displayMetrics) {
        int i = (int) (displayMetrics.heightPixels / displayMetrics.density);
        if (i <= 400) {
            return 32;
        }
        return i <= 720 ? 50 : 90;
    }

    public static zzjn zzf(Context context) {
        return new zzjn("320x50_mb", 0, 0, false, 0, 0, null, true, false, false);
    }

    public static zzjn zzhx() {
        return new zzjn("reward_mb", 0, 0, true, 0, 0, null, false, false, false);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 2, this.zzarb, false);
        c.c(parcel, 3, this.height);
        c.c(parcel, 4, this.heightPixels);
        c.a(parcel, 5, this.zzarc);
        c.c(parcel, 6, this.width);
        c.c(parcel, 7, this.widthPixels);
        c.a(parcel, 8, (Parcelable[]) this.zzard, i, false);
        c.a(parcel, 9, this.zzare);
        c.a(parcel, 10, this.zzarf);
        c.a(parcel, 11, this.zzarg);
        c.G(parcel, iX);
    }

    public final AdSize zzhy() {
        return com.google.android.gms.ads.zzb.zza(this.width, this.height, this.zzarb);
    }
}
