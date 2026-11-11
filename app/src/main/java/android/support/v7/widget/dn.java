package android.support.v7.widget;

import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;
import com.facebook.R;

/* JADX INFO: compiled from: TooltipCompatHandler.java */
/* JADX INFO: loaded from: classes.dex */
class dn implements View.OnAttachStateChangeListener, View.OnHoverListener, View.OnLongClickListener {
    private static dn yZ;
    private final CharSequence oW;
    private final View yS;
    private final Runnable yT = new Cdo(this);
    private final Runnable yU = new dp(this);
    private int yV;
    private int yW;
    private dq yX;
    private boolean yY;

    public static void a(View view, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            if (yZ != null && yZ.yS == view) {
                yZ.hide();
            }
            view.setOnLongClickListener(null);
            view.setLongClickable(false);
            view.setOnHoverListener(null);
            return;
        }
        new dn(view, charSequence);
    }

    private dn(View view, CharSequence charSequence) {
        this.yS = view;
        this.oW = charSequence;
        this.yS.setOnLongClickListener(this);
        this.yS.setOnHoverListener(this);
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view) {
        this.yV = view.getWidth() / 2;
        this.yW = view.getHeight() / 2;
        S(true);
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.view.View.OnHoverListener
    public boolean onHover(View view, MotionEvent motionEvent) {
        if (this.yX == null || !this.yY) {
            AccessibilityManager accessibilityManager = (AccessibilityManager) this.yS.getContext().getSystemService("accessibility");
            if (!accessibilityManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled()) {
                switch (motionEvent.getAction()) {
                    case 7:
                        if (this.yS.isEnabled() && this.yX == null) {
                            this.yV = (int) motionEvent.getX();
                            this.yW = (int) motionEvent.getY();
                            this.yS.removeCallbacks(this.yT);
                            this.yS.postDelayed(this.yT, ViewConfiguration.getLongPressTimeout());
                        }
                        break;
                    case R.styleable.CardView_contentPaddingRight /* 10 */:
                        hide();
                        break;
                }
            }
        }
        return false;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        hide();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void S(boolean z) {
        long longPressTimeout;
        if (android.support.v4.view.aa.isAttachedToWindow(this.yS)) {
            if (yZ != null) {
                yZ.hide();
            }
            yZ = this;
            this.yY = z;
            this.yX = new dq(this.yS.getContext());
            this.yX.a(this.yS, this.yV, this.yW, this.yY, this.oW);
            this.yS.addOnAttachStateChangeListener(this);
            if (this.yY) {
                longPressTimeout = 2500;
            } else if ((android.support.v4.view.aa.j(this.yS) & 1) == 1) {
                longPressTimeout = 3000 - ((long) ViewConfiguration.getLongPressTimeout());
            } else {
                longPressTimeout = 15000 - ((long) ViewConfiguration.getLongPressTimeout());
            }
            this.yS.removeCallbacks(this.yU);
            this.yS.postDelayed(this.yU, longPressTimeout);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hide() {
        if (yZ == this) {
            yZ = null;
            if (this.yX != null) {
                this.yX.hide();
                this.yX = null;
                this.yS.removeOnAttachStateChangeListener(this);
            } else {
                Log.e("TooltipCompatHandler", "sActiveHandler.mPopup == null");
            }
        }
        this.yS.removeCallbacks(this.yT);
        this.yS.removeCallbacks(this.yU);
    }
}
