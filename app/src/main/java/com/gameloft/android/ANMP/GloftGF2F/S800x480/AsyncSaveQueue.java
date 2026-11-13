package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/** One SQLite writer, one bounded/coalesced pending snapshot, absolute retries. */
public final class AsyncSaveQueue {
    public interface Listener { void completed(Result result); }
    /** A compensating checkpoint must remain queued until it is durable. */
    public interface RetryableCheckpoint {}

    public static final class Result {
        public final long sequence;
        public final Object checkpoint;
        public final RuntimeException failure;
        public final long durationNanos;
        public final int records;
        public final long bytes;
        Result(Ticket ticket, RuntimeException failure, long durationNanos) {
            this.sequence = ticket.sequence;
            this.checkpoint = ticket.checkpoint;
            this.failure = failure;
            this.durationNanos = durationNanos;
            records = ticket.snapshot.recordCount();
            bytes = ticket.snapshot.byteCount();
        }
    }

    private static final class Ticket {
        final GameSaveStore.Snapshot snapshot;
        final long sequence;
        final Object checkpoint;
        Ticket(GameSaveStore.Snapshot snapshot, long sequence, Object checkpoint) {
            this.snapshot = snapshot;
            this.sequence = sequence;
            this.checkpoint = checkpoint;
        }
    }

    private final Listener listener;
    private final ScheduledExecutorService executor;
    private Ticket pending;
    private boolean running;
    private boolean closed;
    private int failures;
    private long durableSequence;

    public AsyncSaveQueue(Listener listener) {
        this.listener = listener;
        executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            public Thread newThread(Runnable task) {
                Thread thread = new Thread(task, "GreenFarm-SQLite");
                thread.setDaemon(true);
                return thread;
            }
        });
    }

    public synchronized void submit(GameSaveStore.Snapshot snapshot, long sequence, Object checkpoint) {
        if (closed) throw new IllegalStateException("Save writer is closed");
        Ticket next = new Ticket(snapshot, sequence, checkpoint);
        pending = merge(pending, next);
        if (!running) {
            running = true;
            executor.execute(new Runnable() { public void run() { writeNext(); } });
        }
    }

    private static Ticket merge(Ticket older, Ticket newer) {
        if (older == null) return newer;
        if (newer == null) return older;
        return new Ticket(GameSaveStore.coalesceSnapshots(older.snapshot, newer.snapshot),
                Math.max(older.sequence, newer.sequence),
                newer.checkpoint != null ? newer.checkpoint : older.checkpoint);
    }

    private void writeNext() {
        Ticket ticket;
        synchronized (this) {
            ticket = pending;
            pending = null;
            if (ticket == null) { running = false; notifyAll(); return; }
        }
        long start = System.nanoTime();
        RuntimeException failure = null;
        try { GameSaveStore.applySnapshot(ticket.snapshot); }
        catch (RuntimeException problem) { failure = problem; }
        long duration = System.nanoTime() - start;
        synchronized (this) {
            if (failure == null) {
                durableSequence = Math.max(durableSequence, ticket.sequence);
                failures = 0;
            } else {
                failures++;
                // Language confirmation is a cancellable barrier. Its caller
                // restores the old header after failure, rather than allowing
                // an automatic retry to apply a canceled language selection.
                if (ticket.checkpoint == null || ticket.checkpoint instanceof RetryableCheckpoint) {
                    pending = merge(ticket, pending);
                }
            }
        }
        listener.completed(new Result(ticket, failure, duration));
        synchronized (this) {
            if (pending == null || closed) {
                running = false;
                notifyAll();
            } else {
                long delay = failure == null ? 0L : Math.min(8000L, 1000L << Math.min(failures - 1, 3));
                executor.schedule(new Runnable() { public void run() { writeNext(); } }, delay, TimeUnit.MILLISECONDS);
            }
        }
    }

    /** Used only at lifecycle boundaries; normal game frames never wait here. */
    public synchronized boolean awaitIdle(long timeoutMillis) {
        long end = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(timeoutMillis);
        while (running || pending != null) {
            long remaining = end - System.nanoTime();
            if (remaining <= 0L) return false;
            try { TimeUnit.NANOSECONDS.timedWait(this, remaining); }
            catch (InterruptedException stopped) { Thread.currentThread().interrupt(); return false; }
        }
        return true;
    }

    public synchronized boolean isIdle() { return !running && pending == null; }
    public synchronized long durableSequence() { return durableSequence; }

    public synchronized void close() {
        if (running || pending != null) throw new IllegalStateException("Cannot discard pending saves");
        closed = true;
        executor.shutdown();
    }
}
