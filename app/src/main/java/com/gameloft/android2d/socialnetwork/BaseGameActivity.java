package com.gameloft.android2d.socialnetwork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseGameActivity extends FragmentActivity implements e {
    protected a bpj;
    protected int bpk = 1;
    protected boolean bma = false;

    protected BaseGameActivity() {
    }

    public a Uu() {
        if (this.bpj == null) {
            this.bpj = new a(this);
            this.bpj.bJ(this.bma);
        }
        return this.bpj;
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.bpj == null) {
            Uu();
        }
        if (!this.bpj.bmc) {
            this.bpj.a(this, 3);
        }
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        this.bpj.a((Activity) this, false);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
        this.bpj.onStop();
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.bpj.onActivityResult(i, i2, intent);
    }
}
