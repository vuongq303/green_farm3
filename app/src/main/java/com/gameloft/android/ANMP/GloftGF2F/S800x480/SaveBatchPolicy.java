package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/** Pure scheduling policy; no timer, database access or gesture suppression. */
public final class SaveBatchPolicy {
    public static final long DEBOUNCE_MILLIS = 200L;
    public static final long MAX_WAIT_MILLIS = 500L;
    public static final int MAX_OPERATIONS = 50;
    private long first = -1L;
    private long last;
    private int operations;

    public void changed(long now, long count) {
        if (count <= 0L) return;
        if (first < 0L) first = now;
        last = now;
        operations = (int) Math.min(MAX_OPERATIONS, operations + Math.min(count, MAX_OPERATIONS));
    }

    public boolean due(long now) {
        return first >= 0L && (operations >= MAX_OPERATIONS
                || now - last >= DEBOUNCE_MILLIS || now - first >= MAX_WAIT_MILLIS);
    }

    public boolean isDirty() { return first >= 0L; }
    public void captured() { first = -1L; operations = 0; }
}
