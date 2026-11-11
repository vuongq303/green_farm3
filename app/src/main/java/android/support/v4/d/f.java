package android.support.v4.d;

import android.graphics.Typeface;
import java.util.ArrayList;

/* JADX INFO: compiled from: FontsContractCompat.java */
/* JADX INFO: loaded from: classes.dex */
class f implements n<Typeface> {
    private final /* synthetic */ String val$id;

    f(String str) {
        this.val$id = str;
    }

    @Override // android.support.v4.d.n
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void i(Typeface typeface) {
        ArrayList arrayList;
        synchronized (b.sLock) {
            arrayList = (ArrayList) b.fS.get(this.val$id);
            b.fS.remove(this.val$id);
        }
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < arrayList.size()) {
                ((n) arrayList.get(i2)).i(typeface);
                i = i2 + 1;
            } else {
                return;
            }
        }
    }
}
