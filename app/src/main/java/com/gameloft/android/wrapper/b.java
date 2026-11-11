package com.gameloft.android.wrapper;

import android.content.pm.ApplicationInfo;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: compiled from: ApkManager.java */
/* JADX INFO: loaded from: classes.dex */
public class b {
    private static b beC = null;

    private b() {
    }

    public static b Ou() {
        if (beC == null) {
            beC = new b();
        }
        return beC;
    }

    public String Ov() {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = au.getContext().getPackageManager().getApplicationInfo(au.getContext().getPackageName(), 0);
        } catch (Exception e) {
        }
        return applicationInfo.sourceDir;
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached with updateSeq = 1051. Try increasing type updates limit count.
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:79)
        */
    public long ea(java.lang.String r7) {
        /*
            r6 = this;
            r0 = 0
            if (r7 == 0) goto La
            boolean r2 = r7.isEmpty()
            if (r2 == 0) goto Lb
        La:
            return r0
        Lb:
            java.lang.String r2 = r6.Ov()
            java.util.zip.ZipFile r4 = new java.util.zip.ZipFile     // Catch: java.lang.Exception -> L22
            r4.<init>(r2)     // Catch: java.lang.Exception -> L22
            java.util.zip.ZipEntry r2 = r4.getEntry(r7)     // Catch: java.lang.Exception -> L22
            if (r2 == 0) goto L24
            long r0 = r2.getSize()     // Catch: java.lang.Exception -> L22
        L1e:
            r4.close()     // Catch: java.lang.Exception -> L22
            goto La
        L22:
            r2 = move-exception
            goto La
        L24:
            java.lang.String r2 = java.io.File.separator     // Catch: java.lang.Exception -> L22
            boolean r2 = r7.endsWith(r2)     // Catch: java.lang.Exception -> L22
            if (r2 == 0) goto L4e
        L2c:
            java.util.Enumeration r5 = r4.entries()     // Catch: java.lang.Exception -> L22
            r2 = r0
        L31:
            boolean r0 = r5.hasMoreElements()     // Catch: java.lang.Exception -> L62
            if (r0 == 0) goto L67
            java.lang.Object r0 = r5.nextElement()     // Catch: java.lang.Exception -> L62
            java.util.zip.ZipEntry r0 = (java.util.zip.ZipEntry) r0     // Catch: java.lang.Exception -> L62
            java.lang.String r1 = r0.getName()     // Catch: java.lang.Exception -> L62
            boolean r1 = r1.startsWith(r7)     // Catch: java.lang.Exception -> L62
            if (r1 == 0) goto L65
            long r0 = r0.getSize()     // Catch: java.lang.Exception -> L62
            long r0 = r0 + r2
        L4c:
            r2 = r0
            goto L31
        L4e:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L22
            r2.<init>()     // Catch: java.lang.Exception -> L22
            java.lang.StringBuilder r2 = r2.append(r7)     // Catch: java.lang.Exception -> L22
            java.lang.String r3 = java.io.File.separator     // Catch: java.lang.Exception -> L22
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch: java.lang.Exception -> L22
            java.lang.String r7 = r2.toString()     // Catch: java.lang.Exception -> L22
            goto L2c
        L62:
            r0 = move-exception
            r0 = r2
            goto La
        L65:
            r0 = r2
            goto L4c
        L67:
            r0 = r2
            goto L1e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gameloft.android.wrapper.b.ea(java.lang.String):long");
    }

    public long eb(String str) {
        long size = 0;
        if (str != null && !str.isEmpty()) {
            if (!str.startsWith(".")) {
                str = "." + str;
            }
            try {
                ZipFile zipFile = new ZipFile(Ov());
                Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
                while (enumerationEntries.hasMoreElements()) {
                    ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                    size = zipEntryNextElement.getName().endsWith(str) ? zipEntryNextElement.getSize() + size : size;
                }
                zipFile.close();
            } catch (Exception e) {
            }
        }
        return size;
    }

    public int Ow() {
        int size = 0;
        try {
            ZipFile zipFile = new ZipFile(Ov());
            size = zipFile.size();
            zipFile.close();
            return size;
        } catch (Exception e) {
            return size;
        }
    }
}
