package android.support.v7.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.appcompat.R;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.facebook.appevents.codeless.internal.Constants;

/* JADX INFO: compiled from: TooltipPopup.java */
/* JADX INFO: loaded from: classes.dex */
class dq {
    private final TextView jM;
    private final Context mContext;
    private final View mZ;
    private final WindowManager.LayoutParams zb = new WindowManager.LayoutParams();
    private final Rect zc = new Rect();
    private final int[] zd = new int[2];
    private final int[] ze = new int[2];

    dq(Context context) {
        this.mContext = context;
        this.mZ = LayoutInflater.from(this.mContext).inflate(R.layout.tooltip, (ViewGroup) null);
        this.jM = (TextView) this.mZ.findViewById(R.id.message);
        this.zb.setTitle(getClass().getSimpleName());
        this.zb.packageName = this.mContext.getPackageName();
        this.zb.type = 1002;
        this.zb.width = -2;
        this.zb.height = -2;
        this.zb.format = -3;
        this.zb.windowAnimations = R.style.Animation_AppCompat_Tooltip;
        this.zb.flags = 24;
    }

    void a(View view, int i, int i2, boolean z, CharSequence charSequence) {
        if (isShowing()) {
            hide();
        }
        this.jM.setText(charSequence);
        a(view, i, i2, z, this.zb);
        ((WindowManager) this.mContext.getSystemService("window")).addView(this.mZ, this.zb);
    }

    void hide() {
        if (isShowing()) {
            ((WindowManager) this.mContext.getSystemService("window")).removeView(this.mZ);
        }
    }

    boolean isShowing() {
        return this.mZ.getParent() != null;
    }

    private void a(View view, int i, int i2, boolean z, WindowManager.LayoutParams layoutParams) {
        int height;
        int i3;
        int dimensionPixelOffset = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tooltip_precise_anchor_threshold);
        if (view.getWidth() < dimensionPixelOffset) {
            i = view.getWidth() / 2;
        }
        if (view.getHeight() >= dimensionPixelOffset) {
            int dimensionPixelOffset2 = this.mContext.getResources().getDimensionPixelOffset(R.dimen.tooltip_precise_anchor_extra_offset);
            height = i2 + dimensionPixelOffset2;
            i3 = i2 - dimensionPixelOffset2;
        } else {
            height = view.getHeight();
            i3 = 0;
        }
        layoutParams.gravity = 49;
        int dimensionPixelOffset3 = this.mContext.getResources().getDimensionPixelOffset(z ? R.dimen.tooltip_y_offset_touch : R.dimen.tooltip_y_offset_non_touch);
        View viewM = M(view);
        if (viewM == null) {
            Log.e("TooltipPopup", "Cannot find app view");
            return;
        }
        viewM.getWindowVisibleDisplayFrame(this.zc);
        if (this.zc.left < 0 && this.zc.top < 0) {
            Resources resources = this.mContext.getResources();
            int identifier = resources.getIdentifier("status_bar_height", "dimen", Constants.PLATFORM);
            int dimensionPixelSize = identifier != 0 ? resources.getDimensionPixelSize(identifier) : 0;
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            this.zc.set(0, dimensionPixelSize, displayMetrics.widthPixels, displayMetrics.heightPixels);
        }
        viewM.getLocationOnScreen(this.ze);
        view.getLocationOnScreen(this.zd);
        int[] iArr = this.zd;
        iArr[0] = iArr[0] - this.ze[0];
        int[] iArr2 = this.zd;
        iArr2[1] = iArr2[1] - this.ze[1];
        layoutParams.x = (this.zd[0] + i) - (this.zc.width() / 2);
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mZ.measure(iMakeMeasureSpec, iMakeMeasureSpec);
        int measuredHeight = this.mZ.getMeasuredHeight();
        int i4 = ((i3 + this.zd[1]) - dimensionPixelOffset3) - measuredHeight;
        int i5 = height + this.zd[1] + dimensionPixelOffset3;
        if (z) {
            if (i4 >= 0) {
                layoutParams.y = i4;
                return;
            } else {
                layoutParams.y = i5;
                return;
            }
        }
        if (measuredHeight + i5 <= this.zc.height()) {
            layoutParams.y = i5;
        } else {
            layoutParams.y = i4;
        }
    }

    private static View M(View view) {
        for (Context context = view.getContext(); context instanceof ContextWrapper; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof Activity) {
                return ((Activity) context).getWindow().getDecorView();
            }
        }
        return view.getRootView();
    }
}
