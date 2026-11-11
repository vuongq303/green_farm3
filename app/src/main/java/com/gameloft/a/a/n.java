package com.gameloft.a.a;

import com.gameloft.android.wrapper.au;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: CrmUtils.java */
/* JADX INFO: loaded from: classes.dex */
public class n {
    static int[] Tt;
    static int[] Tu;
    static long[] Tv;
    static long[] Tw;
    static byte[] Tx;
    static byte[] Ty;
    private static int[] UG;
    private static int Va = 7;
    private static int bwr = 0;
    static byte[] Tz = {-128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final char[] TA = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] UF = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    private static void VU() {
        byte[] bytes = au.Pz().getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bwr |= bytes[i] << ((i + 5) % 31);
        }
    }

    public static int nb(int i) {
        if (bwr == 0) {
            VU();
        }
        return ((i << Va) | (i >>> (32 - Va))) ^ bwr;
    }

    public static int nc(int i) {
        if (bwr == 0) {
            VU();
        }
        int i2 = bwr ^ i;
        return (i2 << (32 - Va)) | (i2 >>> Va);
    }

    public static int d(byte[] bArr, int i, int i2) {
        int i3 = i + 1;
        bArr[i] = (byte) (i2 & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i2 >>> 8) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i2 >>> 16) & 255);
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((i2 >>> 24) & 255);
        return i6;
    }

    public static int l(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = ((bArr[i2] & 255) << 8) | (bArr[i] & 255);
        int i5 = i3 + 1;
        int i6 = i4 | ((bArr[i3] & 255) << 16);
        int i7 = i5 + 1;
        return i6 | ((bArr[i5] & 255) << 24);
    }

    public static void a(Object obj, int i, Object obj2, int i2, int i3) {
        System.arraycopy(obj, i, obj2, i2, i3);
    }

    public static int a(byte[] bArr, int i, byte[] bArr2) {
        a(bArr2, 0, bArr, i, bArr2.length);
        return bArr2.length + i;
    }

    public static int b(byte[] bArr, int i, byte[] bArr2) {
        a(bArr, i, bArr2, 0, bArr2.length);
        return bArr2.length + i;
    }

    public static byte[] a(byte[] bArr, String str, boolean z) {
        byte[] bytes = null;
        if (!"".equals(str) && str != null) {
            bytes = str.getBytes();
        }
        return a(bArr, bytes, z);
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, boolean z) {
        return bArr.length == 0 ? bArr : a(a(a(bArr, z, (int[]) null), a(bArr2, false, (int[]) null)), false, (byte[]) null);
    }

    public static int[] a(int[] iArr, int[] iArr2) {
        int length = iArr.length - 1;
        if (length >= 1) {
            if (iArr2.length < 4) {
                int[] iArr3 = new int[4];
                System.arraycopy(iArr2, 0, iArr3, 0, iArr2.length);
                iArr2 = iArr3;
            }
            int i = iArr[length];
            int i2 = iArr[0];
            int i3 = (52 / (length + 1)) + 6;
            int i4 = 0;
            while (true) {
                int i5 = i3 - 1;
                if (i3 <= 0) {
                    break;
                }
                i4 -= 1640531527;
                int i6 = (i4 >>> 2) & 3;
                int i7 = 0;
                while (i7 < length) {
                    int i8 = iArr[i7 + 1];
                    i = (((i ^ iArr2[(i7 & 3) ^ i6]) + (i8 ^ i4)) ^ (((i >>> 5) ^ (i8 << 2)) + ((i8 >>> 3) ^ (i << 4)))) + iArr[i7];
                    iArr[i7] = i;
                    i7++;
                }
                int i9 = iArr[0];
                i = iArr[length] + (((iArr2[(i7 & 3) ^ i6] ^ i) + (i9 ^ i4)) ^ (((i >>> 5) ^ (i9 << 2)) + ((i9 >>> 3) ^ (i << 4))));
                iArr[length] = i;
                i3 = i5;
            }
        }
        return iArr;
    }

    public static byte[] b(byte[] bArr, String str, boolean z) {
        byte[] bytes = null;
        if (!"".equals(str) && str != null) {
            bytes = str.getBytes();
        }
        return b(bArr, bytes, z);
    }

    public static byte[] b(byte[] bArr, byte[] bArr2, boolean z) {
        return (bArr == null || bArr.length == 0) ? bArr : a(b(a(bArr, false, (int[]) null), a(bArr2, false, (int[]) null)), z, (byte[]) null);
    }

    public static int[] b(int[] iArr, int[] iArr2) {
        int length = iArr.length - 1;
        if (length >= 1) {
            if (iArr2.length < 4) {
                int[] iArr3 = new int[4];
                System.arraycopy(iArr2, 0, iArr3, 0, iArr2.length);
                iArr2 = iArr3;
            }
            int i = iArr[length];
            int i2 = iArr[0];
            for (int i3 = ((52 / (length + 1)) + 6) * (-1640531527); i3 != 0; i3 -= -1640531527) {
                int i4 = (i3 >>> 2) & 3;
                int i5 = length;
                while (i5 > 0) {
                    int i6 = iArr[i5 - 1];
                    i2 = iArr[i5] - (((i2 ^ i3) + (i6 ^ iArr2[(i5 & 3) ^ i4])) ^ (((i6 >>> 5) ^ (i2 << 2)) + ((i2 >>> 3) ^ (i6 << 4))));
                    iArr[i5] = i2;
                    i5--;
                }
                int i7 = iArr[length];
                i2 = iArr[0] - (((iArr2[(i5 & 3) ^ i4] ^ i7) + (i2 ^ i3)) ^ (((i7 >>> 5) ^ (i2 << 2)) + ((i2 >>> 3) ^ (i7 << 4))));
                iArr[0] = i2;
            }
        }
        return iArr;
    }

    public static int[] a(byte[] bArr, boolean z, int[] iArr) {
        int length = (bArr.length & 3) == 0 ? bArr.length >>> 2 : (bArr.length >>> 2) + 1;
        if (z) {
            if (iArr == null) {
                iArr = new int[length + 1];
            }
            iArr[length] = bArr.length;
        } else if (iArr == null) {
            iArr = new int[length];
        }
        int length2 = bArr.length;
        for (int i = 0; i < length2; i++) {
            int i2 = i >>> 2;
            iArr[i2] = iArr[i2] | ((bArr[i] & 255) << ((i & 3) << 3));
        }
        return iArr;
    }

    public static byte[] a(int[] iArr, boolean z, byte[] bArr) {
        int length = iArr.length << 2;
        if (z) {
            int i = iArr[iArr.length - 1];
            if (i > length) {
                return null;
            }
            length = i;
        }
        if (bArr == null) {
            bArr = new byte[length];
        }
        for (int i2 = 0; i2 < length; i2++) {
            bArr[i2] = (byte) ((iArr[i2 >>> 2] >>> ((i2 & 3) << 3)) & 255);
        }
        return bArr;
    }

    public static void kM() {
        Tx = new byte[64];
        Tv = new long[1];
        Tv[0] = 0;
        Tt = new int[4];
        Tt[0] = 1732584193;
        Tt[1] = -271733879;
        Tt[2] = -1732584194;
        Tt[3] = 271733878;
    }

    private static void kN() {
        Ty = new byte[64];
        Tw = new long[1];
        Tw[0] = 0;
        Tu = new int[4];
        System.arraycopy(Tx, 0, Ty, 0, Ty.length);
        System.arraycopy(Tt, 0, Tu, 0, Tu.length);
        Tw = Tv;
    }

    private static void a(byte[] bArr, int i, int[] iArr) {
        iArr[0] = (bArr[i + 0] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16) | (bArr[i + 3] << 24);
        iArr[1] = (bArr[i + 4] & 255) | ((bArr[i + 5] & 255) << 8) | ((bArr[i + 6] & 255) << 16) | (bArr[i + 7] << 24);
        iArr[2] = (bArr[i + 8] & 255) | ((bArr[i + 9] & 255) << 8) | ((bArr[i + 10] & 255) << 16) | (bArr[i + 11] << 24);
        iArr[3] = (bArr[i + 12] & 255) | ((bArr[i + 13] & 255) << 8) | ((bArr[i + 14] & 255) << 16) | (bArr[i + 15] << 24);
        iArr[4] = (bArr[i + 16] & 255) | ((bArr[i + 17] & 255) << 8) | ((bArr[i + 18] & 255) << 16) | (bArr[i + 19] << 24);
        iArr[5] = (bArr[i + 20] & 255) | ((bArr[i + 21] & 255) << 8) | ((bArr[i + 22] & 255) << 16) | (bArr[i + 23] << 24);
        iArr[6] = (bArr[i + 24] & 255) | ((bArr[i + 25] & 255) << 8) | ((bArr[i + 26] & 255) << 16) | (bArr[i + 27] << 24);
        iArr[7] = (bArr[i + 28] & 255) | ((bArr[i + 29] & 255) << 8) | ((bArr[i + 30] & 255) << 16) | (bArr[i + 31] << 24);
        iArr[8] = (bArr[i + 32] & 255) | ((bArr[i + 33] & 255) << 8) | ((bArr[i + 34] & 255) << 16) | (bArr[i + 35] << 24);
        iArr[9] = (bArr[i + 36] & 255) | ((bArr[i + 37] & 255) << 8) | ((bArr[i + 38] & 255) << 16) | (bArr[i + 39] << 24);
        iArr[10] = (bArr[i + 40] & 255) | ((bArr[i + 41] & 255) << 8) | ((bArr[i + 42] & 255) << 16) | (bArr[i + 43] << 24);
        iArr[11] = (bArr[i + 44] & 255) | ((bArr[i + 45] & 255) << 8) | ((bArr[i + 46] & 255) << 16) | (bArr[i + 47] << 24);
        iArr[12] = (bArr[i + 48] & 255) | ((bArr[i + 49] & 255) << 8) | ((bArr[i + 50] & 255) << 16) | (bArr[i + 51] << 24);
        iArr[13] = (bArr[i + 52] & 255) | ((bArr[i + 53] & 255) << 8) | ((bArr[i + 54] & 255) << 16) | (bArr[i + 55] << 24);
        iArr[14] = (bArr[i + 56] & 255) | ((bArr[i + 57] & 255) << 8) | ((bArr[i + 58] & 255) << 16) | (bArr[i + 59] << 24);
        iArr[15] = (bArr[i + 60] & 255) | ((bArr[i + 61] & 255) << 8) | ((bArr[i + 62] & 255) << 16) | (bArr[i + 63] << 24);
    }

    private static void a(int[] iArr, byte[] bArr, int i, int[] iArr2) {
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = iArr[2];
        int i5 = iArr[3];
        a(bArr, i, iArr2);
        int i6 = i2 + ((((i3 & i4) | ((i3 ^ (-1)) & i5)) + iArr2[0]) - 680876936);
        int i7 = ((i6 >>> 25) | (i6 << 7)) + i3;
        int i8 = i5 + ((((i7 & i3) | ((i7 ^ (-1)) & i4)) + iArr2[1]) - 389564586);
        int i9 = ((i8 >>> 20) | (i8 << 12)) + i7;
        int i10 = i4 + ((i9 & i7) | ((i9 ^ (-1)) & i3)) + iArr2[2] + 606105819;
        int i11 = ((i10 >>> 15) | (i10 << 17)) + i9;
        int i12 = i3 + ((((i11 & i9) | ((i11 ^ (-1)) & i7)) + iArr2[3]) - 1044525330);
        int i13 = ((i12 >>> 10) | (i12 << 22)) + i11;
        int i14 = i7 + ((((i13 & i11) | ((i13 ^ (-1)) & i9)) + iArr2[4]) - 176418897);
        int i15 = ((i14 >>> 25) | (i14 << 7)) + i13;
        int i16 = i9 + ((i15 & i13) | ((i15 ^ (-1)) & i11)) + iArr2[5] + 1200080426;
        int i17 = ((i16 >>> 20) | (i16 << 12)) + i15;
        int i18 = i11 + ((((i17 & i15) | ((i17 ^ (-1)) & i13)) + iArr2[6]) - 1473231341);
        int i19 = ((i18 >>> 15) | (i18 << 17)) + i17;
        int i20 = i13 + ((((i19 & i17) | ((i19 ^ (-1)) & i15)) + iArr2[7]) - 45705983);
        int i21 = ((i20 >>> 10) | (i20 << 22)) + i19;
        int i22 = i15 + ((i21 & i19) | ((i21 ^ (-1)) & i17)) + iArr2[8] + 1770035416;
        int i23 = ((i22 >>> 25) | (i22 << 7)) + i21;
        int i24 = i17 + ((((i23 & i21) | ((i23 ^ (-1)) & i19)) + iArr2[9]) - 1958414417);
        int i25 = ((i24 >>> 20) | (i24 << 12)) + i23;
        int i26 = i19 + ((((i25 & i23) | ((i25 ^ (-1)) & i21)) + iArr2[10]) - 42063);
        int i27 = ((i26 >>> 15) | (i26 << 17)) + i25;
        int i28 = i21 + ((((i27 & i25) | ((i27 ^ (-1)) & i23)) + iArr2[11]) - 1990404162);
        int i29 = ((i28 >>> 10) | (i28 << 22)) + i27;
        int i30 = i23 + ((i29 & i27) | ((i29 ^ (-1)) & i25)) + iArr2[12] + 1804603682;
        int i31 = ((i30 >>> 25) | (i30 << 7)) + i29;
        int i32 = i25 + ((((i31 & i29) | ((i31 ^ (-1)) & i27)) + iArr2[13]) - 40341101);
        int i33 = ((i32 >>> 20) | (i32 << 12)) + i31;
        int i34 = i27 + ((((i33 & i31) | ((i33 ^ (-1)) & i29)) + iArr2[14]) - 1502002290);
        int i35 = ((i34 >>> 15) | (i34 << 17)) + i33;
        int i36 = i29 + ((i35 & i33) | ((i35 ^ (-1)) & i31)) + iArr2[15] + 1236535329;
        int i37 = ((i36 >>> 10) | (i36 << 22)) + i35;
        int i38 = i31 + ((((i37 & i33) | ((i33 ^ (-1)) & i35)) + iArr2[1]) - 165796510);
        int i39 = ((i38 >>> 27) | (i38 << 5)) + i37;
        int i40 = i33 + ((((i39 & i35) | ((i35 ^ (-1)) & i37)) + iArr2[6]) - 1069501632);
        int i41 = ((i40 >>> 23) | (i40 << 9)) + i39;
        int i42 = i35 + ((i41 & i37) | ((i37 ^ (-1)) & i39)) + iArr2[11] + 643717713;
        int i43 = ((i42 >>> 18) | (i42 << 14)) + i41;
        int i44 = i37 + ((((i43 & i39) | ((i39 ^ (-1)) & i41)) + iArr2[0]) - 373897302);
        int i45 = ((i44 >>> 12) | (i44 << 20)) + i43;
        int i46 = i39 + ((((i45 & i41) | ((i41 ^ (-1)) & i43)) + iArr2[5]) - 701558691);
        int i47 = ((i46 >>> 27) | (i46 << 5)) + i45;
        int i48 = i41 + ((i47 & i43) | ((i43 ^ (-1)) & i45)) + iArr2[10] + 38016083;
        int i49 = ((i48 >>> 23) | (i48 << 9)) + i47;
        int i50 = i43 + ((((i49 & i45) | ((i45 ^ (-1)) & i47)) + iArr2[15]) - 660478335);
        int i51 = ((i50 >>> 18) | (i50 << 14)) + i49;
        int i52 = i45 + ((((i51 & i47) | ((i47 ^ (-1)) & i49)) + iArr2[4]) - 405537848);
        int i53 = ((i52 >>> 12) | (i52 << 20)) + i51;
        int i54 = i47 + ((i53 & i49) | ((i49 ^ (-1)) & i51)) + iArr2[9] + 568446438;
        int i55 = ((i54 >>> 27) | (i54 << 5)) + i53;
        int i56 = i49 + ((((i55 & i51) | ((i51 ^ (-1)) & i53)) + iArr2[14]) - 1019803690);
        int i57 = ((i56 >>> 23) | (i56 << 9)) + i55;
        int i58 = i51 + ((((i57 & i53) | ((i53 ^ (-1)) & i55)) + iArr2[3]) - 187363961);
        int i59 = ((i58 >>> 18) | (i58 << 14)) + i57;
        int i60 = i53 + ((i59 & i55) | ((i55 ^ (-1)) & i57)) + iArr2[8] + 1163531501;
        int i61 = ((i60 >>> 12) | (i60 << 20)) + i59;
        int i62 = i55 + ((((i61 & i57) | ((i57 ^ (-1)) & i59)) + iArr2[13]) - 1444681467);
        int i63 = ((i62 >>> 27) | (i62 << 5)) + i61;
        int i64 = i57 + ((((i63 & i59) | ((i59 ^ (-1)) & i61)) + iArr2[2]) - 51403784);
        int i65 = ((i64 >>> 23) | (i64 << 9)) + i63;
        int i66 = i59 + ((i65 & i61) | ((i61 ^ (-1)) & i63)) + iArr2[7] + 1735328473;
        int i67 = ((i66 >>> 18) | (i66 << 14)) + i65;
        int i68 = i61 + ((((i67 & i63) | ((i63 ^ (-1)) & i65)) + iArr2[12]) - 1926607734);
        int i69 = ((i68 >>> 12) | (i68 << 20)) + i67;
        int i70 = i63 + ((((i69 ^ i67) ^ i65) + iArr2[5]) - 378558);
        int i71 = ((i70 >>> 28) | (i70 << 4)) + i69;
        int i72 = i65 + ((((i71 ^ i69) ^ i67) + iArr2[8]) - 2022574463);
        int i73 = ((i72 >>> 21) | (i72 << 11)) + i71;
        int i74 = i67 + ((i73 ^ i71) ^ i69) + iArr2[11] + 1839030562;
        int i75 = ((i74 >>> 16) | (i74 << 16)) + i73;
        int i76 = i69 + ((((i75 ^ i73) ^ i71) + iArr2[14]) - 35309556);
        int i77 = ((i76 >>> 9) | (i76 << 23)) + i75;
        int i78 = i71 + ((((i77 ^ i75) ^ i73) + iArr2[1]) - 1530992060);
        int i79 = ((i78 >>> 28) | (i78 << 4)) + i77;
        int i80 = i73 + ((i79 ^ i77) ^ i75) + iArr2[4] + 1272893353;
        int i81 = ((i80 >>> 21) | (i80 << 11)) + i79;
        int i82 = i75 + ((((i81 ^ i79) ^ i77) + iArr2[7]) - 155497632);
        int i83 = ((i82 >>> 16) | (i82 << 16)) + i81;
        int i84 = i77 + ((((i83 ^ i81) ^ i79) + iArr2[10]) - 1094730640);
        int i85 = ((i84 >>> 9) | (i84 << 23)) + i83;
        int i86 = i79 + ((i85 ^ i83) ^ i81) + iArr2[13] + 681279174;
        int i87 = ((i86 >>> 28) | (i86 << 4)) + i85;
        int i88 = i81 + ((((i87 ^ i85) ^ i83) + iArr2[0]) - 358537222);
        int i89 = ((i88 >>> 21) | (i88 << 11)) + i87;
        int i90 = i83 + ((((i89 ^ i87) ^ i85) + iArr2[3]) - 722521979);
        int i91 = ((i90 >>> 16) | (i90 << 16)) + i89;
        int i92 = i85 + ((i91 ^ i89) ^ i87) + iArr2[6] + 76029189;
        int i93 = ((i92 >>> 9) | (i92 << 23)) + i91;
        int i94 = i87 + ((((i93 ^ i91) ^ i89) + iArr2[9]) - 640364487);
        int i95 = ((i94 >>> 28) | (i94 << 4)) + i93;
        int i96 = i89 + ((((i95 ^ i93) ^ i91) + iArr2[12]) - 421815835);
        int i97 = ((i96 >>> 21) | (i96 << 11)) + i95;
        int i98 = i91 + ((i97 ^ i95) ^ i93) + iArr2[15] + 530742520;
        int i99 = ((i98 >>> 16) | (i98 << 16)) + i97;
        int i100 = i93 + ((((i99 ^ i97) ^ i95) + iArr2[2]) - 995338651);
        int i101 = ((i100 >>> 9) | (i100 << 23)) + i99;
        int i102 = i95 + (((((i97 ^ (-1)) | i101) ^ i99) + iArr2[0]) - 198630844);
        int i103 = ((i102 >>> 26) | (i102 << 6)) + i101;
        int i104 = i97 + (((i99 ^ (-1)) | i103) ^ i101) + iArr2[7] + 1126891415;
        int i105 = ((i104 >>> 22) | (i104 << 10)) + i103;
        int i106 = i99 + (((((i101 ^ (-1)) | i105) ^ i103) + iArr2[14]) - 1416354905);
        int i107 = ((i106 >>> 17) | (i106 << 15)) + i105;
        int i108 = i101 + (((((i103 ^ (-1)) | i107) ^ i105) + iArr2[5]) - 57434055);
        int i109 = ((i108 >>> 11) | (i108 << 21)) + i107;
        int i110 = i103 + (((i105 ^ (-1)) | i109) ^ i107) + iArr2[12] + 1700485571;
        int i111 = ((i110 >>> 26) | (i110 << 6)) + i109;
        int i112 = i105 + (((((i107 ^ (-1)) | i111) ^ i109) + iArr2[3]) - 1894986606);
        int i113 = ((i112 >>> 22) | (i112 << 10)) + i111;
        int i114 = i107 + (((((i109 ^ (-1)) | i113) ^ i111) + iArr2[10]) - 1051523);
        int i115 = ((i114 >>> 17) | (i114 << 15)) + i113;
        int i116 = i109 + (((((i111 ^ (-1)) | i115) ^ i113) + iArr2[1]) - 2054922799);
        int i117 = ((i116 >>> 11) | (i116 << 21)) + i115;
        int i118 = i111 + (((i113 ^ (-1)) | i117) ^ i115) + iArr2[8] + 1873313359;
        int i119 = ((i118 >>> 26) | (i118 << 6)) + i117;
        int i120 = i113 + (((((i115 ^ (-1)) | i119) ^ i117) + iArr2[15]) - 30611744);
        int i121 = ((i120 >>> 22) | (i120 << 10)) + i119;
        int i122 = i115 + (((((i117 ^ (-1)) | i121) ^ i119) + iArr2[6]) - 1560198380);
        int i123 = ((i122 >>> 17) | (i122 << 15)) + i121;
        int i124 = i117 + (((i119 ^ (-1)) | i123) ^ i121) + iArr2[13] + 1309151649;
        int i125 = ((i124 >>> 11) | (i124 << 21)) + i123;
        int i126 = i119 + (((((i121 ^ (-1)) | i125) ^ i123) + iArr2[4]) - 145523070);
        int i127 = ((i126 >>> 26) | (i126 << 6)) + i125;
        int i128 = i121 + (((((i123 ^ (-1)) | i127) ^ i125) + iArr2[11]) - 1120210379);
        int i129 = ((i128 >>> 22) | (i128 << 10)) + i127;
        int i130 = i123 + (((i125 ^ (-1)) | i129) ^ i127) + iArr2[2] + 718787259;
        int i131 = ((i130 >>> 17) | (i130 << 15)) + i129;
        int i132 = i125 + (((((i127 ^ (-1)) | i131) ^ i129) + iArr2[9]) - 343485551);
        iArr[0] = i127 + iArr[0];
        iArr[1] = iArr[1] + ((i132 >>> 11) | (i132 << 21)) + i131;
        iArr[2] = iArr[2] + i131;
        iArr[3] = iArr[3] + i129;
    }

    private static void a(byte[] bArr, int[] iArr, long[] jArr, byte[] bArr2, int i, int i2) {
        int i3;
        if (i2 - i > bArr2.length) {
            i2 = bArr2.length - i;
        }
        int i4 = (int) (jArr[0] & 63);
        jArr[0] = jArr[0] + ((long) i2);
        int i5 = 64 - i4;
        if (i2 >= i5) {
            int[] iArr2 = new int[16];
            if (i5 == 64) {
                i5 = 0;
            } else {
                for (int i6 = 0; i6 < i5; i6++) {
                    bArr[i6 + i4] = bArr2[i6 + i];
                }
                a(iArr, bArr, 0, iArr2);
            }
            i3 = i5;
            while (i3 + 63 < i2) {
                a(iArr, bArr2, i3 + i, iArr2);
                i3 += 64;
            }
            i4 = 0;
        } else {
            i3 = 0;
        }
        if (i3 < i2) {
            for (int i7 = i3; i7 < i2; i7++) {
                bArr[(i4 + i7) - i3] = bArr2[i7 + i];
            }
        }
    }

    public static void A(String str, String str2) throws UnsupportedEncodingException {
        if (str2 == null) {
            str2 = "ISO8859_1";
        }
        byte[] bytes = str.getBytes(str2);
        a(Tx, Tt, Tv, bytes, 0, bytes.length);
    }

    private static byte[] e(int[] iArr, int i) {
        byte[] bArr = new byte[i];
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            bArr[i2 + 0] = (byte) (iArr[i3] & 255);
            bArr[i2 + 1] = (byte) ((iArr[i3] >>> 8) & 255);
            bArr[i2 + 2] = (byte) ((iArr[i3] >>> 16) & 255);
            bArr[i2 + 3] = (byte) ((iArr[i3] >>> 24) & 255);
            i2 += 4;
            i3++;
        }
        return bArr;
    }

    private static byte[] kO() {
        kN();
        byte[] bArrE = e(new int[]{(int) (Tw[0] << 3), (int) (Tw[0] >> 29)}, 8);
        int i = (int) (Tw[0] & 63);
        a(Ty, Tu, Tw, Tz, 0, i < 56 ? 56 - i : 120 - i);
        a(Ty, Tu, Tw, bArrE, 0, 8);
        return e(Tu, 16);
    }

    public static String d(byte[] bArr) {
        int i = 0;
        char[] cArr = new char[bArr.length * 2];
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i3 < bArr.length) {
                int i4 = i2 + 1;
                cArr[i2] = TA[(bArr[i3] >>> 4) & 15];
                i2 = i4 + 1;
                cArr[i4] = TA[bArr[i3] & 15];
                i = i3 + 1;
            } else {
                return new String(cArr);
            }
        }
    }

    public static String kP() {
        return d(kO());
    }

    public static long VV() {
        return System.currentTimeMillis();
    }

    public static void a(JSONObject jSONObject, JSONObject jSONObject2) {
        Iterator<String> itKeys = jSONObject2.keys();
        while (itKeys.hasNext()) {
            jSONObject.remove(itKeys.next());
        }
        Iterator<String> itKeys2 = jSONObject2.keys();
        while (itKeys2.hasNext()) {
            String next = itKeys2.next();
            try {
                jSONObject.put(next, jSONObject2.get(next));
            } catch (JSONException e) {
            }
        }
    }

    private static boolean lc() {
        return UG != null;
    }

    private static void ld() {
        UG = new int[128];
        for (int i = 0; i < UG.length; i++) {
            UG[i] = -1;
        }
        for (int i2 = 0; i2 < UF.length; i2++) {
            UG[UF[i2]] = i2;
        }
    }

    public static String e(byte[] bArr) {
        return e(bArr, 0, bArr.length);
    }

    public static String e(byte[] bArr, int i, int i2) {
        int i3 = 0;
        if (!lc()) {
            ld();
        }
        char[] cArr = new char[((i2 + 2) / 3) * 4];
        int i4 = 0;
        while (true) {
            int i5 = i3;
            if (i4 < cArr.length) {
                a(bArr, i + i5, i2 - i5, cArr, i4);
                i3 = i5 + 3;
                i4 += 4;
            } else {
                return new String(cArr);
            }
        }
    }

    private static void a(byte[] bArr, int i, int i2, char[] cArr, int i3) {
        byte b = bArr[i];
        cArr[i3] = UF[(b >>> 2) & 63];
        if (i2 > 2) {
            byte b2 = bArr[i + 1];
            byte b3 = bArr[i + 2];
            cArr[i3 + 1] = UF[((b << 4) & 48) + ((b2 >>> 4) & 15)];
            cArr[i3 + 2] = UF[((b2 << 2) & 60) + ((b3 >>> 6) & 3)];
            cArr[i3 + 3] = UF[b3 & 63];
            return;
        }
        if (i2 > 1) {
            byte b4 = bArr[i + 1];
            cArr[i3 + 1] = UF[((b << 4) & 48) + ((b4 >>> 4) & 15)];
            cArr[i3 + 2] = UF[((b4 << 2) & 60) + 0];
            cArr[i3 + 3] = '=';
            return;
        }
        cArr[i3 + 1] = UF[((b << 4) & 48) + 0];
        cArr[i3 + 2] = '=';
        cArr[i3 + 3] = '=';
    }

    public static byte[] bJ(String str) {
        return e(str, 0, str.length());
    }

    public static byte[] e(String str, int i, int i2) {
        if (!lc()) {
            ld();
        }
        int i3 = (i2 / 4) * 3;
        if (str.charAt((i + i2) - 1) == '=') {
            i3--;
            if (str.charAt((i + i2) - 2) == '=') {
                i3--;
            }
        }
        byte[] bArr = new byte[i3];
        int i4 = 0;
        for (int i5 = 0; i5 < i2; i5 += 4) {
            a(str.charAt(i + i5), str.charAt(i + i5 + 1), str.charAt(i + i5 + 2), str.charAt(i + i5 + 3), bArr, i4);
            i4 += 3;
        }
        return bArr;
    }

    private static void a(char c, char c2, char c3, char c4, byte[] bArr, int i) {
        int i2;
        int i3;
        char c5;
        int i4 = UG[c & 127];
        int i5 = UG[c2 & 127];
        if (c4 == '=') {
            if (c3 == '=') {
                c5 = 2;
                i3 = 0;
                i2 = 0;
            } else {
                i2 = UG[c3 & 127];
                c5 = 1;
                i3 = 0;
            }
        } else {
            i2 = UG[c3 & 127];
            i3 = UG[c4 & 127];
            c5 = 0;
        }
        bArr[i] = (byte) (((i4 << 2) & 252) | ((i5 >>> 4) & 3));
        if (c5 < 2) {
            bArr[i + 1] = (byte) (((i5 << 4) & 240) | ((i2 >>> 2) & 15));
            if (c5 < 1) {
                bArr[i + 2] = (byte) (((i2 << 6) & 192) | (i3 & 63));
            }
        }
    }

    public static JSONObject[] d(JSONObject jSONObject, String str) throws JSONException {
        JSONArray jSONArray = jSONObject.getJSONArray(str);
        JSONObject[] jSONObjectArr = new JSONObject[jSONArray.length()];
        for (int i = 0; i < jSONObjectArr.length; i++) {
            jSONObjectArr[i] = jSONArray.getJSONObject(i);
        }
        return jSONObjectArr;
    }

    public static String[] e(JSONObject jSONObject, String str) throws Exception {
        Object obj = jSONObject.get(str);
        if (obj == null) {
            throw new Exception("Key not found: " + str);
        }
        if (!(obj instanceof JSONArray)) {
            throw new Exception("There is no array for this key: " + str);
        }
        JSONArray jSONArray = (JSONArray) obj;
        String[] strArr = new String[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            strArr[i] = jSONArray.getString(i);
        }
        return strArr;
    }

    public static byte[] hl(String str) {
        try {
            File file = new File(au.Pq() + "/" + str);
            byte[] bArr = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bArr);
            fileInputStream.close();
            return bArr;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean f(String str, byte[] bArr) {
        String str2 = au.Pq() + "/" + str;
        try {
            File file = new File(str2);
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
                file.createNewFile();
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(str2);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                bufferedOutputStream.write(bArr);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                fileOutputStream.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e2) {
            return false;
        }
    }

    public static void ap(String str, String str2) {
        au.Z(au.Pq() + "/" + str, str2);
    }

    public static String hm(String str) {
        return au.ep(au.Pq() + "/" + str);
    }
}
