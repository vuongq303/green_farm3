# Recovery of core peer-class accesses

Evidence comes from the matching original APK's `classes.dex`, disassembled in
`/workspace/tooling/original-smali/com/gameloft/android/ANMP/GloftGF2F/S800x480`.
The APK/DEX hashes are recorded in `JAVA_BLOCKERS_RECOVERY.md`.

## Synthetic accessors

JADX removed synthetic accessors and inlined their private-field accesses into
separate peer classes. This is legal in the original DEX through the accessors,
but the resulting Java cannot access those private fields directly.

Restored the following **31 original accessor methods**, with original names,
descriptors, read/write targets and return values. Private fields were not made
public or package-visible.

| Owner | Recovered original methods |
|---|---|
| `ac` | `ma`, `mb`: sound-ID and completion arrays |
| `ao` | `a(ao)`, `a(ao,int)`, `a(ao,boolean)`, `b(ao,boolean)`, `access$302` |
| `bz` | `a(bz)`, `a(bz,bc,String)`, `access$002`, `access$302`, `aq`, `b(bz)`, `b(bz,bc,String)`, `b(bz,String)`, `c(bz)`, `c(bz,bc,String)`, `os` |
| `bx` | `eQ`, `nO`: TCP length state (`eP` already existed) |
| `by` | `a(by)`, `a(by,boolean)`: TCP worker state |
| `n` | `p(long)`: readiness timestamp |
| `bq` | `nO`, `nP`, `access$100`, `access$200`, `access$600`, `H`, `b(String,e)`, `cC` |

Restored original call sites in `ad`, `ap`, `br`, `bx`, `by`, `ca` and `o`.
Calls in `cs` and `cy` now also use their original `ci` accessors
(`a(ci,boolean)`, `MM`, `MN`, `MO`, `MP`, `bD`), restored separately by the agent
owning `ci.java`. No TCP worker loop, save worker dispatch, sound completion
semantics or thread flags were redesigned.

## Other directly evidenced Java corrections

- `aw.d(aj)` passes integer 0/1 to the original sprite-draw descriptor
  `(aj,int,int,int,int,int)`. The DEX reads `ahV` and normalizes it to 0/1;
  JADX incorrectly retained a boolean argument. Both branches of the following
  `ahS` test use that same integer; no additional sprite choice was invented.
- `f.a(int,boolean,bc)` calls the class `S800x480.i.ic()` in DEX. Fully qualifying
  it prevents the Java integer parameter named `i` from hiding the class.
- `s.aL`'s URL check uses DEX reference/null comparison, not an integer compare.
  Changed the impossible String `!= 0` expression to `!= null`.
- `cv.D(int,String)` has no original checked-throws annotation and implements
  `ar.D`, which does not declare checked exceptions. Removed the export's
  invalid `throws Exception`; `ci.dd` is already restored without that inferred
  declaration.
- `n` has stale DEX descriptors referencing the absent
  `FakeIAPLib$ItemFeed` type, while the actual bodies allocate and return existing
  `S800x480.q` objects/arrays. Corrected four source type occurrences to `q`,
  preserving the existing data fields and constructors. DEX references to these
  methods are confined to `n` and `GLLib`, both rebuilt core sources. No class was
  renamed and no purchase/reward logic was changed.

## Validation

`git diff --check` passed. A scoped core-source compiler probe with Android 28,
Apache legacy, current AGP R.jar and the original converted classpath reports no
remaining private-field, `ItemFeed`, class-`i` shadow or sprite boolean/int
diagnostics in these files. The probe still fails on broader source/classpath
issues; the remaining diagnostics in these peer files concern SDK nested
constants (`FirebaseAnalytics.Param/Event`, `R.styleable`) being repaired by the
classpath owner. Full output is retained at
`/workspace/tooling/recovery-bytecode/core-peer-fixes-1.log`.

