package com.google.android.gms.common.server.response;

import android.util.Log;
import com.facebook.R;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.server.response.FastJsonResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/* JADX INFO: loaded from: classes.dex */
public class b<T extends FastJsonResponse> {
    private final char[] bPh = new char[1];
    private final char[] bPi = new char[32];
    private final char[] bPj = new char[1024];
    private final StringBuilder bPk = new StringBuilder(32);
    private final StringBuilder bPl = new StringBuilder(1024);
    private final Stack<Integer> bPs = new Stack<>();
    private static final char[] bPm = {'u', 'l', 'l'};
    private static final char[] bPn = {'r', 'u', 'e'};
    private static final char[] bPo = {'r', 'u', 'e', '\"'};
    private static final char[] bPp = {'a', 'l', 's', 'e'};
    private static final char[] bPq = {'a', 'l', 's', 'e', '\"'};
    private static final char[] bPr = {'\n'};
    private static final d<Integer> bPt = new k();
    private static final d<Long> bPu = new l();
    private static final d<Float> bPv = new m();
    private static final d<Double> bPw = new n();
    private static final d<Boolean> bPx = new o();
    private static final d<String> bPy = new p();
    private static final d<BigInteger> bPz = new q();
    private static final d<BigDecimal> bPA = new r();

    private final int a(BufferedReader bufferedReader, char[] cArr) throws c, IOException {
        int i;
        char cJ = j(bufferedReader);
        if (cJ == 0) {
            throw new c("Unexpected EOF");
        }
        if (cJ == ',') {
            throw new c("Missing value");
        }
        if (cJ == 'n') {
            b(bufferedReader, bPm);
            return 0;
        }
        bufferedReader.mark(1024);
        if (cJ == '\"') {
            boolean z = false;
            int i2 = 0;
            while (i2 < cArr.length && bufferedReader.read(cArr, i2, 1) != -1) {
                char c = cArr[i2];
                if (Character.isISOControl(c)) {
                    throw new c("Unexpected control character while reading string");
                }
                if (c == '\"' && !z) {
                    bufferedReader.reset();
                    bufferedReader.skip(i2 + 1);
                    return i2;
                }
                if (c == '\\') {
                    z = !z;
                } else {
                    z = false;
                }
                i2++;
            }
            i = i2;
        } else {
            cArr[0] = cJ;
            i = 1;
            while (i < cArr.length && bufferedReader.read(cArr, i, 1) != -1) {
                if (cArr[i] == '}' || cArr[i] == ',' || Character.isWhitespace(cArr[i]) || cArr[i] == ']') {
                    bufferedReader.reset();
                    bufferedReader.skip(i - 1);
                    cArr[i] = 0;
                    return i;
                }
                i++;
            }
        }
        if (i == cArr.length) {
            throw new c("Absurdly long value");
        }
        throw new c("Unexpected EOF");
    }

    private final String a(BufferedReader bufferedReader) throws c, IOException {
        String strB = null;
        this.bPs.push(2);
        char cJ = j(bufferedReader);
        switch (cJ) {
            case '\"':
                this.bPs.push(3);
                strB = b(bufferedReader, this.bPi, this.bPk, null);
                ne(3);
                if (j(bufferedReader) != ':') {
                    throw new c("Expected key/value separator");
                }
                return strB;
            case ']':
                ne(2);
                ne(1);
                ne(5);
                return strB;
            case '}':
                ne(2);
                return strB;
            default:
                throw new c(new StringBuilder(19).append("Unexpected token: ").append(cJ).toString());
        }
    }

