"""Compare recovered tile-render control flow with original method bytecode.

Fixtures omit unrelated Android initialization, retain original method Code
unchanged, and record sprite/effect calls through test-only stand-ins. This
checks render selection/coordinates; it is not a pixel or Android gameplay test.
"""

from pathlib import Path
import os
import shutil
import subprocess
import sys
import tempfile
import unittest
import zipfile

import test_gllib_recovered_algorithms as fixtures


ROOT = Path(__file__).resolve().parents[1]
PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'
METHODS = {('a', '(Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/aj;IIIIIII)V'),
           ('ab', '(II)Z'), ('w', '(III)I'), ('a', '(IIIZ)I')}
FIELDS = {'abT', 'abS', 'abU', 'abV', 'abW', 'abP', 'abQ', 'abR'}


def extract_method(text, signature):
    start = text.index(signature)
    opening = text.index('{', start)
    depth = 1
    position = opening + 1
    while depth:
        depth += (text[position] == '{') - (text[position] == '}')
        position += 1
    return text[start:position]


RUNNER = r'''
import java.lang.reflect.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
public class TileRendererComparison {
    static final String P = "com.gameloft.android.ANMP.GloftGF2F.S800x480.";
    static Class<?> load(String directory) throws Exception {
        URLClassLoader loader = new URLClassLoader(new URL[]{Paths.get(directory).toUri().toURL()}, null);
        return Class.forName(P + "aa", true, loader);
    }
    static void set(Class<?> type, String name, Object value) throws Exception {
        Field field = type.getDeclaredField(name); field.setAccessible(true); field.set(null, value);
    }
    static Class<?> peer(Class<?> type, String name) throws Exception {
        return Class.forName(P + name, true, type.getClassLoader());
    }
    static Object run(Class<?> type, int[][] map, int[] screen, byte[][][] bytes,
                      int flags, int mode, int overlayMode, int opacity, int[] args) throws Exception {
        Class<?> options = peer(type, "w");
        set(options, "Wq", (flags & 1) != 0); set(options, "Wr", (flags & 2) != 0);
        set(options, "Ww", (flags & 4) != 0); set(options, "Wv", (flags & 8) != 0);
        set(type, "abT", map); set(type, "abS", screen); set(type, "abU", bytes);
        set(type, "abW", opacity); set(type, "abP", 0); set(type, "abQ", 1); set(type, "abR", overlayMode);
        Class<?> sprite = peer(type, "a");
        Object banks = Array.newInstance(sprite, 2);
        Array.set(banks, 0, sprite.getConstructor(int.class, int.class).newInstance(0, mode));
        Array.set(banks, 1, sprite.getConstructor(int.class, int.class).newInstance(1, (mode + 1) & 1));
        set(type, "abV", banks);
        Class<?> effects = peer(type, "GLLib");
        List<String> calls = (List<String>) effects.getField("calls").get(null); calls.clear();
        Class<?> graphics = peer(type, "aj");
        Method draw = type.getDeclaredMethod("a", graphics, int.class, int.class, int.class,
                                             int.class, int.class, int.class, int.class);
        draw.setAccessible(true);
        String error = "";
        try { draw.invoke(null, graphics.getConstructor().newInstance(), 0,
                          args[0], args[1], args[2], args[3], args[4], args[5]); }
        catch (InvocationTargetException caught) { error = caught.getCause().getClass().getName(); }
        return error + ":" + calls.toString();
    }
    public static void main(String[] args) throws Exception {
        Class<?> original = load(args[0]), restored = load(args[1]);
        Random random = new Random(44220);
        for (int trial = 0; trial < 4000; trial++) {
            int width = 2 + random.nextInt(6), height = 2 + random.nextInt(6), count = width * height;
            int format = random.nextInt(16);
            int wrap = (random.nextBoolean() ? 1 : 0) | (random.nextBoolean() ? 2 : 0)
                     | (random.nextBoolean() ? 16 : 0) | (random.nextBoolean() ? 32 : 0);
            int[][] map = new int[2][14];
            for (int[] layer : map) { layer[2] = width; layer[3] = height; layer[13] = wrap; }
            int[] screen = new int[6]; screen[2] = 8 + random.nextInt(12); screen[5] = 9 + random.nextInt(12);
            byte[][][] data = new byte[2][2][];
            for (int layer = 0; layer < 2; layer++) {
                data[layer][0] = new byte[(format & 1) != 0 ? count * 2 : count];
                data[layer][1] = new byte[count];
                for (int tile = 0; tile < count; tile++) {
                    int value = random.nextInt(5) == 0 ? ((format & 1) != 0 ? 65535 : 255) : random.nextInt(200);
                    if ((format & 1) != 0) { data[layer][0][tile * 2] = (byte)value; data[layer][0][tile * 2 + 1] = (byte)(value >> 8); }
                    else data[layer][0][tile] = (byte)value;
                    int transform = random.nextInt((format & 4) != 0 ? 16 : (format & 8) != 0 ? 4 : 8);
                    if ((format & 4) != 0) data[layer][1][tile >> 1] |= transform << ((tile & 1) << 2);
                    else if ((format & 8) != 0) data[layer][1][tile >> 2] |= transform << ((tile & 3) << 1);
                    else data[layer][1][tile] = (byte)transform;
                }
            }
            int[] draw = {random.nextInt(width + 7) - 3, random.nextInt(height + 7) - 3,
                          random.nextInt(11) - 1, random.nextInt(11) - 1,
                          random.nextInt(81) - 40, random.nextInt(81) - 40};
            int mode = random.nextInt(2), overlay = random.nextInt(8), opacity = random.nextBoolean() ? 100 : 17;
            Object before = run(original, map, screen, data, format, mode, overlay, opacity, draw);
            Object after = run(restored, map, screen, data, format, mode, overlay, opacity, draw);
            if (!before.equals(after)) throw new AssertionError("trial=" + trial + " wrap=" + wrap
                    + " format=" + format + " args=" + Arrays.toString(draw) + "\noriginal=" + before + "\nrestored=" + after);
        }
        System.out.println("PASS: 4000 original/recovered tile renderer traces and exception outcomes match");
    }
}
'''


