package com.gameloft.android2d.iap.a.a;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.text.TextUtils;
import com.android.vending.billing.IInAppBillingService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: compiled from: IabHelper.java */
/* JADX INFO: loaded from: classes.dex */
public class r {
    boolean bma = false;
    String bmb = "IAP-IabHelper";
    boolean bmc = false;
    boolean bmd = false;
    boolean bme = false;
    String bmf = "";
    IInAppBillingService bmg;
    ServiceConnection bmh;
    int bmi;
    String bmj;
    String bmk;
    aa bml;
    Context mContext;

    public r(Context context, String str) {
        this.bmk = null;
        this.mContext = context.getApplicationContext();
        this.bmk = str;
        ft("IAB helper created.");
    }

    public void enableDebugLogging(boolean z) {
        this.bma = z;
    }

    public void a(ab abVar) {
        if (this.bmc) {
            throw new IllegalStateException("IAB helper is already set up.");
        }
        ft("Starting in-app billing setup.");
        this.bmh = new s(this, abVar);
        try {
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent.setPackage("com.android.vending");
            if (intent != null && !this.mContext.getPackageManager().queryIntentServices(intent, 0).isEmpty()) {
                this.mContext.bindService(intent, this.bmh, 1);
            } else if (abVar != null) {
                abVar.a(new ad(3, "Billing service unavailable on device."));
            }
        } catch (Exception e) {
            if (abVar != null) {
                abVar.a(new ad(9, "Unable to bind the service on this device."));
            }
        }
    }

    public void Sm() {
        ft("Disposing.");
        this.bmc = false;
        if (this.bmh != null) {
            ft("Unbinding from service.");
            if (this.mContext != null) {
                this.mContext.unbindService(this.bmh);
            }
            this.bmh = null;
            this.bmg = null;
            this.bml = null;
        }
    }

    public void a(Activity activity, String str, int i, aa aaVar, String str2) {
        a(activity, str, "inapp", i, aaVar, str2);
    }

    public void a(Activity activity, String str, String str2, int i, aa aaVar, String str3) {
        try {
            fr("launchPurchaseFlow");
            fs("launchPurchaseFlow");
            if (str2.equals("subs") && !this.bmd) {
                ad adVar = new ad(-1009, "Subscriptions are not available.");
                if (aaVar != null) {
                    aaVar.a(adVar, null, -1);
                    return;
                }
                return;
            }
            try {
                ft("Constructing buy intent for " + str + ", item type: " + str2);
                Bundle bundleA = this.bmg.a(3, this.mContext.getPackageName(), str, str2, str3);
                int iQ = q(bundleA);
                if (iQ != 0) {
                    Sn();
                    fu("Unable to buy item, Error response: " + mD(iQ));
                    ad adVar2 = new ad(iQ, "Unable to buy item");
                    if (aaVar != null) {
                        aaVar.a(adVar2, null, iQ);
                    }
                } else {
                    PendingIntent pendingIntent = (PendingIntent) bundleA.getParcelable("BUY_INTENT");
                    ft("Launching buy intent for " + str + ". Request code: " + i);
                    this.bmi = i;
                    this.bml = aaVar;
                    this.bmj = str2;
                    IntentSender intentSender = pendingIntent.getIntentSender();
                    Intent intent = new Intent();
                    Integer num = 0;
                    int iIntValue = num.intValue();
                    Integer num2 = 0;
                    int iIntValue2 = num2.intValue();
                    Integer num3 = 0;
                    activity.startIntentSenderForResult(intentSender, i, intent, iIntValue, iIntValue2, num3.intValue());
                }
            } catch (IntentSender.SendIntentException e) {
                fu("SendIntentException while launching purchase flow for sku " + str);
                ad adVar3 = new ad(-1004, "Failed to send intent.");
                if (aaVar != null) {
                    aaVar.a(adVar3, null, -1);
                }
            } catch (RemoteException e2) {
                fu("RemoteException while launching purchase flow for sku " + str);
                ad adVar4 = new ad(-1001, "Remote exception while starting purchase flow");
                if (aaVar != null) {
                    aaVar.a(adVar4, null, -1);
                }
            }
        } catch (Exception e3) {
            ft("Cannot start launchPurchaseFlow, another AsyncTask is in place. Please try again in a few second" + e3.toString());
        }
    }

