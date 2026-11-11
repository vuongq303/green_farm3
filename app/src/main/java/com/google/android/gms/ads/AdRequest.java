package com.google.android.gms.ads;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.mediation.customevent.CustomEvent;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzlw;
import com.google.android.gms.internal.ads.zzlx;
import java.util.Date;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class AdRequest {
    public static final String DEVICE_ID_EMULATOR = "B3EEABB8EE11C2BE770B684D95219ECB";
    public static final int ERROR_CODE_INTERNAL_ERROR = 0;
    public static final int ERROR_CODE_INVALID_REQUEST = 1;
    public static final int ERROR_CODE_NETWORK_ERROR = 2;
    public static final int ERROR_CODE_NO_FILL = 3;
    public static final int GENDER_FEMALE = 2;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_UNKNOWN = 0;
    public static final int MAX_CONTENT_URL_LENGTH = 512;
    private final zzlw bzp;

    public final class Builder {
        private final zzlx bzq = new zzlx();

        public Builder() {
            this.bzq.zzad("B3EEABB8EE11C2BE770B684D95219ECB");
        }

        public final Builder addCustomEventExtrasBundle(Class<? extends CustomEvent> cls, Bundle bundle) {
            this.bzq.zzb(cls, bundle);
            return this;
        }

        public final Builder addKeyword(String str) {
            this.bzq.zzac(str);
            return this;
        }

        public final Builder addNetworkExtras(NetworkExtras networkExtras) {
            this.bzq.zza(networkExtras);
            return this;
        }

        public final Builder addNetworkExtrasBundle(Class<? extends MediationAdapter> cls, Bundle bundle) {
            this.bzq.zza(cls, bundle);
            if (cls.equals(AdMobAdapter.class) && bundle.getBoolean("_emulatorLiveAds")) {
                this.bzq.zzae("B3EEABB8EE11C2BE770B684D95219ECB");
            }
            return this;
        }

        public final Builder addTestDevice(String str) {
            this.bzq.zzad(str);
            return this;
        }

        public final AdRequest build() {
            return new AdRequest(this);
        }

        public final Builder setBirthday(Date date) {
            this.bzq.zza(date);
            return this;
        }

        public final Builder setContentUrl(String str) {
            bf.d(str, (Object) "Content URL must be non-null.");
            bf.d(str, (Object) "Content URL must be non-empty.");
            bf.b(str.length() <= 512, "Content URL must not exceed %d in length.  Provided length was %d.", Integer.valueOf(AdRequest.MAX_CONTENT_URL_LENGTH), Integer.valueOf(str.length()));
            this.bzq.zzaf(str);
            return this;
        }

        public final Builder setGender(int i) {
            this.bzq.zzt(i);
            return this;
        }

        public final Builder setIsDesignedForFamilies(boolean z) {
            this.bzq.zzk(z);
            return this;
        }

        public final Builder setLocation(Location location) {
            this.bzq.zzb(location);
            return this;
        }

        public final Builder setRequestAgent(String str) {
            this.bzq.zzah(str);
            return this;
        }

        public final Builder tagForChildDirectedTreatment(boolean z) {
            this.bzq.zzj(z);
            return this;
        }
    }

    private AdRequest(Builder builder) {
        this.bzp = new zzlw(builder.bzq);
    }

    public final Date getBirthday() {
        return this.bzp.getBirthday();
    }

    public final String getContentUrl() {
        return this.bzp.getContentUrl();
    }

    public final <T extends CustomEvent> Bundle getCustomEventExtrasBundle(Class<T> cls) {
        return this.bzp.getCustomEventExtrasBundle(cls);
    }

    public final int getGender() {
        return this.bzp.getGender();
    }

    public final Set<String> getKeywords() {
        return this.bzp.getKeywords();
    }

    public final Location getLocation() {
        return this.bzp.getLocation();
    }

    @Deprecated
    public final <T extends NetworkExtras> T getNetworkExtras(Class<T> cls) {
        return (T) this.bzp.getNetworkExtras(cls);
    }

    public final <T extends MediationAdapter> Bundle getNetworkExtrasBundle(Class<T> cls) {
        return this.bzp.getNetworkExtrasBundle(cls);
    }

    public final boolean isTestDevice(Context context) {
        return this.bzp.isTestDevice(context);
    }

    public final zzlw zzay() {
        return this.bzp;
    }
}
