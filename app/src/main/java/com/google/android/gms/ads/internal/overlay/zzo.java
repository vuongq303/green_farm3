package com.google.android.gms.ads.internal.overlay;

import android.R;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzamu;
import com.google.android.gms.internal.ads.zzkb;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzo extends FrameLayout implements View.OnClickListener {
    private final ImageButton bAL;
    private final zzw bAM;

    public zzo(Context context, g gVar, @Nullable zzw zzwVar) {
        super(context);
        this.bAM = zzwVar;
        setOnClickListener(this);
        this.bAL = new ImageButton(context);
        this.bAL.setImageResource(R.drawable.btn_dialog);
        this.bAL.setBackgroundColor(0);
        this.bAL.setOnClickListener(this);
        ImageButton imageButton = this.bAL;
        zzkb.zzif();
        int iZza = zzamu.zza(context, gVar.paddingLeft);
        zzkb.zzif();
        int iZza2 = zzamu.zza(context, 0);
        zzkb.zzif();
        int iZza3 = zzamu.zza(context, gVar.paddingRight);
        zzkb.zzif();
        imageButton.setPadding(iZza, iZza2, iZza3, zzamu.zza(context, gVar.paddingBottom));
        this.bAL.setContentDescription("Interstitial close button");
        zzkb.zzif();
        zzamu.zza(context, gVar.size);
        ImageButton imageButton2 = this.bAL;
        zzkb.zzif();
        int iZza4 = zzamu.zza(context, gVar.size + gVar.paddingLeft + gVar.paddingRight);
        zzkb.zzif();
        addView(imageButton2, new FrameLayout.LayoutParams(iZza4, zzamu.zza(context, gVar.size + gVar.paddingBottom), 17));
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (this.bAM != null) {
            this.bAM.zzni();
        }
    }

    public final void zzu(boolean z) {
        if (z) {
            this.bAL.setVisibility(8);
        } else {
            this.bAL.setVisibility(0);
        }
    }
}
