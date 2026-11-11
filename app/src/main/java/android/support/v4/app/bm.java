package android.support.v4.app;

import android.os.Bundle;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

/* JADX INFO: compiled from: LoaderManager.java */
/* JADX INFO: loaded from: classes.dex */
final class bm implements android.support.v4.content.g<Object>, android.support.v4.content.h<Object> {
    boolean bg;
    android.support.v4.content.f<Object> cA;
    boolean cB;
    boolean cC;
    Object cD;
    boolean cE;
    boolean cF;
    boolean cG;
    bm cH;
    final /* synthetic */ bl cI;
    final Bundle cy;
    bk<Object> cz;
    final int mId;
    boolean mRetaining;
    boolean mStarted;

    public bm(bl blVar, int i, Bundle bundle, bk<Object> bkVar) {
        this.cI = blVar;
        this.mId = i;
        this.cy = bundle;
        this.cz = bkVar;
    }

    void start() {
        if (this.mRetaining && this.cE) {
            this.mStarted = true;
            return;
        }
        if (!this.mStarted) {
            this.mStarted = true;
            if (bl.DEBUG) {
                Log.v("LoaderManager", "  Starting: " + this);
            }
            if (this.cA == null && this.cz != null) {
                this.cA = this.cz.b(this.mId, this.cy);
            }
            if (this.cA != null) {
                if (this.cA.getClass().isMemberClass() && !Modifier.isStatic(this.cA.getClass().getModifiers())) {
                    throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + this.cA);
                }
                if (!this.cG) {
                    this.cA.a(this.mId, this);
                    this.cA.a((android.support.v4.content.g<Object>) this);
                    this.cG = true;
                }
                this.cA.startLoading();
            }
        }
    }

    void X() {
        if (bl.DEBUG) {
            Log.v("LoaderManager", "  Retaining: " + this);
        }
        this.mRetaining = true;
        this.cE = this.mStarted;
        this.mStarted = false;
        this.cz = null;
    }

    void T() {
        if (this.mRetaining) {
            if (bl.DEBUG) {
                Log.v("LoaderManager", "  Finished Retaining: " + this);
            }
            this.mRetaining = false;
            if (this.mStarted != this.cE && !this.mStarted) {
                stop();
            }
        }
        if (this.mStarted && this.cB && !this.cF) {
            c(this.cA, this.cD);
        }
    }

    void Y() {
        if (this.mStarted && this.cF) {
            this.cF = false;
            if (this.cB && !this.mRetaining) {
                c(this.cA, this.cD);
            }
        }
    }

    void stop() {
        if (bl.DEBUG) {
            Log.v("LoaderManager", "  Stopping: " + this);
        }
        this.mStarted = false;
        if (!this.mRetaining && this.cA != null && this.cG) {
            this.cG = false;
            this.cA.a((android.support.v4.content.h<Object>) this);
            this.cA.b(this);
            this.cA.stopLoading();
        }
    }

    void destroy() {
        String str;
        if (bl.DEBUG) {
            Log.v("LoaderManager", "  Destroying: " + this);
        }
        this.bg = true;
        boolean z = this.cC;
        this.cC = false;
        if (this.cz != null && this.cA != null && this.cB && z) {
            if (bl.DEBUG) {
                Log.v("LoaderManager", "  Resetting: " + this);
            }
            if (this.cI.mHost != null) {
                String str2 = this.cI.mHost.mFragmentManager.bh;
                this.cI.mHost.mFragmentManager.bh = "onLoaderReset";
                str = str2;
            } else {
                str = null;
            }
            try {
                this.cz.a(this.cA);
                if (this.cI.mHost != null) {
                    this.cI.mHost.mFragmentManager.bh = str;
                }
            } catch (Throwable th) {
                if (this.cI.mHost != null) {
                    this.cI.mHost.mFragmentManager.bh = str;
                }
                throw th;
            }
        }
        this.cz = null;
        this.cD = null;
        this.cB = false;
        if (this.cA != null) {
            if (this.cG) {
                this.cG = false;
                this.cA.a((android.support.v4.content.h<Object>) this);
                this.cA.b(this);
            }
            this.cA.reset();
        }
        if (this.cH != null) {
            this.cH.destroy();
        }
    }

    @Override // android.support.v4.content.g
    public void b(android.support.v4.content.f<Object> fVar) {
        if (bl.DEBUG) {
            Log.v("LoaderManager", "onLoadCanceled: " + this);
        }
        if (this.bg) {
            if (bl.DEBUG) {
                Log.v("LoaderManager", "  Ignoring load canceled -- destroyed");
            }
        } else {
            if (this.cI.cv.get(this.mId) != this) {
                if (bl.DEBUG) {
                    Log.v("LoaderManager", "  Ignoring load canceled -- not active");
                    return;
                }
                return;
            }
            bm bmVar = this.cH;
            if (bmVar != null) {
                if (bl.DEBUG) {
                    Log.v("LoaderManager", "  Switching to pending loader: " + bmVar);
                }
                this.cH = null;
                this.cI.cv.put(this.mId, null);
                destroy();
                this.cI.a(bmVar);
            }
        }
    }

    @Override // android.support.v4.content.h
    public void b(android.support.v4.content.f<Object> fVar, Object obj) {
        if (bl.DEBUG) {
            Log.v("LoaderManager", "onLoadComplete: " + this);
        }
        if (this.bg) {
            if (bl.DEBUG) {
                Log.v("LoaderManager", "  Ignoring load complete -- destroyed");
                return;
            }
            return;
        }
        if (this.cI.cv.get(this.mId) != this) {
            if (bl.DEBUG) {
                Log.v("LoaderManager", "  Ignoring load complete -- not active");
                return;
            }
            return;
        }
        bm bmVar = this.cH;
        if (bmVar != null) {
            if (bl.DEBUG) {
                Log.v("LoaderManager", "  Switching to pending loader: " + bmVar);
            }
            this.cH = null;
            this.cI.cv.put(this.mId, null);
            destroy();
            this.cI.a(bmVar);
            return;
        }
        if (this.cD != obj || !this.cB) {
            this.cD = obj;
            this.cB = true;
            if (this.mStarted) {
                c(fVar, obj);
            }
        }
        bm bmVar2 = this.cI.cw.get(this.mId);
        if (bmVar2 != null && bmVar2 != this) {
            bmVar2.cC = false;
            bmVar2.destroy();
            this.cI.cw.remove(this.mId);
        }
        if (this.cI.mHost != null && !this.cI.P()) {
            this.cI.mHost.mFragmentManager.z();
        }
    }

    void c(android.support.v4.content.f<Object> fVar, Object obj) {
        String str;
        if (this.cz != null) {
            if (this.cI.mHost == null) {
                str = null;
            } else {
                String str2 = this.cI.mHost.mFragmentManager.bh;
                this.cI.mHost.mFragmentManager.bh = "onLoadFinished";
                str = str2;
            }
            try {
                if (bl.DEBUG) {
                    Log.v("LoaderManager", "  onLoadFinished in " + fVar + ": " + fVar.dataToString(obj));
                }
                this.cz.a(fVar, obj);
                if (this.cI.mHost != null) {
                    this.cI.mHost.mFragmentManager.bh = str;
                }
                this.cC = true;
            } catch (Throwable th) {
                if (this.cI.mHost != null) {
                    this.cI.mHost.mFragmentManager.bh = str;
                }
                throw th;
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("LoaderInfo{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" #");
        sb.append(this.mId);
        sb.append(" : ");
        android.support.v4.e.f.a(this.cA, sb);
        sb.append("}}");
        return sb.toString();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mId=");
        printWriter.print(this.mId);
        printWriter.print(" mArgs=");
        printWriter.println(this.cy);
        printWriter.print(str);
        printWriter.print("mCallbacks=");
        printWriter.println(this.cz);
        printWriter.print(str);
        printWriter.print("mLoader=");
        printWriter.println(this.cA);
        if (this.cA != null) {
            this.cA.dump(String.valueOf(str) + "  ", fileDescriptor, printWriter, strArr);
        }
        if (this.cB || this.cC) {
            printWriter.print(str);
            printWriter.print("mHaveData=");
            printWriter.print(this.cB);
            printWriter.print("  mDeliveredData=");
            printWriter.println(this.cC);
            printWriter.print(str);
            printWriter.print("mData=");
            printWriter.println(this.cD);
        }
        printWriter.print(str);
        printWriter.print("mStarted=");
        printWriter.print(this.mStarted);
        printWriter.print(" mReportNextStart=");
        printWriter.print(this.cF);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.bg);
        printWriter.print(str);
        printWriter.print("mRetaining=");
        printWriter.print(this.mRetaining);
        printWriter.print(" mRetainingStarted=");
        printWriter.print(this.cE);
        printWriter.print(" mListenerRegistered=");
        printWriter.println(this.cG);
        if (this.cH != null) {
            printWriter.print(str);
            printWriter.println("Pending Loader ");
            printWriter.print(this.cH);
            printWriter.println(":");
            this.cH.dump(String.valueOf(str) + "  ", fileDescriptor, printWriter, strArr);
        }
    }
}
