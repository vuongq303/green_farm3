"""Compare actual bf text-render Code with the freshly compiled source.

The entire original bf.class remains unchanged. Test-only font/graphics peers
record calls, and a subclass records nt/nu endpoints without invoking Android GL.
No fixture code is added to the application or packaged into an APK.
"""

from pathlib import Path
import os
import shutil
import subprocess
import tempfile
import unittest
import zipfile


ROOT = Path(__file__).resolve().parents[1]
import sys
sys.path.insert(0, str(ROOT / "tools"))
from game_source_context import legacy_core_source, current_source_path

PACKAGE = "com/gameloft/android/ANMP/GloftGF2F/S800x480"
RUNNER = r"""
import java.lang.reflect.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class BfTextComparison {
    static final String P = "com.gameloft.android.ANMP.GloftGF2F.S800x480.";
    static class Game {
        final Class<?> font, base, options, graphics, recorder;
        int successfulDrawCases, drawCalls, literalFiveCases, warningCalls, verticalCalls;
        Game(String directory) throws Exception {
            ClassLoader loader = new URLClassLoader(new URL[]{Paths.get(directory).toUri().toURL()}, null);
            font = Class.forName(P + "ProbeFont", true, loader);
            base = Class.forName(P + "a", true, loader);
            options = Class.forName(P + "w", true, loader);
            graphics = Class.forName(P + "aj", true, loader);
            recorder = Class.forName(P + "GLLib", true, loader);
        }
        void set(Class<?> type, Object object, String name, Object value) throws Exception {
            field(type, name).set(object, value);
        }
        Field field(Class<?> type, String name) throws Exception {
            for (Class<?> owner = type; owner != null; owner = owner.getSuperclass()) {
                try {Field field = owner.getDeclaredField(name); field.setAccessible(true); return field;}
                catch (NoSuchFieldException ignored) {}
            }
            throw new NoSuchFieldException(name);
        }
        String run(String text, boolean array, int flags, boolean vertical, int step,
                   int style, int mode, int anchor, int start, int end, int cap,
                   boolean[] controls, int failAt, boolean error) throws Exception {
            for (int i = 0; i < 5; i++) set(options, null,
                    new String[]{"XA", "XE", "XF", "Xq", "Zo"}[i], (flags & (1 << i)) != 0);
            Object instance = font.getConstructor().newInstance();
            set(base, instance, "IN", vertical); set(base, instance, "IS", step);
            set(base, instance, "IU", (style & 1) != 0); set(base, instance, "IV", (style & 2) != 0);
            set(base, instance, "IT", 9); set(base, instance, "Jc", (flags & 32) == 0 ? -1 : 384);
            set(base, null, "In", '~'); set(base, null, "Ip", '#'); set(base, null, "Io", (flags & 64) != 0);
            set(base, null, "Je", start); set(base, null, "Jf", end); set(base, null, "Jg", cap);
            set(base, null, "II", 11); set(base, null, "IJ", 17); set(base, null, "IK", 12);
            set(base, null, "IL", 6); set(base, null, "IM", -3);
            set(font, instance, "aiW", 3); set(font, instance, "ajd", mode & 3);
            set(font, instance, "aje", 2); set(font, instance, "ajf", -4);
            set(font, instance, "ajc", (mode & 16) == 0);
            if ((mode & 4) != 0) {
                Object peer = base.getConstructor().newInstance(); set(base, peer, "id", "peer");
                set(font, instance, "ajh", peer);
            }
            boolean[] outlines = (boolean[]) field(font, "aja").get(instance);
            boolean[] shadows = (boolean[]) field(font, "ajb").get(instance);
            outlines[3] = (mode & 8) != 0; shadows[3] = (mode & 32) != 0;
            List<String> calls = (List<String>) field(recorder, "calls").get(null);
            calls.clear(); set(recorder, null, "failAt", failAt);
            Throwable marker = error ? new AssertionError("injected render failure")
                                     : new IllegalStateException("injected render failure");
            set(recorder, null, "failure", marker);
            Method draw = font.getMethod("a", graphics, String.class, char[].class,
                    int.class, int.class, int.class, int.class, boolean.class, boolean.class, boolean.class);
            String failure = "";
            try {
                draw.invoke(instance, graphics.getConstructor().newInstance(), array ? null : text,
                        array && text != null ? text.toCharArray() : null, 73, 41, anchor, -1,
                        controls[0], controls[1], controls[2]);
            } catch (InvocationTargetException caught) {
                Throwable cause = caught.getCause();
                failure = cause.getClass().getName() + ":injected=" + (cause == marker);
            }
            boolean drew = false, literalFive = false;
            for (String call : calls) {
                if (call.startsWith("INT:")) throw new AssertionError("Integer draw overload selected: " + call);
                if (call.startsWith("float:")) {drew = true; drawCalls++;}
                if (call.startsWith("float:self:5:")) literalFive = true;
                if (call.startsWith("warning:")) warningCalls++;
                if (call.startsWith("vertical:")) verticalCalls++;
            }
            if (failure.isEmpty() && drew) successfulDrawCases++;
            if (failure.isEmpty() && literalFive) literalFiveCases++;
            return failure + "/" + calls + "/palette=" + field(font, "aiW").get(instance)
                    + "/mode=" + field(font, "ajd").get(instance)
                    + "/under=" + field(base, "IU").get(instance)
                    + "/bold=" + field(base, "IV").get(instance)
                    + "/saved=" + field(base, "II").get(null)
                    + "/measure=" + field(base, "IJ").get(null) + ":" + field(base, "IK").get(null);
        }
    }
    static int check(Game before, Game after, String text, boolean array, int flags,
                     boolean vertical, int step, int style, int mode, int anchor,
                     int start, int end, int cap, boolean[] controls, int failAt, boolean error) throws Exception {
        String a = before.run(text, array, flags, vertical, step, style, mode, anchor, start, end, cap, controls, failAt, error);
        String b = after.run(text, array, flags, vertical, step, style, mode, anchor, start, end, cap, controls, failAt, error);
        if (!a.equals(b)) throw new AssertionError("text=" + text + " array=" + array + " flags=" + flags
                + " IN=" + vertical + " IS=" + step + " style=" + style + " mode=" + mode + " anchor=" + anchor
                + " range=" + start + ":" + end + ":" + cap + " failure=" + failAt
                + "\noriginal=" + a + "\nrestored=" + b);
        return 1;
    }
    public static void main(String[] args) throws Exception {
        Game original = new Game(args[0]), restored = new Game(args[1]);
        int count = 0;
        String[] targeted = {"AB", "a\u0002\u0005B", "A\u0002\u00ffB", "A \nB", "\\_A\\^B\\_C\\^D"};
        for (int flags = 0; flags < 8; flags++) for (boolean vertical : new boolean[]{false, true})
            for (int step : new int[]{-4, 0, 5}) for (String text : targeted)
                for (boolean array : new boolean[]{false, true}) for (int style = 0; style < 4; style++)
                    count += check(original, restored, text, array, flags | 8, vertical, step, style,
                            0, 0, -1, -1, -1, new boolean[]{true, false, false}, -1, false);
        for (Game game : new Game[]{original, restored}) {
            if (game.successfulDrawCases != 1920 || game.drawCalls <= 1920
                    || game.literalFiveCases < 384 || game.warningCalls == 0 || game.verticalCalls == 0)
                throw new AssertionError("Targeted setup did not exercise successful glyph rendering: "
                        + game.successfulDrawCases + "/" + game.drawCalls + "/" + game.literalFiveCases
                        + "/" + game.warningCalls + "/" + game.verticalCalls);
        }
        Random random = new Random(44220);
        String[] edges = {null, "", "A", "AB", "A B", "\n", "A\nB", "\u0002\u0005A", "\u0002\u00ffB",
                "\u0002\u0000X", "\u0001\u0003A\u0001\u00ffB", "A\\_B\\_C", "A\\^B\\^C", "A\\3B",
                "A\\", "A\u0001", "A\u0002", "~A#B", "\u0003A"};
        String alphabet = "AB ab\n~#\\_^ÿ";
        for (int trial = 0; trial < 3000; trial++) {
            String text;
            if (trial % 3 == 0) text = edges[random.nextInt(edges.length)];
            else {
                StringBuilder value = new StringBuilder();
                for (int i = 0, length = random.nextInt(15); i < length; i++)
                    value.append(alphabet.charAt(random.nextInt(alphabet.length())));
                text = value.toString();
            }
            int start = random.nextInt(5) - 2, end = random.nextBoolean() ? -1 : random.nextInt(17);
            count += check(original, restored, text, random.nextBoolean(), random.nextInt(128),
                    random.nextBoolean(), random.nextInt(11) - 5, random.nextInt(4), random.nextInt(64),
                    new int[]{0, 1, 2, 8, 32, 43}[random.nextInt(6)], start, end,
                    random.nextBoolean() ? -1 : random.nextInt(17),
                    new boolean[]{random.nextBoolean(), random.nextBoolean(), random.nextBoolean()},
                    trial % 5 == 0 ? random.nextInt(15) : -1, random.nextBoolean());
        }
        System.out.println("PASS: " + count + " original/recovered text-render call traces, state and failure outcomes match; 1920 targeted positive-draw cases per implementation");
    }
}
"""


