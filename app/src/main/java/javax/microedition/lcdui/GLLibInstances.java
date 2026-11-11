package javax.microedition.lcdui;

import com.gameloft.android.ANMP.GloftGF2F.S800x480.GLLib;
import com.gameloft.android.wrapper.au;

/* JADX INFO: loaded from: classes.dex */
public class GLLibInstances {
    public static int Pointer_GetNumPointers() {
        if (au.bhM.indexOf("800x480") <= -1) {
            return 1;
        }
        return GLLib.Pointer_GetNumPointers();
    }

    public static void Pointer_SetCurrentPointer(int i) {
        if (au.bhM.indexOf("800x480") > -1) {
            GLLib.Pointer_SetCurrentPointer(i);
        }
    }
}
