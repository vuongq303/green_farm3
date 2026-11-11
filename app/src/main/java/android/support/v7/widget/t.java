package android.support.v7.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug;
import android.view.ViewGroup;

/* JADX INFO: compiled from: ActionMenuView.java */
/* JADX INFO: loaded from: classes.dex */
public class t extends bx {

    @ViewDebug.ExportedProperty
    public boolean ti;

    @ViewDebug.ExportedProperty
    public int tj;

    @ViewDebug.ExportedProperty
    public int tk;

    @ViewDebug.ExportedProperty
    public boolean tl;

    @ViewDebug.ExportedProperty
    public boolean tm;
    boolean tn;

    public t(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public t(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
    }

    public t(t tVar) {
        super(tVar);
        this.ti = tVar.ti;
    }

    public t(int i, int i2) {
        super(i, i2);
        this.ti = false;
    }
}
