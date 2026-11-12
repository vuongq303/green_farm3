# Sprite coordinate overload recovery

The original DEX distinguishes module drawing
`a(aj,int,int,int,int)` from frame drawing
`a(aj,int,float,float,int)`. JADX retained integer expressions for some frame
coordinates but omitted the original `int-to-float` operations. Java then
selected the module overload, changing which sprite data and indices were used.

Restored explicit coordinate casts only at callsites whose original invocation
descriptor is `a(Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/aj;IFFI)V`.
The matching original `a.smali` is under
`/workspace/tooling/original-smali/com/gameloft/android/ANMP/GloftGF2F/S800x480`.

| Containing original method | Original evidence | Java correction |
|---|---|---|
| `a(aj;IIIII)V` animation-frame wrapper | Lines 17363–17375 add each animation offset, convert both coordinate sums with `int-to-float`, then invoke the frame overload. | Cast the completed coordinate sums, preserving integer addition before conversion. |
| `a(aj;IIIIIIZ)V` scaled-frame wrapper | Lines 17515–17527 convert the two position arguments before invoking the frame overload. | Cast both position arguments. |
| `a(aj;String;[CIIIIZZZ)V` glyph rendering | Lines 18625–18637, 18681–18697, 18716–18728 and 18880–18892 convert glyph/underline positions before frame draws. | Cast both coordinates in all corresponding glyph, bold and underline calls. |
| `a([IIIIIZZ)V` frame bounds calculation | Lines 22380–22390 convert both positions and invoke the frame overload while `HQ=1`. | Cast both positions in the null-graphics call used to compute frame bounds. |

The original text method shares its three ordinary glyph/bold/underline draw
sites across several character paths. JADX duplicated that shared block into
the escaped-backslash, ordinary-character and control-character-2 branches.
Those three copies plus the separate space-underline branch require ten
physical Java callsite corrections for four original bytecode invocations.
Together with the three wrappers, this change contains thirteen Java callsite
corrections.

Intentional integer module draws remain integer calls. This change does not
alter coordinate arithmetic, sprite selection, rendering effects, defaults,
error handling or assets.

The related runtime failure in `GLLib` is a separate overloaded-helper error:
two texture-coordinate calls originally target `a(IIFFFF)V`, but all-integer
Java arguments selected pointer hit-testing `a(IIIIII)Z`. The captured exception
was `ArrayIndexOutOfBoundsException: length=10; index=256`; the texture width
became a pointer-array index. Those `GLLib` callsites are maintained separately.

The class rebuilt on 2026-11-12 at 03:15:34 UTC was checked against the original
converted class's invocation descriptors. The animation wrapper, scaled wrapper
and bounds helper each retain one frame-overload call; the text renderer has ten
frame-overload calls corresponding to the four original shared calls. None of
these four methods retains an accidental integer module-overload call. The
staged source matches the edited source exactly and the rebuilt class postdates
it. A wider audit of all 133 rebuilt classes reports no remaining rendering
float-overload deficit.

This descriptor check does not prove Android startup, rendering correctness,
age-screen navigation or gameplay.

## Multiline vertical anchor continuation

The first restored render displayed the age-screen body below its original
position. The multiline renderer `a(aj,String,short[],IIIIII)` computed the
bottom or vertical-center anchor position, then overwrote either result with
the unadjusted y argument. Original smali19398–19407 subtracts the complete block
height for anchor bit32, while `:cond_10b` subtracts half the block height for
bit2 and returns to `:goto_4b`. When neither bit is set, the original y argument
reaches that continuation unchanged. Restored the corresponding final `else`
in Java; bottom-anchor precedence and integer shifting are unchanged.

`tests/test_sprite_text_anchor.py` freshly extracts and compiles the source
method and compares it with the original converted method's unchanged Code in
a temporary fixture. Test-only helpers record line coordinates and supply
controlled clip, font and style state; they are not packaged into the game.
All 7,392 cases passed on 2026-11-12, including anchor combinations, one-to-four
lines, partial line ranges, character limits, clipping, extended table formats
and style restoration. The check compares draw coordinates, palette calls,
restored style flags and character-range state. It does not compare rendered
pixels or establish the cause of any remaining single-line text offset.