This probe is not a successful full build or runtime/gameplay test. Larger
decompiled control-flow problems outside these exact operations were left for
separate bytecode-guided recovery.

## Follow-up: original constants and lifecycle override

The consolidated core classpath exposed 25 expressions where JADX had replaced
an original inlined literal with a reference to a nested SDK constant class not
present in the original converted binary. Restored the actual original literals
in `s`, `f`, `ch`, `bv`, `bh`, `bz` and `bn`:

- Firebase-derived strings: `quantity`, `level`, `end_date`, `start_date`,
  `content_type`, `score`, `group_id`, `login`.
- JavaUtils-derived integer constants: 300 and 320.

Every replacement was checked against the corresponding original class's smali
`const-string`/`const` instructions. The values, call arguments, resource
coordinates, JSON keys, data arrays and existing branches remain the same.
Unused imports of those SDK classes were removed where appropriate; no fake SDK
constant classes or dependencies were added.

Removed `throws JSONException` from `GloftGF2F.al(boolean)`. The original method
has no Throws annotation, MIDlet's declaration cannot be overridden with a new
checked exception, and the currently recovered lifecycle callees no longer have
inferred checked-throws declarations. No exception is caught, converted or
swallowed by this correction.

A follow-up probe using the consolidated `nonpublic.jar` classpath and keeping
the original partial `DataInput` implementation `l` as a binary dependency now
reports 29 errors, all in separately owned `a.java`. Its log is
`/workspace/tooling/recovery-bytecode/core-peer-final-compile.log`. This is still
a failing diagnostic probe, and later flow checks can reveal further errors
after those methods are repaired.

## Late flow recovery from the real core Gradle compilation

The dedicated core build excludes original core classfiles from its compiler
classpath. This exposes additional flow errors that earlier mixed probes could
mask. The following groups were recovered against original smali:

- `f`: remove five duplicated unreachable second `break` statements. The original
  case branches jump once to their continuation. Preserve the no-handler
  `f.a(int,boolean,bc)` exception behavior with a generic same-instance rethrow
  for the checked `JSONException` introduced by source reconstruction. No reward
  callback arguments or action logic were replaced.
- `aa.a(aj,int,int,int,int,int,int,int)`: reconstruct the single tile-render method
  from the original `cond_58`/`cond_225` register join and subsequent loops.
  The viewport origin and column count come from the original adjusted arguments,
  not zero defaults. Restore original clipping/wrapping conditions, inclusive
  row/column counters, sprite flip offsets, overlay mode and effect teardown.
  Explicit float casts retain the original float draw overloads; the overlay
  retains its original integer draw overload.
- `ch.fg`: the original `cond_c1` and `cond_c5` both choose animation arguments
  `(3,40)`. Restore this actual fallback branch rather than initializing a local
  to an arbitrary default.
- `z`: restore the existing orthogonal/diagonal movement cost into the original
  joined register, including skipping blocked diagonal corners and blocked target
  nodes where the original branches do so. Restore ordered open-list traversal
  using the current head/node and `aaw` links. No heuristic, penalty, movement
  direction table or tie-breaking rule was redesigned.
- `cg.read()` and `ak.b(InputStream,OutputStream)`: the original DEX has no
  handler at these boundaries. Preserve the same escaping `IOException` instance
  through Java's checked-exception rules using generic rethrow. Existing gzip
  CRC and size checks remain unchanged.
- `i`: remove one unreachable duplicate `break` from the tracking switch; the
  original case jumps once to the common continuation.
- `am.cancel()`: unlike the two IO boundaries above, the original cleanup DOES
  have three separate `Exception` handlers. Restore those handlers and monitor
  regions for input close, connection disconnect and output close. Preserve the
  existing `e(Exception)` recording, initial connection guard, mode flags and
  final nulling order. Do not replace those handlers with unchecked rethrow.
