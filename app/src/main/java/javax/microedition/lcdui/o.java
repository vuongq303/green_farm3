package javax.microedition.lcdui;

/* JADX INFO: compiled from: Command.java */
/* JADX INFO: loaded from: classes.dex */
public class o {
    private int cmj;
    public int cmk = 0;
    private String label;
    private int priority;

    public o(String str, int i, int i2) {
        this.label = str;
        this.cmj = i;
        this.priority = i2;
    }

    public String getLabel() {
        return this.label;
    }

    public int ake() {
        return this.cmj;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof o)) {
            o oVar = (o) obj;
            if (oVar.cmj == this.cmj && oVar.priority == oVar.priority && oVar.label != null && oVar.label == this.label) {
                return true;
            }
        }
        return false;
    }
}
