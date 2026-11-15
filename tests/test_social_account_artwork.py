"""Exercise the real account logo renderer's original-resource and GL lifecycle contracts."""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath
from android_build_tools import jdk_tool


class SocialAccountArtworkTest(unittest.TestCase):
    def test_bundled_mark_keeps_coordinates_and_drops_old_context_handles(self):
        sources = {
            'android/content/res/Resources.java': 'package android.content.res; public class Resources {}',
            'android/content/Context.java': '''package android.content;
public class Context { public Context application=this; public final android.content.res.Resources resources=new android.content.res.Resources();
public Context getApplicationContext(){return application;} public android.content.res.Resources getResources(){return resources;} }''',
            'android/graphics/Bitmap.java': '''package android.graphics;
public class Bitmap { public static int recycled;public final int size;public Bitmap(int s){size=s;}
public static Bitmap createScaledBitmap(Bitmap b,int w,int h,boolean filter){if(w!=h||!filter)throw new AssertionError();return b.size==w?b:new Bitmap(w);}
public void getPixels(int[] p,int offset,int stride,int x,int y,int w,int h){if(p.length!=size*size||w!=size||h!=size)throw new AssertionError();java.util.Arrays.fill(p,0xff4285f4);}
public void recycle(){recycled++;} }''',
            'android/graphics/BitmapFactory.java': '''package android.graphics;
public class BitmapFactory {public static class Options {public boolean inScaled=true;}
public static int decodes;public static android.content.res.Resources lastResources;
public static Bitmap decodeResource(android.content.res.Resources r,int id,Options o){
if(id!=0x7f020038||o.inScaled)throw new AssertionError("must reuse unscaled original Google mark");decodes++;lastResources=r;return new Bitmap(18);}}''',
            'android/util/Log.java': 'package android.util; public class Log {public static int w(String t,String m){return 0;}}',
            'com/quanhoang/greenfarm3/R.java': 'package com.quanhoang.greenfarm3;public class R {public static class drawable {public static final int googleg_standard_color_18=0x7f020038;}}',
            'com/quanhoang/greenfarm3/S800x480/ay.java': '''package com.quanhoang.greenfarm3.S800x480;
public class ay {public static int uploads,deletes;public static ay a(int[]p,int w,int h,boolean alpha){if(!alpha||p.length!=w*h||p[0]!=0xff4285f4)throw new AssertionError();uploads++;return new ay();}public void nk(){deletes++;}}''',
            'com/quanhoang/greenfarm3/S800x480/GameRuntime.java': '''package com.quanhoang.greenfarm3.S800x480;
public class GameRuntime {public static final Object Qj=new Object();public static int draws;
public static void a(Object graphics,ay image,int x,int y,int anchor,boolean enabled){if(graphics!=Qj||image==null||x!=505||y!=408||anchor!=20||!enabled)throw new AssertionError("original coordinates/anchor changed");draws++;}}''',
            'ArtworkChecks.java': '''import com.quanhoang.greenfarm3.S800x480.*;
public class ArtworkChecks {static void ok(boolean b,String m){if(!b)throw new AssertionError(m);}
public static void main(String[]args){
ok(!SocialAccountArtwork.drawGoogleIcon(505,408,32),"missing context must not allocate GL texture");
android.content.Context activity=new android.content.Context(),application=new android.content.Context();activity.application=application;
SocialAccountArtwork.initialize(activity);SocialAccountArtwork.onSurfaceCreated();
ok(SocialAccountArtwork.drawGoogleIcon(505,408,32),"first original logo draw");
ok(ay.uploads==1&&android.graphics.BitmapFactory.decodes==1&&android.graphics.Bitmap.recycled==2,"decode/scale/upload once and free CPU bitmaps");
ok(android.graphics.BitmapFactory.lastResources==application.resources,"must retain application context");
ok(SocialAccountArtwork.drawGoogleIcon(505,408,32)&&ay.uploads==1,"subsequent frame reuses current GL texture");
SocialAccountArtwork.onSurfaceCreated();
ok(ay.deletes==0,"new EGL context must not delete old context handle");
ok(SocialAccountArtwork.drawGoogleIcon(505,408,32)&&ay.uploads==2,"new EGL context rebuilds logo texture");
ok(SocialAccountArtwork.drawGoogleIcon(505,408,18)&&ay.uploads==3&&ay.deletes==1,"same-context resize disposes its previous texture");
ok(android.graphics.Bitmap.recycled==5,"identity scaling recycles bitmap exactly once");
int calls=GameRuntime.draws;ok(!SocialAccountArtwork.drawGoogleIcon(505,408,0)&&!SocialAccountArtwork.drawGoogleIcon(505,408,100)&&GameRuntime.draws==calls,"invalid footprint cannot alter UI");
System.out.println("PASS bundled Google mark, original coordinates, bitmap ownership and EGL context lifecycle");
}}''',
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
            self.assertIn('PASS bundled Google mark', result.stdout)


if __name__ == '__main__':
    unittest.main()
