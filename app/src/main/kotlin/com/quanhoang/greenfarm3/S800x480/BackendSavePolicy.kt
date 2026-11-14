package com.quanhoang.greenfarm3.S800x480

/** Scheduling only: SQLite durability never waits for an optional legacy upload. */
class BackendSavePolicy(private val tagPrefix: String = "farm-autosave") {
    class Upload(val payload: String, val tag: String, internal val sequence: Long)

    private var epoch = 0L
    private var uploadId = 0L
    private var changedSequence = -1L
    private var changedAt = 0L
    private var capturedSequence = -1L
    private var capturedPayload: String? = null
    private var durableSequence = -1L
    private var acknowledgedSequence = -1L
    private var acknowledgedPayload: String? = null
    private var active: Upload? = null
    private var retryAt = 0L
    private var failures = 0

    @Synchronized fun reset() {
        epoch++
        changedSequence = -1L
        capturedSequence = -1L
        capturedPayload = null
        durableSequence = -1L
        acknowledgedSequence = -1L
        acknowledgedPayload = null
        active = null
        retryAt = 0L
        failures = 0
    }

    @Synchronized fun baseline(payload: String) {
        if (changedSequence < 0L) acknowledgedPayload = payload
    }

    @Synchronized fun changed(sequence: Long, now: Long) {
        if (sequence <= changedSequence) return
        changedSequence = sequence
        changedAt = now
        failures = 0
    }

    @Synchronized fun captured(sequence: Long, payload: String?) {
        // Loading a save, periodic unchanged captures and local-mode captures
        // cannot turn historical local data into an upload request.
        if (changedSequence < 0L || sequence != changedSequence || payload.isNullOrEmpty()) return
        capturedSequence = sequence
        capturedPayload = payload
    }

    @Synchronized fun durable(sequence: Long) {
        durableSequence = maxOf(durableSequence, sequence)
    }

    @Synchronized fun begin(now: Long): Upload? {
        if (active != null || changedSequence <= acknowledgedSequence ||
            capturedSequence < changedSequence || capturedSequence > durableSequence ||
            now - changedAt < DEBOUNCE_MILLIS || now < retryAt || failures >= MAX_ATTEMPTS) return null
        val payload = capturedPayload ?: return null
        if (payload == acknowledgedPayload) {
            acknowledgedSequence = capturedSequence
            return null
        }
        return Upload(payload, "$tagPrefix:$epoch:$capturedSequence:${++uploadId}", capturedSequence).also { active = it }
    }

    @Synchronized fun completed(tag: String?, success: Boolean, now: Long): Boolean {
        val sent = active ?: return false
        if (sent.tag != tag) return false
        active = null
        if (success) {
            acknowledgedSequence = maxOf(acknowledgedSequence, sent.sequence)
            acknowledgedPayload = sent.payload
            failures = 0
            retryAt = 0L
        } else {
            failures++
            retryAt = now + minOf(8000L, 500L shl minOf(failures - 1, 4))
        }
        return true
    }

    companion object {
        const val DEBOUNCE_MILLIS = 500L
        private const val MAX_ATTEMPTS = 5
    }
}