class TileRendererTest(unittest.TestCase):
    def test_renderer_call_traces_match_original_bytecode(self):
        jar = Path(os.environ.get('GREENFARM_ORIGINAL_JAR', ROOT / '.local/bytecode-tools/greenfarm-original.jar'))
        java_home = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-21.0.9+10'))
        java, javac = java_home / 'bin/java', java_home / 'bin/javac'
        if not jar.exists() or not java.is_file() or not javac.is_file():
            self.skipTest('Requires verified original dex2jar artifact and JDK')
        text = (ROOT / 'app/src/main/java' / PACKAGE / 'aa.java').read_text()
        signatures = ('private static void a(aj ajVar, int i, int i2, int i3, int i4, int i5, int i6, int i7)',
                      'private static boolean ab(int i, int i2)',
                      'private static final int w(int i, int i2, int i3)',
                      'private static int a(int i, int i2, int i3, boolean z)')
        fields = 'static int[][] abT; static int[] abS; static byte[][][] abU; static a[] abV; static int abW,abP,abQ,abR;'
        with tempfile.TemporaryDirectory(prefix='aa-render-differential-') as temp:
            temporary = Path(temp)
            source = temporary / 'sources' / PACKAGE; source.mkdir(parents=True)
            prefix = 'package com.gameloft.android.ANMP.GloftGF2F.S800x480;\n'
            (source / 'aa.java').write_text(prefix + 'public class aa {' + fields + '\n'
                                          + '\n'.join(extract_method(text, signature) for signature in signatures) + '\n}')
            (source / 'aj.java').write_text(prefix + 'public class aj {}')
            (source / 'w.java').write_text(prefix + 'public class w {public static boolean Wq,Wr,Ww,Wv;}')
            (source / 'GLLib.java').write_text(prefix + '''public class GLLib {
                public static java.util.List<String> calls = new java.util.ArrayList<>();
                public static short k(byte[] data,int i) {return (short)((data[i]&255)|((data[i+1]&255)<<8));}
                public static void h(int i,boolean b) {calls.add("h:"+i+":"+b);}
                public static void l(int i,int j,int k) {calls.add("l:"+i+":"+j+":"+k);}
                public static void i(int i,boolean b) {calls.add("i:"+i+":"+b);}
            }''')
            (source / 'a.java').write_text(prefix + '''public class a {
                private int bank,kind; public a(int id,int mode){bank=id;kind=mode;}
                public int hv(){GLLib.calls.add("hv:"+bank);return kind;}
                public int t(int tile,int frame){GLLib.calls.add("t:"+bank+":"+tile+":"+frame);return tile+1000;}
                public void a(aj g,int tile,float x,float y,int flags,int width,int height){GLLib.calls.add("scaled:"+bank+":"+tile+":"+x+":"+y+":"+flags+":"+width+":"+height);}
                public void a(aj g,int tile,float x,float y,int flags){GLLib.calls.add("flipped:"+bank+":"+tile+":"+x+":"+y+":"+flags);}
                public void a(aj g,int tile,int x,int y,int flags){GLLib.calls.add("overlay:"+bank+":"+tile+":"+x+":"+y+":"+flags);}
            }''')
            restored = temporary / 'restored'; restored.mkdir()
            subprocess.run([str(javac), '-source', '8', '-target', '8', '-d', str(restored),
                            *map(str, source.glob('*.java'))], check=True, capture_output=True, text=True)
            original = temporary / 'original'; shutil.copytree(restored, original)
            previous_methods, previous_fields = fixtures.METHODS, fixtures.FIELDS
            try:
                fixtures.METHODS, fixtures.FIELDS = METHODS, FIELDS
                with zipfile.ZipFile(jar) as archive:
                    fixture = fixtures.method_fixture(archive.read(PACKAGE + '/aa.class'))
            finally:
                fixtures.METHODS, fixtures.FIELDS = previous_methods, previous_fields
            (original / PACKAGE / 'aa.class').write_bytes(fixture)
            runner = temporary / 'TileRendererComparison.java'; runner.write_text(RUNNER)
            subprocess.run([str(javac), str(runner)], check=True, capture_output=True, text=True)
            result = subprocess.run([str(java), '-cp', str(temporary), 'TileRendererComparison',
                                     str(original), str(restored)], capture_output=True, text=True, timeout=60)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS: 4000', result.stdout)


if __name__ == '__main__':
    unittest.main()
