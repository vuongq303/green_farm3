package com.google.android.gms.internal.measurement;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'zzbyc' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:153)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: loaded from: classes.dex */
public class zzaau {
    public static final zzaau zzbxu = new zzaau("DOUBLE", 0, zzaaz.DOUBLE, 1);
    public static final zzaau zzbxv = new zzaau("FLOAT", 1, zzaaz.FLOAT, 5);
    public static final zzaau zzbxw = new zzaau("INT64", 2, zzaaz.LONG, 0);
    public static final zzaau zzbxx = new zzaau("UINT64", 3, zzaaz.LONG, 0);
    public static final zzaau zzbxy = new zzaau("INT32", 4, zzaaz.INT, 0);
    public static final zzaau zzbxz = new zzaau("FIXED64", 5, zzaaz.LONG, 1);
    public static final zzaau zzbya = new zzaau("FIXED32", 6, zzaaz.INT, 5);
    public static final zzaau zzbyb = new zzaau("BOOL", 7, zzaaz.BOOLEAN, 0);
    public static final zzaau zzbyc;
    public static final zzaau zzbyd;
    public static final zzaau zzbye;
    public static final zzaau zzbyf;
    public static final zzaau zzbyg;
    public static final zzaau zzbyh;
    public static final zzaau zzbyi;
    public static final zzaau zzbyj;
    public static final zzaau zzbyk;
    public static final zzaau zzbyl;
    private static final /* synthetic */ zzaau[] zzbyo;
    private final zzaaz zzbym;
    private final int zzbyn;

    static {
        final int i = 3;
        final int i2 = 2;
        final String str = "STRING";
        final int i3 = 8;
        final zzaaz zzaazVar = zzaaz.STRING;
        zzbyc = new zzaau(str, i3, zzaazVar, i2) { // from class: com.google.android.gms.internal.measurement.zzaav
            {
                int i4 = 8;
                int i5 = 2;
                zzaat zzaatVar = null;
            }
        };
        final String str2 = "GROUP";
        final int i4 = 9;
        final zzaaz zzaazVar2 = zzaaz.MESSAGE;
        zzbyd = new zzaau(str2, i4, zzaazVar2, i) { // from class: com.google.android.gms.internal.measurement.zzaaw
            {
                int i5 = 9;
                int i6 = 3;
                zzaat zzaatVar = null;
            }
        };
        final String str3 = "MESSAGE";
        final int i5 = 10;
        final zzaaz zzaazVar3 = zzaaz.MESSAGE;
        zzbye = new zzaau(str3, i5, zzaazVar3, i2) { // from class: com.google.android.gms.internal.measurement.zzaax
            {
                int i6 = 10;
                int i7 = 2;
                zzaat zzaatVar = null;
            }
        };
        final String str4 = "BYTES";
        final int i6 = 11;
        final zzaaz zzaazVar4 = zzaaz.BYTE_STRING;
        zzbyf = new zzaau(str4, i6, zzaazVar4, i2) { // from class: com.google.android.gms.internal.measurement.zzaay
            {
                int i7 = 11;
                int i8 = 2;
                zzaat zzaatVar = null;
            }
        };
        zzbyg = new zzaau("UINT32", 12, zzaaz.INT, 0);
        zzbyh = new zzaau("ENUM", 13, zzaaz.ENUM, 0);
        zzbyi = new zzaau("SFIXED32", 14, zzaaz.INT, 5);
        zzbyj = new zzaau("SFIXED64", 15, zzaaz.LONG, 1);
        zzbyk = new zzaau("SINT32", 16, zzaaz.INT, 0);
        zzbyl = new zzaau("SINT64", 17, zzaaz.LONG, 0);
        zzbyo = new zzaau[]{zzbxu, zzbxv, zzbxw, zzbxx, zzbxy, zzbxz, zzbya, zzbyb, zzbyc, zzbyd, zzbye, zzbyf, zzbyg, zzbyh, zzbyi, zzbyj, zzbyk, zzbyl};
    }

    private zzaau(String str, int i, zzaaz zzaazVar, int i2) {
        super(str, i);
        this.zzbym = zzaazVar;
        this.zzbyn = i2;
    }

    /* synthetic */ zzaau(String str, int i, zzaaz zzaazVar, int i2, zzaat zzaatVar) {
        this(str, i, zzaazVar, i2);
    }

    public static zzaau[] values() {
        return (zzaau[]) zzbyo.clone();
    }

    public final zzaaz zzvn() {
        return this.zzbym;
    }
}
