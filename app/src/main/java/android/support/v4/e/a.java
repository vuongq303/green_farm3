package android.support.v4.e;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: ArrayMap.java */
/* JADX INFO: loaded from: classes.dex */
public class a<K, V> extends r<K, V> implements Map<K, V> {
    j<K, V> gp;

    public a() {
    }

    public a(int i) {
        super(i);
    }

    private j<K, V> aG() {
        if (this.gp == null) {
            this.gp = new b(this);
        }
        return this.gp;
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        ensureCapacity(this.mSize + map.size());
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public boolean retainAll(Collection<?> collection) {
        return j.c(this, collection);
    }

    @Override // java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        return aG().aM();
    }

    @Override // java.util.Map
    public Set<K> keySet() {
        return aG().getKeySet();
    }

    @Override // java.util.Map
    public Collection<V> values() {
        return aG().aN();
    }
}
