"""Execute the production account gate/UI methods with native-UI and account peers.

These checks exercise game-state and button ownership, not rendered pixels or
an installed Android device. The original dialog coordinates and frames are
recorded without replacing the game renderer in the production application.
"""
from pathlib import Path
import re
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from test_aa_tile_renderer import extract_method

METHODS = (
    'private static void enterOnlineAccountGate()',
    'private static void socialAccountLeft()',
    'private static void socialAccountRight()',
    'private static void configureSocialAccountUi()',
    'private static String socialAccountWelcome(String accountName, String gameId, boolean vietnamese)',
    'static int gV(int i)',
    'private static boolean socialAccountScreen(int phase)',
    'static boolean kx(int i)',
    'static void CL()',
    'static void CM()',
    'static void CN()',
    'static boolean qS()',
)

SHELL = r'''
public class FarmGameEngine extends GameRuntime {
static int aYU,aTL,aTO,auC,transitions,lastTransition,dfCalls,layerRebuilds,sounds,legacyUi,legacyCalls,visibilityCalls;
static boolean aUb,aTK,aPg,aPe,aPj,aPl=true,arD,aPf,aPh,aPi,auz,aOM;
static boolean asl,asm,apq,apx;static int loadingPops,oldVersionPrompts;
static short[][][] atc=new short[50][11][24];
static String[][] ate=new String[50][11];
static boolean[][] shown=new boolean[50][11],enabled=new boolean[50][11];
static Sprite[] atf=new Sprite[125];
static a[] aZh=new a[8];
static void e(int g,int i,boolean v){shown[g][i]=v;visibilityCalls++;}
static void f(int g,int i,boolean v){enabled[g][i]=v;}
static void DF(){dfCalls++;}static void gM(int g){if(g!=29)throw new AssertionError();layerRebuilds++;}
static void fF(int state){transitions++;lastTransition=state;}
static void fG(int state){legacyCalls++;}static void kp(int kind){legacyCalls++;}
static void qB(){loadingPops++;}static void qt(){oldVersionPrompts++;}
static void gX(int sound){if(sound!=56)throw new AssertionError();sounds++;}
static boolean qN(){return false;}static void bq(boolean show){legacyUi++;}
static int FC(){return 10;}static void hT(int amount){throw new AssertionError("account action must not grant legacy reward");}
static void Jd(){throw new AssertionError("account action must not mark Facebook reward");}
static void hb(int sound){legacyCalls++;}static void B(int a,int b,int c){legacyCalls++;}static void bz(boolean b){legacyCalls++;}
static int currentEngineState(){return 47;}static void E(int state,boolean error){legacyCalls++;}
static void gJ(int gui){if(gui!=29)throw new AssertionError();GameRuntime.drawnGui++;}
static void be(int gui,int item){if(gui!=0||(item!=2&&item!=4))throw new AssertionError();GameRuntime.busyArtwork++;}
static int kH(){return 800;}static int kI(){return 480;}
static void ok(boolean v,String m){if(!v)throw new AssertionError(m);}
static void reset(){
 aYU=16;aTL=-1;aTO=-1;auC=-1;aUb=aTK=false;aPg=aPe=aPj=arD=aPf=aPh=aPi=false;
 transitions=lastTransition=dfCalls=layerRebuilds=sounds=legacyUi=legacyCalls=visibilityCalls=0;
 socialAccountUiStatus=-1;socialAccountUiLanguage=-1;socialAccountUiBusy=false;socialAccountUiMessage=socialAccountUiGameId=socialAccountUiName=null;
 asl=asm=apq=apx=false;loadingPops=oldVersionPrompts=0;
 LocalDevelopmentConfig.ONLINE_ACCOUNTS=true;SocialAccountController.reset();GameRuntime.reset();SocialAccountArtwork.draws=0;GloftGF2F.minimizes=0;GloftGF2F.language=16;
 atc[29][6][2]=157;atc[29][6][3]=399;atc[29][6][5]=20;atc[29][6][6]=78;
 atc[29][7][2]=165;atc[29][7][3]=408;atc[29][7][5]=20;atc[29][7][6]=147;
 atc[29][8][2]=497;atc[29][8][3]=399;atc[29][8][5]=20;atc[29][8][6]=80;
 atc[29][9][2]=164;atc[29][9][3]=407;atc[29][9][5]=226;atc[29][9][6]=38;
 atc[29][10][2]=504;atc[29][10][3]=407;atc[29][10][5]=226;atc[29][10][6]=38;
 atc[0][3][2]=184;atc[0][3][3]=412;
 atc[29][3][5]=394;atc[29][3][6]=103;atc[29][3][7]=2;
 for(int i=0;i<atf.length;i++)atf[i]=new Sprite();
 for(int i=0;i<aZh.length;i++)aZh[i]=new a();
 a.charWidth=10;a.lineHeight=18;a.spacing=1;a.measureCalls=0;
 a.failWrap=false;
}
static String geometry(){StringBuilder out=new StringBuilder();for(int item:new int[]{6,7,8,9,10})for(int field:new int[]{2,3,5,6,7})out.append(atc[29][item][field]).append(',');return out.toString();}
static void testEntryDefaults(){
 reset();enterOnlineAccountGate();ok(aYU==16&&aTL==18&&aTO==18&&auC==0&&aUb&&aTK,"missing runtime age/gender defaults");
 ok(transitions==1&&lastTransition==47&&sounds==0&&legacyCalls==0,"gate uses original account loading without legacy consent handlers");
 reset();aYU=7;aTL=11;aTO=10;auC=2;enterOnlineAccountGate();
 ok(aYU==7&&aTL==11&&aTO==10&&auC==2&&!aTK,"existing age/gender and age restriction retained");
 reset();aYU=-1;GloftGF2F.language=9;enterOnlineAccountGate();ok(aYU==9,"supported device language retained");
 reset();aYU=-1;GloftGF2F.language=99;enterOnlineAccountGate();ok(aYU==0,"unknown device language has original English fallback");
}
static void testNativeUiAndButtons(){
 reset();String before=geometry();configureSocialAccountUi();
 ok(before.equals(geometry()),"original button textures, coordinates, footprint and fonts unchanged");
 ok(shown[29][0]&&shown[29][1]&&shown[29][2]&&shown[29][3],"native background and dialog remain visible");
 ok(!shown[29][4]&&!shown[29][5]&&ate[29][4].equals(""),"account UI has no old coin reward");
 ok(shown[29][6]&&shown[29][7]&&shown[29][8]&&enabled[29][6]&&enabled[29][7]&&enabled[29][8],"original provider button and Facebook mark ownership");
 ok(ate[29][3].equals("Vui lòng đăng nhập tài khoản để tiếp tục")&&ate[29][9].equals("Facebook")&&ate[29][10].equals("Đăng nhập"),"requested native dialog wording");
 aYU=7;configureSocialAccountUi();ok(atc[29][1][5]==50,"Japanese keeps its original localized logo sprite");
 aYU=16;configureSocialAccountUi();ok(atc[29][1][5]==49,"other languages keep the original standard logo sprite");
 CM();ok(SocialAccountController.facebook==1&&SocialAccountController.password==0,"left native button starts Facebook");
 reset();CN();ok(SocialAccountController.facebook==1&&SocialAccountController.password==0,"Facebook icon shares left action");
 reset();CL();ok(SocialAccountController.password==1&&SocialAccountController.facebook==0,"right native button starts the Website browser flow");
 reset();SocialAccountController.state=SocialAccountController.WELCOME;configureSocialAccountUi();
 ok(ate[29][3].contains("Chào mừng GF000123 trở lại!")&&ate[29][3].contains("thiết bị"),"welcome identifies account without claiming cloud farm synchronization");
 ok(ate[29][9].equals("Đăng nhập")&&ate[29][10].equals("Đăng xuất")&&!shown[29][7],"welcome reuses buttons and hides provider-specific mark");
 socialAccountLeft();ok(SocialAccountController.continues==1&&transitions==0,"continue validates session before any world entry");
 reset();SocialAccountController.state=SocialAccountController.WELCOME;socialAccountRight();
 ok(SocialAccountController.logouts==1&&transitions==0&&legacyCalls==0,"logout is account-only and cannot invoke farm/reset/legacy callbacks");
 reset();SocialAccountController.inProgress=true;configureSocialAccountUi();CM();CN();CL();socialAccountLeft();socialAccountRight();
 ok(!enabled[29][6]&&!enabled[29][7]&&!enabled[29][8]&&sounds==0&&SocialAccountController.actions()==0,"busy button repeats cannot create requests");
 reset();SocialAccountController.state=SocialAccountController.ERROR;configureSocialAccountUi();
 ok(enabled[29][6]&&enabled[29][8]&&ate[29][3].equals("Mất kết nối. Vui lòng thử lại."),"native error text and retry actions restored");
 aYU=0;configureSocialAccountUi();ok(ate[29][3].equals("Cannot reach the account server."),"localized failure survives the renderer");
 socialAccountRight();ok(SocialAccountController.password==1,"failed authorization can be retried");
}
static void testGateAndBusyRendering(){
 reset();socialAccountScreen(0);socialAccountScreen(1);
 ok(SocialAccountController.startups==2&&transitions==0,"entry/resume call the idempotent controller but cannot enter world");
 for(int state:new int[]{SocialAccountController.SIGNED_OUT,SocialAccountController.CHECKING,SocialAccountController.ERROR,SocialAccountController.WELCOME}){
  SocialAccountController.state=state;socialAccountScreen(2);ok(transitions==0,"only verified READY can enter gameplay");
 }
 SocialAccountController.state=SocialAccountController.READY;socialAccountScreen(2);
 ok(transitions==1&&lastTransition==18&&legacyCalls==0,"verified session uses original loading/world continuation without binding save to account");
 reset();GameRuntime.key=19;SocialAccountController.inProgress=true;socialAccountScreen(2);
 ok(SocialAccountController.cancels==1&&GloftGF2F.minimizes==0&&transitions==0,"Back cancels pending authentication only");
 ok(enabled[29][6]&&enabled[29][8],"Back cancellation restores retry controls");
 reset();GameRuntime.key=19;socialAccountScreen(2);ok(GloftGF2F.minimizes==1&&transitions==0,"idle Back retains minimize behavior");
 reset();socialAccountScreen(3);ok(GameRuntime.drawnGui==1&&SocialAccountArtwork.draws==1&&GameRuntime.busyArtwork==0,"normal login uses native box and original native account mark");
 SocialAccountController.state=SocialAccountController.WELCOME;socialAccountScreen(3);ok(SocialAccountArtwork.draws==1,"welcome removes account mark");
 SocialAccountController.inProgress=true;socialAccountScreen(3);
 ok(GameRuntime.lastAlpha==0x66000000&&GameRuntime.busyArtwork==2&&GameRuntime.fill>=48&&GameRuntime.fill<416,"busy rendering uses original loading art over translucent overlay");
 ok(socialAccountScreen(8),"busy modal consumes additional pointer input");SocialAccountController.inProgress=false;ok(!socialAccountScreen(8),"idle native buttons receive pointer input");
}
static void testUiRefreshOwnership(){
 reset();socialAccountScreen(0);int initialVisibility=visibilityCalls;
 ok(dfCalls==1&&layerRebuilds==1&&initialVisibility>0,"entry registers native account widgets");
 socialAccountScreen(2);socialAccountScreen(2);socialAccountScreen(3);
 ok(dfCalls==1&&layerRebuilds==1&&visibilityCalls==initialVisibility,"unchanged update/render frames preserve native hitboxes and animation state");
 SocialAccountController.state=SocialAccountController.CHECKING;socialAccountScreen(2);
 ok(dfCalls==2&&layerRebuilds==2&&visibilityCalls==initialVisibility*2,"status changes refresh the native presentation");
 SocialAccountController.inProgress=true;socialAccountScreen(2);
 ok(dfCalls==3&&!enabled[29][6]&&!enabled[29][8],"busy changes refresh and disable native buttons");
 socialAccountScreen(2);ok(dfCalls==3,"unchanged busy frames retain original animation state");
 SocialAccountController.errorMessage="Kết nối hết thời gian. Vui lòng thử lại.";socialAccountScreen(2);
 ok(dfCalls==4,"changed controller message invalidates presentation");
 SocialAccountController.savedGameId="GF000456";socialAccountScreen(2);
 ok(dfCalls==5,"changed account identifier invalidates presentation");
 SocialAccountController.state=SocialAccountController.WELCOME;SocialAccountController.inProgress=false;socialAccountScreen(2);
 ok(dfCalls==6&&ate[29][3].contains("GF000456"),"welcome renders changed account identity");
 aYU=7;socialAccountScreen(2);ok(dfCalls==7&&atc[29][1][5]==50,"language changes refresh localized native artwork");
 socialAccountScreen(1);ok(dfCalls==8&&layerRebuilds==8,"resume re-registers native hitboxes even when account presentation is unchanged");
 socialAccountScreen(2);ok(dfCalls==8,"steady frame after resume does not rebuild again");
 socialAccountScreen(0);ok(dfCalls==9&&layerRebuilds==9,"re-entry refreshes native UI ownership");
}
static void testVerifiedAccountName(){
 reset();String before=geometry();SocialAccountController.savedAccountName="unverified_cached_name";configureSocialAccountUi();
 ok(!ate[29][3].contains("unverified_cached_name"),"unverified remembered names do not label the login screen");
 SocialAccountController.state=SocialAccountController.WELCOME;SocialAccountController.savedAccountName="player123";configureSocialAccountUi();
 ok(ate[29][3].equals("player123 (GF000123)\nNông trại cục bộ."),"verified username and backend GAME_ID appear with the local-farm notice");
 ok(before.equals(geometry())&&atc[29][3][5]==394&&atc[29][3][6]==103&&atc[29][3][7]==2,"verified identity keeps original buttons, text bounds and font");
 int rebuilds=dfCalls,measurements=a.measureCalls;configureSocialAccountUi();socialAccountScreen(2);
 ok(dfCalls==rebuilds&&a.measureCalls==measurements,"stable verified names do not rebuild hitboxes or repeat font layout");
 SocialAccountController.savedAccountName="player456";configureSocialAccountUi();
 ok(dfCalls==rebuilds+1&&ate[29][3].startsWith("player456 (GF000123)")&&!ate[29][3].contains("player123"),"verified name changes invalidate the UI cache");
 SocialAccountController.savedAccountName="";configureSocialAccountUi();
 ok(ate[29][3].startsWith("Chào mừng GF000123 trở lại!"),"missing provider display name falls back to the original GAME_ID welcome");
 String longUsername="WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";SocialAccountController.savedAccountName=longUsername;aYU=0;configureSocialAccountUi();
 ok(ate[29][3].startsWith(longUsername+" (GF000123)")&&ate[29][3].contains("Farm stored on this device."),"32-character website username remains complete when native layout fits");
 a.charWidth=20;a.lineHeight=26;a.spacing=2;socialAccountUiStatus=-1;configureSocialAccountUi();
 String fitted=ate[29][3];a font=aZh[2];int lines=font.a(fitted,394,-1,false)[0];
 ok(fitted.contains("... (GF000123)")&&lines*font.hG()+(lines-1)*font.hA()<=103,"oversized names are fitted using the original native font metrics");
 a.charWidth=10;a.lineHeight=18;a.spacing=1;String emoji=new String(Character.toChars(0x1F33E));StringBuilder providerName=new StringBuilder();
 for(int i=0;i<33;i++)providerName.append(emoji);SocialAccountController.savedAccountName=providerName.toString();configureSocialAccountUi();
 ok(ate[29][3].startsWith(providerName.substring(0,64)+"... (GF000123)"),"provider display cap preserves Unicode code points");
 a.failWrap=true;SocialAccountController.savedAccountName="different name";socialAccountScreen(2);
 ok(ate[29][3].startsWith("Welcome back, GF000123!")&&ate[29][9].equals("Continue")&&ate[29][10].equals("Log out"),
   "a welcome font failure still replaces the busy caption and installs the verified account actions");
 ok(SocialAccountController.layoutFailures==1,"welcome font failure attributed without exposing the account name");
 SocialAccountController.state=SocialAccountController.LOGGING_OUT;SocialAccountController.inProgress=true;configureSocialAccountUi();
 ok(!ate[29][3].contains("GF000123")&&!ate[29][3].contains(emoji),"leaving the verified welcome removes its account label");
 ok(transitions==0&&legacyCalls==0&&SocialAccountController.actions()==0,"presenting an account name cannot enter or rebind a farm");
}
static void testLegacyModeOwnership(){
 reset();LocalDevelopmentConfig.ONLINE_ACCOUNTS=false;kx(0);
 ok(legacyUi==1&&SocialAccountController.startups==0&&transitions==0,"disabled online mode retains original Facebook screen entry");
 kx(1);ok(legacyUi==2&&SocialAccountController.startups==0,"disabled mode retains original resume handler");
 kx(3);ok(GameRuntime.drawnGui==1&&SocialAccountArtwork.draws==0,"disabled mode renders original UI without new account art");
 reset();kx(0);ok(SocialAccountController.startups==1&&legacyUi==0,"online mode owns account screen before legacy callbacks");
}
static void testLoadingReadiness(){
 reset();ok(!qS()&&loadingPops==0,"incomplete loading cannot reach account UI");
 asl=true;ok(!qS()&&loadingPops==0,"active but incomplete resource load stays loading");
 asm=true;apq=apx=true;
 ok(qS()&&loadingPops==1&&oldVersionPrompts==0,"cached anonymous SDK upgrade state cannot block new account gate");
 ok(apq&&apx,"bypassing cached legacy prompt does not erase its stored state");
 reset();LocalDevelopmentConfig.ONLINE_ACCOUNTS=false;asl=asm=apq=apx=true;
 ok(!qS()&&loadingPops==0&&oldVersionPrompts==1,"legacy mode keeps original upgrade screen behavior");
}
public static void main(String[]args){testEntryDefaults();testNativeUiAndButtons();testGateAndBusyRendering();testUiRefreshOwnership();testVerifiedAccountName();testLegacyModeOwnership();testLoadingReadiness();
System.out.println("PASS actual native account gate/buttons, original geometry, verified-only entry, cancellation, defaults and legacy-mode ownership");}
'''

