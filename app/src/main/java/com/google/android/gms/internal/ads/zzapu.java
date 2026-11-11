package com.google.android.gms.internal.ads;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.concurrent.CountDownLatch;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzapu extends Thread implements SurfaceTexture.OnFrameAvailableListener, zzapt {
    private static final float[] zzcyv = {-1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f};
    private final float[] zzcys;
    private final zzapr zzcyw;
    private final float[] zzcyx;
    private final float[] zzcyy;
    private final float[] zzcyz;
    private final float[] zzcza;
    private final float[] zzczb;
    private final float[] zzczc;
    private float zzczd;
    private float zzcze;
    private float zzczf;
    private SurfaceTexture zzczg;
    private SurfaceTexture zzczh;
    private int zzczi;
    private int zzczj;
    private int zzczk;
    private FloatBuffer zzczl;
    private final CountDownLatch zzczm;
    private final Object zzczn;
    private EGL10 zzczo;
    private EGLDisplay zzczp;
    private EGLContext zzczq;
    private EGLSurface zzczr;
    private volatile boolean zzczs;
    private volatile boolean zzczt;
    private int zzuq;
    private int zzur;

    public zzapu(Context context) {
        super("SphericalVideoProcessor");
        this.zzczl = ByteBuffer.allocateDirect(zzcyv.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.zzczl.put(zzcyv).position(0);
        this.zzcys = new float[9];
        this.zzcyx = new float[9];
        this.zzcyy = new float[9];
        this.zzcyz = new float[9];
        this.zzcza = new float[9];
        this.zzczb = new float[9];
        this.zzczc = new float[9];
        this.zzczd = Float.NaN;
        this.zzcyw = new zzapr(context);
        this.zzcyw.zza(this);
        this.zzczm = new CountDownLatch(1);
        this.zzczn = new Object();
    }

    private static void zza(float[] fArr, float f) {
        fArr[0] = 1.0f;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        fArr[3] = 0.0f;
        fArr[4] = (float) Math.cos(f);
        fArr[5] = (float) (-Math.sin(f));
        fArr[6] = 0.0f;
        fArr[7] = (float) Math.sin(f);
        fArr[8] = (float) Math.cos(f);
    }

    private static void zza(float[] fArr, float[] fArr2, float[] fArr3) {
        fArr[0] = (fArr2[0] * fArr3[0]) + (fArr2[1] * fArr3[3]) + (fArr2[2] * fArr3[6]);
        fArr[1] = (fArr2[0] * fArr3[1]) + (fArr2[1] * fArr3[4]) + (fArr2[2] * fArr3[7]);
        fArr[2] = (fArr2[0] * fArr3[2]) + (fArr2[1] * fArr3[5]) + (fArr2[2] * fArr3[8]);
        fArr[3] = (fArr2[3] * fArr3[0]) + (fArr2[4] * fArr3[3]) + (fArr2[5] * fArr3[6]);
        fArr[4] = (fArr2[3] * fArr3[1]) + (fArr2[4] * fArr3[4]) + (fArr2[5] * fArr3[7]);
        fArr[5] = (fArr2[3] * fArr3[2]) + (fArr2[4] * fArr3[5]) + (fArr2[5] * fArr3[8]);
        fArr[6] = (fArr2[6] * fArr3[0]) + (fArr2[7] * fArr3[3]) + (fArr2[8] * fArr3[6]);
        fArr[7] = (fArr2[6] * fArr3[1]) + (fArr2[7] * fArr3[4]) + (fArr2[8] * fArr3[7]);
        fArr[8] = (fArr2[6] * fArr3[2]) + (fArr2[7] * fArr3[5]) + (fArr2[8] * fArr3[8]);
    }

    private static void zzb(float[] fArr, float f) {
        fArr[0] = (float) Math.cos(f);
        fArr[1] = (float) (-Math.sin(f));
        fArr[2] = 0.0f;
        fArr[3] = (float) Math.sin(f);
        fArr[4] = (float) Math.cos(f);
        fArr[5] = 0.0f;
        fArr[6] = 0.0f;
        fArr[7] = 0.0f;
        fArr[8] = 1.0f;
    }

    private static int zzd(int i, String str) {
        int iGlCreateShader = GLES20.glCreateShader(i);
        zzdo("createShader");
        if (iGlCreateShader != 0) {
            GLES20.glShaderSource(iGlCreateShader, str);
            zzdo("shaderSource");
            GLES20.glCompileShader(iGlCreateShader);
            zzdo("compileShader");
            int[] iArr = new int[1];
            GLES20.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
            zzdo("getShaderiv");
            if (iArr[0] == 0) {
                Log.e("SphericalVideoRenderer", new StringBuilder(37).append("Could not compile shader ").append(i).append(":").toString());
                Log.e("SphericalVideoRenderer", GLES20.glGetShaderInfoLog(iGlCreateShader));
                GLES20.glDeleteShader(iGlCreateShader);
                zzdo("deleteShader");
                return 0;
            }
        }
        return iGlCreateShader;
    }

    private static void zzdo(String str) {
        int iGlGetError = GLES20.glGetError();
        if (iGlGetError != 0) {
            Log.e("SphericalVideoRenderer", new StringBuilder(String.valueOf(str).length() + 21).append(str).append(": glError ").append(iGlGetError).toString());
        }
    }

    private final boolean zztk() {
        boolean zEglDestroyContext = false;
        if (this.zzczr != null && this.zzczr != EGL10.EGL_NO_SURFACE) {
            zEglDestroyContext = this.zzczo.eglMakeCurrent(this.zzczp, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT) | false | this.zzczo.eglDestroySurface(this.zzczp, this.zzczr);
            this.zzczr = null;
        }
        if (this.zzczq != null) {
            zEglDestroyContext |= this.zzczo.eglDestroyContext(this.zzczp, this.zzczq);
            this.zzczq = null;
        }
        if (this.zzczp == null) {
            return zEglDestroyContext;
        }
        boolean zEglTerminate = zEglDestroyContext | this.zzczo.eglTerminate(this.zzczp);
        this.zzczp = null;
        return zEglTerminate;
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.zzczk++;
        synchronized (this.zzczn) {
            this.zzczn.notifyAll();
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        boolean z;
        int iGlCreateProgram;
        if (this.zzczh == null) {
            zzakb.e("SphericalVideoProcessor started with no output texture.");
            this.zzczm.countDown();
            return;
        }
        this.zzczo = (EGL10) EGLContext.getEGL();
        this.zzczp = this.zzczo.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (this.zzczp != EGL10.EGL_NO_DISPLAY && this.zzczo.eglInitialize(this.zzczp, new int[2])) {
            int[] iArr = new int[1];
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            EGLConfig eGLConfig = (!this.zzczo.eglChooseConfig(this.zzczp, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12325, 16, 12344}, eGLConfigArr, 1, iArr) || iArr[0] <= 0) ? null : eGLConfigArr[0];
            if (eGLConfig == null) {
                z = false;
            } else {
                this.zzczq = this.zzczo.eglCreateContext(this.zzczp, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
                if (this.zzczq == null || this.zzczq == EGL10.EGL_NO_CONTEXT) {
                    z = false;
                } else {
                    this.zzczr = this.zzczo.eglCreateWindowSurface(this.zzczp, eGLConfig, this.zzczh, null);
                    z = (this.zzczr == null || this.zzczr == EGL10.EGL_NO_SURFACE || !this.zzczo.eglMakeCurrent(this.zzczp, this.zzczr, this.zzczr, this.zzczq)) ? false : true;
                }
            }
        } else {
            z = false;
        }
        zzna<String> zznaVar = zznk.zzazp;
        int iZzd = zzd(35633, !((String) zzkb.zzik().zzd(zznaVar)).equals(zznaVar.zzja()) ? (String) zzkb.zzik().zzd(zznaVar) : "attribute highp vec3 aPosition;varying vec3 pos;void main() {  gl_Position = vec4(aPosition, 1.0);  pos = aPosition;}");
        if (iZzd == 0) {
            iGlCreateProgram = 0;
        } else {
            zzna<String> zznaVar2 = zznk.zzazq;
            int iZzd2 = zzd(35632, !((String) zzkb.zzik().zzd(zznaVar2)).equals(zznaVar2.zzja()) ? (String) zzkb.zzik().zzd(zznaVar2) : "#extension GL_OES_EGL_image_external : require\n#define INV_PI 0.3183\nprecision highp float;varying vec3 pos;uniform samplerExternalOES uSplr;uniform mat3 uVMat;uniform float uFOVx;uniform float uFOVy;void main() {  vec3 ray = vec3(pos.x * tan(uFOVx), pos.y * tan(uFOVy), -1);  ray = (uVMat * ray).xyz;  ray = normalize(ray);  vec2 texCrd = vec2(    0.5 + atan(ray.x, - ray.z) * INV_PI * 0.5, acos(ray.y) * INV_PI);  gl_FragColor = vec4(texture2D(uSplr, texCrd).xyz, 1.0);}");
            if (iZzd2 == 0) {
                iGlCreateProgram = 0;
            } else {
                iGlCreateProgram = GLES20.glCreateProgram();
                zzdo("createProgram");
                if (iGlCreateProgram != 0) {
                    GLES20.glAttachShader(iGlCreateProgram, iZzd);
                    zzdo("attachShader");
                    GLES20.glAttachShader(iGlCreateProgram, iZzd2);
                    zzdo("attachShader");
                    GLES20.glLinkProgram(iGlCreateProgram);
                    zzdo("linkProgram");
                    int[] iArr2 = new int[1];
                    GLES20.glGetProgramiv(iGlCreateProgram, 35714, iArr2, 0);
                    zzdo("getProgramiv");
                    if (iArr2[0] != 1) {
                        Log.e("SphericalVideoRenderer", "Could not link program: ");
                        Log.e("SphericalVideoRenderer", GLES20.glGetProgramInfoLog(iGlCreateProgram));
                        GLES20.glDeleteProgram(iGlCreateProgram);
                        zzdo("deleteProgram");
                        iGlCreateProgram = 0;
                    } else {
                        GLES20.glValidateProgram(iGlCreateProgram);
                        zzdo("validateProgram");
                    }
                }
            }
        }
        this.zzczi = iGlCreateProgram;
        GLES20.glUseProgram(this.zzczi);
        zzdo("useProgram");
        int iGlGetAttribLocation = GLES20.glGetAttribLocation(this.zzczi, "aPosition");
        GLES20.glVertexAttribPointer(iGlGetAttribLocation, 3, 5126, false, 12, (Buffer) this.zzczl);
        zzdo("vertexAttribPointer");
        GLES20.glEnableVertexAttribArray(iGlGetAttribLocation);
        zzdo("enableVertexAttribArray");
        int[] iArr3 = new int[1];
        GLES20.glGenTextures(1, iArr3, 0);
        zzdo("genTextures");
        int i = iArr3[0];
        GLES20.glBindTexture(36197, i);
        zzdo("bindTextures");
        GLES20.glTexParameteri(36197, 10240, 9729);
        zzdo("texParameteri");
        GLES20.glTexParameteri(36197, 10241, 9729);
        zzdo("texParameteri");
        GLES20.glTexParameteri(36197, 10242, 33071);
        zzdo("texParameteri");
        GLES20.glTexParameteri(36197, 10243, 33071);
        zzdo("texParameteri");
        this.zzczj = GLES20.glGetUniformLocation(this.zzczi, "uVMat");
        GLES20.glUniformMatrix3fv(this.zzczj, 1, false, new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f}, 0);
        boolean z2 = this.zzczi != 0;
        if (!z || !z2) {
            String strValueOf = String.valueOf(GLUtils.getEGLErrorString(this.zzczo.eglGetError()));
            String strConcat = strValueOf.length() != 0 ? "EGL initialization failed: ".concat(strValueOf) : new String("EGL initialization failed: ");
            zzakb.e(strConcat);
            com.google.android.gms.ads.internal.zzbv.zzeo().zza(new Throwable(strConcat), "SphericalVideoProcessor.run.1");
            zztk();
            this.zzczm.countDown();
            return;
        }
        this.zzczg = new SurfaceTexture(i);
        this.zzczg.setOnFrameAvailableListener(this);
        this.zzczm.countDown();
        this.zzcyw.start();
        try {
            try {
                this.zzczs = true;
                while (!this.zzczt) {
                    while (this.zzczk > 0) {
                        this.zzczg.updateTexImage();
                        this.zzczk--;
                    }
                    if (this.zzcyw.zza(this.zzcys)) {
                        if (Float.isNaN(this.zzczd)) {
                            float[] fArr = this.zzcys;
                            float[] fArr2 = {0.0f, 1.0f, 0.0f};
                            float[] fArr3 = {(fArr[0] * fArr2[0]) + (fArr[1] * fArr2[1]) + (fArr[2] * fArr2[2]), (fArr[3] * fArr2[0]) + (fArr[4] * fArr2[1]) + (fArr[5] * fArr2[2]), (fArr[8] * fArr2[2]) + (fArr[6] * fArr2[0]) + (fArr[7] * fArr2[1])};
                            this.zzczd = -(((float) Math.atan2(fArr3[1], fArr3[0])) - 1.5707964f);
                        }
                        zzb(this.zzczb, this.zzczd + this.zzcze);
                    } else {
                        zza(this.zzcys, -1.5707964f);
                        zzb(this.zzczb, this.zzcze);
                    }
                    zza(this.zzcyx, 1.5707964f);
                    zza(this.zzcyy, this.zzczb, this.zzcyx);
                    zza(this.zzcyz, this.zzcys, this.zzcyy);
                    zza(this.zzcza, this.zzczf);
                    zza(this.zzczc, this.zzcza, this.zzcyz);
                    GLES20.glUniformMatrix3fv(this.zzczj, 1, false, this.zzczc, 0);
                    GLES20.glDrawArrays(5, 0, 4);
                    zzdo("drawArrays");
                    GLES20.glFinish();
                    this.zzczo.eglSwapBuffers(this.zzczp, this.zzczr);
                    if (this.zzczs) {
                        GLES20.glViewport(0, 0, this.zzuq, this.zzur);
                        zzdo("viewport");
                        int iGlGetUniformLocation = GLES20.glGetUniformLocation(this.zzczi, "uFOVx");
                        int iGlGetUniformLocation2 = GLES20.glGetUniformLocation(this.zzczi, "uFOVy");
                        if (this.zzuq > this.zzur) {
                            GLES20.glUniform1f(iGlGetUniformLocation, 0.87266463f);
                            GLES20.glUniform1f(iGlGetUniformLocation2, (0.87266463f * this.zzur) / this.zzuq);
                        } else {
                            GLES20.glUniform1f(iGlGetUniformLocation, (0.87266463f * this.zzuq) / this.zzur);
                            GLES20.glUniform1f(iGlGetUniformLocation2, 0.87266463f);
                        }
                        this.zzczs = false;
                    }
                    try {
                        synchronized (this.zzczn) {
                            try {
                                if (!this.zzczt && !this.zzczs && this.zzczk == 0) {
                                    this.zzczn.wait();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    } catch (InterruptedException e) {
                    }
                }
                this.zzcyw.stop();
                this.zzczg.setOnFrameAvailableListener(null);
                this.zzczg = null;
                zztk();
            } catch (IllegalStateException e2) {
                zzakb.zzdk("SphericalVideoProcessor halted unexpectedly.");
                this.zzcyw.stop();
                this.zzczg.setOnFrameAvailableListener(null);
                this.zzczg = null;
                zztk();
            } catch (Throwable th2) {
                zzakb.zzb("SphericalVideoProcessor died.", th2);
                com.google.android.gms.ads.internal.zzbv.zzeo().zza(th2, "SphericalVideoProcessor.run.2");
                this.zzcyw.stop();
                this.zzczg.setOnFrameAvailableListener(null);
                this.zzczg = null;
                zztk();
            }
        } catch (Throwable th3) {
            this.zzcyw.stop();
            this.zzczg.setOnFrameAvailableListener(null);
            this.zzczg = null;
            zztk();
            throw th3;
        }
    }

    public final void zza(SurfaceTexture surfaceTexture, int i, int i2) {
        this.zzuq = i;
        this.zzur = i2;
        this.zzczh = surfaceTexture;
    }

    public final void zzb(float f, float f2) {
        float f3;
        float f4;
        if (this.zzuq > this.zzur) {
            f3 = (1.7453293f * f) / this.zzuq;
            f4 = (1.7453293f * f2) / this.zzuq;
        } else {
            f3 = (1.7453293f * f) / this.zzur;
            f4 = (1.7453293f * f2) / this.zzur;
        }
        this.zzcze -= f3;
        this.zzczf -= f4;
        if (this.zzczf < -1.5707964f) {
            this.zzczf = -1.5707964f;
        }
        if (this.zzczf > 1.5707964f) {
            this.zzczf = 1.5707964f;
        }
    }

    public final void zzh(int i, int i2) {
        synchronized (this.zzczn) {
            this.zzuq = i;
            this.zzur = i2;
            this.zzczs = true;
            this.zzczn.notifyAll();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzapt
    public final void zznn() {
        synchronized (this.zzczn) {
            this.zzczn.notifyAll();
        }
    }

    public final void zzti() {
        synchronized (this.zzczn) {
            this.zzczt = true;
            this.zzczh = null;
            this.zzczn.notifyAll();
        }
    }

    public final SurfaceTexture zztj() {
        if (this.zzczh == null) {
            return null;
        }
        try {
            this.zzczm.await();
        } catch (InterruptedException e) {
        }
        return this.zzczg;
    }
}
