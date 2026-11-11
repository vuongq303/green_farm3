package android.support.v7.widget;

import android.R;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/* JADX INFO: compiled from: AppCompatProgressBarHelper.java */
/* JADX INFO: loaded from: classes.dex */
class an {
    private static final int[] pY = {R.attr.indeterminateDrawable, R.attr.progressDrawable};
    private final ProgressBar ud;
    private Bitmap ue;

    an(ProgressBar progressBar) {
        this.ud = progressBar;
    }

    void a(AttributeSet attributeSet, int i) {
        da daVarA = da.a(this.ud.getContext(), attributeSet, pY, i, 0);
        Drawable drawableAu = daVarA.au(0);
        if (drawableAu != null) {
            this.ud.setIndeterminateDrawable(l(drawableAu));
        }
        Drawable drawableAu2 = daVarA.au(1);
        if (drawableAu2 != null) {
            this.ud.setProgressDrawable(b(drawableAu2, false));
        }
        daVarA.recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Drawable b(Drawable drawable, boolean z) {
        if (drawable instanceof android.support.v4.a.a.g) {
            Drawable drawableAt = ((android.support.v4.a.a.g) drawable).at();
            if (drawableAt != null) {
                ((android.support.v4.a.a.g) drawable).e(b(drawableAt, z));
            }
        } else {
            if (drawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                Drawable[] drawableArr = new Drawable[numberOfLayers];
                for (int i = 0; i < numberOfLayers; i++) {
                    int id = layerDrawable.getId(i);
                    drawableArr[i] = b(layerDrawable.getDrawable(i), id == 16908301 || id == 16908303);
                }
                LayerDrawable layerDrawable2 = new LayerDrawable(drawableArr);
                for (int i2 = 0; i2 < numberOfLayers; i2++) {
                    layerDrawable2.setId(i2, layerDrawable.getId(i2));
                }
                return layerDrawable2;
            }
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (this.ue == null) {
                    this.ue = bitmap;
                }
                ShapeDrawable shapeDrawable = new ShapeDrawable(ei());
                shapeDrawable.getPaint().setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP));
                shapeDrawable.getPaint().setColorFilter(bitmapDrawable.getPaint().getColorFilter());
                return z ? new ClipDrawable(shapeDrawable, 3, 1) : shapeDrawable;
            }
        }
        return drawable;
    }

    private Drawable l(Drawable drawable) {
        if (drawable instanceof AnimationDrawable) {
            AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
            int numberOfFrames = animationDrawable.getNumberOfFrames();
            AnimationDrawable animationDrawable2 = new AnimationDrawable();
            animationDrawable2.setOneShot(animationDrawable.isOneShot());
            for (int i = 0; i < numberOfFrames; i++) {
                Drawable drawableB = b(animationDrawable.getFrame(i), true);
                drawableB.setLevel(10000);
                animationDrawable2.addFrame(drawableB, animationDrawable.getDuration(i));
            }
            animationDrawable2.setLevel(10000);
            return animationDrawable2;
        }
        return drawable;
    }

    private Shape ei() {
        return new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null);
    }

    Bitmap ej() {
        return this.ue;
    }
}