PEERS = r'''
class LocalDevelopmentConfig {static boolean ONLINE_ACCOUNTS=true;}
class LocalDevelopmentMode {static boolean skipLegacyBackend(){return LocalDevelopmentConfig.ONLINE_ACCOUNTS;}}
class SocialAccountController {
 static final int SIGNED_OUT=0,CHECKING=1,BROWSER=2,EXCHANGING=3,WELCOME=4,READY=5,ERROR=6,LOGGING_OUT=7;
 static int state,startups,ticks,facebook,password,continues,logouts,cancels,layoutFailures;static boolean inProgress;
 static String errorMessage,savedGameId,savedAccountName;
 static void reset(){state=SIGNED_OUT;startups=ticks=facebook=password=continues=logouts=cancels=layoutFailures=0;inProgress=false;errorMessage=null;savedGameId="GF000123";savedAccountName="";}
 static void reportWelcomeLayoutFailure(RuntimeException failure){layoutFailures++;}
 static boolean busy(){return inProgress;}static int status(){return state;}
 static String gameId(){return savedGameId;}static String message(int language){
  if(errorMessage!=null)return errorMessage;
  if(state==ERROR)return language==16?"Mất kết nối. Vui lòng thử lại.":"Cannot reach the account server.";
  return language==16?"Vui lòng đăng nhập tài khoản để tiếp tục":"Sign in to continue.";
 }
 static String accountName(){return state==WELCOME||state==READY?savedAccountName:"";}
 static void startupGate(){startups++;}static void tick(){ticks++;}
 static void choose(String provider){if(provider.equals("facebook"))facebook++;else if(provider.equals("password"))password++;else throw new AssertionError();inProgress=true;state=BROWSER;}
 static void continueSaved(){continues++;inProgress=true;state=CHECKING;}static void logout(){logouts++;inProgress=true;state=LOGGING_OUT;}
 static void cancel(){cancels++;inProgress=false;state=ERROR;}static int actions(){return facebook+password+continues+logouts;}
}
class GloftGF2F {static GloftGF2F acw=new GloftGF2F(),acx=acw;static int language,minimizes;
 int kR(){return language;}String bY(String key){return "on";}void jf(String value){if(!value.equals("MIDlet://minimize"))throw new AssertionError();minimizes++;}}
class bt {static boolean nY(){return false;}static void nT(){throw new AssertionError("legacy Facebook SDK invoked");}}
class Sprite {void a(Object graphics,int frame,float x,float y,int transform){}}
class a {
 static int charWidth=10,lineHeight=18,spacing=1,measureCalls;
 static boolean failWrap;
 void a(String value,char[] ignored){throw new StringIndexOutOfBoundsException("stale layout bounds");}
 short[] a(String value,int width,int unused,boolean flag){if(failWrap)throw new IndexOutOfBoundsException();measureCalls++;int lines=0;for(String part:value.split("\\n",-1))lines+=Math.max(1,(part.codePointCount(0,part.length())*charWidth+width-1)/width);return new short[]{(short)lines};}
 int hG(){return lineHeight;}int hA(){return spacing;}
}
class w {static int screenWidth=800,screenHeight=480;}
class SystemClock {static long uptimeMillis(){return 1000L;}}
class SocialAccountArtwork {static int draws;static boolean drawAccountIcon(int x,int y,int size){if(x!=505||y!=408||size!=32)throw new AssertionError("original icon footprint");draws++;return true;}}
class GameRuntime {static final Object Qj=new Object();static int key,drawnGui,busyArtwork,lastAlpha,fill;
 static void reset(){key=-1;drawnGui=busyArtwork=lastAlpha=fill=0;}static int jO(){return key;}
 static void dl(int color){lastAlpha=color;}static void dk(int color){if(color!=26856)throw new AssertionError("native loading color");}
 static void b(Object graphics,float x,float y,float width,float height,boolean v){if(height==20)fill=(int)width;else if(x!=0||y!=0||width!=800||height!=480)throw new AssertionError("overlay bounds");}
}
'''


