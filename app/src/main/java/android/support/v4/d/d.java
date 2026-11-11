package android.support.v4.d;

import android.content.Context;
import android.graphics.Typeface;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: FontsContractCompat.java */
/* JADX INFO: loaded from: classes.dex */
class d implements Callable<Typeface> {
    private final /* synthetic */ a fU;
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ String val$id;

    d(Context context, a aVar, String str) {
        this.val$context = context;
        this.fU = aVar;
        this.val$id = str;
    }

    @Override // java.util.concurrent.Callable
    /* JADX INFO: renamed from: aD, reason: merged with bridge method [inline-methods] */
    public Typeface call() {
        Typeface typefaceA = b.a(this.val$context, this.fU);
        if (typefaceA != null) {
            b.ft.put(this.val$id, typefaceA);
        }
        return typefaceA;
    }
}
