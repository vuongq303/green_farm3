package com.quanhoang.greenfarm3.testsupport;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import java.util.List;
import org.json.JSONArray;

/** Separate test APK only. Never included in the game APK or its launch activity. */
public final class SaveTransferInstrumentation extends Instrumentation {
    private String prefix;
    @Override public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        prefix = arguments == null ? null : arguments.getString("fixture_prefix");
        start();
    }
    @Override public void onStart() {
        Bundle result = new Bundle();
        try {
            List<String> checks = SaveTransferDatabaseProbe.runInContext(getTargetContext(), prefix);
            result.putString("probe_status", "passed");
            result.putString("checks_json", new JSONArray(checks).toString());
            result.putString("checks_count", Integer.toString(checks.size()));
            finish(Activity.RESULT_OK, result);
        } catch (Throwable failure) {
            result.putString("probe_status", "failed");
            result.putString("error_class", failure.getClass().getName());
            result.putString("error", String.valueOf(failure.getMessage()));
            android.util.Log.e("GF3TransferProbe", "isolated transfer fixture failed", failure);
            finish(Activity.RESULT_CANCELED, result);
        }
    }
}
