package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class zzci {
    private static final String[] zzrc = {"/aclk", "/pcs/click"};
    private String zzqy = "googleads.g.doubleclick.net";
    private String zzqz = "/pagead/ads";
    private String zzra = "ad.doubleclick.net";
    private String[] zzrb = {".doubleclick.net", ".googleadservices.com", ".googlesyndication.com"};
    private zzce zzrd;

    public zzci(zzce zzceVar) {
        this.zzrd = zzceVar;
    }

    private final Uri zza(Uri uri, Context context, String str, boolean z, View view, Activity activity) throws zzcj {
        try {
            boolean zZza = zza(uri);
            if (zZza) {
                if (uri.toString().contains("dc_ms=")) {
                    throw new zzcj("Parameter already exists: dc_ms");
                }
            } else if (uri.getQueryParameter("ms") != null) {
                throw new zzcj("Query parameter already exists: ms");
            }
            String strZza = z ? this.zzrd.zza(context, str, view, activity) : this.zzrd.zza(context);
            if (!zZza) {
                String string = uri.toString();
                int iIndexOf = string.indexOf("&adurl");
                if (iIndexOf == -1) {
                    iIndexOf = string.indexOf("?adurl");
                }
                return iIndexOf != -1 ? Uri.parse(string.substring(0, iIndexOf + 1) + "ms=" + strZza + "&" + string.substring(iIndexOf + 1)) : uri.buildUpon().appendQueryParameter("ms", strZza).build();
            }
            String string2 = uri.toString();
            int iIndexOf2 = string2.indexOf(";adurl");
            if (iIndexOf2 != -1) {
                return Uri.parse(string2.substring(0, iIndexOf2 + 1) + "dc_ms=" + strZza + ";" + string2.substring(iIndexOf2 + 1));
            }
            String encodedPath = uri.getEncodedPath();
            int iIndexOf3 = string2.indexOf(encodedPath);
            return Uri.parse(string2.substring(0, encodedPath.length() + iIndexOf3) + ";dc_ms=" + strZza + ";" + string2.substring(encodedPath.length() + iIndexOf3));
        } catch (UnsupportedOperationException e) {
            throw new zzcj("Provided Uri is not in a valid state");
        }
    }

    private final boolean zza(Uri uri) {
        if (uri == null) {
            throw new NullPointerException();
        }
        try {
            return uri.getHost().equals(this.zzra);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public final Uri zza(Uri uri, Context context) {
        return zza(uri, context, null, false, null, null);
    }

    public final Uri zza(Uri uri, Context context, View view, Activity activity) throws zzcj {
        try {
            return zza(uri, context, uri.getQueryParameter("ai"), true, view, activity);
        } catch (UnsupportedOperationException e) {
            throw new zzcj("Provided Uri is not in a valid state");
        }
    }

    public final void zza(MotionEvent motionEvent) {
        this.zzrd.zza(motionEvent);
    }

    public final zzce zzaa() {
        return this.zzrd;
    }

    public final boolean zzb(Uri uri) {
        if (uri == null) {
            throw new NullPointerException();
        }
        try {
            String host = uri.getHost();
            for (String str : this.zzrb) {
                if (host.endsWith(str)) {
                    return true;
                }
            }
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public final boolean zzc(Uri uri) {
        if (!zzb(uri)) {
            return false;
        }
        for (String str : zzrc) {
            if (uri.getPath().endsWith(str)) {
                return true;
            }
        }
        return false;
    }
}
