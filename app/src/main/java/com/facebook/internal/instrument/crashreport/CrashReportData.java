package com.facebook.internal.instrument.crashreport;

import android.os.Build;
import com.facebook.internal.Utility;
import com.facebook.internal.instrument.InstrumentUtility;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class CrashReportData {
    private static final String PARAM_APP_VERSION = "app_version";
    private static final String PARAM_CALLSTACK = "callstack";
    private static final String PARAM_DEVICE_MODEL = "device_model";
    private static final String PARAM_DEVICE_OS = "device_os_version";
    private static final String PARAM_REASON = "reason";
    private static final String PARAM_TIMESTAMP = "timestamp";
    private String appVersion;
    private String cause;
    private String filename;
    private String stackTrace;
    private Long timestamp;

    public CrashReportData(Throwable th) {
        this.appVersion = Utility.getAppVersion();
        this.cause = InstrumentUtility.getCause(th);
        this.stackTrace = InstrumentUtility.getStackTrace(th);
        this.timestamp = Long.valueOf(System.currentTimeMillis() / 1000);
        this.filename = new StringBuffer().append(InstrumentUtility.CRASH_REPORT_PREFIX).append(this.timestamp.toString()).append(".json").toString();
    }

    public CrashReportData(File file) {
        this.filename = file.getName();
        JSONObject file2 = InstrumentUtility.readFile(this.filename, true);
        if (file2 != null) {
            this.appVersion = file2.optString(PARAM_APP_VERSION, null);
            this.cause = file2.optString(PARAM_REASON, null);
            this.stackTrace = file2.optString(PARAM_CALLSTACK, null);
            this.timestamp = Long.valueOf(file2.optLong(PARAM_TIMESTAMP, 0L));
        }
    }

    public int compareTo(CrashReportData crashReportData) {
        if (this.timestamp == null) {
            return -1;
        }
        if (crashReportData.timestamp == null) {
            return 1;
        }
        return crashReportData.timestamp.compareTo(this.timestamp);
    }

    public boolean isValid() {
        return (this.stackTrace == null || this.timestamp == null) ? false : true;
    }

    public void save() {
        if (isValid()) {
            InstrumentUtility.writeFile(this.filename, toString());
        }
    }

    public void clear() {
        InstrumentUtility.deleteFile(this.filename);
    }

    public String toString() {
        JSONObject parameters = getParameters();
        if (parameters == null) {
            return null;
        }
        return parameters.toString();
    }

    public JSONObject getParameters() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PARAM_DEVICE_OS, Build.VERSION.RELEASE);
            jSONObject.put(PARAM_DEVICE_MODEL, Build.MODEL);
            if (this.appVersion != null) {
                jSONObject.put(PARAM_APP_VERSION, this.appVersion);
            }
            if (this.timestamp != null) {
                jSONObject.put(PARAM_TIMESTAMP, this.timestamp);
            }
            if (this.cause != null) {
                jSONObject.put(PARAM_REASON, this.cause);
            }
            if (this.stackTrace != null) {
                jSONObject.put(PARAM_CALLSTACK, this.stackTrace);
                return jSONObject;
            }
            return jSONObject;
        } catch (JSONException e) {
            return null;
        }
    }
}
