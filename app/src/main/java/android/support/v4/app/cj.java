package android.support.v4.app;

import android.os.Bundle;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class cj {
    bv dM;
    CharSequence dN;
    CharSequence dO;
    boolean dP = false;

    public void b(bv bvVar) {
        if (this.dM != bvVar) {
            this.dM = bvVar;
            if (this.dM != null) {
                this.dM.a(this);
            }
        }
    }

    public void a(Bundle bundle) {
    }
}
