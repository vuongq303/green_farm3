"""Execute the real accepted-inventory mutation gate, including rejected actions."""
from pathlib import Path
import subprocess
import tempfile
import sys
import unittest
from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from game_source_context import core_source_directory, qualified_package


class InventorySavePriorityTest(unittest.TestCase):
    def test_only_accepted_inventory_debits_request_critical_checkpoint(self):
        method = extract_method((core_source_directory() / 'FarmGameEngine.java').read_text(), 'static boolean O(int i, int i2, int i3)')
        code = '''package PACKAGE;
class ProgressSaveController {static int critical;static void requestCriticalSave(){critical++;}}
class j {int[][] KW={{0,1}};static j bY(int i){return new j();}}
class FarmGameEngine {
 static int total=10,changes;static boolean awV,aTu,hasUnsavedGameChanges;
 static short[] aZL={1},aZE={1},aZI={1},aZG={1};
 static int hF(int id){return total;}static int Fw(){return 100;}
 static void H(int a,int b,int c,int d){}static void Q(int c,int item,int delta){changes+=delta;}
 static void aa(int a,int b,int c){}static int aIm;
 METHOD
}
public class InventoryPriorityProbe {
 public static void main(String[] args){
  if(!FarmGameEngine.O(0,0,3)||ProgressSaveController.critical!=0)throw new AssertionError("ordinary pickup bypassed batch policy");
  if(!FarmGameEngine.O(0,0,-2)||ProgressSaveController.critical!=1||FarmGameEngine.changes!=1)throw new AssertionError("accepted debit not prioritized");
  FarmGameEngine.total=-1;
  if(FarmGameEngine.O(0,0,-1)||ProgressSaveController.critical!=1||FarmGameEngine.changes!=1)throw new AssertionError("rejected debit persisted");
  System.out.println("PASS accepted inventory debit bypasses debounce; ordinary pickups batch; rejected operations do not save");
 }
}
'''.replace('PACKAGE', qualified_package()).replace('METHOD', method)
        with tempfile.TemporaryDirectory() as temp:
            java = Path(temp) / 'InventoryPriorityProbe.java'
            java.write_text(code)
            result = subprocess.run([str(jdk_tool('javac')), '-d', temp, str(java)], text=True, capture_output=True)
            self.assertEqual(result.returncode, 0, result.stderr)
            result = subprocess.run([str(jdk_tool('java')), '-cp', temp, qualified_package()+'.InventoryPriorityProbe'], text=True, capture_output=True)
            self.assertEqual(result.returncode, 0, result.stdout+result.stderr)
            print(result.stdout.strip())
