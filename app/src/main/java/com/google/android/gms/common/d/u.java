package com.google.android.gms.common.d;

import android.os.Process;
import android.os.StrictMode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class u {
    private static String bQH = null;
    private static int bQI = 0;

    @Nullable
    public static String abF() {
        if (bQH == null) {
            bQH = nk(abG());
        }
        return bQH;
    }

    private static int abG() {
        if (bQI == 0) {
            bQI = Process.myPid();
        }
        return bQI;
    }

    private static BufferedReader ik(String str) {
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            return new BufferedReader(new FileReader(str));
        } finally {
            StrictMode.setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    @Nullable
    private static String nk(int i) throws Throwable {
        Throwable th;
        BufferedReader bufferedReader;
        BufferedReader bufferedReaderIk;
        String strTrim = null;
        if (i > 0) {
            try {
                bufferedReaderIk = ik(new StringBuilder(25).append("/proc/").append(i).append("/cmdline").toString());
                try {
                    strTrim = bufferedReaderIk.readLine().trim();
                    o.closeQuietly(bufferedReaderIk);
                } catch (IOException e) {
                    o.closeQuietly(bufferedReaderIk);
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReaderIk;
                    o.closeQuietly(bufferedReader);
                    throw th;
                }
            } catch (IOException e2) {
                bufferedReaderIk = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedReader = null;
            }
        }
        return strTrim;
    }
}
