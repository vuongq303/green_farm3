package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.facebook.R;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.be;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.server.converter.ConverterWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/* JADX INFO: loaded from: classes.dex */
public abstract class FastJsonResponse {
    protected static final String QUOTE = "\"";
    private int zzwk;
    private byte[] zzwl;
    private boolean zzwm;

    public class Field<I, O> extends AbstractSafeParcelable {
        public static final e CREATOR = new e();
        protected final int bOX;
        protected final boolean bOY;
        protected final int bOZ;
        protected final boolean bPa;
        protected final String bPb;
        protected final int bPc;
        protected final Class<? extends FastJsonResponse> bPd;
        protected final String bPe;
        private FieldMappingDictionary bPf;
        private a<I, O> bPg;
        private final int zzal;

        Field(int i, int i2, boolean z, int i3, boolean z2, String str, int i4, String str2, ConverterWrapper converterWrapper) {
            this.zzal = i;
            this.bOX = i2;
            this.bOY = z;
            this.bOZ = i3;
            this.bPa = z2;
            this.bPb = str;
            this.bPc = i4;
            if (str2 == null) {
                this.bPd = null;
                this.bPe = null;
            } else {
                this.bPd = SafeParcelResponse.class;
                this.bPe = str2;
            }
            if (converterWrapper == null) {
                this.bPg = null;
            } else {
                this.bPg = (a<I, O>) converterWrapper.aaI();
            }
        }

        protected Field(int i, boolean z, int i2, boolean z2, String str, int i3, Class<? extends FastJsonResponse> cls, a<I, O> aVar) {
            this.zzal = 1;
            this.bOX = i;
            this.bOY = z;
            this.bOZ = i2;
            this.bPa = z2;
            this.bPb = str;
            this.bPc = i3;
            this.bPd = cls;
            if (cls == null) {
                this.bPe = null;
            } else {
                this.bPe = cls.getCanonicalName();
            }
            this.bPg = aVar;
        }

        public static Field<Integer, Integer> L(String str, int i) {
            return new Field<>(0, false, 0, false, str, i, null, null);
        }

        public static Field<Boolean, Boolean> M(String str, int i) {
            return new Field<>(6, false, 6, false, str, i, null, null);
        }

        public static Field<String, String> N(String str, int i) {
            return new Field<>(7, false, 7, false, str, i, null, null);
        }

        public static Field<ArrayList<String>, ArrayList<String>> O(String str, int i) {
            return new Field<>(7, true, 7, true, str, i, null, null);
        }

        public static Field<byte[], byte[]> P(String str, int i) {
            return new Field<>(8, false, 8, false, str, i, null, null);
        }

        public static Field a(String str, int i, a<?, ?> aVar, boolean z) {
            return new Field(aVar.aaJ(), z, aVar.aaK(), false, str, i, null, aVar);
        }

        public static <T extends FastJsonResponse> Field<T, T> a(String str, int i, Class<T> cls) {
            return new Field<>(11, false, 11, false, str, i, cls, null);
        }

        private final ConverterWrapper aaS() {
            if (this.bPg == null) {
                return null;
            }
            return ConverterWrapper.a(this.bPg);
        }

        public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> b(String str, int i, Class<T> cls) {
            return new Field<>(11, true, 11, true, str, i, cls, null);
        }

        private final String zzcz() {
            if (this.bPe == null) {
                return null;
            }
            return this.bPe;
        }

        public void a(FieldMappingDictionary fieldMappingDictionary) {
            this.bPf = fieldMappingDictionary;
        }

        public int aaJ() {
            return this.bOX;
        }

        public int aaK() {
            return this.bOZ;
        }

        public boolean aaL() {
            return this.bOY;
        }

        public boolean aaM() {
            return this.bPa;
        }

        public String aaN() {
            return this.bPb;
        }

        public int aaO() {
            return this.bPc;
        }

        public boolean aaP() {
            return this.bPc != -1;
        }

        public Class<? extends FastJsonResponse> aaQ() {
            return this.bPd;
        }

        public boolean aaR() {
            return this.bPg != null;
        }

