package com.quanhoang.greenfarm3;

import android.app.Activity;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class Nt extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (getIntent().getBooleanExtra(Pt.hb().hc(), false)) {
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
