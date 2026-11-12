# SDK method recovery from official bytecode

Only the methods named below were reconstructed. No complete SDK dependency or
binary has been added to the application. This does not replace the original
game APK/DEX needed to verify application-specific code or shrunk SDK changes.

The repository's `play-services-ads.properties` and
`play-services-analytics-impl.properties` both identify version 15.0.0. The
corresponding official Google Maven artifacts contain the same class names,
field names, method descriptors, strings and branch conditions as the broken
JADX methods. Artifact downloads used HTTPS and were checked against Google's
published `.sha1` sidecars. SHA-256 fingerprints are recorded below.

| Artifact | SHA-256 |
| --- | --- |
| [play-services-ads-15.0.0.aar](https://dl.google.com/dl/android/maven2/com/google/android/gms/play-services-ads/15.0.0/play-services-ads-15.0.0.aar) | `f675c2814d57738e2989cf6442819d6ad55dc4bfa68bfe7c48d3de1aa50278b1` |
| [play-services-analytics-impl-15.0.0.aar](https://dl.google.com/dl/android/maven2/com/google/android/gms/play-services-analytics-impl/15.0.0/play-services-analytics-impl-15.0.0.aar) | `26e79219df1ceb98e429fe37ae8c6e66df12a94e2c25fa290397bb4c2269d963` |

`classes.jar` was extracted locally and inspected using
`java com.sun.tools.javap.Main -p -c`. CFR 0.152 was used as an additional
decompiler; its output was checked against bytecode, including exception tables.
CFR itself was downloaded from
[Maven Central](https://repo.maven.apache.org/maven2/org/benf/cfr/0.152/cfr-0.152.jar),
verified against its published `.sha1`, and has SHA-256
`f686e8f3ded377d7bc87d216a90e9e9512df4156e75b06c655a16648ae8765b2`.

## Recovered methods

- `com.google.android.gms.internal.measurement.zzbl.zzd(Context)`: preserves the
  worker-thread check, 36-byte read, extra-byte corruption detection, minimum
  length 14, original logs, file deletion rules, return values and stream cleanup.
  The official `Preconditions.checkNotMainThread` call maps to the existing
  obfuscated `bf.hW` call already present in the repository.
- `com.google.android.gms.internal.ads.zzcz.zzb(File,String)`: preserves existing
  cache/dex guards, filename/hash/SDK checks, decoding, dex output, failure return
  values and ignored cleanup IOExceptions. A structured try/catch/finally replaces
  overlapping register lifetimes; no cache verification was disabled. Original
  bytecode offsets 75–286 cover length/read/validation/write operations; handlers
  307 and 338 distinguish the three recoverable exceptions from other throwables.
- `com.google.android.gms.internal.ads.zzbk.zzb(byte[])`: restores the missing
  `throws NoSuchAlgorithmException` declaration, confirmed by the official
  `javap -p` method descriptor listing. The method body is unchanged.

Local downloaded artifacts, CFR output and disassembly are retained under
`/workspace/tooling/recovery-bytecode/`. Their presence does not establish a
successful application build or runtime validation.
