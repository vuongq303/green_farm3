package com.google.android.gms.internal.measurement;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.a.a;
import com.google.android.gms.a.d;
import com.google.android.gms.a.j;
import com.google.android.gms.a.n;
import com.google.android.gms.common.e.c;
import com.google.android.gms.common.internal.bf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class zzbf extends zzar {
    private boolean started;
    private final zzbc zzwv;
    private final zzco zzww;
    private final zzcn zzwx;
    private final zzax zzwy;
    private long zzwz;
    private final zzbw zzxa;
    private final zzbw zzxb;
    private final zzcz zzxc;
    private long zzxd;
    private boolean zzxe;

    protected zzbf(zzat zzatVar, zzav zzavVar) {
        super(zzatVar);
        bf.checkNotNull(zzavVar);
        this.zzwz = Long.MIN_VALUE;
        this.zzwx = new zzcn(zzatVar);
        this.zzwv = new zzbc(zzatVar);
        this.zzww = new zzco(zzatVar);
        this.zzwy = new zzax(zzatVar);
        this.zzxc = new zzcz(zzbt());
        this.zzxa = new zzbg(this, zzatVar);
        this.zzxb = new zzbh(this, zzatVar);
    }

    private final void zza(zzaw zzawVar, zzv zzvVar) {
        bf.checkNotNull(zzawVar);
        bf.checkNotNull(zzvVar);
        d dVar = new d(zzbs());
        dVar.hJ(zzawVar.zzcp());
        dVar.bW(zzawVar.zzcq());
        j jVarXn = dVar.Xn();
        zzad zzadVar = (zzad) jVarXn.b(zzad.class);
        zzadVar.zzl(ShareConstants.WEB_DIALOG_PARAM_DATA);
        zzadVar.zzb(true);
        jVarXn.a(zzvVar);
        zzy zzyVar = (zzy) jVarXn.b(zzy.class);
        zzu zzuVar = (zzu) jVarXn.b(zzu.class);
        for (Map.Entry<String, String> entry : zzawVar.zzcs().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("an".equals(key)) {
                zzuVar.setAppName(value);
            } else if ("av".equals(key)) {
                zzuVar.setAppVersion(value);
            } else if ("aid".equals(key)) {
                zzuVar.setAppId(value);
            } else if ("aiid".equals(key)) {
                zzuVar.setAppInstallerId(value);
            } else if ("uid".equals(key)) {
                zzadVar.setUserId(value);
            } else {
                zzyVar.set(key, value);
            }
        }
        zzb("Sending installation campaign to", zzawVar.zzcp(), zzvVar);
        jVarXn.zza(zzcb().zzfa());
        jVarXn.Xs();
    }

    private final long zzcy() {
        n.zzab();
        zzch();
        try {
            return this.zzwv.zzcy();
        } catch (SQLiteException e) {
            zze("Failed to get min/max hit times from local store", e);
            return 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzdd() {
        zzb((zzca) new zzbj(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzde() {
        try {
            this.zzwv.zzcx();
            zzdi();
        } catch (SQLiteException e) {
            zzd("Failed to delete stale hits", e);
        }
        this.zzxb.zzh(86400000L);
    }

    private final void zzdf() {
        if (this.zzxe || !zzbu.zzdt() || this.zzwy.isConnected()) {
            return;
        }
        if (this.zzxc.zzj(zzcc.zzzt.get().longValue())) {
            this.zzxc.start();
            zzq("Connecting to service");
            if (this.zzwy.connect()) {
                zzq("Connected to service");
                this.zzxc.clear();
                onServiceConnected();
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:58:0x015e A[Catch: all -> 0x01e0, TryCatch #1 {all -> 0x01e0, blocks: (B:14:0x0042, B:15:0x004a, B:17:0x0056, B:22:0x0075, B:23:0x0083, B:24:0x0087, B:26:0x008d, B:28:0x009b, B:39:0x00ea, B:41:0x00f2, B:42:0x00f8, B:44:0x00fe, B:46:0x010d, B:47:0x011e, B:50:0x0134, B:56:0x0156, B:58:0x015e, B:59:0x0169, B:61:0x016f, B:63:0x017e, B:72:0x01a6, B:65:0x0187, B:34:0x00c9), top: B:88:0x0042, inners: #0, #6, #7 }] */
    /* JADX WARN: Code duplicated, block: B:61:0x016f A[Catch: all -> 0x01e0, LOOP:3: B:59:0x0169->B:61:0x016f, LOOP_END, TRY_LEAVE, TryCatch #1 {all -> 0x01e0, blocks: (B:14:0x0042, B:15:0x004a, B:17:0x0056, B:22:0x0075, B:23:0x0083, B:24:0x0087, B:26:0x008d, B:28:0x009b, B:39:0x00ea, B:41:0x00f2, B:42:0x00f8, B:44:0x00fe, B:46:0x010d, B:47:0x011e, B:50:0x0134, B:56:0x0156, B:58:0x015e, B:59:0x0169, B:61:0x016f, B:63:0x017e, B:72:0x01a6, B:65:0x0187, B:34:0x00c9), top: B:88:0x0042, inners: #0, #6, #7 }] */
    /* JADX WARN: Code duplicated, block: B:65:0x0187 A[Catch: all -> 0x01e0, PHI: r0
  0x0187: PHI (r0v25 long) = (r0v24 long), (r0v36 long) binds: [B:57:0x015c, B:64:0x0186] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #1 {all -> 0x01e0, blocks: (B:14:0x0042, B:15:0x004a, B:17:0x0056, B:22:0x0075, B:23:0x0083, B:24:0x0087, B:26:0x008d, B:28:0x009b, B:39:0x00ea, B:41:0x00f2, B:42:0x00f8, B:44:0x00fe, B:46:0x010d, B:47:0x011e, B:50:0x0134, B:56:0x0156, B:58:0x015e, B:59:0x0169, B:61:0x016f, B:63:0x017e, B:72:0x01a6, B:65:0x0187, B:34:0x00c9), top: B:88:0x0042, inners: #0, #6, #7 }] */
    /* JADX WARN: Code duplicated, block: B:91:0x018d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:95:0x01c7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private final boolean zzdg() {
        long j;
        Iterator<Long> it;
        long jMax;
        n.zzab();
        zzch();
        zzq("Dispatching a batch of local hits");
        boolean z = !this.zzwy.isConnected();
        boolean z2 = this.zzww.zzew() ? false : true;
        if (z && z2) {
            zzq("No network or service available. Will retry later");
        } else {
            long jMax2 = Math.max(zzbu.zzdx(), zzbu.zzdy());
            ArrayList arrayList = new ArrayList();
            long jMax3 = 0;
            loop0: while (true) {
                try {
                    this.zzwv.beginTransaction();
                    arrayList.clear();
                    try {
                        List<zzch> listZzd = this.zzwv.zzd(jMax2);
                        if (listZzd.isEmpty()) {
                            zzq("Store is empty, nothing to dispatch");
                            zzdk();
                            try {
                                this.zzwv.setTransactionSuccessful();
                                this.zzwv.endTransaction();
                                break;
                            } catch (SQLiteException e) {
                                zze("Failed to commit local dispatch transaction", e);
                                zzdk();
                                break;
                            }
                        }
                        zza("Hits loaded from store. count", Integer.valueOf(listZzd.size()));
                        Iterator<zzch> it2 = listZzd.iterator();
                        while (it2.hasNext()) {
                            if (it2.next().zzem() == jMax3) {
                                zzd("Database contains successfully uploaded hit", Long.valueOf(jMax3), Integer.valueOf(listZzd.size()));
                                zzdk();
                                try {
                                    this.zzwv.setTransactionSuccessful();
                                    this.zzwv.endTransaction();
                                    break loop0;
                                } catch (SQLiteException e2) {
                                    zze("Failed to commit local dispatch transaction", e2);
                                    zzdk();
                                    break loop0;
                                }
                            }
                        }
                        if (this.zzwy.isConnected()) {
                            zzq("Service connected, sending hits to the service");
                            while (!listZzd.isEmpty()) {
                                zzch zzchVar = listZzd.get(0);
                                if (!this.zzwy.zzb(zzchVar)) {
                                    break;
                                }
                                jMax3 = Math.max(jMax3, zzchVar.zzem());
                                listZzd.remove(zzchVar);
                                zzb("Hit sent do device AnalyticsService for delivery", zzchVar);
                                try {
                                    this.zzwv.zze(zzchVar.zzem());
                                    arrayList.add(Long.valueOf(zzchVar.zzem()));
                                } catch (SQLiteException e3) {
                                    zze("Failed to remove hit that was send for delivery", e3);
                                    zzdk();
                                    try {
                                        this.zzwv.setTransactionSuccessful();
                                        this.zzwv.endTransaction();
                                    } catch (SQLiteException e4) {
                                        zze("Failed to commit local dispatch transaction", e4);
                                        zzdk();
                                    }
                                }
                            }
                            j = jMax3;
                            if (this.zzww.zzew()) {
                                List<Long> listZzb = this.zzww.zzb(listZzd);
                                it = listZzb.iterator();
                                jMax = j;
                                while (it.hasNext()) {
                                    jMax = Math.max(jMax, it.next().longValue());
                                }
                                try {
                                    this.zzwv.zza(listZzb);
                                    arrayList.addAll(listZzb);
                                    j = jMax;
                                    if (arrayList.isEmpty()) {
                                        try {
                                            this.zzwv.setTransactionSuccessful();
                                            this.zzwv.endTransaction();
                                            break;
                                        } catch (SQLiteException e5) {
                                            zze("Failed to commit local dispatch transaction", e5);
                                            zzdk();
                                        }
                                    } else {
                                        try {
                                            this.zzwv.setTransactionSuccessful();
                                            this.zzwv.endTransaction();
                                            jMax3 = j;
                                        } catch (SQLiteException e6) {
                                            zze("Failed to commit local dispatch transaction", e6);
                                            zzdk();
                                        }
                                    }
                                } catch (SQLiteException e7) {
                                    zze("Failed to remove successfully uploaded hits", e7);
                                    zzdk();
                                    try {
                                        this.zzwv.setTransactionSuccessful();
                                        this.zzwv.endTransaction();
                                    } catch (SQLiteException e8) {
                                        zze("Failed to commit local dispatch transaction", e8);
                                        zzdk();
                                    }
                                }
                            } else {
                                if (arrayList.isEmpty()) {
                                    this.zzwv.setTransactionSuccessful();
                                    this.zzwv.endTransaction();
                                    break;
                                }
                                this.zzwv.setTransactionSuccessful();
                                this.zzwv.endTransaction();
                                jMax3 = j;
                            }
                        } else {
                            j = jMax3;
                            if (this.zzww.zzew()) {
                                List<Long> listZzb2 = this.zzww.zzb(listZzd);
                                it = listZzb2.iterator();
                                jMax = j;
                                while (it.hasNext()) {
                                    jMax = Math.max(jMax, it.next().longValue());
                                }
                                this.zzwv.zza(listZzb2);
                                arrayList.addAll(listZzb2);
                                j = jMax;
                                if (arrayList.isEmpty()) {
                                    this.zzwv.setTransactionSuccessful();
                                    this.zzwv.endTransaction();
                                    break;
                                }
                                this.zzwv.setTransactionSuccessful();
                                this.zzwv.endTransaction();
                                jMax3 = j;
                            } else {
                                if (arrayList.isEmpty()) {
                                    this.zzwv.setTransactionSuccessful();
                                    this.zzwv.endTransaction();
                                    break;
                                }
                                this.zzwv.setTransactionSuccessful();
                                this.zzwv.endTransaction();
                                jMax3 = j;
                            }
                        }
                    } catch (SQLiteException e9) {
                        zzd("Failed to read hits from persisted store", e9);
                        zzdk();
                        try {
                            this.zzwv.setTransactionSuccessful();
                            this.zzwv.endTransaction();
                        } catch (SQLiteException e10) {
                            zze("Failed to commit local dispatch transaction", e10);
                            zzdk();
                        }
                    }
                } catch (Throwable th) {
                    try {
                        this.zzwv.setTransactionSuccessful();
                        this.zzwv.endTransaction();
                        throw th;
                    } catch (SQLiteException e11) {
                        zze("Failed to commit local dispatch transaction", e11);
                        zzdk();
                    }
                }
            }
        }
        return false;
    }

    private final void zzdj() {
        zzbz zzbzVarZzbz = zzbz();
        if (zzbzVarZzbz.zzei() && !zzbzVarZzbz.zzef()) {
            long jZzcy = zzcy();
            if (jZzcy == 0 || Math.abs(zzbt().currentTimeMillis() - jZzcy) > zzcc.zzys.get().longValue()) {
                return;
            }
            zza("Dispatch alarm scheduled (ms)", Long.valueOf(zzbu.zzdw()));
            zzbzVarZzbz.zzej();
        }
    }

    private final void zzdk() {
        if (this.zzxa.zzef()) {
            zzq("All hits dispatched or no network/service. Going to power save mode");
        }
        this.zzxa.cancel();
        zzbz zzbzVarZzbz = zzbz();
        if (zzbzVarZzbz.zzef()) {
            zzbzVarZzbz.cancel();
        }
    }

    private final long zzdl() {
        if (this.zzwz != Long.MIN_VALUE) {
            return this.zzwz;
        }
        long jLongValue = zzcc.zzyn.get().longValue();
        zzde zzdeVarZzca = zzca();
        zzdeVarZzca.zzch();
        if (!zzdeVarZzca.zzaca) {
            return jLongValue;
        }
        zzde zzdeVarZzca2 = zzca();
        zzdeVarZzca2.zzch();
        return ((long) zzdeVarZzca2.zzaac) * 1000;
    }

    private final void zzdm() {
        zzch();
        n.zzab();
        this.zzxe = true;
        this.zzwy.disconnect();
        zzdi();
    }

    private final boolean zzx(String str) {
        return c.aV(getContext()).checkCallingOrSelfPermission(str) == 0;
    }

    protected final void onServiceConnected() {
        n.zzab();
        n.zzab();
        zzch();
        if (!zzbu.zzdt()) {
            zzt("Service client disabled. Can't dispatch local hits to device AnalyticsService");
        }
        if (!this.zzwy.isConnected()) {
            zzq("Service not connected");
            return;
        }
        if (this.zzwv.isEmpty()) {
            return;
        }
        zzq("Dispatching local hits to device AnalyticsService");
        while (true) {
            try {
                List<zzch> listZzd = this.zzwv.zzd(zzbu.zzdx());
                if (listZzd.isEmpty()) {
                    zzdi();
                    return;
                }
                while (!listZzd.isEmpty()) {
                    zzch zzchVar = listZzd.get(0);
                    if (!this.zzwy.zzb(zzchVar)) {
                        zzdi();
                        return;
                    }
                    listZzd.remove(zzchVar);
                    try {
                        this.zzwv.zze(zzchVar.zzem());
                    } catch (SQLiteException e) {
                        zze("Failed to remove hit that was send for delivery", e);
                        zzdk();
                        return;
                    }
                }
            } catch (SQLiteException e2) {
                zze("Failed to read hits from store", e2);
                zzdk();
                return;
            }
        }
    }

    final void start() {
        zzch();
        bf.a(!this.started, "Analytics backend already started");
        this.started = true;
        zzbw().c(new zzbi(this));
    }

    public final long zza(zzaw zzawVar, boolean z) {
        bf.checkNotNull(zzawVar);
        zzch();
        n.zzab();
        try {
            this.zzwv.beginTransaction();
            zzbc zzbcVar = this.zzwv;
            long jZzco = zzawVar.zzco();
            String strZzaz = zzawVar.zzaz();
            bf.id(strZzaz);
            zzbcVar.zzch();
            n.zzab();
            int iDelete = zzbcVar.getWritableDatabase().delete("properties", "app_uid=? AND cid<>?", new String[]{String.valueOf(jZzco), strZzaz});
            if (iDelete > 0) {
                zzbcVar.zza("Deleted property records", Integer.valueOf(iDelete));
            }
            long jZza = this.zzwv.zza(zzawVar.zzco(), zzawVar.zzaz(), zzawVar.zzcp());
            zzawVar.zzb(1 + jZza);
            zzbc zzbcVar2 = this.zzwv;
            bf.checkNotNull(zzawVar);
            zzbcVar2.zzch();
            n.zzab();
            SQLiteDatabase writableDatabase = zzbcVar2.getWritableDatabase();
            Map<String, String> mapZzcs = zzawVar.zzcs();
            bf.checkNotNull(mapZzcs);
            Uri.Builder builder = new Uri.Builder();
            for (Map.Entry<String, String> entry : mapZzcs.entrySet()) {
                builder.appendQueryParameter(entry.getKey(), entry.getValue());
            }
            String encodedQuery = builder.build().getEncodedQuery();
            String str = encodedQuery == null ? "" : encodedQuery;
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_uid", Long.valueOf(zzawVar.zzco()));
            contentValues.put("cid", zzawVar.zzaz());
            contentValues.put("tid", zzawVar.zzcp());
            contentValues.put("adid", Integer.valueOf(zzawVar.zzcq() ? 1 : 0));
            contentValues.put("hits_count", Long.valueOf(zzawVar.zzcr()));
            contentValues.put(NativeProtocol.WEB_DIALOG_PARAMS, str);
            try {
                if (writableDatabase.insertWithOnConflict("properties", null, contentValues, 5) == -1) {
                    zzbcVar2.zzu("Failed to insert/update a property (got -1)");
                }
            } catch (SQLiteException e) {
                zzbcVar2.zze("Error storing a property", e);
            }
            this.zzwv.setTransactionSuccessful();
            return jZza;
        } catch (SQLiteException e2) {
            zze("Failed to update Analytics property", e2);
            return -1L;
        } finally {
            try {
                this.zzwv.endTransaction();
            } catch (SQLiteException e3) {
                zze("Failed to end transaction", e3);
            }
        }
    }

    public final void zza(zzch zzchVar) throws Throwable {
        Pair<String, Long> pairZzfh;
        bf.checkNotNull(zzchVar);
        n.zzab();
        zzch();
        if (this.zzxe) {
            zzr("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            zza("Delivering hit", zzchVar);
        }
        if (TextUtils.isEmpty(zzchVar.zzeq()) && (pairZzfh = zzcb().zzff().zzfh()) != null) {
            Long l = (Long) pairZzfh.second;
            String str = (String) pairZzfh.first;
            String strValueOf = String.valueOf(l);
            String string = new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(str).length()).append(strValueOf).append(":").append(str).toString();
            HashMap map = new HashMap(zzchVar.zzcs());
            map.put("_m", string);
            zzchVar = new zzch(this, map, zzchVar.zzen(), zzchVar.zzeo(), zzchVar.zzem(), zzchVar.zzel(), zzchVar.getCommands());
        }
        zzdf();
        if (this.zzwy.zzb(zzchVar)) {
            zzr("Hit sent to the device AnalyticsService for delivery");
            return;
        }
        try {
            this.zzwv.zzc(zzchVar);
            zzdi();
        } catch (SQLiteException e) {
            zze("Delivery failed to save hit to a database", e);
            zzbu().zza(zzchVar, "deliver: failed to insert hit to database");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzar
    protected final void zzac() {
        this.zzwv.zzm();
        this.zzww.zzm();
        this.zzwy.zzm();
    }

    protected final void zzb(zzaw zzawVar) {
        n.zzab();
        zzb("Sending first hit to property", zzawVar.zzcp());
        if (zzcb().zzfb().zzj(zzbu.zzed())) {
            return;
        }
        String strZzfe = zzcb().zzfe();
        if (TextUtils.isEmpty(strZzfe)) {
            return;
        }
        zzv zzvVarZza = zzdd.zza(zzbu(), strZzfe);
        zzb("Found relevant installation campaign", zzvVarZza);
        zza(zzawVar, zzvVarZza);
    }

    public final void zzb(zzca zzcaVar) {
        long j = this.zzxd;
        n.zzab();
        zzch();
        long jZzfc = zzcb().zzfc();
        zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(jZzfc != 0 ? Math.abs(zzbt().currentTimeMillis() - jZzfc) : -1L));
        zzdf();
        try {
            zzdg();
            zzcb().zzfd();
            zzdi();
            if (zzcaVar != null) {
                zzcaVar.zza(null);
            }
            if (this.zzxd != j) {
                this.zzwx.zzev();
            }
        } catch (Throwable th) {
            zze("Local dispatch failed", th);
            zzcb().zzfd();
            zzdi();
            if (zzcaVar != null) {
                zzcaVar.zza(th);
            }
        }
    }

    public final void zzbn() {
        n.zzab();
        zzch();
        zzq("Delete all hits from local store");
        try {
            zzbc zzbcVar = this.zzwv;
            n.zzab();
            zzbcVar.zzch();
            zzbcVar.getWritableDatabase().delete("hits2", null, null);
            zzbc zzbcVar2 = this.zzwv;
            n.zzab();
            zzbcVar2.zzch();
            zzbcVar2.getWritableDatabase().delete("properties", null, null);
            zzdi();
        } catch (SQLiteException e) {
            zzd("Failed to delete hits from store", e);
        }
        zzdf();
        if (this.zzwy.zzct()) {
            zzq("Device service unavailable. Can't clear hits stored on the device service.");
        }
    }

    final void zzbr() {
        n.zzab();
        this.zzxd = zzbt().currentTimeMillis();
    }

    protected final void zzdc() {
        zzch();
        n.zzab();
        Context context = zzbs().getContext();
        if (!zzct.zza(context)) {
            zzt("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!zzcu.zze(context)) {
            zzu("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!a.zza(context)) {
            zzt("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzcb().zzfa();
        if (!zzx("android.permission.ACCESS_NETWORK_STATE")) {
            zzu("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzdm();
        }
        if (!zzx("android.permission.INTERNET")) {
            zzu("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzdm();
        }
        if (zzcu.zze(getContext())) {
            zzq("AnalyticsService registered in the app manifest and enabled");
        } else {
            zzt("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!this.zzxe && !this.zzwv.isEmpty()) {
            zzdf();
        }
        zzdi();
    }

    public final void zzdh() {
        n.zzab();
        zzch();
        zzr("Sync dispatching local hits");
        long j = this.zzxd;
        zzdf();
        try {
            zzdg();
            zzcb().zzfd();
            zzdi();
            if (this.zzxd != j) {
                this.zzwx.zzev();
            }
        } catch (Throwable th) {
            zze("Sync local dispatch failed", th);
            zzdi();
        }
    }

    public final void zzdi() {
        boolean zIsConnected;
        long jMin;
        n.zzab();
        zzch();
        if (!(!this.zzxe && zzdl() > 0)) {
            this.zzwx.unregister();
            zzdk();
            return;
        }
        if (this.zzwv.isEmpty()) {
            this.zzwx.unregister();
            zzdk();
            return;
        }
        if (zzcc.zzzo.get().booleanValue()) {
            zIsConnected = true;
        } else {
            this.zzwx.zzet();
            zIsConnected = this.zzwx.isConnected();
        }
        if (!zIsConnected) {
            zzdk();
            zzdj();
            return;
        }
        zzdj();
        long jZzdl = zzdl();
        long jZzfc = zzcb().zzfc();
        if (jZzfc != 0) {
            jMin = jZzdl - Math.abs(zzbt().currentTimeMillis() - jZzfc);
            if (jMin <= 0) {
                jMin = Math.min(zzbu.zzdv(), jZzdl);
            }
        } else {
            jMin = Math.min(zzbu.zzdv(), jZzdl);
        }
        zza("Dispatch scheduled (ms)", Long.valueOf(jMin));
        if (this.zzxa.zzef()) {
            this.zzxa.zzi(Math.max(1L, jMin + this.zzxa.zzee()));
        } else {
            this.zzxa.zzh(jMin);
        }
    }

    public final void zzg(long j) {
        n.zzab();
        zzch();
        if (j < 0) {
            j = 0;
        }
        this.zzwz = j;
        zzdi();
    }

    public final void zzy(String str) {
        bf.id(str);
        n.zzab();
        zzv zzvVarZza = zzdd.zza(zzbu(), str);
        if (zzvVarZza == null) {
            zzd("Parsing failed. Ignoring invalid campaign data", str);
            return;
        }
        String strZzfe = zzcb().zzfe();
        if (str.equals(strZzfe)) {
            zzt("Ignoring duplicate install campaign");
            return;
        }
        if (!TextUtils.isEmpty(strZzfe)) {
            zzd("Ignoring multiple install campaigns. original, new", strZzfe, str);
            return;
        }
        zzcb().zzac(str);
        if (zzcb().zzfb().zzj(zzbu.zzed())) {
            zzd("Campaign received too late, ignoring", zzvVarZza);
            return;
        }
        zzb("Received installation campaign", zzvVarZza);
        Iterator<zzaw> it = this.zzwv.zzf(0L).iterator();
        while (it.hasNext()) {
            zza(it.next(), zzvVarZza);
        }
    }
}
