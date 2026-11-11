package android.support.v4.app;

/* JADX INFO: compiled from: Fragment.java */
/* JADX INFO: loaded from: classes.dex */
class o implements Runnable {
    final /* synthetic */ Fragment am;

    o(Fragment fragment) {
        this.am = fragment;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.am.callStartTransitionListener();
    }
}
