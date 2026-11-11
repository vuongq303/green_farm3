package com.c.a.a.a.e;

import com.c.a.a.a.f.a.d;
import java.util.Collection;
import java.util.HashMap;

/* JADX INFO: compiled from: AvidAdSessionRegistry.java */
/* JADX INFO: loaded from: classes.dex */
public class a implements d {
    private static a cjt = new a();
    private b cjw;
    private final HashMap<String, com.c.a.a.a.f.a.a> cju = new HashMap<>();
    private final HashMap<String, com.c.a.a.a.f.a> cjv = new HashMap<>();
    private int cjx = 0;

    public static a aiW() {
        return cjt;
    }

    public void a(b bVar) {
        this.cjw = bVar;
    }

    public void a(com.c.a.a.a.f.a aVar, com.c.a.a.a.f.a.a aVar2) {
        this.cjv.put(aVar.aiZ(), aVar);
        this.cju.put(aVar.aiZ(), aVar2);
        aVar2.a(this);
        if (this.cjv.size() == 1 && this.cjw != null) {
            this.cjw.a(this);
        }
    }

    public Collection<com.c.a.a.a.f.a.a> aiX() {
        return this.cju.values();
    }

    public boolean isEmpty() {
        return this.cjv.isEmpty();
    }

    public boolean aiY() {
        return this.cjx > 0;
    }

    public com.c.a.a.a.f.a.a iN(String str) {
        return this.cju.get(str);
    }

    @Override // com.c.a.a.a.f.a.d
    public void a(com.c.a.a.a.f.a.a aVar) {
        this.cjv.remove(aVar.aiZ());
        this.cju.remove(aVar.aiZ());
        aVar.a(null);
        if (this.cjv.size() == 0 && this.cjw != null) {
            this.cjw.a(this);
        }
    }

    @Override // com.c.a.a.a.f.a.d
    public void b(com.c.a.a.a.f.a.a aVar) {
        this.cjx++;
        if (this.cjx == 1 && this.cjw != null) {
            this.cjw.b(this);
        }
    }

    @Override // com.c.a.a.a.f.a.d
    public void c(com.c.a.a.a.f.a.a aVar) {
        this.cjx--;
        if (this.cjx == 0 && this.cjw != null) {
            this.cjw.b(this);
        }
    }
}
