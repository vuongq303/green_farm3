package com.gameloft.android.ANMP.GloftGF2F;

/* JADX INFO: compiled from: IGPFreemiumActivity.java */
/* JADX INFO: loaded from: classes.dex */
class z implements Runnable {
    final /* synthetic */ y Dn;

    z(y yVar) {
        this.Dn = yVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.gameloft.android.wrapper.au.getActivity().runOnUiThread(new aa(this));
    }
}
