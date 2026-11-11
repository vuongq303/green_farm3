package android.support.v4.content;

import android.content.Context;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: compiled from: Loader.java */
/* JADX INFO: loaded from: classes.dex */
public class f<D> {
    h<D> eD;
    g<D> eE;
    Context mContext;
    int mId;
    boolean mStarted = false;
    boolean eF = false;
    boolean eG = true;
    boolean eH = false;
    boolean eI = false;

    public f(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void deliverResult(D d) {
        if (this.eD != null) {
            this.eD.b(this, d);
        }
    }

    public void deliverCancellation() {
        if (this.eE != null) {
            this.eE.b(this);
        }
    }

    public void a(int i, h<D> hVar) {
        if (this.eD != null) {
            throw new IllegalStateException("There is already a listener registered");
        }
        this.eD = hVar;
        this.mId = i;
    }

    public void a(h<D> hVar) {
        if (this.eD == null) {
            throw new IllegalStateException("No listener register");
        }
        if (this.eD != hVar) {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        }
        this.eD = null;
    }

    public void a(g<D> gVar) {
        if (this.eE != null) {
            throw new IllegalStateException("There is already a listener registered");
        }
        this.eE = gVar;
    }

    public void b(g<D> gVar) {
        if (this.eE == null) {
            throw new IllegalStateException("No listener register");
        }
        if (this.eE != gVar) {
            throw new IllegalArgumentException("Attempting to unregister the wrong listener");
        }
        this.eE = null;
    }

    public boolean isAbandoned() {
        return this.eF;
    }

    public final void startLoading() {
        this.mStarted = true;
        this.eG = false;
        this.eF = false;
        onStartLoading();
    }

    protected void onStartLoading() {
    }

    public boolean cancelLoad() {
        return onCancelLoad();
    }

    protected boolean onCancelLoad() {
        return false;
    }

    public void forceLoad() {
        onForceLoad();
    }

    protected void onForceLoad() {
    }

    public void stopLoading() {
        this.mStarted = false;
        onStopLoading();
    }

    protected void onStopLoading() {
    }

    public void reset() {
        onReset();
        this.eG = true;
        this.mStarted = false;
        this.eF = false;
        this.eH = false;
        this.eI = false;
    }

    protected void onReset() {
    }

    public void commitContentChanged() {
        this.eI = false;
    }

    public void rollbackContentChanged() {
        if (this.eI) {
            onContentChanged();
        }
    }

    public void onContentChanged() {
        if (this.mStarted) {
            forceLoad();
        } else {
            this.eH = true;
        }
    }

    public String dataToString(D d) {
        StringBuilder sb = new StringBuilder(64);
        android.support.v4.e.f.a(d, sb);
        sb.append("}");
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        android.support.v4.e.f.a(this, sb);
        sb.append(" id=");
        sb.append(this.mId);
        sb.append("}");
        return sb.toString();
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print(str);
        printWriter.print("mId=");
        printWriter.print(this.mId);
        printWriter.print(" mListener=");
        printWriter.println(this.eD);
        if (this.mStarted || this.eH || this.eI) {
            printWriter.print(str);
            printWriter.print("mStarted=");
            printWriter.print(this.mStarted);
            printWriter.print(" mContentChanged=");
            printWriter.print(this.eH);
            printWriter.print(" mProcessingChange=");
            printWriter.println(this.eI);
        }
        if (this.eF || this.eG) {
            printWriter.print(str);
            printWriter.print("mAbandoned=");
            printWriter.print(this.eF);
            printWriter.print(" mReset=");
            printWriter.println(this.eG);
        }
    }
}
