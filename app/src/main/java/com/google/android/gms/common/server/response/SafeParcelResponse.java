package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.facebook.R;
import com.google.android.gms.common.internal.bf;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class SafeParcelResponse extends FastSafeParcelableJsonResponse {
    public static final Parcelable.Creator<SafeParcelResponse> CREATOR = new j();
    private final String bP;
    private final Parcel bPH;
    private final int bPI;
    private int bPJ;
    private int bPK;
    private final FieldMappingDictionary bPf;
    private final int zzal;

    SafeParcelResponse(int i, Parcel parcel, FieldMappingDictionary fieldMappingDictionary) {
        this.zzal = i;
        this.bPH = (Parcel) bf.checkNotNull(parcel);
        this.bPI = 2;
        this.bPf = fieldMappingDictionary;
        if (this.bPf == null) {
            this.bP = null;
        } else {
            this.bP = this.bPf.aaW();
        }
        this.bPJ = 2;
    }

    public SafeParcelResponse(FieldMappingDictionary fieldMappingDictionary, String str) {
        this.zzal = 1;
        this.bPH = Parcel.obtain();
        this.bPI = 0;
        this.bPf = (FieldMappingDictionary) bf.checkNotNull(fieldMappingDictionary);
        this.bP = (String) bf.checkNotNull(str);
        this.bPJ = 0;
    }

    private static void a(StringBuilder sb, int i, Object obj) {
        switch (i) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                sb.append(obj);
                return;
            case 7:
                sb.append("\"").append(com.google.android.gms.common.d.p.ij(obj.toString())).append("\"");
                return;
            case 8:
                sb.append("\"").append(com.google.android.gms.common.d.c.o((byte[]) obj)).append("\"");
                return;
            case 9:
                sb.append("\"").append(com.google.android.gms.common.d.c.p((byte[]) obj));
                sb.append("\"");
                return;
            case R.styleable.CardView_contentPaddingRight /* 10 */:
                com.google.android.gms.common.d.q.a(sb, (HashMap) obj);
                return;
            case R.styleable.CardView_contentPaddingTop /* 11 */:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException(new StringBuilder(26).append("Unknown type = ").append(i).toString());
        }
    }

    private final void a(StringBuilder sb, FastJsonResponse.Field<?, ?> field, Object obj) {
        if (!field.aaL()) {
            a(sb, field.aaJ(), obj);
            return;
        }
        ArrayList arrayList = (ArrayList) obj;
        sb.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                sb.append(",");
            }
            a(sb, field.aaJ(), arrayList.get(i));
        }
        sb.append("]");
    }

    private final void a(StringBuilder sb, Map<String, FastJsonResponse.Field<?, ?>> map, Parcel parcel) {
        SparseArray sparseArray = new SparseArray();
        for (Map.Entry<String, FastJsonResponse.Field<?, ?>> entry : map.entrySet()) {
            sparseArray.put(entry.getValue().aaO(), entry);
        }
        sb.append('{');
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        boolean z = false;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            Map.Entry entry2 = (Map.Entry) sparseArray.get(com.google.android.gms.common.internal.safeparcel.a.nT(iV));
            if (entry2 != null) {
                if (z) {
                    sb.append(",");
                }
                String str = (String) entry2.getKey();
                FastJsonResponse.Field<?, ?> field = (FastJsonResponse.Field) entry2.getValue();
                sb.append("\"").append(str).append("\":");
                if (field.aaR()) {
                    switch (field.aaK()) {
                        case 0:
                            a(sb, field, getOriginalValue(field, Integer.valueOf(com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV))));
                            break;
                        case 1:
                            a(sb, field, getOriginalValue(field, com.google.android.gms.common.internal.safeparcel.a.h(parcel, iV)));
                            break;
                        case 2:
                            a(sb, field, getOriginalValue(field, Long.valueOf(com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV))));
                            break;
                        case 3:
                            a(sb, field, getOriginalValue(field, Float.valueOf(com.google.android.gms.common.internal.safeparcel.a.i(parcel, iV))));
                            break;
                        case 4:
                            a(sb, field, getOriginalValue(field, Double.valueOf(com.google.android.gms.common.internal.safeparcel.a.k(parcel, iV))));
                            break;
                        case 5:
                            a(sb, field, getOriginalValue(field, com.google.android.gms.common.internal.safeparcel.a.m(parcel, iV)));
                            break;
                        case 6:
                            a(sb, field, getOriginalValue(field, Boolean.valueOf(com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV))));
                            break;
                        case 7:
                            a(sb, field, getOriginalValue(field, com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV)));
                            break;
                        case 8:
                        case 9:
                            a(sb, field, getOriginalValue(field, com.google.android.gms.common.internal.safeparcel.a.q(parcel, iV)));
                            break;
                        case R.styleable.CardView_contentPaddingRight /* 10 */:
                            a(sb, field, getOriginalValue(field, x(com.google.android.gms.common.internal.safeparcel.a.p(parcel, iV))));
                            break;
                        case R.styleable.CardView_contentPaddingTop /* 11 */:
                            throw new IllegalArgumentException("Method does not accept concrete type.");
                        default:
                            throw new IllegalArgumentException(new StringBuilder(36).append("Unknown field out type = ").append(field.aaK()).toString());
                    }
                } else if (field.aaM()) {
                    sb.append("[");
                    switch (field.aaK()) {
                        case 0:
                            com.google.android.gms.common.d.b.a(sb, com.google.android.gms.common.internal.safeparcel.a.s(parcel, iV));
                            break;
                        case 1:
                            com.google.android.gms.common.d.b.a(sb, com.google.android.gms.common.internal.safeparcel.a.u(parcel, iV));
                            break;
                        case 2:
                            com.google.android.gms.common.d.b.a(sb, com.google.android.gms.common.internal.safeparcel.a.t(parcel, iV));
                            break;
                        case 3:
                            com.google.android.gms.common.d.b.a(sb, com.google.android.gms.common.internal.safeparcel.a.v(parcel, iV));
                            break;
                        case 4:
                            com.google.android.gms.common.d.b.a(sb, com.google.android.gms.common.internal.safeparcel.a.w(parcel, iV));
                            break;
                        case 5:
                            com.google.android.gms.common.d.b.a(sb, com.google.android.gms.common.internal.safeparcel.a.x(parcel, iV));
                            break;
                        case 6:
                            com.google.android.gms.common.d.b.a(sb, com.google.android.gms.common.internal.safeparcel.a.r(parcel, iV));
                            break;
                        case 7:
                            com.google.android.gms.common.d.b.a(sb, com.google.android.gms.common.internal.safeparcel.a.y(parcel, iV));
                            break;
                        case 8:
                        case 9:
                        case R.styleable.CardView_contentPaddingRight /* 10 */:
                            throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                        case R.styleable.CardView_contentPaddingTop /* 11 */:
                            Parcel[] parcelArrC = com.google.android.gms.common.internal.safeparcel.a.C(parcel, iV);
                            int length = parcelArrC.length;
                            for (int i = 0; i < length; i++) {
                                if (i > 0) {
                                    sb.append(",");
                                }
                                parcelArrC[i].setDataPosition(0);
                                a(sb, field.aaU(), parcelArrC[i]);
                            }
                            break;
                        default:
                            throw new IllegalStateException("Unknown field type out.");
                    }
                    sb.append("]");
                } else {
                    switch (field.aaK()) {
                        case 0:
                            sb.append(com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV));
                            break;
                        case 1:
                            sb.append(com.google.android.gms.common.internal.safeparcel.a.h(parcel, iV));
                            break;
                        case 2:
                            sb.append(com.google.android.gms.common.internal.safeparcel.a.f(parcel, iV));
                            break;
                        case 3:
                            sb.append(com.google.android.gms.common.internal.safeparcel.a.i(parcel, iV));
                            break;
                        case 4:
                            sb.append(com.google.android.gms.common.internal.safeparcel.a.k(parcel, iV));
                            break;
                        case 5:
                            sb.append(com.google.android.gms.common.internal.safeparcel.a.m(parcel, iV));
                            break;
                        case 6:
                            sb.append(com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV));
                            break;
                        case 7:
                            sb.append("\"").append(com.google.android.gms.common.d.p.ij(com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV))).append("\"");
                            break;
                        case 8:
                            sb.append("\"").append(com.google.android.gms.common.d.c.o(com.google.android.gms.common.internal.safeparcel.a.q(parcel, iV))).append("\"");
                            break;
                        case 9:
                            sb.append("\"").append(com.google.android.gms.common.d.c.p(com.google.android.gms.common.internal.safeparcel.a.q(parcel, iV)));
                            sb.append("\"");
                            break;
                        case R.styleable.CardView_contentPaddingRight /* 10 */:
                            Bundle bundleP = com.google.android.gms.common.internal.safeparcel.a.p(parcel, iV);
                            Set<String> setKeySet = bundleP.keySet();
                            setKeySet.size();
                            sb.append("{");
                            boolean z2 = true;
                            for (String str2 : setKeySet) {
                                if (!z2) {
                                    sb.append(",");
                                }
                                sb.append("\"").append(str2).append("\"");
                                sb.append(":");
                                sb.append("\"").append(com.google.android.gms.common.d.p.ij(bundleP.getString(str2))).append("\"");
                                z2 = false;
                            }
                            sb.append("}");
                            break;
                        case R.styleable.CardView_contentPaddingTop /* 11 */:
                            Parcel parcelB = com.google.android.gms.common.internal.safeparcel.a.B(parcel, iV);
                            parcelB.setDataPosition(0);
                            a(sb, field.aaU(), parcelB);
                            break;
                        default:
                            throw new IllegalStateException("Unknown field type out");
                    }
                }
                z = true;
            }
        }
        if (parcel.dataPosition() != iW) {
            throw new com.google.android.gms.common.internal.safeparcel.b(new StringBuilder(37).append("Overread allowed size end=").append(iW).toString(), parcel);
        }
        sb.append('}');
    }

    private final void b(FastJsonResponse.Field<?, ?> field) {
        if (!field.aaP()) {
            throw new IllegalStateException("Field does not have a valid safe parcelable field id.");
        }
        if (this.bPH == null) {
            throw new IllegalStateException("Internal Parcel object is null.");
        }
        switch (this.bPJ) {
            case 0:
                this.bPK = com.google.android.gms.common.internal.safeparcel.c.x(this.bPH);
                this.bPJ = 1;
                return;
            case 1:
                return;
            case 2:
                throw new IllegalStateException("Attempted to parse JSON with a SafeParcelResponse object that is already filled with data.");
            default:
                throw new IllegalStateException("Unknown parse state in SafeParcelResponse.");
        }
    }

    public static HashMap<String, String> x(Bundle bundle) {
        HashMap<String, String> map = new HashMap<>();
        for (String str : bundle.keySet()) {
            map.put(str, bundle.getString(str));
        }
        return map;
    }

    public Parcel aaX() {
        switch (this.bPJ) {
            case 0:
                this.bPK = com.google.android.gms.common.internal.safeparcel.c.x(this.bPH);
            case 1:
                com.google.android.gms.common.internal.safeparcel.c.G(this.bPH, this.bPK);
                this.bPJ = 2;
                break;
        }
        return this.bPH;
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<T> arrayList) {
        b(field);
        ArrayList arrayList2 = new ArrayList();
        arrayList.size();
        ArrayList<T> arrayList3 = arrayList;
        int size = arrayList3.size();
        int i = 0;
        while (i < size) {
            T t = arrayList3.get(i);
            i++;
            arrayList2.add(((SafeParcelResponse) t).aaX());
        }
        com.google.android.gms.common.internal.safeparcel.c.d(this.bPH, field.aaO(), arrayList2, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public <T extends FastJsonResponse> void addConcreteTypeInternal(FastJsonResponse.Field<?, ?> field, String str, T t) {
        b(field);
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), ((SafeParcelResponse) t).aaX(), true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public Map<String, FastJsonResponse.Field<?, ?>> getFieldMappings() {
        if (this.bPf == null) {
            return null;
        }
        return this.bPf.m6if(this.bP);
    }

    @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse, com.google.android.gms.common.server.response.FastJsonResponse
    public Object getValueObject(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public int getVersionCode() {
        return this.zzal;
    }

    @Override // com.google.android.gms.common.server.response.FastSafeParcelableJsonResponse, com.google.android.gms.common.server.response.FastJsonResponse
    public boolean isPrimitiveFieldSet(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setBigDecimalInternal(FastJsonResponse.Field<?, ?> field, String str, BigDecimal bigDecimal) {
        b(field);
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), bigDecimal, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setBigDecimalsInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<BigDecimal> arrayList) {
        b(field);
        int size = arrayList.size();
        BigDecimal[] bigDecimalArr = new BigDecimal[size];
        for (int i = 0; i < size; i++) {
            bigDecimalArr[i] = arrayList.get(i);
        }
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), bigDecimalArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setBigIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, BigInteger bigInteger) {
        b(field);
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), bigInteger, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setBigIntegersInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<BigInteger> arrayList) {
        b(field);
        int size = arrayList.size();
        BigInteger[] bigIntegerArr = new BigInteger[size];
        for (int i = 0; i < size; i++) {
            bigIntegerArr[i] = arrayList.get(i);
        }
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), bigIntegerArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setBooleanInternal(FastJsonResponse.Field<?, ?> field, String str, boolean z) {
        b(field);
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), z);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setBooleansInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Boolean> arrayList) {
        b(field);
        int size = arrayList.size();
        boolean[] zArr = new boolean[size];
        for (int i = 0; i < size; i++) {
            zArr[i] = arrayList.get(i).booleanValue();
        }
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), zArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setDecodedBytesInternal(FastJsonResponse.Field<?, ?> field, String str, byte[] bArr) {
        b(field);
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), bArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setDoubleInternal(FastJsonResponse.Field<?, ?> field, String str, double d) {
        b(field);
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), d);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setDoublesInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Double> arrayList) {
        b(field);
        int size = arrayList.size();
        double[] dArr = new double[size];
        for (int i = 0; i < size; i++) {
            dArr[i] = arrayList.get(i).doubleValue();
        }
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), dArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setFloatInternal(FastJsonResponse.Field<?, ?> field, String str, float f) {
        b(field);
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), f);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setFloatsInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Float> arrayList) {
        b(field);
        int size = arrayList.size();
        float[] fArr = new float[size];
        for (int i = 0; i < size; i++) {
            fArr[i] = arrayList.get(i).floatValue();
        }
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), fArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setIntegerInternal(FastJsonResponse.Field<?, ?> field, String str, int i) {
        b(field);
        com.google.android.gms.common.internal.safeparcel.c.c(this.bPH, field.aaO(), i);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setIntegersInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Integer> arrayList) {
        b(field);
        int size = arrayList.size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            iArr[i] = arrayList.get(i).intValue();
        }
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), iArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setLongInternal(FastJsonResponse.Field<?, ?> field, String str, long j) {
        b(field);
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), j);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setLongsInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<Long> arrayList) {
        b(field);
        int size = arrayList.size();
        long[] jArr = new long[size];
        for (int i = 0; i < size; i++) {
            jArr[i] = arrayList.get(i).longValue();
        }
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), jArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setStringInternal(FastJsonResponse.Field<?, ?> field, String str, String str2) {
        b(field);
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), str2, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setStringMapInternal(FastJsonResponse.Field<?, ?> field, String str, Map<String, String> map) {
        b(field);
        Bundle bundle = new Bundle();
        for (String str2 : map.keySet()) {
            bundle.putString(str2, map.get(str2));
        }
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), bundle, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    protected void setStringsInternal(FastJsonResponse.Field<?, ?> field, String str, ArrayList<String> arrayList) {
        b(field);
        int size = arrayList.size();
        String[] strArr = new String[size];
        for (int i = 0; i < size; i++) {
            strArr[i] = arrayList.get(i);
        }
        com.google.android.gms.common.internal.safeparcel.c.a(this.bPH, field.aaO(), strArr, true);
    }

    @Override // com.google.android.gms.common.server.response.FastJsonResponse
    public String toString() {
        bf.d(this.bPf, "Cannot convert to JSON on client side.");
        Parcel parcelAaX = aaX();
        parcelAaX.setDataPosition(0);
        StringBuilder sb = new StringBuilder(100);
        a(sb, this.bPf.m6if(this.bP), parcelAaX);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        FieldMappingDictionary fieldMappingDictionary;
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, getVersionCode());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, aaX(), false);
        switch (this.bPI) {
            case 0:
                fieldMappingDictionary = null;
                break;
            case 1:
                fieldMappingDictionary = this.bPf;
                break;
            case 2:
                fieldMappingDictionary = this.bPf;
                break;
            default:
                throw new IllegalStateException(new StringBuilder(34).append("Invalid creation type: ").append(this.bPI).toString());
        }
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) fieldMappingDictionary, i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
