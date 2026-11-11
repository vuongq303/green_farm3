package android.support.v4.app;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: compiled from: FragmentActivity.java */
/* JADX INFO: loaded from: classes.dex */
class v extends z<FragmentActivity> {
    final /* synthetic */ FragmentActivity aH;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public v(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.aH = fragmentActivity;
    }

    @Override // android.support.v4.app.z
    public void onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.aH.dump(str, fileDescriptor, printWriter, strArr);
    }

    @Override // android.support.v4.app.z
    public boolean b(Fragment fragment) {
        return !this.aH.isFinishing();
    }

    @Override // android.support.v4.app.z
    public LayoutInflater onGetLayoutInflater() {
        return this.aH.getLayoutInflater().cloneInContext(this.aH);
    }

    @Override // android.support.v4.app.z
    /* JADX INFO: renamed from: q, reason: merged with bridge method [inline-methods] */
    public FragmentActivity onGetHost() {
        return this.aH;
    }

    @Override // android.support.v4.app.z
    public void r() {
        this.aH.supportInvalidateOptionsMenu();
    }

    @Override // android.support.v4.app.z
    public void a(Fragment fragment, Intent intent, int i, Bundle bundle) {
        this.aH.startActivityFromFragment(fragment, intent, i, bundle);
    }

    @Override // android.support.v4.app.z
    public void a(Fragment fragment, IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) {
        this.aH.startIntentSenderFromFragment(fragment, intentSender, i, intent, i2, i3, i4, bundle);
    }

    @Override // android.support.v4.app.z
    public void a(Fragment fragment, String[] strArr, int i) {
        this.aH.requestPermissionsFromFragment(fragment, strArr, i);
    }

    @Override // android.support.v4.app.z
    public boolean f(String str) {
        return a.a((Activity) this.aH, str);
    }

    @Override // android.support.v4.app.z
    public boolean onHasWindowAnimations() {
        return this.aH.getWindow() != null;
    }

    @Override // android.support.v4.app.z
    public int onGetWindowAnimations() {
        Window window = this.aH.getWindow();
        if (window == null) {
            return 0;
        }
        return window.getAttributes().windowAnimations;
    }

    @Override // android.support.v4.app.z
    public void onAttachFragment(Fragment fragment) {
        this.aH.onAttachFragment(fragment);
    }

    @Override // android.support.v4.app.z, android.support.v4.app.x
    public View onFindViewById(int i) {
        return this.aH.findViewById(i);
    }

    @Override // android.support.v4.app.z, android.support.v4.app.x
    public boolean onHasView() {
        Window window = this.aH.getWindow();
        return (window == null || window.peekDecorView() == null) ? false : true;
    }
}
