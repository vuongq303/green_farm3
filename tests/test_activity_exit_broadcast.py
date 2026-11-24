"""Exercise the same broadcast helper entry points used by the retained Android DEX."""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath


class ActivityExitBroadcastTest(unittest.TestCase):
    def test_modern_and_legacy_registrations_keep_receiver_private(self):
        source = ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/ActivityExitBroadcast.kt'
        peers = {
            'android/os/Build.java': '''
                package android.os;
                public class Build { public static class VERSION { public static int SDK_INT; } }
            ''',
            'android/content/BroadcastReceiver.java': '''
                package android.content;
                public class BroadcastReceiver {}
            ''',
            'android/content/IntentFilter.java': '''
                package android.content;
                public class IntentFilter {}
            ''',
            'android/content/Intent.java': '''
                package android.content;
                public class Intent {
                    public String target;
                    public Intent setPackage(String target) { this.target = target; return this; }
                }
            ''',
            'android/content/Context.java': '''
                package android.content;
                public class Context {
                    public static final int RECEIVER_NOT_EXPORTED = 4;
                    public BroadcastReceiver receiver;
                    public IntentFilter filter;
                    public int flags;
                    public String permission;
                    public Intent sent;
                    public Context(String pkg) { packageName = pkg; }
                    private final String packageName;
                    public String getPackageName() { return packageName; }
                    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, int flags) {
                        this.receiver = receiver; this.filter = filter; this.flags = flags;
                        return null;
                    }
                    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String permission, Object scheduler) {
                        this.receiver = receiver; this.filter = filter; this.permission = permission;
                        return null;
                    }
                    public void sendBroadcast(Intent intent) { sent = intent; }
                }
            ''',
            'ExitBroadcastProbe.java': '''
                import android.content.*;
                import android.os.Build;
                import com.quanhoang.greenfarm3.S800x480.ActivityExitBroadcast;

                public class ExitBroadcastProbe {
                    public static void main(String[] arguments) {
                        for (int api : new int[]{16, 32, 33, 37}) {
                            Build.VERSION.SDK_INT = api;
                            for (String pkg : new String[]{"com.quanhoang.greenfarm3", "com.gameloft.android.ANMP.GloftGF2F"}) {
                                Context context = new Context(pkg);
                                BroadcastReceiver receiver = new BroadcastReceiver();
                                IntentFilter filter = new IntentFilter();
                                ActivityExitBroadcast.register(context, receiver, filter);
                                if (context.receiver != receiver || context.filter != filter) throw new AssertionError("receiver identity");
                                if (api >= 33) {
                                    if (context.flags != Context.RECEIVER_NOT_EXPORTED || context.permission != null) throw new AssertionError("exported");
                                } else if (context.flags != 0 || !context.permission.equals(pkg + ".permission.INTERNAL_BROADCAST")) {
                                    throw new AssertionError("signature permission");
                                }
                                Intent intent = new Intent();
                                ActivityExitBroadcast.send(context, intent);
                                if (context.sent != intent || !pkg.equals(intent.target)) throw new AssertionError("cross-app broadcast");
                            }
                        }
                    }
                }
            ''',
        }
        with tempfile.TemporaryDirectory() as temporary:
            folder = Path(temporary)
            java_files = []
            for relative, content in peers.items():
                path = folder / relative
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text(content, encoding='utf-8')
                java_files.append(path)
            compile_mixed([source], java_files, folder)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(folder), 'ExitBroadcastProbe'],
                                    capture_output=True, text=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
