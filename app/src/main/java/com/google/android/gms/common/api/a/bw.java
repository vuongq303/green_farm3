package com.google.android.gms.common.api.a;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public final class bw extends Fragment implements u {
    private static WeakHashMap<Activity, WeakReference<bw>> bLn = new WeakHashMap<>();
    private Bundle bLp;
    private Map<String, t> bLo = new android.support.v4.e.a();
    private int zzlc = 0;

    public static bw q(Activity activity) {
        bw bwVar;
        WeakReference<bw> weakReference = bLn.get(activity);
        if (weakReference == null || (bwVar = weakReference.get()) == null) {
            try {
                bwVar = (bw) activity.getFragmentManager().findFragmentByTag("LifecycleFragmentImpl");
                if (bwVar == null || bwVar.isRemoving()) {
                    bwVar = new bw();
                    activity.getFragmentManager().beginTransaction().add(bwVar, "LifecycleFragmentImpl").commitAllowingStateLoss();
                }
                bLn.put(activity, new WeakReference<>(bwVar));
            } catch (ClassCastException e) {
                throw new IllegalStateException("Fragment with tag LifecycleFragmentImpl is not a LifecycleFragmentImpl", e);
            }
        }
        return bwVar;
    }

    @Override // com.google.android.gms.common.api.a.u
    public final Activity Zn() {
        return getActivity();
    }

    @Override // com.google.android.gms.common.api.a.u
    public final <T extends t> T a(String str, Class<T> cls) {
        return cls.cast(this.bLo.get(str));
    }

    @Override // com.google.android.gms.common.api.a.u
    public final void a(String str, t tVar) {
        if (this.bLo.containsKey(str)) {
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 59).append("LifecycleCallback with tag ").append(str).append(" already added to this fragment.").toString());
        }
        this.bLo.put(str, tVar);
        if (this.zzlc > 0) {
            new Handler(Looper.getMainLooper()).post(new bx(this, tVar, str));
        }
    }

    @Override // android.app.Fragment
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        Iterator<t> it = this.bLo.values().iterator();
        while (it.hasNext()) {
            it.next().dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    @Override // android.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        Iterator<t> it = this.bLo.values().iterator();
        while (it.hasNext()) {
            it.next().onActivityResult(i, i2, intent);
        }
    }

    @Override // android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzlc = 1;
        this.bLp = bundle;
        for (Map.Entry<String, t> entry : this.bLo.entrySet()) {
            entry.getValue().onCreate(bundle != null ? bundle.getBundle(entry.getKey()) : null);
        }
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.zzlc = 5;
        Iterator<t> it = this.bLo.values().iterator();
        while (it.hasNext()) {
            it.next().onDestroy();
        }
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        this.zzlc = 3;
        Iterator<t> it = this.bLo.values().iterator();
        while (it.hasNext()) {
            it.next().onResume();
        }
    }

    @Override // android.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle == null) {
            return;
        }
        for (Map.Entry<String, t> entry : this.bLo.entrySet()) {
            Bundle bundle2 = new Bundle();
            entry.getValue().onSaveInstanceState(bundle2);
            bundle.putBundle(entry.getKey(), bundle2);
        }
    }

    @Override // android.app.Fragment
    public final void onStart() {
        super.onStart();
        this.zzlc = 2;
        Iterator<t> it = this.bLo.values().iterator();
        while (it.hasNext()) {
            it.next().onStart();
        }
    }

    @Override // android.app.Fragment
    public final void onStop() {
        super.onStop();
        this.zzlc = 4;
        Iterator<t> it = this.bLo.values().iterator();
        while (it.hasNext()) {
            it.next().onStop();
        }
    }
}
