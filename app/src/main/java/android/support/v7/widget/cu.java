package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.TextView;

/* JADX INFO: compiled from: ScrollingTabContainerView.java */
/* JADX INFO: loaded from: classes.dex */
class cu extends bw {
    private ImageView jK;
    private View rT;
    private TextView uU;
    final /* synthetic */ cq xD;
    private final int[] xE;
    private android.support.v7.app.d xF;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public cu(cq cqVar, Context context, android.support.v7.app.d dVar, boolean z) {
        super(context, null, R.attr.actionBarTabStyle);
        this.xD = cqVar;
        this.xE = new int[]{android.R.attr.background};
        this.xF = dVar;
        da daVarA = da.a(context, null, this.xE, R.attr.actionBarTabStyle, 0);
        if (daVarA.hasValue(0)) {
            setBackgroundDrawable(daVarA.getDrawable(0));
        }
        daVarA.recycle();
        if (z) {
            setGravity(8388627);
        }
        update();
    }

    public void a(android.support.v7.app.d dVar) {
        this.xF = dVar;
        update();
    }

    @Override // android.view.View
    public void setSelected(boolean z) {
        boolean z2 = isSelected() != z;
        super.setSelected(z);
        if (z2 && z) {
            sendAccessibilityEvent(4);
        }
    }

    @Override // android.support.v7.widget.bw, android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(android.support.v7.app.d.class.getName());
    }

    @Override // android.support.v7.widget.bw, android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(android.support.v7.app.d.class.getName());
    }

    @Override // android.support.v7.widget.bw, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.xD.xy > 0 && getMeasuredWidth() > this.xD.xy) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(this.xD.xy, 1073741824), i2);
        }
    }

    public void update() {
        android.support.v7.app.d dVar = this.xF;
        View customView = dVar.getCustomView();
        if (customView != null) {
            ViewParent parent = customView.getParent();
            if (parent != this) {
                if (parent != null) {
                    ((ViewGroup) parent).removeView(customView);
                }
                addView(customView);
            }
            this.rT = customView;
            if (this.uU != null) {
                this.uU.setVisibility(8);
            }
            if (this.jK != null) {
                this.jK.setVisibility(8);
                this.jK.setImageDrawable(null);
                return;
            }
            return;
        }
        if (this.rT != null) {
            removeView(this.rT);
            this.rT = null;
        }
        Drawable icon = dVar.getIcon();
        CharSequence text = dVar.getText();
        if (icon != null) {
            if (this.jK == null) {
                AppCompatImageView appCompatImageView = new AppCompatImageView(getContext());
                bx bxVar = new bx(-2, -2);
                bxVar.gravity = 16;
                appCompatImageView.setLayoutParams(bxVar);
                addView(appCompatImageView, 0);
                this.jK = appCompatImageView;
            }
            this.jK.setImageDrawable(icon);
            this.jK.setVisibility(0);
        } else if (this.jK != null) {
            this.jK.setVisibility(8);
            this.jK.setImageDrawable(null);
        }
        boolean z = !TextUtils.isEmpty(text);
        if (z) {
            if (this.uU == null) {
                bb bbVar = new bb(getContext(), null, R.attr.actionBarTabTextStyle);
                bbVar.setEllipsize(TextUtils.TruncateAt.END);
                bx bxVar2 = new bx(-2, -2);
                bxVar2.gravity = 16;
                bbVar.setLayoutParams(bxVar2);
                addView(bbVar);
                this.uU = bbVar;
            }
            this.uU.setText(text);
            this.uU.setVisibility(0);
        } else if (this.uU != null) {
            this.uU.setVisibility(8);
            this.uU.setText((CharSequence) null);
        }
        if (this.jK != null) {
            this.jK.setContentDescription(dVar.getContentDescription());
        }
        di.a(this, z ? null : dVar.getContentDescription());
    }

    public android.support.v7.app.d eY() {
        return this.xF;
    }
}
