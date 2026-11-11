package com.google.android.gms.internal.measurement;

import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
final class zzfr extends zzhk {
    static final Pair<String, Long> zzajr = new Pair<>("", 0L);
    private SharedPreferences zzaba;
    public zzfv zzajs;
    public final zzfu zzajt;
    public final zzfu zzaju;
    public final zzfu zzajv;
    public final zzfu zzajw;
    public final zzfu zzajx;
    public final zzfu zzajy;
    public final zzfu zzajz;
    public final zzfw zzaka;
    private String zzakb;
    private boolean zzakc;
    private long zzakd;
    private String zzake;
    private long zzakf;
    private final Object zzakg;
    public final zzfu zzakh;
    public final zzfu zzaki;
    public final zzft zzakj;
    public final zzfu zzakk;
    public final zzfu zzakl;
    public boolean zzakm;

    zzfr(zzgl zzglVar) {
        super(zzglVar);
        this.zzajt = new zzfu(this, "last_upload", 0L);
        this.zzaju = new zzfu(this, "last_upload_attempt", 0L);
        this.zzajv = new zzfu(this, "backoff", 0L);
        this.zzajw = new zzfu(this, "last_delete_stale", 0L);
        this.zzakh = new zzfu(this, "time_before_start", 10000L);
        this.zzaki = new zzfu(this, "session_timeout", 1800000L);
        this.zzakj = new zzft(this, "start_new_session", true);
        this.zzakk = new zzfu(this, "last_pause_time", 0L);
        this.zzakl = new zzfu(this, "time_active", 0L);
        this.zzajx = new zzfu(this, "midnight_offset", 0L);
        this.zzajy = new zzfu(this, "first_open_time", 0L);
        this.zzajz = new zzfu(this, "app_install_time", 0L);
        this.zzaka = new zzfw(this, "app_instance_id", null);
        this.zzakg = new Object();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SharedPreferences zziu() {
        zzab();
        zzch();
        return this.zzaba;
    }

    final void setMeasurementEnabled(boolean z) {
        zzab();
        zzgg().zzir().zzg("Setting measurementEnabled", Boolean.valueOf(z));
        SharedPreferences.Editor editorEdit = zziu().edit();
        editorEdit.putBoolean("measurement_enabled", z);
        editorEdit.apply();
    }

    final Pair<String, Boolean> zzbj(String str) {
        zzab();
        long jElapsedRealtime = zzbt().elapsedRealtime();
        if (this.zzakb != null && jElapsedRealtime < this.zzakd) {
            return new Pair<>(this.zzakb, Boolean.valueOf(this.zzakc));
        }
        this.zzakd = jElapsedRealtime + zzgi().zza(str, zzew.zzagm);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
            if (advertisingIdInfo != null) {
                this.zzakb = advertisingIdInfo.getId();
                this.zzakc = advertisingIdInfo.isLimitAdTrackingEnabled();
            }
            if (this.zzakb == null) {
                this.zzakb = "";
            }
        } catch (Throwable th) {
            zzgg().zziq().zzg("Unable to get advertising id", th);
            this.zzakb = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair<>(this.zzakb, Boolean.valueOf(this.zzakc));
    }

    final String zzbk(String str) {
        zzab();
        String str2 = (String) zzbj(str).first;
        MessageDigest messageDigest = zzjv.getMessageDigest("MD5");
        if (messageDigest == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigest.digest(str2.getBytes())));
    }

    final void zzbl(String str) {
        zzab();
        SharedPreferences.Editor editorEdit = zziu().edit();
        editorEdit.putString("gmp_app_id", str);
        editorEdit.apply();
    }

    final void zzbm(String str) {
        synchronized (this.zzakg) {
            this.zzake = str;
            this.zzakf = zzbt().elapsedRealtime();
        }
    }

    final void zzf(boolean z) {
        zzab();
        zzgg().zzir().zzg("Setting useService", Boolean.valueOf(z));
        SharedPreferences.Editor editorEdit = zziu().edit();
        editorEdit.putBoolean("use_service", z);
        editorEdit.apply();
    }

    final boolean zzg(boolean z) {
        zzab();
        return zziu().getBoolean("measurement_enabled", z);
    }

    final void zzh(boolean z) {
        zzab();
        zzgg().zzir().zzg("Updating deferred analytics collection", Boolean.valueOf(z));
        SharedPreferences.Editor editorEdit = zziu().edit();
        editorEdit.putBoolean("deferred_analytics_collection", z);
        editorEdit.apply();
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final boolean zzhh() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final void zzig() {
        this.zzaba = getContext().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzakm = this.zzaba.getBoolean("has_been_opened", false);
        if (!this.zzakm) {
            SharedPreferences.Editor editorEdit = this.zzaba.edit();
            editorEdit.putBoolean("has_been_opened", true);
            editorEdit.apply();
        }
        this.zzajs = new zzfv(this, "health_monitor", Math.max(0L, zzew.zzagn.get().longValue()));
    }

    final String zziv() {
        zzab();
        return zziu().getString("gmp_app_id", null);
    }

    final String zziw() {
        String str;
        synchronized (this.zzakg) {
            str = Math.abs(zzbt().elapsedRealtime() - this.zzakf) < 1000 ? this.zzake : null;
        }
        return str;
    }

    final Boolean zzix() {
        zzab();
        if (zziu().contains("use_service")) {
            return Boolean.valueOf(zziu().getBoolean("use_service", false));
        }
        return null;
    }

    final void zziy() {
        zzab();
        zzgg().zzir().log("Clearing collection preferences.");
        boolean zContains = zziu().contains("measurement_enabled");
        boolean zZzg = zContains ? zzg(true) : true;
        SharedPreferences.Editor editorEdit = zziu().edit();
        editorEdit.clear();
        editorEdit.apply();
        if (zContains) {
            setMeasurementEnabled(zZzg);
        }
    }

    protected final String zziz() {
        zzab();
        String string = zziu().getString("previous_os_version", null);
        zzfw().zzch();
        String str = Build.VERSION.RELEASE;
        if (!TextUtils.isEmpty(str) && !str.equals(string)) {
            SharedPreferences.Editor editorEdit = zziu().edit();
            editorEdit.putString("previous_os_version", str);
            editorEdit.apply();
        }
        return string;
    }

    final boolean zzja() {
        zzab();
        return zziu().getBoolean("deferred_analytics_collection", false);
    }

    final boolean zzjb() {
        return this.zzaba.contains("deferred_analytics_collection");
    }
}
