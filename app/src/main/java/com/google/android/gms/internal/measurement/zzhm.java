package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.e.a;
import android.text.TextUtils;
import com.google.android.gms.common.d.f;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.f.j;
import com.google.android.gms.measurement.b;
import com.google.android.gms.measurement.c;
import com.google.android.gms.measurement.d;
import com.google.android.gms.measurement.e;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class zzhm extends zzhk {
    protected zzif zzaoi;
    private d zzaoj;
    private final Set<e> zzaok;
    private boolean zzaol;
    private final AtomicReference<String> zzaom;
    protected boolean zzaon;

    protected zzhm(zzgl zzglVar) {
        super(zzglVar);
        this.zzaok = new CopyOnWriteArraySet();
        this.zzaon = true;
        this.zzaom = new AtomicReference<>();
    }

    private final void zza(b bVar) {
        long jCurrentTimeMillis = zzbt().currentTimeMillis();
        bf.checkNotNull(bVar);
        bf.id(bVar.mName);
        bf.id(bVar.bXr);
        bf.checkNotNull(bVar.mValue);
        bVar.bXB = jCurrentTimeMillis;
        String str = bVar.mName;
        Object obj = bVar.mValue;
        if (zzgc().zzby(str) != 0) {
            zzgg().zzil().zzg("Invalid conditional user property name", zzgb().zzbg(str));
            return;
        }
        if (zzgc().zzi(str, obj) != 0) {
            zzgg().zzil().zze("Invalid conditional user property value", zzgb().zzbg(str), obj);
            return;
        }
        Object objZzj = zzgc().zzj(str, obj);
        if (objZzj == null) {
            zzgg().zzil().zze("Unable to normalize conditional user property value", zzgb().zzbg(str), obj);
            return;
        }
        bVar.mValue = objZzj;
        long j = bVar.bXt;
        if (!TextUtils.isEmpty(bVar.bXs) && (j > 15552000000L || j < 1)) {
            zzgg().zzil().zze("Invalid conditional user property timeout", zzgb().zzbg(str), Long.valueOf(j));
            return;
        }
        long j2 = bVar.bXy;
        if (j2 > 15552000000L || j2 < 1) {
            zzgg().zzil().zze("Invalid conditional user property time to live", zzgb().zzbg(str), Long.valueOf(j2));
        } else {
            zzgf().zzc(new zzht(this, bVar));
        }
    }

    private final void zza(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        Bundle bundle2;
        if (bundle == null) {
            bundle2 = new Bundle();
        } else {
            bundle2 = new Bundle(bundle);
            for (String str4 : bundle2.keySet()) {
                Object obj = bundle2.get(str4);
                if (obj instanceof Bundle) {
                    bundle2.putBundle(str4, new Bundle((Bundle) obj));
                } else if (obj instanceof Parcelable[]) {
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    int i = 0;
                    while (true) {
                        int i2 = i;
                        if (i2 < parcelableArr.length) {
                            if (parcelableArr[i2] instanceof Bundle) {
                                parcelableArr[i2] = new Bundle((Bundle) parcelableArr[i2]);
                            }
                            i = i2 + 1;
                        }
                    }
                } else if (obj instanceof ArrayList) {
                    ArrayList arrayList = (ArrayList) obj;
                    int i3 = 0;
                    while (true) {
                        int i4 = i3;
                        if (i4 < arrayList.size()) {
                            Object obj2 = arrayList.get(i4);
                            if (obj2 instanceof Bundle) {
                                arrayList.set(i4, new Bundle((Bundle) obj2));
                            }
                            i3 = i4 + 1;
                        }
                    }
                }
            }
        }
        zzgf().zzc(new zzie(this, str, str2, j, bundle2, z, z2, z3, str3));
    }

    private final void zza(String str, String str2, long j, Object obj) {
        zzgf().zzc(new zzho(this, str, str2, obj, j));
    }

    private final void zza(String str, String str2, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        zza(str, str2, zzbt().currentTimeMillis(), bundle, true, z2, z3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(String str, String str2, Object obj, long j) {
        bf.id(str);
        bf.id(str2);
        zzab();
        zzch();
        if (!this.zzacr.isEnabled()) {
            zzgg().zziq().log("User property not set since app measurement is disabled");
        } else if (this.zzacr.zzjk()) {
            zzgg().zziq().zze("Setting user property (FE)", zzgb().zzbe(str2), obj);
            zzfx().zzb(new zzjs(str2, j, obj, str));
        }
    }

    private final void zza(String str, String str2, String str3, Bundle bundle) {
        long jCurrentTimeMillis = zzbt().currentTimeMillis();
        bf.id(str2);
        b bVar = new b();
        bVar.bXq = str;
        bVar.mName = str2;
        bVar.bXB = jCurrentTimeMillis;
        if (str3 != null) {
            bVar.bXz = str3;
            bVar.bXA = bundle;
        }
        zzgf().zzc(new zzhu(this, bVar));
    }

    private final Map<String, Object> zzb(String str, String str2, String str3, boolean z) {
        if (zzgf().zzjg()) {
            zzgg().zzil().log("Cannot get user properties from analytics worker thread");
            return Collections.emptyMap();
        }
        zzgf();
        if (zzgg.isMainThread()) {
            zzgg().zzil().log("Cannot get user properties from main thread");
            return Collections.emptyMap();
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zzacr.zzgf().zzc(new zzhw(this, atomicReference, str, str2, str3, z));
            try {
                atomicReference.wait(5000L);
            } catch (InterruptedException e) {
                zzgg().zzin().zzg("Interrupted waiting for get user properties", e);
            }
        }
        List<zzjs> list = (List) atomicReference.get();
        if (list == null) {
            zzgg().zzin().log("Timed out waiting for get user properties");
            return Collections.emptyMap();
        }
        a aVar = new a(list.size());
        for (zzjs zzjsVar : list) {
            aVar.put(zzjsVar.name, zzjsVar.getValue());
        }
        return aVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(b bVar) {
        zzab();
        zzch();
        bf.checkNotNull(bVar);
        bf.id(bVar.mName);
        bf.id(bVar.bXr);
        bf.checkNotNull(bVar.mValue);
        if (!this.zzacr.isEnabled()) {
            zzgg().zziq().log("Conditional property not sent since Firebase Analytics is disabled");
            return;
        }
        zzjs zzjsVar = new zzjs(bVar.mName, bVar.bXD, bVar.mValue, bVar.bXr);
        try {
            zzeu zzeuVarZza = zzgc().zza(bVar.bXw, bVar.bXx, bVar.bXr, 0L, true, false);
            zzfx().zzf(new zzef(bVar.bXq, bVar.bXr, zzjsVar, bVar.bXB, false, bVar.bXs, zzgc().zza(bVar.bXu, bVar.bXv, bVar.bXr, 0L, true, false), bVar.bXt, zzeuVarZza, bVar.bXy, zzgc().zza(bVar.bXz, bVar.bXA, bVar.bXr, 0L, true, false)));
        } catch (IllegalArgumentException e) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzb(String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        int length;
        int i;
        bf.id(str);
        bf.id(str2);
        bf.checkNotNull(bundle);
        zzab();
        zzch();
        if (!this.zzacr.isEnabled()) {
            zzgg().zziq().log("Event not sent since app measurement is disabled");
            return;
        }
        if (!this.zzaol) {
            this.zzaol = true;
            try {
                try {
                    Class.forName("com.google.android.gms.tagmanager.TagManagerService").getDeclaredMethod("initialize", Context.class).invoke(null, getContext());
                } catch (Exception e) {
                    zzgg().zzin().zzg("Failed to invoke Tag Manager's initialize() method", e);
                }
            } catch (ClassNotFoundException e2) {
                zzgg().zzip().log("Tag Manager is not found and thus will not be used");
            }
        }
        if (z3 && !"_iap".equals(str2)) {
            zzjv zzjvVarZzgc = this.zzacr.zzgc();
            if (!zzjvVarZzgc.zzq("event", str2)) {
                i = 2;
            } else if (zzjvVarZzgc.zza("event", c.bXE, str2)) {
                i = !zzjvVarZzgc.zza("event", 40, str2) ? 2 : 0;
            } else {
                i = 13;
            }
            if (i != 0) {
                this.zzacr.zzgc();
                this.zzacr.zzgc().zza(i, "_ev", zzjv.zza(str2, 40, true), str2 != null ? str2.length() : 0);
                return;
            }
        }
        zzik zzikVarZzkk = zzfy().zzkk();
        if (zzikVarZzkk != null && !bundle.containsKey("_sc")) {
            zzikVarZzkk.zzapq = true;
        }
        zzih.zza(zzikVarZzkk, bundle, z && z3);
        boolean zEquals = "am".equals(str);
        boolean zZzcb = zzjv.zzcb(str2);
        if (z && this.zzaoj != null && !zZzcb && !zEquals) {
            zzgg().zziq().zze("Passing event to registered event handler (FE)", zzgb().zzbe(str2), zzgb().zzb(bundle));
            this.zzaoj.a(str, str2, bundle, j);
            return;
        }
        if (this.zzacr.zzjk()) {
            int iZzbw = zzgc().zzbw(str2);
            if (iZzbw != 0) {
                zzgc();
                this.zzacr.zzgc().zza(str3, iZzbw, "_ev", zzjv.zza(str2, 40, true), str2 != null ? str2.length() : 0);
                return;
            }
            List<String> listW = f.w("_o", "_sn", "_sc", "_si");
            Bundle bundleZza = zzgc().zza(str2, bundle, listW, z3, true);
            zzik zzikVar = (bundleZza != null && bundleZza.containsKey("_sc") && bundleZza.containsKey("_si")) ? new zzik(bundleZza.getString("_sn"), bundleZza.getString("_sc"), Long.valueOf(bundleZza.getLong("_si")).longValue()) : null;
            zzik zzikVar2 = zzikVar == null ? zzikVarZzkk : zzikVar;
            ArrayList arrayList = new ArrayList();
            arrayList.add(bundleZza);
            long jNextLong = zzgc().zzku().nextLong();
            int i2 = 0;
            String[] strArr = (String[]) bundleZza.keySet().toArray(new String[bundle.size()]);
            Arrays.sort(strArr);
            int length2 = strArr.length;
            int i3 = 0;
            while (i3 < length2) {
                String str4 = strArr[i3];
                Object obj = bundleZza.get(str4);
                zzgc();
                Bundle[] bundleArrZze = zzjv.zze(obj);
                if (bundleArrZze != null) {
                    bundleZza.putInt(str4, bundleArrZze.length);
                    int i4 = 0;
                    while (true) {
                        int i5 = i4;
                        if (i5 >= bundleArrZze.length) {
                            break;
                        }
                        Bundle bundle2 = bundleArrZze[i5];
                        zzih.zza((zzig) zzikVar2, bundle2, true);
                        Bundle bundleZza2 = zzgc().zza("_ep", bundle2, listW, z3, false);
                        bundleZza2.putString("_en", str2);
                        bundleZza2.putLong("_eid", jNextLong);
                        bundleZza2.putString("_gn", str4);
                        bundleZza2.putInt("_ll", bundleArrZze.length);
                        bundleZza2.putInt("_i", i5);
                        arrayList.add(bundleZza2);
                        i4 = i5 + 1;
                    }
                    length = bundleArrZze.length + i2;
                } else {
                    length = i2;
                }
                i3++;
                i2 = length;
            }
            if (i2 != 0) {
                bundleZza.putLong("_eid", jNextLong);
                bundleZza.putInt("_epc", i2);
            }
            int i6 = 0;
            while (true) {
                int i7 = i6;
                if (i7 >= arrayList.size()) {
                    break;
                }
                Bundle bundle3 = (Bundle) arrayList.get(i7);
                String str5 = i7 != 0 ? "_ep" : str2;
                bundle3.putString("_o", str);
                Bundle bundleZzd = z2 ? zzgc().zzd(bundle3) : bundle3;
                zzgg().zziq().zze("Logging event (FE)", zzgb().zzbe(str2), zzgb().zzb(bundleZzd));
                zzfx().zzc(new zzeu(str5, new zzer(bundleZzd), str, j), str3);
                if (!zEquals) {
                    Iterator<e> it = this.zzaok.iterator();
                    while (it.hasNext()) {
                        it.next().b(str, str2, new Bundle(bundleZzd), j);
                    }
                }
                i6 = i7 + 1;
            }
            if (zzfy().zzkk() == null || !"_ae".equals(str2)) {
                return;
            }
            zzge().zzm(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzc(b bVar) {
        zzab();
        zzch();
        bf.checkNotNull(bVar);
        bf.id(bVar.mName);
        if (!this.zzacr.isEnabled()) {
            zzgg().zziq().log("Conditional property not cleared since Firebase Analytics is disabled");
            return;
        }
        try {
            zzfx().zzf(new zzef(bVar.bXq, bVar.bXr, new zzjs(bVar.mName, 0L, null, null), bVar.bXB, bVar.bXC, bVar.bXs, null, bVar.bXt, null, bVar.bXy, zzgc().zza(bVar.bXz, bVar.bXA, bVar.bXr, bVar.bXB, true, false)));
        } catch (IllegalArgumentException e) {
        }
    }

    private final List<b> zzf(String str, String str2, String str3) {
        if (zzgf().zzjg()) {
            zzgg().zzil().log("Cannot get conditional user properties from analytics worker thread");
            return Collections.emptyList();
        }
        zzgf();
        if (zzgg.isMainThread()) {
            zzgg().zzil().log("Cannot get conditional user properties from main thread");
            return Collections.emptyList();
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zzacr.zzgf().zzc(new zzhv(this, atomicReference, str, str2, str3));
            try {
                atomicReference.wait(5000L);
            } catch (InterruptedException e) {
                zzgg().zzin().zze("Interrupted waiting for get conditional user properties", str, e);
            }
        }
        List<zzef> list = (List) atomicReference.get();
        if (list == null) {
            zzgg().zzin().zzg("Timed out waiting for get conditional user properties", str);
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (zzef zzefVar : list) {
            b bVar = new b();
            bVar.bXq = str;
            bVar.bXr = str2;
            bVar.bXB = zzefVar.zzaem;
            bVar.mName = zzefVar.zzael.name;
            bVar.mValue = zzefVar.zzael.getValue();
            bVar.bXC = zzefVar.zzaen;
            bVar.bXs = zzefVar.zzaeo;
            if (zzefVar.zzaep != null) {
                bVar.bXu = zzefVar.zzaep.name;
                if (zzefVar.zzaep.zzafo != null) {
                    bVar.bXv = zzefVar.zzaep.zzafo.zzif();
                }
            }
            bVar.bXt = zzefVar.zzaeq;
            if (zzefVar.zzaer != null) {
                bVar.bXw = zzefVar.zzaer.name;
                if (zzefVar.zzaer.zzafo != null) {
                    bVar.bXx = zzefVar.zzaer.zzafo.zzif();
                }
            }
            bVar.bXD = zzefVar.zzael.zzaqu;
            bVar.bXy = zzefVar.zzaes;
            if (zzefVar.zzaet != null) {
                bVar.bXz = zzefVar.zzaet.name;
                if (zzefVar.zzaet.zzafo != null) {
                    bVar.bXA = zzefVar.zzaet.zzafo.zzif();
                }
            }
            arrayList.add(bVar);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzj(boolean z) {
        zzab();
        zzch();
        zzgg().zziq().zzg("Setting app measurement enabled (FE)", Boolean.valueOf(z));
        zzgh().setMeasurementEnabled(z);
        if (!zzgi().zzav(zzfv().zzah())) {
            zzfx().zzkm();
        } else if (!this.zzacr.isEnabled() || !this.zzaon) {
            zzfx().zzkm();
        } else {
            zzgg().zziq().log("Recording app launch after enabling measurement for the first time (FE)");
            zzkj();
        }
    }

    public final void clearConditionalUserProperty(String str, String str2, Bundle bundle) {
        zza((String) null, str, str2, bundle);
    }

    public final void clearConditionalUserPropertyAs(String str, String str2, String str3, Bundle bundle) {
        bf.id(str);
        zzfq();
        zza(str, str2, str3, bundle);
    }

    public final com.google.android.gms.f.f<String> getAppInstanceId() {
        try {
            String strZziw = zzgh().zziw();
            return strZziw != null ? j.G(strZziw) : j.a(zzgf().zzjh(), new zzhq(this));
        } catch (Exception e) {
            zzgg().zzin().log("Failed to schedule task for getAppInstanceId");
            return j.i(e);
        }
    }

    public final List<b> getConditionalUserProperties(String str, String str2) {
        return zzf(null, str, str2);
    }

    public final List<b> getConditionalUserPropertiesAs(String str, String str2, String str3) {
        bf.id(str);
        zzfq();
        return zzf(str, str2, str3);
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final Map<String, Object> getUserProperties(String str, String str2, boolean z) {
        return zzb(null, str, str2, z);
    }

    public final Map<String, Object> getUserPropertiesAs(String str, String str2, String str3, boolean z) {
        bf.id(str);
        zzfq();
        return zzb(str, str2, str3, z);
    }

    public final void registerOnMeasurementEventListener(e eVar) {
        zzch();
        bf.checkNotNull(eVar);
        if (this.zzaok.add(eVar)) {
            return;
        }
        zzgg().zzin().log("OnEventListener already registered");
    }

    public final void resetAnalyticsData() {
        zzgf().zzc(new zzhs(this));
    }

    public final void setConditionalUserProperty(b bVar) {
        bf.checkNotNull(bVar);
        b bVar2 = new b(bVar);
        if (!TextUtils.isEmpty(bVar2.bXq)) {
            zzgg().zzin().log("Package name should be null when calling setConditionalUserProperty");
        }
        bVar2.bXq = null;
        zza(bVar2);
    }

    public final void setConditionalUserPropertyAs(b bVar) {
        bf.checkNotNull(bVar);
        bf.id(bVar.bXq);
        zzfq();
        zza(new b(bVar));
    }

    public final void setEventInterceptor(d dVar) {
        zzab();
        zzch();
        if (dVar != null && dVar != this.zzaoj) {
            bf.a(this.zzaoj == null, "EventInterceptor already set.");
        }
        this.zzaoj = dVar;
    }

    public final void setMeasurementEnabled(boolean z) {
        zzch();
        zzgf().zzc(new zzib(this, z));
    }

    public final void setMinimumSessionDuration(long j) {
        zzgf().zzc(new zzic(this, j));
    }

    public final void setSessionTimeoutDuration(long j) {
        zzgf().zzc(new zzid(this, j));
    }

    public final void unregisterOnMeasurementEventListener(e eVar) {
        zzch();
        bf.checkNotNull(eVar);
        if (this.zzaok.remove(eVar)) {
            return;
        }
        zzgg().zzin().log("OnEventListener had not been registered");
    }

    public final void zza(String str, String str2, Bundle bundle) {
        zza(str, str2, bundle, true, this.zzaoj == null || zzjv.zzcb(str2), false, null);
    }

    public final void zza(String str, String str2, Bundle bundle, long j) {
        zza(str, str2, j, bundle, false, true, true, null);
    }

    public final void zza(String str, String str2, Bundle bundle, boolean z) {
        zza(str, str2, bundle, true, this.zzaoj == null || zzjv.zzcb(str2), true, null);
    }

    public final void zza(String str, String str2, Object obj) {
        bf.id(str);
        long jCurrentTimeMillis = zzbt().currentTimeMillis();
        int iZzby = zzgc().zzby(str2);
        if (iZzby != 0) {
            zzgc();
            this.zzacr.zzgc().zza(iZzby, "_ev", zzjv.zza(str2, 24, true), str2 != null ? str2.length() : 0);
            return;
        }
        if (obj == null) {
            zza(str, str2, jCurrentTimeMillis, (Object) null);
            return;
        }
        int iZzi = zzgc().zzi(str2, obj);
        if (iZzi != 0) {
            zzgc();
            this.zzacr.zzgc().zza(iZzi, "_ev", zzjv.zza(str2, 24, true), ((obj instanceof String) || (obj instanceof CharSequence)) ? String.valueOf(obj).length() : 0);
        } else {
            Object objZzj = zzgc().zzj(str2, obj);
            if (objZzj != null) {
                zza(str, str2, jCurrentTimeMillis, objZzj);
            }
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    final String zzae(long j) {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            zzgf().zzc(new zzhr(this, atomicReference));
            try {
                atomicReference.wait(j);
            } catch (InterruptedException e) {
                zzgg().zzin().log("Interrupted waiting for app instance id");
                return null;
            }
        }
        return (String) atomicReference.get();
    }

    final void zzbm(String str) {
        this.zzaom.set(str);
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ com.google.android.gms.common.d.e zzbt() {
        return super.zzbt();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzfq() {
        super.zzfq();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzdx zzfs() {
        return super.zzfs();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzee zzft() {
        return super.zzft();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzhm zzfu() {
        return super.zzfu();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfb zzfv() {
        return super.zzfv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzeo zzfw() {
        return super.zzfw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzil zzfx() {
        return super.zzfx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzih zzfy() {
        return super.zzfy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfc zzfz() {
        return super.zzfz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzei zzga() {
        return super.zzga();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfe zzgb() {
        return super.zzgb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzjv zzgc() {
        return super.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzgf zzgd() {
        return super.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzjk zzge() {
        return super.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzgg zzgf() {
        return super.zzgf();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfg zzgg() {
        return super.zzgg();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfr zzgh() {
        return super.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzeh zzgi() {
        return super.zzgi();
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final boolean zzhh() {
        return false;
    }

    public final String zziw() {
        return this.zzaom.get();
    }

    public final List<zzjs> zzk(boolean z) {
        zzch();
        zzgg().zziq().log("Fetching user attributes (FE)");
        if (zzgf().zzjg()) {
            zzgg().zzil().log("Cannot get all user properties from analytics worker thread");
            return Collections.emptyList();
        }
        zzgf();
        if (zzgg.isMainThread()) {
            zzgg().zzil().log("Cannot get all user properties from main thread");
            return Collections.emptyList();
        }
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            this.zzacr.zzgf().zzc(new zzhp(this, atomicReference, z));
            try {
                atomicReference.wait(5000L);
            } catch (InterruptedException e) {
                zzgg().zzin().zzg("Interrupted waiting for get user properties", e);
            }
        }
        List<zzjs> list = (List) atomicReference.get();
        if (list != null) {
            return list;
        }
        zzgg().zzin().log("Timed out waiting for get user properties");
        return Collections.emptyList();
    }

    public final Boolean zzke() {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            zzgf().zzc(new zzhn(this, atomicReference));
            try {
                atomicReference.wait(5000L);
            } catch (InterruptedException e) {
                zzgg().zzin().log("Interrupted waiting for boolean test flag value");
                return null;
            }
        }
        return (Boolean) atomicReference.get();
    }

    public final String zzkf() {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            zzgf().zzc(new zzhx(this, atomicReference));
            try {
                atomicReference.wait(5000L);
            } catch (InterruptedException e) {
                zzgg().zzin().log("Interrupted waiting for String test flag value");
                return null;
            }
        }
        return (String) atomicReference.get();
    }

    public final Long zzkg() {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            zzgf().zzc(new zzhy(this, atomicReference));
            try {
                atomicReference.wait(5000L);
            } catch (InterruptedException e) {
                zzgg().zzin().log("Interrupted waiting for long test flag value");
                return null;
            }
        }
        return (Long) atomicReference.get();
    }

    public final Integer zzkh() {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            zzgf().zzc(new zzhz(this, atomicReference));
            try {
                atomicReference.wait(5000L);
            } catch (InterruptedException e) {
                zzgg().zzin().log("Interrupted waiting for int test flag value");
                return null;
            }
        }
        return (Integer) atomicReference.get();
    }

    public final Double zzki() {
        AtomicReference atomicReference = new AtomicReference();
        synchronized (atomicReference) {
            zzgf().zzc(new zzia(this, atomicReference));
            try {
                atomicReference.wait(5000L);
            } catch (InterruptedException e) {
                zzgg().zzin().log("Interrupted waiting for double test flag value");
                return null;
            }
        }
        return (Double) atomicReference.get();
    }

    public final void zzkj() {
        zzab();
        zzch();
        if (this.zzacr.zzjk()) {
            zzfx().zzkj();
            this.zzaon = false;
            String strZziz = zzgh().zziz();
            if (TextUtils.isEmpty(strZziz)) {
                return;
            }
            zzfw().zzch();
            if (strZziz.equals(Build.VERSION.RELEASE)) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("_po", strZziz);
            zza("auto", "_ou", bundle);
        }
    }
}
