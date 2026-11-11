package com.google.a.c.a;

/* JADX INFO: compiled from: Base256Encoder.java */
/* JADX INFO: loaded from: classes.dex */
final class b implements g {
    b() {
    }

    public int agI() {
        return 5;
    }

    @Override // com.google.a.c.a.g
    public void a(h hVar) {
        StringBuilder sb = new StringBuilder();
        sb.append((char) 0);
        while (hVar.agQ()) {
            sb.append(hVar.agL());
            hVar.pos++;
            int iA = j.a(hVar.getMessage(), hVar.pos, agI());
            if (iA != agI()) {
                hVar.oP(iA);
                break;
            }
        }
        int length = sb.length() - 1;
        int iAgN = hVar.agN() + length + 1;
        hVar.oQ(iAgN);
        boolean z = hVar.agT().ahc() - iAgN > 0;
        if (hVar.agQ() || z) {
            if (length <= 249) {
                sb.setCharAt(0, (char) length);
            } else if (length <= 1555) {
                sb.setCharAt(0, (char) ((length / 250) + 249));
                sb.insert(1, (char) (length % 250));
            } else {
                throw new IllegalStateException("Message length not in valid ranges: " + length);
            }
        }
        int length2 = sb.length();
        for (int i = 0; i < length2; i++) {
            hVar.l(a(sb.charAt(i), hVar.agN() + 1));
        }
    }

    private static char a(char c, int i) {
        int i2 = ((i * 149) % 255) + 1 + c;
        return i2 <= 255 ? (char) i2 : (char) (i2 - 256);
    }
}
