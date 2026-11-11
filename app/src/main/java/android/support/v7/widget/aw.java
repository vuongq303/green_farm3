package android.support.v7.widget;

import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: compiled from: AppCompatSpinner.java */
/* JADX INFO: loaded from: classes.dex */
class aw implements AdapterView.OnItemClickListener {
    final /* synthetic */ as uy;
    final /* synthetic */ av uz;

    aw(av avVar, as asVar) {
        this.uz = avVar;
        this.uy = asVar;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.uz.ut.setSelection(i);
        if (this.uz.ut.getOnItemClickListener() != null) {
            this.uz.ut.performItemClick(view, i, this.uz.jO.getItemId(i));
        }
        this.uz.dismiss();
    }
}
