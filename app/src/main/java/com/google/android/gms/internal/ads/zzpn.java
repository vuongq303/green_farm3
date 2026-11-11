package com.google.android.gms.internal.ads;

import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.google.android.gms.ads.formats.AdChoicesView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdAssetNames;
import com.google.android.gms.c.a;
import com.google.android.gms.c.d;
import com.google.android.gms.common.d.s;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzpn extends zzqb implements View.OnClickListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    private static final String[] zzbjs = {NativeAppInstallAd.ASSET_MEDIA_VIDEO, NativeContentAd.ASSET_MEDIA_VIDEO, UnifiedNativeAdAssetNames.ASSET_MEDIA_VIDEO};
    private zzoz zzbij;
    private final FrameLayout zzbjt;
    private View zzbju;
    private final boolean zzbjv;
    private View zzbjx;
    private FrameLayout zzvh;
    private final Object mLock = new Object();
    private Map<String, WeakReference<View>> zzbjw = Collections.synchronizedMap(new HashMap());
    private boolean zzbjy = false;
    private Point zzbjz = new Point();
    private Point zzbka = new Point();
    private WeakReference<zzfp> zzbkb = new WeakReference<>(null);

    public zzpn(FrameLayout frameLayout, FrameLayout frameLayout2) {
        this.zzbjt = frameLayout;
        this.zzvh = frameLayout2;
        com.google.android.gms.ads.internal.zzbv.zzfg();
        zzaor.zza((View) this.zzbjt, (ViewTreeObserver.OnGlobalLayoutListener) this);
        com.google.android.gms.ads.internal.zzbv.zzfg();
        zzaor.zza((View) this.zzbjt, (ViewTreeObserver.OnScrollChangedListener) this);
        this.zzbjt.setOnTouchListener(this);
        this.zzbjt.setOnClickListener(this);
        if (frameLayout2 != null && s.abC()) {
            frameLayout2.setElevation(Float.MAX_VALUE);
        }
        zznk.initialize(this.zzbjt.getContext());
        this.zzbjv = ((Boolean) zzkb.zzik().zzd(zznk.zzbcd)).booleanValue();
    }

    private final void zzkt() {
        synchronized (this.mLock) {
            if (!this.zzbjv && this.zzbjy) {
                int measuredWidth = this.zzbjt.getMeasuredWidth();
                int measuredHeight = this.zzbjt.getMeasuredHeight();
                if (measuredWidth != 0 && measuredHeight != 0 && this.zzvh != null) {
                    this.zzvh.setLayoutParams(new FrameLayout.LayoutParams(measuredWidth, measuredHeight));
                    this.zzbjy = false;
                }
            }
        }
    }

    private final void zzl(View view) {
        if (this.zzbij != null) {
            zzoz zzozVarZzkn = this.zzbij instanceof zzoy ? ((zzoy) this.zzbij).zzkn() : this.zzbij;
            if (zzozVarZzkn != null) {
                zzozVarZzkn.zzl(view);
            }
        }
    }

    private final int zzv(int i) {
        zzkb.zzif();
        return zzamu.zzb(this.zzbij.getContext(), i);
    }

    @Override // com.google.android.gms.internal.ads.zzqa
    public final void destroy() {
        synchronized (this.mLock) {
            if (this.zzvh != null) {
                this.zzvh.removeAllViews();
            }
            this.zzvh = null;
            this.zzbjw = null;
            this.zzbjx = null;
            this.zzbij = null;
            this.zzbjz = null;
            this.zzbka = null;
            this.zzbkb = null;
            this.zzbju = null;
        }
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        synchronized (this.mLock) {
            if (this.zzbij == null) {
                return;
            }
            this.zzbij.cancelUnconfirmedClick();
            Bundle bundle = new Bundle();
            bundle.putFloat("x", zzv(this.zzbjz.x));
            bundle.putFloat("y", zzv(this.zzbjz.y));
            bundle.putFloat("start_x", zzv(this.zzbka.x));
            bundle.putFloat("start_y", zzv(this.zzbka.y));
            if (this.zzbjx == null || !this.zzbjx.equals(view)) {
                this.zzbij.zza(view, this.zzbjw, bundle, this.zzbjt);
            } else if (!(this.zzbij instanceof zzoy)) {
                this.zzbij.zza(view, NativeContentAd.ASSET_ATTRIBUTION_ICON_IMAGE, bundle, this.zzbjw, this.zzbjt);
            } else if (((zzoy) this.zzbij).zzkn() != null) {
                ((zzoy) this.zzbij).zzkn().zza(view, NativeContentAd.ASSET_ATTRIBUTION_ICON_IMAGE, bundle, this.zzbjw, this.zzbjt);
            }
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        synchronized (this.mLock) {
            zzkt();
            if (this.zzbij != null) {
                this.zzbij.zzc(this.zzbjt, this.zzbjw);
            }
        }
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public final void onScrollChanged() {
        synchronized (this.mLock) {
            if (this.zzbij != null) {
                this.zzbij.zzc(this.zzbjt, this.zzbjw);
            }
            zzkt();
        }
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        synchronized (this.mLock) {
            if (this.zzbij != null) {
                int[] iArr = new int[2];
                this.zzbjt.getLocationOnScreen(iArr);
                Point point = new Point((int) (motionEvent.getRawX() - iArr[0]), (int) (motionEvent.getRawY() - iArr[1]));
                this.zzbjz = point;
                if (motionEvent.getAction() == 0) {
                    this.zzbka = point;
                }
                MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                motionEventObtain.setLocation(point.x, point.y);
                this.zzbij.zzd(motionEventObtain);
                motionEventObtain.recycle();
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:139:0x027f  */
    @Override // com.google.android.gms.internal.ads.zzqa
    public final void zza(a aVar) {
        ViewGroup viewGroup;
        zzaqw zzaqwVarZzko;
        View view;
        zzpd zzpdVar;
        View view2;
        zzpd zzpdVar2;
        synchronized (this.mLock) {
            zzl(null);
            Object objB = d.b(aVar);
            if (!(objB instanceof zzpd)) {
                zzakb.zzdk("Not an instance of native engine. This is most likely a transient error");
                return;
            }
            if (!this.zzbjv && this.zzvh != null) {
                this.zzvh.setLayoutParams(new FrameLayout.LayoutParams(0, 0));
                this.zzbjt.requestLayout();
            }
            this.zzbjy = true;
            zzpd zzpdVar3 = (zzpd) objB;
            if (this.zzbij != null && ((Boolean) zzkb.zzik().zzd(zznk.zzbbu)).booleanValue()) {
                this.zzbij.zzb(this.zzbjt, this.zzbjw);
            }
            if ((this.zzbij instanceof zzpd) && (zzpdVar2 = (zzpd) this.zzbij) != null && zzpdVar2.getContext() != null && com.google.android.gms.ads.internal.zzbv.zzfh().zzu(this.zzbjt.getContext())) {
                zzaix zzaixVarZzks = zzpdVar2.zzks();
                if (zzaixVarZzks != null) {
                    zzaixVarZzks.zzx(false);
                }
                zzfp zzfpVar = this.zzbkb.get();
                if (zzfpVar != null && zzaixVarZzks != null) {
                    zzfpVar.zzb(zzaixVarZzks);
                }
            }
            if ((this.zzbij instanceof zzoy) && ((zzoy) this.zzbij).zzkm()) {
                ((zzoy) this.zzbij).zzc(zzpdVar3);
            } else {
                this.zzbij = zzpdVar3;
                if (zzpdVar3 instanceof zzoy) {
                    ((zzoy) zzpdVar3).zzc(null);
                }
            }
            if (this.zzvh == null) {
                return;
            }
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbbu)).booleanValue()) {
                this.zzvh.setClickable(false);
            }
            this.zzvh.removeAllViews();
            boolean zZzkj = zzpdVar3.zzkj();
            if (zZzkj) {
                if (this.zzbjw == null) {
                    view2 = null;
                    break;
                }
                String[] strArr = {NativeAd.ASSET_ADCHOICES_CONTAINER_VIEW, UnifiedNativeAdAssetNames.ASSET_ADCHOICES_CONTAINER_VIEW};
                int i = 0;
                while (true) {
                    if (i >= 2) {
                        view2 = null;
                        break;
                    }
                    WeakReference<View> weakReference = this.zzbjw.get(strArr[i]);
                    if (weakReference != null) {
                        view2 = weakReference.get();
                        break;
                    }
                    i++;
                }
                if (view2 instanceof ViewGroup) {
                    viewGroup = (ViewGroup) view2;
                } else {
                    viewGroup = null;
                }
            } else {
                viewGroup = null;
            }
            boolean z = zZzkj && viewGroup != null;
            this.zzbjx = zzpdVar3.zza(this, z);
            if (this.zzbjx != null) {
                if (this.zzbjw != null) {
                    this.zzbjw.put(NativeContentAd.ASSET_ATTRIBUTION_ICON_IMAGE, new WeakReference<>(this.zzbjx));
                }
                if (z) {
                    viewGroup.removeAllViews();
                    viewGroup.addView(this.zzbjx);
                } else {
                    AdChoicesView adChoicesView = new AdChoicesView(zzpdVar3.getContext());
                    adChoicesView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                    adChoicesView.addView(this.zzbjx);
                    if (this.zzvh != null) {
                        this.zzvh.addView(adChoicesView);
                    }
                }
            }
            zzpdVar3.zza(this.zzbjt, this.zzbjw, (Map<String, WeakReference<View>>) null, this, this);
            if (this.zzbjv) {
                if (this.zzbju == null) {
                    this.zzbju = new View(this.zzbjt.getContext());
                    this.zzbju.setLayoutParams(new FrameLayout.LayoutParams(-1, 0));
                }
                if (this.zzbjt != this.zzbju.getParent()) {
                    this.zzbjt.addView(this.zzbju);
                }
            }
            try {
                zzaqwVarZzko = zzpdVar3.zzko();
            } catch (Exception e) {
                com.google.android.gms.ads.internal.zzbv.zzem();
                if (zzakq.zzrp()) {
                    zzakb.zzdk("Privileged processes cannot create HTML overlays.");
                    zzaqwVarZzko = null;
                } else {
                    zzakb.zzb("Error obtaining overlay.", e);
                    zzaqwVarZzko = null;
                }
            }
            if (zzaqwVarZzko != null && this.zzvh != null) {
                this.zzvh.addView(zzaqwVarZzko.getView());
            }
            synchronized (this.mLock) {
                zzpdVar3.zzf(this.zzbjw);
                if (this.zzbjw == null) {
                    view = null;
                    break;
                }
                String[] strArr2 = zzbjs;
                int length = strArr2.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        view = null;
                        break;
                    }
                    WeakReference<View> weakReference2 = this.zzbjw.get(strArr2[i2]);
                    if (weakReference2 != null) {
                        view = weakReference2.get();
                        break;
                    }
                    i2++;
                }
                if (view instanceof FrameLayout) {
                    zzpo zzpoVar = new zzpo(this, view);
                    if (zzpdVar3 instanceof zzoy) {
                        zzpdVar3.zzb(view, zzpoVar);
                    } else {
                        zzpdVar3.zza(view, zzpoVar);
                    }
                } else {
                    zzpdVar3.zzkq();
                }
            }
            zzpdVar3.zzi(this.zzbjt);
            zzl(this.zzbjt);
            this.zzbij.zzj(this.zzbjt);
            if ((this.zzbij instanceof zzpd) && (zzpdVar = (zzpd) this.zzbij) != null && zzpdVar.getContext() != null && com.google.android.gms.ads.internal.zzbv.zzfh().zzu(this.zzbjt.getContext())) {
                zzfp zzfpVar2 = this.zzbkb.get();
                if (zzfpVar2 == null) {
                    zzfpVar2 = new zzfp(this.zzbjt.getContext(), this.zzbjt);
                    this.zzbkb = new WeakReference<>(zzfpVar2);
                }
                zzfpVar2.zza(zzpdVar.zzks());
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzqa
    public final a zzak(String str) {
        synchronized (this.mLock) {
            if (this.zzbjw == null) {
                return null;
            }
            WeakReference<View> weakReference = this.zzbjw.get(str);
            return d.F(weakReference != null ? weakReference.get() : null);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzqa
    public final void zzb(a aVar, int i) {
        zzfp zzfpVar;
        if (com.google.android.gms.ads.internal.zzbv.zzfh().zzu(this.zzbjt.getContext()) && this.zzbkb != null && (zzfpVar = this.zzbkb.get()) != null) {
            zzfpVar.zzgm();
        }
        zzkt();
    }

    @Override // com.google.android.gms.internal.ads.zzqa
    public final void zzb(String str, a aVar) {
        View view = (View) d.b(aVar);
        synchronized (this.mLock) {
            if (this.zzbjw == null) {
                return;
            }
            if (view == null) {
                this.zzbjw.remove(str);
            } else {
                this.zzbjw.put(str, new WeakReference<>(view));
                if (NativeAd.ASSET_ADCHOICES_CONTAINER_VIEW.equals(str) || UnifiedNativeAdAssetNames.ASSET_ADCHOICES_CONTAINER_VIEW.equals(str)) {
                    return;
                }
                view.setOnTouchListener(this);
                view.setClickable(true);
                view.setOnClickListener(this);
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzqa
    public final void zzc(a aVar) {
        this.zzbij.setClickConfirmingView((View) d.b(aVar));
    }
}
