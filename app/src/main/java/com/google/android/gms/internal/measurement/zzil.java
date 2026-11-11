package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.d.e;
import com.google.android.gms.common.g;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.stats.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes.dex */
public final class zzil extends zzhk {
    private final zziz zzapr;
    private zzey zzaps;
    private volatile Boolean zzapt;
    private final zzem zzapu;
    private final zzjp zzapv;
    private final List<Runnable> zzapw;
    private final zzem zzapx;

    protected zzil(zzgl zzglVar) {
        super(zzglVar);
        this.zzapw = new ArrayList();
        this.zzapv = new zzjp(zzglVar.zzbt());
        this.zzapr = new zziz(this);
        this.zzapu = new zzim(this, zzglVar);
        this.zzapx = new zzir(this, zzglVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onServiceDisconnected(ComponentName componentName) {
        zzab();
        if (this.zzaps != null) {
            this.zzaps = null;
            zzgg().zzir().zzg("Disconnected from device MeasurementService", componentName);
            zzab();
            zzdf();
        }
    }

    static /* synthetic */ zzey zza(zzil zzilVar, zzey zzeyVar) {
        zzilVar.zzaps = null;
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzcu() {
        zzab();
        this.zzapv.start();
        this.zzapu.zzh(zzew.zzahr.get().longValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzcv() {
        zzab();
        if (isConnected()) {
            zzgg().zzir().log("Inactivity, disconnecting from the service");
            disconnect();
        }
    }

    private final void zzf(Runnable runnable) {
        zzab();
        if (isConnected()) {
            runnable.run();
        } else {
            if (this.zzapw.size() >= 1000) {
                zzgg().zzil().log("Discarding data. Max runnable queue size reached");
                return;
            }
            this.zzapw.add(runnable);
            this.zzapx.zzh(60000L);
            zzdf();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzko() {
        zzab();
        zzgg().zzir().zzg("Processing queued up service tasks", Integer.valueOf(this.zzapw.size()));
        Iterator<Runnable> it = this.zzapw.iterator();
        while (it.hasNext()) {
            try {
                it.next().run();
            } catch (Throwable th) {
                zzgg().zzil().zzg("Task exception while flushing queue", th);
            }
        }
        this.zzapw.clear();
        this.zzapx.cancel();
    }

    private final zzec zzl(boolean z) {
        return zzfv().zzbd(z ? zzgg().zzit() : null);
    }

    public final void disconnect() {
        zzab();
        zzch();
        try {
            a.aaY().a(getContext(), this.zzapr);
        } catch (IllegalArgumentException e) {
        } catch (IllegalStateException e2) {
        }
        this.zzaps = null;
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public final boolean isConnected() {
        zzab();
        zzch();
        return this.zzaps != null;
    }

    protected final void resetAnalyticsData() {
        zzab();
        zzch();
        zzec zzecVarZzl = zzl(false);
        zzfz().resetAnalyticsData();
        zzf(new zzin(this, zzecVarZzl));
    }

    protected final void zza(zzey zzeyVar) {
        zzab();
        bf.checkNotNull(zzeyVar);
        this.zzaps = zzeyVar;
        zzcu();
        zzko();
    }

    final void zza(zzey zzeyVar, AbstractSafeParcelable abstractSafeParcelable, zzec zzecVar) throws Throwable {
        zzab();
        zzch();
        int size = 100;
        for (int i = 0; i < 1001 && size == 100; i++) {
            ArrayList arrayList = new ArrayList();
            List<AbstractSafeParcelable> listZzp = zzfz().zzp(100);
            if (listZzp != null) {
                arrayList.addAll(listZzp);
                size = listZzp.size();
            } else {
                size = 0;
            }
            if (abstractSafeParcelable != null && size < 100) {
                arrayList.add(abstractSafeParcelable);
            }
            ArrayList arrayList2 = arrayList;
            int size2 = arrayList2.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj = arrayList2.get(i2);
                i2++;
                AbstractSafeParcelable abstractSafeParcelable2 = (AbstractSafeParcelable) obj;
                if (abstractSafeParcelable2 instanceof zzeu) {
                    try {
                        zzeyVar.zza((zzeu) abstractSafeParcelable2, zzecVar);
                    } catch (RemoteException e) {
                        zzgg().zzil().zzg("Failed to send event to the service", e);
                    }
                } else if (abstractSafeParcelable2 instanceof zzjs) {
                    try {
                        zzeyVar.zza((zzjs) abstractSafeParcelable2, zzecVar);
                    } catch (RemoteException e2) {
                        zzgg().zzil().zzg("Failed to send attribute to the service", e2);
                    }
                } else if (abstractSafeParcelable2 instanceof zzef) {
                    try {
                        zzeyVar.zza((zzef) abstractSafeParcelable2, zzecVar);
                    } catch (RemoteException e3) {
                        zzgg().zzil().zzg("Failed to send conditional property to the service", e3);
                    }
                } else {
                    zzgg().zzil().log("Discarding data. Unrecognized parcel type.");
                }
            }
        }
    }

    protected final void zza(zzig zzigVar) {
        zzab();
        zzch();
        zzf(new zziq(this, zzigVar));
    }

    public final void zza(AtomicReference<String> atomicReference) {
        zzab();
        zzch();
        zzf(new zzio(this, atomicReference, zzl(false)));
    }

    protected final void zza(AtomicReference<List<zzef>> atomicReference, String str, String str2, String str3) {
        zzab();
        zzch();
        zzf(new zziv(this, atomicReference, str, str2, str3, zzl(false)));
    }

    protected final void zza(AtomicReference<List<zzjs>> atomicReference, String str, String str2, String str3, boolean z) {
        zzab();
        zzch();
        zzf(new zziw(this, atomicReference, str, str2, str3, z, zzl(false)));
    }

    protected final void zza(AtomicReference<List<zzjs>> atomicReference, boolean z) {
        zzab();
        zzch();
        zzf(new zziy(this, atomicReference, zzl(false), z));
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    protected final void zzb(zzjs zzjsVar) {
        zzab();
        zzch();
        zzf(new zzix(this, zzfz().zza(zzjsVar), zzjsVar, zzl(true)));
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ e zzbt() {
        return super.zzbt();
    }

    protected final void zzc(zzeu zzeuVar, String str) {
        bf.checkNotNull(zzeuVar);
        zzab();
        zzch();
        zzf(new zzit(this, true, zzfz().zza(zzeuVar), zzeuVar, zzl(true), str));
    }

    final void zzdf() {
        boolean z;
        boolean z2;
        zzab();
        zzch();
        if (isConnected()) {
            return;
        }
        if (this.zzapt == null) {
            zzab();
            zzch();
            Boolean boolZzix = zzgh().zzix();
            if (boolZzix == null || !boolZzix.booleanValue()) {
                if (zzfv().zzij() != 1) {
                    zzgg().zzir().log("Checking service availability");
                    int iIsGooglePlayServicesAvailable = g.YD().isGooglePlayServicesAvailable(zzgc().getContext(), 12451);
                    switch (iIsGooglePlayServicesAvailable) {
                        case 0:
                            zzgg().zzir().log("Service available");
                            z = true;
                            z2 = true;
                            break;
                        case 1:
                            zzgg().zzir().log("Service missing");
                            z = true;
                            z2 = false;
                            break;
                        case 2:
                            zzgg().zziq().log("Service container out of date");
                            if (zzgc().zzkv() >= 12400) {
                                Boolean boolZzix2 = zzgh().zzix();
                                z2 = boolZzix2 == null || boolZzix2.booleanValue();
                                z = false;
                            } else {
                                z = true;
                                z2 = false;
                            }
                            break;
                        case 3:
                            zzgg().zzin().log("Service disabled");
                            z = false;
                            z2 = false;
                            break;
                        case 9:
                            zzgg().zzin().log("Service invalid");
                            z = false;
                            z2 = false;
                            break;
                        case 18:
                            zzgg().zzin().log("Service updating");
                            z = true;
                            z2 = true;
                            break;
                        default:
                            zzgg().zzin().zzg("Unexpected service status", Integer.valueOf(iIsGooglePlayServicesAvailable));
                            z = false;
                            z2 = false;
                            break;
                    }
                } else {
                    z = true;
                    z2 = true;
                }
                if (z) {
                    zzgh().zzf(z2);
                }
            } else {
                z2 = true;
            }
            this.zzapt = Boolean.valueOf(z2);
        }
        if (this.zzapt.booleanValue()) {
            this.zzapr.zzkp();
            return;
        }
        List<ResolveInfo> listQueryIntentServices = getContext().getPackageManager().queryIntentServices(new Intent().setClassName(getContext(), "com.google.android.gms.measurement.AppMeasurementService"), NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
        if (!(listQueryIntentServices != null && listQueryIntentServices.size() > 0)) {
            zzgg().zzil().log("Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest");
            return;
        }
        Intent intent = new Intent("com.google.android.gms.measurement.START");
        intent.setComponent(new ComponentName(getContext(), "com.google.android.gms.measurement.AppMeasurementService"));
        this.zzapr.zzc(intent);
    }

    protected final void zzf(zzef zzefVar) {
        bf.checkNotNull(zzefVar);
        zzab();
        zzch();
        zzf(new zziu(this, true, zzfz().zzc(zzefVar), new zzef(zzefVar), zzl(true), zzefVar));
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

    protected final void zzkj() {
        zzab();
        zzch();
        zzf(new zzip(this, zzl(true)));
    }

    protected final void zzkm() {
        zzab();
        zzch();
        zzf(new zzis(this, zzl(true)));
    }

    final Boolean zzkn() {
        return this.zzapt;
    }
}
