package android.support.v7.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

/* JADX INFO: compiled from: AppCompatSpinner.java */
/* JADX INFO: loaded from: classes.dex */
public class as extends Spinner implements android.support.v4.view.z {
    private static final int[] un = {R.attr.spinnerMode};
    private final Rect iH;
    private bt pd;
    private final Context rz;
    private final x tq;
    private SpinnerAdapter uo;
    private final boolean up;
    private av uq;
    private int ur;

    public as(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.support.v7.appcompat.R.attr.spinnerStyle);
    }

    public as(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, -1);
    }

    public as(Context context, AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i, i2, null);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x004b  */
    /* JADX WARN: Code duplicated, block: B:22:0x0089  */
    /* JADX WARN: Code duplicated, block: B:25:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:44:0x00e3  */
    public as(Context context, AttributeSet attributeSet, int i, int i2, Resources.Theme theme) throws Throwable {
        CharSequence[] textArray;
        TypedArray typedArrayObtainStyledAttributes;
        super(context, attributeSet, i);
        this.iH = new Rect();
        da daVarA = da.a(context, attributeSet, android.support.v7.appcompat.R.styleable.Spinner, i, 0);
        this.tq = new x(this);
        if (theme != null) {
            this.rz = new android.support.v7.c.e(context, theme);
        } else {
            int resourceId = daVarA.getResourceId(android.support.v7.appcompat.R.styleable.Spinner_popupTheme, 0);
            if (resourceId != 0) {
                this.rz = new android.support.v7.c.e(context, resourceId);
            } else {
                this.rz = Build.VERSION.SDK_INT < 23 ? context : null;
            }
        }
        if (this.rz != null) {
            if (i2 == -1) {
                if (Build.VERSION.SDK_INT >= 11) {
                    try {
                        typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, un, i, 0);
                        try {
                            try {
                                i2 = typedArrayObtainStyledAttributes.hasValue(0) ? typedArrayObtainStyledAttributes.getInt(0, 0) : i2;
                                if (typedArrayObtainStyledAttributes != null) {
                                    typedArrayObtainStyledAttributes.recycle();
                                }
                            } catch (Throwable th) {
                                th = th;
                                if (typedArrayObtainStyledAttributes != null) {
                                    typedArrayObtainStyledAttributes.recycle();
                                }
                                throw th;
                            }
                        } catch (Exception e) {
                            e = e;
                            Log.i("AppCompatSpinner", "Could not read android:spinnerMode", e);
                            if (typedArrayObtainStyledAttributes != null) {
                                typedArrayObtainStyledAttributes.recycle();
                            }
                            if (i2 == 1) {
                                av avVar = new av(this, this.rz, attributeSet, i);
                                da daVarA2 = da.a(this.rz, attributeSet, android.support.v7.appcompat.R.styleable.Spinner, i, 0);
                                this.ur = daVarA2.getLayoutDimension(android.support.v7.appcompat.R.styleable.Spinner_android_dropDownWidth, -2);
                                avVar.setBackgroundDrawable(daVarA2.getDrawable(android.support.v7.appcompat.R.styleable.Spinner_android_popupBackground));
                                avVar.l(daVarA.getString(android.support.v7.appcompat.R.styleable.Spinner_android_prompt));
                                daVarA2.recycle();
                                this.uq = avVar;
                                this.pd = new at(this, this, avVar);
                            }
                            textArray = daVarA.getTextArray(android.support.v7.appcompat.R.styleable.Spinner_android_entries);
                            if (textArray != null) {
                                ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.simple_spinner_item, textArray);
                                arrayAdapter.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
                                setAdapter((SpinnerAdapter) arrayAdapter);
                            }
                            daVarA.recycle();
                            this.up = true;
                            if (this.uo != null) {
                                setAdapter(this.uo);
                                this.uo = null;
                            }
                            this.tq.a(attributeSet, i);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        typedArrayObtainStyledAttributes = null;
                    } catch (Throwable th2) {
                        th = th2;
                        typedArrayObtainStyledAttributes = null;
                        if (typedArrayObtainStyledAttributes != null) {
                            typedArrayObtainStyledAttributes.recycle();
                        }
                        throw th;
                    }
                } else {
                    i2 = 1;
                }
            }
            if (i2 == 1) {
                av avVar2 = new av(this, this.rz, attributeSet, i);
                da daVarA3 = da.a(this.rz, attributeSet, android.support.v7.appcompat.R.styleable.Spinner, i, 0);
                this.ur = daVarA3.getLayoutDimension(android.support.v7.appcompat.R.styleable.Spinner_android_dropDownWidth, -2);
                avVar2.setBackgroundDrawable(daVarA3.getDrawable(android.support.v7.appcompat.R.styleable.Spinner_android_popupBackground));
                avVar2.l(daVarA.getString(android.support.v7.appcompat.R.styleable.Spinner_android_prompt));
                daVarA3.recycle();
                this.uq = avVar2;
                this.pd = new at(this, this, avVar2);
            }
        }
        textArray = daVarA.getTextArray(android.support.v7.appcompat.R.styleable.Spinner_android_entries);
        if (textArray != null) {
            ArrayAdapter arrayAdapter2 = new ArrayAdapter(context, R.layout.simple_spinner_item, textArray);
            arrayAdapter2.setDropDownViewResource(android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item);
            setAdapter((SpinnerAdapter) arrayAdapter2);
        }
        daVarA.recycle();
        this.up = true;
        if (this.uo != null) {
            setAdapter(this.uo);
            this.uo = null;
        }
        this.tq.a(attributeSet, i);
    }

    @Override // android.widget.Spinner
    public Context getPopupContext() {
        if (this.uq != null) {
            return this.rz;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            return super.getPopupContext();
        }
        return null;
    }

    @Override // android.widget.Spinner
    public void setPopupBackgroundDrawable(Drawable drawable) {
        if (this.uq != null) {
            this.uq.setBackgroundDrawable(drawable);
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setPopupBackgroundDrawable(drawable);
        }
    }

    @Override // android.widget.Spinner
    public void setPopupBackgroundResource(int i) {
        setPopupBackgroundDrawable(android.support.v7.a.a.b.a(getPopupContext(), i));
    }

    @Override // android.widget.Spinner
    public Drawable getPopupBackground() {
        if (this.uq != null) {
            return this.uq.getBackground();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getPopupBackground();
        }
        return null;
    }

    @Override // android.widget.Spinner
    public void setDropDownVerticalOffset(int i) {
        if (this.uq != null) {
            this.uq.setVerticalOffset(i);
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownVerticalOffset(i);
        }
    }

    @Override // android.widget.Spinner
    public int getDropDownVerticalOffset() {
        if (this.uq != null) {
            return this.uq.getVerticalOffset();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownVerticalOffset();
        }
        return 0;
    }

    @Override // android.widget.Spinner
    public void setDropDownHorizontalOffset(int i) {
        if (this.uq != null) {
            this.uq.setHorizontalOffset(i);
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownHorizontalOffset(i);
        }
    }

    @Override // android.widget.Spinner
    public int getDropDownHorizontalOffset() {
        if (this.uq != null) {
            return this.uq.getHorizontalOffset();
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownHorizontalOffset();
        }
        return 0;
    }

    @Override // android.widget.Spinner
    public void setDropDownWidth(int i) {
        if (this.uq != null) {
            this.ur = i;
        } else if (Build.VERSION.SDK_INT >= 16) {
            super.setDropDownWidth(i);
        }
    }

    @Override // android.widget.Spinner
    public int getDropDownWidth() {
        if (this.uq != null) {
            return this.ur;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return super.getDropDownWidth();
        }
        return 0;
    }

    @Override // android.widget.AdapterView
    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        if (!this.up) {
            this.uo = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        if (this.uq != null) {
            this.uq.setAdapter(new au(spinnerAdapter, (this.rz == null ? getContext() : this.rz).getTheme()));
        }
    }

    @Override // android.widget.Spinner, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.uq != null && this.uq.isShowing()) {
            this.uq.dismiss();
        }
    }

    @Override // android.widget.Spinner, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.pd == null || !this.pd.onTouch(this, motionEvent)) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.widget.Spinner, android.widget.AbsSpinner, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.uq != null && View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
            setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), a(getAdapter(), getBackground())), View.MeasureSpec.getSize(i)), getMeasuredHeight());
        }
    }

    @Override // android.widget.Spinner, android.view.View
    public boolean performClick() {
        if (this.uq == null) {
            return super.performClick();
        }
        if (!this.uq.isShowing()) {
            this.uq.show();
        }
        return true;
    }

    @Override // android.widget.Spinner
    public void setPrompt(CharSequence charSequence) {
        if (this.uq != null) {
            this.uq.l(charSequence);
        } else {
            super.setPrompt(charSequence);
        }
    }

    @Override // android.widget.Spinner
    public CharSequence getPrompt() {
        return this.uq != null ? this.uq.getHintText() : super.getPrompt();
    }

    @Override // android.view.View
    public void setBackgroundResource(int i) {
        super.setBackgroundResource(i);
        if (this.tq != null) {
            this.tq.an(i);
        }
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        if (this.tq != null) {
            this.tq.i(drawable);
        }
    }

    @Override // android.support.v4.view.z
    public void a(ColorStateList colorStateList) {
        if (this.tq != null) {
            this.tq.a(colorStateList);
        }
    }

    @Override // android.support.v4.view.z
    public ColorStateList aQ() {
        if (this.tq != null) {
            return this.tq.aQ();
        }
        return null;
    }

    @Override // android.support.v4.view.z
    public void a(PorterDuff.Mode mode) {
        if (this.tq != null) {
            this.tq.a(mode);
        }
    }

    @Override // android.support.v4.view.z
    public PorterDuff.Mode aR() {
        if (this.tq != null) {
            return this.tq.aR();
        }
        return null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.tq != null) {
            this.tq.ec();
        }
    }

    int a(SpinnerAdapter spinnerAdapter, Drawable drawable) {
        if (spinnerAdapter == null) {
            return 0;
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int iMax = Math.max(0, getSelectedItemPosition());
        int iMin = Math.min(spinnerAdapter.getCount(), iMax + 15);
        int iMax2 = Math.max(0, iMax - (15 - (iMin - iMax)));
        int i = 0;
        View view = null;
        int iMax3 = 0;
        while (iMax2 < iMin) {
            int itemViewType = spinnerAdapter.getItemViewType(iMax2);
            if (itemViewType != i) {
                view = null;
            } else {
                itemViewType = i;
            }
            view = spinnerAdapter.getView(iMax2, view, this);
            if (view.getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            iMax3 = Math.max(iMax3, view.getMeasuredWidth());
            iMax2++;
            i = itemViewType;
        }
        if (drawable == null) {
            return iMax3;
        }
        drawable.getPadding(this.iH);
        return this.iH.left + this.iH.right + iMax3;
    }
}
