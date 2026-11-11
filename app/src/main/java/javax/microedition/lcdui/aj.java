package javax.microedition.lcdui;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: compiled from: GLSurfaceView.java */
/* JADX INFO: loaded from: classes.dex */
public interface aj {
    void ajU();

    void onDrawFrame(GL10 gl10);

    void onSurfaceChanged(GL10 gl10, int i, int i2);

    void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig);
}
