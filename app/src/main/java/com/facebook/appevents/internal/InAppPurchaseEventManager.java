package com.facebook.appevents.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import com.facebook.FacebookSdk;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
class InAppPurchaseEventManager {
    private static final String AS_INTERFACE = "asInterface";
    private static final int CACHE_CLEAR_TIME_LIMIT_SEC = 604800;
    private static final String DETAILS_LIST = "DETAILS_LIST";
    private static final String GET_PURCHASES = "getPurchases";
    private static final String GET_PURCHASE_HISTORY = "getPurchaseHistory";
    private static final String GET_SKU_DETAILS = "getSkuDetails";
    private static final String INAPP = "inapp";
    private static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";
    private static final String INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";
    private static final String IN_APP_BILLING_SERVICE = "com.android.vending.billing.IInAppBillingService";
    private static final String IN_APP_BILLING_SERVICE_STUB = "com.android.vending.billing.IInAppBillingService$Stub";
    private static final String IS_BILLING_SUPPORTED = "isBillingSupported";
    private static final String ITEM_ID_LIST = "ITEM_ID_LIST";
    private static final String LAST_CLEARED_TIME = "LAST_CLEARED_TIME";
    private static final int MAX_QUERY_PURCHASE_NUM = 30;
    private static final int PURCHASE_EXPIRE_TIME_SEC = 86400;
    private static final int PURCHASE_STOP_QUERY_TIME_SEC = 1200;
    private static final String RESPONSE_CODE = "RESPONSE_CODE";
    private static final int SKU_DETAIL_EXPIRE_TIME_SEC = 43200;
    private static final String SUBSCRIPTION = "subs";
    private static final String TAG = InAppPurchaseEventManager.class.getCanonicalName();
    private static final HashMap<String, Method> methodMap = new HashMap<>();
    private static final HashMap<String, Class<?>> classMap = new HashMap<>();
    private static final String PACKAGE_NAME = FacebookSdk.getApplicationContext().getPackageName();
    private static final String SKU_DETAILS_STORE = "com.facebook.internal.SKU_DETAILS";
    private static final SharedPreferences skuDetailSharedPrefs = FacebookSdk.getApplicationContext().getSharedPreferences(SKU_DETAILS_STORE, 0);
    private static final String PURCHASE_INAPP_STORE = "com.facebook.internal.PURCHASE";
    private static final SharedPreferences purchaseInappSharedPrefs = FacebookSdk.getApplicationContext().getSharedPreferences(PURCHASE_INAPP_STORE, 0);

    InAppPurchaseEventManager() {
    }

    static Object asInterface(Context context, IBinder iBinder) {
        return invokeMethod(context, IN_APP_BILLING_SERVICE_STUB, AS_INTERFACE, null, new Object[]{iBinder});
    }

    static Map<String, String> getSkuDetails(Context context, ArrayList<String> arrayList, Object obj, boolean z) {
        Map<String, String> skuDetailsFromCache = readSkuDetailsFromCache(arrayList);
        ArrayList arrayList2 = new ArrayList();
        for (String str : arrayList) {
            if (!skuDetailsFromCache.containsKey(str)) {
                arrayList2.add(str);
            }
        }
        skuDetailsFromCache.putAll(getSkuDetailsFromGoogle(context, arrayList2, obj, z));
        return skuDetailsFromCache;
    }

