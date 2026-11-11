package android.support.v4.app;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* JADX INFO: compiled from: FragmentManager.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class aa {
    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public abstract Fragment h(String str);

    public abstract boolean isStateSaved();

    public abstract void popBackStack(int i, int i2);

    public abstract boolean popBackStackImmediate();

    public abstract au y();
}