class BfTextRendererTest(unittest.TestCase):
    def test_glyph_flags_literal_escapes_styles_recursion_and_errors(self):
        jar = Path(os.environ.get("GREENFARM_ORIGINAL_JAR", ROOT / ".local/bytecode-tools/greenfarm-original.jar"))
        home = Path(os.environ.get("JAVA_HOME", "/workspace/tooling/jdk-21.0.9+10"))
        java, javac = home / "bin/java", home / "bin/javac"
        if not jar.exists() or not java.is_file() or not javac.is_file():
            self.skipTest("Requires verified original dex2jar artifact and JDK")
        with tempfile.TemporaryDirectory(prefix="bf-text-differential-") as temp:
            temporary = Path(temp)
            source = temporary / "source" / PACKAGE
            source.mkdir(parents=True)
            (source / 'bf.java').write_text(legacy_core_source('bf'))
            prefix = "package com.gameloft.android.ANMP.GloftGF2F.S800x480;\n"
            (source / "aj.java").write_text(prefix + "public class aj {}")
            (source / "w.java").write_text(prefix + "public class w {public static boolean XA,XE,XF,Xq,Zo;}")
            (source / "GLLib.java").write_text(prefix + """public class GLLib {
                public static java.util.List<String> calls=new java.util.ArrayList<>();
                public static int failAt=-1;public static Throwable failure;
                public static void add(String call){calls.add(call);if(calls.size()-1==failAt){
                    if(failure instanceof Error)throw (Error)failure;throw (RuntimeException)failure;}}
                public static void be(String text){add("warning:"+text);}
                public static void m2do(int mode){add("blend:"+mode);}
                public static void g(int r,int g,int b,int a){add("color:"+r+":"+g+":"+b+":"+a);}
            }""")
            (source / "a.java").write_text(prefix + """public class a {
                public short[] GM={3};public boolean IN,IU,IV;public int IS,IT,Jc=-1;
                public static int II,IJ,IK,IL,IM,Je=-1,Jf=-1,Jg=-1;
                public static char In,Ip;public static boolean Io;public String id="self";
                public void bQ(int value){}public int bS(int value){return value;}public void hs(){}
                public void a(aj g,String s,char[] c,int x,int y,int anchor,int unused,boolean save,boolean measure,boolean nested){}
                protected char a(String text,char[] chars,int index){GLLib.add("read:"+index);return text!=null?text.charAt(index):chars[index];}
                public void a(String text,char[] chars){GLLib.add("measure:"+(text!=null?text:java.util.Arrays.toString(chars)));IJ=19;IK=13;IL=7;IM=-5;}
                public int bw(int index){GLLib.add("bw:"+index);return index*2+1;}
                public int bN(int character){GLLib.add("map:"+character);return (character*7)%29;}
                public int hv(){GLLib.add("limit");return 12;}
                protected int bP(int glyph){GLLib.add("width:"+glyph);return 4+glyph%5;}
                protected int bO(int glyph){GLLib.add("vertical:"+glyph);return glyph%7-3;}
                // Actual final hA/hG are pure field/scale getters; preserve that contract.
                public int hA(){return 11;}public int hG(){return 2;}
                public int hF(){GLLib.add("spacing");return 1;}public int hD(){GLLib.add("space");return 6;}
                protected int hH(){GLLib.add("slant");return -3;}
                public void a(aj g,int glyph,float x,float y,int flags){GLLib.add("float:"+id+":"+glyph+":"+x+":"+y+":"+flags);}
                public void a(aj g,int glyph,int x,int y,int flags){GLLib.add("INT:"+id+":"+glyph+":"+x+":"+y+":"+flags);}
            }""")
            (source / "ProbeFont.java").write_text(prefix + """public class ProbeFont extends bf {
                public ProbeFont(){super(32);}
                public void nt(){GLLib.add("begin:"+aiW+":"+ajd);}
                public void nu(){GLLib.add("end:"+aiW+":"+ajd);}
                public void bQ(int value){GLLib.add("palette:"+value);super.bQ(value);}
            }""")
            restored = temporary / "restored"
            restored.mkdir()
            result = subprocess.run([str(javac), "-source", "8", "-target", "8", "-d", str(restored), *map(str, source.glob("*.java"))], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            original = temporary / "original"
            shutil.copytree(restored, original)
            with zipfile.ZipFile(jar) as archive:
                # Keep the entire original class, including its Code and constant pool.
                (original / PACKAGE / "bf.class").write_bytes(archive.read(PACKAGE + "/bf.class"))
            runner = temporary / "BfTextComparison.java"
            runner.write_text(RUNNER)
            result = subprocess.run([str(javac), str(runner)], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            result = subprocess.run([str(java), "-cp", str(temporary), "BfTextComparison", str(original), str(restored)], capture_output=True, text=True, timeout=60)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn("PASS: 4920", result.stdout)
            self.assertIn("1920 targeted positive-draw cases per implementation", result.stdout)

            # Reintroduce each reviewed defect independently in temporary source.
            # Every mutation must fail on differing original/current traces, not
            # compilation, linkage, a setup exception or the positive-draw gate.
            current = (source / "bf.java").read_text()
            mutations = {
                "XA": ("if (!w.XA || iA <= hv())", "if (w.XA || iA <= hv())"),
                "XE": ("if (w.XE && this.IN) {\n                                int iBO = i14 + bO(i8);",
                       "if (!w.XE && this.IN) {\n                                int iBO = i14 + bO(i8);"),
                "XF": ("} else if (!w.XF || this.IS == 0) {\n                                i12 = i5;",
                       "} else if (w.XF || this.IS == 0) {\n                                i12 = i5;"),
                "char2_range": ("if (!w.XA || iA <= hv())", "if (!w.XA || cA == 2 || iA <= hv())"),
            }
            for label, (before, after) in mutations.items():
                with self.subTest(mutation=label):
                    self.assertEqual(current.count(before), 1, "Mutation must target exactly one recovered branch: " + label)
                    (source / "bf.java").write_text(current.replace(before, after, 1))
                    mutant = temporary / ("mutant-" + label)
                    shutil.copytree(restored, mutant)
                    result = subprocess.run([str(javac), "-source", "8", "-target", "8", "-sourcepath", "", "-cp", str(mutant), "-d", str(mutant), str(source / "bf.java")], capture_output=True, text=True)
                    self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
                    result = subprocess.run([str(java), "-cp", str(temporary), "BfTextComparison", str(original), str(mutant)], capture_output=True, text=True, timeout=60)
                    self.assertNotEqual(result.returncode, 0, "Mutation escaped the original-bytecode comparison: " + label)
                    self.assertIn("\noriginal=", result.stderr)
                    self.assertIn("\nrestored=", result.stderr)


if __name__ == "__main__":
    unittest.main()
