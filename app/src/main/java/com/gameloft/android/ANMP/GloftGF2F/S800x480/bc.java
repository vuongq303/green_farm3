package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.ServerProtocol;

/* JADX INFO: compiled from: JSonObject.java */
/* JADX INFO: loaded from: classes.dex */
public class bc {
    private String[] aiP;
    private Object[] aiQ;
    private int aiR;

    public bc() {
        this.aiR = 0;
        this.aiP = new String[10];
        this.aiQ = new Object[10];
    }

    public int no() {
        return this.aiR;
    }

    public String[] np() {
        return this.aiP;
    }

    public Object[] nq() {
        return this.aiQ;
    }

    public bc(String str) {
        ca(str);
    }

    public void ca(String str) {
        if (str != null) {
            this.aiR = 0;
            this.aiP = new String[10];
            this.aiQ = new Object[10];
            if (str.startsWith("[")) {
                str = "{\"json\":" + str + "}";
            }
            f(str, 0, str.length());
        }
    }

    private void f(String str, int i, int i2) {
        while (true) {
            try {
                String strJ = j(str, i, i2);
                if (strJ != null) {
                    int iIndexOf = str.indexOf(strJ, i) + strJ.length() + 1;
                    i = g(str, iIndexOf, i2);
                    a(strJ, h(str, iIndexOf, i));
                } else {
                    return;
                }
            } catch (Exception e) {
                GLLib.d(e);
                return;
            }
        }
    }

    private int g(String str, int i, int i2) {
        int i3 = i;
        boolean z = false;
        int i4 = 0;
        char c = 0;
        while (i3 < i2) {
            char cCharAt = str.charAt(i3);
            if (cCharAt != ' ' && cCharAt != ':' && cCharAt != ',' && cCharAt != '}' && cCharAt != ']') {
                z = true;
            }
            switch (cCharAt) {
                case '\"':
                case '\'':
                    if (c == 0) {
                        c = cCharAt;
                    } else {
                        if (cCharAt == c) {
                            return i3 + 1;
                        }
                    }
                    break;
                case ',':
                    if (c == 0 && z) {
                        return i3 + 1;
                    }
                    break;
                case '[':
                case '{':
                    if (c == 0 || c == cCharAt) {
                        i4++;
                        c = cCharAt;
                    }
                    break;
                case '\\':
                    i3++;
                    break;
                case ']':
                    if (c == '[') {
                        i4--;
                    }
                    if (i4 == 0 && z && c != '\"') {
                        return i3 + 1;
                    }
                    break;
                case '}':
                    if (c == '{') {
                        i4--;
                    }
                    if (i4 == 0 && z && c != '\"') {
                        return i3 + 1;
                    }
                    break;
            }
            i3++;
        }
        return -1;
    }

    private Object h(String str, int i, int i2) {
        char cCharAt = 0;
        while (i < i2) {
            cCharAt = str.charAt(i);
            if (cCharAt != ' ' && cCharAt != ':' && cCharAt != ',') {
                break;
            }
            i++;
        }
        switch (cCharAt) {
            case '\"':
            case '\'':
                return l(str, i + 1, i2);
            case '[':
                return i(str, i + 1, i2);
            case '{':
                return new bc(str.substring(i, i2));
            default:
                return l(str, i, i2);
        }
    }

    private Object i(String str, int i, int i2) {
        int i3 = -1;
        int iG = i;
        do {
            iG = g(str, iG, i2);
            i3++;
        } while (iG != -1);
        if (i3 < 0) {
            return null;
        }
        Object[] objArr = new Object[i3];
        int iG2 = g(str, i, i2);
        int i4 = 0;
        while (iG2 >= 0) {
            objArr[i4] = h(str, i, iG2);
            i4++;
            i = iG2;
            iG2 = g(str, iG2, i2);
        }
        return objArr;
    }

    private String j(String str, int i, int i2) {
        return k(str, i, i2);
    }

    private String k(String str, int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        boolean z = false;
        boolean z2 = false;
        while (i < i2) {
            char cCharAt = str.charAt(i);
            if (z) {
                a(stringBuffer, cCharAt);
            } else {
                switch (cCharAt) {
                    case '\"':
                    case '\'':
                        z2 = !z2;
                        if (!z2) {
                            return stringBuffer.toString();
                        }
                        break;
                        break;
                    case '\\':
                        z = true;
                        break;
                    default:
                        if (z2) {
                            stringBuffer.append(cCharAt);
                        }
                        break;
                }
            }
            i++;
        }
        return null;
    }

    private String l(String str, int i, int i2) {
        return i2 <= i ? "" : str.substring(i, i2 - 1).trim();
    }

    private void a(StringBuffer stringBuffer, char c) {
        switch (c) {
            case 'b':
                stringBuffer.append('\b');
                break;
            case FacebookRequestErrorClassification.EC_INVALID_SESSION /* 102 */:
                stringBuffer.append('\f');
                break;
            case 'n':
                stringBuffer.append('\n');
                break;
            case 'r':
                stringBuffer.append('\r');
                break;
            case 't':
                stringBuffer.append('\t');
                break;
            default:
                stringBuffer.append(c);
                break;
        }
    }

    public void a(String str, Object obj) {
        int iCc = cc(str);
        if (iCc != -1) {
            this.aiQ[iCc] = obj;
            return;
        }
        this.aiP[this.aiR] = str;
        this.aiQ[this.aiR] = obj;
        this.aiR++;
        if (this.aiR == this.aiP.length) {
            eI(5);
        }
    }

