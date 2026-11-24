"""Exercise encrypted auth records across separate JVMs with file-backed preferences.

AES/GCM and production SecureAccountStore are real. Android preferences/Keystore
are host peers; this does not establish device Keystore or Android lifecycle health.
The isolated farm sentinel must remain byte-for-byte unchanged in every scenario.
"""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath

JAVA = {
    'android/content/Context.java': r'''package android.content;
public class Context {
 public static final int MODE_PRIVATE=0; private final java.io.File root;
 public Context(java.io.File root){this.root=root;}
 public Context getApplicationContext(){return this;}
 public String getPackageName(){return "com.quanhoang.greenfarm3";}
 public SharedPreferences getSharedPreferences(String name,int mode){return new SharedPreferences(new java.io.File(root,name+".properties"));}
}''',
    'android/content/SharedPreferences.java': r'''package android.content;
import java.nio.file.*;import java.util.*;import java.io.*;
public class SharedPreferences {
 private final File file;public SharedPreferences(File f){file=f;}
 private Properties load(){try{Properties p=new Properties();if(file.exists())try(InputStream in=new FileInputStream(file)){p.load(in);}return p;}catch(IOException e){throw new IllegalStateException(e);}}
 public String getString(String key,String fallback){return load().getProperty(key,fallback);}
 public Editor edit(){return new Editor();}
 public class Editor {
  private final Map<String,String> changes=new HashMap<>();
  public Editor putString(String key,String value){changes.put(key,value);return this;}
  public Editor remove(String key){changes.put(key,null);return this;}
  public boolean commit(){
   if(new File(file.getParentFile(),"fail-commit").exists())return false;
   try{Properties p=load();for(Map.Entry<String,String> e:changes.entrySet()){if(e.getValue()==null)p.remove(e.getKey());else p.setProperty(e.getKey(),e.getValue());}
    try(OutputStream out=new FileOutputStream(file)){p.store(out,"isolated auth fixture");}return true;
   }catch(IOException e){return false;}
  }
 }
}''',
    'android/os/Build.java': 'package android.os;public class Build{public static class VERSION{public static int SDK_INT=28;}}',
    'android/util/Base64.java': '''package android.util;public class Base64 {
 public static final int NO_WRAP=2;
 public static String encodeToString(byte[] b,int flags){return java.util.Base64.getEncoder().encodeToString(b);}
 public static byte[] decode(String s,int flags){return java.util.Base64.getDecoder().decode(s);}
}''',
    'android/security/keystore/KeyProperties.java': '''package android.security.keystore;public class KeyProperties {
 public static final String KEY_ALGORITHM_AES="AES",BLOCK_MODE_GCM="GCM",ENCRYPTION_PADDING_NONE="NoPadding";
 public static final int PURPOSE_ENCRYPT=1,PURPOSE_DECRYPT=2;
}''',
    'android/security/keystore/KeyPermanentlyInvalidatedException.java': '''package android.security.keystore;
public class KeyPermanentlyInvalidatedException extends java.security.InvalidKeyException {}''',
    'android/security/keystore/KeyGenParameterSpec.java': '''package android.security.keystore;
public class KeyGenParameterSpec implements java.security.spec.AlgorithmParameterSpec {
 public final String alias;private KeyGenParameterSpec(String a){alias=a;}
 public static class Builder {private final String alias;public Builder(String a,int purposes){alias=a;}
  public Builder setBlockModes(String... s){return this;}public Builder setEncryptionPaddings(String... s){return this;}
  public Builder setRandomizedEncryptionRequired(boolean b){return this;}public Builder setUserAuthenticationRequired(boolean b){return this;}
  public KeyGenParameterSpec build(){return new KeyGenParameterSpec(alias);}
 }
}''',
    'host/AuthProvider.java': r'''package host;
import java.io.*;import java.nio.file.*;import java.security.*;import java.security.cert.Certificate;
import java.security.spec.AlgorithmParameterSpec;import java.util.*;import javax.crypto.*;import javax.crypto.spec.SecretKeySpec;
public class AuthProvider extends Provider {
 public static Path root;
 public AuthProvider(){super("AndroidKeyStore",1.0,"isolated host Keystore peer");put("KeyStore.AndroidKeyStore",Keys.class.getName());put("KeyGenerator.AES",Generator.class.getName());}
 static Path key(){return root.resolve("auth-key");}
 public static class Keys extends KeyStoreSpi {
  public void engineLoad(InputStream in,char[] p)throws IOException{if(Files.exists(root.resolve("key-unavailable")))throw new IOException("temporary host fixture failure");}
  public void engineStore(OutputStream out,char[] p){}
  public boolean engineContainsAlias(String alias){return Files.exists(key());}
  public Key engineGetKey(String alias,char[] p)throws UnrecoverableKeyException{
   try{if(Files.exists(root.resolve("key-transient-get")))throw new UnrecoverableKeyException();if(Files.exists(root.resolve("key-invalidated"))){UnrecoverableKeyException f=new UnrecoverableKeyException();f.initCause(new android.security.keystore.KeyPermanentlyInvalidatedException());throw f;}return new SecretKeySpec(Files.readAllBytes(key()),"AES");}catch(IOException e){throw new UnrecoverableKeyException();}
  }
  public Certificate[] engineGetCertificateChain(String alias){return null;}public Certificate engineGetCertificate(String a){return null;}
  public Date engineGetCreationDate(String a){return null;}public void engineSetKeyEntry(String a,Key k,char[] p,Certificate[] c){}
  public void engineSetKeyEntry(String a,byte[] k,Certificate[] c){}public void engineSetCertificateEntry(String a,Certificate c){}
  public void engineDeleteEntry(String a){try{Files.deleteIfExists(key());Files.deleteIfExists(root.resolve("key-invalidated"));}catch(IOException e){throw new IllegalStateException(e);}}public Enumeration<String> engineAliases(){return Collections.emptyEnumeration();}
  public int engineSize(){return 0;}public boolean engineIsKeyEntry(String a){return engineContainsAlias(a);}
  public boolean engineIsCertificateEntry(String a){return false;}public String engineGetCertificateAlias(Certificate c){return null;}
 }
 public static class Generator extends KeyGeneratorSpi {
  protected void engineInit(SecureRandom r){}protected void engineInit(int size,SecureRandom r){}
  protected void engineInit(AlgorithmParameterSpec spec,SecureRandom r){}
  protected SecretKey engineGenerateKey(){try{byte[] b=new byte[32];new SecureRandom().nextBytes(b);Files.write(key(),b);return new SecretKeySpec(b,"AES");}catch(IOException e){throw new IllegalStateException(e);}}
 }
}''',
}

