"""Exercise the production restore binding decision across fresh JVM processes.

Only restoreGlobalGameplay's prefix before gameplay-field decode is selected;
ax's complete production error wrapper executes around it. The real
byte/string codec and Kotlin globalLoadFinished guard execute from source. Storage
and SDK boundaries are peers. This isolates the cold-start identity regression;
it does not claim to restore a whole farm or replace real SQLite/device tests.
Unrelated legacy-media reads fail; the gameplay record must be read exactly once
(or zero times when an explicit cloud buffer supplies the data).
"""
from pathlib import Path
import hashlib
import struct
import subprocess
import sys
import tempfile
import unittest
import uuid

from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath

PACKAGE = 'com.quanhoang.greenfarm3.S800x480'
JAVA = ROOT / 'app/src/main/java' / PACKAGE.replace('.', '/')
KOTLIN = ROOT / 'app/src/main/kotlin' / PACKAGE.replace('.', '/')

SHELL = r'''package com.quanhoang.greenfarm3.S800x480;
import java.nio.file.*;
import java.util.*;

public class RestorePrefixChecks {
 public static void main(String[] args)throws Exception {
  String scenario=args[0];
  Path file=Paths.get(args[1]);
  byte[] before=Files.exists(file)?Files.readAllBytes(file):null;
  GameRuntime.record=before;
  com.gameloft.android.wrapper.au.identity=args[2].equals("<null>")?null:args[2];
  System.setProperty("HDIDFV",args[2]);
  LocalDevelopmentMode.enabled=!scenario.startsWith("normal");
  boolean cloud=scenario.startsWith("cloud");
  FarmGameEngine.aCw=cloud?before:null;
  boolean localPrivate=!cloud;
  com.gameloft.android.wrapper.au.forbidden=localPrivate;
  // A previous import's local alias must never authorize a cloud buffer.
  com.quanhoang.greenfarm3.migration.SaveTransferIdentity.acceptImported=scenario.equals("cloud-imported");
  FarmGameEngine.asP=scenario.equals("cloud-asP");
  boolean originalAsP=FarmGameEngine.asP;
  boolean originalAsM=FarmGameEngine.asM,originalAsO=FarmGameEngine.asO;
  int originalAsL=FarmGameEngine.asL;
  FarmGameEngine.aPg=FarmGameEngine.aOM=true;
  String expectedCode=scenario.startsWith("cloud")&&!scenario.equals("cloud-match")
    ?"IDENTITY_MISMATCH":scenario.startsWith("unsupported")?"SAVE_VERSION_UNSUPPORTED"
    :scenario.startsWith("corrupt")?"SAVE_DECODE_FAILED":null;
  try {
   boolean loaded=FarmGameEngine.ax(false);
   if(expectedCode!=null)throw new AssertionError("invalid record accepted: "+scenario);
   if(scenario.equals("missing")) {
    check(!loaded,"missing record reported loaded");
    ProgressSaveController.globalLoadFinished(false,loaded);
   } else {
    ProgressSaveController.globalLoadFinished(true,loaded);
    check(loaded&&ProgressSaveController.globalLoaded,"local progress rejected after cold restart");
    check(!FarmGameEngine.asq,"new player was requested for an existing record");
   }
  } catch(SaveRestoreException expected) {
   if(expectedCode==null)throw expected;
   check(expected.getCode().name().equals(expectedCode),"wrong diagnostic: "+expected.getCode());
   if(expectedCode.equals("SAVE_DECODE_FAILED"))
    check(expected.getCause() instanceof IndexOutOfBoundsException
      ||expected.getCause() instanceof NegativeArraySizeException,"original decode cause lost");
   check(!ProgressSaveController.globalLoaded,"failed restore opened load gate");
   check(!FarmGameEngine.asq,"failed existing restore requested a default farm");
  }
  check(FarmGameEngine.aPg&&FarmGameEngine.aOM,"restore changed player/account flags");
  check(FarmGameEngine.asP==originalAsP,"restore changed cloud consent flag");
  if(scenario.startsWith("cloud-legacy"))check(FarmGameEngine.asM==originalAsM
    &&FarmGameEngine.asO==originalAsO&&FarmGameEngine.asL==originalAsL,
    "unverifiable legacy buffer changed engine version flags");
  check(FarmGameEngine.headerWrites==0,"restore attempted to overwrite the original save");
  check(com.quanhoang.greenfarm3.migration.SaveTransferIdentity.calls==0,"imported-local alias consulted during restore");
  if(localPrivate)check(com.gameloft.android.wrapper.au.calls==0,"private restore called unavailable SDK");
  else if(scenario.startsWith("cloud-legacy"))
   check(com.gameloft.android.wrapper.au.calls==0,"legacy buffer reached SDK before rejection");
  else if(expectedCode==null||expectedCode.equals("IDENTITY_MISMATCH"))
   check(com.gameloft.android.wrapper.au.calls==1,"buffer restore did not verify SDK binding exactly once");
  check(GameRuntime.reads==(cloud?0:1),"restore performed duplicate or unrelated storage reads: "+GameRuntime.reads);
  byte[] after=Files.exists(file)?Files.readAllBytes(file):null;
  check(Arrays.equals(before,after),"saved bytes changed during restore");
  check((before!=null)==Files.exists(file),"load created or removed data");
  System.out.println("PASS "+scenario+" original bytes retained");
 }
 static void check(boolean passed,String message){if(!passed)throw new AssertionError(message);}
}
class Log {static int i(String tag,String message){return 0;}}
class LocalDevelopmentMode {static boolean enabled;static boolean isEnabled(){return enabled;}}
class GameRuntime {
 static byte[] record;static int reads;
 static byte[] B(String name,String key){
  if(!name.equals("save_gameplay")){
   throw new IllegalStateException("Unrelated legacy media unavailable: "+name);
  }
  reads++;return record;
 }
 %CODEC%
}
class FarmGameEngine extends GameRuntime {
 static boolean asM,asO,asP,asq,aPg,aOM;
 static byte[]aCw;static byte aCB=4;static int asL=406,headerWrites;
 static void ro(){headerWrites++;}
 %IDENTITY%
 %STRING_CODEC%
 %WRAPPER%
 %RESTORE_PREFIX%
}
'''


class LocalProgressRestoreTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        try:
            cls.java, cls.javac = jdk_tool('java'), jdk_tool('javac')
        except ValueError as error:
            raise unittest.SkipTest(str(error))
        cls.temporary = tempfile.TemporaryDirectory(prefix='greenfarm-local-progress-')
        cls.addClassCleanup(cls.temporary.cleanup)
        cls.directory = Path(cls.temporary.name)
        engine = (JAVA / 'FarmGameEngine.java').read_text()
        runtime = (JAVA / 'GameRuntime.java').read_text()
        wrapper = extract_method(engine, 'static boolean ax(boolean z)')
        restore = extract_method(engine, 'static boolean restoreGlobalGameplay(boolean z)')
        # Stop just after the binding decision, before unrelated mutable world
        # fields. The prefix itself is neither reconstructed nor simplified.
        restore = restore[:restore.index('        int i8 = iL2;')] + '        return true;\n    }'
        controller = (KOTLIN / 'ProgressSaveController.kt').read_text()
        guard = extract_method(controller, 'fun globalLoadFinished(existed: Boolean, loaded: Boolean)')
        codecs = ('public static byte j(byte[] bArr, int i)',
                  'public static int l(byte[] bArr, int i)',
                  'public static void a(Object obj, int i, Object obj2, int i2, int i3)',
                  'public static int b(byte[] bArr, int i, byte[] bArr2)')
        fixture = (SHELL.replace('%CODEC%', '\n'.join(extract_method(runtime, sig) for sig in codecs))
                   .replace('%IDENTITY%', '\n'.join(extract_method(engine, sig) for sig in
                            ('static String rl()', 'static String rv()')))
                   .replace('%STRING_CODEC%', extract_method(engine, 'static String o(byte[] bArr, int i)'))
                   .replace('%WRAPPER%', wrapper)
                   .replace('%RESTORE_PREFIX%', restore))
        sources = cls.directory / 'sources'
        target = sources / PACKAGE.replace('.', '/') / 'RestorePrefixChecks.java'
        target.parent.mkdir(parents=True)
        target.write_text(fixture)
        sdk = sources / 'com/gameloft/android/wrapper/au.java'
        sdk.parent.mkdir(parents=True)
        sdk.write_text('''package com.gameloft.android.wrapper;
public class au {
 public static boolean forbidden;public static String identity;public static int calls;
 public static String Os(){calls++;if(forbidden)throw new AssertionError("SDK unavailable offline");return identity;}
}''')
        identity = sources / 'com/quanhoang/greenfarm3/migration/SaveTransferIdentity.java'
        identity.parent.mkdir(parents=True)
        identity.write_text('''package com.quanhoang.greenfarm3.migration;
public class SaveTransferIdentity {
 public static int calls;public static boolean acceptImported;
 public static boolean matches(String saved,String current){calls++;return acceptImported||saved.equals(current);}
}''')
        diagnostic = sources / PACKAGE.replace('.', '/') / 'SaveRestoreException.java'
        diagnostic.write_text((JAVA / 'SaveRestoreException.java').read_text())
        cls.classes = cls.directory / 'classes'
        controller_source = cls.directory / 'ProgressSaveController.kt'
        controller_source.write_text('package ' + PACKAGE + '\nobject ProgressSaveController {\n'
                                     '@JvmField var globalLoaded = false\n@JvmStatic\n' + guard + '\n}')
        compile_mixed([controller_source], list(sources.rglob('*.java')), cls.classes)

    def run_scenario(self, scenario, file, identity=None):
        result = subprocess.run([str(self.java), '-cp', runtime_classpath(self.classes), PACKAGE + '.RestorePrefixChecks',
                                 scenario, str(file), str(uuid.uuid4()) if identity is None else identity],
                                capture_output=True, text=True, timeout=30)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertIn('PASS ' + scenario, result.stdout)

    def saved_record(self, filename):
        # Original format-4 prefix plus opaque progress bytes. Entire farm
        # decode and SQLite checksums are covered by the other host suites.
        binding = str(uuid.uuid4()).encode('utf-8')
        payload = b'\x04' + struct.pack('<I', len(binding)) + binding + bytes(range(256))
        file = self.directory / filename
        file.write_bytes(payload)
        return file

    def test_private_save_survives_twenty_cold_jvms_with_uuid_changes_for_each_build_mode(self):
        # A/B boundary coverage only: each invocation creates a fresh JVM and
        # receives a new UUID. It does not claim Android or full-farm restore.
        for mode in ('local', 'normal'):
            file = self.saved_record(mode + '-cold-save.bin')
            digest = hashlib.sha256(file.read_bytes()).digest()
            for attempt in range(20):
                with self.subTest(mode=mode, cold_start=attempt + 1):
                    self.run_scenario(mode, file)
                    self.assertEqual(hashlib.sha256(file.read_bytes()).digest(), digest)

    def test_missing_and_malformed_progress_do_not_write_or_reset(self):
        self.run_scenario('missing', self.directory / 'missing.bin')
        cases = {
            'empty': b'',
            'length-prefix': b'\x04\x24',
            'truncated-binding': b'\x04' + struct.pack('<I', 36) + b'abc',
            'negative-length': b'\x04' + struct.pack('<i', -1),
        }
        for name, payload in cases.items():
            with self.subTest(malformed=name):
                file = self.directory / (name + '.bin')
                file.write_bytes(payload)
                self.run_scenario('corrupt-' + name, file)

    def test_unsupported_versions_are_rejected_before_restore(self):
        file = self.saved_record('unsupported.bin')
        original = file.read_bytes()
        for version in (5, 127, 128, 255):
            with self.subTest(version=version):
                file.write_bytes(bytes([version]) + original[1:])
                self.run_scenario('unsupported-' + str(version), file)

    def test_cloud_mismatch_cannot_use_length_consent_or_imported_alias_bypasses(self):
        file = self.saved_record('bound-save.bin')
        self.run_scenario('cloud', file)
        self.run_scenario('cloud-asP', file)
        self.run_scenario('cloud-imported', file)
        self.run_scenario('cloud-short-identity', file, 'different-length')
        self.run_scenario('cloud-null-identity', file, '<null>')

    def test_unbound_legacy_cloud_buffers_are_rejected_before_engine_mutation(self):
        # Versions 0/1/2 have no binding field on their old decode branch.
        file = self.saved_record('legacy-cloud.bin')
        original = file.read_bytes()
        for version in (0, 1, 2):
            with self.subTest(version=version):
                file.write_bytes(bytes([version]) + original[1:])
                self.run_scenario('cloud-legacy-' + str(version), file)

    def test_empty_cloud_bindings_cannot_authorize_a_buffer(self):
        file = self.directory / 'empty-binding.bin'
        file.write_bytes(b'\x04' + struct.pack('<I', 0) + bytes(range(256)))
        self.run_scenario('cloud-empty-saved-binding', file)
        self.run_scenario('cloud-empty-saved-and-current-binding', file, '')

    def test_matching_cloud_binding_is_still_accepted(self):
        file = self.saved_record('same-bound-save.bin')
        payload = file.read_bytes()
        binding = payload[5:5 + struct.unpack('<I', payload[1:5])[0]].decode('utf-8')
        self.run_scenario('cloud-match', file, binding)


