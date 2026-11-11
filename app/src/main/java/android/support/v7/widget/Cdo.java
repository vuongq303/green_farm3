package android.support.v7.widget;

/* JADX INFO: renamed from: android.support.v7.widget.do, reason: invalid class name */
/* JADX INFO: compiled from: TooltipCompatHandler.java */
/* JADX INFO: loaded from: classes.dex */
class Cdo implements Runnable {
    final /* synthetic */ dn za;

    Cdo(dn dnVar) {
        this.za = dnVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.za.S(false);
    }
}
