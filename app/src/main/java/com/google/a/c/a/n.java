package com.google.a.c.a;

/* JADX INFO: compiled from: X12Encoder.java */
/* JADX INFO: loaded from: classes.dex */
final class n extends c {
    n() {
    }

    @Override // com.google.a.c.a.c
    public int agI() {
        return 3;
    }

    @Override // com.google.a.c.a.c, com.google.a.c.a.g
    public void a(h hVar) {
        StringBuilder sb = new StringBuilder();
        while (hVar.agQ()) {
            char cAgL = hVar.agL();
            hVar.pos++;
            a(cAgL, sb);
            if (sb.length() % 3 == 0) {
                a(hVar, sb);
                int iA = j.a(hVar.getMessage(), hVar.pos, agI());
                if (iA != agI()) {
                    hVar.oP(iA);
                    break;
                }
            }
        }
        b(hVar, sb);
    }

    @Override // com.google.a.c.a.c
    int a(char c, StringBuilder sb) {
        if (c == '\r') {
            sb.append((char) 0);
        } else if (c == '*') {
            sb.append((char) 1);
        } else if (c == '>') {
            sb.append((char) 2);
        } else if (c == ' ') {
            sb.append((char) 3);
        } else if (c >= '0' && c <= '9') {
            sb.append((char) ((c - '0') + 4));
        } else if (c >= 'A' && c <= 'Z') {
            sb.append((char) ((c - 'A') + 14));
        } else {
            j.t(c);
        }
        return 1;
    }

    @Override // com.google.a.c.a.c
    void b(h hVar, StringBuilder sb) {
        hVar.agU();
        int iAhc = hVar.agT().ahc() - hVar.agN();
        hVar.pos -= sb.length();
        if (hVar.agS() > 1 || iAhc > 1 || hVar.agS() != iAhc) {
            hVar.l((char) 254);
        }
        if (hVar.agO() < 0) {
            hVar.oP(0);
        }
    }
}