- `by`: the original initial packet-header register is explicitly zero before
  reading; the secondary-read exception branch retains a successfully read
  header. Restore the shared error handler for both paths, including receiver
  state/error recording. Both receive modes join the same queue block, release
  its monitor, then log and sleep. Preserve the original `InterruptedException`
  handler that continues the loop; no new interruption swallowing or rethrow
  policy was introduced.

### Differential evidence

`tests/test_aa_tile_renderer.py` compares 4,000 original/recovered call traces and
exception outcomes. Original renderer Code attributes and data accessors are
retained unchanged in an isolated class shell. Test-only sprite/effect stand-ins
record selection, coordinates, overloads, transform flags and effect calls.
Cases cover clipping, wrapping, byte/word tile IDs, packed flags, overlay modes
and opacity. All comparisons passed. This is not a pixel-rendering or Android
runtime test.

`tests/test_z_pathfinding.py` compares 2,000 cases against the entire original
converted `z.class`. It compares parent, open/closed-list, g/h-score and path
arrays, list head/path length and exception outcomes on 4/8-direction grids,
wall/direction maps, subareas, custom costs and turn penalties. Test-only debug
logging is disabled outside application code; all comparisons passed. This does
not establish that a character has walked successfully in a running farm.

`tests/test_am_http_cancel.py` compares 448 original/recovered scenarios with
test-only streams and HTTP connections. It checks cleanup call order, lock
ownership/release, cancellation flags, retained/caught exceptions, propagated
Errors and reference clearing. The comparison retains original cancellation
Code attributes and omits unrelated constructor/Android initialization; no
network request is made. All comparisons passed.

`tests/test_by_tcp_receiver.py` compares 516 original/recovered scenarios:
512 buffer/error/framing cases and four direct receive-loop queue/interruption
cases. It checks byte buffers, stream calls, receiver/error flags, continuation
length, queue monitor ownership, post-monitor logging and the original sleep
interruption outcome. All comparisons passed. Fixtures use in-memory streams and
test-only peers, make no network connection and are not packaged into the game.

These tests compile the current Java source for each run and require the verified
local dex2jar artifact and JDK. The build artifacts used for comparison are
temporary; no fixture implementation is packaged into the game.

### Runtime loop regressions: availability and request lookup

The source `as.jf()` loop could repeatedly evaluate the same ready slot: its
positive `mK()` branch neither returned nor advanced the index. The original
`as.smali` method (line 156) exits the class monitor and immediately returns
that index; only a false predicate advances to the next of three slots. Restore
that early return and retain Java's synchronized-method monitor release on both
normal and exceptional exits. This matches the reported render-thread stack
through `as.jf`, `mM`, `ci.jC` and `GLLib.onDrawFrame`; runtime behavior after the
change must still be checked on Android.

The same defect existed in `s.cz(int)`: the original `s.smali` method (line 9262)
returns the first matching `MQ` index through its common monitor-exit block and
returns `-1` only after the entire array is exhausted. Its restored source uses
that early return and retains its class monitor. A null array still raises
`NullPointerException` rather than being converted into an absent request.

On 2026-11-12, both focused bytecode comparisons passed without skips:

- `tests/test_as_available_slot.py`: 130 original/current outcomes, including all
  eight three-slot Boolean patterns, null slots/arrays, short arrays, runtime
  exceptions and Errors. The fixture checks predicate call order, monitor
  ownership during each call, identity of propagated injected failures, and
  monitor release after returning or throwing.
- `tests/test_s_request_lookup.py`: 2,415 original/current first-match outcomes
  across empty, repeated, absent, negative and extreme request IDs, plus a null
  array. The lookup leaves the array unchanged. Both implementations also pass
  a contended class-monitor check after a null-array failure: the reader blocks
  until the monitor is released and sees the array assigned under that monitor.

The selected original Code attributes remain unchanged in temporary class
shells; current methods are freshly compiled from source. These checks do not
exercise Android rendering, loading a farm, or player interaction.
