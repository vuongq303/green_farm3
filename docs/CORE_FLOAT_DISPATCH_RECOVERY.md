# Original float overloads in rendering calls

JADX omitted integer-to-float conversions at rendering callsites. Because the
same classes also have integer overloads with different behavior, compiling the
exported Java selected those integer methods. The original DEX method
descriptors and adjacent `int-to-float` instructions establish the intended
targets. Explicit coordinate casts restore those targets without renaming
methods or changing coordinate arithmetic.

The initial Android exception was `ArrayIndexOutOfBoundsException: length=10;
index=256` in `GLLib`'s pointer rectangle method. A sprite draw supplied its
texture width, 256, as that method's pointer index. Original `GLLib.smali` lines
11529–11537 convert the four crop coordinates and call `a(IIFFFF)V`. The Java
call had six integer arguments and selected pointer method `a(IIIIII)Z` instead.
The quad draw path has the same conversion sequence at lines 11175–11183.
Both callsites now target the original texture-coordinate helper.

The same type loss changed sprite frame draws `(aj;IFFI)V` into module draws
`(aj;IIII)V`, a scaled frame draw `(aj;IFFII)V` into animation draw
`(aj;IIIII)V`, line drawing `(aj;FFFFZ)V` into clip changes `(aj;IIIIZ)V`,
and one coordinate wrapper `(FFFFII)V` into pointer checks `(IIIIII)Z`.

## CI callsite evidence

All smali line numbers below refer to the matching original
`/workspace/tooling/original-smali/com/gameloft/android/ANMP/GloftGF2F/S800x480/ci.smali`.
The 29 CI callsites retain their previous integer arithmetic and rounding before
conversion. In particular, `Ke()` retains its explicit float-to-int rounding
before converting the result for the original draw descriptor.

| Containing method | Original float-target invocation lines | Restored Java lines |
|---|---|---|
| `DV()V` | 38688 | 4554 |
| `EA()V` | 41988 | 51871 |
| `El()V` | 50955, 50984, 51008 | 5813, 5816, 5818 |
| `Ep()V` | 51614 | 58405 |
| `GM()V` | 74103 | 9202 |
| `Ke()V` | 133487, 133571 | 14519, 14536 |
| `Kp()V` | 135377, 135688, 135756 | 14821, 14823, 14829 |
| `iF(I)Z` | 256789 | 32255 |
| `ik(I)Z` | 264683 | 50836 |
| `il(I)Z` | 265247, 265687 | 51286, 51312 |
| `im(I)Z` | 266260, 266593 | 62066, 62153 |
| `jR(I)Z` | 302901, 303460 | 62532, 62560 |
| `kx(I)Z` | 336009 | 38043 |
| `lA(I)Z` | 338295 | 53914 |
| `lm(I)Z` | 348811 | 72107 |
| `a(I[SIII)V` | 162898, 163227 | 57338, 57367 |
| `a([SIIZ)V` | 169354 | 53654 |
| `c(IIIIII)V` | 206411 | 24146 |
| `Y(III)V` | 152960, 152980 | 58397, 58398 |

The remaining source group comprises 13 sprite-class callsites (including ten
copies of four original shared glyph calls), 13 peer callsites in `aw`, `bf`
and `ch`, and the two texture-coordinate callsites in `GLLib`: 57 Java callsites
in total. `SPRITE_FLOAT_DISPATCH_RECOVERY.md` records the sprite-class evidence.
Integer sprite methods remain available for original module/animation callers.
Effect-specific rotation and tint discrepancies found during review were left
for a separate correction group, recorded in `SPRITE_EFFECT_BRANCH_FINDINGS.md`.

## Verification and audit limits

`tests/test_core_float_dispatch.py` passes 31 proven overload pairs across the
six affected classes. It reads original and compiled invocation descriptors,
pins the original float-call counts, requires the recovered float target, and
rejects conflicting integer targets absent from the original containing method.
Java shared-block duplication means the restored call count may exceed the
original count. Source/staging identity and compiled-file freshness checks
prevent verification against an earlier build.

`tests/test_gllib_texture_dispatch.py` passes for both full texture draw methods.
It also freshly compiles the current methods in temporary overload fixtures;
removing the casts only in those fixtures selects the pointer method and fails
the expected descriptor check. Fixtures are not packaged into the application.

The read-only `tools/audit_float_dispatch.py` scans matching original/current
class methods for decreased float-overload calls accompanied by increased calls
to a corresponding integer overload. After this group it scanned 133 compiled
classes and reported zero candidates. It does not cover changed containing
method descriptors, renamed/inlined calls or conversions other than float to
integer. Findings require source and smali review; the tool does not rewrite
source. `CORE_FLOAT_DISPATCH_AUDIT_BEFORE.txt` preserves the initial conservative
candidate output; its StringBuilder formatting entry was not changed.

These descriptor checks establish overload selection. They do not establish
pixel-identical Android rendering or successful farm gameplay.
