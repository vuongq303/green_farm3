# GLLib source recovery against original bytecode

The recovered source group is limited to `S800x480/GLLib.java`, `bl.java`
(ProfilerEvent) and `bm.java` (ProfilerNode). These three units compile together
against the original binary dependencies. This does not mean the whole Java
project or all 109 game-core units compile.

## Evidence and changes

The input is the original `green-farm-3.apk`, version4.4.2/code44220, SHA-256
`34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b`.
Original `GLLib.smali` SHA-256 is
`743f42399bf5c24dafd74f742dc7772a621b6b801ec840b9138c567495bf221e`.
The converted original classfile jar is retained locally at
`/workspace/tooling/recovery-bytecode/greenfarm-original.jar`; bytecode from that
jar is used as an independent execution oracle in the tests.

- Replace stale source/debug generic names `ProfilerEvent`/`ProfilerNode` with
  the actual DEX types `bl`/`bm`. Restore the public `bl.a(bl)` method and its
  `compareTo(Object)` forwarder, whose descriptor exists in the original DEX.
- Restore folded constants from instructions: logging buffer300, EGL error8888
  and switch indices10/11/12. They are literal values in DEX, not runtime
  dependencies on the unrelated SDK R/JavaUtils members JADX selected.
- Make the drawing call explicitly choose `a(FFFFII)V`, matching the original
  invoke descriptor and its integer-to-float conversions.
- Restore the shared exception return registers in `cT(int)` (LZMA pack read)
  and `j(InputStream)` (text pack read). Preserve already-read data/pack index on
  the corresponding exception paths, instead of inventing a default after each
  intermediate operation.
- Restore number formatting `a(long,int,String)` from the original switch and
  common grouping loop. The previous export duplicated the loop with unreachable
  breaks, incorrect termination and missing return paths.
- Restore Arabic shaping `a(char[],int)` from the complete smali branches:
  diacritic lookahead termination, preceding/following join categories and
  Lam/Alef ligature handling. Original marking of the next array element with31
  is retained; no new shaping rules or Unicode tables are introduced.
- Restore the LZMA short-repetition edge in `a(int,int,int,int,byte[],int)`.
  After emitting the single repeated byte, original `goto_35` returns directly
  to the outer decode loop. Adding that missing `continue` recovers the branch;
  initializing the otherwise undefined distance register would be incorrect.

## Checked exceptions erased by DEX

Several source methods call `InputStream.read/available`, `Thread.sleep`, or a
decoder that can propagate checked exceptions. Their original DEX has no handler
for those operations, while Java requires a declaration or handler. The source
uses a small generic `propagateChecked` helper to rethrow the **same instance**:
no wrapping, replacement value, swallowing or exception suppression is added.
Existing handlers inside each method retain precedence.

For the two RecordStore handlers whose checked `throws` metadata is absent from
the binary dependency, an `Exception` catch first tests `instanceof` the original
RecordStore exception and rethrows every other exception unchanged. This retains
the original catch filter rather than widening handled failures.

Four actual input-reader failure paths are executed against both versions and
confirm identical exception identity. The rest of the IO/interruption call sites
were checked against their smali handler boundaries; the tests do not claim
coverage of every external device failure.

## Compile-only package/class collision view

DEX legitimately contains both public interface `javax.microedition.a.a`
(Connection) and package `javax.microedition.a.a`, containing interface `a`
(FileConnection). Java source lookup chooses the public interface first and
cannot resolve the package. Deleting Connection also fails because Connector's
method return descriptors refer to it.

`tools/make_core_compile_view.py` clears only `ACC_PUBLIC` on Connection in a
**compile-only jar**. The FileConnection package then resolves and Connector's
original method descriptors remain valid. No instruction, name, descriptor or
runtime artifact is changed. The tool records both class hashes and access flags;
the original jar is never overwritten. Do not package this view in the APK.

```sh
python3 tools/make_core_compile_view.py \
  /workspace/tooling/recovery-bytecode/greenfarm-original.jar \
  /workspace/tooling/recovery-bytecode/core-compile-view.jar
```

## Validation

```sh
python3 -m unittest discover -s tests -p 'test_gllib_recovered_algorithms.py' -v
```

This persistent test compiles the current three source files on every run, then
executes selected recovered methods against their original converted bytecode:

- **10,800** number-format combinations, including language switches, grouping
  boundaries, negative/extreme long values and null/custom separators.
- **2,000** Arabic-shaping arrays with identical supplied category/shape tables.
  These isolate algorithm control flow; they do not replace or validate the
  game's complete original table data.
- **Seven** LZMA payloads generated independently with Python's LZMA encoder,
  including empty, repeated, patterned and random bytes. Both implementations
  reproduce the exact original bytes and finish with identical range/probability
  state.
- **Four** input-reader IO failures rethrow the original exception instance.

The fixture changes only the surrounding test class shell to avoid Android
initialization. Selected method Code attributes remain unchanged. The test
requires JDK, SDK28 and the verified original dex2jar artifact; otherwise it
reports a skip. This is a bytecode-oracle/unit check, not Android gameplay testing.

The consolidated probe of all109 core Java units plus four SDK R alias units is
recorded under `/workspace/tooling/recovery-bytecode/core-final-{command,sources,
compile,exit}.*`. At this checkpoint it exits1 with **56** diagnostics outside
GLLib/bm/bl: `a.java`29, `s.java`12, `f.java`5, `ch.java`3, `bv.java`2 and one
each in `l`, `GloftGF2F`, `bh`, `bz`, `bn`. They include other corrupted loops,
missing return paths and erased checked-exception declarations. This is a
diagnostic checkpoint; later source groups can change those counts.
