package com.google.android.gms.internal.ads;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.c.d;
import com.google.android.gms.common.internal.bf;
import java.util.Iterator;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzom extends RelativeLayout {
    private static final float[] zzbhs = {5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f};
    private AnimationDrawable zzbht;

    public zzom(Context context, zzoj zzojVar, RelativeLayout.LayoutParams layoutParams) {
        super(context);
        bf.checkNotNull(zzojVar);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(zzbhs, null, null));
        shapeDrawable.getPaint().setColor(zzojVar.getBackgroundColor());
        setLayoutParams(layoutParams);
        com.google.android.gms.ads.internal.zzbv.zzem().setBackground(this, shapeDrawable);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        if (!TextUtils.isEmpty(zzojVar.getText())) {
            RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
            TextView textView = new TextView(context);
            textView.setLayoutParams(layoutParams3);
            textView.setId(1195835393);
            textView.setTypeface(Typeface.DEFAULT);
            textView.setText(zzojVar.getText());
            textView.setTextColor(zzojVar.getTextColor());
            textView.setTextSize(zzojVar.getTextSize());
            zzkb.zzif();
            int iZza = zzamu.zza(context, 4);
            zzkb.zzif();
            textView.setPadding(iZza, 0, zzamu.zza(context, 4), 0);
            addView(textView);
            layoutParams2.addRule(1, textView.getId());
        }
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(layoutParams2);
        imageView.setId(1195835394);
        List<zzon> listZzjs = zzojVar.zzjs();
        if (listZzjs != null && listZzjs.size() > 1) {
            this.zzbht = new AnimationDrawable();
            Iterator<zzon> it = listZzjs.iterator();
            while (it.hasNext()) {
                try {
                    this.zzbht.addFrame((Drawable) d.b(it.next().zzjy()), zzojVar.zzjt());
                } catch (Exception e) {
                    zzakb.zzb("Error while getting drawable.", e);
                }
            }
            com.google.android.gms.ads.internal.zzbv.zzem().setBackground(imageView, this.zzbht);
        } else if (listZzjs.size() == 1) {
            try {
                imageView.setImageDrawable((Drawable) d.b(listZzjs.get(0).zzjy()));
            } catch (Exception e2) {
                zzakb.zzb("Error while getting drawable.", e2);
            }
        }
        addView(imageView);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        if (this.zzbht != null) {
            this.zzbht.start();
        }
        super.onAttachedToWindow();
    }
}
