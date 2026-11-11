package android.support.v7.app;

import android.widget.ListView;

/* JADX INFO: compiled from: AlertController.java */
/* JADX INFO: loaded from: classes.dex */
public class s extends ListView {
    private final int kF;
    private final int kG;

    public void a(boolean z, boolean z2) {
        if (!z2 || !z) {
            setPadding(getPaddingLeft(), z ? getPaddingTop() : this.kF, getPaddingRight(), z2 ? getPaddingBottom() : this.kG);
        }
    }
}
