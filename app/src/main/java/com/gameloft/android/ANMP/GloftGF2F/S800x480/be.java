package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.lang.reflect.Array;

/* JADX INFO: compiled from: Matrix4.java */
/* JADX INFO: loaded from: classes.dex */
public class be {
    public float[][] aiV;

    public be() {
        this.aiV = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 4, 4);
    }

    public be(float[][] fArr) {
        this.aiV = fArr;
    }

    public void nr() {
        for (int i = 0; i < this.aiV.length; i++) {
            this.aiV[i][i] = 1.0f;
        }
    }

    public void ns() {
        float[][] fArr = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 4, 4);
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                fArr[i2][i] = this.aiV[i][i2];
            }
        }
        this.aiV = fArr;
    }

    public be b(be beVar) {
        be beVar2 = new be();
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                float f = 0.0f;
                for (int i3 = 0; i3 < 4; i3++) {
                    f += this.aiV[i][i3] * beVar.aiV[i3][i2];
                }
                beVar2.aiV[i][i2] = f;
            }
        }
        return beVar2;
    }

    public cd a(cd cdVar, cd cdVar2) {
        for (int i = 0; i < 4; i++) {
            float f = 0.0f;
            for (int i2 = 0; i2 < 4; i2++) {
                f += cdVar.alf[i2] * this.aiV[i2][i];
            }
            cdVar2.alf[i] = f;
        }
        return cdVar2;
    }

    public be c(be beVar) {
        be beVar2 = new be();
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                beVar2.aiV[i][i2] = this.aiV[i][i2] + beVar.aiV[i][i2];
            }
        }
        return beVar2;
    }

    public static be g(float f) {
        return new be(new float[][]{new float[]{1.0f, 0.0f, 0.0f, 0.0f}, new float[]{0.0f, (float) Math.cos(f), (float) Math.sin(f), 0.0f}, new float[]{0.0f, (float) (-Math.sin(f)), (float) Math.cos(f), 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 1.0f}});
    }

    public static be h(float f) {
        return new be(new float[][]{new float[]{(float) Math.cos(f), 0.0f, (float) (-Math.sin(f)), 0.0f}, new float[]{0.0f, 1.0f, 0.0f, 0.0f}, new float[]{(float) Math.sin(f), 0.0f, (float) Math.cos(f), 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 1.0f}});
    }

    public static be i(float f) {
        return new be(new float[][]{new float[]{(float) Math.cos(f), (float) Math.sin(f), 0.0f, 0.0f}, new float[]{(float) (-Math.sin(f)), (float) Math.cos(f), 0.0f, 0.0f}, new float[]{0.0f, 0.0f, 1.0f, 0.0f}, new float[]{0.0f, 0.0f, 0.0f, 1.0f}});
    }
}
