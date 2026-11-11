package javax.microedition.lcdui;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;
import android.view.MotionEvent;

/* JADX INFO: compiled from: AndroidViewOfSystem.java */
/* JADX INFO: loaded from: classes.dex */
public class c extends GLSurfaceView implements am {
    private e clv;

    public c(Context context, e eVar) {
        super(context);
        this.clv = eVar;
        setEGLContextClientVersion(2);
        setRenderer(this.clv);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        boolean zBE = com.gameloft.android.wrapper.y.bE(z);
        super.onWindowFocusChanged(zBE);
        this.clv.onWindowFocusChanged(zBE);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.clv.onTouchEvent(motionEvent);
    }

    @Override // javax.microedition.lcdui.am
    public boolean onKeyEvent(KeyEvent keyEvent) {
        return this.clv.onKeyEvent(keyEvent);
    }

    @Override // javax.microedition.lcdui.am
    public void cl(boolean z) {
        super.onWindowFocusChanged(z);
    }
}
