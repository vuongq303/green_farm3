package android.support.v7.widget;

import android.view.View;

/* JADX INFO: compiled from: ScrollingTabContainerView.java */
/* JADX INFO: loaded from: classes.dex */
class ct implements View.OnClickListener {
    final /* synthetic */ cq xD;

    ct(cq cqVar) {
        this.xD = cqVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        ((cu) view).eY().select();
        int childCount = this.xD.xv.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.xD.xv.getChildAt(i);
            childAt.setSelected(childAt == view);
        }
    }
}
