package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: compiled from: FragmentHostCallback.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class z<E> extends x {
    final int aL;
    private android.support.v4.e.r<String, bj> aM;
    private boolean aN;
    private final Activity mActivity;
    private boolean mCheckedForLoaderManager;
    final Context mContext;
    final ad mFragmentManager;
    private final Handler mHandler;
    private bl mLoaderManager;
    private boolean mLoadersStarted;

    public abstract E onGetHost();

    z(FragmentActivity fragmentActivity) {
        this(fragmentActivity, fragmentActivity, fragmentActivity.mHandler, 0);
    }

    z(Activity activity, Context context, Handler handler, int i) {
        this.mFragmentManager = new ad();
        this.mActivity = activity;
        this.mContext = context;
        this.mHandler = handler;
        this.aL = i;
    }

    public void onDump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public boolean b(Fragment fragment) {
        return true;
    }

    public LayoutInflater onGetLayoutInflater() {
        return (LayoutInflater) this.mContext.getSystemService("layout_inflater");
    }

    public void r() {
    }

    public void a(Fragment fragment, Intent intent, int i, Bundle bundle) {
        if (i != -1) {
            throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
        }
        this.mContext.startActivity(intent);
    }

    public void a(Fragment fragment, IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) {
        if (i != -1) {
            throw new IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host");
        }
        a.a(this.mActivity, intentSender, i, intent, i2, i3, i4, bundle);
    }

    public void a(Fragment fragment, String[] strArr, int i) {
    }

    public boolean f(String str) {
        return false;
    }

    public boolean onHasWindowAnimations() {
        return true;
    }

    public int onGetWindowAnimations() {
        return this.aL;
    }

    @Override // android.support.v4.app.x
    public View onFindViewById(int i) {
        return null;
    }

    @Override // android.support.v4.app.x
    public boolean onHasView() {
        return true;
    }

    Activity getActivity() {
        return this.mActivity;
    }

    Context getContext() {
        return this.mContext;
    }

    Handler getHandler() {
        return this.mHandler;
    }

    ad v() {
        return this.mFragmentManager;
    }

    bl w() {
        if (this.mLoaderManager != null) {
            return this.mLoaderManager;
        }
        this.mCheckedForLoaderManager = true;
        this.mLoaderManager = a("(root)", this.mLoadersStarted, true);
        return this.mLoaderManager;
    }

    void g(String str) {
        bl blVar;
        if (this.aM != null && (blVar = (bl) this.aM.get(str)) != null && !blVar.mRetaining) {
            blVar.W();
            this.aM.remove(str);
        }
    }

    void onAttachFragment(Fragment fragment) {
    }

    boolean x() {
        return this.aN;
    }

    void doLoaderStart() {
        if (!this.mLoadersStarted) {
            this.mLoadersStarted = true;
            if (this.mLoaderManager != null) {
                this.mLoaderManager.Q();
            } else if (!this.mCheckedForLoaderManager) {
                this.mLoaderManager = a("(root)", this.mLoadersStarted, false);
                if (this.mLoaderManager != null && !this.mLoaderManager.mStarted) {
                    this.mLoaderManager.Q();
                }
            }
            this.mCheckedForLoaderManager = true;
        }
    }

    void doLoaderStop(boolean z) {
        this.aN = z;
        if (this.mLoaderManager != null && this.mLoadersStarted) {
            this.mLoadersStarted = false;
            if (z) {
                this.mLoaderManager.S();
            } else {
                this.mLoaderManager.R();
            }
        }
    }

    void doLoaderDestroy() {
        if (this.mLoaderManager != null) {
            this.mLoaderManager.W();
        }
    }

    void reportLoaderStart() {
        if (this.aM != null) {
            int size = this.aM.size();
            bl[] blVarArr = new bl[size];
            for (int i = size - 1; i >= 0; i--) {
                blVarArr[i] = (bl) this.aM.valueAt(i);
            }
            for (int i2 = 0; i2 < size; i2++) {
                bl blVar = blVarArr[i2];
                blVar.T();
                blVar.V();
            }
        }
    }

    bl a(String str, boolean z, boolean z2) {
        if (this.aM == null) {
            this.aM = new android.support.v4.e.r<>();
        }
        bl blVar = (bl) this.aM.get(str);
        if (blVar == null && z2) {
            bl blVar2 = new bl(str, this, z);
            this.aM.put(str, blVar2);
            return blVar2;
        }
        if (z && blVar != null && !blVar.mStarted) {
            blVar.Q();
            return blVar;
        }
        return blVar;
    }

    android.support.v4.e.r<String, bj> u() {
        boolean z;
        if (this.aM != null) {
            int size = this.aM.size();
            bl[] blVarArr = new bl[size];
            for (int i = size - 1; i >= 0; i--) {
                blVarArr[i] = (bl) this.aM.valueAt(i);
            }
            boolean zX = x();
            z = false;
            for (int i2 = 0; i2 < size; i2++) {
                bl blVar = blVarArr[i2];
                if (!blVar.mRetaining && zX) {
                    if (!blVar.mStarted) {
                        blVar.Q();
                    }
                    blVar.S();
                }
                if (blVar.mRetaining) {
                    z = true;
                } else {
                    blVar.W();
                    this.aM.remove(blVar.mWho);
                }
            }
        } else {
            z = false;
        }
        if (z) {
            return this.aM;
        }
        return null;
    }

    void a(android.support.v4.e.r<String, bj> rVar) {
        if (rVar != null) {
            int size = rVar.size();
            for (int i = 0; i < size; i++) {
                ((bl) rVar.valueAt(i)).b(this);
            }
        }
        this.aM = rVar;
    }

    void dumpLoaders(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mLoadersStarted=");
        printWriter.println(this.mLoadersStarted);
        if (this.mLoaderManager != null) {
            printWriter.print(str);
            printWriter.print("Loader Manager ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this.mLoaderManager)));
            printWriter.println(":");
            this.mLoaderManager.dump(String.valueOf(str) + "  ", fileDescriptor, printWriter, strArr);
        }
    }
}
