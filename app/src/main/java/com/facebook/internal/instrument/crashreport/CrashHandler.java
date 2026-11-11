package com.facebook.internal.instrument.crashreport;

import android.os.Process;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.instrument.InstrumentUtility;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final int MAX_CRASH_REPORT_NUM = 5;
    private static final String TAG = CrashHandler.class.getCanonicalName();
    private static CrashHandler instance;
    private boolean mEndApplication = false;
    private final Thread.UncaughtExceptionHandler mPreviousHandler;

    private CrashHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.mPreviousHandler = uncaughtExceptionHandler;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        if (InstrumentUtility.isSDKRelatedException(th)) {
            new CrashReportData(th).save();
        }
        if (this.mPreviousHandler != null) {
            this.mPreviousHandler.uncaughtException(thread, th);
        }
        if (this.mEndApplication) {
            killProcess();
        }
    }

    public static synchronized void enable() {
        if (FacebookSdk.getAutoLogAppEventsEnabled()) {
            sendCrashReports();
        }
        if (instance != null) {
            String str = TAG;
        } else {
            instance = new CrashHandler(Thread.getDefaultUncaughtExceptionHandler());
            Thread.setDefaultUncaughtExceptionHandler(instance);
        }
    }

    public void endApplication() {
        this.mEndApplication = true;
    }

    private static void killProcess() {
        try {
            Process.killProcess(Process.myPid());
            System.exit(10);
        } catch (Throwable th) {
        }
    }

    private static void sendCrashReports() {
        File[] fileArrListCrashReportFiles = InstrumentUtility.listCrashReportFiles();
        final ArrayList arrayList = new ArrayList();
        for (File file : fileArrListCrashReportFiles) {
            CrashReportData crashReportData = new CrashReportData(file);
            if (crashReportData.isValid()) {
                arrayList.add(crashReportData);
            }
        }
        Collections.sort(arrayList, new Comparator<CrashReportData>() { // from class: com.facebook.internal.instrument.crashreport.CrashHandler.1
            @Override // java.util.Comparator
            public int compare(CrashReportData crashReportData2, CrashReportData crashReportData3) {
                return crashReportData2.compareTo(crashReportData3);
            }
        });
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < arrayList.size() && i < 5; i++) {
            jSONArray.put(arrayList.get(i));
        }
        InstrumentUtility.sendReports("crash_reports", jSONArray, new GraphRequest.Callback() { // from class: com.facebook.internal.instrument.crashreport.CrashHandler.2
            @Override // com.facebook.GraphRequest.Callback
            public void onCompleted(GraphResponse graphResponse) {
                try {
                    if (graphResponse.getError() == null && graphResponse.getJSONObject().getBoolean("success")) {
                        int i2 = 0;
                        while (true) {
                            int i3 = i2;
                            if (arrayList.size() > i3) {
                                ((CrashReportData) arrayList.get(i3)).clear();
                                i2 = i3 + 1;
                            } else {
                                return;
                            }
                        }
                    }
                } catch (JSONException e) {
                }
            }
        });
    }
}
