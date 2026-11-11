package android.support.v7.app;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;

/* JADX INFO: compiled from: AlertController.java */
/* JADX INFO: loaded from: classes.dex */
class m extends CursorAdapter {
    final /* synthetic */ k kA;
    private final int kB;
    private final int kC;
    final /* synthetic */ e kD;
    final /* synthetic */ s kz;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    m(k kVar, Context context, Cursor cursor, boolean z, s sVar, e eVar) {
        super(context, cursor, z);
        this.kA = kVar;
        this.kz = sVar;
        this.kD = eVar;
        Cursor cursor2 = getCursor();
        this.kB = cursor2.getColumnIndexOrThrow(this.kA.ku);
        this.kC = cursor2.getColumnIndexOrThrow(this.kA.kv);
    }

    @Override // android.widget.CursorAdapter
    public void bindView(View view, Context context, Cursor cursor) {
        ((CheckedTextView) view.findViewById(R.id.text1)).setText(cursor.getString(this.kB));
        this.kz.setItemChecked(cursor.getPosition(), cursor.getInt(this.kC) == 1);
    }

    @Override // android.widget.CursorAdapter
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.kA.kc.inflate(this.kD.jT, viewGroup, false);
    }
}