    public boolean a(int i, int i2, Intent intent) {
        if (i != this.bmi) {
            return false;
        }
        fr("handleActivityResult");
        Sn();
        if (intent == null) {
            fu("Null data in IAB activity result.");
            ad adVar = new ad(-1002, "Null data in IAB result");
            if (this.bml != null) {
                this.bml.a(adVar, null, -1);
            }
            return true;
        }
        int iH = h(intent);
        String stringExtra = intent.getStringExtra("INAPP_PURCHASE_DATA");
        String stringExtra2 = intent.getStringExtra("INAPP_DATA_SIGNATURE");
        if (i2 == -1 && iH == 0) {
            ft("Successful resultcode from purchase activity.");
            ft("Purchase data: " + stringExtra);
            ft("Data signature: " + stringExtra2);
            ft("Extras: " + intent.getExtras());
            ft("Expected item type: " + this.bmj);
            if (stringExtra == null || stringExtra2 == null) {
                fu("BUG: either purchaseData or dataSignature is null.");
                ft("Extras: " + intent.getExtras().toString());
                ad adVar2 = new ad(-1008, "IAB returned null purchaseData or dataSignature");
                if (this.bml != null) {
                    this.bml.a(adVar2, null, iH);
                }
                return true;
            }
            try {
                af afVar = new af(this.bmj, stringExtra, stringExtra2);
                String strSu = afVar.Su();
                if (!ag.C(this.bmk, stringExtra, stringExtra2)) {
                    fu("Purchase signature verification FAILED for sku " + strSu);
                    ad adVar3 = new ad(-1003, "Signature verification failed for sku " + strSu);
                    if (this.bml != null) {
                        this.bml.a(adVar3, afVar, iH);
                    }
                    return true;
                }
                ft("Purchase signature successfully verified.");
                if (this.bml != null) {
                    this.bml.a(new ad(0, "Success"), afVar, iH);
                }
            } catch (JSONException e) {
                fu("Failed to parse purchase data.");
                ad adVar4 = new ad(-1002, "Failed to parse purchase data.");
                if (this.bml != null) {
                    this.bml.a(adVar4, null, iH);
                }
                return true;
            }
        } else if (i2 == -1) {
            ft("Result code was OK but in-app billing response was not OK: " + mD(iH));
            if (this.bml != null) {
                this.bml.a(new ad(iH, "Problem purchashing item."), null, iH);
            }
        } else if (i2 == 0) {
            ft("Purchase canceled - Response: " + mD(iH));
            ad adVar5 = new ad(-1005, "User canceled.");
            if (this.bml != null) {
                this.bml.a(adVar5, null, iH);
            }
        } else {
            fu("Purchase failed. Result code: " + Integer.toString(i2) + ". Response: " + mD(iH));
            ad adVar6 = new ad(-1006, "Unknown purchase response.");
            if (this.bml != null) {
                this.bml.a(adVar6, null, iH);
            }
        }
        return true;
    }

    public ae a(boolean z, List<String> list) {
        return a(z, list, (List<String>) null);
    }

    public ae a(boolean z, List<String> list, List<String> list2) throws q {
        int iA;
        int iA2;
        fr("queryInventory");
        try {
            ae aeVar = new ae();
            int iA3 = a(aeVar, "inapp");
            if (iA3 != 0) {
                throw new q(iA3, "Error refreshing inventory (querying owned items).");
            }
            if (z && (iA2 = a("inapp", aeVar, list)) != 0) {
                throw new q(iA2, "Error refreshing inventory (querying prices of items).");
            }
            if (this.bmd) {
                int iA4 = a(aeVar, "subs");
                if (iA4 != 0) {
                    throw new q(iA4, "Error refreshing inventory (querying owned subscriptions).");
                }
                if (z && (iA = a("subs", aeVar, list)) != 0) {
                    throw new q(iA, "Error refreshing inventory (querying prices of subscriptions).");
                }
            }
            return aeVar;
        } catch (RemoteException e) {
            throw new q(-1001, "Remote exception while refreshing inventory.", e);
        } catch (JSONException e2) {
            throw new q(-1002, "Error parsing JSON response while refreshing inventory.", e2);
        }
    }

    public void a(boolean z, List<String> list, ac acVar) {
        Handler handler = new Handler();
        try {
            fr("queryInventory");
            fs("refresh inventory");
            new Thread(new t(this, z, list, handler, acVar)).start();
        } catch (Exception e) {
            ft("Cannot start request refresh inventory, another AsyncTask is in place. Please try again in a few second" + e.toString());
            new ad(6, "Helper is not setup.");
        }
    }

