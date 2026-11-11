package com.google.android.gms.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.common.d.s;
import com.google.android.gms.common.images.internal.CrossFadingDrawable;
import com.google.android.gms.common.images.internal.ImageUtils;
import com.google.android.gms.common.images.internal.LoadingImageView;
import com.google.android.gms.common.images.internal.PostProcessedResourceCache;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.c;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public abstract class ImageRequest {
    protected int mNoDataPlaceholderResId;
    protected ImageManager.OnImageLoadedListener mOnImageLoadedListener;
    protected int mPostProcessingFlags;
    final zza zzpk;
    protected int mLoadingPlaceholderResId = 0;
    protected boolean mUseNewDrawable = false;
    private boolean zzpl = true;
    private boolean zzpm = false;
    private boolean zzpn = true;

    public final class ImageViewImageRequest extends ImageRequest {
        private WeakReference<ImageView> zzpo;

        public ImageViewImageRequest(ImageView imageView, int i) {
            super(null, i);
            c.A(imageView);
            this.zzpo = new WeakReference<>(imageView);
        }

        public ImageViewImageRequest(ImageView imageView, Uri uri) {
            super(uri, 0);
            c.A(imageView);
            this.zzpo = new WeakReference<>(imageView);
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof ImageViewImageRequest)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            ImageView imageView = this.zzpo.get();
            ImageView imageView2 = ((ImageViewImageRequest) obj).zzpo.get();
            return (imageView2 == null || imageView == null || !bd.b(imageView2, imageView)) ? false : true;
        }

        public final int hashCode() {
            return 0;
        }

        @Override // com.google.android.gms.common.images.ImageRequest
        protected final void loadImage(Drawable drawable, boolean z, boolean z2, boolean z3) {
            ImageView imageView = this.zzpo.get();
            if (imageView != null) {
                boolean z4 = (z2 || z3) ? false : true;
                if (z4 && (imageView instanceof LoadingImageView)) {
                    int loadedNoDataPlaceholderResId = ((LoadingImageView) imageView).getLoadedNoDataPlaceholderResId();
                    if (this.mNoDataPlaceholderResId != 0 && loadedNoDataPlaceholderResId == this.mNoDataPlaceholderResId) {
                        return;
                    }
                }
                boolean zShouldCrossFade = shouldCrossFade(z, z2);
                Drawable drawableNewDrawable = (!this.mUseNewDrawable || drawable == null) ? drawable : drawable.getConstantState().newDrawable();
                if (zShouldCrossFade) {
                    drawableNewDrawable = createTransitionDrawable(imageView.getDrawable(), drawableNewDrawable);
                }
                imageView.setImageDrawable(drawableNewDrawable);
                if (imageView instanceof LoadingImageView) {
                    LoadingImageView loadingImageView = (LoadingImageView) imageView;
                    loadingImageView.setLoadedUri(z3 ? this.zzpk.uri : null);
                    loadingImageView.setLoadedNoDataPlaceholderResId(z4 ? this.mNoDataPlaceholderResId : 0);
                }
                if (zShouldCrossFade) {
                    ((CrossFadingDrawable) drawableNewDrawable).startTransition(250);
                }
            }
        }
    }

    public final class ListenerImageRequest extends ImageRequest {
        private WeakReference<ImageManager.OnImageLoadedListener> zzpp;

        public ListenerImageRequest(ImageManager.OnImageLoadedListener onImageLoadedListener, Uri uri) {
            super(uri, 0);
            c.A(onImageLoadedListener);
            this.zzpp = new WeakReference<>(onImageLoadedListener);
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof ListenerImageRequest)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            ListenerImageRequest listenerImageRequest = (ListenerImageRequest) obj;
            ImageManager.OnImageLoadedListener onImageLoadedListener = this.zzpp.get();
            ImageManager.OnImageLoadedListener onImageLoadedListener2 = listenerImageRequest.zzpp.get();
            return onImageLoadedListener2 != null && onImageLoadedListener != null && bd.b(onImageLoadedListener2, onImageLoadedListener) && bd.b(listenerImageRequest.zzpk, this.zzpk);
        }

        public final int hashCode() {
            return bd.hashCode(this.zzpk);
        }

        @Override // com.google.android.gms.common.images.ImageRequest
        protected final void loadImage(Drawable drawable, boolean z, boolean z2, boolean z3) {
            ImageManager.OnImageLoadedListener onImageLoadedListener;
            if (z2 || (onImageLoadedListener = this.zzpp.get()) == null) {
                return;
            }
            onImageLoadedListener.onImageLoaded(this.zzpk.uri, drawable, z3);
        }
    }

    public final class PostProcessingFlags {
        public static final int CIRCLE_CROP = 1;
    }

    public final class TextViewImageRequest extends ImageRequest {
        public static final int POSITION_BOTTOM = 3;
        public static final int POSITION_END = 2;
        public static final int POSITION_START = 0;
        public static final int POSITION_TOP = 1;
        private WeakReference<TextView> zzpq;
        private int zzpr;

        public TextViewImageRequest(TextView textView, int i, int i2) {
            super(null, i2);
            this.zzpr = -1;
            c.A(textView);
            c.a((i == 0 || i == 1 || i == 2 || i == 3) ? false : true, new StringBuilder(29).append("Invalid position: ").append(i).toString());
            this.zzpq = new WeakReference<>(textView);
            this.zzpr = i;
        }

        public TextViewImageRequest(TextView textView, int i, Uri uri) {
            super(uri, 0);
            this.zzpr = -1;
            c.A(textView);
            c.a((i == 0 || i == 1 || i == 2 || i == 3) ? false : true, new StringBuilder(29).append("Invalid position: ").append(i).toString());
            this.zzpq = new WeakReference<>(textView);
            this.zzpr = i;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof TextViewImageRequest)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            TextViewImageRequest textViewImageRequest = (TextViewImageRequest) obj;
            TextView textView = this.zzpq.get();
            TextView textView2 = textViewImageRequest.zzpq.get();
            return textView2 != null && textView != null && bd.b(textView2, textView) && bd.b(Integer.valueOf(textViewImageRequest.zzpr), Integer.valueOf(this.zzpr));
        }

        public final int hashCode() {
            return bd.hashCode(Integer.valueOf(this.zzpr));
        }

        @Override // com.google.android.gms.common.images.ImageRequest
        protected final void loadImage(Drawable drawable, boolean z, boolean z2, boolean z3) {
            TextView textView = this.zzpq.get();
            if (textView != null) {
                int i = this.zzpr;
                boolean zShouldCrossFade = shouldCrossFade(z, z2);
                Drawable[] compoundDrawablesRelative = s.aby() ? textView.getCompoundDrawablesRelative() : textView.getCompoundDrawables();
                Drawable drawableCreateTransitionDrawable = zShouldCrossFade ? createTransitionDrawable(compoundDrawablesRelative[i], drawable) : drawable;
                Drawable drawable2 = i == 0 ? drawableCreateTransitionDrawable : compoundDrawablesRelative[0];
                Drawable drawable3 = i == 1 ? drawableCreateTransitionDrawable : compoundDrawablesRelative[1];
                Drawable drawable4 = i == 2 ? drawableCreateTransitionDrawable : compoundDrawablesRelative[2];
                Drawable drawable5 = i == 3 ? drawableCreateTransitionDrawable : compoundDrawablesRelative[3];
                if (s.aby()) {
                    textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
                } else {
                    textView.setCompoundDrawablesWithIntrinsicBounds(drawable2, drawable3, drawable4, drawable5);
                }
                if (zShouldCrossFade) {
                    ((CrossFadingDrawable) drawableCreateTransitionDrawable).startTransition(250);
                }
            }
        }
    }

    final class zza {
        public final Uri uri;

        public zza(Uri uri) {
            this.uri = uri;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            return bd.b(((zza) obj).uri, this.uri);
        }

        public final int hashCode() {
            return bd.hashCode(this.uri);
        }
    }

    public ImageRequest(Uri uri, int i) {
        this.mNoDataPlaceholderResId = 0;
        this.zzpk = new zza(uri);
        this.mNoDataPlaceholderResId = i;
    }

    private final Drawable zza(Context context, PostProcessedResourceCache postProcessedResourceCache, int i) {
        Resources resources = context.getResources();
        if (this.mPostProcessingFlags <= 0) {
            return resources.getDrawable(i);
        }
        PostProcessedResourceCache.PostProcessedResource postProcessedResource = new PostProcessedResourceCache.PostProcessedResource(i, this.mPostProcessingFlags);
        Drawable drawable = postProcessedResourceCache.get(postProcessedResource);
        if (drawable != null) {
            return drawable;
        }
        Drawable drawable2 = resources.getDrawable(i);
        if ((this.mPostProcessingFlags & 1) != 0) {
            drawable2 = frameDrawableInCircle(resources, drawable2);
        }
        postProcessedResourceCache.put(postProcessedResource, drawable2);
        return drawable2;
    }

    protected CrossFadingDrawable createTransitionDrawable(Drawable drawable, Drawable drawable2) {
        if (drawable == null) {
            drawable = null;
        } else if (drawable instanceof CrossFadingDrawable) {
            drawable = ((CrossFadingDrawable) drawable).getEndDrawable();
        }
        return new CrossFadingDrawable(drawable, drawable2);
    }

    protected Drawable frameDrawableInCircle(Resources resources, Drawable drawable) {
        return ImageUtils.frameDrawableInCircle(resources, drawable);
    }

    protected abstract void loadImage(Drawable drawable, boolean z, boolean z2, boolean z3);

    public void setCrossFadeAlwaysEnabled(boolean z) {
        this.zzpm = z;
        if (z) {
            setCrossFadeEnabled(true);
        }
    }

    public void setCrossFadeEnabled(boolean z) {
        this.zzpl = z;
    }

    public void setLoadingPlaceholder(int i) {
        this.mLoadingPlaceholderResId = i;
    }

    public void setLoadingPlaceholderEnabled(boolean z) {
        this.zzpn = z;
    }

    public void setNoDataPlaceholder(int i) {
        this.mNoDataPlaceholderResId = i;
    }

    public void setOnImageLoadedListener(ImageManager.OnImageLoadedListener onImageLoadedListener) {
        this.mOnImageLoadedListener = onImageLoadedListener;
    }

    public void setPostProcessingFlags(int i) {
        this.mPostProcessingFlags = i;
    }

    public void setUseNewDrawable(boolean z) {
        this.mUseNewDrawable = z;
    }

    protected boolean shouldCrossFade(boolean z, boolean z2) {
        return this.zzpl && !z2 && (!z || this.zzpm);
    }

    final void zza(Context context, Bitmap bitmap, boolean z) {
        c.A(bitmap);
        if ((this.mPostProcessingFlags & 1) != 0) {
            bitmap = ImageUtils.frameBitmapInCircle(bitmap);
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        if (this.mOnImageLoadedListener != null) {
            this.mOnImageLoadedListener.onImageLoaded(this.zzpk.uri, bitmapDrawable, true);
        }
        loadImage(bitmapDrawable, z, false, true);
    }

    final void zza(Context context, PostProcessedResourceCache postProcessedResourceCache) {
        if (this.zzpn) {
            loadImage(this.mLoadingPlaceholderResId != 0 ? zza(context, postProcessedResourceCache, this.mLoadingPlaceholderResId) : null, false, true, false);
        }
    }

    final void zza(Context context, PostProcessedResourceCache postProcessedResourceCache, boolean z) {
        Drawable drawableZza = this.mNoDataPlaceholderResId != 0 ? zza(context, postProcessedResourceCache, this.mNoDataPlaceholderResId) : null;
        if (this.mOnImageLoadedListener != null) {
            this.mOnImageLoadedListener.onImageLoaded(this.zzpk.uri, drawableZza, false);
        }
        loadImage(drawableZza, z, false, false);
    }
}
