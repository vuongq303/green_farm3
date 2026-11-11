package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzfp implements Application.ActivityLifecycleCallbacks, View.OnAttachStateChangeListener, ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    private static final long zzagc = ((Long) zzkb.zzik().zzd(zznk.zzazt)).longValue();
    private final Context zzaeo;
    private final WindowManager zzaeu;
    private final PowerManager zzaev;
    private final KeyguardManager zzaew;
    private BroadcastReceiver zzafe;
    private final Rect zzafh;
    private Application zzagd;
    private WeakReference<ViewTreeObserver> zzage;
    private WeakReference<View> zzagf;
    private zzfu zzagg;
    private final DisplayMetrics zzagj;
    private zzamj zzadz = new zzamj(zzagc);
    private boolean zzafd = false;
    private int zzagh = -1;
    private final HashSet<zzft> zzagi = new HashSet<>();

    public zzfp(Context context, View view) {
        this.zzaeo = context.getApplicationContext();
        this.zzaeu = (WindowManager) context.getSystemService("window");
        this.zzaev = (PowerManager) this.zzaeo.getSystemService("power");
        this.zzaew = (KeyguardManager) context.getSystemService("keyguard");
        if (this.zzaeo instanceof Application) {
            this.zzagd = (Application) this.zzaeo;
            this.zzagg = new zzfu((Application) this.zzaeo, this);
        }
        this.zzagj = context.getResources().getDisplayMetrics();
        this.zzafh = new Rect();
        this.zzafh.right = this.zzaeu.getDefaultDisplay().getWidth();
        this.zzafh.bottom = this.zzaeu.getDefaultDisplay().getHeight();
        View view2 = this.zzagf != null ? this.zzagf.get() : null;
        if (view2 != null) {
            view2.removeOnAttachStateChangeListener(this);
            zzf(view2);
        }
        this.zzagf = new WeakReference<>(view);
        if (view != null) {
            if (com.google.android.gms.ads.internal.zzbv.zzem().isAttachedToWindow(view)) {
                zze(view);
            }
            view.addOnAttachStateChangeListener(this);
        }
    }

    private final Rect zza(Rect rect) {
        return new Rect(zzn(rect.left), zzn(rect.top), zzn(rect.right), zzn(rect.bottom));
    }

    private final void zza(Activity activity, int i) {
        Window window;
        if (this.zzagf == null || (window = activity.getWindow()) == null) {
            return;
        }
        View viewPeekDecorView = window.peekDecorView();
        View view = this.zzagf.get();
        if (view == null || viewPeekDecorView == null || view.getRootView() != viewPeekDecorView.getRootView()) {
            return;
        }
        this.zzagh = i;
    }

    private final void zzao() {
        com.google.android.gms.ads.internal.zzbv.zzek();
        zzakk.zzcrm.post(new zzfq(this));
    }

    private final void zze(View view) {
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            this.zzage = new WeakReference<>(viewTreeObserver);
            viewTreeObserver.addOnScrollChangedListener(this);
            viewTreeObserver.addOnGlobalLayoutListener(this);
        }
        if (this.zzafe == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            this.zzafe = new zzfr(this);
            com.google.android.gms.ads.internal.zzbv.zzfk().zza(this.zzaeo, this.zzafe, intentFilter);
        }
        if (this.zzagd != null) {
            try {
                this.zzagd.registerActivityLifecycleCallbacks(this.zzagg);
            } catch (Exception e) {
                zzakb.zzb("Error registering activity lifecycle callbacks.", e);
            }
        }
    }

    private final void zzf(View view) {
        try {
            if (this.zzage != null) {
                ViewTreeObserver viewTreeObserver = this.zzage.get();
                if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
                    viewTreeObserver.removeOnScrollChangedListener(this);
                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                }
                this.zzage = null;
            }
        } catch (Exception e) {
            zzakb.zzb("Error while unregistering listeners from the last ViewTreeObserver.", e);
        }
        try {
            ViewTreeObserver viewTreeObserver2 = view.getViewTreeObserver();
            if (viewTreeObserver2.isAlive()) {
                viewTreeObserver2.removeOnScrollChangedListener(this);
                viewTreeObserver2.removeGlobalOnLayoutListener(this);
            }
        } catch (Exception e2) {
            zzakb.zzb("Error while unregistering listeners from the ViewTreeObserver.", e2);
        }
        if (this.zzafe != null) {
            try {
                com.google.android.gms.ads.internal.zzbv.zzfk().zza(this.zzaeo, this.zzafe);
            } catch (IllegalStateException e3) {
                zzakb.zzb("Failed trying to unregister the receiver", e3);
            } catch (Exception e4) {
                com.google.android.gms.ads.internal.zzbv.zzeo().zza(e4, "ActiveViewUnit.stopScreenStatusMonitoring");
            }
            this.zzafe = null;
        }
        if (this.zzagd != null) {
            try {
                this.zzagd.unregisterActivityLifecycleCallbacks(this.zzagg);
            } catch (Exception e5) {
                zzakb.zzb("Error registering activity lifecycle callbacks.", e5);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzm(int i) {
        if (this.zzagi.size() == 0 || this.zzagf == null) {
            return;
        }
        View view = this.zzagf.get();
        boolean z = i == 1;
        boolean z2 = view == null;
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        boolean globalVisibleRect = false;
        Rect rect3 = new Rect();
        boolean localVisibleRect = false;
        Rect rect4 = new Rect();
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        if (view != null) {
            globalVisibleRect = view.getGlobalVisibleRect(rect2);
            localVisibleRect = view.getLocalVisibleRect(rect3);
            view.getHitRect(rect4);
            try {
                view.getLocationOnScreen(iArr);
                view.getLocationInWindow(iArr2);
            } catch (Exception e) {
                zzakb.zzb("Failure getting view location.", e);
            }
            rect.left = iArr[0];
            rect.top = iArr[1];
            rect.right = rect.left + view.getWidth();
            rect.bottom = rect.top + view.getHeight();
        }
        int windowVisibility = view != null ? view.getWindowVisibility() : 8;
        if (this.zzagh != -1) {
            windowVisibility = this.zzagh;
        }
        boolean z3 = !z2 && com.google.android.gms.ads.internal.zzbv.zzek().zza(view, this.zzaev, this.zzaew) && globalVisibleRect && localVisibleRect && windowVisibility == 0;
        if (z && !this.zzadz.tryAcquire() && z3 == this.zzafd) {
            return;
        }
        if (z3 || this.zzafd || i != 1) {
            zzfs zzfsVar = new zzfs(com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime(), this.zzaev.isScreenOn(), view != null ? com.google.android.gms.ads.internal.zzbv.zzem().isAttachedToWindow(view) : false, view != null ? view.getWindowVisibility() : 8, zza(this.zzafh), zza(rect), zza(rect2), globalVisibleRect, zza(rect3), localVisibleRect, zza(rect4), this.zzagj.density, z3);
            Iterator<zzft> it = this.zzagi.iterator();
            while (it.hasNext()) {
                it.next().zza(zzfsVar);
            }
            this.zzafd = z3;
        }
    }

    private final int zzn(int i) {
        return (int) (i / this.zzagj.density);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
        zza(activity, 0);
        zzm(3);
        zzao();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        zzm(3);
        zzao();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
        zza(activity, 4);
        zzm(3);
        zzao();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
        zza(activity, 0);
        zzm(3);
        zzao();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        zzm(3);
        zzao();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
        zza(activity, 0);
        zzm(3);
        zzao();
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
        zzm(3);
        zzao();
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        zzm(2);
        zzao();
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public final void onScrollChanged() {
        zzm(1);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
        this.zzagh = -1;
        zze(view);
        zzm(3);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        this.zzagh = -1;
        zzm(3);
        zzao();
        zzf(view);
    }

    public final void zza(zzft zzftVar) {
        this.zzagi.add(zzftVar);
        zzm(3);
    }

    public final void zzb(zzft zzftVar) {
        this.zzagi.remove(zzftVar);
    }

    public final void zzgm() {
        zzm(4);
    }
}
