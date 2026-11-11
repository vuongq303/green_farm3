package com.google.a;

import com.facebook.R;
import com.google.a.d.k;
import com.google.a.d.l;
import com.google.a.d.n;
import com.google.a.d.q;
import com.google.a.d.u;
import java.util.Map;

/* JADX INFO: compiled from: MultiFormatWriter.java */
/* JADX INFO: loaded from: classes.dex */
public final class e implements h {
    @Override // com.google.a.h
    public com.google.a.b.b a(String str, a aVar, int i, int i2, Map<c, ?> map) {
        h aVar2;
        switch (f.bZN[aVar.ordinal()]) {
            case 1:
                aVar2 = new l();
                break;
            case 2:
                aVar2 = new u();
                break;
            case 3:
                aVar2 = new k();
                break;
            case 4:
                aVar2 = new q();
                break;
            case 5:
                aVar2 = new com.google.a.f.a();
                break;
            case 6:
                aVar2 = new com.google.a.d.g();
                break;
            case 7:
                aVar2 = new com.google.a.d.i();
                break;
            case 8:
                aVar2 = new com.google.a.d.d();
                break;
            case 9:
                aVar2 = new n();
                break;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                aVar2 = new com.google.a.e.a();
                break;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                aVar2 = new com.google.a.d.b();
                break;
            case R.styleable.CardView_contentPaddingBottom /* 12 */:
                aVar2 = new com.google.a.c.a();
                break;
            case 13:
                aVar2 = new com.google.a.a.a();
                break;
            default:
                throw new IllegalArgumentException("No encoder available for format " + aVar);
        }
        return aVar2.a(str, aVar, i, i2, map);
    }
}
