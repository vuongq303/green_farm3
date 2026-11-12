# Asynchronous save queue

Normal gameplay serializes a detached snapshot at a complete game-thread
boundary, then submits it to `AsyncSaveQueue`. The queue writes through one
`GreenFarm-SQLite` worker. SQLite work does not run in the queue's `submit`
method. The controller drains completion results on subsequent game frames.

## Batching policy

`SaveBatchPolicy` tracks the first and latest changes since capture:

| Trigger | Due when |
| --- | --- |
| Debounce | 200 ms have elapsed since the latest change |
| Maximum wait | 500 ms have elapsed since the first uncaptured change |
| Operation count | 50 or more operations have accumulated |

Continuous changes cannot keep moving the policy's maximum-wait deadline.
Nonpositive change counts do not start a batch. Capturing resets the policy's
first-change time and operation count, so a new batch gets its own deadline.
These thresholds determine when the policy reports `due`; actual serialization
still requires the controller's valid game-state boundary. They do not promise
a 500 ms durable-write deadline during loading, an unfinished move, a blocked
database, or lifecycle transitions.

## Queue and acknowledgement semantics

There is at most one active writer and one pending ticket. When B and C arrive
while A is writing, their snapshots are coalesced into the pending ticket. Its
sequence is the greater sequence, and record values are absolute values selected
by the store's snapshot-version rules. A completion for A reports only A's
sequence. It cannot acknowledge B/C or remove that pending write. Results also
carry the checkpoint identity, failure, write duration, record count and byte
count.

An ordinary write failure retains its detached absolute snapshot. The queue
merges it with any newer pending snapshot and retries with bounded exponential
delays: 1, 2, 4 and then 8 seconds. Retrying absolute record values avoids
reapplying gameplay deltas. The real store owns transactional commit, rollback
and versioned durable acknowledgements; the queue owns ordering and retry.

A nonnull checkpoint is a cancellable save barrier used by language restart.
Failure completes that ticket once and does not automatically retry its selected
language. The controller can restore the previous language header and recapture
the current gameplay state. Successful restart still depends on the controller
receiving a successful checkpoint completion.

Normal frames do not call `awaitIdle`. Lifecycle boundaries may wait for pending
work through that method. Timeout and interruption return `false`; interruption
also restores the thread's interrupted flag. Neither path discards saves.
`close` rejects a queue with active or pending work and shuts down the executor
only after it is idle. A closed queue rejects new submissions.

## Behavioral verification

Run the dedicated tests with an installed JDK:

```sh
JAVA_HOME=/workspace/tooling/jdk-25 \
  python3 -m unittest discover -s tests -p test_async_save_queue.py -v
```

The test compiles the actual `AsyncSaveQueue.java` and `SaveBatchPolicy.java`
without Android. Its fake store supplies detached, versioned absolute snapshots
and latch-controlled transactions. The latches make the writing/pending order
explicit without depending on a guessed duration for a fast or slow database.
Five passing cases cover:

- 200 ms debounce, 500 ms maximum wait under continuous changes, the 50-operation
  threshold, large counts, ignored nonchanges, and capture reset.
- Blocked A followed by B/C, a single coalesced pending write, exactly one active
  writer, result metadata, and A's inability to acknowledge B/C.
- A failed transaction, retention of old-only records, coalescing of newer
  absolute values, and successful retry without duplicated values.
- Checkpoint failure identity, no automatic retry of a canceled language, and an
  explicit old-language recovery write.
- `awaitIdle` timeout/interruption and refusal to close while work is pending.

These tests verify queue control flow and batching. The fake transaction's
atomic publication is a fixture contract; it is not evidence of real SQLite
rollback, Android lifecycle behavior, controller/gameplay integration, frame
time, or device performance. Those require the real storage probes and Android
runtime validation. No frame-rate or latency improvement is claimed here.
