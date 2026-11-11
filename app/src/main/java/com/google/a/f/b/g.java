package com.google.a.f.b;

import com.gameloft.glads.GLURLConnection;

/* JADX INFO: compiled from: QRCode.java */
/* JADX INFO: loaded from: classes.dex */
public final class g {
    private int cdA = -1;
    private b cdB;
    private com.google.a.f.a.b cdx;
    private com.google.a.f.a.a cdy;
    private com.google.a.f.a.c cdz;

    public b ahz() {
        return this.cdB;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(GLURLConnection.HTTP_OK);
        sb.append("<<\n");
        sb.append(" mode: ");
        sb.append(this.cdx);
        sb.append("\n ecLevel: ");
        sb.append(this.cdy);
        sb.append("\n version: ");
        sb.append(this.cdz);
        sb.append("\n maskPattern: ");
        sb.append(this.cdA);
        if (this.cdB == null) {
            sb.append("\n matrix: null\n");
        } else {
            sb.append("\n matrix:\n");
            sb.append(this.cdB);
        }
        sb.append(">>\n");
        return sb.toString();
    }

    public void a(com.google.a.f.a.b bVar) {
        this.cdx = bVar;
    }

    public void b(com.google.a.f.a.a aVar) {
        this.cdy = aVar;
    }

    public void b(com.google.a.f.a.c cVar) {
        this.cdz = cVar;
    }

    public void oY(int i) {
        this.cdA = i;
    }

    public void k(b bVar) {
        this.cdB = bVar;
    }

    public static boolean oZ(int i) {
        return i >= 0 && i < 8;
    }
}
