# Original-DEX recovery of notification JPEG and APK size methods

The user-supplied `green-farm-3.apk` is the matching Green Farm 3 release:
package `com.gameloft.android.ANMP.GloftGF2F`, version `4.4.2`, code `44220`.

- APK SHA-256: `34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b`
- `classes.dex` SHA-256: `3901b6678b4523947273914b30fe94a750e149f89e0a7a1fa3932261d7539ec4`
- Authoritative disassembly: `/workspace/tooling/original-smali`, produced with
  baksmali 3.0.3. The class and method identities match the existing source;
  neither was renamed.

## `PushNotification.j.a(Context, Bitmap, String)`

Evidence is `com/gameloft/android/ANMP/GloftGF2F/PushNotification/j.smali`, method
starting at line 614, bytecode labels `:try_start_61` through `:catch_98`.

Recovered the file construction, JPEG compression, success flag and structured
`try/catch/finally` from the DEX exception table. The undefined `?? r3` was a
decompiler merge of unrelated String, stream and exception register lifetimes.

The implementation deliberately retains observed original behavior:

- Create `pn_images/<name>/image.jpg` with `mkdir`, not `mkdirs`.
- Compress with JPEG quality 100.
- Ignore the boolean returned by `Bitmap.compress`: a normal return marks the
  operation successful, even if that boolean is false.
- Log caught `Exception`, then close the stream; catch only `IOException` from
  close. The original unconditional close can throw `NullPointerException` when
  opening the output stream failed. No null guard was added during recovery.
- Return the image directory after a successful compression call, otherwise
  null if exception handling and cleanup return normally.

The original DEX does not declare checked throws. The broken export's
`throws Throwable` declaration is unnecessary for the recovered method and was
removed; no checked exception escapes its operations.

## `wrapper.b.ea(String)`

Evidence is `com/gameloft/android/wrapper/b.smali`, method starting at line 150.

Recovered the original entry-size/directory-prefix implementation instead of
the exported `UnsupportedOperationException`:

- Null and empty names return zero before consulting `Ov()`.
- Obtain the APK path through `Ov()` before the ZIP exception handler.
- If an exact ZIP entry exists, return its uncompressed size. An explicit
  directory entry therefore takes precedence over summing descendants.
- Otherwise append `File.separator` when needed and sum sizes of entries whose
  names start with that prefix. Similar sibling prefixes are excluded.
- Close the ZIP on the normal path. On caught `Exception`, return the size
  accumulated so far; preserve the original exception/cleanup behavior.

## Validation and limits

An isolated Java 8-source harness compiled the **exact recovered method bodies**
with JDK 21.0.9 and executed 18 assertions successfully. It exercises real
temporary ZIP and file IO, exact-entry versus prefix behavior, missing ZIP,
`Ov()` exceptions, success and exception returns, output stream closure,
ignored compression-false behavior and the original null-stream failure.

Harness source and classes are retained at
`build/reports/restoration/java-blockers/RecoveryHarness.java` in this machine.
The harness uses test-only stand-ins for `Context` and `Bitmap`, outside the app
source tree. It validates the recovered control flow and IO; it does **not**
validate Android's actual JPEG encoder, full application compilation or gameplay.

CFR output from converted classfiles was consulted as a cross-check, but it also
fails to structure these overlapping exception/register lifetimes. Smali and
its exception table, rather than generated pseudocode, were the primary evidence.
