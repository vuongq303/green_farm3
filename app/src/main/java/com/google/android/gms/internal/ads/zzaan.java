package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.RemoteException;
import android.view.View;
import android.webkit.WebView;
import com.google.android.gms.c.d;
import com.google.android.gms.d.a;
import com.google.android.gms.d.c;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaan {
    private zzatn zzbxh;
    private static final Object sLock = new Object();

    @GuardedBy("sLock")
    private static boolean zzsh = false;

    @GuardedBy("sLock")
    private static boolean zzbxg = false;

    private final void zzj(Context context) {
        synchronized (sLock) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbet)).booleanValue() && !zzbxg) {
                try {
                    zzbxg = true;
                    this.zzbxh = zzato.zzab(a.a(context, a.bRs, "com.google.android.gms.ads.dynamite").ip("com.google.android.gms.ads.omid.DynamiteOmid"));
                } catch (c e) {
                    zzane.zzd("#007 Could not call remote method.", e);
                }
            }
        }
    }

    public final String getVersion(Context context) {
        if (!((Boolean) zzkb.zzik().zzd(zznk.zzbet)).booleanValue()) {
            return null;
        }
        try {
            zzj(context);
            String strValueOf = String.valueOf(this.zzbxh.getVersion());
            return strValueOf.length() != 0 ? "a.".concat(strValueOf) : new String("a.");
        } catch (RemoteException | NullPointerException e) {
            zzane.zzd("#007 Could not call remote method.", e);
            return null;
        }
    }

    public final com.google.android.gms.c.a zza(String str, WebView webView, String str2, String str3, String str4) {
        synchronized (sLock) {
            if (!((Boolean) zzkb.zzik().zzd(zznk.zzbet)).booleanValue() || !zzsh) {
                return null;
            }
            try {
                return this.zzbxh.zza(str, d.F(webView), str2, str3, str4);
            } catch (RemoteException | NullPointerException e) {
                zzane.zzd("#007 Could not call remote method.", e);
                return null;
            }
        }
    }

    public final void zza(com.google.android.gms.c.a aVar, View view) {
        synchronized (sLock) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbet)).booleanValue() && zzsh) {
                try {
                    this.zzbxh.zza(aVar, d.F(view));
                } catch (RemoteException | NullPointerException e) {
                    zzane.zzd("#007 Could not call remote method.", e);
                }
            }
        }
    }

    public final boolean zzi(Context context) {
        synchronized (sLock) {
            if (!((Boolean) zzkb.zzik().zzd(zznk.zzbet)).booleanValue()) {
                return false;
            }
            if (zzsh) {
                return true;
            }
            try {
                zzj(context);
                boolean zZzy = this.zzbxh.zzy(d.F(context));
                zzsh = zZzy;
                return zZzy;
            } catch (RemoteException | NullPointerException e) {
                zzane.zzd("#007 Could not call remote method.", e);
                return false;
            }
        }
    }

    public final void zzm(com.google.android.gms.c.a aVar) {
        synchronized (sLock) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbet)).booleanValue() && zzsh) {
                try {
                    this.zzbxh.zzm(aVar);
                } catch (RemoteException | NullPointerException e) {
                    zzane.zzd("#007 Could not call remote method.", e);
                }
            }
        }
    }

    public final void zzn(com.google.android.gms.c.a aVar) {
        synchronized (sLock) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbet)).booleanValue() && zzsh) {
                try {
                    this.zzbxh.zzn(aVar);
                } catch (RemoteException | NullPointerException e) {
                    zzane.zzd("#007 Could not call remote method.", e);
                }
            }
        }
    }
}
