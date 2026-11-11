package com.google.android.gms.internal.ads;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.d.s;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzgk extends Thread {
    private final zzgf zzahz;
    private final zzadf zzaia;
    private boolean mStarted = false;
    private boolean zzahy = false;
    private boolean zzbm = false;
    private final Object mLock = new Object();
    private final int zzagx = ((Integer) zzkb.zzik().zzd(zznk.zzawl)).intValue();
    private final int zzaic = ((Integer) zzkb.zzik().zzd(zznk.zzawm)).intValue();
    private final int zzagz = ((Integer) zzkb.zzik().zzd(zznk.zzawn)).intValue();
    private final int zzaid = ((Integer) zzkb.zzik().zzd(zznk.zzawo)).intValue();
    private final int zzaie = ((Integer) zzkb.zzik().zzd(zznk.zzawr)).intValue();
    private final int zzaif = ((Integer) zzkb.zzik().zzd(zznk.zzawt)).intValue();
    private final int zzaig = ((Integer) zzkb.zzik().zzd(zznk.zzawu)).intValue();
    private final int zzaib = ((Integer) zzkb.zzik().zzd(zznk.zzawp)).intValue();
    private final String zzaih = (String) zzkb.zzik().zzd(zznk.zzaww);
    private final boolean zzaii = ((Boolean) zzkb.zzik().zzd(zznk.zzawy)).booleanValue();

    public zzgk(zzgf zzgfVar, zzadf zzadfVar) {
        this.zzahz = zzgfVar;
        this.zzaia = zzadfVar;
        setName("ContentFetchTask");
    }

    private final zzgo zza(View view, zzge zzgeVar) {
        boolean z;
        if (view == null) {
            return new zzgo(this, 0, 0);
        }
        boolean globalVisibleRect = view.getGlobalVisibleRect(new Rect());
        if ((view instanceof TextView) && !(view instanceof EditText)) {
            CharSequence text = ((TextView) view).getText();
            if (TextUtils.isEmpty(text)) {
                return new zzgo(this, 0, 0);
            }
            zzgeVar.zzb(text.toString(), globalVisibleRect, view.getX(), view.getY(), view.getWidth(), view.getHeight());
            return new zzgo(this, 1, 0);
        }
        if ((view instanceof WebView) && !(view instanceof zzaqw)) {
            zzgeVar.zzgs();
            WebView webView = (WebView) view;
            if (s.abA()) {
                zzgeVar.zzgs();
                webView.post(new zzgm(this, zzgeVar, webView, globalVisibleRect));
                z = true;
            } else {
                z = false;
            }
            return z ? new zzgo(this, 0, 1) : new zzgo(this, 0, 0);
        }
        if (!(view instanceof ViewGroup)) {
            return new zzgo(this, 0, 0);
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < viewGroup.getChildCount()) {
            zzgo zzgoVarZza = zza(viewGroup.getChildAt(i), zzgeVar);
            i3 += zzgoVarZza.zzaiq;
            i++;
            i2 += zzgoVarZza.zzair;
        }
        return new zzgo(this, i3, i2);
    }

    private static boolean zzgx() {
        try {
            Context context = com.google.android.gms.ads.internal.zzbv.zzen().getContext();
            if (context == null) {
                return false;
            }
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            if (activityManager == null || keyguardManager == null) {
                return false;
            }
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null) {
                return false;
            }
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (Process.myPid() == runningAppProcessInfo.pid) {
                    if (runningAppProcessInfo.importance != 100 || keyguardManager.inKeyguardRestrictedInputMode()) {
                        break;
                        break;
                    }
                    PowerManager powerManager = (PowerManager) context.getSystemService("power");
                    if (!(powerManager == null ? false : powerManager.isScreenOn())) {
                        break;
                    }
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.zzbv.zzeo().zza(th, "ContentFetchTask.isInForeground");
            return false;
        }
    }

    private final void zzgz() {
        synchronized (this.mLock) {
            this.zzahy = true;
            zzakb.zzck(new StringBuilder(42).append("ContentFetchThread: paused, mPause = ").append(this.zzahy).toString());
        }
    }

    /* JADX WARN: Bottom block not found for handler: all -> 0x009c */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r4 = this;
        L0:
            boolean r0 = zzgx()     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            if (r0 == 0) goto L8f
            com.google.android.gms.internal.ads.zzgg r0 = com.google.android.gms.ads.internal.zzbv.zzen()     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            android.app.Activity r1 = r0.getActivity()     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            if (r1 != 0) goto L36
            java.lang.String r0 = "ContentFetchThread: no activity. Sleeping."
            com.google.android.gms.internal.ads.zzakb.zzck(r0)     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            r4.zzgz()     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
        L19:
            int r0 = r4.zzaib     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            int r0 = r0 * 1000
            long r0 = (long) r0     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            java.lang.Thread.sleep(r0)     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
        L21:
            java.lang.Object r1 = r4.mLock
            monitor-enter(r1)
        L24:
            boolean r0 = r4.zzahy     // Catch: java.lang.Throwable -> L9c
            if (r0 == 0) goto L99
            java.lang.String r0 = "ContentFetchTask: waiting"
            com.google.android.gms.internal.ads.zzakb.zzck(r0)     // Catch: java.lang.InterruptedException -> L34 java.lang.Throwable -> L9c
            java.lang.Object r0 = r4.mLock     // Catch: java.lang.InterruptedException -> L34 java.lang.Throwable -> L9c
            r0.wait()     // Catch: java.lang.InterruptedException -> L34 java.lang.Throwable -> L9c
            goto L24
        L34:
            r0 = move-exception
            goto L24
        L36:
            if (r1 == 0) goto L19
            r0 = 0
            android.view.Window r2 = r1.getWindow()     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L6d
            if (r2 == 0) goto L58
            android.view.Window r2 = r1.getWindow()     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L6d
            android.view.View r2 = r2.getDecorView()     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L6d
            if (r2 == 0) goto L58
            android.view.Window r1 = r1.getWindow()     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L6d
            android.view.View r1 = r1.getDecorView()     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L6d
            r2 = 16908290(0x1020002, float:2.3877235E-38)
            android.view.View r0 = r1.findViewById(r2)     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L6d
        L58:
            if (r0 == 0) goto L19
            if (r0 == 0) goto L19
            com.google.android.gms.internal.ads.zzgl r1 = new com.google.android.gms.internal.ads.zzgl     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            r1.<init>(r4, r0)     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            r0.post(r1)     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            goto L19
        L65:
            r0 = move-exception
            java.lang.String r1 = "Error in ContentFetchTask"
            com.google.android.gms.internal.ads.zzakb.zzb(r1, r0)
            goto L21
        L6d:
            r1 = move-exception
            com.google.android.gms.internal.ads.zzajm r2 = com.google.android.gms.ads.internal.zzbv.zzeo()     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            java.lang.String r3 = "ContentFetchTask.extractContent"
            r2.zza(r1, r3)     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            java.lang.String r1 = "Failed getting root view of activity. Content not extracted."
            com.google.android.gms.internal.ads.zzakb.zzck(r1)     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            goto L58
        L7f:
            r0 = move-exception
            java.lang.String r1 = "Error in ContentFetchTask"
            com.google.android.gms.internal.ads.zzakb.zzb(r1, r0)
            com.google.android.gms.internal.ads.zzadf r1 = r4.zzaia
            java.lang.String r2 = "ContentFetchTask.run"
            r1.zza(r0, r2)
            goto L21
        L8f:
            java.lang.String r0 = "ContentFetchTask: sleeping"
            com.google.android.gms.internal.ads.zzakb.zzck(r0)     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            r4.zzgz()     // Catch: java.lang.InterruptedException -> L65 java.lang.Exception -> L7f
            goto L19
        L99:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L9c
            goto L0
        L9c:
            r0 = move-exception
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L9c
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzgk.run():void");
    }

    public final void wakeup() {
        synchronized (this.mLock) {
            this.zzahy = false;
            this.mLock.notifyAll();
            zzakb.zzck("ContentFetchThread: wakeup");
        }
    }

    final void zza(zzge zzgeVar, WebView webView, String str, boolean z) {
        zzgeVar.zzgr();
        try {
            if (!TextUtils.isEmpty(str)) {
                String strOptString = new JSONObject(str).optString("text");
                if (this.zzaii || TextUtils.isEmpty(webView.getTitle())) {
                    zzgeVar.zza(strOptString, z, webView.getX(), webView.getY(), webView.getWidth(), webView.getHeight());
                } else {
                    String title = webView.getTitle();
                    zzgeVar.zza(new StringBuilder(String.valueOf(title).length() + 1 + String.valueOf(strOptString).length()).append(title).append("\n").append(strOptString).toString(), z, webView.getX(), webView.getY(), webView.getWidth(), webView.getHeight());
                }
            }
            if (zzgeVar.zzgn()) {
                this.zzahz.zzb(zzgeVar);
            }
        } catch (JSONException e) {
            zzakb.zzck("Json string may be malformed.");
        } catch (Throwable th) {
            zzakb.zza("Failed to get webview content.", th);
            this.zzaia.zza(th, "ContentFetchTask.processWebViewContent");
        }
    }

    public final void zzgw() {
        synchronized (this.mLock) {
            if (this.mStarted) {
                zzakb.zzck("Content hash thread already started, quiting...");
            } else {
                this.mStarted = true;
                start();
            }
        }
    }

    public final zzge zzgy() {
        return this.zzahz.zzgv();
    }

    public final boolean zzha() {
        return this.zzahy;
    }

    final void zzk(View view) {
        try {
            zzge zzgeVar = new zzge(this.zzagx, this.zzaic, this.zzagz, this.zzaid, this.zzaie, this.zzaif, this.zzaig);
            Context context = com.google.android.gms.ads.internal.zzbv.zzen().getContext();
            if (context != null && !TextUtils.isEmpty(this.zzaih)) {
                String str = (String) view.getTag(context.getResources().getIdentifier((String) zzkb.zzik().zzd(zznk.zzawv), ShareConstants.WEB_DIALOG_PARAM_ID, context.getPackageName()));
                if (str != null && str.equals(this.zzaih)) {
                    return;
                }
            }
            zzgo zzgoVarZza = zza(view, zzgeVar);
            zzgeVar.zzgt();
            if (zzgoVarZza.zzaiq == 0 && zzgoVarZza.zzair == 0) {
                return;
            }
            if (zzgoVarZza.zzair == 0 && zzgeVar.zzgu() == 0) {
                return;
            }
            if (zzgoVarZza.zzair == 0 && this.zzahz.zza(zzgeVar)) {
                return;
            }
            this.zzahz.zzc(zzgeVar);
        } catch (Exception e) {
            zzakb.zzb("Exception in fetchContentOnUIThread", e);
            this.zzaia.zza(e, "ContentFetchTask.fetchContent");
        }
    }
}
