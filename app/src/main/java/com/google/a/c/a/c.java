package com.google.a.c.a;

/* JADX INFO: compiled from: C40Encoder.java */
/* JADX INFO: loaded from: classes.dex */
class c implements g {
    c() {
    }

    public int agI() {
        return 1;
    }

    @Override // com.google.a.c.a.g
    public void a(h hVar) {
        int iA;
        StringBuilder sb = new StringBuilder();
        while (hVar.agQ()) {
            char cAgL = hVar.agL();
            hVar.pos++;
            int iA2 = a(cAgL, sb);
            int length = ((sb.length() / 3) << 1) + hVar.agN();
            hVar.oQ(length);
            int iAhc = hVar.agT().ahc() - length;
            if (!hVar.agQ()) {
                StringBuilder sb2 = new StringBuilder();
                if (sb.length() % 3 == 2 && (iAhc < 2 || iAhc > 2)) {
                    iA2 = a(hVar, sb, sb2, iA2);
                }
                while (sb.length() % 3 == 1 && ((iA2 <= 3 && iAhc != 1) || iA2 > 3)) {
                    iA2 = a(hVar, sb, sb2, iA2);
                }
                break;
            }
            if (sb.length() % 3 == 0 && (iA = j.a(hVar.getMessage(), hVar.pos, agI())) != agI()) {
                hVar.oP(iA);
                break;
            }
        }
        b(hVar, sb);
    }

    private int a(h hVar, StringBuilder sb, StringBuilder sb2, int i) {
        int length = sb.length();
        sb.delete(length - i, length);
        hVar.pos--;
        int iA = a(hVar.agL(), sb2);
        hVar.agV();
        return iA;
    }

    static void a(h hVar, StringBuilder sb) {
        hVar.iB(a(sb, 0));
        sb.delete(0, 3);
    }

    void b(h hVar, StringBuilder sb) {
        int length = (sb.length() / 3) << 1;
        int length2 = sb.length() % 3;
        int iAgN = length + hVar.agN();
        hVar.oQ(iAgN);
        int iAhc = hVar.agT().ahc() - iAgN;
        if (length2 == 2) {
            sb.append((char) 0);
            while (sb.length() >= 3) {
                a(hVar, sb);
            }
            if (hVar.agQ()) {
                hVar.l((char) 254);
            }
        } else if (iAhc == 1 && length2 == 1) {
            while (sb.length() >= 3) {
                a(hVar, sb);
            }
            if (hVar.agQ()) {
                hVar.l((char) 254);
            }
            hVar.pos--;
        } else if (length2 == 0) {
            while (sb.length() >= 3) {
                a(hVar, sb);
            }
            if (iAhc > 0 || hVar.agQ()) {
                hVar.l((char) 254);
            }
        } else {
            throw new IllegalStateException("Unexpected case. Please report!");
        }
        hVar.oP(0);
    }

    int a(char c, StringBuilder sb) {
        if (c == ' ') {
            sb.append((char) 3);
            return 1;
        }
        if (c >= '0' && c <= '9') {
            sb.append((char) ((c - '0') + 4));
            return 1;
        }
        if (c >= 'A' && c <= 'Z') {
            sb.append((char) ((c - 'A') + 14));
            return 1;
        }
        if (c >= 0 && c <= 31) {
            sb.append((char) 0);
            sb.append(c);
            return 2;
        }
        if (c >= '!' && c <= '/') {
            sb.append((char) 1);
            sb.append((char) (c - '!'));
            return 2;
        }
        if (c >= ':' && c <= '@') {
            sb.append((char) 1);
            sb.append((char) ((c - ':') + 15));
            return 2;
        }
        if (c >= '[' && c <= '_') {
            sb.append((char) 1);
            sb.append((char) ((c - '[') + 22));
            return 2;
        }
        if (c >= '`' && c <= 127) {
            sb.append((char) 2);
            sb.append((char) (c - '`'));
            return 2;
        }
        if (c >= 128) {
            sb.append("\u0001\u001e");
            return a((char) (c - 128), sb) + 2;
        }
        throw new IllegalArgumentException("Illegal character: " + c);
    }

    private static String a(CharSequence charSequence, int i) {
        int iCharAt = (charSequence.charAt(i) * 1600) + (charSequence.charAt(i + 1) * '(') + charSequence.charAt(i + 2) + 1;
        return new String(new char[]{(char) (iCharAt / 256), (char) (iCharAt % 256)});
    }
}
