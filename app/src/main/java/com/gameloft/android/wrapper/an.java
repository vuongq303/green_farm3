package com.gameloft.android.wrapper;

import android.os.SystemClock;
import com.facebook.appevents.codeless.internal.Constants;

/* JADX INFO: compiled from: TimeHackDetection.java */
/* JADX INFO: loaded from: classes.dex */
public class an {
    private Thread BG;
    private int bhg;
    private long bhl;
    private int bhe = 247502;
    private long bhh = 0;
    private double bhi = 0.0d;
    private long bhj = 0;
    private long bhk = 0;
    private long bhm = 0;
    private volatile int bhf = 0;
    private boolean akK = false;
    private boolean bhn = false;

    public an(int i) {
        this.bhg = i;
    }

    public void Init() {
        this.bhh = 1000L;
        this.bhi = this.bhh * 0.1f;
        this.bhk = 3L;
        this.bhf = 0;
        this.bhl = System.currentTimeMillis();
        this.bhm = SystemClock.elapsedRealtime();
        this.akK = true;
        this.BG = new Thread(new ap(this));
        if (this.BG != null) {
            this.BG.start();
        }
    }

    public void OnPause() {
        this.bhf++;
        this.akK = false;
    }

    public void OnResume() {
        this.akK = true;
    }

    public void Close() {
        this.bhn = true;
        this.akK = false;
        try {
            this.BG.join();
        } catch (InterruptedException e) {
        }
    }

    public boolean OH() {
        return this.bhn;
    }

    public boolean OI() {
        return this.akK;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void OJ() {
        while (!OH()) {
            if (!OI()) {
                SystemClock.sleep(500L);
            } else if ((!OH() && OI() && b(aq.SYSTEM_CLOCK)) || (!OH() && OI() && b(aq.TIME))) {
                this.bhj++;
                String str = "Detected " + this.bhj + " time mismatches.";
                if (this.bhj >= this.bhk) {
                    OK();
                    int i = 15;
                    if (this.bhg > 0) {
                        i = this.bhg;
                    }
                    String str2 = "Sleeping detection thread for " + i + " minutes before re-activating.";
                    long j = i * 60 * Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS;
                    while (!OH() && OI() && j != 0) {
                        SystemClock.sleep(1000L);
                        j -= 1000;
                    }
                    if (j != 0) {
                    }
                    this.bhj = 0L;
                }
            } else {
                this.bhj = 0L;
            }
        }
    }

    private void OK() {
        Object[] objArr = {new String("time tampering")};
        if (com.gameloft.android2d.d.b.Qm() != null) {
            com.gameloft.android2d.d.b.Qm().b(this.bhe, objArr);
        }
    }

    private long a(aq aqVar) {
        switch (aqVar) {
            case TIME:
                return System.currentTimeMillis() - this.bhl;
            case SYSTEM_CLOCK:
                return SystemClock.elapsedRealtime() - this.bhm;
            default:
                return 0L;
        }
    }

    private boolean b(aq aqVar) {
        int i = this.bhf;
        long jA = a(aqVar);
        SystemClock.sleep(this.bhh);
        long jA2 = a(aqVar);
        if (i == this.bhf) {
            return a(jA, jA2, aqVar);
        }
        this.bhj = 0L;
        return false;
    }

    private boolean a(long j, long j2, aq aqVar) {
        long jAbs = Math.abs((j2 - j) - this.bhh);
        boolean z = ((double) jAbs) > this.bhi;
        if (z) {
            String str = "Time difference: " + jAbs + " for time type: " + aqVar;
            String str2 = "Time mismatch detected inside time type: " + aqVar + " Details below.";
            String str3 = "Previous measured time: " + j;
            String str4 = "Current measured time: " + j2;
        }
        return z;
    }
}
