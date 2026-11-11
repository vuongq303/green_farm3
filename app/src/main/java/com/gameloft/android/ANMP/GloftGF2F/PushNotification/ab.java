package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: compiled from: RemoteImageManager.java */
/* JADX INFO: loaded from: classes.dex */
final class ab extends Thread {
    final /* synthetic */ Context val$context;

    ab(Context context) {
        this.val$context = context;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        String unused = aa.Gl = aa.ah(this.val$context);
        if (aa.Gl == null || aa.Gl.isEmpty()) {
            aa.aj(this.val$context);
            String unused2 = aa.Gl = aa.ah(this.val$context);
            if (aa.Gl == null || aa.Gl.isEmpty()) {
                Boolean unused3 = aa.Gk = true;
                return;
            }
        }
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(aa.Gl).openConnection();
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla");
            httpURLConnection.addRequestProperty("Referer", "google.com");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode != 200 && (responseCode == 302 || responseCode == 301 || responseCode == 303)) {
                httpURLConnection = (HttpURLConnection) new URL(httpURLConnection.getHeaderField("Location")).openConnection();
            }
            String headerField = httpURLConnection.getHeaderField("ETag");
            Environment.getExternalStorageDirectory().getAbsolutePath();
            String strN = j.n(this.val$context, q.FN);
            if (headerField != null && !headerField.isEmpty()) {
                if (headerField.equals(strN)) {
                    Bitmap unused4 = aa.Gj = j.l(this.val$context, q.FN);
                    return;
                }
                try {
                    Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                    if (j.a(this.val$context, bitmapDecodeStream, q.FN) != null) {
                        j.c(this.val$context, q.FN, headerField);
                        j.M(this.val$context);
                        j.a(this.val$context, q.FN, Long.valueOf(System.currentTimeMillis()));
                        j.N(this.val$context);
                    }
                    Bitmap unused5 = aa.Gj = bitmapDecodeStream;
                    return;
                } catch (IOException e) {
                    return;
                }
            }
            aa.af(this.val$context);
        } catch (Exception e2) {
            Boolean unused6 = aa.Gk = true;
            Bitmap unused7 = aa.Gj = null;
        }
    }
}