class LegacyIdentityDiscoveryTest(unittest.TestCase):
    def test_legacy_sdk_does_not_persist_identity_to_the_renamed_own_provider(self):
        """Explain the cold UUID change using actual legacy discovery source.

        Only Android API peers and the persistence-thread boundary are fixtures;
        the production authority filter, in-process map and UUID generator run.
        This documents why local progress must not depend on SDK identity.
        """
        java, javac = jdk_tool('java'), jdk_tool('javac')
        with tempfile.TemporaryDirectory(prefix='greenfarm-identity-discovery-') as temporary:
            root = Path(temporary)
            sources = root / 'source'
            peers = {
                'android/content/pm/ProviderInfo.java':
                    'package android.content.pm;public class ProviderInfo {public String authority;}',
                'android/content/pm/PackageManager.java': '''package android.content.pm;
public class PackageManager {
 public java.util.List<ProviderInfo> queryContentProviders(String process,int uid,int flags){
  ProviderInfo own=new ProviderInfo();own.authority="com.quanhoang.greenfarm3.KeyProvider";
  return java.util.Collections.singletonList(own);
 }
}''',
                'android/content/Context.java': '''package android.content;
public class Context {
 public android.content.pm.PackageManager getPackageManager(){return new android.content.pm.PackageManager();}
 public String getPackageName(){return "com.quanhoang.greenfarm3";}
 public ContentResolver getContentResolver(){return new ContentResolver();}
}''',
                'android/content/ContentResolver.java': '''package android.content;
public class ContentResolver {
 public static int queries;
 public android.database.Cursor query(android.net.Uri uri,String[]p,String s,String[]a,String sort){queries++;return null;}
 public int update(android.net.Uri u,ContentValues v,String s,String[]a){return 0;}
 public Object insert(android.net.Uri u,ContentValues v){return null;}
}''',
                'android/content/ContentValues.java':
                    'package android.content;public class ContentValues {public void put(String k,String v){}}',
                'android/database/Cursor.java': '''package android.database;
public interface Cursor {int getColumnCount();int getCount();boolean moveToFirst();String getString(int i);boolean isLast();boolean moveToNext();void close();}''',
                'android/net/Uri.java': 'package android.net;public class Uri {public static Uri parse(String value){return new Uri();}}',
                'android/text/TextUtils.java': 'package android.text;public class TextUtils {public static boolean isEmpty(String s){return s==null||s.isEmpty();}}',
                'com/google/firebase/analytics/FirebaseAnalytics.java':
                    'package com.google.firebase.analytics;public class FirebaseAnalytics {public static class Param {public static String VALUE="value";}}',
                'com/gameloft/android/wrapper/au.java': '''package com.gameloft.android.wrapper;
public class au {public static android.content.Context getContext(){return new android.content.Context();}}''',
                'com/gameloft/android/wrapper/f.java':
                    'package com.gameloft.android.wrapper;class f extends Thread {f(String k,String v){}}',
                'com/gameloft/android/wrapper/g.java':
                    'package com.gameloft.android.wrapper;class g extends Thread {g(String k,String v){}}',
                'com/gameloft/android/wrapper/IdentityDiscoveryCheck.java': '''package com.gameloft.android.wrapper;
public class IdentityDiscoveryCheck {
 public static void main(String[]args)throws Exception {
  e.init(au.getContext());
  java.lang.reflect.Field field=e.class.getDeclaredField("bfo");field.setAccessible(true);
  if(!((java.util.List<?>)field.get(null)).isEmpty())throw new AssertionError("fixture own provider was discovered");
  if(android.content.ContentResolver.queries!=0)throw new AssertionError("own provider was queried");
  String fresh=com.gameloft.android.hdidfv.HDIDFV.Os();
  if(!e.ec("HDIDFV").equals(""))throw new AssertionError("new process already had identity");
  e.S("HDIDFV",fresh);
  if(!e.ec("HDIDFV").equals(fresh))throw new AssertionError("process cache failed");
  System.out.println("UUID="+fresh);
 }
}''',
            }
            for name, text in peers.items():
                file = sources / name
                file.parent.mkdir(parents=True, exist_ok=True)
                file.write_text(text)
            for name in ('com/gameloft/android/wrapper/e.java', 'com/gameloft/android/hdidfv/HDIDFV.java'):
                file = sources / name
                file.parent.mkdir(parents=True, exist_ok=True)
                file.write_text((ROOT / 'app/src/main/java' / name).read_text())
            classes = root / 'classes'
            built = subprocess.run([str(javac), '-d', str(classes), *map(str, sources.rglob('*.java'))],
                                   capture_output=True, text=True, timeout=60)
            self.assertEqual(built.returncode, 0, built.stdout + built.stderr)
            identities = set()
            for unused in range(3):
                result = subprocess.run([str(java), '-cp', str(classes), 'com.gameloft.android.wrapper.IdentityDiscoveryCheck'],
                                        capture_output=True, text=True, timeout=30)
                self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
                identities.add(uuid.UUID(result.stdout.strip().removeprefix('UUID=')))
            self.assertEqual(len(identities), 3, 'cold processes unexpectedly reused process-only UUID')


if __name__ == '__main__':
    unittest.main()
