package com.google.android.gms.a;

import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
final class i implements Comparator<l> {
    i(h hVar) {
    }

    @Override // java.util.Comparator
    public final /* synthetic */ int compare(l lVar, l lVar2) {
        return lVar.getClass().getCanonicalName().compareTo(lVar2.getClass().getCanonicalName());
    }
}
