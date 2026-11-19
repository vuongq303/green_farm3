"""Exercise the compiled renderer callbacks with observed surface/context sequences."""

from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "tools"))
from android_build_tools import jdk_tool
from game_source_context import current_source_path


class RenderSurfaceLifecycleTest(unittest.TestCase):
    def test_surface_rebind_preserves_resources_and_context_replacement_rebuilds(self):
        source = current_source_path("GLLib").read_text(encoding="utf-8")
        methods = "\n".join(extract_method(source, signature) for signature in (
            "public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig)",
            "public void ajU()",
            "public void onSurfaceChanged(GL10 gl10, int i, int i2)",
        ))
        fixture = """
class GL10 {}
class EGLConfig {}
class Canvas {
    public void ajU() {}
}
public class RenderSurfaceFixture extends Canvas {
    private static RenderSurfaceFixture QD;
    private boolean initializationFailed;
    private GL10 initializedGl;
    private static int initializations;
    private static int surfaceChanges;
    private static void b(String message, String tag, int level) {}
    private static void a(GL10 gl) { initializations++; }
    private static void ku() { surfaceChanges++; }

    public static void main(String[] args) {
        RenderSurfaceFixture renderer = new RenderSurfaceFixture();
        QD = renderer;
        GL10 first = new GL10();
        EGLConfig configuration = new EGLConfig();
        renderer.onSurfaceCreated(first, configuration);
        renderer.onSurfaceChanged(first, 800, 480);
        renderer.onSurfaceCreated(first, configuration);
        renderer.onSurfaceChanged(first, 800, 480);
        check(initializations == 1 && surfaceChanges == 2, "surface rebind");

        GL10 second = new GL10();
        renderer.onSurfaceCreated(second, configuration);
        renderer.onSurfaceChanged(second, 1200, 720);
        check(initializations == 2 && surfaceChanges == 3, "new context");

        renderer.ajU();
        renderer.onSurfaceCreated(second, configuration);
        check(initializations == 3, "explicit context loss");

        RenderSurfaceFixture staleRenderer = new RenderSurfaceFixture();
        staleRenderer.onSurfaceCreated(new GL10(), configuration);
        renderer.initializationFailed = true;
        renderer.onSurfaceCreated(new GL10(), configuration);
        renderer.onSurfaceChanged(second, 480, 800);
        check(initializations == 3 && surfaceChanges == 3, "inactive owner");
    }

    private static void check(boolean passed, String phase) {
        if (!passed) throw new AssertionError(phase);
    }
"""
        with tempfile.TemporaryDirectory(prefix="greenfarm-surface-") as directory:
            java_file = Path(directory) / "RenderSurfaceFixture.java"
            java_file.write_text(fixture + methods + "\n}\n", encoding="utf-8")
            subprocess.run([jdk_tool("javac"), java_file], check=True, cwd=directory)
            subprocess.run([jdk_tool("java"), "-cp", directory, "RenderSurfaceFixture"],
                           check=True, cwd=directory)
