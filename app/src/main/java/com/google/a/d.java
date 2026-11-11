package com.google.a;

/* JADX INFO: compiled from: FormatException.java */
/* JADX INFO: loaded from: classes.dex */
public final class d extends g {
    private static final d bZM;

    static {
        d dVar = new d();
        bZM = dVar;
        dVar.setStackTrace(bZP);
    }

    private d() {
    }

    public static d agu() {
        return bZO ? new d() : bZM;
    }
}
