"""Exercise the real account renderer against its native atlas and original sprite data."""
from pathlib import Path
import struct
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath
from android_build_tools import jdk_tool


class SocialAccountArtworkTest(unittest.TestCase):
    def test_native_account_mark_preserves_button_footprint_and_atlas_ownership(self):
        sources = {
            'com/quanhoang/greenfarm3/S800x480/Sprite.java': '''package com.quanhoang.greenfarm3.S800x480;
public class Sprite {
 public int frames=289,draws;public float x,y;
 public int hv(){return frames;}
 public void a(Object graphics,int frame,float px,float py,int transform){
  if(graphics!=GameRuntime.Qj||frame!=137||transform!=0)throw new AssertionError("original native account frame/renderer");
  draws++;x=px;y=py;
 }
 public void a(Object graphics,int frame,int px,int py,int transform){throw new AssertionError("must preserve the float render overload");}
}''',
            'com/quanhoang/greenfarm3/S800x480/FarmGameEngine.java': '''package com.quanhoang.greenfarm3.S800x480;
public class FarmGameEngine {public static Sprite[] atf;}''',
            'com/quanhoang/greenfarm3/S800x480/GameRuntime.java': '''package com.quanhoang.greenfarm3.S800x480;
public class GameRuntime {public static final Object Qj=new Object();}''',
            'ArtworkChecks.java': '''import com.quanhoang.greenfarm3.S800x480.*;
public class ArtworkChecks {
 static void ok(boolean b,String m){if(!b)throw new AssertionError(m);}
 public static void main(String[]args){
  ok(!SocialAccountArtwork.drawAccountIcon(505,408,32),"missing native atlas cannot allocate or crash");
  FarmGameEngine.atf=new Sprite[20];
  ok(!SocialAccountArtwork.drawAccountIcon(505,408,32),"partial native atlas cannot address missing slot");
  FarmGameEngine.atf=new Sprite[138];
  ok(!SocialAccountArtwork.drawAccountIcon(505,408,32),"unloaded GUI slot is safe");
  Sprite first=new Sprite();FarmGameEngine.atf[20]=first;
  ok(SocialAccountArtwork.drawAccountIcon(505,408,32),"original loaded account icon draws");
  ok(first.draws==1&&first.x==521f&&first.y==424f,"glyph is centered inside original 32px button footprint");
  ok(first.x-10>=505&&first.x+11<=537&&first.y-14>=408&&first.y+15<=440,"original 21x29 pixels fit without moving or scaling button");
  ok(SocialAccountArtwork.drawAccountIcon(100,200,64)&&first.x==132f&&first.y==232f,"larger permitted footprint retains native pixel size");
  Sprite restored=new Sprite();FarmGameEngine.atf[20]=restored;
  ok(SocialAccountArtwork.drawAccountIcon(505,408,32)&&restored.draws==1&&first.draws==2,"new game-owned atlas is used after renderer restoration without retaining old textures");
  int calls=restored.draws;
  for(int size:new int[]{-1,0,16,31,65,100})ok(!SocialAccountArtwork.drawAccountIcon(505,408,size),"invalid footprint rejected");
  ok(!SocialAccountArtwork.drawAccountIcon(-1,408,32)&&!SocialAccountArtwork.drawAccountIcon(505,-1,32),"invalid bounds rejected");
  ok(!SocialAccountArtwork.drawAccountIcon(Integer.MAX_VALUE-16,408,32)&&!SocialAccountArtwork.drawAccountIcon(505,Integer.MAX_VALUE-16,32),"overflow bounds rejected");
  ok(restored.draws==calls,"invalid bounds cannot touch native renderer");
  restored.frames=137;
  ok(!SocialAccountArtwork.drawAccountIcon(505,408,32)&&restored.draws==calls,"incomplete sprite cannot render a missing frame");
  System.out.println("PASS original native account mark, float overload, bounds and game-owned atlas restoration");
 }
}''',
        }
        with tempfile.TemporaryDirectory(prefix='social-account-artwork-') as temp:
            directory = Path(temp)
            peers = []
            for relative, contents in sources.items():
                path = directory / relative
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text(contents)
                peers.append(path)
            output = directory / 'classes'
            compile_mixed([ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/SocialAccountArtwork.kt'], peers, output)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(output), 'ArtworkChecks'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS original native account mark', result.stdout)

    def test_selected_frame_is_the_shipped_padlock_without_asset_changes(self):
        # Original GUI atlas export: pack /11 record 20, BSprite 0x05df.
        # Read its module/frame tables rather than comparing a replacement image.
        assets = ROOT / 'app/src/main/assets/800x480'
        header = (assets / '11').read_bytes()
        count, parts = struct.unpack_from('<HH', header)
        starts = struct.unpack_from('<' + 'H' * parts, header, 4)
        part = max(i for i, start in enumerate(starts) if start <= 20)
        raw = header if part == 0 else (assets / ('11.' + str(part))).read_bytes()
        entries = (starts[part + 1] if part + 1 < parts else count) - starts[part]
        offsets = struct.unpack_from('<' + 'i' * (entries + 1), raw, 4 + parts * 2 if part == 0 else 0)
        slot = 20 - starts[part]
        sprite = raw[offsets[slot] + 1:offsets[slot + 1]]
        self.assertEqual(struct.unpack_from('<HII', sprite), (0x05df, 0x405185b1, 0x100000))
        modules, = struct.unpack_from('<H', sprite, 10)
        # This export uses 9-byte bitmap/solid-rectangle module records.
        self.assertTrue(all(sprite[12 + 9 * i] in (0, 254) for i in range(modules)))
        self.assertEqual(struct.unpack_from('<Bhhhh', sprite, 12 + 99 * 9), (0, 118, 400, 21, 29))
        cursor = 12 + 9 * modules
        fmodules, = struct.unpack_from('<H', sprite, cursor)
        fmodule_table = cursor + 2
        cursor = fmodule_table + 7 * fmodules
        collision_rects, = struct.unpack_from('<H', sprite, cursor)
        cursor += 2 + collision_rects * 8
        frames, = struct.unpack_from('<H', sprite, cursor)
        self.assertGreater(frames, 137)
        frame_modules, first_module, _ = struct.unpack_from('<BHB', sprite, cursor + 2 + 4 * 137)
        self.assertEqual(frame_modules, 1)
        self.assertEqual(struct.unpack_from('<HhhB', sprite, fmodule_table + 7 * first_module), (99, -10, -14, 0))


if __name__ == '__main__':
    unittest.main()
