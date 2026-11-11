package android.support.v7.widget;

import android.content.Context;
import android.os.Build;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.widget.PopupWindow;
import java.lang.reflect.Method;

/* JADX INFO: compiled from: MenuPopupWindow.java */
/* JADX INFO: loaded from: classes.dex */
public class cj extends by implements ci {
    private static Method wH;
    private ci wI;

    static {
        try {
            wH = PopupWindow.class.getDeclaredMethod("setTouchModal", Boolean.TYPE);
        } catch (NoSuchMethodException e) {
            Log.i("MenuPopupWindow", "Could not find method setTouchModal() on PopupWindow. Oh well.");
        }
    }

    public cj(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.support.v7.widget.by
    bq a(Context context, boolean z) {
        ck ckVar = new ck(context, z);
        ckVar.a(this);
        return ckVar;
    }

    public void setEnterTransition(Object obj) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.ww.setEnterTransition((Transition) obj);
        }
    }

    public void setExitTransition(Object obj) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.ww.setExitTransition((Transition) obj);
        }
    }

    public void a(ci ciVar) {
        this.wI = ciVar;
    }

    public void setTouchModal(boolean z) {
        if (wH != null) {
            try {
                wH.invoke(this.ww, Boolean.valueOf(z));
            } catch (Exception e) {
                Log.i("MenuPopupWindow", "Could not invoke setTouchModal() on PopupWindow. Oh well.");
            }
        }
    }

    @Override // android.support.v7.widget.ci
    public void c(android.support.v7.c.a.s sVar, MenuItem menuItem) {
        if (this.wI != null) {
            this.wI.c(sVar, menuItem);
        }
    }

    @Override // android.support.v7.widget.ci
    public void b(android.support.v7.c.a.s sVar, MenuItem menuItem) {
        if (this.wI != null) {
            this.wI.b(sVar, menuItem);
        }
    }
}
