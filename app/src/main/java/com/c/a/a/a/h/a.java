package com.c.a.a.a.h;

import android.view.View;
import com.c.a.a.a.g.e;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/* JADX INFO: compiled from: AvidAdViewCache.java */
/* JADX INFO: loaded from: classes.dex */
public class a {
    private final com.c.a.a.a.e.a ckm;
    private final HashMap<View, String> ckn = new HashMap<>();
    private final HashMap<View, ArrayList<String>> cko = new HashMap<>();
    private final HashSet<View> ckp = new HashSet<>();
    private final HashSet<String> ckq = new HashSet<>();
    private final HashSet<String> ckr = new HashSet<>();
    private boolean cks;

    public a(com.c.a.a.a.e.a aVar) {
        this.ckm = aVar;
    }

    public HashSet<String> ajz() {
        return this.ckq;
    }

    public HashSet<String> ajA() {
        return this.ckr;
    }

    public void prepare() {
        for (com.c.a.a.a.f.a.a aVar : this.ckm.aiX()) {
            View view = aVar.getView();
            if (aVar.isActive() && view != null) {
                if (ae(view)) {
                    this.ckq.add(aVar.aiZ());
                    this.ckn.put(view, aVar.aiZ());
                    d(aVar);
                } else {
                    this.ckr.add(aVar.aiZ());
                }
            }
        }
    }

    private boolean ae(View view) {
        if (!view.hasWindowFocus()) {
            return false;
        }
        HashSet hashSet = new HashSet();
        while (view != null) {
            if (!e.ad(view)) {
                return false;
            }
            hashSet.add(view);
            Object parent = view.getParent();
            view = parent instanceof View ? (View) parent : null;
        }
        this.ckp.addAll(hashSet);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void d(com.c.a.a.a.f.a.a aVar) {
        for (com.c.a.a.a.i.b bVar : aVar.ajg().ajo()) {
            if (!bVar.isEmpty()) {
                a((View) bVar.get(), aVar);
            }
        }
    }

    private void a(View view, com.c.a.a.a.f.a.a aVar) {
        ArrayList<String> arrayList = this.cko.get(view);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.cko.put(view, arrayList);
        }
        arrayList.add(aVar.aiZ());
    }

    public void bM() {
        this.ckn.clear();
        this.cko.clear();
        this.ckp.clear();
        this.ckq.clear();
        this.ckr.clear();
        this.cks = false;
    }

    public void ajB() {
        this.cks = true;
    }

    public String af(View view) {
        if (this.ckn.size() == 0) {
            return null;
        }
        String str = this.ckn.get(view);
        if (str != null) {
            this.ckn.remove(view);
            return str;
        }
        return str;
    }

    public ArrayList<String> ag(View view) {
        if (this.cko.size() == 0) {
            return null;
        }
        ArrayList<String> arrayList = this.cko.get(view);
        if (arrayList != null) {
            this.cko.remove(view);
            Collections.sort(arrayList);
            return arrayList;
        }
        return arrayList;
    }

    public c ah(View view) {
        if (this.ckp.contains(view)) {
            return c.ROOT_VIEW;
        }
        return this.cks ? c.OBSTRUCTION_VIEW : c.UNDERLYING_VIEW;
    }
}
