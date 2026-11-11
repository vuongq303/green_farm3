package com.google.android.gms.ads.internal;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.gameloft.glads.AndroidWebView;
import com.google.android.gms.ads.internal.gmsg.zzv;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzasd;
import com.google.android.gms.internal.ads.zzoo;
import com.google.android.gms.internal.ads.zzoq;
import com.google.android.gms.internal.ads.zzpw;
import com.google.android.gms.internal.ads.zzpx;
import com.google.android.gms.internal.ads.zzxe;
import com.google.android.gms.internal.ads.zzxz;
import com.google.android.gms.internal.ads.zzyc;
import com.unity3d.services.purchasing.core.TransactionErrorDetailsUtilities;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzas {
    static zzv<zzaqw> a(zzxz zzxzVar, zzyc zzycVar, zzac zzacVar) {
        return new n(zzxzVar, zzacVar, zzycVar);
    }

    private static String a(zzpw zzpwVar) {
        if (zzpwVar == null) {
            zzakb.zzdk("Image is null. Returning empty string");
            return "";
        }
        try {
            Uri uri = zzpwVar.getUri();
            if (uri != null) {
                return uri.toString();
            }
        } catch (RemoteException e) {
            zzakb.zzdk("Unable to get image uri. Trying data uri next");
        }
        return b(zzpwVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(zzaqw zzaqwVar) {
        View.OnClickListener onClickListener = zzaqwVar.getOnClickListener();
        if (onClickListener != null) {
            onClickListener.onClick(zzaqwVar.getView());
        }
    }

    static final /* synthetic */ void a(zzoo zzooVar, String str, zzaqw zzaqwVar, boolean z) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("headline", zzooVar.getHeadline());
            jSONObject.put("body", zzooVar.getBody());
            jSONObject.put("call_to_action", zzooVar.getCallToAction());
            jSONObject.put("price", zzooVar.getPrice());
            jSONObject.put("star_rating", String.valueOf(zzooVar.getStarRating()));
            jSONObject.put(TransactionErrorDetailsUtilities.STORE, zzooVar.getStore());
            jSONObject.put("icon", a(zzooVar.zzjz()));
            JSONArray jSONArray = new JSONArray();
            List images = zzooVar.getImages();
            if (images != null) {
                Iterator it = images.iterator();
                while (it.hasNext()) {
                    jSONArray.put(a(t(it.next())));
                }
            }
            jSONObject.put("images", jSONArray);
            jSONObject.put("extras", b(zzooVar.getExtras(), str));
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("assets", jSONObject);
            jSONObject2.put("template_id", "2");
            zzaqwVar.zzb("google.afma.nativeExpressAds.loadAssets", jSONObject2);
        } catch (JSONException e) {
            zzakb.zzc("Exception occurred when loading assets", e);
        }
    }

    static final /* synthetic */ void a(zzoq zzoqVar, String str, zzaqw zzaqwVar, boolean z) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("headline", zzoqVar.getHeadline());
            jSONObject.put("body", zzoqVar.getBody());
            jSONObject.put("call_to_action", zzoqVar.getCallToAction());
            jSONObject.put("advertiser", zzoqVar.getAdvertiser());
            jSONObject.put("logo", a(zzoqVar.zzkg()));
            JSONArray jSONArray = new JSONArray();
            List images = zzoqVar.getImages();
            if (images != null) {
                Iterator it = images.iterator();
                while (it.hasNext()) {
                    jSONArray.put(a(t(it.next())));
                }
            }
            jSONObject.put("images", jSONArray);
            jSONObject.put("extras", b(zzoqVar.getExtras(), str));
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("assets", jSONObject);
            jSONObject2.put("template_id", AppEventsConstants.EVENT_PARAM_VALUE_YES);
            zzaqwVar.zzb("google.afma.nativeExpressAds.loadAssets", jSONObject2);
        } catch (JSONException e) {
            zzakb.zzc("Exception occurred when loading assets", e);
        }
    }

    private static String b(zzpw zzpwVar) {
        String strD;
        try {
            com.google.android.gms.c.a aVarZzjy = zzpwVar.zzjy();
            if (aVarZzjy == null) {
                zzakb.zzdk("Drawable is null. Returning empty string");
                strD = "";
            } else {
                Drawable drawable = (Drawable) com.google.android.gms.c.d.b(aVarZzjy);
                if (drawable instanceof BitmapDrawable) {
                    strD = d(((BitmapDrawable) drawable).getBitmap());
                } else {
                    zzakb.zzdk("Drawable is not an instance of BitmapDrawable. Returning empty string");
                    strD = "";
                }
            }
            return strD;
        } catch (RemoteException e) {
            zzakb.zzdk("Unable to get drawable. Returning empty string");
            return "";
        }
    }

    private static JSONObject b(Bundle bundle, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (bundle == null || TextUtils.isEmpty(str)) {
            return jSONObject;
        }
        JSONObject jSONObject2 = new JSONObject(str);
        Iterator<String> itKeys = jSONObject2.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            if (bundle.containsKey(next)) {
                if (MessengerShareContentUtility.MEDIA_IMAGE.equals(jSONObject2.getString(next))) {
                    Object obj = bundle.get(next);
                    if (obj instanceof Bitmap) {
                        jSONObject.put(next, d((Bitmap) obj));
                    } else {
                        zzakb.zzdk("Invalid type. An image type extra should return a bitmap");
                    }
                } else if (bundle.get(next) instanceof Bitmap) {
                    zzakb.zzdk("Invalid asset type. Bitmap should be returned only for image type");
                } else {
                    jSONObject.put(next, String.valueOf(bundle.get(next)));
                }
            }
        }
        return jSONObject;
    }

    private static String d(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap == null) {
            zzakb.zzdk("Bitmap is null. Returning empty string");
            return "";
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        String strEncodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        String strValueOf = String.valueOf("data:image/png;base64,");
        String strValueOf2 = String.valueOf(strEncodeToString);
        return strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
    }

    private static zzpw t(Object obj) {
        if (obj instanceof IBinder) {
            return zzpx.zzh((IBinder) obj);
        }
        return null;
    }

    public static boolean zza(final zzaqw zzaqwVar, zzxe zzxeVar, CountDownLatch countDownLatch) {
        boolean z;
        try {
            View view = zzaqwVar.getView();
            if (view == null) {
                zzakb.zzdk("AdWebView is null");
                z = false;
            } else {
                view.setVisibility(4);
                List<String> list = zzxeVar.zzbtw.zzbsi;
                if (list == null || list.isEmpty()) {
                    zzakb.zzdk("No template ids present in mediation response");
                    z = false;
                } else {
                    zzaqwVar.zza("/nativeExpressAssetsLoaded", new l(countDownLatch));
                    zzaqwVar.zza("/nativeExpressAssetsLoadingFailed", new m(countDownLatch));
                    zzxz zzxzVarZzmo = zzxeVar.zzbtx.zzmo();
                    zzyc zzycVarZzmp = zzxeVar.zzbtx.zzmp();
                    if (list.contains("2") && zzxzVarZzmo != null) {
                        final zzoo zzooVar = new zzoo(zzxzVarZzmo.getHeadline(), zzxzVarZzmo.getImages(), zzxzVarZzmo.getBody(), zzxzVarZzmo.zzjz(), zzxzVarZzmo.getCallToAction(), zzxzVarZzmo.getStarRating(), zzxzVarZzmo.getStore(), zzxzVarZzmo.getPrice(), null, zzxzVarZzmo.getExtras(), null, zzxzVarZzmo.zzmw() != null ? (View) com.google.android.gms.c.d.b(zzxzVarZzmo.zzmw()) : null, zzxzVarZzmo.zzke(), null);
                        final String str = zzxeVar.zzbtw.zzbsh;
                        zzaqwVar.zzuf().zza(new zzasd(zzooVar, str, zzaqwVar) { // from class: com.google.android.gms.ads.internal.j
                            private final zzoo bBz;
                            private final String zzzo;
                            private final zzaqw zzzp;

                            {
                                this.bBz = zzooVar;
                                this.zzzo = str;
                                this.zzzp = zzaqwVar;
                            }

                            @Override // com.google.android.gms.internal.ads.zzasd
                            public final void zze(boolean z2) {
                                zzas.a(this.bBz, this.zzzo, this.zzzp, z2);
                            }
                        });
                    } else if (!list.contains(AppEventsConstants.EVENT_PARAM_VALUE_YES) || zzycVarZzmp == null) {
                        zzakb.zzdk("No matching template id and mapper");
                        z = false;
                    } else {
                        final zzoq zzoqVar = new zzoq(zzycVarZzmp.getHeadline(), zzycVarZzmp.getImages(), zzycVarZzmp.getBody(), zzycVarZzmp.zzkg(), zzycVarZzmp.getCallToAction(), zzycVarZzmp.getAdvertiser(), null, zzycVarZzmp.getExtras(), null, zzycVarZzmp.zzmw() != null ? (View) com.google.android.gms.c.d.b(zzycVarZzmp.zzmw()) : null, zzycVarZzmp.zzke(), null);
                        final String str2 = zzxeVar.zzbtw.zzbsh;
                        zzaqwVar.zzuf().zza(new zzasd(zzoqVar, str2, zzaqwVar) { // from class: com.google.android.gms.ads.internal.k
                            private final zzoq bBA;
                            private final String zzzo;
                            private final zzaqw zzzp;

                            {
                                this.bBA = zzoqVar;
                                this.zzzo = str2;
                                this.zzzp = zzaqwVar;
                            }

                            @Override // com.google.android.gms.internal.ads.zzasd
                            public final void zze(boolean z2) {
                                zzas.a(this.bBA, this.zzzo, this.zzzp, z2);
                            }
                        });
                    }
                    String str3 = zzxeVar.zzbtw.zzbsf;
                    String str4 = zzxeVar.zzbtw.zzbsg;
                    if (str4 != null) {
                        zzaqwVar.loadDataWithBaseURL(str4, str3, "text/html", AndroidWebView.UTF_8, null);
                    } else {
                        zzaqwVar.loadData(str3, "text/html", AndroidWebView.UTF_8);
                    }
                    z = true;
                }
            }
        } catch (RemoteException e) {
            zzakb.zzc("Unable to invoke load assets", e);
            z = false;
        } catch (RuntimeException e2) {
            countDownLatch.countDown();
            throw e2;
        }
        if (!z) {
            countDownLatch.countDown();
        }
        return z;
    }

    public static View zze(zzajh zzajhVar) {
        if (zzajhVar == null) {
            zzakb.e("AdState is null");
            return null;
        }
        if (zzf(zzajhVar) && zzajhVar.zzbyo != null) {
            return zzajhVar.zzbyo.getView();
        }
        try {
            com.google.android.gms.c.a view = zzajhVar.zzbtx != null ? zzajhVar.zzbtx.getView() : null;
            if (view != null) {
                return (View) com.google.android.gms.c.d.b(view);
            }
            zzakb.zzdk("View in mediation adapter is null.");
            return null;
        } catch (RemoteException e) {
            zzakb.zzc("Could not get View from mediation adapter.", e);
            return null;
        }
    }

    public static boolean zzf(zzajh zzajhVar) {
        return (zzajhVar == null || !zzajhVar.zzceq || zzajhVar.zzbtw == null || zzajhVar.zzbtw.zzbsf == null) ? false : true;
    }
}
