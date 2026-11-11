package com.gameloft.glads;

import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class AndroidWebView implements View.OnTouchListener {
    private static String TAG = "GLADS";
    public static final String UTF_8 = "UTF-8";
    long parent;
    WebView webView = null;
    View solidBlackView = null;
    int orientation = 0;
    boolean isPresentingFullScreen = false;
    boolean isReleased = false;
    boolean openWithModalWebview = false;
    String modalWebviewParams = "{}";
    HashMap buttons = new HashMap();

    public static native void NativeButtonClicked(long j, int i);

    public static native long NativeGetTime();

    public static native void NativeOnCreateWindow(long j, long j2, String str);

    public static native void NativeOnFailedLoad(long j, String str);

    public static native void NativeOnFinishLoad(long j);

    public static native void NativeOnJavaScriptEvaluated(long j, String str, int i);

    public static native void NativeOnTouch(long j);

    public static native boolean NativeShouldOpenURL(long j, String str);

    public AndroidWebView(long j) {
        this.parent = j;
        StartWebView();
    }

    public void SetInitialScale(final int i) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.1
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.setInitialScale(i);
            }
        });
    }

    public void SetUseWideViewPort(final boolean z) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.2
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.getSettings().setUseWideViewPort(z);
            }
        });
    }

    public void SetOrientation(int i) {
        if (AndroidDevice.GetOrientation() != i) {
            AddSolidBlackViewToParent();
        }
        this.orientation = i;
        AndroidUtils.SetOrientation(i);
    }

    public WebView GetWebView() {
        return this.webView;
    }

    public void StartWebView() {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.3
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView = new WebView(AndroidUtils.GetContext());
                AndroidWebView.this.webView.setScrollContainer(false);
                AndroidWebView.this.webView.setVerticalScrollBarEnabled(false);
                AndroidWebView.this.webView.setHorizontalScrollBarEnabled(false);
                AndroidWebView.this.webView.setScrollBarStyle(33554432);
                AndroidWebView.this.webView.getSettings().setJavaScriptEnabled(true);
                AndroidWebView.this.webView.setWebViewClient(new GLWebViewClient(this));
                AndroidWebView.this.webView.setWebChromeClient(new GLWebChromeClient(this));
                AndroidWebView.this.webView.setVisibility(8);
                AndroidWebView.this.webView.setBackgroundColor(0);
                AndroidWebView.this.webView.getSettings().setAppCacheEnabled(false);
                AndroidWebView.this.webView.getSettings().setSupportZoom(false);
                AndroidWebView.this.webView.getSettings().setDefaultTextEncodingName(AndroidWebView.UTF_8);
                AndroidWebView.this.webView.getSettings().setSupportMultipleWindows(true);
                AndroidWebView.this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                if (Build.VERSION.SDK_INT >= 16) {
                    AndroidWebView.this.webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
                    AndroidWebView.this.webView.getSettings().setAllowFileAccessFromFileURLs(true);
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    AndroidWebView.this.webView.getSettings().setMixedContentMode(0);
                }
                if (Build.VERSION.SDK_INT >= 19) {
                    AndroidWebView.this.webView.getSettings().setLoadWithOverviewMode(true);
                }
                if (Build.VERSION.SDK_INT >= 17) {
                    AndroidWebView.this.webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
                }
                AndroidWebView.this.webView.setOnTouchListener(this);
                ViewGroup viewGroupGetParentView = AndroidUtils.GetParentView();
                if (viewGroupGetParentView instanceof RelativeLayout) {
                    AndroidWebView.this.webView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
                } else if (viewGroupGetParentView instanceof FrameLayout) {
                    AndroidWebView.this.webView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                }
                if (viewGroupGetParentView instanceof AbsoluteLayout) {
                    AndroidWebView.this.webView.setLayoutParams(new AbsoluteLayout.LayoutParams(-1, -1, 0, 0));
                }
                AndroidUtils.GetParentView().addView(AndroidWebView.this.webView);
                AndroidWebView.this.webView.addJavascriptInterface(new WebViewJavaScriptInterface(this), "GLAdsV2");
            }
        });
    }

    public void AddSolidBlackViewToParent() {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.4
            @Override // java.lang.Runnable
            public void run() {
                if (AndroidWebView.this.solidBlackView == null) {
                    AndroidWebView.this.solidBlackView = new RelativeLayout(AndroidUtils.GetActivity());
                    AndroidWebView.this.solidBlackView.setBackgroundColor(-16777216);
                    AndroidUtils.GetParentView().addView(AndroidWebView.this.solidBlackView, new RelativeLayout.LayoutParams(-1, -1));
                    AndroidWebView.this.webView.bringToFront();
                    if (Build.VERSION.SDK_INT < 19) {
                        AndroidUtils.GetParentView().invalidate();
                    }
                }
            }
        });
    }

    void RemoveSolidBlackViewFromParent() {
        if (this.solidBlackView != null) {
            AndroidUtils.GetParentView().removeView(this.solidBlackView);
        }
    }

    public void PresentFullScreen(final int i) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.5
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.isPresentingFullScreen = true;
                ViewGroup viewGroupGetParentView = AndroidUtils.GetParentView();
                if (viewGroupGetParentView instanceof RelativeLayout) {
                    AndroidWebView.this.webView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
                } else if (viewGroupGetParentView instanceof FrameLayout) {
                    AndroidWebView.this.webView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                }
                if (viewGroupGetParentView instanceof AbsoluteLayout) {
                    AndroidWebView.this.webView.setLayoutParams(new AbsoluteLayout.LayoutParams(-1, -1, 0, 0));
                }
                AndroidWebView.this.webView.bringToFront();
                AndroidWebView.this.SetOrientation(i);
                AndroidWebView.this.webView.setVisibility(0);
            }
        });
    }

    synchronized void CloseFullScreen() {
        if (this.isPresentingFullScreen) {
            this.isPresentingFullScreen = false;
            this.webView.setVisibility(8);
            AndroidUtils.RestoreOrientation();
            this.webView.setVisibility(0);
            RemoveSolidBlackViewFromParent();
        }
    }

    public void Release() {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.6
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.isReleased = true;
                AndroidWebView.this.CloseFullScreen();
                AndroidWebView.this.webView.setWebViewClient(new WebViewClient());
                AndroidWebView.this.webView.setWebChromeClient(new WebChromeClient());
                AndroidUtils.GetParentView().removeView(AndroidWebView.this.webView);
                final WebView webView = AndroidWebView.this.webView;
                AndroidUtils.GetParentView().postDelayed(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        webView.loadUrl("about:blank");
                    }
                }, 3000L);
                AndroidWebView.this.webView = null;
            }
        });
    }

    public void SetBounds(final int i, final int i2, final int i3, final int i4) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.7
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.setLayoutParams(AndroidWebView.this.GetLayoutParams(i, i2, i3, i4));
            }
        });
    }

    public void BringToFront() {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.8
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.bringToFront();
            }
        });
    }

    public void SetVisible(final boolean z) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.9
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.setVisibility(z ? 0 : 8);
            }
        });
    }

    public void LoadHTMLFile(final String str) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.10
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.loadUrl("file://" + str);
            }
        });
    }

    public void LoadHTMLString(final String str, final String str2) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.11
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.loadDataWithBaseURL(str2, str, "text/html", AndroidWebView.UTF_8, null);
            }
        });
    }

    public void LoadURL(final String str) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.12
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.loadUrl(str);
            }
        });
    }

    public void RunJavaScript(final String str) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.13
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.loadUrl("javascript:" + str);
            }
        });
    }

    public void EvaluateJavaScript(final String str, final int i) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.14
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.loadUrl(String.format("javascript:GLAdsV2.OnJavaScriptEvaluated(eval(\"%s\"), %d);", str, Integer.valueOf(i)));
            }
        });
    }

    public void DismissFullScreen() {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.15
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.CloseFullScreen();
            }
        });
    }

    public ViewGroup.LayoutParams GetLayoutParams(int i, int i2, int i3, int i4) {
        ViewGroup viewGroupGetParentView = AndroidUtils.GetParentView();
        if (viewGroupGetParentView instanceof RelativeLayout) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i3, i4);
            layoutParams.addRule(9);
            layoutParams.addRule(10);
            layoutParams.setMargins(i, i2, 0, 0);
            return layoutParams;
        }
        if (viewGroupGetParentView instanceof FrameLayout) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(i3, i4);
            layoutParams2.setMargins(i, i2, 0, 0);
            return layoutParams2;
        }
        if (!(viewGroupGetParentView instanceof AbsoluteLayout)) {
            return null;
        }
        return new AbsoluteLayout.LayoutParams(i3, i4, i, i2);
    }

    void AddButton(final byte[] bArr, final int i, final int i2, final int i3, final int i4, final int i5) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.16
            @Override // java.lang.Runnable
            public void run() {
                ImageButton imageButton = new ImageButton(AndroidUtils.GetContext());
                imageButton.setId(i5);
                imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.gameloft.glads.AndroidWebView.16.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        AndroidWebView.NativeButtonClicked(AndroidWebView.this.parent, view.getId());
                    }
                });
                imageButton.setBackgroundColor(0);
                imageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageButton.setPadding(0, 0, 0, 0);
                imageButton.setImageBitmap(BitmapFactory.decodeByteArray(bArr, 0, bArr.length));
                AndroidWebView.this.buttons.put(Integer.valueOf(i5), imageButton);
                AndroidWebView.this.webView.addView(imageButton, new AbsoluteLayout.LayoutParams(i3, i4, i, i2));
            }
        });
    }

    void RemoveButton(final int i) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.17
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.removeView((ImageButton) AndroidWebView.this.buttons.get(Integer.valueOf(i)));
            }
        });
    }

    void SetButtonBounds(final int i, final int i2, final int i3, final int i4, final int i5) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.18
            @Override // java.lang.Runnable
            public void run() {
                ((ImageButton) AndroidWebView.this.buttons.get(Integer.valueOf(i))).setLayoutParams(new AbsoluteLayout.LayoutParams(i4, i5, i2, i3));
            }
        });
    }

    void SetUserAgent(final String str) {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.19
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.getSettings().setUserAgentString(str);
            }
        });
    }

    void EnableModalWebView() {
        this.openWithModalWebview = true;
    }

    void ClearCacheAndCookies() {
        AndroidUtils.RunOnMainThread(new Runnable() { // from class: com.gameloft.glads.AndroidWebView.20
            @Override // java.lang.Runnable
            public void run() {
                AndroidWebView.this.webView.clearCache(true);
                AndroidWebView.this.webView.clearHistory();
                if (Build.VERSION.SDK_INT >= 22) {
                    CookieManager.getInstance().removeAllCookies(null);
                    CookieManager.getInstance().flush();
                    return;
                }
                CookieSyncManager cookieSyncManagerCreateInstance = CookieSyncManager.createInstance(AndroidWebView.this.webView.getContext());
                cookieSyncManagerCreateInstance.startSync();
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeAllCookie();
                cookieManager.removeSessionCookie();
                cookieSyncManagerCreateInstance.stopSync();
                cookieSyncManagerCreateInstance.sync();
            }
        });
    }

    public void OnFinishLoad() {
        if (!this.isReleased) {
            NativeOnFinishLoad(this.parent);
        }
    }

    public void OnFailedLoad(String str) {
        if (!this.isReleased) {
            NativeOnFailedLoad(this.parent, str);
        }
    }

    @JavascriptInterface
    public void OnJavaScriptEvaluated(String str, int i) {
        if (!this.isReleased) {
            NativeOnJavaScriptEvaluated(this.parent, str, i);
        }
    }

    public boolean ShouldOpenURL(String str) {
        if (this.isReleased) {
            return true;
        }
        return NativeShouldOpenURL(this.parent, str);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.isReleased) {
            NativeOnTouch(this.parent);
        }
        return false;
    }

    public long GetTime() {
        return NativeGetTime();
    }

    public void OnCreateWindow(long j, String str) {
        if (!this.isReleased) {
            NativeOnCreateWindow(this.parent, j, str);
        }
    }
}
