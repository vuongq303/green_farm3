package com.unity3d.services.ar;

import android.content.Context;
import com.google.ar.core.ArCoreApk;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.FatalException;
import com.google.ar.core.exceptions.UnavailableException;
import com.unity3d.services.core.log.DeviceLog;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ARUtils {
    public static final int AR_CHECK_NOT_SUPPORTED = 0;
    public static final int AR_CHECK_SUPPORTED = 1;
    public static final int AR_CHECK_TRANSIENT = 2;
    private static Config.LightEstimationMode[] lightEstimationModes;
    private static Config.PlaneFindingMode[] planeFindingModes;
    private static Config.UpdateMode[] updateModes;

    public static int isSupported(Context context) {
        if (!ARCheck.isFrameworkPresent()) {
            return 0;
        }
        if (planeFindingModes == null) {
            planeFindingModes = Config.PlaneFindingMode.values();
            lightEstimationModes = Config.LightEstimationMode.values();
            updateModes = Config.UpdateMode.values();
        }
        ArCoreApk.Availability availabilityCheckAvailability = ArCoreApk.getInstance().checkAvailability(context);
        if (availabilityCheckAvailability.isTransient()) {
            return 2;
        }
        if (availabilityCheckAvailability != ArCoreApk.Availability.SUPPORTED_INSTALLED) {
            return 0;
        }
        try {
            new Session(context);
        } catch (SecurityException e) {
        } catch (FatalException e2) {
            return 0;
        } catch (UnavailableException e3) {
            return 0;
        }
        return 1;
    }

    public static Config createConfiguration(JSONObject jSONObject, Session session) {
        Config config = new Config(session);
        if (jSONObject.has("lightEstimationMode")) {
            try {
                String string = jSONObject.getString("lightEstimationMode");
                for (Config.LightEstimationMode lightEstimationMode : lightEstimationModes) {
                    if (string.equalsIgnoreCase(lightEstimationMode.name())) {
                        config.setLightEstimationMode(lightEstimationMode);
                        break;
                    }
                }
            } catch (JSONException e) {
                DeviceLog.warning("lightEstimationEnabled is not a string.");
            }
        }
        if (jSONObject.has("planeFindingMode")) {
            try {
                String string2 = jSONObject.getString("planeFindingMode");
                for (Config.PlaneFindingMode planeFindingMode : planeFindingModes) {
                    if (string2.equalsIgnoreCase(planeFindingMode.name())) {
                        config.setPlaneFindingMode(planeFindingMode);
                        break;
                    }
                }
            } catch (JSONException e2) {
                DeviceLog.warning("planeFindingMode is not a string.");
            }
        }
        if (jSONObject.has("updateMode")) {
            try {
                String string3 = jSONObject.getString("updateMode");
                for (Config.UpdateMode updateMode : updateModes) {
                    if (string3.equalsIgnoreCase(updateMode.name())) {
                        config.setUpdateMode(updateMode);
                        break;
                    }
                }
            } catch (JSONException e3) {
                DeviceLog.warning("updateMode is not a string.");
            }
        }
        return config;
    }

    public static JSONObject getConfigEnums() {
        JSONObject jSONObject = new JSONObject();
        try {
            ArrayList arrayList = new ArrayList();
            for (Config.LightEstimationMode lightEstimationMode : Config.LightEstimationMode.values()) {
                arrayList.add(lightEstimationMode.name());
            }
            jSONObject.put("lightEstimationMode", new JSONArray(arrayList.toArray()));
            arrayList.clear();
            for (Config.PlaneFindingMode planeFindingMode : Config.PlaneFindingMode.values()) {
                arrayList.add(planeFindingMode.name());
            }
            jSONObject.put("planeFindingMode", new JSONArray(arrayList.toArray()));
            arrayList.clear();
            for (Config.UpdateMode updateMode : Config.UpdateMode.values()) {
                arrayList.add(updateMode.name());
            }
            jSONObject.put("updateMode", new JSONArray(arrayList.toArray()));
        } catch (JSONException e) {
        }
        return jSONObject;
    }
}
