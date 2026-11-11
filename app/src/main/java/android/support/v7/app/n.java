package android.support.v7.app;

import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: compiled from: AlertController.java */
/* JADX INFO: loaded from: classes.dex */
class n implements AdapterView.OnItemClickListener {
    final /* synthetic */ k kA;
    final /* synthetic */ e kD;

    n(k kVar, e eVar) {
        this.kA = kVar;
        this.kD = eVar;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.kA.ko.onClick(this.kD.jn, i);
        if (!this.kA.kr) {
            this.kD.jn.dismiss();
        }
    }
}
