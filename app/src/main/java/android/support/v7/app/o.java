package android.support.v7.app;

import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: compiled from: AlertController.java */
/* JADX INFO: loaded from: classes.dex */
class o implements AdapterView.OnItemClickListener {
    final /* synthetic */ k kA;
    final /* synthetic */ e kD;
    final /* synthetic */ s kz;

    o(k kVar, s sVar, e eVar) {
        this.kA = kVar;
        this.kz = sVar;
        this.kD = eVar;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.kA.kp != null) {
            this.kA.kp[i] = this.kz.isItemChecked(i);
        }
        this.kA.ks.onClick(this.kD.jn, i, this.kz.isItemChecked(i));
    }
}
