# Regenerating build-size metadata while retaining the original guard

The Java-core build changes the DEX byte count. The original APK includes an
encrypted `REVISION-2408` property describing that count. Reusing the original
value with newly compiled code causes the unchanged SDK guard to reject the
package. This is per-build metadata, rather than a gameplay setting or save file.

## Original bytecode evidence

The analysis uses the reviewed `green-farm-3.apk` (4.4.2/code44220), SHA-256
`34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b`.
Relevant original smali is retained under `/workspace/tooling/original-smali/`:

1. `com/gameloft/android/ANMP/GloftGF2F/Pt.smali`: `init()` reconstructs the
   property name `REVISION-2408` from `EV`. `EW` is the four-byte big-endian
   integer `00 02 26 BC`; `a(byte[])` decodes it using `ByteBuffer.getInt()`.
2. `.../bh.smali`: `run()` loads the property through `MIDlet.d`, parses a long,
   XORs it with `Pt.a(EW)`, and compares the result with `wrapper.b.eb(".dex")`.
   The original mismatch branch stops the game. Its delay and termination logic
   are not modified by this work.
3. `com/gameloft/android/wrapper/b.smali`: `eb(String)` enumerates the installed
   APK ZIP, summing `ZipEntry.getSize()` for every entry whose name ends with
   `.dex`. These are **uncompressed** sizes, not compressed ZIP lengths. The
   suffix test is not restricted to `classes*.dex` names.
4. `javax/microedition/midlet/MIDlet.smali`: `d`/`e` load the definition when
   `cnP` is enabled; `au.PV()` supplies its lines, and `jh` splits each property
   at its first colon and trims its key/value. Later duplicate keys overwrite
   earlier values, so malformed duplicate revision keys must be rejected.
5. `com/gameloft/android/wrapper/au.smali`: `PW()` reads raw resource `content`;
   `PU()` decrypts it using `h.T(PW(), bih)` and splits CR/LF lines. `bih` is the
   public format seed already embedded in the APK, not an external credential.
6. `com/gameloft/android/wrapper/h.smali`: `T` Base64-decodes ciphertext,
   derives a key from the seed and calls `Cipher.getInstance("AES")` in decrypt
   mode. `ee` hashes seed UTF-8 with SHA-256, renders lowercase hexadecimal and
   takes the first16 characters; `ed` uses their bytes as the AES128 key. This
   is **not** the first16 raw digest bytes. The compatible explicit JCE form is
   `AES/ECB/PKCS5Padding`, with no IV.

An independent host check executed the actual original `h.T` and `Pt.a` methods
from the converted original classfile jar, rather than their restored Java
implementations. It confirmed:

```text
Original uncompressed DEX bytes: 7655124
XOR mask:                      140988 (0x226BC)
Original REVISION-2408:         7792744
7655124 XOR 140988 =            7792744
```

The original decrypted definition contains2236 bytes and40 CR/LF pairs. Its
other properties are intentionally not reproduced in this report or test output.

## Generated resource and unchanged runtime behavior

`tools/UpdateBuildRevision.java` reads the original encrypted `content.bin`,
requires exactly one valid revision key, verifies its original DEX-size relation,
and replaces only that property's decimal digit span with:

```text
new REVISION-2408 = total rebuilt uncompressed .dex bytes XOR 0x226BC
```

It uses a byte-preserving Latin-1 carrier to retain every unrelated plaintext
byte, including line endings and whitespace, then re-encrypts using the original
format. It verifies both encryption round trips and the original guard relation
and emits a JSON record binding input/output hashes to both DEX sizes. It refuses
to overwrite the original metadata or accept missing/duplicate/malformed keys,
an incorrect original size, nonpositive sizes or invalid ciphertext.

The packaging hook updates only generated APK entry `res/raw/content.bin` after
the merged DEX and payload have been verified, and before alignment/signing.
All other APK entry bytes and compression settings are checked unchanged by that
hook. The original APK and checked-in resource remain untouched. Signing covers
the final generated metadata and code together.

The SDK guard, comparison, decryption, failure branch and executable SDK ClassDefs
remain original. Its expected size is regenerated to describe the actual rebuilt
code, so it still rejects a stale expected size or a subsequent different DEX
size. No always-success return, skipped check, exception suppression, alternate
game initialization path or modification to online/gameplay rules is introduced.

## Verification and bounds

```sh
python3 -m unittest discover -s tests -p 'test_update_build_revision.py' -v
```

The tests compile the current metadata tool and use the original `h.T` and
`Pt.a` as an independent oracle. They verify all unrelated decrypted bytes are
unchanged, the regenerated size is accepted, stale/tampered sizes reject, same
size produces identical original ciphertext, changed digit counts/long sizes
work, aliases cannot overwrite the original, and malformed inputs produce no
output. They do not print the application definition or alter SDK code.

These checks isolate the original reader/size predicate; they do not run its
native delayed thread or prove gameplay readiness. The guard itself is a size
consistency check, not a cryptographic code-authenticity proof: a same-size change
is outside what this original predicate can detect. APK signature verification,
DEX merger semantic verification and runtime tests remain separate checks.

All **eight focused tests passed**, including a fresh run on 2026-11-12 without
skips, in the current environment. The packaging-hook
test additionally checks every other generated APK entry's bytes and ZIP
compression setting and confirms the original APK hash is unchanged. Its modified
DEX is a size-only fixture for the packaging predicate, not an executable Android
test application. The eight existing packaging preflight tests also continue to
pass after adding the metadata hook.
