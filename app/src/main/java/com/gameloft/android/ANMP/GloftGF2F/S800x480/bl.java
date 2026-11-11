package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.os.SystemClock;
import java.util.ArrayList;

/* JADX INFO: compiled from: ProfilerEvent.java */
/* JADX INFO: loaded from: classes.dex */
public class bl implements Comparable<ProfilerEvent> {
    private long ajD;
    private long ajE;
    private long ajG;
    private long ajH;
    private ArrayList<Integer> ajJ;
    private ArrayList<Long> ajK;
    private int count;
    private String name;
    private long ajF = -1;
    private int ajI = -1;

    public bl(String str) {
        this.name = str;
        this.ajD = w.Vl ? (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To : System.currentTimeMillis();
        this.count = 1;
        this.ajK = new ArrayList<>(w.Zq);
        this.ajJ = new ArrayList<>(w.Zq);
    }

    public void nz() {
        this.ajI++;
        if (this.ajI >= w.Zq) {
            this.ajJ.remove(0);
            this.ajI--;
        }
        this.ajJ.add(new Integer(0));
    }

    public void nA() {
        if (this.ajD != 0) {
            if (this.ajE == 0) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
                }
                this.ajE = jCurrentTimeMillis;
            }
            s(this.ajE - this.ajD);
            this.count++;
            if (this.ajI >= 0) {
                this.ajJ.set(this.ajI, new Integer(this.ajJ.get(this.ajI).intValue() + 1));
            }
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        if (w.Vl) {
            jCurrentTimeMillis2 = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
        }
        this.ajD = jCurrentTimeMillis2;
        this.ajE = 0L;
    }

    private void s(long j) {
        if (this.ajF == -1 || j > this.ajF) {
            this.ajF = j;
        }
        if (j < this.ajG) {
            this.ajG = j;
        }
        this.ajH += j;
        if (this.ajK.size() >= w.Zq) {
            this.ajK.remove(0);
        }
        this.ajK.add(new Long(j));
    }

    public long nB() {
        return this.ajH / ((long) this.count);
    }

    public int getCount() {
        return this.count;
    }

    public void nC() {
        if (this.ajD != 0) {
            if (this.ajE == 0) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (w.Vl) {
                    jCurrentTimeMillis = (SystemClock.elapsedRealtime() + GLLib.Tn) - GLLib.To;
                }
                this.ajE = jCurrentTimeMillis;
            }
            s(this.ajE - this.ajD);
            this.ajD = 0L;
            this.count++;
            if (this.ajI >= 0) {
                this.ajJ.set(this.ajI, new Integer(this.ajJ.get(this.ajI).intValue() + 1));
            }
        }
    }

    public boolean equals(Object obj) {
        return obj instanceof bl ? this.name.equals(((bl) obj).name) : super.equals(obj);
    }

    public String toString() {
        String str = this.name + "\nSum: " + this.ajH + " Count:" + this.count + "\nMax: " + this.ajF + " Avg: " + nB() + " Min: " + this.ajG + "\nReal: ";
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.ajK.size(); i++) {
            stringBuffer.append(this.ajK.get(i));
            if (i < this.ajK.size() - 1) {
                stringBuffer.append("; ");
            }
        }
        if (this.ajJ.size() > 0) {
            stringBuffer.append("\nNumber of events by groups: ");
            for (int i2 = 0; i2 < this.ajJ.size(); i2++) {
                stringBuffer.append(this.ajJ.get(i2));
                if (i2 < this.ajJ.size() - 1) {
                    stringBuffer.append("; ");
                }
            }
        }
        return str + stringBuffer.toString();
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(bl blVar) {
        if (blVar == null) {
            return 1;
        }
        if (this.ajF == blVar.ajF) {
            return 0;
        }
        return this.ajF > blVar.ajF ? -1 : 1;
    }
}
