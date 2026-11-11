package javax.microedition.lcdui;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* JADX INFO: compiled from: AndroidView.java */
/* JADX INFO: loaded from: classes.dex */
public class b extends v implements am {
    private d clu;

    public b(Context context, d dVar) {
        super(context);
        this.clu = dVar;
        setEGLContextClientVersion(2);
        a(this.clu);
    }

    @Override // javax.microedition.lcdui.v, android.view.View
    public void onWindowFocusChanged(boolean z) {
        boolean zBE = com.gameloft.android.wrapper.y.bE(z);
        super.onWindowFocusChanged(zBE);
        this.clu.onWindowFocusChanged(zBE);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.clu.onTouchEvent(motionEvent);
    }

    @Override // javax.microedition.lcdui.am
    public boolean onKeyEvent(KeyEvent keyEvent) {
        return this.clu.onKeyEvent(keyEvent);
    }

    @Override // javax.microedition.lcdui.am
    public void cl(boolean z) {
        super.onWindowFocusChanged(z);
    }
}
