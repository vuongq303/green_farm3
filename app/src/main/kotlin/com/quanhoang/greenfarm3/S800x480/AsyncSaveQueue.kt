package com.quanhoang.greenfarm3.S800x480

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/** One SQLite writer, one bounded/coalesced pendingTicket snapshot, absolute retries. */
class AsyncSaveQueue(private val listener: Listener) {
    fun interface Listener {
        fun completed(result: Result)
    }

    /** A compensating checkpoint must remain queued until it is durable. */
    interface RetryableCheckpoint

    class Result internal constructor(ticket: Ticket, failure: RuntimeException?, durationNanos: Long) {
        @JvmField val sequence: Long = ticket.sequence
        @JvmField val checkpoint: Any? = ticket.checkpoint
        @JvmField val failure: RuntimeException? = failure
        @JvmField val durationNanos: Long = durationNanos
        @JvmField val records: Int = ticket.snapshot.recordCount()
        @JvmField val bytes: Long = ticket.snapshot.byteCount()
    }

    internal class Ticket(
        val snapshot: GameSaveStore.Snapshot,
        val sequence: Long,
        val checkpoint: Any?
    )

    private val executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor { task ->
        Thread(task, "GreenFarm-SQLite").apply { isDaemon = true }
    }
    private var pendingTicket: Ticket? = null
    private var writeScheduledOrRunning = false
    private var closed = false
    private var consecutiveFailureCount = 0
    private var lastDurableSequence = 0L
    private var retryPaused = false

    @Synchronized
    fun submit(snapshot: GameSaveStore.Snapshot, sequence: Long, checkpoint: Any?) {
        check(!closed) { "Save writer is closed" }
        pendingTicket = mergeTickets(pendingTicket, Ticket(snapshot, sequence, checkpoint))
        if (!writeScheduledOrRunning && !retryPaused) {
            writeScheduledOrRunning = true
            executor.execute { writeNext() }
        }
    }

    private fun writeNext() {
        val ticket: Ticket
        synchronized(this) {
            val next = pendingTicket
            pendingTicket = null
            if (next == null) {
                writeScheduledOrRunning = false
                signalIdleWaiters()
                return
            }
            ticket = next
        }
        val startNanos = System.nanoTime()
        var failure: RuntimeException? = null
        try {
            GameSaveStore.applySnapshot(ticket.snapshot)
        } catch (problem: RuntimeException) {
            failure = problem
        }
        val durationNanos = System.nanoTime() - startNanos
        synchronized(this) {
            if (failure == null) {
                lastDurableSequence = maxOf(lastDurableSequence, ticket.sequence)
                consecutiveFailureCount = 0
                retryPaused = false
            } else {
                consecutiveFailureCount++
                retryPaused = consecutiveFailureCount >= SaveBatchPolicy.MAX_WRITE_ATTEMPTS
                // Cancelled language selections cannot be applied later by an
                // automatic retry. Durable compensation checkpoints can retry.
                if (ticket.checkpoint == null || ticket.checkpoint is RetryableCheckpoint) {
                    pendingTicket = mergeTickets(ticket, pendingTicket)
                }
            }
        }
        listener.completed(Result(ticket, failure, durationNanos))
        synchronized(this) {
            if (pendingTicket == null || closed || retryPaused) {
                writeScheduledOrRunning = false
                signalIdleWaiters()
            } else {
                val retryDelayMillis = if (failure == null) 0L else
                    minOf(SaveBatchPolicy.RETRY_MAX_MILLIS,
                        SaveBatchPolicy.RETRY_INITIAL_MILLIS shl minOf(consecutiveFailureCount - 1, 3))
                executor.schedule({ writeNext() }, retryDelayMillis, TimeUnit.MILLISECONDS)
            }
        }
    }

    /** Explicit retry starts a fresh bounded attempt window, retaining accepted data. */
    @Synchronized
    fun retryPending() {
        if (closed || !retryPaused) return
        retryPaused = false
        consecutiveFailureCount = 0
        if (pendingTicket != null && !writeScheduledOrRunning) {
            writeScheduledOrRunning = true
            executor.execute { writeNext() }
        }
    }

    @Synchronized
    fun isRetryPaused(): Boolean = retryPaused

    /** Used only at lifecycle boundaries; normal game frames never wait here. */
    @Synchronized
    fun awaitIdle(timeoutMillis: Long): Boolean {
        val deadlineNanos = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(timeoutMillis)
        while (writeScheduledOrRunning || pendingTicket != null) {
            if (retryPaused && !writeScheduledOrRunning) return false
            val remainingNanos = deadlineNanos - System.nanoTime()
            if (remainingNanos <= 0L) return false
            try {
                TimeUnit.NANOSECONDS.timedWait(this, remainingNanos)
            } catch (interrupted: InterruptedException) {
                Thread.currentThread().interrupt()
                return false
            }
        }
        return true
    }

    @Synchronized
    fun isIdle(): Boolean = !writeScheduledOrRunning && pendingTicket == null

    @Synchronized
    fun durableSequence(): Long = lastDurableSequence

    @Synchronized
    fun close() {
        check(!writeScheduledOrRunning && pendingTicket == null) { "Cannot discard pending saves" }
        closed = true
        executor.shutdown()
    }

    @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
    private fun signalIdleWaiters() = (this as java.lang.Object).notifyAll()

    private companion object {
        fun mergeTickets(older: Ticket?, newer: Ticket?): Ticket? {
            if (older == null) return newer
            if (newer == null) return older
            // Both snapshot inputs are non-null here. The store's nullable
            // return only covers its separate (null, null) identity case.
            val mergedSnapshot = checkNotNull(GameSaveStore.coalesceSnapshots(older.snapshot, newer.snapshot)) {
                "Two accepted snapshots must produce a merged snapshot"
            }
            return Ticket(
                mergedSnapshot,
                maxOf(older.sequence, newer.sequence),
                newer.checkpoint ?: older.checkpoint
            )
        }
    }
}
