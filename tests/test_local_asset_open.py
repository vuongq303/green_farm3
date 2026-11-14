"""Pack opens use packaged paths even with a stale wrapper resolution prefix."""
from pathlib import Path
import subprocess,sys,tempfile,unittest,zipfile
from test_aa_tile_renderer import extract_method
ROOT=Path(__file__).resolve().parents[1]
sys.path.insert(0,str(ROOT/'tools'))
from android_build_tools import jdk_tool

class LocalAssetOpenTest(unittest.TestCase):
    def test_application_assets_ignore_old_resolution_prefix_on_reopen(self):
        src=(ROOT/'app/src/main/java/com/quanhoang/greenfarm3/S800x480/GameRuntime.java').read_text()
        methods='\n'.join(extract_method(src,s) for s in ['public static final class ResourceFailure','public static final InputStream a(Class cls, String str)'])
        peers={
          'android/content/Context.java':'''package android.content; public class Context {
 public Context getApplicationContext(){return this;}public android.content.res.AssetManager getAssets(){return new android.content.res.AssetManager();}}''',
          'android/content/res/AssetManager.java':'''package android.content.res;public class AssetManager {
 public java.io.InputStream open(String s)throws java.io.IOException{return java.nio.file.Files.newInputStream(java.nio.file.Paths.get(System.getProperty("assets"),s));}}''',
          'com/gameloft/android/wrapper/au.java':'''package com.gameloft.android.wrapper;public class au {
 public static String bhM="stale/480x320";public static android.content.Context getContext(){return new android.content.Context();}}''',
          'Probe.java':'''import java.io.*;class Log{static void e(String a,String b,Throwable c){}}
class GameRuntime{METHODS}
public class Probe{public static void main(String[]args)throws Exception{
 for(int reopen=0;reopen<3;reopen++)for(String name:new String[]{"/0","/9","/11.1","/VI","/EN"}){
  try(InputStream s=GameRuntime.a(Probe.class,name)){if(s.read()<0)throw new AssertionError("empty packaged resource");}}
 try{GameRuntime.a(Probe.class,"/absent");throw new AssertionError("missing asset accepted");}
 catch(GameRuntime.ResourceFailure e){if(!e.resourcePath.equals("assets/800x480/absent"))throw new AssertionError(e.getMessage());}
}}'''.replace('METHODS',methods)}
        with tempfile.TemporaryDirectory() as d:
            d=Path(d);assets=d/'assets'
            with zipfile.ZipFile(ROOT/'green-farm-3.apk') as z:
                for name in z.namelist():
                    if name.startswith('assets/') and not name.endswith('/'):
                        out=d/name;out.parent.mkdir(parents=True,exist_ok=True);out.write_bytes(z.read(name))
            files=[]
            for name,text in peers.items():
                p=d/name;p.parent.mkdir(parents=True,exist_ok=True);p.write_text(text);files.append(str(p))
            r=subprocess.run([str(jdk_tool('javac')),'-d',str(d),*files],capture_output=True,text=True)
            self.assertEqual(r.returncode,0,r.stdout+r.stderr)
            r=subprocess.run([str(jdk_tool('java')),'-Dassets='+str(assets),'-cp',str(d),'Probe'],capture_output=True,text=True,timeout=10)
            self.assertEqual(r.returncode,0,r.stdout+r.stderr)
