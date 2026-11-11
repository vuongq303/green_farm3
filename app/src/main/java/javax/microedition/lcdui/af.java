package javax.microedition.lcdui;

import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

/* JADX INFO: compiled from: GLSurfaceView.java */
/* JADX INFO: loaded from: classes.dex */
class af extends Thread {
    final /* synthetic */ v cmK;
    private GL10 cmL;
    private boolean cmM;
    private aj cmO;
    private ae cmP;
    private boolean mExited;
    private boolean mHasSurface;
    private boolean mHaveEglContext;
    private boolean mHaveEglSurface;
    private boolean mPaused;
    private boolean mRenderComplete;
    private boolean mShouldExit;
    private boolean mWaitingForSurface;
    private ArrayList<Runnable> mEventQueue = new ArrayList<>();
    private boolean cmN = false;
    private int mWidth = 0;
    private int mHeight = 0;
    private boolean mRequestRender = true;
    private int mRenderMode = 1;

    public af(v vVar, aj ajVar) {
        this.cmK = vVar;
        this.cmO = ajVar;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        setName("GLThread " + getId());
        String str = "starting tid=" + getId();
        try {
            guardedRun();
        } catch (InterruptedException e) {
        } finally {
            v.cmE.b(this);
        }
    }

    private void akp() {
        if (this.mHaveEglSurface) {
            this.mHaveEglSurface = false;
            this.cmP.destroySurface();
            v.cmE.d(this);
        }
    }

    /* JADX WARN: Code duplicated, block: B:130:0x0177 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private void guardedRun() {
        Runnable runnableRemove;
        this.cmP = new ae(this.cmK);
        this.mHaveEglContext = false;
        this.mHaveEglSurface = false;
        GL10 gl10 = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        int i = 0;
        int i2 = 0;
        Runnable runnable = null;
        int i3 = 0;
        while (true) {
            try {
                synchronized (v.cmE) {
                    while (true) {
                        if (this.mShouldExit) {
                            this.cmL = null;
                            synchronized (v.cmE) {
                                akp();
                                this.cmP.finish();
                            }
                            return;
                        }
                        if (!this.mEventQueue.isEmpty()) {
                            runnableRemove = this.mEventQueue.remove(0);
                            break;
                        }
                        if (this.mHaveEglSurface && this.mPaused) {
                            String str = "releasing EGL surface because paused tid=" + getId();
                            akp();
                        }
                        if (!this.mHasSurface && !this.mWaitingForSurface) {
                            String str2 = "noticed surfaceView surface lost tid=" + getId();
                            if (this.mHaveEglSurface) {
                                akp();
                            }
                            this.mWaitingForSurface = true;
                            v.cmE.notifyAll();
                        }
                        if (this.mHasSurface && this.mWaitingForSurface) {
                            String str3 = "noticed surfaceView surface acquired tid=" + getId();
                            this.mWaitingForSurface = false;
                            v.cmE.notifyAll();
                        }
                        if (z4) {
                            z3 = false;
                            z4 = false;
                            this.mRenderComplete = true;
                            v.cmE.notifyAll();
                        }
                        if (!this.mPaused && this.mHasSurface && this.mWidth > 0 && this.mHeight > 0 && (this.mRequestRender || this.mRenderMode == 1)) {
                            if (this.mHaveEglContext && !this.mHaveEglSurface && !this.cmP.ako()) {
                                this.cmP.finish();
                                this.cmO.ajU();
                                this.mHaveEglContext = false;
                            }
                            if (!this.mHaveEglContext && v.cmE.c(this)) {
                                this.mHaveEglContext = true;
                                this.cmP.start();
                                v.cmE.notifyAll();
                            }
                            if (this.mHaveEglContext && !this.mHaveEglSurface) {
                                this.mHaveEglSurface = true;
                                z = true;
                                z2 = true;
                            }
                            if (this.mHaveEglSurface) {
                                if (this.cmK.mSizeChanged) {
                                    z2 = true;
                                    i = this.mWidth;
                                    i2 = this.mHeight;
                                    z3 = true;
                                    this.cmK.mSizeChanged = false;
                                } else {
                                    this.mRequestRender = false;
                                }
                                v.cmE.notifyAll();
                                runnableRemove = runnable;
                                break;
                            }
                        }
                        v.cmE.wait();
                        this.cmL = null;
                        synchronized (v.cmE) {
                            akp();
                            this.cmP.finish();
                            throw th;
                        }
                    }
                }
                if (runnableRemove != null) {
                    runnableRemove.run();
                    runnable = null;
                } else {
                    if (this.cmM && f.clT != 0) {
                        if (z && f.clT != 0) {
                            GL10 gl11 = (GL10) this.cmP.a(this.cmK.getHolder());
                            v.cmE.b(gl11);
                            this.cmL = gl11;
                            this.cmO.onSurfaceCreated(gl11, this.cmP.mEglConfig);
                            z = false;
                            i3 = 0;
                            gl10 = gl11;
                        }
                        if (z2 && f.clT != 0) {
                            String str4 = "onSurfaceChanged(" + i + ", " + i2 + ")";
                            this.cmO.onSurfaceChanged(gl10, i, i2);
                            z2 = false;
                        }
                        if (i3 > 1 || !this.cmN) {
                            if (f.clT == 1) {
                                f.clT++;
                                this.cmO.onDrawFrame(gl10);
                            } else if (f.clT != 0) {
                                this.cmO.onDrawFrame(gl10);
                            }
                        }
                        i3++;
                        if (f.clT != 0 && f.clT != 1) {
                            try {
                                if (!this.cmP.akn()) {
                                    String str5 = "egl surface lost tid=" + getId();
                                    akp();
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    if (z3) {
                        z4 = true;
                        runnable = runnableRemove;
                    } else {
                        runnable = runnableRemove;
                    }
                }
            } catch (Throwable th) {
                this.cmL = null;
                synchronized (v.cmE) {
                    akp();
                    this.cmP.finish();
                    throw th;
                }
            }
        }
    }

    public void surfaceCreated() {
        synchronized (v.cmE) {
            String str = "surfaceCreated tid=" + getId();
            this.mHasSurface = true;
            v.cmE.notifyAll();
        }
    }

    public void surfaceDestroyed() {
        synchronized (v.cmE) {
            String str = "surfaceDestroyed tid=" + getId();
            this.mHasSurface = false;
            v.cmE.notifyAll();
            while (!this.mWaitingForSurface && !this.mExited) {
                try {
                    v.cmE.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void onPause() {
        synchronized (v.cmE) {
            this.mPaused = true;
            v.cmE.notifyAll();
        }
    }

    public void onResume() {
        synchronized (v.cmE) {
            this.mPaused = false;
            this.mRequestRender = true;
            v.cmE.notifyAll();
        }
    }

    public void onWindowResize(int i, int i2) {
        synchronized (v.cmE) {
            this.mWidth = i;
            this.mHeight = i2;
            this.cmK.mSizeChanged = true;
            this.mRequestRender = true;
            this.mRenderComplete = false;
            v.cmE.notifyAll();
            while (!this.mExited && !this.mPaused && !this.mRenderComplete) {
                try {
                    v.cmE.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public void onWindowFocusChanged(boolean z) {
        synchronized (v.cmE) {
            this.cmM = z;
            v.cmE.notifyAll();
        }
        String str = "Focus " + (this.cmM ? "gained" : "lost");
    }

    public void requestExitAndWait() {
        synchronized (v.cmE) {
            this.mShouldExit = true;
            v.cmE.notifyAll();
            while (!this.mExited) {
                try {
                    v.cmE.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
