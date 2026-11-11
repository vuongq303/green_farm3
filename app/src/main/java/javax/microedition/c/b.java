package javax.microedition.c;

import android.hardware.SensorManager;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: SensorConnectionAcceleration.java */
/* JADX INFO: loaded from: classes.dex */
public class b implements a {
    private static SensorManager cpg;
    private static javax.microedition.a.a cph;
    private int state;

    private b() {
        if (au.getContext() != null) {
            cpg = (SensorManager) au.getContext().getSystemService("sensor");
            this.state = 1;
            return;
        }
        throw new RuntimeException("Utils.context is null, can't init motion sensor");
    }

    @Override // javax.microedition.a.a
    public void close() {
        this.state = 4;
    }

    public static javax.microedition.a.a akZ() {
        if (cph == null) {
            cph = new b();
        }
        return cph;
    }
}
