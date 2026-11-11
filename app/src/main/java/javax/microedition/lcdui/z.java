package javax.microedition.lcdui;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/* JADX INFO: compiled from: GLSurfaceView.java */
/* JADX INFO: loaded from: classes.dex */
class z implements ac {
    private int EGL_CONTEXT_CLIENT_VERSION;
    final /* synthetic */ v cmK;

    private z(v vVar) {
        this.cmK = vVar;
        this.EGL_CONTEXT_CLIENT_VERSION = 12440;
    }

    @Override // javax.microedition.lcdui.ac
    public EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
        int[] iArr = {this.EGL_CONTEXT_CLIENT_VERSION, this.cmK.mEGLContextClientVersion, 12344};
        EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
        if (this.cmK.mEGLContextClientVersion == 0) {
            iArr = null;
        }
        return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
    }

    @Override // javax.microedition.lcdui.ac
    public void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
        egl10.eglDestroyContext(eGLDisplay, eGLContext);
    }
}
