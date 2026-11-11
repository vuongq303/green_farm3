package com.google.android.gms.internal.ads;

import android.content.Context;
import com.google.android.gms.common.d.s;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
final class zzaiw {
    private static final Map<String, String> zzcnl;
    private final List<String> zzcnm;
    private final zzaii zzcnn;
    private final Context zzrt;

    static {
        HashMap map = new HashMap();
        if (s.abC()) {
            map.put("android.webkit.resource.AUDIO_CAPTURE", "android.permission.RECORD_AUDIO");
            map.put("android.webkit.resource.VIDEO_CAPTURE", "android.permission.CAMERA");
        }
        zzcnl = map;
    }

    zzaiw(Context context, List<String> list, zzaii zzaiiVar) {
        this.zzrt = context;
        this.zzcnm = list;
        this.zzcnn = zzaiiVar;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0043  */
    final List<String> zzc(String[] strArr) {
        boolean z;
        boolean z2;
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            Iterator<String> it = this.zzcnm.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                String next = it.next();
                if (next.equals(str)) {
                    z = true;
                    break;
                }
                String strValueOf = String.valueOf("android.webkit.resource.");
                String strValueOf2 = String.valueOf(next);
                if ((strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf)).equals(str)) {
                    z = true;
                    break;
                }
            }
            if (z) {
                if (zzcnl.containsKey(str)) {
                    com.google.android.gms.ads.internal.zzbv.zzek();
                    if (zzakk.zzl(this.zzrt, zzcnl.get(str))) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                } else {
                    z2 = true;
                }
                if (z2) {
                    arrayList.add(str);
                } else {
                    this.zzcnn.zzch(str);
                }
            } else {
                this.zzcnn.zzcg(str);
            }
        }
        return arrayList;
    }
}
