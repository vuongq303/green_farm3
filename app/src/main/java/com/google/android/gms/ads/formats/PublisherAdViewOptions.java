package com.google.android.gms.ads.formats;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzjp;
import com.google.android.gms.internal.ads.zzla;
import com.google.android.gms.internal.ads.zzlb;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class PublisherAdViewOptions extends AbstractSafeParcelable {
    public static final Parcelable.Creator<PublisherAdViewOptions> CREATOR = new zzc();
    private final zzla bzG;
    private final boolean zzvm;
    private AppEventListener zzvo;

    public final class Builder {
        private boolean zzvm = false;
        private AppEventListener zzvo;

        public final PublisherAdViewOptions build() {
            return new PublisherAdViewOptions(this);
        }

        public final Builder setAppEventListener(AppEventListener appEventListener) {
            this.zzvo = appEventListener;
            return this;
        }

        public final Builder setManualImpressionsEnabled(boolean z) {
            this.zzvm = z;
            return this;
        }
    }

    private PublisherAdViewOptions(Builder builder) {
        this.zzvm = builder.zzvm;
        this.zzvo = builder.zzvo;
        this.bzG = this.zzvo != null ? new zzjp(this.zzvo) : null;
    }

    PublisherAdViewOptions(boolean z, IBinder iBinder) {
        this.zzvm = z;
        this.bzG = iBinder != null ? zzlb.zzd(iBinder) : null;
    }

    public final AppEventListener getAppEventListener() {
        return this.zzvo;
    }

    public final boolean getManualImpressionsEnabled() {
        return this.zzvm;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 1, getManualImpressionsEnabled());
        c.a(parcel, 2, this.bzG == null ? null : this.bzG.asBinder(), false);
        c.G(parcel, iX);
    }

    public final zzla zzbg() {
        return this.bzG;
    }
}