    public void a(ac acVar) {
        a(true, (List<String>) null, acVar);
    }

    void b(af afVar) throws q {
        fr("consume");
        if (!afVar.bmD.equals("inapp")) {
            throw new q(-1010, "Items of type '" + afVar.bmD + "' can't be consumed.");
        }
        try {
            String token = afVar.getToken();
            String strSu = afVar.Su();
            if (token == null || token.equals("")) {
                fu("Can't consume " + strSu + ". No token.");
                throw new q(-1007, "PurchaseInfo is missing token for sku: " + strSu + " " + afVar);
            }
            ft("Consuming sku: " + strSu + ", token: " + token);
            int iB = this.bmg.b(3, this.mContext.getPackageName(), token);
            if (iB == 0) {
                ft("Successfully consumed sku: " + strSu);
            } else {
                ft("Error consuming consuming sku " + strSu + ". " + mD(iB));
                throw new q(iB, "Error consuming sku " + strSu);
            }
        } catch (RemoteException e) {
            throw new q(-1001, "Remote exception while consuming. PurchaseInfo: " + afVar, e);
        }
    }

    public void a(af afVar, y yVar) {
        fr("consume");
        ArrayList arrayList = new ArrayList();
        arrayList.add(afVar);
        a(arrayList, yVar, (z) null);
    }

    public static String mD(int i) {
        String[] strArrSplit = "0:OK/1:User Canceled/2:Unknown/3:Billing Unavailable/4:Item unavailable/5:Developer Error/6:Error/7:Item Already Owned/8:Item not owned".split("/");
        String[] strArrSplit2 = "0:OK/-1001:Remote exception during initialization/-1002:Bad response received/-1003:Purchase signature verification failed/-1004:Send intent failed/-1005:User cancelled/-1006:Unknown purchase response/-1007:Missing token/-1008:Unknown error/-1009:Subscriptions not available/-1010:Invalid consumption attempt".split("/");
        if (i <= -1000) {
            int i2 = (-1000) - i;
            return (i2 < 0 || i2 >= strArrSplit2.length) ? String.valueOf(i) + ":Unknown IAB Helper Error" : strArrSplit2[i2];
        }
        if (i < 0 || i >= strArrSplit.length) {
            return String.valueOf(i) + ":Unknown";
        }
        return strArrSplit[i];
    }

    void fr(String str) {
        if (!this.bmc) {
            fu("Illegal state for operation (" + str + "): IAB helper is not set up.");
            throw new IllegalStateException("IAB helper is not set up. Can't perform operation: " + str);
        }
    }

    int q(Bundle bundle) {
        Object obj = bundle.get("RESPONSE_CODE");
        if (obj == null) {
            ft("Bundle with null response code, assuming OK (known issue)");
            return 0;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        if (obj instanceof Long) {
            return (int) ((Long) obj).longValue();
        }
        fu("Unexpected type for bundle response code.");
        fu(obj.getClass().getName());
        throw new RuntimeException("Unexpected type for bundle response code: " + obj.getClass().getName());
    }

    int h(Intent intent) {
        Object obj = intent.getExtras().get("RESPONSE_CODE");
        if (obj == null) {
            fu("Intent with no response code, assuming OK (known issue)");
            return 0;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).intValue();
        }
        if (obj instanceof Long) {
            return (int) ((Long) obj).longValue();
        }
        fu("Unexpected type for intent response code.");
        fu(obj.getClass().getName());
        throw new RuntimeException("Unexpected type for intent response code: " + obj.getClass().getName());
    }

    void fs(String str) {
        if (this.bme) {
            throw new IllegalStateException("Can't start async operation (" + str + ") because another async operation(" + this.bmf + ") is in progress.");
        }
        this.bmf = str;
        this.bme = true;
        ft("Starting async operation: " + str);
    }

    void Sn() {
        ft("Ending async operation: " + this.bmf);
        this.bmf = "";
        this.bme = false;
    }