    private void eI(int i) {
        String[] strArr = new String[this.aiR + i];
        Object[] objArr = new Object[this.aiR + i];
        System.arraycopy(this.aiP, 0, strArr, 0, this.aiR);
        System.arraycopy(this.aiQ, 0, objArr, 0, this.aiR);
        this.aiP = strArr;
        this.aiQ = objArr;
    }

    public void t(String str, int i) {
        a(str, String.valueOf(i));
    }

    public boolean cb(String str) {
        return cc(str) != -1;
    }

    private int cc(String str) {
        if (str == null) {
            return -1;
        }
        for (int i = 0; i < this.aiR; i++) {
            if (this.aiP[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

    public Object cd(String str) {
        int iCc = cc(str);
        if (iCc != -1) {
            return this.aiQ[iCc];
        }
        return null;
    }

    public int ce(String str) throws Exception {
        Object objCd = cd(str);
        if (objCd == null) {
            throw new Exception("Key not found: " + str);
        }
        String strSubstring = (String) objCd;
        if (strSubstring.indexOf(46) != -1) {
            strSubstring = strSubstring.substring(0, strSubstring.indexOf(46));
        }
        return Integer.parseInt(strSubstring);
    }

    public int[] cf(String str) throws Exception {
        Object objCd = cd(str);
        if (objCd == null) {
            throw new Exception("Key not found: " + str);
        }
        Object[] objArr = (Object[]) objCd;
        int[] iArr = new int[objArr.length];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < objArr.length) {
                iArr[i2] = Integer.parseInt((String) objArr[i2]);
                i = i2 + 1;
            } else {
                return iArr;
            }
        }
    }

    public long[] cg(String str) throws Exception {
        Object objCd = cd(str);
        if (objCd == null) {
            throw new Exception("Key not found: " + str);
        }
        Object[] objArr = (Object[]) objCd;
        long[] jArr = new long[objArr.length];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < objArr.length) {
                jArr[i2] = Long.parseLong((String) objArr[i2]);
                i = i2 + 1;
            } else {
                return jArr;
            }
        }
    }

    public String ch(String str) throws Exception {
        Object objCd = cd(str);
        if (objCd == null) {
            throw new Exception("Key not found: " + str);
        }
        return objCd.toString();
    }

    public String[] ci(String str) throws Exception {
        Object objCd = cd(str);
        if (objCd == null) {
            throw new Exception("Key not found: " + str);
        }
        Object[] objArr = (Object[]) objCd;
        String[] strArr = new String[objArr.length];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < objArr.length) {
                strArr[i2] = (String) objArr[i2];
                i = i2 + 1;
            } else {
                return strArr;
            }
        }
    }

    public bc[] cj(String str) throws Exception {
        Object objCd = cd(str);
        if (objCd == null) {
            throw new Exception("Key not found: " + str);
        }
        Object[] objArr = (Object[]) objCd;
        bc[] bcVarArr = new bc[objArr.length];
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < objArr.length) {
                bcVarArr[i2] = (bc) objArr[i2];
                i = i2 + 1;
            } else {
                return bcVarArr;
            }
        }
    }

    public boolean ck(String str) throws Exception {
        Object objCd = cd(str);
        if (objCd == null) {
            throw new Exception("Key not found: " + str);
        }
        return cl((String) objCd);
    }

    private boolean cl(String str) {
        return ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(str.toLowerCase());
    }

    private static boolean s(Object obj) {
        String string = obj.toString();
        if (string.length() == 0) {
            return false;
        }
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private void a(StringBuffer stringBuffer, Object obj) {
        boolean z = (!obj.toString().equals(obj) || s(obj) || obj.toString().startsWith("{") || obj.toString().startsWith("[")) ? false : true;
        if (z) {
            stringBuffer.append("\"");
        }
        stringBuffer.append(obj);
        if (z) {
            stringBuffer.append("\"");
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("{");
        for (int i = 0; i < this.aiR; i++) {
            a(stringBuffer, this.aiP[i]);
            stringBuffer.append(": ");
            Object obj = this.aiQ[i];
            if (obj.getClass().isArray()) {
                Object[] objArr = (Object[]) obj;
                stringBuffer.append("[");
                for (int i2 = 0; i2 < objArr.length; i2++) {
                    a(stringBuffer, objArr[i2]);
                    if (i2 < objArr.length - 1) {
                        stringBuffer.append(", ");
                    }
                }
                stringBuffer.append("]");
            } else {
                a(stringBuffer, obj);
            }
            if (i < this.aiR - 1) {
                stringBuffer.append(", ");
            }
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public boolean cm(String str) {
        String string = cd(str).toString();
        if (string.length() <= 0) {
            return false;
        }
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) < '0' || string.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public boolean cn(String str) {
        String string = cd(str).toString();
        if (string.length() <= 0) {
            return false;
        }
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < string.length(); i3++) {
            if (string.charAt(i3) >= '0' && string.charAt(i3) <= '9') {
                i2++;
            } else if (string.charAt(i3) == '.') {
                i++;
            }
        }
        return i <= 1 && i + i2 == string.length();
    }

    public boolean co(String str) {
        return true;
    }

    public boolean cp(String str) {
        try {
            cj(str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cq(String str) {
        try {
            cg(str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cr(String str) {
        try {
            ci(str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
