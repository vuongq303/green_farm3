package com.facebook.internal.instrument.errorreport;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.instrument.InstrumentUtility;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public final class ErrorReportHandler {
    private static final int MAX_ERROR_REPORT_NUM = 1000;

    public static void save(String str) {
        try {
            new ErrorReportData(str).save();
        } catch (Exception e) {
        }
    }

    public static void enable() {
        if (FacebookSdk.getAutoLogAppEventsEnabled()) {
            sendErrorReports();
        }
    }

    public static void sendErrorReports() {
        File[] fileArrListErrorReportFiles = listErrorReportFiles();
        final ArrayList arrayList = new ArrayList();
        for (File file : fileArrListErrorReportFiles) {
            ErrorReportData errorReportData = new ErrorReportData(file);
            if (errorReportData.isValid()) {
                arrayList.add(errorReportData);
            }
        }
        Collections.sort(arrayList, new Comparator<ErrorReportData>() { // from class: com.facebook.internal.instrument.errorreport.ErrorReportHandler.1
            @Override // java.util.Comparator
            public int compare(ErrorReportData errorReportData2, ErrorReportData errorReportData3) {
                return errorReportData2.compareTo(errorReportData3);
            }
        });
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < arrayList.size() && i < 1000; i++) {
            jSONArray.put(arrayList.get(i));
        }
        InstrumentUtility.sendReports("error_reports", jSONArray, new GraphRequest.Callback() { // from class: com.facebook.internal.instrument.errorreport.ErrorReportHandler.2
            @Override // com.facebook.GraphRequest.Callback
            public void onCompleted(GraphResponse graphResponse) {
                try {
                    if (graphResponse.getError() == null && graphResponse.getJSONObject().getBoolean("success")) {
                        int i2 = 0;
                        while (true) {
                            int i3 = i2;
                            if (arrayList.size() > i3) {
                                ((ErrorReportData) arrayList.get(i3)).clear();
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

    public static File[] listErrorReportFiles() {
        File instrumentReportDir = InstrumentUtility.getInstrumentReportDir();
        return instrumentReportDir == null ? new File[0] : instrumentReportDir.listFiles(new FilenameFilter() { // from class: com.facebook.internal.instrument.errorreport.ErrorReportHandler.3
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str) {
                return str.matches(String.format("^%s[0-9]+.json$", InstrumentUtility.ERROR_REPORT_PREFIX));
            }
        });
    }
}
