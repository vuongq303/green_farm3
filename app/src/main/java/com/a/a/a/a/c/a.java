package com.a.a.a.a.c;

import com.a.a.a.a.b.i;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public class a {
    private static a cek = new a();
    private final ArrayList<i> cel = new ArrayList<>();
    private final ArrayList<i> cem = new ArrayList<>();

    private a() {
    }

    public static a ahU() {
        return cek;
    }

    public void a(i iVar) {
        this.cel.add(iVar);
    }

    public boolean ahT() {
        return this.cem.size() > 0;
    }

    public Collection<i> ahV() {
        return Collections.unmodifiableCollection(this.cel);
    }

    public Collection<i> ahW() {
        return Collections.unmodifiableCollection(this.cem);
    }

    public void b(i iVar) {
        boolean zAhT = ahT();
        this.cem.add(iVar);
        if (zAhT) {
            return;
        }
        h.aib().b();
    }

    public void c(i iVar) {
        boolean zAhT = ahT();
        this.cel.remove(iVar);
        this.cem.remove(iVar);
        if (!zAhT || ahT()) {
            return;
        }
        h.aib().c();
    }
}
