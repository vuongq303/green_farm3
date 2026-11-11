package android.support.v7.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/* JADX INFO: compiled from: AlertController.java */
/* JADX INFO: loaded from: classes.dex */
class l extends ArrayAdapter<CharSequence> {
    final /* synthetic */ k kA;
    final /* synthetic */ s kz;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    l(k kVar, Context context, int i, int i2, CharSequence[] charSequenceArr, s sVar) {
        super(context, i, i2, charSequenceArr);
        this.kA = kVar;
        this.kz = sVar;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = super.getView(i, view, viewGroup);
        if (this.kA.kp != null && this.kA.kp[i]) {
            this.kz.setItemChecked(i, true);
        }
        return view2;
    }
}
