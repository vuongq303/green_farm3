package com.google.android.gms.internal.ads;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.webkit.WebSettings;
import com.google.android.gms.common.d.d;
import com.google.android.gms.common.d.w;
import com.google.android.gms.common.m;

/* JADX INFO: loaded from: classes.dex */
public class zzakw extends zzaky {
    @Override // com.google.android.gms.internal.ads.zzakq
    public final String getDefaultUserAgent(Context context) {
        zzamn zzamnVarZzsb = zzamn.zzsb();
        if (TextUtils.isEmpty(zzamnVarZzsb.zzcpq)) {
            zzamnVarZzsb.zzcpq = d.abm() ? (String) zzaml.zza(context, new zzamo(zzamnVarZzsb, context)) : (String) zzaml.zza(context, new zzamp(zzamnVarZzsb, m.getRemoteContext(context), context));
        }
        return zzamnVarZzsb.zzcpq;
    }

    @Override // com.google.android.gms.internal.ads.zzakq
    public final Drawable zza(Context context, Bitmap bitmap, boolean z, float f) {
        if (!z || f <= 0.0f || f > 25.0f) {
            return new BitmapDrawable(context.getResources(), bitmap);
        }
        try {
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapCreateScaledBitmap);
            RenderScript renderScriptCreate = RenderScript.create(context);
            ScriptIntrinsicBlur scriptIntrinsicBlurCreate = ScriptIntrinsicBlur.create(renderScriptCreate, Element.U8_4(renderScriptCreate));
            Allocation allocationCreateFromBitmap = Allocation.createFromBitmap(renderScriptCreate, bitmapCreateScaledBitmap);
            Allocation allocationCreateFromBitmap2 = Allocation.createFromBitmap(renderScriptCreate, bitmapCreateBitmap);
            scriptIntrinsicBlurCreate.setRadius(f);
            scriptIntrinsicBlurCreate.setInput(allocationCreateFromBitmap);
            scriptIntrinsicBlurCreate.forEach(allocationCreateFromBitmap2);
            allocationCreateFromBitmap2.copyTo(bitmapCreateBitmap);
            return new BitmapDrawable(context.getResources(), bitmapCreateBitmap);
        } catch (RuntimeException e) {
            return new BitmapDrawable(context.getResources(), bitmap);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaky, com.google.android.gms.internal.ads.zzakt, com.google.android.gms.internal.ads.zzakq
    public final boolean zza(Context context, WebSettings webSettings) {
        super.zza(context, webSettings);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        return true;
    }

    @Override // com.google.android.gms.internal.ads.zzakq
    public final void zzaw(Context context) {
        zzamn zzamnVarZzsb = zzamn.zzsb();
        zzakb.v("Updating user agent.");
        String defaultUserAgent = WebSettings.getDefaultUserAgent(context);
        if (!defaultUserAgent.equals(zzamnVarZzsb.zzcpq)) {
            Context remoteContext = m.getRemoteContext(context);
            if (d.abm() || remoteContext == null) {
                SharedPreferences.Editor editorPutString = context.getSharedPreferences("admob_user_agent", 0).edit().putString("user_agent", WebSettings.getDefaultUserAgent(context));
                if (remoteContext == null) {
                    editorPutString.apply();
                } else {
                    w.a(context, editorPutString, "admob_user_agent");
                }
            }
            zzamnVarZzsb.zzcpq = defaultUserAgent;
        }
        zzakb.v("User agent is updated.");
    }
}
