package com.moat.analytics.mobile.glft;

import com.gameloft.glads.AndroidWebView;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes.dex */
class o {
    o() {
    }

    private static String a(InputStream inputStream) throws IOException {
        char[] cArr = new char[256];
        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, AndroidWebView.UTF_8);
        int i = 0;
        do {
            int i2 = inputStreamReader.read(cArr, 0, cArr.length);
            if (i2 <= 0) {
                break;
            }
            i += i2;
            sb.append(cArr, 0, i2);
        } while (i < 1024);
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r1v3, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.io.IOException] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0052 -> B:36:0x0038). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x0054 -> B:36:0x0038). Please report as a decompilation issue!!! */
    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:20:0x0058
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1478)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    static com.moat.analytics.mobile.glft.a.b.a<java.lang.String> jb(java.lang.String r4) {
        /*
            r1 = 0
            java.net.URL r0 = new java.net.URL     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            r0.<init>(r4)     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            java.net.URLConnection r0 = r0.openConnection()     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            java.net.HttpURLConnection r0 = (java.net.HttpURLConnection) r0     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            r2 = 0
            r0.setUseCaches(r2)     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            r2 = 10000(0x2710, float:1.4013E-41)
            r0.setReadTimeout(r2)     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            r2 = 15000(0x3a98, float:2.102E-41)
            r0.setConnectTimeout(r2)     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            java.lang.String r2 = "GET"
            r0.setRequestMethod(r2)     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            r2 = 1
            r0.setDoInput(r2)     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            r0.connect()     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            int r2 = r0.getResponseCode()     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            r3 = 400(0x190, float:5.6E-43)
            if (r2 < r3) goto L39
            com.moat.analytics.mobile.glft.a.b.a r0 = com.moat.analytics.mobile.glft.a.b.a.a()     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            if (r1 == 0) goto L38
            r1.close()     // Catch: java.io.IOException -> L61
        L38:
            return r0
        L39:
            java.io.InputStream r1 = r0.getInputStream()     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            java.lang.String r0 = a(r1)     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            com.moat.analytics.mobile.glft.a.b.a r0 = com.moat.analytics.mobile.glft.a.b.a.a(r0)     // Catch: java.io.IOException -> L4d java.lang.Throwable -> L5a
            if (r1 == 0) goto L38
            r1.close()     // Catch: java.io.IOException -> L4b
            goto L38
        L4b:
            r1 = move-exception
            goto L38
        L4d:
            r0 = move-exception
            com.moat.analytics.mobile.glft.a.b.a r0 = com.moat.analytics.mobile.glft.a.b.a.a()     // Catch: java.lang.Throwable -> L5a
            if (r1 == 0) goto L38
            r1.close()     // Catch: java.io.IOException -> L58
            goto L38
        L58:
            r1 = move-exception
            goto L38
        L5a:
            r0 = move-exception
            if (r1 == 0) goto L60
            r1.close()     // Catch: java.io.IOException -> L63
        L60:
            throw r0
        L61:
            r1 = move-exception
            goto L38
        L63:
            r1 = move-exception
            goto L60
        */
        throw new UnsupportedOperationException("Method not decompiled: com.moat.analytics.mobile.glft.o.jb(java.lang.String):com.moat.analytics.mobile.glft.a.b.a");
    }
}
