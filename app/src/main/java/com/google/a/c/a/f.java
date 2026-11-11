package com.google.a.c.a;

/* JADX INFO: compiled from: EdifactEncoder.java */
/* JADX INFO: loaded from: classes.dex */
final class f implements g {
    f() {
    }

    public int agI() {
        return 4;
    }

    @Override // com.google.a.c.a.g
    public void a(h hVar) {
        StringBuilder sb = new StringBuilder();
        while (hVar.agQ()) {
            b(hVar.agL(), sb);
            hVar.pos++;
            if (sb.length() >= 4) {
                hVar.iB(a(sb, 0));
                sb.delete(0, 4);
                if (j.a(hVar.getMessage(), hVar.pos, agI()) != agI()) {
                    hVar.oP(0);
                    break;
                }
            }
        }
        sb.append((char) 31);
        a(hVar, sb);
    }

    private static void a(h hVar, CharSequence charSequence) {
        try {
            int length = charSequence.length();
            if (length == 0) {
                hVar.oP(0);
                return;
            }
            if (length == 1) {
                hVar.agU();
                int iAhc = hVar.agT().ahc() - hVar.agN();
                if (hVar.agS() == 0 && iAhc <= 2) {
                    hVar.oP(0);
                    return;
                }
            }
            if (length > 4) {
                throw new IllegalStateException("Count must not exceed 4");
            }
            int i = length - 1;
            String strA = a(charSequence, 0);
            boolean z = (!hVar.agQ()) && i <= 2;
            if (i <= 2) {
                hVar.oQ(hVar.agN() + i);
                if (hVar.agT().ahc() - hVar.agN() >= 3) {
                    hVar.oQ(hVar.agN() + strA.length());
                    z = false;
                }
            }
            if (z) {
                hVar.agV();
                hVar.pos -= i;
            } else {
                hVar.iB(strA);
            }
            hVar.oP(0);
        } catch (Throwable th) {
            hVar.oP(0);
            throw th;
        }
    }

    private static void b(char c, StringBuilder sb) {
        if (c >= ' ' && c <= '?') {
            sb.append(c);
        } else if (c >= '@' && c <= '^') {
            sb.append((char) (c - '@'));
        } else {
            j.t(c);
        }
    }

    private static String a(CharSequence charSequence, int i) {
        int length = charSequence.length() - i;
        if (length == 0) {
            throw new IllegalStateException("StringBuilder must not be empty");
        }
        char cCharAt = charSequence.charAt(i);
        char cCharAt2 = length >= 2 ? charSequence.charAt(i + 1) : (char) 0;
        int iCharAt = (length >= 4 ? charSequence.charAt(i + 3) : (char) 0) + ((length >= 3 ? charSequence.charAt(i + 2) : (char) 0) << 6) + (cCharAt2 << '\f') + (cCharAt << 18);
        char c = (char) ((iCharAt >> 16) & 255);
        char c2 = (char) ((iCharAt >> 8) & 255);
        char c3 = (char) (iCharAt & 255);
        StringBuilder sb = new StringBuilder(3);
        sb.append(c);
        if (length >= 2) {
            sb.append(c2);
        }
        if (length >= 3) {
            sb.append(c3);
        }
        return sb.toString();
    }
}
