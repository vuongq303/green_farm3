package com.google.a.b;

import com.gameloft.glads.AndroidWebView;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: CharacterSetECI.java */
/* JADX INFO: loaded from: classes.dex */
public enum c {
    Cp437(new int[]{0, 2}, new String[0]),
    ISO8859_1(new int[]{1, 3}, "ISO-8859-1"),
    ISO8859_2(4, "ISO-8859-2"),
    ISO8859_3(5, "ISO-8859-3"),
    ISO8859_4(6, "ISO-8859-4"),
    ISO8859_5(7, "ISO-8859-5"),
    ISO8859_6(8, "ISO-8859-6"),
    ISO8859_7(9, "ISO-8859-7"),
    ISO8859_8(10, "ISO-8859-8"),
    ISO8859_9(11, "ISO-8859-9"),
    ISO8859_10(12, "ISO-8859-10"),
    ISO8859_11(13, "ISO-8859-11"),
    ISO8859_13(15, "ISO-8859-13"),
    ISO8859_14(16, "ISO-8859-14"),
    ISO8859_15(17, "ISO-8859-15"),
    ISO8859_16(18, "ISO-8859-16"),
    SJIS(20, "Shift_JIS"),
    Cp1250(21, "windows-1250"),
    Cp1251(22, "windows-1251"),
    Cp1252(23, "windows-1252"),
    Cp1256(24, "windows-1256"),
    UnicodeBigUnmarked(25, "UTF-16BE", "UnicodeBig"),
    UTF8(26, AndroidWebView.UTF_8),
    ASCII(new int[]{27, 170}, "US-ASCII"),
    Big5(28),
    GB18030(29, "GB2312", "EUC_CN", "GBK"),
    EUC_KR(30, "EUC-KR");

    private static final Map<Integer, c> caO = new HashMap();
    private static final Map<String, c> caP = new HashMap();
    private final int[] caQ;
    private final String[] caR;

    static {
        for (c cVar : values()) {
            for (int i : cVar.caQ) {
                caO.put(Integer.valueOf(i), cVar);
            }
            caP.put(cVar.name(), cVar);
            for (String str : cVar.caR) {
                caP.put(str, cVar);
            }
        }
    }

    c(int i) {
        this(new int[]{i}, new String[0]);
    }

    c(int i, String... strArr) {
        this.caQ = new int[]{i};
        this.caR = strArr;
    }

    c(int[] iArr, String... strArr) {
        this.caQ = iArr;
        this.caR = strArr;
    }

    public int getValue() {
        return this.caQ[0];
    }

    public static c iA(String str) {
        return caP.get(str);
    }
}
