package a;

import java.util.concurrent.Executor;

/* JADX INFO: compiled from: BoltsExecutors.java */
/* JADX INFO: loaded from: classes.dex */
class i implements Executor {
    private ThreadLocal<Integer> zx;

    private i() {
        this.zx = new ThreadLocal<>();
    }

    private int fu() {
        Integer num = this.zx.get();
        if (num == null) {
            num = 0;
        }
        int iIntValue = num.intValue() + 1;
        this.zx.set(Integer.valueOf(iIntValue));
        return iIntValue;
    }

    private int fv() {
        Integer num = this.zx.get();
        if (num == null) {
            num = 0;
        }
        int iIntValue = num.intValue() - 1;
        if (iIntValue == 0) {
            this.zx.remove();
        } else {
            this.zx.set(Integer.valueOf(iIntValue));
        }
        return iIntValue;
    }

    @Override // java.util.concurrent.Executor
    public void execute(Runnable runnable) {
        try {
            if (fu() <= 15) {
                runnable.run();
            } else {
                g.fs().execute(runnable);
            }
            fv();
        } catch (Throwable th) {
            fv();
            throw th;
        }
    }
}
