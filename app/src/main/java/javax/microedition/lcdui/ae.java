package javax.microedition.lcdui;

import android.opengl.GLDebugHelper;
import android.view.SurfaceHolder;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;

/* JADX INFO: compiled from: GLSurfaceView.java */
/* JADX INFO: loaded from: classes.dex */
class ae {
    final /* synthetic */ v cmK;
    EGL10 mEgl;
    EGLConfig mEglConfig;
    EGLContext mEglContext;
    EGLDisplay mEglDisplay;
    EGLSurface mEglSurface;

    public ae(v vVar) {
        this.cmK = vVar;
    }

    public void start() {
        this.mEgl = (EGL10) EGLContext.getEGL();
        this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (this.mEglDisplay == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("eglGetDisplay failed");
        }
        if (this.mEgl.eglInitialize(this.mEglDisplay, new int[2])) {
            this.mEglConfig = this.cmK.cmG.chooseConfig(this.mEgl, this.mEglDisplay);
            this.mEglContext = this.cmK.cmH.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
            if (this.mEglContext == null || this.mEglContext == EGL10.EGL_NO_CONTEXT) {
                throwEglException("createContext");
            }
            this.mEglSurface = null;
            return;
        }
        throw new RuntimeException("eglInitialize failed");
    }

    public GL a(SurfaceHolder surfaceHolder) {
        if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
            this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            this.cmK.cmI.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
        }
        this.mEglSurface = this.cmK.cmI.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, surfaceHolder);
        if (this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
            throwEglException("createWindowSurface");
        }
        if (!this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
            throwEglException("eglMakeCurrent");
        }
        GL gl = this.mEglContext.getGL();
        if (this.cmK.cmJ != null) {
            gl = this.cmK.cmJ.wrap(gl);
        }
        if ((this.cmK.mDebugFlags & 3) != 0) {
            int i = 0;
            ai aiVar = null;
            if ((this.cmK.mDebugFlags & 1) != 0) {
                i = 1;
            }
            if ((this.cmK.mDebugFlags & 2) != 0) {
                aiVar = new ai();
            }
            return GLDebugHelper.wrap(gl, i, aiVar);
        }
        return gl;
    }

    public boolean akn() {
        this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
        return this.mEgl.eglGetError() != 12302;
    }

    public void destroySurface() {
        if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
            this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            this.cmK.cmI.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
            this.mEglSurface = null;
        }
    }

    public void finish() {
        if (this.mEglContext != null) {
            this.cmK.cmH.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
            this.mEglContext = null;
        }
        if (this.mEglDisplay != null) {
            this.mEgl.eglTerminate(this.mEglDisplay);
            this.mEglDisplay = null;
        }
    }

    private void throwEglException(String str) {
        throw new RuntimeException(str + " failed: " + this.mEgl.eglGetError());
    }

    public boolean ako() {
        this.mEgl.eglGetCurrentContext();
        boolean z = this.mEgl.eglGetError() != 12302;
        if (!z) {
            return z;
        }
        this.mEgl.eglGetError();
        if (this.cmK.cmF != null && this.cmK.cmF.cmL != null) {
            this.cmK.cmF.cmL.glEnable(1);
        }
        return this.mEgl.eglGetError() == 12288;
    }
}
