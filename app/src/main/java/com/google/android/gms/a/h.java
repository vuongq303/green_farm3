package com.google.android.gms.a;

import android.net.Uri;
import android.text.TextUtils;
import android.util.LogPrinter;
import com.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public final class h implements u {
    private static final Uri zzqn;
    private final LogPrinter bEs = new LogPrinter(4, "GA/LogCatTransport");

    static {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(ShareConstants.MEDIA_URI);
        builder.authority("local");
        zzqn = builder.build();
    }

    @Override // com.google.android.gms.a.u
    public final Uri Xo() {
        return zzqn;
    }

    @Override // com.google.android.gms.a.u
    public final void b(j jVar) {
        ArrayList arrayList = new ArrayList(jVar.Xq());
        Collections.sort(arrayList, new i(this));
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            String string = ((l) obj).toString();
            if (!TextUtils.isEmpty(string)) {
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(string);
            }
        }
        this.bEs.println(sb.toString());
    }
}
