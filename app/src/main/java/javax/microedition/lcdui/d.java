package javax.microedition.lcdui;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.gameloft.android.wrapper.au;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: compiled from: AndroidView_Renderer.java */
/* JADX INFO: loaded from: classes.dex */
public class d implements aj, an {
    public ArrayList clw = new ArrayList();
    private t clx;
    protected b cly;

    public d(Context context, t tVar) {
        this.clx = tVar;
        ajS();
    }

    public View ajS() {
        this.cly = new b(au.getContext(), this);
        return this.cly;
    }

    @Override // javax.microedition.lcdui.an
    public am ajT() {
        return this.cly;
    }

    @Override // javax.microedition.lcdui.an
    public int getHeight() {
        return this.cly.getHeight();
    }

    @Override // javax.microedition.lcdui.an
    public int getWidth() {
        return this.cly.getWidth();
    }

    @Override // javax.microedition.lcdui.an
    public void postInvalidate() {
        this.cly.postInvalidate();
    }

    public void onWindowFocusChanged(boolean z) {
        this.clx.onWindowFocusChanged(z);
    }

    @Override // javax.microedition.lcdui.aj
    public void onDrawFrame(GL10 gl10) {
        if (f.clT == 1) {
            f.clT++;
        } else if (f.clT != 0) {
            this.clx.onDrawFrame(gl10);
        }
    }

    @Override // javax.microedition.lcdui.aj
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        au.j(gl10.glGetString(7937), gl10.glGetString(7936), gl10.glGetString(7938), gl10.glGetString(7939));
        this.clx.onSurfaceCreated(gl10, eGLConfig);
    }

    @Override // javax.microedition.lcdui.aj
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        this.clx.onSurfaceChanged(gl10, i, i2);
    }

    @Override // javax.microedition.lcdui.aj
    public void ajU() {
        this.clx.ajU();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.clx.onTouchEvent(motionEvent);
    }

    public boolean onKeyEvent(KeyEvent keyEvent) {
        return this.clx.onKeyEvent(keyEvent);
    }
}
