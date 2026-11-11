package android.support.v7.widget;

import android.view.View;

/* JADX INFO: compiled from: Toolbar.java */
/* JADX INFO: loaded from: classes.dex */
class db implements View.OnClickListener {
    final /* synthetic */ Toolbar yy;

    db(Toolbar toolbar) {
        this.yy = toolbar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.yy.collapseActionView();
    }
}
