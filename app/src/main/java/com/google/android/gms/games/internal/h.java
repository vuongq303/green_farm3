package com.google.android.gms.games.internal;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import java.util.Arrays;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class h {
    public static boolean a(Bundle bundle, Bundle bundle2) {
        boolean z;
        boolean z2;
        if (bundle == bundle2) {
            return true;
        }
        if (bundle == null || bundle2 == null) {
            return false;
        }
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        Set<String> setKeySet = bundle.keySet();
        if (!setKeySet.equals(bundle2.keySet())) {
            return false;
        }
        for (String str : setKeySet) {
            Object obj = bundle.get(str);
            Object obj2 = bundle2.get(str);
            if (obj == null) {
                if (obj2 != null) {
                    return false;
                }
            } else if (obj instanceof Bundle) {
                if (!(obj2 instanceof Bundle) || !a((Bundle) obj, (Bundle) obj2)) {
                    return false;
                }
            } else if (obj instanceof byte[]) {
                if (!(obj2 instanceof byte[]) || !Arrays.equals((byte[]) obj, (byte[]) obj2)) {
                    return false;
                }
            } else if (obj instanceof char[]) {
                if (!(obj2 instanceof char[]) || !Arrays.equals((char[]) obj, (char[]) obj2)) {
                    return false;
                }
            } else if (obj instanceof short[]) {
                if (!(obj2 instanceof short[]) || !Arrays.equals((short[]) obj, (short[]) obj2)) {
                    return false;
                }
            } else if (obj instanceof float[]) {
                if (!(obj2 instanceof float[]) || !Arrays.equals((float[]) obj, (float[]) obj2)) {
                    return false;
                }
            } else if (obj instanceof CharSequence[]) {
                if (!(obj2 instanceof CharSequence[]) || !Arrays.equals((CharSequence[]) obj, (CharSequence[]) obj2)) {
                    return false;
                }
            } else {
                if (obj instanceof Object[]) {
                    if (obj2 instanceof Object[]) {
                        Parcelable[] parcelableArr = (Parcelable[]) obj;
                        Parcelable[] parcelableArr2 = (Parcelable[]) obj2;
                        if (parcelableArr == parcelableArr2) {
                            z = true;
                            break;
                        }
                        int length = parcelableArr.length;
                        if (parcelableArr2.length == length) {
                            int i = 0;
                            while (true) {
                                if (i >= length) {
                                    z = true;
                                    break;
                                }
                                Parcelable parcelable = parcelableArr[i];
                                Parcelable parcelable2 = parcelableArr2[i];
                                if (parcelable == null) {
                                    if (parcelable2 != null) {
                                        z = false;
                                        break;
                                    }
                                    i++;
                                } else if (parcelable instanceof Bundle) {
                                    if (!(parcelable2 instanceof Bundle) || !a((Bundle) parcelable, (Bundle) parcelable2)) {
                                        z = false;
                                        break;
                                    }
                                    i++;
                                } else {
                                    if (!parcelable.equals(parcelable2)) {
                                        z = false;
                                        break;
                                    }
                                    i++;
                                }
                            }
                        } else {
                            z = false;
                        }
                        if (!z) {
                        }
                    }
                    return false;
                }
                if (obj instanceof SparseArray) {
                    if (obj2 instanceof SparseArray) {
                        SparseArray sparseArray = (SparseArray) obj;
                        SparseArray sparseArray2 = (SparseArray) obj2;
                        if (sparseArray == sparseArray2) {
                            z2 = true;
                            break;
                        }
                        int size = sparseArray.size();
                        if (sparseArray2.size() == size) {
                            int i2 = 0;
                            while (true) {
                                if (i2 >= size) {
                                    z2 = true;
                                    break;
                                }
                                if (sparseArray.keyAt(i2) != sparseArray2.keyAt(i2)) {
                                    z2 = false;
                                    break;
                                }
                                Object objValueAt = sparseArray.valueAt(i2);
                                Object objValueAt2 = sparseArray2.valueAt(i2);
                                if (objValueAt == null) {
                                    if (objValueAt2 != null) {
                                        z2 = false;
                                        break;
                                    }
                                    i2++;
                                } else if (objValueAt instanceof Bundle) {
                                    if (!(objValueAt2 instanceof Bundle) || !a((Bundle) objValueAt, (Bundle) objValueAt2)) {
                                        z2 = false;
                                        break;
                                    }
                                    i2++;
                                } else {
                                    if (!objValueAt.equals(objValueAt2)) {
                                        z2 = false;
                                        break;
                                    }
                                    i2++;
                                }
                            }
                        } else {
                            z2 = false;
                        }
                        if (!z2) {
                        }
                    }
                    return false;
                }
                if (!obj.equals(obj2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int y(Bundle bundle) {
        int size;
        if (bundle != null && (size = bundle.size()) != 0) {
            String[] strArr = (String[]) bundle.keySet().toArray(new String[size]);
            Arrays.sort(strArr);
            int iHashCode = 1;
            for (String str : strArr) {
                int i = iHashCode * 31;
                Object obj = bundle.get(str);
                if (obj == null) {
                    iHashCode = i;
                } else if (obj instanceof Bundle) {
                    iHashCode = y((Bundle) obj) + i;
                } else if (obj instanceof byte[]) {
                    iHashCode = Arrays.hashCode((byte[]) obj) + i;
                } else if (obj instanceof char[]) {
                    iHashCode = Arrays.hashCode((char[]) obj) + i;
                } else if (obj instanceof short[]) {
                    iHashCode = Arrays.hashCode((short[]) obj) + i;
                } else if (obj instanceof float[]) {
                    iHashCode = Arrays.hashCode((float[]) obj) + i;
                } else if (obj instanceof CharSequence[]) {
                    iHashCode = Arrays.hashCode((CharSequence[]) obj) + i;
                } else if (obj instanceof Object[]) {
                    Object[] objArr = (Object[]) obj;
                    int length = objArr.length;
                    int i2 = 0;
                    int iY = 1;
                    while (i2 < length) {
                        Object obj2 = objArr[i2];
                        int i3 = iY * 31;
                        i2++;
                        iY = obj2 instanceof Bundle ? y((Bundle) obj2) + i3 : obj2 != null ? obj2.hashCode() + i3 : i3;
                    }
                    iHashCode = i + iY;
                } else if (obj instanceof SparseArray) {
                    SparseArray sparseArray = (SparseArray) obj;
                    int size2 = sparseArray.size();
                    int iY2 = 1;
                    for (int i4 = 0; i4 < size2; i4++) {
                        int iKeyAt = ((iY2 * 31) + sparseArray.keyAt(i4)) * 31;
                        Object objValueAt = sparseArray.valueAt(i4);
                        iY2 = objValueAt instanceof Bundle ? y((Bundle) objValueAt) + iKeyAt : objValueAt != null ? objValueAt.hashCode() + iKeyAt : iKeyAt;
                    }
                    iHashCode = i + iY2;
                } else {
                    iHashCode = obj.hashCode() + i;
                }
            }
            return iHashCode;
        }
        return 0;
    }
}
