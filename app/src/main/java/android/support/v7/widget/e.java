package android.support.v7.widget;

import android.view.View;

/* JADX INFO: compiled from: ActionBarContextView.java */
/* JADX INFO: loaded from: classes.dex */
class e implements View.OnClickListener {
    final /* synthetic */ android.support.v7.c.b sa;
    final /* synthetic */ d sb;

    e(d dVar, android.support.v7.c.b bVar) {
        this.sb = dVar;
        this.sa = bVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.sa.finish();
    }
}