        public FastJsonResponse aaT() {
            if (this.bPd != SafeParcelResponse.class) {
                return this.bPd.newInstance();
            }
            bf.d(this.bPf, "The field mapping dictionary must be set if the concrete type is a SafeParcelResponse object.");
            return new SafeParcelResponse(this.bPf, this.bPe);
        }

        public Map<String, Field<?, ?>> aaU() {
            bf.checkNotNull(this.bPe);
            bf.checkNotNull(this.bPf);
            return this.bPf.m6if(this.bPe);
        }

        public O convert(I i) {
            return this.bPg.convert(i);
        }

        public I convertBack(O o) {
            return this.bPg.convertBack(o);
        }

        public int getVersionCode() {
            return this.zzal;
        }

        public String toString() {
            be beVarC = bd.C(this).c("versionCode", Integer.valueOf(this.zzal)).c("typeIn", Integer.valueOf(this.bOX)).c("typeInArray", Boolean.valueOf(this.bOY)).c("typeOut", Integer.valueOf(this.bOZ)).c("typeOutArray", Boolean.valueOf(this.bPa)).c("outputFieldName", this.bPb).c("safeParcelFieldId", Integer.valueOf(this.bPc)).c("concreteTypeName", zzcz());
            Class<? extends FastJsonResponse> clsAaQ = aaQ();
            if (clsAaQ != null) {
                beVarC.c("concreteType.class", clsAaQ.getCanonicalName());
            }
            if (this.bPg != null) {
                beVarC.c("converterName", this.bPg.getClass().getCanonicalName());
            }
            return beVarC.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, getVersionCode());
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, aaJ());
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, aaL());
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 4, aaK());
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, aaM());
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, aaN(), false);
            com.google.android.gms.common.internal.safeparcel.c.c(parcel, 7, aaO());
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, zzcz(), false);
            com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, (Parcelable) aaS(), i, false);
            com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
        }
    }

    public static InputStream getUnzippedStream(byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        if (com.google.android.gms.common.d.o.s(bArr)) {
            try {
                return new GZIPInputStream(byteArrayInputStream);
            } catch (IOException e) {
            }
        }
        return byteArrayInputStream;
    }

    private final <I, O> void zza(Field<I, O> field, I i) {
        String strAaN = field.aaN();
        O oConvert = field.convert(i);
        switch (field.aaK()) {
            case 0:
                if (zzb(strAaN, oConvert)) {
                    setIntegerInternal(field, strAaN, ((Integer) oConvert).intValue());
                    return;
                }
                return;
            case 1:
                setBigIntegerInternal(field, strAaN, (BigInteger) oConvert);
                return;
            case 2:
                if (zzb(strAaN, oConvert)) {
                    setLongInternal(field, strAaN, ((Long) oConvert).longValue());
                    return;
                }
                return;
            case 3:
            default:
                throw new IllegalStateException(new StringBuilder(44).append("Unsupported type for conversion: ").append(field.aaK()).toString());
            case 4:
                if (zzb(strAaN, oConvert)) {
                    setDoubleInternal(field, strAaN, ((Double) oConvert).doubleValue());
                    return;
                }
                return;
            case 5:
                setBigDecimalInternal(field, strAaN, (BigDecimal) oConvert);
                return;
            case 6:
                if (zzb(strAaN, oConvert)) {
                    setBooleanInternal(field, strAaN, ((Boolean) oConvert).booleanValue());
                    return;
                }
                return;
            case 7:
                setStringInternal(field, strAaN, (String) oConvert);
                return;
            case 8:
            case 9:
                if (zzb(strAaN, oConvert)) {
                    setDecodedBytesInternal(field, strAaN, (byte[]) oConvert);
                    return;
                }
                return;
        }
    }

    private static void zza(StringBuilder sb, Field field, Object obj) {
        if (field.aaJ() == 11) {
            sb.append(field.aaQ().cast(obj).toString());
        } else {
            if (field.aaJ() != 7) {
                sb.append(obj);
                return;
            }
            sb.append(QUOTE);
            sb.append(com.google.android.gms.common.d.p.ij((String) obj));
            sb.append(QUOTE);
        }
    }

    private static <O> boolean zzb(String str, O o) {
        if (o != null) {
            return true;
        }
        if (Log.isLoggable("FastJsonResponse", 6)) {
            Log.e("FastJsonResponse", new StringBuilder(String.valueOf(str).length() + 58).append("Output field (").append(str).append(") has a null value, but expected a primitive").toString());
        }
        return false;
    }

    public <T extends FastJsonResponse> void addConcreteType(String str, T t) {
        throw new UnsupportedOperationException("Concrete type not supported");
    }

    public <T extends FastJsonResponse> void addConcreteTypeArray(String str, ArrayList<T> arrayList) {
        throw new UnsupportedOperationException("Concrete type array not supported");
    }

    public <T extends FastJsonResponse> void addConcreteTypeArrayInternal(Field<?, ?> field, String str, ArrayList<T> arrayList) {
        addConcreteTypeArray(str, arrayList);
    }

    public <T extends FastJsonResponse> void addConcreteTypeInternal(Field<?, ?> field, String str, T t) {
        addConcreteType(str, t);
    }

    public HashMap<String, Object> getConcreteTypeArrays() {
        return null;
    }

    public HashMap<String, Object> getConcreteTypes() {
        return null;
    }

    public abstract Map<String, Field<?, ?>> getFieldMappings();

    protected Object getFieldValue(Field field) {
        String strAaN = field.aaN();
        if (field.aaQ() == null) {
            return getValueObject(field.aaN());
        }
        bf.a(getValueObject(field.aaN()) == null, "Concrete field shouldn't be value object: %s", field.aaN());
        HashMap<String, Object> concreteTypeArrays = field.aaM() ? getConcreteTypeArrays() : getConcreteTypes();
        if (concreteTypeArrays != null) {
            return concreteTypeArrays.get(strAaN);
        }
        try {
            char upperCase = Character.toUpperCase(strAaN.charAt(0));
            String strSubstring = strAaN.substring(1);
            return getClass().getMethod(new StringBuilder(String.valueOf(strSubstring).length() + 4).append("get").append(upperCase).append(strSubstring).toString(), new Class[0]).invoke(this, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <O, I> I getOriginalValue(Field<I, O> field, Object obj) {
        return ((Field) field).bPg != null ? field.convertBack(obj) : obj;
    }

    public i<? extends FastJsonResponse> getPostProcessor() {
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:40:0x0045 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:45:? A[SYNTHETIC] */
    public byte[] getResponseBody() throws Throwable {
        Throwable th;
        GZIPInputStream gZIPInputStream;
        GZIPInputStream gZIPInputStream2;
        byte[] byteArray;
        bf.bZ(this.zzwm);
        try {
            gZIPInputStream2 = new GZIPInputStream(new ByteArrayInputStream(this.zzwl));
            try {
                try {
                    byte[] bArr = new byte[4096];
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    while (true) {
                        int i = gZIPInputStream2.read(bArr, 0, 4096);
                        if (i == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, i);
                    }
                    byteArrayOutputStream.flush();
                    byteArray = byteArrayOutputStream.toByteArray();
                    try {
                        gZIPInputStream2.close();
                    } catch (IOException e) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    gZIPInputStream = gZIPInputStream2;
                    if (gZIPInputStream != null) {
                        throw th;
                    }
                    try {
                        gZIPInputStream.close();
                        throw th;
                    } catch (IOException e2) {
                        throw th;
                    }
                }
            } catch (IOException e3) {
                byteArray = this.zzwl;
                if (gZIPInputStream2 != null) {
                    try {
                        gZIPInputStream2.close();
                    } catch (IOException e4) {
                    }
                }
            }
        } catch (IOException e5) {
            gZIPInputStream2 = null;
        } catch (Throwable th3) {
            th = th3;
            gZIPInputStream = null;
            if (gZIPInputStream != null) {
                throw th;
            }
            gZIPInputStream.close();
            throw th;
        }
        return byteArray;
    }

    public int getResponseCode() {
        bf.bZ(this.zzwm);
        return this.zzwk;
    }

    protected abstract Object getValueObject(String str);

    protected boolean isConcreteTypeArrayFieldSet(String str) {
        throw new UnsupportedOperationException("Concrete type arrays not supported");
    }

    protected boolean isConcreteTypeFieldSet(String str) {
        throw new UnsupportedOperationException("Concrete types not supported");
    }

    protected boolean isFieldSet(Field field) {
        if (field.aaK() == 11) {
            return field.aaM() ? isConcreteTypeArrayFieldSet(field.aaN()) : isConcreteTypeFieldSet(field.aaN());
        }
        return isPrimitiveFieldSet(field.aaN());
    }

    protected abstract boolean isPrimitiveFieldSet(String str);

    public <T extends FastJsonResponse> void parseNetworkResponse(int i, byte[] bArr) {
        this.zzwk = i;
        this.zzwl = bArr;
        this.zzwm = true;
        InputStream unzippedStream = getUnzippedStream(bArr);
        try {
            new b().a(unzippedStream, this);
        } finally {
            try {
                unzippedStream.close();
            } catch (IOException e) {
            }
        }
    }

    public final <O> void setBigDecimal(Field<BigDecimal, O> field, BigDecimal bigDecimal) {
        if (((Field) field).bPg != null) {
            zza(field, bigDecimal);
        } else {
            setBigDecimalInternal(field, field.aaN(), bigDecimal);
        }
    }

    protected void setBigDecimal(String str, BigDecimal bigDecimal) {
        throw new UnsupportedOperationException("BigDecimal not supported");
    }

    protected void setBigDecimalInternal(Field<?, ?> field, String str, BigDecimal bigDecimal) {
        setBigDecimal(str, bigDecimal);
    }

    public final <O> void setBigDecimals(Field<ArrayList<BigDecimal>, O> field, ArrayList<BigDecimal> arrayList) {
        if (((Field) field).bPg != null) {
            zza(field, arrayList);
        } else {
            setBigDecimalsInternal(field, field.aaN(), arrayList);
        }
    }

    protected void setBigDecimals(String str, ArrayList<BigDecimal> arrayList) {
        throw new UnsupportedOperationException("BigDecimal list not supported");
    }

    protected void setBigDecimalsInternal(Field<?, ?> field, String str, ArrayList<BigDecimal> arrayList) {
        setBigDecimals(str, arrayList);
    }

    public final <O> void setBigInteger(Field<BigInteger, O> field, BigInteger bigInteger) {
        if (((Field) field).bPg != null) {
            zza(field, bigInteger);
        } else {
            setBigIntegerInternal(field, field.aaN(), bigInteger);
        }
    }

    protected void setBigInteger(String str, BigInteger bigInteger) {
        throw new UnsupportedOperationException("BigInteger not supported");
    }

    protected void setBigIntegerInternal(Field<?, ?> field, String str, BigInteger bigInteger) {
        setBigInteger(str, bigInteger);
    }

    public final <O> void setBigIntegers(Field<ArrayList<BigInteger>, O> field, ArrayList<BigInteger> arrayList) {
        if (((Field) field).bPg != null) {
            zza(field, arrayList);
        } else {
            setBigIntegersInternal(field, field.aaN(), arrayList);
        }
    }

    protected void setBigIntegers(String str, ArrayList<BigInteger> arrayList) {
        throw new UnsupportedOperationException("BigInteger list not supported");
    }

    protected void setBigIntegersInternal(Field<?, ?> field, String str, ArrayList<BigInteger> arrayList) {
        setBigIntegers(str, arrayList);
    }

    public final <O> void setBoolean(Field<Boolean, O> field, boolean z) {
        if (((Field) field).bPg != null) {
            zza(field, Boolean.valueOf(z));
        } else {
            setBooleanInternal(field, field.aaN(), z);
        }
    }

    protected void setBoolean(String str, boolean z) {
        throw new UnsupportedOperationException("Boolean not supported");
    }

    protected void setBooleanInternal(Field<?, ?> field, String str, boolean z) {
        setBoolean(str, z);
    }

    public final <O> void setBooleans(Field<ArrayList<Boolean>, O> field, ArrayList<Boolean> arrayList) {
        if (((Field) field).bPg != null) {
            zza(field, arrayList);
        } else {
            setBooleansInternal(field, field.aaN(), arrayList);
        }
    }

    protected void setBooleans(String str, ArrayList<Boolean> arrayList) {
        throw new UnsupportedOperationException("Boolean list not supported");
    }

    protected void setBooleansInternal(Field<?, ?> field, String str, ArrayList<Boolean> arrayList) {
        setBooleans(str, arrayList);
    }

    public final <O> void setDecodedBytes(Field<byte[], O> field, byte[] bArr) {
        if (((Field) field).bPg != null) {
            zza(field, bArr);
        } else {
            setDecodedBytesInternal(field, field.aaN(), bArr);
        }
    }

    protected void setDecodedBytes(String str, byte[] bArr) {
        throw new UnsupportedOperationException("byte[] not supported");
    }

    protected void setDecodedBytesInternal(Field<?, ?> field, String str, byte[] bArr) {
        setDecodedBytes(str, bArr);
    }

    public final <O> void setDouble(Field<Double, O> field, double d) {
        if (((Field) field).bPg != null) {
            zza(field, Double.valueOf(d));
        } else {
            setDoubleInternal(field, field.aaN(), d);
        }
    }

    protected void setDouble(String str, double d) {
        throw new UnsupportedOperationException("Double not supported");
    }

    protected void setDoubleInternal(Field<?, ?> field, String str, double d) {
        setDouble(str, d);
    }

    public final <O> void setDoubles(Field<ArrayList<Double>, O> field, ArrayList<Double> arrayList) {
        if (((Field) field).bPg != null) {
            zza(field, arrayList);
        } else {
            setDoublesInternal(field, field.aaN(), arrayList);
        }
    }

    protected void setDoubles(String str, ArrayList<Double> arrayList) {
        throw new UnsupportedOperationException("Double list not supported");
    }

    protected void setDoublesInternal(Field<?, ?> field, String str, ArrayList<Double> arrayList) {
        setDoubles(str, arrayList);
    }

    public final <O> void setFloat(Field<Float, O> field, float f) {
        if (((Field) field).bPg != null) {
            zza(field, Float.valueOf(f));
        } else {
            setFloatInternal(field, field.aaN(), f);
        }
    }

    protected void setFloat(String str, float f) {
        throw new UnsupportedOperationException("Float not supported");
    }

    protected void setFloatInternal(Field<?, ?> field, String str, float f) {
        setFloat(str, f);
    }

    public final <O> void setFloats(Field<ArrayList<Float>, O> field, ArrayList<Float> arrayList) {
        if (((Field) field).bPg != null) {
            zza(field, arrayList);
        } else {
            setFloatsInternal(field, field.aaN(), arrayList);
        }
    }

    protected void setFloats(String str, ArrayList<Float> arrayList) {
        throw new UnsupportedOperationException("Float list not supported");
    }

    protected void setFloatsInternal(Field<?, ?> field, String str, ArrayList<Float> arrayList) {
        setFloats(str, arrayList);
    }

    public final <O> void setInteger(Field<Integer, O> field, int i) {
        if (((Field) field).bPg != null) {
            zza(field, Integer.valueOf(i));
        } else {
            setIntegerInternal(field, field.aaN(), i);
        }
    }

    protected void setInteger(String str, int i) {
        throw new UnsupportedOperationException("Integer not supported");
    }

    protected void setIntegerInternal(Field<?, ?> field, String str, int i) {
        setInteger(str, i);
    }

    public final <O> void setIntegers(Field<ArrayList<Integer>, O> field, ArrayList<Integer> arrayList) {
        if (((Field) field).bPg != null) {
            zza(field, arrayList);
        } else {
            setIntegersInternal(field, field.aaN(), arrayList);
        }
    }

    protected void setIntegers(String str, ArrayList<Integer> arrayList) {
        throw new UnsupportedOperationException("Integer list not supported");
    }

    protected void setIntegersInternal(Field<?, ?> field, String str, ArrayList<Integer> arrayList) {
        setIntegers(str, arrayList);
    }

    public final <O> void setLong(Field<Long, O> field, long j) {
        if (((Field) field).bPg != null) {
            zza(field, Long.valueOf(j));
        } else {
            setLongInternal(field, field.aaN(), j);
        }
    }

    protected void setLong(String str, long j) {
        throw new UnsupportedOperationException("Long not supported");
    }

    protected void setLongInternal(Field<?, ?> field, String str, long j) {
        setLong(str, j);
    }

    public final <O> void setLongs(Field<ArrayList<Long>, O> field, ArrayList<Long> arrayList) {
        if (((Field) field).bPg != null) {
            zza(field, arrayList);
        } else {
            setLongsInternal(field, field.aaN(), arrayList);
        }
    }

    protected void setLongs(String str, ArrayList<Long> arrayList) {
        throw new UnsupportedOperationException("Long list not supported");
    }

    protected void setLongsInternal(Field<?, ?> field, String str, ArrayList<Long> arrayList) {
        setLongs(str, arrayList);
    }

    public final <O> void setString(Field<String, O> field, String str) {
        if (((Field) field).bPg != null) {
            zza(field, str);
        } else {
            setStringInternal(field, field.aaN(), str);
        }
    }

    protected void setString(String str, String str2) {
        throw new UnsupportedOperationException("String not supported");
    }

    protected void setStringInternal(Field<?, ?> field, String str, String str2) {
        setString(str, str2);
    }

    public final <O> void setStringMap(Field<Map<String, String>, O> field, Map<String, String> map) {
        if (((Field) field).bPg != null) {
            zza(field, map);
        } else {
            setStringMapInternal(field, field.aaN(), map);
        }
    }

    protected void setStringMap(String str, Map<String, String> map) {
        throw new UnsupportedOperationException("String map not supported");
    }

    protected void setStringMapInternal(Field<?, ?> field, String str, Map<String, String> map) {
        setStringMap(str, map);
    }

    public final <O> void setStrings(Field<ArrayList<String>, O> field, ArrayList<String> arrayList) {
        if (((Field) field).bPg != null) {
            zza(field, arrayList);
        } else {
            setStringsInternal(field, field.aaN(), arrayList);
        }
    }

    protected void setStrings(String str, ArrayList<String> arrayList) {
        throw new UnsupportedOperationException("String list not supported");
    }

    protected void setStringsInternal(Field<?, ?> field, String str, ArrayList<String> arrayList) {
        setStrings(str, arrayList);
    }

    public String toString() {
        Map<String, Field<?, ?>> fieldMappings = getFieldMappings();
        StringBuilder sb = new StringBuilder(100);
        for (String str : fieldMappings.keySet()) {
            Field<?, ?> field = fieldMappings.get(str);
            if (isFieldSet(field)) {
                Object originalValue = getOriginalValue(field, getFieldValue(field));
                if (sb.length() == 0) {
                    sb.append("{");
                } else {
                    sb.append(",");
                }
                sb.append(QUOTE).append(str).append("\":");
                if (originalValue != null) {
                    switch (field.aaK()) {
                        case 8:
                            sb.append(QUOTE).append(com.google.android.gms.common.d.c.o((byte[]) originalValue)).append(QUOTE);
                            break;
                        case 9:
                            sb.append(QUOTE).append(com.google.android.gms.common.d.c.p((byte[]) originalValue)).append(QUOTE);
                            break;
                        case R.styleable.CardView_contentPaddingRight /* 10 */:
                            com.google.android.gms.common.d.q.a(sb, (HashMap) originalValue);
                            break;
                        default:
                            if (field.aaL()) {
                                ArrayList arrayList = (ArrayList) originalValue;
                                sb.append("[");
                                int size = arrayList.size();
                                for (int i = 0; i < size; i++) {
                                    if (i > 0) {
                                        sb.append(",");
                                    }
                                    Object obj = arrayList.get(i);
                                    if (obj != null) {
                                        zza(sb, field, obj);
                                    }
                                }
                                sb.append("]");
                            } else {
                                zza(sb, field, originalValue);
                            }
                            break;
                    }
                } else {
                    sb.append("null");
                }
            }
        }
        if (sb.length() > 0) {
            sb.append("}");
        } else {
            sb.append("{}");
        }
        return sb.toString();
    }
}
