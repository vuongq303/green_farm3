package com.unity3d.services.ar.view;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.WindowManager;
import com.google.ar.core.Session;

/* JADX INFO: loaded from: classes.dex */
public class DisplayRotationHelper implements DisplayManager.DisplayListener {
    private final Context context;
    private final Display display;
    private boolean viewportChanged;
    private int viewportHeight;
    private int viewportWidth;

    DisplayRotationHelper(Context context) {
        this.context = context;
        this.display = ((WindowManager) context.getSystemService(WindowManager.class)).getDefaultDisplay();
    }

    void onResume() {
        ((DisplayManager) this.context.getSystemService(DisplayManager.class)).registerDisplayListener(this, null);
    }

    void onPause() {
        ((DisplayManager) this.context.getSystemService(DisplayManager.class)).unregisterDisplayListener(this);
    }

    void onSurfaceChanged(int i, int i2) {
        this.viewportWidth = i;
        this.viewportHeight = i2;
        this.viewportChanged = true;
    }

    void updateSessionIfNeeded(Session session) {
        if (this.viewportChanged) {
            session.setDisplayGeometry(this.display.getRotation(), this.viewportWidth, this.viewportHeight);
            this.viewportChanged = false;
        }
    }

    public int getRotation() {
        return this.display.getRotation();
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
        this.viewportChanged = true;
    }
}
