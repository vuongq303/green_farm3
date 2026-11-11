package android.support.v7.widget;

/* JADX INFO: compiled from: TooltipCompatHandler.java */
/* JADX INFO: loaded from: classes.dex */
class dp implements Runnable {
    final /* synthetic */ dn za;

    dp(dn dnVar) {
        this.za = dnVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.za.hide();
    }
}