    int a(ae aeVar, String str) {
        ft("Querying owned items, item type: " + str);
        ft("Package name: " + this.mContext.getPackageName());
        String string = null;
        boolean z = false;
        while (true) {
            ft("Calling getPurchases with continuation token: " + string);
            try {
                Bundle bundleA = this.bmg.a(3, this.mContext.getPackageName(), str, string);
                int iQ = q(bundleA);
                ft("Owned items response: " + String.valueOf(iQ));
                if (iQ != 0) {
                    ft("getPurchases() failed: " + mD(iQ));
                    return iQ;
                }
                if (!bundleA.containsKey("INAPP_PURCHASE_ITEM_LIST") || !bundleA.containsKey("INAPP_PURCHASE_DATA_LIST") || !bundleA.containsKey("INAPP_DATA_SIGNATURE_LIST")) {
                    fu("Bundle returned from getPurchases() doesn't contain required fields.");
                    return -1002;
                }
                ArrayList<String> stringArrayList = bundleA.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
                ArrayList<String> stringArrayList2 = bundleA.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                ArrayList<String> stringArrayList3 = bundleA.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                boolean z2 = z;
                for (int i = 0; i < stringArrayList2.size(); i++) {
                    String str2 = stringArrayList2.get(i);
                    String str3 = stringArrayList3.get(i);
                    String str4 = stringArrayList.get(i);
                    if (ag.C(this.bmk, str2, str3)) {
                        ft("Sku is owned: " + str4);
                        af afVar = new af(str, str2, str3);
                        if (TextUtils.isEmpty(afVar.getToken())) {
                            fv("BUG: empty/null token!");
                            ft("Purchase data: " + str2);
                        }
                        aeVar.c(afVar);
                    } else {
                        fv("Purchase signature verification **FAILED**. Not adding item.");
                        ft("   Purchase data: " + str2);
                        ft("   Signature: " + str3);
                        z2 = true;
                    }
                }
                string = bundleA.getString("INAPP_CONTINUATION_TOKEN");
                ft("Continuation token: " + string);
                if (TextUtils.isEmpty(string)) {
                    return z2 ? -1003 : 0;
                }
                z = z2;
            } catch (Exception e) {
                ft("There is a exception in queryPurchases(): " + e.toString());
                return 9;
            }
        }
    }

    int a(String str, ae aeVar, List<String> list) {
        ft("Querying SKU details.");
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(aeVar.fx(str));
        if (list != null) {
            for (String str2 : list) {
                if (!arrayList.contains(str2)) {
                    arrayList.add(str2);
                }
            }
        }
        if (arrayList.size() == 0) {
            ft("queryPrices: nothing to do because there are no SKUs.");
            return 0;
        }
        ArrayList<ArrayList<String>> arrayList2 = new ArrayList();
        int size = arrayList.size() / 20;
        int size2 = arrayList.size() % 20;
        for (int i = 0; i < size; i++) {
            ArrayList arrayList3 = new ArrayList();
            Iterator it = arrayList.subList(i * 20, (i * 20) + 20).iterator();
            while (it.hasNext()) {
                arrayList3.add((String) it.next());
            }
            arrayList2.add(arrayList3);
        }
        if (size2 != 0) {
            ArrayList arrayList4 = new ArrayList();
            Iterator it2 = arrayList.subList(size * 20, (size * 20) + size2).iterator();
            while (it2.hasNext()) {
                arrayList4.add((String) it2.next());
            }
            arrayList2.add(arrayList4);
        }
        for (ArrayList<String> arrayList5 : arrayList2) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("ITEM_ID_LIST", arrayList5);
            Bundle bundleA = this.bmg.a(3, this.mContext.getPackageName(), str, bundle);
            if (!bundleA.containsKey("DETAILS_LIST")) {
                int iQ = q(bundleA);
                if (iQ != 0) {
                    ft("getSkuDetails() failed: " + mD(iQ));
                    return iQ;
                }
                fu("getSkuDetails() returned a bundle with neither an error nor a detail list.");
                return -1002;
            }
            Iterator<String> it3 = bundleA.getStringArrayList("DETAILS_LIST").iterator();
            while (it3.hasNext()) {
                ah ahVar = new ah(str, it3.next());
                ft("Got sku details: " + ahVar);
                aeVar.a(ahVar);
            }
        }
        return 0;
    }

    void a(List<af> list, y yVar, z zVar) {
        Handler handler = new Handler();
        try {
            fs("consume");
            new Thread(new v(this, list, yVar, handler, zVar)).start();
        } catch (Exception e) {
            ft("Cannot start consume, another AsyncTask is in place. Please try again in a few second" + e.toString());
        }
    }

    void ft(String str) {
        if (this.bma) {
        }
    }

    void fu(String str) {
    }

    void fv(String str) {
    }
}
