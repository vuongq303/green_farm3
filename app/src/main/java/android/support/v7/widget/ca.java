package android.support.v7.widget;

import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: compiled from: ListPopupWindow.java */
/* JADX INFO: loaded from: classes.dex */
class ca implements AdapterView.OnItemSelectedListener {
    final /* synthetic */ by wx;

    ca(by byVar) {
        this.wx = byVar;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        bq bqVar;
        if (i != -1 && (bqVar = this.wx.vY) != null) {
            bqVar.M(false);
        }
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
