# Restored sprite effect branches

These three control-flow differences were found while reviewing startup drawing
and restored as a separate group after the float-overload and multiline-anchor
corrections. Original method bytecode and differential tests establish the
changes below; they do not establish that the effects were active in the
observed startup run.

All original references below are to
`/workspace/tooling/original-smali/com/gameloft/android/ANMP/GloftGF2F/S800x480/a.smali`.

## Module offset rotation

Before these changes, Java `a(aj,int,int,float,float,int)` started at line 4546;
the relevant block was lines 4607–4625. Its original DEX descriptor is
`a(Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/aj;IIFFI)V`, method lines
15559–16934.

When the input transform contains bit 4, the ordinary offset transform is
`newX = -oldY - moduleHeight` and `newY = oldX`. Original branches at lines
15961 and 15979 jump to `:cond_415` (line 16904) when the special rotation effect
requires preserving those offsets. That block copies the existing X offset,
leaves Y unchanged, and joins `:goto_157`; it does not perform the ordinary
quarter-turn. The former Java effect branch assigned `f3=fBw`, then fell through
and overwrote it with `f3=-fBx-i7; fBx=fBw`.

The original preservation condition is:

```java
(i3 & 4) != 0
    && w.Zo
    && (GLLib.TX & 262144) != 0
    && (GLLib.TY[18][6] % GLLib.Rk != 0 || GLLib.TY[18][10] != 0)
```

Preservation and the ordinary quarter-turn are now mutually exclusive, matching
the original branch. The transformed flags still use `HY`; only the lost
coordinate branch was restored.

## Animation offset rotation

Before these changes, Java `a(aj,int,int,int,int,int)` started at line 4355; its
relevant block was lines 4374–4389. The original descriptor is
`a(Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/aj;IIIII)V`, method lines
17028–17454.

The same preservation condition uses argument `i5` for the input transform.
Original lines 17240/17258 branch to `:cond_12c` (line 17445), which preserves
the animation offsets before joining `:goto_ba`. The former Java assignment
`i6=iBu` fell through to `i6=-iBv; iBv=iBu`, losing the original exclusive branch.
The restored Java preserves the offsets when the original condition holds.
Outside that condition, the original ordinary transform remains
`newX=-oldY; newY=oldX`.

## Tint enable guard

Before these changes, private Java `a(aj,int,float,float,float,float,int)` started
at line 4748; the relevant block was lines 4770–4799. Original descriptor
`a(Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/aj;IFFFFI)V` starts at line 805;
the color-selection evidence is in lines 805–1000.

When `w.Zj` is false, original `:cond_58` reads `w.Zm`. If that flag is false it
branches directly to `:cond_73` and calls `bH(i)`. If true, it tests
`GLLib.TX & 16384`; only an enabled effect selects `GLLib.TY[14][0]`.
The former Java `else if (w.Zm)` called `bH(i)` and tested the effect only in the
following `else`, reversing that flag's meaning. Corrected that fallback guard
to `!w.Zm`. The `w.Zj=true` branch retains its original grayscale/tint ordering.

## Read-only runtime observations that would establish relevance

JDI can read `w.Zo`, `w.Zj`, `w.Zm`, `GLLib.TX`, `GLLib.Rk`, and rows 14/18 of
`GLLib.TY`. To determine relevance, sample at entry to the exact draw overload,
including its transform argument. Effect state can be temporary and restored
within one draw, so an idle global snapshot showing `TX=0` does not rule out
activation during drawing. The two rotation findings require transform bit 4
and the full preservation condition above; the tint finding requires
`w.Zj=false` and affects the color choice governed by `w.Zm` and bit 16384.
No field writes, forced effect flags, or production logging are needed for
those observations.

An observed runtime sample had `Zo=true`, `Zj=false` and `Zm=false`. The complete
rotation condition, transform argument and transient `TX` state were not
established for that draw; the sample does not prove that a corrected effect
branch caused the startup symptoms.

## Differential verification

On 2026-11-12, both focused comparisons passed without skips. They retain the
actual original method Code in temporary fixture classes and freshly extract
and compile the current source methods. Test-only peers record final draw and
effect calls without accessing Android or a GPU; no fixture class is packaged
into the game.

- `tests/test_sprite_effect_offsets.py`: 18,432 animation/module outcomes across
  transform flags 0–7, all source transform flags 0–7, effect support enabled or
  disabled, inactive/integral/nonintegral/alternate rotations, both scale modes,
  disabled/short/byte mapping offsets and normal/hyper-frame dispatch. The check
  compares final draw coordinates/flags, module dimensions and effect-table
  writes. Original transform arrays are supplied without running unrelated
  static initialization; only fixture field final modifiers are removed, while
  tested Code remains unchanged.
- `tests/test_sprite_tint_branch.py`: 5,760 outcomes across every `Zj`/`Zm`
  combination, grayscale/tint effect bits alone and together, unrelated active
  bits, opaque/transparent/partial-alpha colors, two module indices and
  render-target save/restore. Module types deliberately select no GPU geometry;
  the check compares selected colors, base-color lookup and target call order.

Every valid fixture must return and record its final draw or selected color,
so matching setup failures cannot satisfy the comparison. Independently
reinstating each of the three former branches in an in-memory source mutation
caused the corresponding differential check to fail; production source was
not changed during those mutation checks.

These checks establish the recovered branch behavior under controlled inputs.
They do not prove framebuffer pixels, player interaction or farm gameplay.