PEER = '''package com.quanhoang.greenfarm3.S800x480
class SocialAuthClient {companion object {
 fun isAuthorizationUrl(value:String,origin:String,provider:String)=value.startsWith(origin+"auth/web/login?attempt=")&&provider=="password"
}}
'''

HARNESS = r'''
import com.quanhoang.greenfarm3.S800x480.*
import host.AuthProvider
import java.nio.file.*
import java.security.Security
fun expect(code:String,work:()->Unit){try{work();error("expected $code")}catch(e:SocialAuthFailure){check(e.code==code){e.code}}}
fun main(args:Array<String>){
 val dir=Paths.get(args[0]);AuthProvider.root=dir;Security.addProvider(AuthProvider())
 val vault=SecureAccountStore(android.content.Context(dir.toFile()),"https://auth.example/")
 val session=SocialSession("private-access-token", "private-refresh-token",1700000900000L,1702592000000L,SocialIdentity("GF000001","password","Player"))
 val attempt=SocialAttempt("password","s".repeat(43),"v".repeat(43),"https://auth.example/auth/web/login?attempt="+"a".repeat(43),1700000300000L)
 val callback=SocialCallback(attempt.state,"c".repeat(43),null)
 when(args[1]){
  "write"->{vault.saveSession(session);vault.saveAttempt(attempt);vault.saveCallback(callback)}
  "read"->{check(vault.session()==session);check(vault.attempt()==attempt);check(vault.callback()==callback)}
  "bad-pending"->{expect("auth_record_unreadable"){vault.callback()};check(vault.session()==session);vault.clearAttempt();check(vault.callback()==null&&vault.attempt()==null&&vault.session()==session)}
  "bad-session"->{expect("auth_record_unreadable"){vault.session()};vault.clearSession();check(vault.session()==null)}
  "missing-key"->{expect("auth_record_unreadable"){vault.session()};check(!Files.exists(dir.resolve("auth-key"))){"read silently created replacement key"}}
  "invalidated-key"->{expect("auth_record_unreadable"){vault.session()};vault.saveSession(session);check(vault.session()==session)}
  "transient-key"->{expect("storage_error"){vault.session()};expect("storage_error"){vault.saveSession(session)}}
  "failed-write"->{expect("storage_error"){vault.saveSession(session.copy(accessToken="replacement"))};check(vault.session()==session)}
  else->error("unknown phase")
 }
 check(String(Files.readAllBytes(dir.resolve("farm-sentinel")))=="existing farm unchanged")
 println("PASS encrypted auth phase "+args[1])
}
'''


