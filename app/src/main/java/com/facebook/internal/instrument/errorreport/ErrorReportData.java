package com.facebook.internal.instrument.errorreport;

import com.facebook.internal.instrument.InstrumentUtility;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final class ErrorReportData {
    private static final String PARAM_TIMESTAMP = "timestamp";
    private static final String PRARAM_ERROR_MESSAGE = "error_message";
    private String errorMessage;
    private String filename;
    private Long timestamp;

    public ErrorReportData(String str) {
        this.timestamp = Long.valueOf(System.currentTimeMillis() / 1000);
        this.errorMessage = str;
        this.filename = new StringBuffer().append(InstrumentUtility.ERROR_REPORT_PREFIX).append(this.timestamp).append(".json").toString();
    }

    public ErrorReportData(File file) {
        this.filename = file.getName();
        JSONObject file2 = InstrumentUtility.readFile(this.filename, true);
        if (file2 != null) {
            this.timestamp = Long.valueOf(file2.optLong(PARAM_TIMESTAMP, 0L));
            this.errorMessage = file2.optString("error_message", null);
        }
    }

    public int compareTo(ErrorReportData errorReportData) {
        if (this.timestamp == null) {
            return -1;
        }
        if (errorReportData.timestamp == null) {
            return 1;
        }
        return errorReportData.timestamp.compareTo(this.timestamp);
    }

    public boolean isValid() {
        return (this.errorMessage == null || this.timestamp == null) ? false : true;
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
            if (this.timestamp != null) {
                jSONObject.put(PARAM_TIMESTAMP, this.timestamp);
            }
            jSONObject.put("error_message", this.errorMessage);
            return jSONObject;
        } catch (JSONException e) {
            return null;
        }
    }
}
