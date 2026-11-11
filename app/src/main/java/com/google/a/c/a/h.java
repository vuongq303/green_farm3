package com.google.a.c.a;

import java.nio.charset.Charset;

/* JADX INFO: compiled from: EncoderContext.java */
/* JADX INFO: loaded from: classes.dex */
final class h {
    private final String cbo;
    private l cbp;
    private com.google.a.b cbq;
    private com.google.a.b cbr;
    private final StringBuilder cbs;
    private int cbt;
    private k cbu;
    private int cbv;
    int pos;

    h(String str) {
        byte[] bytes = str.getBytes(Charset.forName("ISO-8859-1"));
        StringBuilder sb = new StringBuilder(bytes.length);
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            char c = (char) (bytes[i] & 255);
            if (c == '?' && str.charAt(i) != '?') {
                throw new IllegalArgumentException("Message contains characters outside ISO-8859-1 encoding.");
            }
            sb.append(c);
        }
        this.cbo = sb.toString();
        this.cbp = l.FORCE_NONE;
        this.cbs = new StringBuilder(str.length());
        this.cbt = -1;
    }

    public void a(l lVar) {
        this.cbp = lVar;
    }

    public void a(com.google.a.b bVar, com.google.a.b bVar2) {
        this.cbq = bVar;
        this.cbr = bVar2;
    }

    public String getMessage() {
        return this.cbo;
    }

    public void oO(int i) {
        this.cbv = i;
    }

    public char agL() {
        return this.cbo.charAt(this.pos);
    }

    public StringBuilder agM() {
        return this.cbs;
    }

    public void iB(String str) {
        this.cbs.append(str);
    }

    public void l(char c) {
        this.cbs.append(c);
    }

    public int agN() {
        return this.cbs.length();
    }

    public int agO() {
        return this.cbt;
    }

    public void oP(int i) {
        this.cbt = i;
    }

    public void agP() {
        this.cbt = -1;
    }

    public boolean agQ() {
        return this.pos < agR();
    }

    private int agR() {
        return this.cbo.length() - this.cbv;
    }

    public int agS() {
        return agR() - this.pos;
    }

    public k agT() {
        return this.cbu;
    }

    public void agU() {
        oQ(agN());
    }

    public void oQ(int i) {
        if (this.cbu == null || i > this.cbu.ahc()) {
            this.cbu = k.a(i, this.cbp, this.cbq, this.cbr, true);
        }
    }

    public void agV() {
        this.cbu = null;
    }
}
