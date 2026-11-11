package com.gameloft.glads;

import com.c.a.a.a.f.a;
import com.c.a.a.a.f.b;
import com.c.a.a.a.f.e;

/* JADX INFO: loaded from: classes.dex */
public class AvidSDK {
    private a avidSession = null;
    long parent;

    public AvidSDK(long j) {
        this.parent = j;
    }

    public void StartSession(final String str, final boolean z) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AvidSDK.1
            @Override // java.lang.Runnable
            public void run() {
                e eVar = new e(str);
                if (z) {
                    AvidSDK.this.avidSession = b.b(AndroidUtils.GetActivity().getApplicationContext(), eVar);
                } else {
                    AvidSDK.this.avidSession = b.a(AndroidUtils.GetActivity().getApplicationContext(), eVar);
                }
            }
        });
    }

    public void EndSession() {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AvidSDK.2
            @Override // java.lang.Runnable
            public void run() {
                AvidSDK.this.avidSession.aja();
                AvidSDK.this.avidSession = null;
            }
        });
    }

    public void OnCreateWebView(final Object obj) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AvidSDK.3
            @Override // java.lang.Runnable
            public void run() {
                if (AvidSDK.this.avidSession != null) {
                    AvidSDK.this.avidSession.a(((AndroidWebView) obj).GetWebView(), AndroidUtils.GetActivity());
                }
            }
        });
    }
}
