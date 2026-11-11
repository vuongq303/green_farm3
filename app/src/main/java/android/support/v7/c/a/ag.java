package android.support.v7.c.a;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/* JADX INFO: compiled from: MenuPopupHelper.java */
/* JADX INFO: loaded from: classes.dex */
public class ag {
    private final Context mContext;
    private final s nw;
    private int pF;
    private View pG;
    private boolean pN;
    private aj pO;
    private PopupWindow.OnDismissListener pQ;
    private final int pv;
    private final int pw;
    private final boolean px;
    private af rm;
    private final PopupWindow.OnDismissListener rn;

    public ag(Context context, s sVar, View view, boolean z, int i) {
        this(context, sVar, view, z, i, 0);
    }

    public ag(Context context, s sVar, View view, boolean z, int i, int i2) {
        this.pF = 8388611;
        this.rn = new ah(this);
        this.mContext = context;
        this.nw = sVar;
        this.pG = view;
        this.px = z;
        this.pv = i;
        this.pw = i2;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.pQ = onDismissListener;
    }

    public void setAnchorView(View view) {
        this.pG = view;
    }

    public void setForceShowIcon(boolean z) {
        this.pN = z;
        if (this.rm != null) {
            this.rm.setForceShowIcon(z);
        }
    }

    public void setGravity(int i) {
        this.pF = i;
    }

    public void show() {
        if (!dA()) {
            throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
        }
    }

    public af dz() {
        if (this.rm == null) {
            this.rm = dB();
        }
        return this.rm;
    }

    public boolean dA() {
        if (isShowing()) {
            return true;
        }
        if (this.pG == null) {
            return false;
        }
        a(0, 0, false, false);
        return true;
    }

    public boolean j(int i, int i2) {
        if (isShowing()) {
            return true;
        }
        if (this.pG == null) {
            return false;
        }
        a(i, i2, true, true);
        return true;
    }

    private af dB() {
        af apVar;
        Display defaultDisplay = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 17) {
            defaultDisplay.getRealSize(point);
        } else {
            defaultDisplay.getSize(point);
        }
        if (Math.min(point.x, point.y) >= this.mContext.getResources().getDimensionPixelSize(R.dimen.abc_cascading_menus_min_smallest_width)) {
            apVar = new h(this.mContext, this.pG, this.pv, this.pw, this.px);
        } else {
            apVar = new ap(this.mContext, this.nw, this.pG, this.pv, this.pw, this.px);
        }
        apVar.e(this.nw);
        apVar.setOnDismissListener(this.rn);
        apVar.setAnchorView(this.pG);
        apVar.b(this.pO);
        apVar.setForceShowIcon(this.pN);
        apVar.setGravity(this.pF);
        return apVar;
    }

    private void a(int i, int i2, boolean z, boolean z2) {
        af afVarDz = dz();
        afVarDz.q(z2);
        if (z) {
            if ((android.support.v4.view.j.getAbsoluteGravity(this.pF, android.support.v4.view.aa.f(this.pG)) & 7) == 5) {
                i += this.pG.getWidth();
            }
            afVarDz.setHorizontalOffset(i);
            afVarDz.setVerticalOffset(i2);
            int i3 = (int) ((this.mContext.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            afVarDz.setEpicenterBounds(new Rect(i - i3, i2 - i3, i + i3, i3 + i2));
        }
        afVarDz.show();
    }

    public void dismiss() {
        if (isShowing()) {
            this.rm.dismiss();
        }
    }

    protected void onDismiss() {
        this.rm = null;
        if (this.pQ != null) {
            this.pQ.onDismiss();
        }
    }

    public boolean isShowing() {
        return this.rm != null && this.rm.isShowing();
    }

    public void c(aj ajVar) {
        this.pO = ajVar;
        if (this.rm != null) {
            this.rm.b(ajVar);
        }
    }
}
