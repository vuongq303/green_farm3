package com.a.a.a.a;

import android.content.Context;
import com.a.a.a.a.c.e;
import com.a.a.a.a.c.h;
import com.a.a.a.a.e.f;

/* JADX INFO: loaded from: classes.dex */
public class b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f2a;

    private void d(String str, Context context) {
        iJ(str);
        f.c(context, "Application Context cannot be null");
    }

    private void iJ(String str) {
        f.c(str, "Version cannot be null");
        if (!str.matches("[0-9]+\\.[0-9]+\\.[0-9]+.*")) {
            throw new IllegalArgumentException("Invalid version format : " + str);
        }
    }

    String ahA() {
        return "1.1.3-Gameloft";
    }

    boolean b() {
        return this.f2a;
    }

    boolean c(String str, Context context) {
        d(str, context);
        if (!iH(str)) {
            return false;
        }
        if (b()) {
            return true;
        }
        ch(true);
        h.aib().a(context);
        com.a.a.a.a.c.b.ahX().a(context);
        com.a.a.a.a.e.b.a(context);
        e.ahY().a(context);
        return true;
    }

    void ch(boolean z) {
        this.f2a = z;
    }

    boolean iH(String str) {
        return iI(ahA()) == iI(str);
    }

    int iI(String str) {
        iJ(str);
        return Integer.parseInt(str.split("\\.", 2)[0]);
    }
}
