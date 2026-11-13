# Original format4 initialization oracle

`original-gameplay-oracle.json` contains5 public synthetic vectors produced by
verbatim original Java methods, plus the numeric result from a read-only existing
normal SQLite backup. No device binding, session, raw player payload or private
database is included. The private backup record checksum was verified first.
The known normal farm decoded to1900 coins,50 cash, XP7, level1 and tutorial23;
its1900 coins are already spent progress, not a new-player default.

Original APK SHA-256 is pinned in the JSON. DEX instructions for the selected
methods were inspected independently with dexlib2; the signed `SHR_INT` and
`SHR_LONG`, sign extension of the XOR key and little-endian primitive reads agree
with the extracted source. Relevant instruction listings are included here.
The current raw format4 backup independently matches the known live values.

The exact original layout in `rt()` and corresponding `ax()` load is: format byte
then a little-endian32-bit UTF-8 binding byte length and binding bytes. Let P be
5 plus that byte length. Four flags precede tutorial at P+4. Tutorial plus6 flags,
2 ints and a long occupy23 more bytes, so fixed fields before quests total27.
Four quest slots each take23 bytes;304 quest-state ints take1216 bytes. Thus player
level is at L=P+27+4*23+304*4=P+1335, XP at L+4, signed64-bit encoded coins at L+8,
cash at L+16. The short at L+20 is followed by plot/tree/animal/pond limits at
L+22/L+30/L+34/L+42. `RMSHeader[4]` is the selected game language.

`OriginalGameplayOracle.java` contains the8 extracted primitive/encoding methods
and assertions for the5 synthetic vectors. Compile it with a JDK, then run with
empty standard input to reproduce those public vectors. An optional base64 input
containing a format4 gameplay record produces only numeric fields; keep any real
save input private. This oracle is a host research tool, not code in the APK.