class SocialAccountEngineTest(unittest.TestCase):
    def test_original_account_ui_and_game_entry_handlers(self):
        source = (ROOT / 'app/src/main/java/com/quanhoang/greenfarm3/S800x480/FarmGameEngine.java').read_text()
        methods = '\n'.join(extract_method(source, signature) for signature in METHODS)
        cache_fields = re.findall(r'^    private static (?:int|boolean|String) socialAccountUi\w+(?: = [^;]+)?;', source, re.MULTILINE)
        self.assertEqual(len(cache_fields), 6, 'execute the production UI-cache declarations')
        with tempfile.TemporaryDirectory(prefix='social-account-engine-') as temp:
            path = Path(temp)
            fixture = path / 'FarmGameEngine.java'
            fixture.write_text(SHELL + '\n'.join(cache_fields) + '\n' + methods + '\n}\n' + PEERS)
            compiled = subprocess.run([str(jdk_tool('javac')), '-encoding', 'UTF-8', '-source', '8', '-target', '8', str(fixture)],
                                      capture_output=True, text=True, timeout=30)
            self.assertEqual(compiled.returncode, 0, compiled.stdout + compiled.stderr)
            result = subprocess.run([str(jdk_tool('java')), '-cp', str(path), 'FarmGameEngine'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS actual native account gate', result.stdout)
        # Preserve the integration boundary: full loading first, UI gate before
        # entering the existing world/save controller, and no age UI setup.
        logo = extract_method(source, 'static boolean il(int i)')
        self.assertIn('if (LocalDevelopmentConfig.ONLINE_ACCOUNTS)', logo)
        self.assertIn('enterOnlineAccountGate();', logo)
        age = extract_method(source, 'static boolean lA(int i)')
        self.assertLess(age.index('enterOnlineAccountGate();'), age.index('gG(44);'))
        world = extract_method(source, 'static boolean jM(int i)')
        self.assertLess(world.index('SocialAccountController'), world.index('ProgressSaveController.enterWorld();'))


if __name__ == '__main__':
    unittest.main()
