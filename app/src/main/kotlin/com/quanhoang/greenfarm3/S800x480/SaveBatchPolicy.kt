package com.quanhoang.greenfarm3.S800x480

/** Pure scheduling policy; no timer, database access or gesture suppression. */
class SaveBatchPolicy {
    private var firstChangeUptimeMillis = -1L
    private var lastChangeUptimeMillis = 0L
    private var pendingOperationCount = 0

    fun changed(nowUptimeMillis: Long, newOperationCount: Long) {
        if (newOperationCount <= 0L) return
        if (firstChangeUptimeMillis < 0L) firstChangeUptimeMillis = nowUptimeMillis
        lastChangeUptimeMillis = nowUptimeMillis
        pendingOperationCount = minOf(
            MAX_OPERATIONS.toLong(),
            pendingOperationCount + minOf(newOperationCount, MAX_OPERATIONS.toLong())
        ).toInt()
    }

    fun due(nowUptimeMillis: Long): Boolean = firstChangeUptimeMillis >= 0L && (
        pendingOperationCount >= MAX_OPERATIONS ||
            nowUptimeMillis - lastChangeUptimeMillis >= DEBOUNCE_MILLIS ||
            nowUptimeMillis - firstChangeUptimeMillis >= MAX_WAIT_MILLIS
        )

    fun isDirty(): Boolean = firstChangeUptimeMillis >= 0L

    fun captured() {
        firstChangeUptimeMillis = -1L
        pendingOperationCount = 0
    }

    companion object {
        const val DEBOUNCE_MILLIS = 200L
        const val MAX_WAIT_MILLIS = 500L
        const val MAX_OPERATIONS = 50
    }
}
