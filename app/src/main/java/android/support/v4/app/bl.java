package android.support.v4.app;

import android.os.Bundle;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: compiled from: LoaderManager.java */
/* JADX INFO: loaded from: classes.dex */
class bl extends bj {
    static boolean DEBUG = false;
    final android.support.v4.e.s<bm> cv = new android.support.v4.e.s<>();
    final android.support.v4.e.s<bm> cw = new android.support.v4.e.s<>();
    boolean cx;
    z mHost;
    boolean mRetaining;
    boolean mStarted;
    final String mWho;

    bl(String str, z zVar, boolean z) {
        this.mWho = str;
        this.mHost = zVar;
        this.mStarted = z;
    }

    void b(z zVar) {
        this.mHost = zVar;
    }

    private bm b(int i, Bundle bundle, bk<Object> bkVar) {
        bm bmVar = new bm(this, i, bundle, bkVar);
        bmVar.cA = bkVar.b(i, bundle);
        return bmVar;
    }

    private bm c(int i, Bundle bundle, bk<Object> bkVar) {
        try {
            this.cx = true;
            bm bmVarB = b(i, bundle, bkVar);
            a(bmVarB);
            return bmVarB;
        } finally {
            this.cx = false;
        }
    }

    void a(bm bmVar) {
        this.cv.put(bmVar.mId, bmVar);
        if (this.mStarted) {
            bmVar.start();
        }
    }

    @Override // android.support.v4.app.bj
    public <D> android.support.v4.content.f<D> a(int i, Bundle bundle, bk<D> bkVar) {
        if (this.cx) {
            throw new IllegalStateException("Called while creating a loader");
        }
        bm bmVarC = this.cv.get(i);
        if (DEBUG) {
            Log.v("LoaderManager", "initLoader in " + this + ": args=" + bundle);
        }
        if (bmVarC == null) {
            bmVarC = c(i, bundle, bkVar);
            if (DEBUG) {
                Log.v("LoaderManager", "  Created new loader " + bmVarC);
            }
        } else {
            if (DEBUG) {
                Log.v("LoaderManager", "  Re-using existing loader " + bmVarC);
            }
            bmVarC.cz = bkVar;
        }
        if (bmVarC.cB && this.mStarted) {
            bmVarC.c(bmVarC.cA, bmVarC.cD);
        }
        return (android.support.v4.content.f<D>) bmVarC.cA;
    }

    void Q() {
        if (DEBUG) {
            Log.v("LoaderManager", "Starting in " + this);
        }
        if (this.mStarted) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w("LoaderManager", "Called doStart when already started: " + this, runtimeException);
        } else {
            this.mStarted = true;
            for (int size = this.cv.size() - 1; size >= 0; size--) {
                this.cv.valueAt(size).start();
            }
        }
    }

    void R() {
        if (DEBUG) {
            Log.v("LoaderManager", "Stopping in " + this);
        }
        if (!this.mStarted) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w("LoaderManager", "Called doStop when not started: " + this, runtimeException);
        } else {
            for (int size = this.cv.size() - 1; size >= 0; size--) {
                this.cv.valueAt(size).stop();
            }
            this.mStarted = false;
        }
    }

    void S() {
        if (DEBUG) {
            Log.v("LoaderManager", "Retaining in " + this);
        }
        if (!this.mStarted) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w("LoaderManager", "Called doRetain when not started: " + this, runtimeException);
        } else {
            this.mRetaining = true;
            this.mStarted = false;
            for (int size = this.cv.size() - 1; size >= 0; size--) {
                this.cv.valueAt(size).X();
            }
        }
    }

    void T() {
        if (this.mRetaining) {
            if (DEBUG) {
                Log.v("LoaderManager", "Finished Retaining in " + this);
            }
            this.mRetaining = false;
            for (int size = this.cv.size() - 1; size >= 0; size--) {
                this.cv.valueAt(size).T();
            }
        }
    }

    void U() {
        for (int size = this.cv.size() - 1; size >= 0; size--) {
            this.cv.valueAt(size).cF = true;
        }
    }

    void V() {
        for (int size = this.cv.size() - 1; size >= 0; size--) {
            this.cv.valueAt(size).Y();
        }
    }

    void W() {
        if (!this.mRetaining) {
            if (DEBUG) {
                Log.v("LoaderManager", "Destroying Active in " + this);
            }
            for (int size = this.cv.size() - 1; size >= 0; size--) {
                this.cv.valueAt(size).destroy();
            }
            this.cv.clear();
        }
        if (DEBUG) {
            Log.v("LoaderManager", "Destroying Inactive in " + this);
        }
        for (int size2 = this.cw.size() - 1; size2 >= 0; size2--) {
            this.cw.valueAt(size2).destroy();
        }
        this.cw.clear();
        this.mHost = null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        android.support.v4.e.f.a(this.mHost, sb);
        sb.append("}}");
        return sb.toString();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.cv.size() > 0) {
            printWriter.print(str);
            printWriter.println("Active Loaders:");
            String str2 = String.valueOf(str) + "    ";
            for (int i = 0; i < this.cv.size(); i++) {
                bm bmVarValueAt = this.cv.valueAt(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.cv.keyAt(i));
                printWriter.print(": ");
                printWriter.println(bmVarValueAt.toString());
                bmVarValueAt.dump(str2, fileDescriptor, printWriter, strArr);
            }
        }
        if (this.cw.size() > 0) {
            printWriter.print(str);
            printWriter.println("Inactive Loaders:");
            String str3 = String.valueOf(str) + "    ";
            for (int i2 = 0; i2 < this.cw.size(); i2++) {
                bm bmVarValueAt2 = this.cw.valueAt(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.cw.keyAt(i2));
                printWriter.print(": ");
                printWriter.println(bmVarValueAt2.toString());
                bmVarValueAt2.dump(str3, fileDescriptor, printWriter, strArr);
            }
        }
    }

    @Override // android.support.v4.app.bj
    public boolean P() {
        int size = this.cv.size();
        boolean z = false;
        for (int i = 0; i < size; i++) {
            bm bmVarValueAt = this.cv.valueAt(i);
            z |= bmVarValueAt.mStarted && !bmVarValueAt.cC;
        }
        return z;
    }
}
