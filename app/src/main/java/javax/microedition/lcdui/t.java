package javax.microedition.lcdui;

import android.view.KeyEvent;
import android.view.MotionEvent;
import com.gameloft.android.wrapper.au;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: compiled from: Displayable.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class t {
    public ArrayList clw = new ArrayList();
    public p cmr;
    protected an cms;

    public t() {
        akh();
    }

    public void a(o oVar) {
        this.clw.add(oVar);
    }

    public void b(o oVar) {
        this.clw.remove(oVar);
    }

    public void a(p pVar) {
        this.cmr = pVar;
    }

    protected void cK(int i, int i2) {
    }

    public am akh() {
        if (com.gameloft.android.wrapper.d.beF) {
            String str = "Canvas use_system_GLSurfaceView: " + com.gameloft.android.wrapper.d.beF;
            this.cms = new e(au.getContext(), this);
        } else {
            this.cms = new d(au.getContext(), this);
        }
        return this.cms.ajT();
    }

    public am ajT() {
        return this.cms.ajT();
    }

    public int getHeight() {
        return this.cms.getHeight();
    }

    public int getWidth() {
        return this.cms.getWidth();
    }

    public void postInvalidate() {
        this.cms.postInvalidate();
    }

    public void cI(int i, int i2) {
    }

    public void onWindowFocusChanged(boolean z) {
    }

    public void onDrawFrame(GL10 gl10) {
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
    }

    public void ajU() {
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    public void cl(boolean z) {
    }

    public void T(int i, boolean z) {
    }
}
