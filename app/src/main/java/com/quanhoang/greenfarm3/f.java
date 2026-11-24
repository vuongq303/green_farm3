package com.quanhoang.greenfarm3;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;
import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.MessengerShareContentUtility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/* JADX INFO: compiled from: EmulatorDetector.java */
/* JADX INFO: loaded from: classes.dex */
public class f {
    private static boolean AL = false;
    private static int AM = 0;
    public static String AN = "";
    private static f AU;
    private final String[] AO = {"intel", "amd"};
    private final String[] AP = {"qcom", "exynos", "universal", "hi", "mt"};
    private final String[] AQ = {"/", "/system/user/idc/", "/system/lib/hw/"};
    private final String[] AS = {"android_x86", "bluestacks", ".nox", ".memu"};
    private final String[] AT = {"/selinux_version", "/default.prop"};
    private boolean AV = false;
    private final Context mContext;

    private f(Context context) {
        this.mContext = context;
    }

    public static boolean fM() {
        Activity activity = com.gameloft.android.wrapper.au.getActivity();
        if (activity == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        if (AU == null) {
            AU = new f(activity);
        }
        return AU.fO();
    }

    public boolean fN() {
        return AL;
    }

    private boolean fO() {
        if (!this.AV) {
            if (fP()) {
                this.AV = true;
                a("BASIC_LOG Check Proc " + this.AV, 0);
                AN = "You are using emulator processor";
            }
            if (fR()) {
                this.AV = true;
                a("BASIC_LOG Check Build Prop " + this.AV, 0);
                AN = "You are using emulator Build Prop";
            }
        }
        if (!this.AV && fS()) {
            this.AV = true;
            a("BASIC_LOG Check Advanced " + this.AV, 0);
        }
        return this.AV;
    }

    private boolean fP() {
        boolean z;
        boolean z2;
        Map<String, String> mapFQ = fQ();
        String[] strArr = this.AO;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            if (!mapFQ.toString().toLowerCase().contains(strArr[i])) {
                i++;
            } else {
                a("ADVANCED_LOG isContainsEmuProc true", 1);
                z = true;
                break;
            }
        }
        String[] strArr2 = this.AP;
        int length2 = strArr2.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length2) {
                z2 = false;
                break;
            }
            if (!Build.HARDWARE.toLowerCase().contains(strArr2[i2])) {
                i2++;
            } else {
                a("ADVANCED_LOG isContainAndroidProc true", 1);
                z2 = true;
                break;
            }
        }
        return z && z2;
    }

    public static Map<String, String> fQ() {
        HashMap map = new HashMap();
        try {
            Scanner scanner = new Scanner(new File("/proc/cpuinfo"));
            while (scanner.hasNextLine()) {
                String[] strArrSplit = scanner.nextLine().split(": ");
                if (strArrSplit.length > 1) {
                    map.put(strArrSplit[0].trim(), strArrSplit[1].trim());
                }
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
        return map;
    }

    private boolean fR() {
        return Build.FINGERPRINT.toLowerCase().contains(MessengerShareContentUtility.TEMPLATE_GENERIC_TYPE) || Build.FINGERPRINT.toLowerCase().contains("unknown") || Build.MODEL.toLowerCase().contains(ServerProtocol.DIALOG_PARAM_SDK_VERSION) || Build.MODEL.toLowerCase().contains("emulator") || Build.BRAND.toLowerCase().contains(MessengerShareContentUtility.TEMPLATE_GENERIC_TYPE) || Build.DEVICE.toLowerCase().contains(MessengerShareContentUtility.TEMPLATE_GENERIC_TYPE);
    }

    private boolean fS() {
        if (fT()) {
            a("ADVANCED_LOG You device is using Emulator buildprint", 1);
            return true;
        }
        if (fU()) {
            a("ADVANCED_LOG You device is using Emulator Filesystem", 1);
            return true;
        }
        if (n("bluestacks")) {
            a("ADVANCED_LOG You device is using Emulator wifiSSID", 1);
            return true;
        }
        return this.AV;
    }

    private boolean fT() {
        for (String str : this.AS) {
            for (String str2 : this.AT) {
                if (b(str2, str)) {
                    AN = "Fingerprint is emulator";
                    return true;
                }
            }
        }
        return false;
    }

    private boolean fU() {
        for (String str : this.AS) {
            for (String str2 : this.AQ) {
                if (a(str2, str)) {
                    AN = "Have some file which being used in emulator";
                    return true;
                }
            }
        }
        return false;
    }

    private boolean a(String str, String str2) {
        try {
            for (File file : new File(str).listFiles()) {
                String name = file.getName();
                a("ALL_LOG FileName:" + name + " ContainsFilename = " + str2, 2);
                if (name.toLowerCase().contains(str2)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean n(String str) {
        try {
            String ssid = ((WifiManager) this.mContext.getSystemService("wifi")).getConnectionInfo().getSSID();
            a("ALL_LOG ssid --> " + ssid, 2);
            if (ssid.toLowerCase().contains(str)) {
                return true;
            }
            return false;
        } catch (Exception e) {
        }
    }

    private boolean b(String str, String str2) {
        String line;
        try {
            FileReader fileReader = new FileReader(str);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            do {
                line = bufferedReader.readLine();
                if (line != null) {
                    a("ALL_LOG" + str + " data --> " + line, 2);
                } else {
                    bufferedReader.close();
                    fileReader.close();
                }
                return false;
            } while (!line.toLowerCase().contains(str2));
            return true;
        } catch (Exception e) {
        }
    }

    private void a(String str, int i) {
        if (fN() && AM >= i) {
            getClass().getName();
        }
    }
}
