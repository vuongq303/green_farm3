package android.support.v7.c.a;

import android.view.CollapsibleActionView;
import android.view.View;
import android.widget.FrameLayout;

/* JADX INFO: compiled from: MenuItemWrapperICS.java */
/* JADX INFO: loaded from: classes.dex */
class aa extends FrameLayout implements android.support.v7.c.d {
    final CollapsibleActionView ri;

    /* JADX WARN: Multi-variable type inference failed */
    aa(View view) {
        super(view.getContext());
        this.ri = (CollapsibleActionView) view;
        addView(view);
    }

    @Override // android.support.v7.c.d
    public void onActionViewExpanded() {
        this.ri.onActionViewExpanded();
    }

    @Override // android.support.v7.c.d
    public void onActionViewCollapsed() {
        this.ri.onActionViewCollapsed();
    }

    View dy() {
        return (View) this.ri;
    }
}
