package com.google.android.gms.internal.ads;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.ServerProtocol;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzet implements ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    private final Context zzaeo;
    private final WeakReference<zzajh> zzaeq;
    private final zzgd zzaes;
    protected final zzer zzaet;
    private final WindowManager zzaeu;
    private final PowerManager zzaev;
    private final KeyguardManager zzaew;
    private final DisplayMetrics zzaex;
    private zzfa zzaey;
    private boolean zzaez;
    private boolean zzafc;
    private BroadcastReceiver zzafe;
    private float zzafj;
    private final Object mLock = new Object();
    private boolean zzaaq = false;
    private boolean zzafa = false;
    private final HashSet<zzeq> zzaff = new HashSet<>();
    private final HashSet<zzfo> zzafg = new HashSet<>();
    private final Rect zzafh = new Rect();
    private WeakReference<ViewTreeObserver> zzaer = new WeakReference<>(null);
    private boolean zzafb = true;
    private boolean zzafd = false;
    private zzamj zzadz = new zzamj(200);
    private final zzew zzafi = new zzew(this, new Handler());

    public zzet(Context context, zzjn zzjnVar, zzajh zzajhVar, zzang zzangVar, zzgd zzgdVar) {
        this.zzaeq = new WeakReference<>(zzajhVar);
        this.zzaes = zzgdVar;
        this.zzaet = new zzer(UUID.randomUUID().toString(), zzangVar, zzjnVar.zzarb, zzajhVar.zzcob, zzajhVar.zzfz(), zzjnVar.zzare);
        this.zzaeu = (WindowManager) context.getSystemService("window");
        this.zzaev = (PowerManager) context.getApplicationContext().getSystemService("power");
        this.zzaew = (KeyguardManager) context.getSystemService("keyguard");
        this.zzaeo = context;
        this.zzaeo.getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, this.zzafi);
        this.zzaex = context.getResources().getDisplayMetrics();
        Display defaultDisplay = this.zzaeu.getDefaultDisplay();
        this.zzafh.right = defaultDisplay.getWidth();
        this.zzafh.bottom = defaultDisplay.getHeight();
        zzgb();
    }

    private final boolean isScreenOn() {
        return Build.VERSION.SDK_INT >= 20 ? this.zzaev.isInteractive() : this.zzaev.isScreenOn();
    }

    private static int zza(int i, DisplayMetrics displayMetrics) {
        return (int) (i / displayMetrics.density);
    }

    private final JSONObject zza(View view, Boolean bool) throws JSONException {
        if (view == null) {
            return zzgg().put("isAttachedToWindow", false).put("isScreenOn", isScreenOn()).put("isVisible", false);
        }
        boolean zIsAttachedToWindow = com.google.android.gms.ads.internal.zzbv.zzem().isAttachedToWindow(view);
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        try {
            view.getLocationOnScreen(iArr);
            view.getLocationInWindow(iArr2);
        } catch (Exception e) {
            zzakb.zzb("Failure getting view location.", e);
        }
        Rect rect = new Rect();
        rect.left = iArr[0];
        rect.top = iArr[1];
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        Rect rect2 = new Rect();
        boolean globalVisibleRect = view.getGlobalVisibleRect(rect2, null);
        Rect rect3 = new Rect();
        boolean localVisibleRect = view.getLocalVisibleRect(rect3);
        Rect rect4 = new Rect();
        view.getHitRect(rect4);
        JSONObject jSONObjectZzgg = zzgg();
        jSONObjectZzgg.put("windowVisibility", view.getWindowVisibility()).put("isAttachedToWindow", zIsAttachedToWindow).put("viewBox", new JSONObject().put("top", zza(this.zzafh.top, this.zzaex)).put("bottom", zza(this.zzafh.bottom, this.zzaex)).put("left", zza(this.zzafh.left, this.zzaex)).put("right", zza(this.zzafh.right, this.zzaex))).put("adBox", new JSONObject().put("top", zza(rect.top, this.zzaex)).put("bottom", zza(rect.bottom, this.zzaex)).put("left", zza(rect.left, this.zzaex)).put("right", zza(rect.right, this.zzaex))).put("globalVisibleBox", new JSONObject().put("top", zza(rect2.top, this.zzaex)).put("bottom", zza(rect2.bottom, this.zzaex)).put("left", zza(rect2.left, this.zzaex)).put("right", zza(rect2.right, this.zzaex))).put("globalVisibleBoxVisible", globalVisibleRect).put("localVisibleBox", new JSONObject().put("top", zza(rect3.top, this.zzaex)).put("bottom", zza(rect3.bottom, this.zzaex)).put("left", zza(rect3.left, this.zzaex)).put("right", zza(rect3.right, this.zzaex))).put("localVisibleBoxVisible", localVisibleRect).put("hitBox", new JSONObject().put("top", zza(rect4.top, this.zzaex)).put("bottom", zza(rect4.bottom, this.zzaex)).put("left", zza(rect4.left, this.zzaex)).put("right", zza(rect4.right, this.zzaex))).put("screenDensity", this.zzaex.density);
        if (bool == null) {
            bool = Boolean.valueOf(com.google.android.gms.ads.internal.zzbv.zzek().zza(view, this.zzaev, this.zzaew));
        }
        jSONObjectZzgg.put("isVisible", bool.booleanValue());
        return jSONObjectZzgg;
    }

    private static JSONObject zza(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject2 = new JSONObject();
        jSONArray.put(jSONObject);
        jSONObject2.put("units", jSONArray);
        return jSONObject2;
    }

    private final void zza(JSONObject jSONObject, boolean z) {
        try {
            JSONObject jSONObjectZza = zza(jSONObject);
            ArrayList arrayList = new ArrayList(this.zzafg);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((zzfo) obj).zzb(jSONObjectZza, z);
            }
        } catch (Throwable th) {
            zzakb.zzb("Skipping active view message.", th);
        }
    }

    private final void zzgd() {
        if (this.zzaey != null) {
            this.zzaey.zza(this);
        }
    }

    private final void zzgf() {
        ViewTreeObserver viewTreeObserver = this.zzaer.get();
        if (viewTreeObserver == null || !viewTreeObserver.isAlive()) {
            return;
        }
        viewTreeObserver.removeOnScrollChangedListener(this);
        viewTreeObserver.removeGlobalOnLayoutListener(this);
    }

    private final JSONObject zzgg() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("afmaVersion", this.zzaet.zzfw()).put("activeViewJSON", this.zzaet.zzfx()).put("timestamp", com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime()).put("adFormat", this.zzaet.zzfv()).put("hashCode", this.zzaet.zzfy()).put("isMraid", this.zzaet.zzfz()).put("isStopped", this.zzafa).put("isPaused", this.zzaaq).put("isNative", this.zzaet.zzga()).put("isScreenOn", isScreenOn()).put("appMuted", com.google.android.gms.ads.internal.zzbv.zzfj().zzdp()).put("appVolume", com.google.android.gms.ads.internal.zzbv.zzfj().zzdo()).put("deviceVolume", this.zzafj);
        return jSONObject;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        zzl(2);
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public final void onScrollChanged() {
        zzl(1);
    }

    public final void pause() {
        synchronized (this.mLock) {
            this.zzaaq = true;
            zzl(3);
        }
    }

    public final void resume() {
        synchronized (this.mLock) {
            this.zzaaq = false;
            zzl(3);
        }
    }

    public final void stop() {
        synchronized (this.mLock) {
            this.zzafa = true;
            zzl(3);
        }
    }

    public final void zza(zzfa zzfaVar) {
        synchronized (this.mLock) {
            this.zzaey = zzfaVar;
        }
    }

    public final void zza(zzfo zzfoVar) {
        if (this.zzafg.isEmpty()) {
            synchronized (this.mLock) {
                if (this.zzafe == null) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.SCREEN_ON");
                    intentFilter.addAction("android.intent.action.SCREEN_OFF");
                    this.zzafe = new zzeu(this);
                    com.google.android.gms.ads.internal.zzbv.zzfk().zza(this.zzaeo, this.zzafe, intentFilter);
                }
            }
            zzl(3);
        }
        this.zzafg.add(zzfoVar);
        try {
            zzfoVar.zzb(zza(zza(this.zzaes.zzgh(), (Boolean) null)), false);
        } catch (JSONException e) {
            zzakb.zzb("Skipping measurement update for new client.", e);
        }
    }

    final void zza(zzfo zzfoVar, Map<String, String> map) {
        String strValueOf = String.valueOf(this.zzaet.zzfy());
        zzakb.zzck(strValueOf.length() != 0 ? "Received request to untrack: ".concat(strValueOf) : new String("Received request to untrack: "));
        zzb(zzfoVar);
    }

    public final void zzb(zzfo zzfoVar) {
        this.zzafg.remove(zzfoVar);
        zzfoVar.zzgl();
        if (this.zzafg.isEmpty()) {
            synchronized (this.mLock) {
                zzgf();
                synchronized (this.mLock) {
                    if (this.zzafe != null) {
                        try {
                            try {
                                com.google.android.gms.ads.internal.zzbv.zzfk().zza(this.zzaeo, this.zzafe);
                            } catch (IllegalStateException e) {
                                zzakb.zzb("Failed trying to unregister the receiver", e);
                            }
                        } catch (Exception e2) {
                            com.google.android.gms.ads.internal.zzbv.zzeo().zza(e2, "ActiveViewUnit.stopScreenStatusMonitoring");
                        }
                        this.zzafe = null;
                    }
                }
                this.zzaeo.getContentResolver().unregisterContentObserver(this.zzafi);
                this.zzafb = false;
                zzgd();
                ArrayList arrayList = new ArrayList(this.zzafg);
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    zzb((zzfo) obj);
                }
            }
        }
    }

    final boolean zzc(Map<String, String> map) {
        if (map == null) {
            return false;
        }
        String str = map.get("hashCode");
        return !TextUtils.isEmpty(str) && str.equals(this.zzaet.zzfy());
    }

    final void zzd(Map<String, String> map) {
        zzl(3);
    }

    final void zze(Map<String, String> map) {
        if (map.containsKey("isVisible")) {
            boolean z = AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("isVisible")) || ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(map.get("isVisible"));
            Iterator<zzeq> it = this.zzaff.iterator();
            while (it.hasNext()) {
                it.next().zza(this, z);
            }
        }
    }

    public final void zzgb() {
        this.zzafj = zzalb.zzay(this.zzaeo);
    }

    public final void zzgc() {
        synchronized (this.mLock) {
            if (this.zzafb) {
                this.zzafc = true;
                try {
                    JSONObject jSONObjectZzgg = zzgg();
                    jSONObjectZzgg.put("doneReasonCode", "u");
                    zza(jSONObjectZzgg, true);
                } catch (RuntimeException e) {
                    zzakb.zzb("Failure while processing active view data.", e);
                } catch (JSONException e2) {
                    zzakb.zzb("JSON failure while processing active view data.", e2);
                }
                String strValueOf = String.valueOf(this.zzaet.zzfy());
                zzakb.zzck(strValueOf.length() != 0 ? "Untracking ad unit: ".concat(strValueOf) : new String("Untracking ad unit: "));
            }
        }
    }

    public final boolean zzge() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzafb;
        }
        return z;
    }

    protected final void zzl(int i) {
        boolean z;
        ViewTreeObserver viewTreeObserver;
        ViewTreeObserver viewTreeObserver2;
        synchronized (this.mLock) {
            Iterator<zzfo> it = this.zzafg.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                } else if (it.next().zzgk()) {
                    z = true;
                    break;
                }
            }
            if (z && this.zzafb) {
                View viewZzgh = this.zzaes.zzgh();
                boolean z2 = viewZzgh != null && com.google.android.gms.ads.internal.zzbv.zzek().zza(viewZzgh, this.zzaev, this.zzaew);
                boolean z3 = viewZzgh != null && z2 && viewZzgh.getGlobalVisibleRect(new Rect(), null);
                if (this.zzaes.zzgi()) {
                    zzgc();
                    return;
                }
                if (i == 1 && !this.zzadz.tryAcquire() && z3 == this.zzafd) {
                    return;
                }
                if (z3 || this.zzafd || i != 1) {
                    try {
                        zza(zza(viewZzgh, Boolean.valueOf(z2)), false);
                        this.zzafd = z3;
                    } catch (RuntimeException | JSONException e) {
                        zzakb.zza("Active view update failed.", e);
                    }
                    View viewZzgh2 = this.zzaes.zzgj().zzgh();
                    if (viewZzgh2 != null && (viewTreeObserver2 = viewZzgh2.getViewTreeObserver()) != (viewTreeObserver = this.zzaer.get())) {
                        zzgf();
                        if (!this.zzaez || (viewTreeObserver != null && viewTreeObserver.isAlive())) {
                            this.zzaez = true;
                            viewTreeObserver2.addOnScrollChangedListener(this);
                            viewTreeObserver2.addOnGlobalLayoutListener(this);
                        }
                        this.zzaer = new WeakReference<>(viewTreeObserver2);
                    }
                    zzgd();
                }
            }
        }
    }
}
