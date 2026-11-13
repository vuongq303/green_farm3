"""Compare restored pathfinding with original DEX-converted class bytecode."""

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

PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'
RUNNER = r'''
import java.lang.reflect.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
public class PathFindingComparison {
    static final String P = "com.gameloft.android.ANMP.GloftGF2F.S800x480.";
    static Class<?> load(String path) throws Exception {
        URLClassLoader loader = new URLClassLoader(new URL[]{Paths.get(path).toUri().toURL()}, null);
        return Class.forName(P + "z", true, loader);
    }
    static Object field(Class<?> type, Object object, String name) throws Exception {
        Field field = type.getDeclaredField(name); field.setAccessible(true); return field.get(object);
    }
    static String run(Class<?> type, int width, int height, byte[] map, int dirs,
                      int penalty, boolean custom, boolean debug, boolean directional, int[] input) throws Exception {
        Class<?> options = Class.forName(P + "w", true, type.getClassLoader());
        options.getField("YW").set(null, custom); options.getField("YV").set(null, debug);
        Object finder = type.getConstructor().newInstance();
        type.getMethod("a", int.class,int.class,byte[].class,int.class,int.class,int.class,int.class,int.class)
            .invoke(finder, width, height, map.clone(), 10, 14, penalty, dirs, directional ? 255 : 1);
        Method find = type.getMethod("a",int.class,int.class,int.class,int.class,int.class,int.class,
                                    int.class,int.class,int.class,boolean.class);
        String error = "";
        try { find.invoke(finder,input[0],input[1],input[2],input[3],input[4],input[5],input[6],input[7],input[8],directional); }
        catch (InvocationTargetException caught) { error = caught.getCause().getClass().getName(); }
        StringBuilder result = new StringBuilder(error);
        for (String name : new String[]{"aaI","aaJ"}) result.append('/').append(field(type,finder,name));
        for (String name : new String[]{"aau","aav","aaw","aax","aay","aaH"})
            result.append('/').append(Arrays.toString((short[]) field(type,finder,name)));
        return result.toString();
    }
    public static void main(String[] args) throws Exception {
        Class<?> original=load(args[0]), restored=load(args[1]);
        Random random=new Random(44220);
        for (int trial=0;trial<2000;trial++) {
            int width=3+random.nextInt(7),height=3+random.nextInt(7),dirs=random.nextBoolean()?4:8;
            boolean directional=random.nextBoolean(), custom=random.nextBoolean(), debug=random.nextBoolean();
            byte[] map=new byte[width*height];
            for(int i=0;i<map.length;i++) map[i]=(byte)(directional?random.nextInt(256):(random.nextInt(4)==0?1:0));
            int sx=random.nextInt(width-1),sy=random.nextInt(height-1);
            int sw=2+random.nextInt(width-sx-1),sh=2+random.nextInt(height-sy-1);
            int[] input={sx+random.nextInt(sw),sy+random.nextInt(sh),random.nextInt(dirs+1)-1,
                         sx+random.nextInt(sw),sy+random.nextInt(sh),sx,sy,sw,sh};
            int penalty=random.nextInt(8);
            Object before=run(original,width,height,map,dirs,penalty,custom,debug,directional,input);
            Object after=run(restored,width,height,map,dirs,penalty,custom,debug,directional,input);
            if(!before.equals(after))throw new AssertionError("trial="+trial+" dirs="+dirs+" custom="+custom+
                " directional="+directional+" input="+Arrays.toString(input)+"\noriginal="+before+"\nrestored="+after);
        }
        System.out.println("PASS: 2000 original/recovered pathfinding states, paths and exception outcomes match");
    }
}
'''


class PathFindingTest(unittest.TestCase):
    def test_states_and_paths_match_original_bytecode(self):
        jar = Path(os.environ.get('GREENFARM_ORIGINAL_JAR', ROOT / '.local/bytecode-tools/greenfarm-original.jar'))
        java_home = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-21.0.9+10'))
        java, javac = java_home / 'bin/java', java_home / 'bin/javac'
        if not jar.exists() or not java.is_file() or not javac.is_file():
            self.skipTest('Requires verified original dex2jar artifact and JDK')
        with tempfile.TemporaryDirectory(prefix='z-pathfinding-differential-') as temp:
            temporary = Path(temp)
            source = temporary / 'source' / PACKAGE; source.mkdir(parents=True)
            (source / 'z.java').write_text(legacy_core_source('z'))
            prefix = 'package com.gameloft.android.ANMP.GloftGF2F.S800x480;\n'
            (source / 'w.java').write_text(prefix + 'public class w {public static int YU=256; public static boolean YV,YW;}')
            (source / 'GLLib.java').write_text(prefix + '''public class GLLib {
                public static void c(boolean okay,String message){if(!okay)throw new AssertionError(message);}
                public static void be(String message){}
            }''')
            constants = temporary / 'source/com/facebook/appevents/codeless/internal/Constants.java'
            constants.parent.mkdir(parents=True)
            constants.write_text('package com.facebook.appevents.codeless.internal; public class Constants {'
                                 'public static final int APP_INDEXING_SCHEDULE_INTERVAL_MS=1000;}')
            restored = temporary / 'restored'; restored.mkdir()
            result = subprocess.run([str(javac), '-source','8','-target','8','-d',str(restored),
                                     *map(str,(temporary / 'source').rglob('*.java'))], capture_output=True, text=True)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            original = temporary / 'original'; shutil.copytree(restored,original)
            with zipfile.ZipFile(jar) as archive:
                (original / PACKAGE / 'z.class').write_bytes(archive.read(PACKAGE + '/z.class'))
            runner = temporary / 'PathFindingComparison.java'; runner.write_text(RUNNER)
            subprocess.run([str(javac),str(runner)],check=True,capture_output=True,text=True)
            result = subprocess.run([str(java),'-cp',str(temporary),'PathFindingComparison',str(original),str(restored)],
                                    capture_output=True,text=True,timeout=60)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            self.assertIn('PASS: 2000',result.stdout)


if __name__ == '__main__':
    unittest.main()
