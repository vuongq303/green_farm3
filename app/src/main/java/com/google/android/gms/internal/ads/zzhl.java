package com.google.android.gms.internal.ads;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzhl extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzhl> CREATOR = new zzhm();
    public final String url;
    private final long zzajv;
    private final String zzajw;
    private final String zzajx;
    private final String zzajy;
    private final Bundle zzajz;
    private final boolean zzaka;
    private long zzakb;

    zzhl(String str, long j, String str2, String str3, String str4, Bundle bundle, boolean z, long j2) {
        this.url = str;
        this.zzajv = j;
        this.zzajw = str2 == null ? "" : str2;
        this.zzajx = str3 == null ? "" : str3;
        this.zzajy = str4 == null ? "" : str4;
        this.zzajz = bundle == null ? new Bundle() : bundle;
        this.zzaka = z;
        this.zzakb = j2;
    }

    public static zzhl zzaa(String str) {
        return zzd(Uri.parse(str));
    }

    public static zzhl zzd(Uri uri) {
        try {
            if (!"gcache".equals(uri.getScheme())) {
                return null;
            }
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments.size() != 2) {
                zzakb.zzdk(new StringBuilder(62).append("Expected 2 path parts for namespace and id, found :").append(pathSegments.size()).toString());
                return null;
            }
            String str = pathSegments.get(0);
            String str2 = pathSegments.get(1);
            String host = uri.getHost();
            String queryParameter = uri.getQueryParameter("url");
            boolean zEquals = AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(uri.getQueryParameter("read_only"));
            String queryParameter2 = uri.getQueryParameter("expiration");
            long j = queryParameter2 != null ? Long.parseLong(queryParameter2) : 0L;
            Bundle bundle = new Bundle();
            for (String str3 : com.google.android.gms.ads.internal.zzbv.zzem().zzh(uri)) {
                if (str3.startsWith("tag.")) {
                    bundle.putString(str3.substring(4), uri.getQueryParameter(str3));
                }
            }
            return new zzhl(queryParameter, j, host, str, str2, bundle, zEquals, 0L);
        } catch (NullPointerException | NumberFormatException e) {
            zzakb.zzc("Unable to parse Uri into cache offering.", e);
            return null;
        }
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 2, this.url, false);
        c.a(parcel, 3, this.zzajv);
        c.a(parcel, 4, this.zzajw, false);
        c.a(parcel, 5, this.zzajx, false);
        c.a(parcel, 6, this.zzajy, false);
        c.a(parcel, 7, this.zzajz, false);
        c.a(parcel, 8, this.zzaka);
        c.a(parcel, 9, this.zzakb);
        c.G(parcel, iX);
    }
}
