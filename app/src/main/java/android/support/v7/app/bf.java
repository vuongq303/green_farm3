package android.support.v7.app;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Calendar;

/* JADX INFO: compiled from: TwilightManager.java */
/* JADX INFO: loaded from: classes.dex */
class bf {
    private static bf mK;
    private final Context mContext;
    private final LocationManager mL;
    private final bg mM = new bg();

    static bf l(Context context) {
        if (mK == null) {
            Context applicationContext = context.getApplicationContext();
            mK = new bf(applicationContext, (LocationManager) applicationContext.getSystemService(FirebaseAnalytics.Param.LOCATION));
        }
        return mK;
    }

    bf(Context context, LocationManager locationManager) {
        this.mContext = context;
        this.mL = locationManager;
    }

    boolean bY() {
        bg bgVar = this.mM;
        if (ca()) {
            return bgVar.mN;
        }
        Location locationBZ = bZ();
        if (locationBZ != null) {
            a(locationBZ);
            return bgVar.mN;
        }
        Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
        int i = Calendar.getInstance().get(11);
        return i < 6 || i >= 22;
    }

    private Location bZ() {
        Location locationK = android.support.v4.content.u.d(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0 ? k("network") : null;
        Location locationK2 = android.support.v4.content.u.d(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0 ? k("gps") : null;
        if (locationK2 != null && locationK != null) {
            return locationK2.getTime() > locationK.getTime() ? locationK2 : locationK;
        }
        if (locationK2 == null) {
            locationK2 = locationK;
        }
        return locationK2;
    }

    private Location k(String str) {
        if (this.mL != null) {
            try {
                if (this.mL.isProviderEnabled(str)) {
                    return this.mL.getLastKnownLocation(str);
                }
            } catch (Exception e) {
                Log.d("TwilightManager", "Failed to get last known location", e);
            }
        }
        return null;
    }

    private boolean ca() {
        return this.mM != null && this.mM.mS > System.currentTimeMillis();
    }

    private void a(Location location) {
        long j;
        long j2;
        bg bgVar = this.mM;
        long jCurrentTimeMillis = System.currentTimeMillis();
        be beVarBX = be.bX();
        beVarBX.a(jCurrentTimeMillis - 86400000, location.getLatitude(), location.getLongitude());
        long j3 = beVarBX.mI;
        beVarBX.a(jCurrentTimeMillis, location.getLatitude(), location.getLongitude());
        boolean z = beVarBX.state == 1;
        long j4 = beVarBX.mJ;
        long j5 = beVarBX.mI;
        beVarBX.a(86400000 + jCurrentTimeMillis, location.getLatitude(), location.getLongitude());
        long j6 = beVarBX.mJ;
        if (j4 == -1 || j5 == -1) {
            j = 43200000 + jCurrentTimeMillis;
        } else {
            if (jCurrentTimeMillis > j5) {
                j2 = 0 + j6;
            } else if (jCurrentTimeMillis > j4) {
                j2 = 0 + j5;
            } else {
                j2 = 0 + j4;
            }
            j = j2 + 60000;
        }
        bgVar.mN = z;
        bgVar.mO = j3;
        bgVar.mP = j4;
        bgVar.mQ = j5;
        bgVar.mR = j6;
        bgVar.mS = j;
    }
}
