package com.google.android.gms.internal.measurement;

import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.Utility;
import com.google.android.gms.common.internal.bf;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class zzch {
    private final List<zzbo> zzaae;
    private final long zzaaf;
    private final long zzaag;
    private final int zzaah;
    private final boolean zzaai;
    private final String zzaaj;
    private final Map<String, String> zzsi;

    public zzch(zzaq zzaqVar, Map<String, String> map, long j, boolean z) {
        this(zzaqVar, map, j, z, 0L, 0, null);
    }

    public zzch(zzaq zzaqVar, Map<String, String> map, long j, boolean z, long j2, int i) {
        this(zzaqVar, map, j, z, j2, i, null);
    }

    public zzch(zzaq zzaqVar, Map<String, String> map, long j, boolean z, long j2, int i, List<zzbo> list) {
        String value;
        String strZza;
        String strZza2;
        bf.checkNotNull(zzaqVar);
        bf.checkNotNull(map);
        this.zzaag = j;
        this.zzaai = z;
        this.zzaaf = j2;
        this.zzaah = i;
        this.zzaae = list != null ? list : Collections.emptyList();
        if (list == null) {
            value = null;
            break;
        }
        Iterator<zzbo> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                value = null;
                break;
            }
            zzbo next = it.next();
            if ("appendVersion".equals(next.getId())) {
                value = next.getValue();
                break;
            }
        }
        this.zzaaj = TextUtils.isEmpty(value) ? null : value;
        HashMap map2 = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (zzc(entry.getKey()) && (strZza2 = zza(zzaqVar, entry.getKey())) != null) {
                map2.put(strZza2, zzb(zzaqVar, entry.getValue()));
            }
        }
        for (Map.Entry<String, String> entry2 : map.entrySet()) {
            if (!zzc(entry2.getKey()) && (strZza = zza(zzaqVar, entry2.getKey())) != null) {
                map2.put(strZza, zzb(zzaqVar, entry2.getValue()));
            }
        }
        if (!TextUtils.isEmpty(this.zzaaj)) {
            zzdd.zzb(map2, "_v", this.zzaaj);
            if (this.zzaaj.equals("ma4.0.0") || this.zzaaj.equals("ma4.0.1")) {
                map2.remove("adid");
            }
        }
        this.zzsi = Collections.unmodifiableMap(map2);
    }

    private static String zza(zzaq zzaqVar, Object obj) {
        if (obj == null) {
            return null;
        }
        String string = obj.toString();
        if (string.startsWith("&")) {
            string = string.substring(1);
        }
        int length = string.length();
        if (length > 256) {
            string = string.substring(0, 256);
            zzaqVar.zzc("Hit param name is too long and will be trimmed", Integer.valueOf(length), string);
        }
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    private static String zzb(zzaq zzaqVar, Object obj) {
        String string = obj == null ? "" : obj.toString();
        int length = string.length();
        if (length <= 8192) {
            return string;
        }
        String strSubstring = string.substring(0, Utility.DEFAULT_STREAM_BUFFER_SIZE);
        zzaqVar.zzc("Hit param value is too long and will be trimmed", Integer.valueOf(length), strSubstring);
        return strSubstring;
    }

    private static boolean zzc(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj.toString().startsWith("&");
    }

    private final String zzd(String str, String str2) {
        bf.id(str);
        bf.b(!str.startsWith("&"), "Short param name required");
        String str3 = this.zzsi.get(str);
        return str3 != null ? str3 : str2;
    }

    public final List<zzbo> getCommands() {
        return this.zzaae;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ht=").append(this.zzaag);
        if (this.zzaaf != 0) {
            sb.append(", dbId=").append(this.zzaaf);
        }
        if (this.zzaah != 0) {
            sb.append(", appUID=").append(this.zzaah);
        }
        ArrayList arrayList = new ArrayList(this.zzsi.keySet());
        Collections.sort(arrayList);
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            String str = (String) obj;
            sb.append(", ");
            sb.append(str);
            sb.append("=");
            sb.append(this.zzsi.get(str));
        }
        return sb.toString();
    }

    public final Map<String, String> zzcs() {
        return this.zzsi;
    }

    public final int zzel() {
        return this.zzaah;
    }

    public final long zzem() {
        return this.zzaaf;
    }

    public final long zzen() {
        return this.zzaag;
    }

    public final boolean zzeo() {
        return this.zzaai;
    }

    public final long zzep() {
        return zzdd.zzaf(zzd("_s", AppEventsConstants.EVENT_PARAM_VALUE_NO));
    }

    public final String zzeq() {
        return zzd("_m", "");
    }
}
