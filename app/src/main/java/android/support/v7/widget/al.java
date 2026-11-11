package android.support.v7.widget;

import android.content.Context;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import java.lang.reflect.Field;

/* JADX INFO: compiled from: AppCompatPopupWindow.java */
/* JADX INFO: loaded from: classes.dex */
class al extends PopupWindow {
    private static final boolean tY;
    private boolean tZ;

    static {
        tY = Build.VERSION.SDK_INT < 21;
    }

    public al(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        a(context, attributeSet, i, i2);
    }

    private void a(Context context, AttributeSet attributeSet, int i, int i2) {
        da daVarA = da.a(context, attributeSet, R.styleable.PopupWindow, i, i2);
        if (daVarA.hasValue(R.styleable.PopupWindow_overlapAnchor)) {
            K(daVarA.getBoolean(R.styleable.PopupWindow_overlapAnchor, false));
        }
        setBackgroundDrawable(daVarA.getDrawable(R.styleable.PopupWindow_android_popupBackground));
        int i3 = Build.VERSION.SDK_INT;
        if (i2 != 0 && i3 < 11 && daVarA.hasValue(R.styleable.PopupWindow_android_popupAnimationStyle)) {
            setAnimationStyle(daVarA.getResourceId(R.styleable.PopupWindow_android_popupAnimationStyle, -1));
        }
        daVarA.recycle();
        if (Build.VERSION.SDK_INT < 14) {
            a(this);
        }
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view, int i, int i2) {
        if (tY && this.tZ) {
            i2 -= view.getHeight();
        }
        super.showAsDropDown(view, i, i2);
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view, int i, int i2, int i3) {
        if (tY && this.tZ) {
            i2 -= view.getHeight();
        }
        super.showAsDropDown(view, i, i2, i3);
    }

    @Override // android.widget.PopupWindow
    public void update(View view, int i, int i2, int i3, int i4) {
        super.update(view, i, (tY && this.tZ) ? i2 - view.getHeight() : i2, i3, i4);
    }

    private static void a(PopupWindow popupWindow) {
        try {
            Field declaredField = PopupWindow.class.getDeclaredField("mAnchor");
            declaredField.setAccessible(true);
            Field declaredField2 = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
            declaredField2.setAccessible(true);
            declaredField2.set(popupWindow, new am(declaredField, popupWindow, (ViewTreeObserver.OnScrollChangedListener) declaredField2.get(popupWindow)));
        } catch (Exception e) {
            Log.d("AppCompatPopupWindow", "Exception while installing workaround OnScrollChangedListener", e);
        }
    }

    public void K(boolean z) {
        if (tY) {
            this.tZ = z;
        } else {
            android.support.v4.widget.t.a(this, z);
        }
    }
}
