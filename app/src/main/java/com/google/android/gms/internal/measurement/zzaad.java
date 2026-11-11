package com.google.android.gms.internal.measurement;

import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
class zzaad<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private boolean zzbmk;
    private final int zzbwi;
    private List<zzaai> zzbwj;
    private Map<K, V> zzbwk;
    private volatile zzaak zzbwl;
    private Map<K, V> zzbwm;

    private zzaad(int i) {
        this.zzbwi = i;
        this.zzbwj = Collections.emptyList();
        this.zzbwk = Collections.emptyMap();
        this.zzbwm = Collections.emptyMap();
    }

    /* synthetic */ zzaad(int i, zzaae zzaaeVar) {
        this(i);
    }

    private final int zza(K k) {
        int i = 0;
        int size = this.zzbwj.size() - 1;
        if (size >= 0) {
            int iCompareTo = k.compareTo((Comparable) this.zzbwj.get(size).getKey());
            if (iCompareTo > 0) {
                return -(size + 2);
            }
            if (iCompareTo == 0) {
                return size;
            }
        }
        int i2 = size;
        while (i <= i2) {
            int i3 = (i + i2) / 2;
            int iCompareTo2 = k.compareTo((Comparable) this.zzbwj.get(i3).getKey());
            if (iCompareTo2 < 0) {
                i2 = i3 - 1;
            } else {
                if (iCompareTo2 <= 0) {
                    return i3;
                }
                i = i3 + 1;
            }
        }
        return -(i + 1);
    }

    static <FieldDescriptorType extends zzyt<FieldDescriptorType>> zzaad<FieldDescriptorType, Object> zzai(int i) {
        return new zzaae(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final V zzak(int i) {
        zzvd();
        V v = (V) this.zzbwj.remove(i).getValue();
        if (!this.zzbwk.isEmpty()) {
            Iterator<Map.Entry<K, V>> it = zzve().entrySet().iterator();
            this.zzbwj.add(new zzaai(this, it.next()));
            it.remove();
        }
        return v;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzvd() {
        if (this.zzbmk) {
            throw new UnsupportedOperationException();
        }
    }

    private final SortedMap<K, V> zzve() {
        zzvd();
        if (this.zzbwk.isEmpty() && !(this.zzbwk instanceof TreeMap)) {
            this.zzbwk = new TreeMap();
            this.zzbwm = ((TreeMap) this.zzbwk).descendingMap();
        }
        return (SortedMap) this.zzbwk;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        zzvd();
        if (!this.zzbwj.isEmpty()) {
            this.zzbwj.clear();
        }
        if (this.zzbwk.isEmpty()) {
            return;
        }
        this.zzbwk.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return zza(comparable) >= 0 || this.zzbwk.containsKey(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        if (this.zzbwl == null) {
            this.zzbwl = new zzaak(this, null);
        }
        return this.zzbwl;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzaad)) {
            return super.equals(obj);
        }
        zzaad zzaadVar = (zzaad) obj;
        int size = size();
        if (size != zzaadVar.size()) {
            return false;
        }
        int iZzvb = zzvb();
        if (iZzvb != zzaadVar.zzvb()) {
            return entrySet().equals(zzaadVar.entrySet());
        }
        for (int i = 0; i < iZzvb; i++) {
            if (!zzaj(i).equals(zzaadVar.zzaj(i))) {
                return false;
            }
        }
        if (iZzvb != size) {
            return this.zzbwk.equals(zzaadVar.zzbwk);
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int iZza = zza(comparable);
        return iZza >= 0 ? (V) this.zzbwj.get(iZza).getValue() : this.zzbwk.get(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int hashCode() {
        int iZzvb = zzvb();
        int iHashCode = 0;
        for (int i = 0; i < iZzvb; i++) {
            iHashCode += this.zzbwj.get(i).hashCode();
        }
        return this.zzbwk.size() > 0 ? this.zzbwk.hashCode() + iHashCode : iHashCode;
    }

    public final boolean isImmutable() {
        return this.zzbmk;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        zzvd();
        Comparable comparable = (Comparable) obj;
        int iZza = zza(comparable);
        if (iZza >= 0) {
            return zzak(iZza);
        }
        if (this.zzbwk.isEmpty()) {
            return null;
        }
        return this.zzbwk.remove(comparable);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.zzbwj.size() + this.zzbwk.size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
    public final V put(K k, V v) {
        zzvd();
        int iZza = zza(k);
        if (iZza >= 0) {
            return (V) this.zzbwj.get(iZza).setValue(v);
        }
        zzvd();
        if (this.zzbwj.isEmpty() && !(this.zzbwj instanceof ArrayList)) {
            this.zzbwj = new ArrayList(this.zzbwi);
        }
        int i = -(iZza + 1);
        if (i >= this.zzbwi) {
            return zzve().put(k, v);
        }
        if (this.zzbwj.size() == this.zzbwi) {
            zzaai zzaaiVarRemove = this.zzbwj.remove(this.zzbwi - 1);
            zzve().put((Comparable) zzaaiVarRemove.getKey(), zzaaiVarRemove.getValue());
        }
        this.zzbwj.add(i, new zzaai(this, k, v));
        return null;
    }

    public final Map.Entry<K, V> zzaj(int i) {
        return this.zzbwj.get(i);
    }

    public void zzqy() {
        if (this.zzbmk) {
            return;
        }
        this.zzbwk = this.zzbwk.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzbwk);
        this.zzbwm = this.zzbwm.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.zzbwm);
        this.zzbmk = true;
    }

    public final int zzvb() {
        return this.zzbwj.size();
    }

    public final Iterable<Map.Entry<K, V>> zzvc() {
        return this.zzbwk.isEmpty() ? zzaaf.zzvf() : this.zzbwk.entrySet();
    }
}
