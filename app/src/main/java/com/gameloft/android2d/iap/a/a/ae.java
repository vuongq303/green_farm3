package com.gameloft.android2d.iap.a.a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: Inventory.java */
/* JADX INFO: loaded from: classes.dex */
public class ae {
    Map<String, ah> bmB = new HashMap();
    Map<String, af> bmC = new HashMap();

    ae() {
    }

    public af fw(String str) {
        return this.bmC.get(str);
    }

    public List<String> Sr() {
        return new ArrayList(this.bmC.keySet());
    }

    List<String> fx(String str) {
        ArrayList arrayList = new ArrayList();
        for (af afVar : this.bmC.values()) {
            if (afVar.Ss().equals(str)) {
                arrayList.add(afVar.Su());
            }
        }
        return arrayList;
    }

    void a(ah ahVar) {
        this.bmB.put(ahVar.Su(), ahVar);
    }

    void c(af afVar) {
        this.bmC.put(afVar.Su(), afVar);
    }
}
