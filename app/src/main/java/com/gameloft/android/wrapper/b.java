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

    // Recovered from the original APK classes.dex, method ea(String).
    public long ea(String str) {
        long size = 0;
        if (str == null || str.isEmpty()) {
            return size;
        }
        String apkPath = Ov();
        try {
            ZipFile zipFile = new ZipFile(apkPath);
            ZipEntry entry = zipFile.getEntry(str);
            if (entry != null) {
                size = entry.getSize();
            } else {
                if (!str.endsWith(java.io.File.separator)) {
                    str += java.io.File.separator;
                }
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry child = entries.nextElement();
                    if (child.getName().startsWith(str)) {
                        size += child.getSize();
                    }
                }
            }
            zipFile.close();
        } catch (Exception e) {
            // The original returns the accumulated size if ZIP traversal fails.
        }
        return size;
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
