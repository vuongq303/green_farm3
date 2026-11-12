# RecordStore writer recovered from the original APK

The missing `javax.microedition.b.b.b(int, byte[], int, int)` method is now
reconstructed from `classes.dex`, not from an assumed save format. Class and
method names, fields, storage paths and the public API are unchanged.

## Evidence

- Input: repository `green-farm-3.apk` added in commit
  `228cc7dd7b4b413f430e93c74cc147a1ffb8a23c`.
- Package `com.gameloft.android.ANMP.GloftGF2F`, version `4.4.2`, code `44220`.
- APK SHA-256:
  `34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b`.
- DEX SHA-256:
  `3901b6678b4523947273914b30fe94a750e149f89e0a7a1fa3932261d7539ec4`.
- Authoritative method: `Ljavax/microedition/b/b;->b(I[BII)V`, original debug
  source lines 1014–1081. Baksmali 3.0.3 output is retained locally at
  `/workspace/tooling/original-smali/javax/microedition/b/b.smali`; its SHA-256
  is `1df231ec668d23843f9992d44070c6136aead508ad534ca45ce1107c0fdca950`.
- Independent cross-check: JADX 1.5.3 recovered the same writer with renaming,
  method inlining, anonymous-class inlining and inner-class movement disabled.
  Output is retained locally at
  `/workspace/tooling/recovery-bytecode/jadx-original/recordstore.java`.
  That run fails to recover a different method, so the complete decompiled file
  was not substituted for the checked-in class.

## Preserved behavior

1. Return immediately when both original demo flags `MIDlet.coj` and `cok` are
   enabled.
2. Obtain the backup generation through the existing `pj(recordId)` helper.
3. Write CRC before opening the saved record, using the existing helper `a`.
   CRC is serialized as eight big-endian bytes. The original checksum covers
   `data[0:length]`, although the payload write uses `data[offset:offset+length]`.
   This offset discrepancy is preserved and tested; it was not silently fixed.
4. Write `<store>_<id>_saved<generation>`: one low byte of record ID, a four-byte
   big-endian length, and the payload slice. Update `cpd` to that length. The
   writer itself does not create or replace the canonical `<store>_<id>` file.
5. Apply the original `cpe` backup retention. Every 625 generations below 10000,
   remove older CRC files in the original interval except checkpoint indices
   divisible by 625 and the immediately following index. Keep the generation
   saturation behavior of `pj`/`pk` at 10000.
6. Close the saved-record stream in `finally`. Propagate write errors; preserve
   the original behavior in which an error from `close()` replaces an in-flight
   exception. The redundant catch-and-rethrow in DEX is expressed by an equivalent
   `try/finally`, without adding suppression or swallowing failures.

## Validation and limits

Run:

```sh
python3 -m unittest discover -s tests -p 'test_recordstore_writer.py' -v
```

All **eight tests passed**. They compile the recovered production writer and its
existing CRC/generation helpers verbatim into a small Java harness. Only the
Android file-context adapter and demo-flag holder are replaced with test adapters.
Checks cover exact record bytes/CRC encoding, the offset discrepancy, backup
retention, sparse CRC checkpoints, the generation cap, demo mode, write failure
cleanup and close-failure precedence.

These are format and failure-path tests of the actual recovered method, not a
claim that Android save/load or gameplay has been tested. The rest of this class
and the surrounding Java project retain other pre-existing JADX defects, such as
an out-of-scope exception local in the constructor and checked-exception metadata
lost elsewhere. They were not changed as part of this isolated writer repair.
