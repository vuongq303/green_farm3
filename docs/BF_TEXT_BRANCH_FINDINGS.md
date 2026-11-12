# Restored `bf` text branches

Status: **fixed and differentially verified** on 2026-11-12. These findings came
from the age-screen layout audit. The renderer restoration corrected the
`XA/XE/XF` conditions and consolidated character-2 escapes into the shared glyph
path. The branch evidence below describes the pre-restoration Java.

The reference is Green Farm 3 4.4.2 / version code 44220, disassembled at:

```text
/workspace/tooling/original-smali/com/gameloft/android/ANMP/GloftGF2F/S800x480/bf.smali
SHA-256 c86a52dbbfe172008adf6b3171dc2ae182113502229d3adcd892c59850caceca
```

All findings concern this method:

```text
bf.a(aj, String, char[], int, int, int, int, boolean, boolean, boolean)
DEX descriptor:
(Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/aj;Ljava/lang/String;[CIIIIZZZ)V
```

Source line numbers below refer to
[bf.java](../app/src/main/java/com/gameloft/android/ANMP/GloftGF2F/S800x480/bf.java)
before these branch fixes. Smali labels remain useful after source lines move.

## 1. Normal glyph range-check flag is inverted

Pre-restoration source lines 237–244 kept the glyph index when:

```java
w.XA || index <= hv()
```

Original smali lines 818–851, at `:goto_26a`, instead do:

```text
sget-boolean ..., w->XA
if-eqz ..., :cond_412       # XA=false: keep index
invoke-virtual ..., bf->hv()I
if-le index, limit, :cond_412
... log unavailable character ...
const/4 ..., 0x0           # XA=true and index>limit: use glyph 0
```

The original keep condition is `!w.XA || index <= hv()`.

Both implementations agree for an index within the glyph range. For an index
above `hv()`, pre-restoration Java clamped when `XA=false`; original code clamps when
`XA=true`. This is a flag-policy difference, not evidence that age-screen text
contains an out-of-range glyph.

## 2. Normal glyph vertical-advance conditions are inverted

Pre-restoration source lines 258–274 advanced the glyph Y coordinate using:

```java
if (!w.XE && IN) {
    y += bO(index);
} else if (w.XF || IS == 0) {
    // keep y
} else {
    y += IS;
}
```

Original smali lines 950–976 require **both** `w.XE` and `IN` before invoking
`bO(index)` and adding its result. Either false condition branches to
`:cond_3de`:

```text
sget-boolean ..., w->XE
if-eqz ..., :cond_3de
iget-boolean ..., bf->IN
if-eqz ..., :cond_3de
invoke-virtual ..., bf->bO(I)I
add-int nextY, currentY, glyphAdvance
```

At `:cond_3de`, original lines 1247–1272 add `IS` only when `w.XF=true` and
`IS!=0`. Otherwise `:cond_408` (1294–1303) retains the current Y coordinate.
The original flow is therefore:

```java
if (w.XE && IN) {
    y += bO(index);
} else if (w.XF && IS != 0) {
    y += IS;
}
```

The pre-restoration Java condition differed for the `XE/IN` glyph-advance mode and for
nonzero `IS`. With the observed age-screen `IN=false, IS=0`, both flows retain
the glyph Y coordinate between ordinary characters.

## 3. Character-2 escape duplicates an incomplete shared path

Character value `2` selects a literal glyph index from the following character.
Original smali lines 1226–1245 (`:cond_3cc`) read that index and jump to
`:goto_26a`. Thus escaped glyphs use the same range check, rendering, horizontal
advance, and conditional vertical advance described above.

Pre-restoration source lines 323–367 duplicated rendering instead. Its `if (w.XA)`
arms both assigned the unmodified index, and all final `XE/XF` arms retained the
same Y coordinate. It therefore omitted:

- Original `XA=true && index>hv()` fallback to glyph 0.
- Original `XE && IN` advance by `bO(index)`.
- Original fallback `XF && IS!=0` advance by `IS`.

Ordinary text without a character-2 escape does not exercise this duplicated
path. For an escaped valid glyph with `IN=false, IS=0`, these differences do not
change vertical position.

## Why these were not used to patch age-screen coordinates

Runtime inspection reported:

```text
w.XA=false  w.XE=false  w.XF=false  w.XG=false
w.Zo=true   w.Zm=false  w.Zj=false
bf fonts: IN=false, IS=0, Jc=-1
IT values observed across fonts: 15, 18, 31
```

The displayed age-screen characters were in range. The divergent vertical
branches were inactive with `IN=false, IS=0`. These findings were therefore not
used to explain or compensate for the header/body layout difference.

`bf` does not override text measurement, wrapping, `hG()`, or `hA()`; these are
inherited from `a`. Its initial `IT` adjustment and `IJ/IK` anchor handling
(source 122–169; original smali 133–249 and 525–666) match the reference.
Its ordinary newline step `hA()+hG()` also matches original `:cond_390`, smali
1155–1176. The age-screen investigation continued in the inherited wrapped-text
anchor path in `a`.

## Verification

[test_bf_text_renderer.py](../tests/test_bf_text_renderer.py) passed **4,920**
comparisons with no skips. It loads the complete original `bf.class` unchanged,
including its original constant pool and method Code, and compares it with
freshly compiled current source. Test-only `a/w/aj` peers and `ProbeFont` overrides
record the `nt()/nu()` rendering endpoints without invoking Android graphics.

Cases cover valid/out-of-range glyphs, `XA` both ways, `XE/IN` combinations,
`XF` with zero/positive/negative `IS`, character-2 escapes, String/char-array input,
styles, palette and character limits, anchors/scaling, shadow/peer recursion,
and injected same-instance `RuntimeException`/`Error` outcomes. Pure `hA()/hG()`
getters are intentionally unlogged. No guessed coordinate offsets or flag
defaults were introduced.
