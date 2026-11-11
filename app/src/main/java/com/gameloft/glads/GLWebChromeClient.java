package com.gameloft.glads;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.webkit.ConsoleMessage;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* JADX INFO: loaded from: classes.dex */
public class GLWebChromeClient extends WebChromeClient {
    AndroidWebView parentWebView;

    GLWebChromeClient(AndroidWebView androidWebView) {
        this.parentWebView = androidWebView;
    }

    @Override // android.webkit.WebChromeClient
    public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
        if (this.parentWebView.openWithModalWebview) {
            openModalWebView(message);
            return true;
        }
        callNative(webView, message);
        return true;
    }

    private void callNative(WebView webView, Message message) {
        final long jGetTime = this.parentWebView.GetTime();
        WebView webView2 = new WebView(webView.getContext());
        webView2.getSettings().setJavaScriptEnabled(true);
        webView2.setWebViewClient(new WebViewClient() { // from class: com.gameloft.glads.GLWebChromeClient.1
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView3, String str, Bitmap bitmap) {
                GLWebChromeClient.this.parentWebView.OnCreateWindow(jGetTime, str);
                webView3.destroy();
            }
        });
        ((WebView.WebViewTransport) message.obj).setWebView(webView2);
        message.sendToTarget();
    }

    private void openModalWebView(Message message) {
        try {
            AndroidDevice.LaunchModalWebView(this.parentWebView.modalWebviewParams, message);
            this.parentWebView.OnCreateWindow(this.parentWebView.GetTime(), "ignored");
        } catch (Exception e) {
        }
    }

    @Override // android.webkit.WebChromeClient
    public void onPermissionRequest(PermissionRequest permissionRequest) {
        permissionRequest.grant(permissionRequest.getResources());
    }

    @Override // android.webkit.WebChromeClient
    public void onCloseWindow(WebView webView) {
    }

    @Override // android.webkit.WebChromeClient
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        return WebViewFileChooser.ShowFileChooser(valueCallback, fileChooserParams);
    }

    @Override // android.webkit.WebChromeClient
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }
}
