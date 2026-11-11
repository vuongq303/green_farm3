package android.support.v4.d;

import android.graphics.Typeface;
import android.widget.TextView;

/* JADX INFO: compiled from: FontsContractCompat.java */
/* JADX INFO: loaded from: classes.dex */
class e implements n<Typeface> {
    private final /* synthetic */ TextView fV;
    private final /* synthetic */ int fW;

    e(TextView textView, int i) {
        this.fV = textView;
        this.fW = i;
    }

    @Override // android.support.v4.d.n
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void i(Typeface typeface) {
        this.fV.setTypeface(typeface, this.fW);
    }
}
