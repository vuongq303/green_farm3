package com.quanhoang.greenfarm3.PushNotification;

/* JADX INFO: compiled from: AsyncTask.java */
/* JADX INFO: loaded from: classes.dex */
class b implements Runnable {
    final /* synthetic */ Integer Fd;
    final /* synthetic */ a Fe;

    b(a aVar, Integer num) {
        this.Fe = aVar;
        this.Fd = num;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            if (this.Fe.Fc) {
                this.Fe.onCancelled();
            } else {
                this.Fe.a(this.Fd);
            }
        } catch (Exception e) {
        }
    }
}