    private static Map<String, String> getSkuDetailsFromGoogle(Context context, ArrayList<String> arrayList, Object obj, boolean z) {
        HashMap map = new HashMap();
        if (obj == null || arrayList.isEmpty()) {
            return map;
        }
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ITEM_ID_LIST, arrayList);
        Object[] objArr = new Object[4];
        objArr[0] = 3;
        objArr[1] = PACKAGE_NAME;
        objArr[2] = z ? SUBSCRIPTION : INAPP;
        objArr[3] = bundle;
        Object objInvokeMethod = invokeMethod(context, IN_APP_BILLING_SERVICE, GET_SKU_DETAILS, obj, objArr);
        if (objInvokeMethod != null) {
            Bundle bundle2 = (Bundle) objInvokeMethod;
            if (bundle2.getInt(RESPONSE_CODE) == 0) {
                ArrayList<String> stringArrayList = bundle2.getStringArrayList(DETAILS_LIST);
                if (stringArrayList != null && arrayList.size() == stringArrayList.size()) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        map.put(arrayList.get(i), stringArrayList.get(i));
                    }
                }
                writeSkuDetailsToCache(map);
            }
        }
        return map;
    }

    private static Map<String, String> readSkuDetailsFromCache(ArrayList<String> arrayList) {
        HashMap map = new HashMap();
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        for (String str : arrayList) {
            String string = skuDetailSharedPrefs.getString(str, null);
            if (string != null) {
                String[] strArrSplit = string.split(";", 2);
                if (jCurrentTimeMillis - Long.parseLong(strArrSplit[0]) < 43200) {
                    map.put(str, strArrSplit[1]);
                }
            }
        }
        return map;
    }

    private static void writeSkuDetailsToCache(Map<String, String> map) {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        SharedPreferences.Editor editorEdit = skuDetailSharedPrefs.edit();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            editorEdit.putString(entry.getKey(), jCurrentTimeMillis + ";" + entry.getValue());
        }
        editorEdit.apply();
    }

    private static Boolean isBillingSupported(Context context, Object obj, String str) {
        if (obj == null) {
            return false;
        }
        Object objInvokeMethod = invokeMethod(context, IN_APP_BILLING_SERVICE, IS_BILLING_SUPPORTED, obj, new Object[]{3, PACKAGE_NAME, str});
        return Boolean.valueOf(objInvokeMethod != null && ((Integer) objInvokeMethod).intValue() == 0);
    }

    static ArrayList<String> getPurchasesInapp(Context context, Object obj) {
        return filterPurchases(getPurchases(context, obj, INAPP));
    }

    static ArrayList<String> getPurchasesSubs(Context context, Object obj) {
        return filterPurchases(getPurchases(context, obj, SUBSCRIPTION));
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0063  */
    private static ArrayList<String> getPurchases(Context context, Object obj, String str) {
        int i;
        ArrayList<String> arrayList = new ArrayList<>();
        if (obj == null) {
            return arrayList;
        }
        if (isBillingSupported(context, obj, str).booleanValue()) {
            int i2 = 0;
            String string = null;
            while (true) {
                Object objInvokeMethod = invokeMethod(context, IN_APP_BILLING_SERVICE, GET_PURCHASES, obj, new Object[]{3, PACKAGE_NAME, str, string});
                if (objInvokeMethod != null) {
                    Bundle bundle = (Bundle) objInvokeMethod;
                    if (bundle.getInt(RESPONSE_CODE) == 0) {
                        ArrayList<String> stringArrayList = bundle.getStringArrayList(INAPP_PURCHASE_DATA_LIST);
                        if (stringArrayList == null) {
                            break;
                        }
                        int size = i2 + stringArrayList.size();
                        arrayList.addAll(stringArrayList);
                        string = bundle.getString(INAPP_CONTINUATION_TOKEN);
                        i = size;
                    } else {
                        i = i2;
                        string = null;
                    }
                    if (i >= MAX_QUERY_PURCHASE_NUM || string == null) {
                        break;
                    }
                    i2 = i;
                } else {
                    i = i2;
                    string = null;
                    if (i >= MAX_QUERY_PURCHASE_NUM) {
                        break;
                    }
                    break;
                    break;
                }
            }
        }
        return arrayList;
    }

    static boolean hasFreeTrialPeirod(String str) {
        try {
            String strOptString = new JSONObject(str).optString("freeTrialPeriod");
            return (strOptString == null || strOptString.isEmpty()) ? false : true;
        } catch (JSONException e) {
            String str2 = TAG;
            return false;
        }
    }

    static ArrayList<String> getPurchaseHistoryInapp(Context context, Object obj) {
        Class<?> cls;
        ArrayList<String> arrayList = new ArrayList<>();
        return (obj == null || (cls = getClass(context, IN_APP_BILLING_SERVICE)) == null || getMethod(cls, GET_PURCHASE_HISTORY) == null) ? arrayList : filterPurchases(getPurchaseHistory(context, obj, INAPP));
    }

    private static ArrayList<String> getPurchaseHistory(Context context, Object obj, String str) {
        int i;
        ArrayList<String> stringArrayList;
        int i2;
        boolean z;
        ArrayList<String> arrayList = new ArrayList<>();
        if (isBillingSupported(context, obj, str).booleanValue()) {
            int i3 = 0;
            boolean z2 = false;
            String str2 = null;
            while (true) {
                String string = null;
                Object objInvokeMethod = invokeMethod(context, IN_APP_BILLING_SERVICE, GET_PURCHASE_HISTORY, obj, new Object[]{6, PACKAGE_NAME, str, str2, new Bundle()});
                if (objInvokeMethod != null) {
                    long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
                    Bundle bundle = (Bundle) objInvokeMethod;
                    if (bundle.getInt(RESPONSE_CODE) != 0 || (stringArrayList = bundle.getStringArrayList(INAPP_PURCHASE_DATA_LIST)) == null) {
                        i = i3;
                    } else {
                        Iterator<String> it = stringArrayList.iterator();
                        while (true) {
                            i2 = i3;
                            if (!it.hasNext()) {
                                z = z2;
                                break;
                            }
                            String next = it.next();
                            try {
                                if (jCurrentTimeMillis - (new JSONObject(next).getLong("purchaseTime") / 1000) > 1200) {
                                    z = true;
                                    break;
                                }
                                arrayList.add(next);
                                i3 = i2 + 1;
                            } catch (JSONException e) {
                                String str3 = TAG;
                                i3 = i2;
                            }
                        }
                        string = bundle.getString(INAPP_CONTINUATION_TOKEN);
                        z2 = z;
                        i = i2;
                    }
                } else {
                    i = i3;
                }
                if (i >= MAX_QUERY_PURCHASE_NUM || string == null || z2) {
                    break;
                }
                i3 = i;
                str2 = string;
            }
        }
        return arrayList;
    }

    private static ArrayList<String> filterPurchases(ArrayList<String> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        SharedPreferences.Editor editorEdit = purchaseInappSharedPrefs.edit();
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        for (String str : arrayList) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                String string = jSONObject.getString("productId");
                long j = jSONObject.getLong("purchaseTime");
                String string2 = jSONObject.getString("purchaseToken");
                if (jCurrentTimeMillis - (j / 1000) <= 86400 && !purchaseInappSharedPrefs.getString(string, "").equals(string2)) {
                    editorEdit.putString(string, string2);
                    arrayList2.add(str);
                }
            } catch (JSONException e) {
                String str2 = TAG;
            }
        }
        editorEdit.apply();
        return arrayList2;
    }

    private static Method getMethod(Class<?> cls, String str) {
        byte b = 0;
        Method declaredMethod = methodMap.get(str);
        if (declaredMethod == null) {
            Class<?>[] clsArr = null;
            try {
                switch (str.hashCode()) {
                    case -1801122596:
                        b = !str.equals(GET_PURCHASES) ? (byte) -1 : (byte) 3;
                        break;
                    case -1450694211:
                        b = !str.equals(IS_BILLING_SUPPORTED) ? (byte) -1 : (byte) 2;
                        break;
                    case -1123215065:
                        if (!str.equals(AS_INTERFACE)) {
                            b = -1;
                        }
                        break;
                    case -594356707:
                        b = !str.equals(GET_PURCHASE_HISTORY) ? (byte) -1 : (byte) 4;
                        break;
                    case -573310373:
                        b = !str.equals(GET_SKU_DETAILS) ? (byte) -1 : (byte) 1;
                        break;
                    default:
                        b = -1;
                        break;
                }
                switch (b) {
                    case 0:
                        clsArr = new Class[]{IBinder.class};
                        break;
                    case 1:
                        clsArr = new Class[]{Integer.TYPE, String.class, String.class, Bundle.class};
                        break;
                    case 2:
                        clsArr = new Class[]{Integer.TYPE, String.class, String.class};
                        break;
                    case 3:
                        clsArr = new Class[]{Integer.TYPE, String.class, String.class, String.class};
                        break;
                    case 4:
                        clsArr = new Class[]{Integer.TYPE, String.class, String.class, String.class, Bundle.class};
                        break;
                }
                declaredMethod = cls.getDeclaredMethod(str, clsArr);
                methodMap.put(str, declaredMethod);
                return declaredMethod;
            } catch (NoSuchMethodException e) {
                String str2 = TAG;
                String str3 = cls.getName() + "." + str + " method not found";
                return declaredMethod;
            }
        }
        return declaredMethod;
    }

    private static Class<?> getClass(Context context, String str) {
        Class<?> clsLoadClass = classMap.get(str);
        if (clsLoadClass == null) {
            try {
                clsLoadClass = context.getClassLoader().loadClass(str);
                classMap.put(str, clsLoadClass);
                return clsLoadClass;
            } catch (ClassNotFoundException e) {
                String str2 = TAG;
                String str3 = str + " is not available, please add " + str + " to the project.";
                return clsLoadClass;
            }
        }
        return clsLoadClass;
    }

    private static Object invokeMethod(Context context, String str, String str2, Object obj, Object[] objArr) {
        Method method;
        Class<?> cls = getClass(context, str);
        if (cls == null || (method = getMethod(cls, str2)) == null) {
            return null;
        }
        if (obj != null) {
            obj = cls.cast(obj);
        }
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e2) {
            return null;
        }
    }

    static void clearSkuDetailsCache() {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        long j = skuDetailSharedPrefs.getLong(LAST_CLEARED_TIME, 0L);
        if (j == 0) {
            skuDetailSharedPrefs.edit().putLong(LAST_CLEARED_TIME, jCurrentTimeMillis).apply();
        } else if (jCurrentTimeMillis - j > 604800) {
            skuDetailSharedPrefs.edit().clear().putLong(LAST_CLEARED_TIME, jCurrentTimeMillis).apply();
        }
    }
}