    private final String a(BufferedReader bufferedReader, char[] cArr, StringBuilder sb, char[] cArr2) throws c, IOException {
        switch (j(bufferedReader)) {
            case '\"':
                return b(bufferedReader, cArr, sb, cArr2);
            case 'n':
                b(bufferedReader, bPm);
                return null;
            default:
                throw new c("Expected string");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Switch 'out' block B:29:0x0037 for B:13:0x0048 already processed. Defaulting to fallback option. */
    private final <T extends FastJsonResponse> ArrayList<T> a(BufferedReader bufferedReader, FastJsonResponse.Field<?, ?> field) throws c, IOException {
        ArrayList<T> arrayList = (ArrayList<T>) new ArrayList();
        char cJ = j(bufferedReader);
        switch (cJ) {
            case ']':
                ne(5);
                return arrayList;
            case 'n':
                b(bufferedReader, bPm);
                ne(5);
                return null;
            case '{':
                this.bPs.push(1);
                while (true) {
                    try {
                        FastJsonResponse fastJsonResponseAaT = field.aaT();
                        if (!a(bufferedReader, fastJsonResponseAaT)) {
                            return arrayList;
                        }
                        arrayList.add(fastJsonResponseAaT);
                        char cJ2 = j(bufferedReader);
                        switch (cJ2) {
                            case ',':
                                if (j(bufferedReader) != '{') {
                                    throw new c("Expected start of next object in array");
                                }
                                this.bPs.push(1);
                                break;
                            case ']':
                                ne(5);
                                return arrayList;
                            default:
                                throw new c(new StringBuilder(19).append("Unexpected token: ").append(cJ2).toString());
                        }
                    } catch (IllegalAccessException e) {
                        throw new c("Error instantiating inner object", e);
                    } catch (InstantiationException e2) {
                        throw new c("Error instantiating inner object", e2);
                    }
                }
                break;
            default:
                throw new c(new StringBuilder(19).append("Unexpected token: ").append(cJ).toString());
        }
    }

    private final <O> ArrayList<O> a(BufferedReader bufferedReader, d<O> dVar) throws c, IOException {
        char cJ = j(bufferedReader);
        if (cJ != 'n') {
            if (cJ == '[') {
                this.bPs.push(5);
                ArrayList<O> arrayList = new ArrayList<>();
                while (true) {
                    bufferedReader.mark(1024);
                    switch (j(bufferedReader)) {
                        case 0:
                            throw new c("Unexpected EOF");
                        case ',':
                            break;
                        case ']':
                            ne(5);
                            return arrayList;
                        default:
                            bufferedReader.reset();
                            arrayList.add(dVar.h(this, bufferedReader));
                            break;
                    }
                }
            } else {
                throw new c("Expected start of array");
            }
        } else {
            b(bufferedReader, bPm);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean a(BufferedReader bufferedReader, FastJsonResponse fastJsonResponse) throws c, IOException {
        HashMap map;
        Map<String, FastJsonResponse.Field<?, ?>> fieldMappings = fastJsonResponse.getFieldMappings();
        String strA = a(bufferedReader);
        if (strA == null) {
            ne(1);
            return false;
        }
        while (strA != null) {
            FastJsonResponse.Field<?, ?> field = fieldMappings.get(strA);
            if (field == null) {
                strA = b(bufferedReader);
            } else {
                this.bPs.push(4);
                switch (field.aaJ()) {
                    case 0:
                        if (!field.aaL()) {
                            fastJsonResponse.setInteger(field, d(bufferedReader));
                        } else {
                            fastJsonResponse.setIntegers((FastJsonResponse.Field) field, (ArrayList<Integer>) a(bufferedReader, bPt));
                        }
                        break;
                    case 1:
                        if (!field.aaL()) {
                            fastJsonResponse.setBigInteger(field, f(bufferedReader));
                        } else {
                            fastJsonResponse.setBigIntegers((FastJsonResponse.Field) field, (ArrayList<BigInteger>) a(bufferedReader, bPz));
                        }
                        break;
                    case 2:
                        if (!field.aaL()) {
                            fastJsonResponse.setLong(field, e(bufferedReader));
                        } else {
                            fastJsonResponse.setLongs((FastJsonResponse.Field) field, (ArrayList<Long>) a(bufferedReader, bPu));
                        }
                        break;
                    case 3:
                        if (!field.aaL()) {
                            fastJsonResponse.setFloat(field, g(bufferedReader));
                        } else {
                            fastJsonResponse.setFloats((FastJsonResponse.Field) field, (ArrayList<Float>) a(bufferedReader, bPv));
                        }
                        break;
                    case 4:
                        if (!field.aaL()) {
                            fastJsonResponse.setDouble(field, h(bufferedReader));
                        } else {
                            fastJsonResponse.setDoubles((FastJsonResponse.Field) field, (ArrayList<Double>) a(bufferedReader, bPw));
                        }
                        break;
                    case 5:
                        if (!field.aaL()) {
                            fastJsonResponse.setBigDecimal(field, i(bufferedReader));
                        } else {
                            fastJsonResponse.setBigDecimals((FastJsonResponse.Field) field, (ArrayList<BigDecimal>) a(bufferedReader, bPA));
                        }
                        break;
                    case 6:
                        if (!field.aaL()) {
                            fastJsonResponse.setBoolean(field, a(bufferedReader, false));
                        } else {
                            fastJsonResponse.setBooleans((FastJsonResponse.Field) field, (ArrayList<Boolean>) a(bufferedReader, bPx));
                        }
                        break;
                    case 7:
                        if (!field.aaL()) {
                            fastJsonResponse.setString(field, c(bufferedReader));
                        } else {
                            fastJsonResponse.setStrings((FastJsonResponse.Field) field, (ArrayList<String>) a(bufferedReader, bPy));
                        }
                        break;
                    case 8:
                        fastJsonResponse.setDecodedBytes(field, com.google.android.gms.common.d.c.decode(a(bufferedReader, this.bPj, this.bPl, bPr)));
                        break;
                    case 9:
                        fastJsonResponse.setDecodedBytes(field, com.google.android.gms.common.d.c.ig(a(bufferedReader, this.bPj, this.bPl, bPr)));
                        break;
                    case R.styleable.CardView_contentPaddingRight /* 10 */:
                        char cJ = j(bufferedReader);
                        if (cJ == 'n') {
                            b(bufferedReader, bPm);
                            map = null;
                        } else {
                            if (cJ != '{') {
                                throw new c("Expected start of a map object");
                            }
                            this.bPs.push(1);
                            map = new HashMap();
                            while (true) {
                                switch (j(bufferedReader)) {
                                    case 0:
                                        throw new c("Unexpected EOF");
                                    case '\"':
                                        String strB = b(bufferedReader, this.bPi, this.bPk, null);
                                        if (j(bufferedReader) != ':') {
                                            String strValueOf = String.valueOf(strB);
                                            throw new c(strValueOf.length() != 0 ? "No map value found for key ".concat(strValueOf) : new String("No map value found for key "));
                                        }
                                        if (j(bufferedReader) != '\"') {
                                            String strValueOf2 = String.valueOf(strB);
                                            throw new c(strValueOf2.length() != 0 ? "Expected String value for key ".concat(strValueOf2) : new String("Expected String value for key "));
                                        }
                                        map.put(strB, b(bufferedReader, this.bPi, this.bPk, null));
                                        char cJ2 = j(bufferedReader);
                                        if (cJ2 != ',') {
                                            if (cJ2 != '}') {
                                                throw new c(new StringBuilder(48).append("Unexpected character while parsing string map: ").append(cJ2).toString());
                                            }
                                            ne(1);
                                        }
                                        break;
                                    case '}':
                                        ne(1);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        fastJsonResponse.setStringMap(field, map);
                        break;
                    case R.styleable.CardView_contentPaddingTop /* 11 */:
                        if (field.aaL()) {
                            char cJ3 = j(bufferedReader);
                            if (cJ3 != 'n') {
                                this.bPs.push(5);
                                if (cJ3 != '[') {
                                    throw new c("Expected array start");
                                }
                                fastJsonResponse.addConcreteTypeArrayInternal(field, field.aaN(), a(bufferedReader, field));
                            } else {
                                b(bufferedReader, bPm);
                                fastJsonResponse.addConcreteTypeArrayInternal(field, field.aaN(), null);
                            }
                        } else {
                            char cJ4 = j(bufferedReader);
                            if (cJ4 == 'n') {
                                b(bufferedReader, bPm);
                                fastJsonResponse.addConcreteTypeInternal(field, field.aaN(), null);
                            } else {
                                this.bPs.push(1);
                                if (cJ4 != '{') {
                                    throw new c("Expected start of object");
                                }
                                try {
                                    FastJsonResponse fastJsonResponseAaT = field.aaT();
                                    a(bufferedReader, fastJsonResponseAaT);
                                    fastJsonResponse.addConcreteTypeInternal(field, field.aaN(), fastJsonResponseAaT);
                                } catch (IllegalAccessException e) {
                                    throw new c("Error instantiating inner object", e);
                                } catch (InstantiationException e2) {
                                    throw new c("Error instantiating inner object", e2);
                                }
                            }
                        }
                        break;
                    default:
                        throw new c(new StringBuilder(30).append("Invalid field type ").append(field.aaJ()).toString());
                }
                ne(4);
                ne(2);
                char cJ5 = j(bufferedReader);
                switch (cJ5) {
                    case ',':
                        strA = a(bufferedReader);
                        break;
                    case '}':
                        strA = null;
                        break;
                    default:
                        throw new c(new StringBuilder(55).append("Expected end of object or field separator, but found: ").append(cJ5).toString());
                }
            }
        }
        i<? extends FastJsonResponse> postProcessor = fastJsonResponse.getPostProcessor();
        if (postProcessor != null) {
            postProcessor.a(fastJsonResponse);
        }
        ne(1);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Switch 'out' block B:3:0x0002 for B:4:0x0006 already processed. Defaulting to fallback option. */
    public final boolean a(BufferedReader bufferedReader, boolean z) throws c, IOException {
        while (true) {
            char cJ = j(bufferedReader);
            switch (cJ) {
                case '\"':
                    if (z) {
                        throw new c("No boolean value found in string");
                    }
                    z = true;
                    break;
                case FacebookRequestErrorClassification.EC_INVALID_SESSION /* 102 */:
                    b(bufferedReader, z ? bPq : bPp);
                    return false;
                case 'n':
                    b(bufferedReader, bPm);
                    return false;
                case 't':
                    b(bufferedReader, z ? bPo : bPn);
                    return true;
                default:
                    throw new c(new StringBuilder(19).append("Unexpected token: ").append(cJ).toString());
            }
        }
    }

    private final String b(BufferedReader bufferedReader) throws c, IOException {
        bufferedReader.mark(1024);
        switch (j(bufferedReader)) {
            case '\"':
                if (bufferedReader.read(this.bPh) == -1) {
                    throw new c("Unexpected EOF while parsing string");
                }
                char c = this.bPh[0];
                boolean z = false;
                while (true) {
                    if (c != '\"' || z) {
                        boolean z2 = c == '\\' && !z;
                        if (bufferedReader.read(this.bPh) == -1) {
                            throw new c("Unexpected EOF while parsing string");
                        }
                        c = this.bPh[0];
                        if (Character.isISOControl(c)) {
                            throw new c("Unexpected control character while reading string");
                        }
                        z = z2;
                    }
                }
                break;
            case ',':
                throw new c("Missing value");
            case '[':
                this.bPs.push(5);
                bufferedReader.mark(32);
                if (j(bufferedReader) != ']') {
                    bufferedReader.reset();
                    int i = 1;
                    boolean z3 = false;
                    boolean z4 = false;
                    while (i > 0) {
                        char cJ = j(bufferedReader);
                        if (cJ == 0) {
                            throw new c("Unexpected EOF while parsing array");
                        }
                        if (Character.isISOControl(cJ)) {
                            throw new c("Unexpected control character while reading array");
                        }
                        if (cJ == '\"' && !z4) {
                            z3 = !z3;
                        }
                        int i2 = (cJ != '[' || z3) ? i : i + 1;
                        i = (cJ != ']' || z3) ? i2 : i2 - 1;
                        z4 = (cJ == '\\' && z3) ? !z4 : false;
                    }
                    ne(5);
                } else {
                    ne(5);
                }
                break;
            case '{':
                this.bPs.push(1);
                bufferedReader.mark(32);
                char cJ2 = j(bufferedReader);
                if (cJ2 == '}') {
                    ne(1);
                } else {
                    if (cJ2 != '\"') {
                        throw new c(new StringBuilder(18).append("Unexpected token ").append(cJ2).toString());
                    }
                    bufferedReader.reset();
                    a(bufferedReader);
                    while (b(bufferedReader) != null) {
                    }
                    ne(1);
                }
                break;
            default:
                bufferedReader.reset();
                a(bufferedReader, this.bPj);
                break;
        }
        char cJ3 = j(bufferedReader);
        switch (cJ3) {
            case ',':
                ne(2);
                return a(bufferedReader);
            case '}':
                ne(2);
                return null;
            default:
                throw new c(new StringBuilder(18).append("Unexpected token ").append(cJ3).toString());
        }
    }

    private static String b(BufferedReader bufferedReader, char[] cArr, StringBuilder sb, char[] cArr2) throws c, IOException {
        boolean z;
        boolean z2;
        sb.setLength(0);
        bufferedReader.mark(cArr.length);
        boolean z3 = false;
        boolean z4 = false;
        while (true) {
            int i = bufferedReader.read(cArr);
            if (i == -1) {
                throw new c("Unexpected EOF while parsing string");
            }
            int i2 = 0;
            while (i2 < i) {
                char c = cArr[i2];
                if (Character.isISOControl(c)) {
                    if (cArr2 == null) {
                        z2 = false;
                        break;
                    }
                    int i3 = 0;
                    while (true) {
                        if (i3 >= cArr2.length) {
                            z2 = false;
                            break;
                        }
                        if (cArr2[i3] == c) {
                            z2 = true;
                            break;
                        }
                        i3++;
                    }
                    if (!z2) {
                        throw new c("Unexpected control character while reading string");
                    }
                }
                if (c == '\"' && !z4) {
                    sb.append(cArr, 0, i2);
                    bufferedReader.reset();
                    bufferedReader.skip(i2 + 1);
                    return z3 ? com.google.android.gms.common.d.p.ii(sb.toString()) : sb.toString();
                }
                if (c == '\\') {
                    z = true;
                    z4 = !z4;
                } else {
                    z = z3;
                    z4 = false;
                }
                i2++;
                z3 = z;
            }
            sb.append(cArr, 0, i);
            bufferedReader.mark(cArr.length);
        }
    }

    private final void b(BufferedReader bufferedReader, char[] cArr) throws c, IOException {
        int i = 0;
        while (i < cArr.length) {
            int i2 = bufferedReader.read(this.bPi, 0, cArr.length - i);
            if (i2 == -1) {
                throw new c("Unexpected EOF");
            }
            for (int i3 = 0; i3 < i2; i3++) {
                if (cArr[i3 + i] != this.bPi[i3]) {
                    throw new c("Unexpected character");
                }
            }
            i += i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String c(BufferedReader bufferedReader) {
        return a(bufferedReader, this.bPi, this.bPk, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int d(BufferedReader bufferedReader) throws c, IOException {
        int i;
        int i2;
        boolean z;
        int i3;
        int i4 = 0;
        int iA = a(bufferedReader, this.bPj);
        if (iA == 0) {
            return 0;
        }
        char[] cArr = this.bPj;
        if (iA <= 0) {
            throw new c("No number to parse");
        }
        if (cArr[0] == '-') {
            i = Integer.MIN_VALUE;
            i2 = 1;
            z = true;
        } else {
            i = -2147483647;
            i2 = 0;
            z = false;
        }
        if (i2 < iA) {
            i3 = i2 + 1;
            int iDigit = Character.digit(cArr[i2], 10);
            if (iDigit < 0) {
                throw new c("Unexpected non-digit character");
            }
            i4 = -iDigit;
        } else {
            i3 = i2;
        }
        while (i3 < iA) {
            int i5 = i3 + 1;
            int iDigit2 = Character.digit(cArr[i3], 10);
            if (iDigit2 < 0) {
                throw new c("Unexpected non-digit character");
            }
            if (i4 < -214748364) {
                throw new c("Number too large");
            }
            int i6 = i4 * 10;
            if (i6 < i + iDigit2) {
                throw new c("Number too large");
            }
            i4 = i6 - iDigit2;
            i3 = i5;
        }
        if (!z) {
            return -i4;
        }
        if (i3 <= 1) {
            throw new c("No digits to parse");
        }
        return i4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long e(BufferedReader bufferedReader) throws c, IOException {
        long j;
        int i;
        boolean z;
        int i2;
        long j2 = 0;
        int iA = a(bufferedReader, this.bPj);
        if (iA == 0) {
            return 0L;
        }
        char[] cArr = this.bPj;
        if (iA <= 0) {
            throw new c("No number to parse");
        }
        if (cArr[0] == '-') {
            j = Long.MIN_VALUE;
            i = 1;
            z = true;
        } else {
            j = -9223372036854775807L;
            i = 0;
            z = false;
        }
        if (i < iA) {
            i2 = i + 1;
            int iDigit = Character.digit(cArr[i], 10);
            if (iDigit < 0) {
                throw new c("Unexpected non-digit character");
            }
            j2 = -iDigit;
        } else {
            i2 = i;
        }
        while (i2 < iA) {
            int i3 = i2 + 1;
            int iDigit2 = Character.digit(cArr[i2], 10);
            if (iDigit2 < 0) {
                throw new c("Unexpected non-digit character");
            }
            if (j2 < -922337203685477580L) {
                throw new c("Number too large");
            }
            long j3 = j2 * 10;
            if (j3 < ((long) iDigit2) + j) {
                throw new c("Number too large");
            }
            j2 = j3 - ((long) iDigit2);
            i2 = i3;
        }
        if (!z) {
            return -j2;
        }
        if (i2 <= 1) {
            throw new c("No digits to parse");
        }
        return j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BigInteger f(BufferedReader bufferedReader) throws c, IOException {
        int iA = a(bufferedReader, this.bPj);
        if (iA == 0) {
            return null;
        }
        return new BigInteger(new String(this.bPj, 0, iA));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final float g(BufferedReader bufferedReader) throws c, IOException {
        int iA = a(bufferedReader, this.bPj);
        if (iA == 0) {
            return 0.0f;
        }
        return Float.parseFloat(new String(this.bPj, 0, iA));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final double h(BufferedReader bufferedReader) throws c, IOException {
        int iA = a(bufferedReader, this.bPj);
        if (iA == 0) {
            return 0.0d;
        }
        return Double.parseDouble(new String(this.bPj, 0, iA));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BigDecimal i(BufferedReader bufferedReader) throws c, IOException {
        int iA = a(bufferedReader, this.bPj);
        if (iA == 0) {
            return null;
        }
        return new BigDecimal(new String(this.bPj, 0, iA));
    }

    private final char j(BufferedReader bufferedReader) {
        if (bufferedReader.read(this.bPh) == -1) {
            return (char) 0;
        }
        while (Character.isWhitespace(this.bPh[0])) {
            if (bufferedReader.read(this.bPh) == -1) {
                return (char) 0;
            }
        }
        return this.bPh[0];
    }

    private final void ne(int i) throws c {
        if (this.bPs.isEmpty()) {
            throw new c(new StringBuilder(46).append("Expected state ").append(i).append(" but had empty stack").toString());
        }
        int iIntValue = this.bPs.pop().intValue();
        if (iIntValue != i) {
            throw new c(new StringBuilder(46).append("Expected state ").append(i).append(" but had ").append(iIntValue).toString());
        }
    }

    public void a(InputStream inputStream, T t) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1024);
        try {
            try {
                this.bPs.push(0);
                char cJ = j(bufferedReader);
                switch (cJ) {
                    case 0:
                        throw new c("No data to parse");
                    case '[':
                        this.bPs.push(5);
                        Map<String, FastJsonResponse.Field<?, ?>> fieldMappings = t.getFieldMappings();
                        if (fieldMappings.size() != 1) {
                            throw new c("Object array response class must have a single Field");
                        }
                        FastJsonResponse.Field<?, ?> value = fieldMappings.entrySet().iterator().next().getValue();
                        t.addConcreteTypeArrayInternal(value, value.aaN(), a(bufferedReader, value));
                        break;
                    case '{':
                        this.bPs.push(1);
                        a(bufferedReader, t);
                        break;
                    default:
                        throw new c(new StringBuilder(19).append("Unexpected token: ").append(cJ).toString());
                }
                ne(0);
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.w("FastParser", "Failed to close reader while parsing.");
                }
            } catch (IOException e2) {
                throw new c(e2);
            }
        } catch (Throwable th) {
            try {
                bufferedReader.close();
            } catch (IOException e3) {
                Log.w("FastParser", "Failed to close reader while parsing.");
            }
            throw th;
        }
    }
}
