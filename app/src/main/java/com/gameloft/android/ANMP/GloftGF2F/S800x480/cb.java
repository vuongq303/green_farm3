package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.opengl.GLES20;

/* JADX INFO: compiled from: VBOBatch.java */
/* JADX INFO: loaded from: classes.dex */
public class cb {
    int[] akW;
    int akX;
    int akY;
    int akZ;
    int ala;
    int alb;
    int alc;
    int ald;
    ay ale;

    public int[] ot() {
        return this.akW;
    }

    public int ou() {
        return this.akX;
    }

    public int ov() {
        return this.akY;
    }

    public int ow() {
        return this.akZ;
    }

    public int ox() {
        return this.ala;
    }

    public int oy() {
        return this.alb;
    }

    public int oz() {
        return this.alc;
    }

    public int oA() {
        return this.ald;
    }

    public ay oB() {
        return this.ale;
    }

    public void b(ay ayVar) {
        this.ale = ayVar;
    }

    public void Destroy() {
        GLES20.glDeleteBuffers(2, this.akW, 0);
        this.akW = null;
    }

    public String toString() {
        return "buf: " + this.akW[0] + " " + this.akW[1] + "\nstride " + this.akX + "\nindexDataLen " + this.akY + "\nblendMode " + this.akZ + "\nrenderMode " + this.ala + "\ncoordMode " + this.alb + "\nfxColor " + this.ald + "\ntexture " + this.ale;
    }
}
