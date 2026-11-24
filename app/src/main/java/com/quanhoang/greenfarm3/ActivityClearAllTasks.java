package com.quanhoang.greenfarm3;

import android.app.Activity;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class ActivityClearAllTasks extends Activity {
    private byte[] Ae = {69, 88, 73, 84};

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent().getBooleanExtra(new String(this.Ae), false)) {
            finish();
            onDestroy();
        }
    }

    @Override // android.app.Activity
    public void onPause() {
        super.onPause();
        finish();
        onDestroy();
    }

    @Override // android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }
}
