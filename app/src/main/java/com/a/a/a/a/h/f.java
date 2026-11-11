package com.a.a.a.a.h;

import android.view.View;
import com.a.a.a.a.b.i;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class f {
    private final HashMap<View, String> cfb = new HashMap<>();
    private final HashMap<View, ArrayList<String>> cfc = new HashMap<>();
    private final HashSet<View> cfd = new HashSet<>();
    private final HashSet<String> cfe = new HashSet<>();
    private final HashSet<String> cff = new HashSet<>();
    private boolean f;

    private boolean X(View view) {
        if (!view.hasWindowFocus()) {
            return false;
        }
        HashSet hashSet = new HashSet();
        while (view != null) {
            if (!com.a.a.a.a.e.g.X(view)) {
                return false;
            }
            hashSet.add(view);
            Object parent = view.getParent();
            view = parent instanceof View ? (View) parent : null;
        }
        this.cfd.addAll(hashSet);
        return true;
    }

    private void a(View view, i iVar) {
        ArrayList<String> arrayList = this.cfc.get(view);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.cfc.put(view, arrayList);
        }
        arrayList.add(iVar.ahR());
    }

    private void a(i iVar) {
        Iterator<com.a.a.a.a.f.a> it = iVar.ahP().iterator();
        while (it.hasNext()) {
            View view = (View) it.next().get();
            if (view != null) {
                a(view, iVar);
            }
        }
    }

    public String Y(View view) {
        if (this.cfb.size() == 0) {
            return null;
        }
        String str = this.cfb.get(view);
        if (str == null) {
            return str;
        }
        this.cfb.remove(view);
        return str;
    }

    public ArrayList<String> Z(View view) {
        if (this.cfc.size() == 0) {
            return null;
        }
        ArrayList<String> arrayList = this.cfc.get(view);
        if (arrayList == null) {
            return arrayList;
        }
        this.cfc.remove(view);
        Collections.sort(arrayList);
        return arrayList;
    }

    public h aa(View view) {
        if (this.cfd.contains(view)) {
            return h.PARENT_VIEW;
        }
        return this.f ? h.OBSTRUCTION_VIEW : h.UNDERLYING_VIEW;
    }

    public HashSet<String> aiq() {
        return this.cfe;
    }

    public HashSet<String> air() {
        return this.cff;
    }

    public void c() {
        com.a.a.a.a.c.a aVarAhU = com.a.a.a.a.c.a.ahU();
        if (aVarAhU != null) {
            for (i iVar : aVarAhU.ahW()) {
                View viewAhS = iVar.ahS();
                if (iVar.ahT()) {
                    if (viewAhS == null || !X(viewAhS)) {
                        this.cff.add(iVar.ahR());
                    } else {
                        this.cfe.add(iVar.ahR());
                        this.cfb.put(viewAhS, iVar.ahR());
                        a(iVar);
                    }
                }
            }
        }
    }

    public void d() {
        this.cfb.clear();
        this.cfc.clear();
        this.cfd.clear();
        this.cfe.clear();
        this.cff.clear();
        this.f = false;
    }

    public void e() {
        this.f = true;
    }
}