class SecureAccountStoreTest(unittest.TestCase):
    def test_encrypted_records_reopen_and_auth_only_failures(self):
        java = Path(os.environ.get('GREENFARM_TEST_JAVA', str(Path(os.environ.get('JAVA_HOME', '/workspace/.tools/jdk-25')) / 'bin/java')))
        cache = Path(os.environ.get('GRADLE_USER_HOME', '/workspace/.cache/gradle'))
        jars = list((cache / 'caches/modules-2/files-2.1/org.robolectric/android-all/9-robolectric-4913185-2').glob('*/*.jar'))
        if not java.exists() or not jars:
            self.skipTest('Requires JDK and cached Android JSON runtime')
        with tempfile.TemporaryDirectory(prefix='secure-auth-reopen-') as temporary:
            directory = Path(temporary)
            java_sources = []
            for name, contents in JAVA.items():
                path = directory / name
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text(contents)
                java_sources.append(path)
            peer, harness = directory / 'Peer.kt', directory / 'AuthStoreChecks.kt'
            peer.write_text(PEER)
            harness.write_text(HARNESS)
            source = ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480'
            classes = directory / 'classes'
            compile_mixed([source / (name + '.kt') for name in ('SecureAccountStore', 'SocialAccountFlow')] + [peer, harness],
                          java_sources, classes, classpath=[jars[0]])
            records = directory / 'records'
            records.mkdir()
            sentinel = records / 'farm-sentinel'
            sentinel.write_text('existing farm unchanged')

            def run(phase):
                result = subprocess.run([str(java), '-cp', runtime_classpath(classes, jars[0]), 'AuthStoreChecksKt', str(records), phase],
                                        capture_output=True, text=True, timeout=20)
                self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
                self.assertEqual(sentinel.read_text(), 'existing farm unchanged')

            def corrupt(name, value):
                path = records / 'greenfarm_online_auth_v1.properties'
                lines = path.read_text().splitlines()
                path.write_text('\n'.join(name + '=' + value if line.startswith(name + '=') else line for line in lines) + '\n')

            run('write')
            raw = (records / 'greenfarm_online_auth_v1.properties').read_text()
            self.assertNotIn('private-access-token', raw)
            self.assertNotIn('private-refresh-token', raw)
            run('read')  # A fresh JVM, file-backed preferences and separately reloaded AES key.
            corrupt('callback', 'invalid-format')
            run('bad-pending')
            run('write')
            corrupt('session', 'invalid-format')
            run('bad-session')
            run('write')
            (records / 'auth-key').write_bytes(bytes(32))  # Record authenticated with a different key.
            run('bad-session')
            run('write')
            (records / 'auth-key').unlink()
            run('missing-key')
            run('write')
            (records / 'key-invalidated').touch()
            run('invalidated-key')
            run('write')
            before = (records / 'greenfarm_online_auth_v1.properties').read_bytes()
            marker = records / 'key-unavailable'
            marker.touch()
            run('transient-key')
            marker.unlink()
            marker = records / 'key-transient-get'
            key_before = (records / 'auth-key').read_bytes()
            marker.touch()
            run('transient-key')
            marker.unlink()
            self.assertEqual((records / 'auth-key').read_bytes(), key_before)
            self.assertEqual((records / 'greenfarm_online_auth_v1.properties').read_bytes(), before)
            (records / 'fail-commit').touch()
            run('failed-write')
            self.assertEqual((records / 'greenfarm_online_auth_v1.properties').read_bytes(), before)
            (records / 'fail-commit').unlink()
            run('read')


if __name__ == '__main__':
    unittest.main()
