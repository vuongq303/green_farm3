package com.google.android.gms.internal.measurement;

import com.facebook.R;
import com.google.android.gms.internal.measurement.zzabd;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzabe<M extends zzabd<M>, T> {
    public final int tag;
    private final int type;
    protected final Class<T> zzbzi;
    protected final boolean zzbzj;
    private final zzyv<?, ?> zzbzk;

    private zzabe(int i, Class<T> cls, int i2, boolean z) {
        this(11, cls, null, 810, false);
    }

    private zzabe(int i, Class<T> cls, zzyv<?, ?> zzyvVar, int i2, boolean z) {
        this.type = i;
        this.zzbzi = cls;
        this.tag = i2;
        this.zzbzj = false;
        this.zzbzk = null;
    }

    public static <M extends zzabd<M>, T extends zzabj> zzabe<M, T> zza(int i, Class<T> cls, long j) {
        return new zzabe<>(11, cls, 810, false);
    }

    private final Object zzf(zzaba zzabaVar) {
        Class componentType = this.zzbzj ? this.zzbzi.getComponentType() : this.zzbzi;
        try {
            switch (this.type) {
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    zzabj zzabjVar = (zzabj) componentType.newInstance();
                    zzabaVar.zza(zzabjVar, this.tag >>> 3);
                    return zzabjVar;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    zzabj zzabjVar2 = (zzabj) componentType.newInstance();
                    zzabaVar.zza(zzabjVar2);
                    return zzabjVar2;
                default:
                    throw new IllegalArgumentException(new StringBuilder(24).append("Unknown type ").append(this.type).toString());
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading extension field", e);
        } catch (IllegalAccessException e2) {
            String strValueOf = String.valueOf(componentType);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(strValueOf).length() + 33).append("Error creating instance of class ").append(strValueOf).toString(), e2);
        } catch (InstantiationException e3) {
            String strValueOf2 = String.valueOf(componentType);
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(strValueOf2).length() + 33).append("Error creating instance of class ").append(strValueOf2).toString(), e3);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzabe)) {
            return false;
        }
        zzabe zzabeVar = (zzabe) obj;
        return this.type == zzabeVar.type && this.zzbzi == zzabeVar.zzbzi && this.tag == zzabeVar.tag && this.zzbzj == zzabeVar.zzbzj;
    }

    public final int hashCode() {
        return (this.zzbzj ? 1 : 0) + ((((((this.type + 1147) * 31) + this.zzbzi.hashCode()) * 31) + this.tag) * 31);
    }

    protected final void zza(Object obj, zzabb zzabbVar) {
        try {
            zzabbVar.zzat(this.tag);
            switch (this.type) {
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    int i = this.tag >>> 3;
                    ((zzabj) obj).zza(zzabbVar);
                    zzabbVar.zzg(i, 4);
                    return;
                case R.styleable.CardView_contentPaddingTop /* 11 */:
                    zzabbVar.zzb((zzabj) obj);
                    return;
                default:
                    throw new IllegalArgumentException(new StringBuilder(24).append("Unknown type ").append(this.type).toString());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    final T zzi(List<zzabl> list) {
        if (list == null) {
            return null;
        }
        if (!this.zzbzj) {
            if (list.isEmpty()) {
                return null;
            }
            return this.zzbzi.cast(zzf(zzaba.zzj(list.get(list.size() - 1).zzbto)));
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            zzabl zzablVar = list.get(i);
            if (zzablVar.zzbto.length != 0) {
                arrayList.add(zzf(zzaba.zzj(zzablVar.zzbto)));
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            return null;
        }
        T tCast = this.zzbzi.cast(Array.newInstance(this.zzbzi.getComponentType(), size));
        for (int i2 = 0; i2 < size; i2++) {
            Array.set(tCast, i2, arrayList.get(i2));
        }
        return tCast;
    }

    protected final int zzx(Object obj) {
        int i = this.tag >>> 3;
        switch (this.type) {
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                return (zzabb.zzas(i) << 1) + ((zzabj) obj).zzwg();
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                return zzabb.zzb(i, (zzabj) obj);
            default:
                throw new IllegalArgumentException(new StringBuilder(24).append("Unknown type ").append(this.type).toString());
        }
    }
}
