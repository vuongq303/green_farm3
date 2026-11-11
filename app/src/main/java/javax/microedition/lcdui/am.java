package javax.microedition.lcdui;

import android.view.KeyEvent;

/* JADX INFO: compiled from: IAndroidView.java */
/* JADX INFO: loaded from: classes.dex */
public interface am {
    void cl(boolean z);

    boolean onKeyEvent(KeyEvent keyEvent);

    void onPause();

    void onResume();
}
