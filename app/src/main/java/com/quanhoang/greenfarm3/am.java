package com.quanhoang.greenfarm3;

/* JADX INFO: compiled from: InGameBrowserActivity.java */
/* JADX INFO: loaded from: classes.dex */
class am implements Runnable {
    final /* synthetic */ al Ez;

    am(al alVar) {
        this.Ez = alVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.Ez.Ex.Ec != null) {
            try {
                this.Ez.Ex.Ec.setVisibility(this.Ez.Ex.Ee != null ? 0 : 8);
            } catch (Exception e) {
            }
        }
    }
}
