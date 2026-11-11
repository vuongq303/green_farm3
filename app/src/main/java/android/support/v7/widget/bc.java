package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;

/* JADX INFO: compiled from: AppCompatTextViewAutoSizeHelper.java */
/* JADX INFO: loaded from: classes.dex */
class bc {
    private static final RectF uK = new RectF();
    private static Hashtable<String, Method> uL = new Hashtable<>();
    private final Context mContext;
    private int uM = 0;
    private boolean uN = false;
    private float uO = -1.0f;
    private float uP = -1.0f;
    private float uQ = -1.0f;
    private int[] uR = new int[0];
    private boolean uS = false;
    private TextPaint uT;
    private final TextView uU;

    bc(TextView textView) {
        this.uU = textView;
        this.mContext = this.uU.getContext();
    }

    void a(AttributeSet attributeSet, int i) {
        int resourceId;
        TypedArray typedArrayObtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.AppCompatTextView, i, 0);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizeTextType)) {
            this.uM = typedArrayObtainStyledAttributes.getInt(R.styleable.AppCompatTextView_autoSizeTextType, 0);
        }
        float dimension = typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizeStepGranularity) ? typedArrayObtainStyledAttributes.getDimension(R.styleable.AppCompatTextView_autoSizeStepGranularity, -1.0f) : -1.0f;
        float dimension2 = typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizeMinTextSize) ? typedArrayObtainStyledAttributes.getDimension(R.styleable.AppCompatTextView_autoSizeMinTextSize, -1.0f) : -1.0f;
        float dimension3 = typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizeMaxTextSize) ? typedArrayObtainStyledAttributes.getDimension(R.styleable.AppCompatTextView_autoSizeMaxTextSize, -1.0f) : -1.0f;
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizePresetSizes) && (resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextView_autoSizePresetSizes, 0)) > 0) {
            TypedArray typedArrayObtainTypedArray = typedArrayObtainStyledAttributes.getResources().obtainTypedArray(resourceId);
            a(typedArrayObtainTypedArray);
            typedArrayObtainTypedArray.recycle();
        }
        typedArrayObtainStyledAttributes.recycle();
        if (es()) {
            if (this.uM == 1) {
                if (!this.uS) {
                    DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
                    if (dimension2 == -1.0f) {
                        dimension2 = TypedValue.applyDimension(2, 12.0f, displayMetrics);
                    }
                    if (dimension3 == -1.0f) {
                        dimension3 = TypedValue.applyDimension(2, 112.0f, displayMetrics);
                    }
                    if (dimension == -1.0f) {
                        dimension = 1.0f;
                    }
                    b(dimension2, dimension3, dimension);
                }
                eq();
                return;
            }
            return;
        }
        this.uM = 0;
    }

    void setAutoSizeTextTypeWithDefaults(int i) {
        if (es()) {
            switch (i) {
                case 0:
                    er();
                    return;
                case 1:
                    DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
                    b(TypedValue.applyDimension(2, 12.0f, displayMetrics), TypedValue.applyDimension(2, 112.0f, displayMetrics), 1.0f);
                    if (eq()) {
                        en();
                        return;
                    }
                    return;
                default:
                    throw new IllegalArgumentException("Unknown auto-size text type: " + i);
            }
        }
    }

    void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) {
        if (es()) {
            DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
            b(TypedValue.applyDimension(i4, i, displayMetrics), TypedValue.applyDimension(i4, i2, displayMetrics), TypedValue.applyDimension(i4, i3, displayMetrics));
            if (eq()) {
                en();
            }
        }
    }

    void setAutoSizeTextTypeUniformWithPresetSizes(int[] iArr, int i) {
        if (es()) {
            int length = iArr.length;
            if (length > 0) {
                int[] iArrCopyOf = new int[length];
                if (i == 0) {
                    iArrCopyOf = Arrays.copyOf(iArr, length);
                } else {
                    DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
                    for (int i2 = 0; i2 < length; i2++) {
                        iArrCopyOf[i2] = Math.round(TypedValue.applyDimension(i, iArr[i2], displayMetrics));
                    }
                }
                this.uR = b(iArrCopyOf);
                if (!ep()) {
                    throw new IllegalArgumentException("None of the preset sizes is valid: " + Arrays.toString(iArr));
                }
            } else {
                this.uS = false;
            }
            if (eq()) {
                en();
            }
        }
    }

    int getAutoSizeTextType() {
        return this.uM;
    }

    int getAutoSizeStepGranularity() {
        return Math.round(this.uO);
    }

    int getAutoSizeMinTextSize() {
        return Math.round(this.uP);
    }

    int getAutoSizeMaxTextSize() {
        return Math.round(this.uQ);
    }

    int[] getAutoSizeTextAvailableSizes() {
        return this.uR;
    }

    private void a(TypedArray typedArray) {
        int length = typedArray.length();
        int[] iArr = new int[length];
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                iArr[i] = typedArray.getDimensionPixelSize(i, -1);
            }
            this.uR = b(iArr);
            ep();
        }
    }

    private boolean ep() {
        int length = this.uR.length;
        this.uS = length > 0;
        if (this.uS) {
            this.uM = 1;
            this.uP = this.uR[0];
            this.uQ = this.uR[length - 1];
            this.uO = -1.0f;
        }
        return this.uS;
    }

    private int[] b(int[] iArr) {
        int length = iArr.length;
        if (length != 0) {
            Arrays.sort(iArr);
            ArrayList arrayList = new ArrayList();
            for (int i : iArr) {
                if (i > 0 && Collections.binarySearch(arrayList, Integer.valueOf(i)) < 0) {
                    arrayList.add(Integer.valueOf(i));
                }
            }
            if (length != arrayList.size()) {
                int size = arrayList.size();
                iArr = new int[size];
                for (int i2 = 0; i2 < size; i2++) {
                    iArr[i2] = ((Integer) arrayList.get(i2)).intValue();
                }
            }
        }
        return iArr;
    }

    private void b(float f, float f2, float f3) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException("Minimum auto-size text size (" + f + "px) is less or equal to (0px)");
        }
        if (f2 <= f) {
            throw new IllegalArgumentException("Maximum auto-size text size (" + f2 + "px) is less or equal to minimum auto-size text size (" + f + "px)");
        }
        if (f3 <= 0.0f) {
            throw new IllegalArgumentException("The auto-size step granularity (" + f3 + "px) is less or equal to (0px)");
        }
        this.uM = 1;
        this.uP = f;
        this.uQ = f2;
        this.uO = f3;
        this.uS = false;
    }

    private boolean eq() {
        if (es() && this.uM == 1) {
            if (!this.uS || this.uR.length == 0) {
                float fRound = Math.round(this.uP);
                int i = 1;
                while (Math.round(this.uO + fRound) <= Math.round(this.uQ)) {
                    i++;
                    fRound += this.uO;
                }
                int[] iArr = new int[i];
                float f = this.uP;
                int i2 = 0;
                while (i2 < i) {
                    iArr[i2] = Math.round(f);
                    i2++;
                    f = this.uO + f;
                }
                this.uR = b(iArr);
            }
            this.uN = true;
        } else {
            this.uN = false;
        }
        return this.uN;
    }

    void en() {
        if (eo()) {
            if (this.uN) {
                if (this.uU.getMeasuredHeight() > 0 && this.uU.getMeasuredWidth() > 0) {
                    int measuredWidth = ((Boolean) a(this.uU, "getHorizontallyScrolling", false)).booleanValue() ? 1048576 : (this.uU.getMeasuredWidth() - this.uU.getTotalPaddingLeft()) - this.uU.getTotalPaddingRight();
                    int height = (this.uU.getHeight() - this.uU.getCompoundPaddingBottom()) - this.uU.getCompoundPaddingTop();
                    if (measuredWidth > 0 && height > 0) {
                        synchronized (uK) {
                            uK.setEmpty();
                            uK.right = measuredWidth;
                            uK.bottom = height;
                            float fA = a(uK);
                            if (fA != this.uU.getTextSize()) {
                                b(0, fA);
                            }
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
            this.uN = true;
        }
    }

    private void er() {
        this.uM = 0;
        this.uP = -1.0f;
        this.uQ = -1.0f;
        this.uO = -1.0f;
        this.uR = new int[0];
        this.uN = false;
    }

    void b(int i, float f) {
        Resources resources;
        if (this.mContext == null) {
            resources = Resources.getSystem();
        } else {
            resources = this.mContext.getResources();
        }
        d(TypedValue.applyDimension(i, f, resources.getDisplayMetrics()));
    }

    private void d(float f) {
        if (f != this.uU.getPaint().getTextSize()) {
            this.uU.getPaint().setTextSize(f);
            boolean zIsInLayout = Build.VERSION.SDK_INT >= 18 ? this.uU.isInLayout() : false;
            if (this.uU.getLayout() != null) {
                this.uN = false;
                try {
                    Method methodM = m("nullLayouts");
                    if (methodM != null) {
                        methodM.invoke(this.uU, new Object[0]);
                    }
                } catch (Exception e) {
                    Log.w("ACTVAutoSizeHelper", "Failed to invoke TextView#nullLayouts() method", e);
                }
                if (!zIsInLayout) {
                    this.uU.requestLayout();
                } else {
                    this.uU.forceLayout();
                }
                this.uU.invalidate();
            }
        }
    }

    private int a(RectF rectF) {
        int length = this.uR.length;
        if (length == 0) {
            throw new IllegalStateException("No available text sizes to choose from.");
        }
        int i = 0;
        int i2 = length - 1;
        int i3 = 1;
        while (i3 <= i2) {
            int i4 = (i3 + i2) / 2;
            if (a(this.uR[i4], rectF)) {
                i = i3;
                i3 = i4 + 1;
            } else {
                int i5 = i4 - 1;
                i2 = i5;
                i = i5;
            }
        }
        return this.uR[i];
    }

    private boolean a(int i, RectF rectF) {
        StaticLayout staticLayoutA;
        CharSequence text = this.uU.getText();
        int maxLines = Build.VERSION.SDK_INT >= 16 ? this.uU.getMaxLines() : -1;
        if (this.uT == null) {
            this.uT = new TextPaint();
        } else {
            this.uT.reset();
        }
        this.uT.set(this.uU.getPaint());
        this.uT.setTextSize(i);
        Layout.Alignment alignment = (Layout.Alignment) a(this.uU, "getLayoutAlignment", Layout.Alignment.ALIGN_NORMAL);
        if (Build.VERSION.SDK_INT >= 23) {
            staticLayoutA = a(text, alignment, Math.round(rectF.right), maxLines);
        } else {
            staticLayoutA = a(text, alignment, Math.round(rectF.right));
        }
        if (maxLines == -1 || (staticLayoutA.getLineCount() <= maxLines && staticLayoutA.getLineEnd(staticLayoutA.getLineCount() - 1) == text.length())) {
            return ((float) staticLayoutA.getHeight()) <= rectF.bottom;
        }
        return false;
    }

    private StaticLayout a(CharSequence charSequence, Layout.Alignment alignment, int i, int i2) {
        TextDirectionHeuristic textDirectionHeuristic = (TextDirectionHeuristic) a(this.uU, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR);
        StaticLayout.Builder hyphenationFrequency = StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), this.uT, i).setAlignment(alignment).setLineSpacing(this.uU.getLineSpacingExtra(), this.uU.getLineSpacingMultiplier()).setIncludePad(this.uU.getIncludeFontPadding()).setBreakStrategy(this.uU.getBreakStrategy()).setHyphenationFrequency(this.uU.getHyphenationFrequency());
        if (i2 == -1) {
            i2 = Integer.MAX_VALUE;
        }
        return hyphenationFrequency.setMaxLines(i2).setTextDirection(textDirectionHeuristic).build();
    }

    private StaticLayout a(CharSequence charSequence, Layout.Alignment alignment, int i) {
        float fFloatValue;
        float fFloatValue2;
        boolean zBooleanValue;
        if (Build.VERSION.SDK_INT >= 16) {
            fFloatValue = this.uU.getLineSpacingMultiplier();
            fFloatValue2 = this.uU.getLineSpacingExtra();
            zBooleanValue = this.uU.getIncludeFontPadding();
        } else {
            fFloatValue = ((Float) a(this.uU, "getLineSpacingMultiplier", Float.valueOf(1.0f))).floatValue();
            fFloatValue2 = ((Float) a(this.uU, "getLineSpacingExtra", Float.valueOf(0.0f))).floatValue();
            zBooleanValue = ((Boolean) a(this.uU, "getIncludeFontPadding", true)).booleanValue();
        }
        return new StaticLayout(charSequence, this.uT, i, alignment, fFloatValue, fFloatValue2, zBooleanValue);
    }

    private <T> T a(Object obj, String str, T t) {
        boolean z = false;
        try {
            try {
                T t2 = (T) m(str).invoke(obj, new Object[0]);
                if (t2 == null) {
                }
                return t2;
            } catch (Exception e) {
                z = true;
                Log.w("ACTVAutoSizeHelper", "Failed to invoke TextView#" + str + "() method", e);
                if (0 != 0) {
                    return null;
                }
                return t;
            }
        } catch (Throwable th) {
            if (0 != 0 || z) {
            }
            throw th;
        }
    }

    private Method m(String str) {
        try {
            Method method = uL.get(str);
            if (method == null) {
                Method declaredMethod = TextView.class.getDeclaredMethod(str, new Class[0]);
                if (declaredMethod != null) {
                    declaredMethod.setAccessible(true);
                    uL.put(str, declaredMethod);
                    return declaredMethod;
                }
                return declaredMethod;
            }
            return method;
        } catch (Exception e) {
            Log.w("ACTVAutoSizeHelper", "Failed to retrieve TextView#" + str + "() method", e);
            return null;
        }
    }

    boolean eo() {
        return es() && this.uM != 0;
    }

    private boolean es() {
        return !(this.uU instanceof ah);
    }
}
