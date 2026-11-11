package com.google.android.gms.common.api.a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: loaded from: classes.dex */
public class t {
    protected final u bJO;

    protected t(u uVar) {
        this.bJO = uVar;
    }

    protected static u a(s sVar) {
        if (sVar.Zj()) {
            return ch.a(sVar.Zm());
        }
        if (sVar.Zk()) {
            return bw.q(sVar.Zl());
        }
        throw new IllegalArgumentException("Can't get fragment for unexpected activity.");
    }

    public static u p(Activity activity) {
        return a(new s(activity));
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public final Activity getActivity() {
        return this.bJO.Zn();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public void onCreate(Bundle bundle) {
    }

    public void onDestroy() {
    }

    public void onResume() {
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    public void onStart() {
    }

    public void onStop() {
    }
}
