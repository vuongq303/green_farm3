package com.unity3d.services.core.misc;

import android.text.TextUtils;
import com.unity3d.services.core.log.DeviceLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class JsonStorage {
    private JSONObject _data;

    public synchronized boolean initData() {
        boolean z;
        if (this._data == null) {
            this._data = new JSONObject();
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public synchronized void setData(JSONObject jSONObject) {
        this._data = jSONObject;
    }

    public synchronized JSONObject getData() {
        return this._data;
    }

    public synchronized boolean hasData() {
        return this._data != null && this._data.length() > 0;
    }

    public synchronized void clearData() {
        this._data = null;
    }

    public synchronized boolean set(String str, Object obj) {
        boolean z;
        try {
            if (this._data == null || str == null || str.length() == 0 || obj == null) {
                DeviceLog.error("Storage not properly initialized or incorrect parameters:" + this._data + ", " + str + ", " + obj);
                z = false;
            } else {
                createObjectTree(getParentObjectTreeFor(str));
                if (findObject(getParentObjectTreeFor(str)) instanceof JSONObject) {
                    JSONObject jSONObject = (JSONObject) findObject(getParentObjectTreeFor(str));
                    String[] strArrSplit = str.split("\\.");
                    if (jSONObject != null) {
                        try {
                            jSONObject.put(strArrSplit[strArrSplit.length - 1], obj);
                        } catch (JSONException e) {
                            DeviceLog.exception("Couldn't set value", e);
                            z = false;
                        }
                    }
                    z = true;
                } else {
                    DeviceLog.debug("Cannot set subvalue to an object that is not JSONObject");
                    z = false;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return z;
    }

    public synchronized Object get(String str) {
        JSONObject jSONObject;
        Object obj;
        Object obj2 = null;
        synchronized (this) {
            try {
                if (this._data == null) {
                    DeviceLog.error("Data is NULL, readStorage probably not called");
                } else {
                    String[] strArrSplit = str.split("\\.");
                    if ((findObject(getParentObjectTreeFor(str)) instanceof JSONObject) && (jSONObject = (JSONObject) findObject(getParentObjectTreeFor(str))) != null) {
                        try {
                            obj = jSONObject.has(strArrSplit[strArrSplit.length + (-1)]) ? jSONObject.get(strArrSplit[strArrSplit.length - 1]) : null;
                        } catch (Exception e) {
                            DeviceLog.exception("Error getting data", e);
                        }
                        obj2 = obj;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return obj2;
    }

    public synchronized List<String> getKeys(String str, boolean z) {
        ArrayList arrayList;
        if (get(str) instanceof JSONObject) {
            JSONObject jSONObject = (JSONObject) get(str);
            ArrayList arrayList2 = new ArrayList();
            if (jSONObject != null) {
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    List<String> keys = z ? getKeys(str + "." + next, z) : null;
                    arrayList2.add(next);
                    if (keys != null) {
                        Iterator<String> it = keys.iterator();
                        while (it.hasNext()) {
                            arrayList2.add(next + "." + it.next());
                        }
                    }
                }
            }
            arrayList = arrayList2;
        } else {
            arrayList = null;
        }
        return arrayList;
    }

    public synchronized boolean delete(String str) {
        boolean z;
        JSONObject jSONObject;
        if (this._data == null) {
            DeviceLog.error("Data is NULL, readStorage probably not called");
            z = false;
        } else {
            String[] strArrSplit = str.split("\\.");
            z = (!(findObject(getParentObjectTreeFor(str)) instanceof JSONObject) || (jSONObject = (JSONObject) findObject(getParentObjectTreeFor(str))) == null || jSONObject.remove(strArrSplit[strArrSplit.length + (-1)]) == null) ? false : true;
        }
        return z;
    }

    private synchronized Object findObject(String str) {
        JSONObject jSONObject;
        String[] strArrSplit = str.split("\\.");
        jSONObject = this._data;
        if (str.length() != 0) {
            for (int i = 0; i < strArrSplit.length; i++) {
                if (!jSONObject.has(strArrSplit[i])) {
                    jSONObject = null;
                    break;
                }
                try {
                    jSONObject = jSONObject.getJSONObject(strArrSplit[i]);
                } catch (Exception e) {
                    DeviceLog.exception("Couldn't read JSONObject: " + strArrSplit[i], e);
                    jSONObject = null;
                    return jSONObject;
                }
            }
        }
        return jSONObject;
    }

    private synchronized void createObjectTree(String str) {
        try {
            String[] strArrSplit = str.split("\\.");
            JSONObject jSONObject = this._data;
            if (str.length() != 0) {
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= strArrSplit.length) {
                        break;
                    }
                    if (!jSONObject.has(strArrSplit[i2])) {
                        try {
                            jSONObject = jSONObject.put(strArrSplit[i2], new JSONObject()).getJSONObject(strArrSplit[i2]);
                        } catch (Exception e) {
                            DeviceLog.exception("Couldn't create new JSONObject", e);
                        }
                    } else {
                        try {
                            jSONObject = jSONObject.getJSONObject(strArrSplit[i2]);
                        } catch (Exception e2) {
                            DeviceLog.exception("Couldn't get existing JSONObject", e2);
                        }
                    }
                    i = i2 + 1;
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    private synchronized String getParentObjectTreeFor(String str) {
        ArrayList arrayList;
        arrayList = new ArrayList(Arrays.asList(str.split("\\.")));
        arrayList.remove(arrayList.size() - 1);
        return TextUtils.join(".", arrayList.toArray());
    }
}
