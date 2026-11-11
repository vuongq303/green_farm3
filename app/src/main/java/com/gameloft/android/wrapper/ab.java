package com.gameloft.android.wrapper;

/* JADX INFO: compiled from: LowProfileListener.java */
/* JADX INFO: loaded from: classes.dex */
class ab implements Runnable {
    final /* synthetic */ aa bfM;

    ab(aa aaVar) {
        this.bfM = aaVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        z.j(this.bfM.bfL);
    }
}
