package javax.microedition.lcdui;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/* JADX INFO: compiled from: GLSurfaceView.java */
/* JADX INFO: loaded from: classes.dex */
public class v extends SurfaceView implements SurfaceHolder.Callback {
    private static final ag cmE = new ag();
    private af cmF;
    private ab cmG;
    private ac cmH;
    private ad cmI;
    private ah cmJ;
    private int mDebugFlags;
    private int mEGLContextClientVersion;
    private boolean mSizeChanged;

    public v(Context context) {
        super(context);
        this.mSizeChanged = true;
        init();
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        holder.setType(2);
    }

    public void a(aj ajVar) {
        w wVar = null;
        checkRenderThreadState();
        if (this.cmG == null) {
            this.cmG = new ak(this, true);
        }
        if (this.cmH == null) {
            this.cmH = new z(this);
        }
        if (this.cmI == null) {
            this.cmI = new aa();
        }
        this.cmF = new af(this, ajVar);
        this.cmF.start();
    }

    public void setEGLContextClientVersion(int i) {
        checkRenderThreadState();
        this.mEGLContextClientVersion = i;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.cmF.surfaceCreated();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.cmF.surfaceDestroyed();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        int i4;
        int i5;
        if (com.gameloft.android.wrapper.d.beG.compareTo("landscape") == 0 && i2 < i3) {
            i4 = i2;
            i5 = i3;
        } else if (com.gameloft.android.wrapper.d.beG.compareTo("portrait") != 0 || i2 <= i3) {
            i4 = i3;
            i5 = i2;
        } else {
            i4 = i2;
            i5 = i3;
        }
        String str = "onSizeChange: " + i5 + ", " + i4 + " orientation:" + com.gameloft.android.wrapper.d.beG;
        this.cmF.onWindowResize(i5, i4);
    }

    public void onPause() {
        this.cmF.onPause();
    }

    public void onResume() {
        this.cmF.onResume();
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        this.cmF.onWindowFocusChanged(z);
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.cmF.requestExitAndWait();
    }

    private void checkRenderThreadState() {
        if (this.cmF != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }
}
