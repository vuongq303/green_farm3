package com.google.android.gms.internal.ads;

import com.gameloft.glads.AndroidWebView;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzaum<P> {
    private static final Charset UTF_8 = Charset.forName(AndroidWebView.UTF_8);
    private ConcurrentMap<String, List<zzaun<P>>> zzdhk = new ConcurrentHashMap();
    private zzaun<P> zzdhl;

    protected final zzaun<P> zza(P p, zzaxr.zzb zzbVar) throws GeneralSecurityException {
        byte[] bArrArray;
        switch (zzbVar.zzzs()) {
            case LEGACY:
            case CRUNCHY:
                bArrArray = ByteBuffer.allocate(5).put((byte) 0).putInt(zzbVar.zzzr()).array();
                break;
            case TINK:
                bArrArray = ByteBuffer.allocate(5).put((byte) 1).putInt(zzbVar.zzzr()).array();
                break;
            case RAW:
                bArrArray = zzauc.zzdhg;
                break;
            default:
                throw new GeneralSecurityException("unknown output prefix type");
        }
        zzaun<P> zzaunVar = new zzaun<>(p, bArrArray, zzbVar.zzzq(), zzbVar.zzzs());
        ArrayList arrayList = new ArrayList();
        arrayList.add(zzaunVar);
        String str = new String(zzaunVar.zzwj(), UTF_8);
        List<zzaun<P>> listPut = this.zzdhk.put(str, Collections.unmodifiableList(arrayList));
        if (listPut != null) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.addAll(listPut);
            arrayList2.add(zzaunVar);
            this.zzdhk.put(str, Collections.unmodifiableList(arrayList2));
        }
        return zzaunVar;
    }

    protected final void zza(zzaun<P> zzaunVar) {
        this.zzdhl = zzaunVar;
    }

    public final zzaun<P> zzwh() {
        return this.zzdhl;
    }
}
