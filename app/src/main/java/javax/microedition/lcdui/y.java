package javax.microedition.lcdui;

import com.facebook.appevents.codeless.internal.Constants;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

/* JADX INFO: compiled from: GLSurfaceView.java */
/* JADX INFO: loaded from: classes.dex */
class y extends x {
    final /* synthetic */ v cmK;
    protected int mAlphaSize;
    protected int mBlueSize;
    protected int mDepthSize;
    protected int mGreenSize;
    protected int mRedSize;
    protected int mStencilSize;
    private int[] mValue;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public y(v vVar, int i, int i2, int i3, int i4, int i5, int i6) {
        super(vVar, new int[]{12324, i, 12323, i2, 12322, i3, 12321, i4, 12325, i5, 12326, i6, 12344});
        this.cmK = vVar;
        this.mValue = new int[1];
        this.mRedSize = i;
        this.mGreenSize = i2;
        this.mBlueSize = i3;
        this.mAlphaSize = i4;
        this.mDepthSize = i5;
        this.mStencilSize = i6;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0081  */
    @Override // javax.microedition.lcdui.x
    public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
        int iAbs;
        EGLConfig eGLConfig = null;
        int i = Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
        int length = eGLConfigArr.length;
        int i2 = 0;
        while (i2 < length) {
            EGLConfig eGLConfig2 = eGLConfigArr[i2];
            int iFindConfigAttrib = findConfigAttrib(egl10, eGLDisplay, eGLConfig2, 12325, 0);
            int iFindConfigAttrib2 = findConfigAttrib(egl10, eGLDisplay, eGLConfig2, 12326, 0);
            if (iFindConfigAttrib < this.mDepthSize || iFindConfigAttrib2 < this.mStencilSize) {
                iAbs = i;
                eGLConfig2 = eGLConfig;
            } else {
                int iFindConfigAttrib3 = findConfigAttrib(egl10, eGLDisplay, eGLConfig2, 12324, 0);
                int iFindConfigAttrib4 = findConfigAttrib(egl10, eGLDisplay, eGLConfig2, 12323, 0);
                int iFindConfigAttrib5 = findConfigAttrib(egl10, eGLDisplay, eGLConfig2, 12322, 0);
                iAbs = Math.abs(findConfigAttrib(egl10, eGLDisplay, eGLConfig2, 12321, 0) - this.mAlphaSize) + Math.abs(iFindConfigAttrib3 - this.mRedSize) + Math.abs(iFindConfigAttrib4 - this.mGreenSize) + Math.abs(iFindConfigAttrib5 - this.mBlueSize);
                if (iAbs >= i) {
                    iAbs = i;
                    eGLConfig2 = eGLConfig;
                }
            }
            i2++;
            i = iAbs;
            eGLConfig = eGLConfig2;
        }
        return eGLConfig;
    }

    private int findConfigAttrib(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int i2) {
        if (egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, this.mValue)) {
            return this.mValue[0];
        }
        return i2;
    }
}
