package com.google.android.gms.games.internal;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
final class f extends c implements View.OnAttachStateChangeListener, ViewTreeObserver.OnGlobalLayoutListener {
    private WeakReference<View> bTP;
    private boolean bTQ;

    protected f(i iVar, int i) {
        super(iVar, i);
        this.bTQ = false;
    }

    private final void Q(View view) {
        Display display;
        int displayId = -1;
        if (com.google.android.gms.common.d.s.aby() && (display = view.getDisplay()) != null) {
            displayId = display.getDisplayId();
        }
        IBinder windowToken = view.getWindowToken();
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int width = view.getWidth();
        int height = view.getHeight();
        this.bTO.zzje = displayId;
        this.bTO.bTM = windowToken;
        this.bTO.left = iArr[0];
        this.bTO.top = iArr[1];
        this.bTO.right = iArr[0] + width;
        this.bTO.bottom = iArr[1] + height;
        if (this.bTQ) {
            YZ();
        }
    }

    @Override // com.google.android.gms.games.internal.c
    public final void YZ() {
        if (this.bTO.bTM == null) {
            this.bTQ = true;
        } else {
            super.YZ();
            this.bTQ = false;
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        View view;
        if (this.bTP == null || (view = this.bTP.get()) == null) {
            return;
        }
        Q(view);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        Q(view);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        this.bTN.zzbd();
        view.removeOnAttachStateChangeListener(this);
    }

    @Override // com.google.android.gms.games.internal.c
    public final void zzb(View view) {
        this.bTN.zzbd();
        if (this.bTP != null) {
            View decorView = this.bTP.get();
            Context context = this.bTN.getContext();
            if (decorView == null && (context instanceof Activity)) {
                decorView = ((Activity) context).getWindow().getDecorView();
            }
            if (decorView != null) {
                decorView.removeOnAttachStateChangeListener(this);
                ViewTreeObserver viewTreeObserver = decorView.getViewTreeObserver();
                if (com.google.android.gms.common.d.s.abx()) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this);
                } else {
                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                }
            }
        }
        this.bTP = null;
        Context context2 = this.bTN.getContext();
        if (view == null && (context2 instanceof Activity)) {
            View viewFindViewById = ((Activity) context2).findViewById(R.id.content);
            if (viewFindViewById == null) {
                viewFindViewById = ((Activity) context2).getWindow().getDecorView();
            }
            di.ax("PopupManager", "You have not specified a View to use as content view for popups. Falling back to the Activity content view. Note that this may not work as expected in multi-screen environments");
            view = viewFindViewById;
        }
        if (view == null) {
            di.ay("PopupManager", "No content view usable to display popups. Popups will not be displayed in response to this client's calls. Use setViewForPopups() to set your content view.");
            return;
        }
        Q(view);
        this.bTP = new WeakReference<>(view);
        view.addOnAttachStateChangeListener(this);
        view.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override // com.google.android.gms.games.internal.c
    protected final void zzm(int i) {
        this.bTO = new e(i, null);
    }
}
