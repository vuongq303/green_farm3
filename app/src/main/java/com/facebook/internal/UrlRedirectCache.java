package com.facebook.internal;

import android.net.Uri;
import com.facebook.LoggingBehavior;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
class UrlRedirectCache {
    private static FileLruCache urlRedirectCache;
    static final String TAG = UrlRedirectCache.class.getSimpleName();
    private static final String REDIRECT_CONTENT_TAG = TAG + "_Redirect";

    UrlRedirectCache() {
    }

    static synchronized FileLruCache getCache() {
        if (urlRedirectCache == null) {
            urlRedirectCache = new FileLruCache(TAG, new FileLruCache.Limits());
        }
        return urlRedirectCache;
    }

    static Uri getRedirectedUri(Uri uri) throws Throwable {
        Throwable th;
        InputStreamReader inputStreamReader;
        InputStreamReader inputStreamReader2;
        Uri uri2 = null;
        if (uri != null) {
            String string = uri.toString();
            try {
                FileLruCache cache = getCache();
                boolean z = false;
                inputStreamReader = null;
                while (true) {
                    try {
                        InputStream inputStream = cache.get(string, REDIRECT_CONTENT_TAG);
                        if (inputStream == null) {
                            break;
                        }
                        z = true;
                        inputStreamReader2 = new InputStreamReader(inputStream);
                        try {
                            char[] cArr = new char[128];
                            StringBuilder sb = new StringBuilder();
                            while (true) {
                                int i = inputStreamReader2.read(cArr, 0, cArr.length);
                                if (i <= 0) {
                                    break;
                                }
                                sb.append(cArr, 0, i);
                            }
                            Utility.closeQuietly(inputStreamReader2);
                            string = sb.toString();
                            inputStreamReader = inputStreamReader2;
                        } catch (IOException e) {
                        } catch (Throwable th2) {
                            th = th2;
                            inputStreamReader = inputStreamReader2;
                            Utility.closeQuietly(inputStreamReader);
                            throw th;
                        }
                    } catch (IOException e2) {
                        inputStreamReader2 = inputStreamReader;
                    } catch (Throwable th3) {
                        th = th3;
                    }
                    Utility.closeQuietly(inputStreamReader2);
                }
                if (z) {
                    uri2 = Uri.parse(string);
                    Utility.closeQuietly(inputStreamReader);
                } else {
                    Utility.closeQuietly(inputStreamReader);
                }
            } catch (IOException e3) {
                inputStreamReader2 = null;
            } catch (Throwable th4) {
                th = th4;
                inputStreamReader = null;
            }
        }
        return uri2;
    }

    static void cacheUriRedirect(Uri uri, Uri uri2) {
        if (uri != null && uri2 != null) {
            OutputStream outputStreamOpenPutStream = null;
            try {
                outputStreamOpenPutStream = getCache().openPutStream(uri.toString(), REDIRECT_CONTENT_TAG);
                outputStreamOpenPutStream.write(uri2.toString().getBytes());
            } catch (IOException e) {
            } finally {
                Utility.closeQuietly(outputStreamOpenPutStream);
            }
        }
    }

    static void clearCache() {
        try {
            getCache().clearCache();
        } catch (IOException e) {
            Logger.log(LoggingBehavior.CACHE, 5, TAG, "clearCache failed " + e.getMessage());
        }
    }
}
