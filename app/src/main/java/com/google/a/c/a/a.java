package com.google.a.c.a;

/* JADX INFO: compiled from: ASCIIEncoder.java */
/* JADX INFO: loaded from: classes.dex */
final class a implements g {
    a() {
    }

    public int agI() {
        return 0;
    }

    @Override // com.google.a.c.a.g
    public void a(h hVar) {
        if (j.c(hVar.getMessage(), hVar.pos) >= 2) {
            hVar.l(a(hVar.getMessage().charAt(hVar.pos), hVar.getMessage().charAt(hVar.pos + 1)));
            hVar.pos += 2;
            return;
        }
        char cAgL = hVar.agL();
        int iA = j.a(hVar.getMessage(), hVar.pos, agI());
        if (iA != agI()) {
            switch (iA) {
                case 1:
                    hVar.l((char) 230);
                    hVar.oP(1);
                    return;
                case 2:
                    hVar.l((char) 239);
                    hVar.oP(2);
                    return;
                case 3:
                    hVar.l((char) 238);
                    hVar.oP(3);
                    return;
                case 4:
                    hVar.l((char) 240);
                    hVar.oP(4);
                    return;
                case 5:
                    hVar.l((char) 231);
                    hVar.oP(5);
                    return;
                default:
                    throw new IllegalStateException("Illegal mode: " + iA);
            }
        }
        if (j.m(cAgL)) {
            hVar.l((char) 235);
            hVar.l((char) ((cAgL - 128) + 1));
            hVar.pos++;
        } else {
            hVar.l((char) (cAgL + 1));
            hVar.pos++;
        }
    }

    private static char a(char c, char c2) {
        if (j.isDigit(c) && j.isDigit(c2)) {
            return (char) (((c - '0') * 10) + (c2 - '0') + 130);
        }
        throw new IllegalArgumentException("not digits: " + c + c2);
    }
}
