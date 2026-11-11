package javax.microedition.lcdui;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.gameloft.android.wrapper.au;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: compiled from: AndroidView_Renderer_Of_System.java */
/* JADX INFO: loaded from: classes.dex */
public class e implements GLSurfaceView.Renderer, an {
    public ArrayList clw = new ArrayList();
    private t clx;
    protected c clz;

    public e(Context context, t tVar) {
        this.clx = tVar;
        ajS();
    }

    public View ajS() {
        this.clz = new c(au.getContext(), this);
        return this.clz;
    }

    @Override // javax.microedition.lcdui.an
    public am ajT() {
        return this.clz;
    }

    @Override // javax.microedition.lcdui.an
    public int getHeight() {
        return this.clz.getHeight();
    }

    @Override // javax.microedition.lcdui.an
    public int getWidth() {
        return this.clz.getWidth();
    }

    @Override // javax.microedition.lcdui.an
    public void postInvalidate() {
        this.clz.postInvalidate();
    }

    public void onWindowFocusChanged(boolean z) {
        this.clx.onWindowFocusChanged(z);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl10) {
        if (f.clT == 1) {
            f.clT++;
        } else if (f.clT != 0) {
            this.clx.onDrawFrame(gl10);
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        au.j(gl10.glGetString(7937), gl10.glGetString(7936), gl10.glGetString(7938), gl10.glGetString(7939));
        this.clx.onSurfaceCreated(gl10, eGLConfig);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        this.clx.onSurfaceChanged(gl10, i, i2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.clx.onTouchEvent(motionEvent);
    }

    public boolean onKeyEvent(KeyEvent keyEvent) {
        return this.clx.onKeyEvent(keyEvent);
    }
}
