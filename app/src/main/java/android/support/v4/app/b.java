package android.support.v4.app;

import android.app.Activity;
import android.content.pm.PackageManager;

/* JADX INFO: compiled from: ActivityCompat.java */
/* JADX INFO: loaded from: classes.dex */
class b implements Runnable {
    private final /* synthetic */ String[] G;
    private final /* synthetic */ Activity val$activity;
    private final /* synthetic */ int val$requestCode;

    b(String[] strArr, Activity activity, int i) {
        this.G = strArr;
        this.val$activity = activity;
        this.val$requestCode = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        int[] iArr = new int[this.G.length];
        PackageManager packageManager = this.val$activity.getPackageManager();
        String packageName = this.val$activity.getPackageName();
        int length = this.G.length;
        for (int i = 0; i < length; i++) {
            iArr[i] = packageManager.checkPermission(this.G[i], packageName);
        }
        ((c) this.val$activity).onRequestPermissionsResult(this.val$requestCode, this.G, iArr);
    }
}
