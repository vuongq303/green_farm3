import java.nio.file.*;
import java.util.*;
import org.jf.dexlib2.*;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.*;
import org.jf.dexlib2.iface.instruction.*;
import org.jf.dexlib2.iface.reference.*;
import org.jf.dexlib2.immutable.*;
import org.jf.dexlib2.immutable.instruction.*;
import org.jf.dexlib2.immutable.reference.ImmutableStringReference;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.dexlib2.writer.pool.DexPool;

/** Permanent production transform, after verified source merge and relocation.
 * Removes Firebase definitions from the original SDK DEX, and retires online
 * SDK entry points still needed as binary symbols by the legacy wrapper.
 * Assets, native libraries and game/storage methods are never rewritten here.
 */
public final class RemoveOnlineServices {
    static boolean firebase(String t) { return t.contains("Lcom/google/firebase/"); }
    static boolean removed(String t) {
        return firebase(t) || t.contains("/PushNotification/Firebase")
            || t.startsWith("Lcom/google/android/gms/measurement/")
            || t.startsWith("Lcom/google/android/gms/internal/measurement/");
    }
    static boolean retired(String t) {
        return t.startsWith("Lcom/gameloft/android2d/socialnetwork/")
            || t.equals("Lcom/gameloft/a/b/a;")
            || t.startsWith("Lcom/gameloft/android2d/iap/")
            || t.equals("Lcom/quanhoang/greenfarm3/PushNotification/q;")
            || t.equals("Lcom/quanhoang/greenfarm3/S800x480/bt;")
            || t.startsWith("Lcom/gameloft/android2d/adsmanager/")
            || t.startsWith("Lcom/gameloft/adsmanager/")
            || t.startsWith("Lcom/google/android/gms/measurement/")
            || t.startsWith("Lcom/google/android/gms/internal/measurement/");
    }
    static boolean retiredMethod(Method m) {
        if(m.getDefiningClass().equals("Lcom/gameloft/android/wrapper/au;") && m.getName().equals("Qh"))return true;
        // Tracking may retain a local object for the wrapper ABI, but may not
        // authorize, schedule uploads or contact a time/authentication server.
        if(m.getDefiningClass().equals("Lcom/quanhoang/greenfarm3/bx;"))
            return !m.getName().equals("h") && !m.getName().equals("ND");
        return retired(m.getDefiningClass());
    }
    static boolean usesFirebase(Method m) {
        MethodImplementation impl=m.getImplementation();
        if(impl==null)return false;
        for(Instruction i:impl.getInstructions()) if(i instanceof ReferenceInstruction) {
            Reference r=((ReferenceInstruction)i).getReference();
            if(r instanceof MethodReference && firebase(((MethodReference)r).getDefiningClass()))return true;
            if(r instanceof FieldReference && firebase(((FieldReference)r).getDefiningClass()))return true;
            if(r instanceof TypeReference && firebase(((TypeReference)r).getType()))return true;
        }
        return false;
    }
    static Method unavailable(Method m) {
        String type=m.getReturnType();
        List<Instruction> code=new ArrayList<>();
        int registers=2;
        if(type.equals("V"))code.add(new ImmutableInstruction10x(Opcode.RETURN_VOID));
        else if(type.equals("J")||type.equals("D")) {
            code.add(new ImmutableInstruction21s(Opcode.CONST_WIDE_16,0,0));
            code.add(new ImmutableInstruction11x(Opcode.RETURN_WIDE,0));
        } else {
            if(type.equals("Ljava/lang/String;"))code.add(new ImmutableInstruction21c(Opcode.CONST_STRING,0,new ImmutableStringReference("")));
            else code.add(new ImmutableInstruction11n(Opcode.CONST_4,0,0));
            code.add(new ImmutableInstruction11x(type.startsWith("L")||type.startsWith("[")?Opcode.RETURN_OBJECT:Opcode.RETURN,0));
        }
        int incoming=(m.getAccessFlags()&8)==0?1:0;
        for(CharSequence p:m.getParameterTypes())incoming+=(p.toString().equals("J")||p.toString().equals("D"))?2:1;
        registers+=incoming;
        return new ImmutableMethod(m.getDefiningClass(),m.getName(),m.getParameters(),type,
            m.getAccessFlags(),m.getAnnotations(),m.getHiddenApiRestrictions(),
            new ImmutableMethodImplementation(registers,code,Collections.emptyList(),Collections.emptyList()));
    }
    static String sha256(byte[] data)throws Exception {
        byte[] hash=java.security.MessageDigest.getInstance("SHA-256").digest(data);
        StringBuilder s=new StringBuilder();for(byte b:hash)s.append(String.format("%02x",b&255));return s.toString();
    }
    public static void main(String[] args)throws Exception {
        if(args.length!=2)throw new IllegalArgumentException("input.dex output.dex");
        Path input=Paths.get(args[0]),output=Paths.get(args[1]);
        if(input.toRealPath().equals(output.toAbsolutePath().normalize()))throw new IllegalArgumentException("Do not overwrite input");
        byte[] before=Files.readAllBytes(input);
        DexBackedDexFile dex=new DexBackedDexFile(Opcodes.getDefault(),before);
        List<ClassDef> kept=new ArrayList<>();int dropped=0,changed=0,firebaseDropped=0;
        for(ClassDef c:dex.getClasses()) {
            if(removed(c.getType())) {dropped++;if(firebase(c.getType()))firebaseDropped++;continue;}
            List<Method> methods=new ArrayList<>();
            for(Method m:c.getMethods()) {
                boolean retire=m.getImplementation()!=null&&!m.getName().equals("<init>")&&(retiredMethod(m)||usesFirebase(m));
                if(retire) {
                    if(!retired(c.getType())&&c.getType().contains("/S800x480/"))throw new IllegalStateException("Unexpected Firebase dependency in gameplay: "+c.getType()+m.getName());
                    methods.add(unavailable(m));changed++;
                } else methods.add(m);
            }
            kept.add(new ImmutableClassDef(c.getType(),c.getAccessFlags(),c.getSuperclass(),c.getInterfaces(),c.getSourceFile(),c.getAnnotations(),c.getFields(),methods));
        }
        if(dropped==0)throw new IllegalStateException("Expected original Firebase definitions missing");
        DexPool pool=new DexPool(dex.getOpcodes());for(ClassDef c:kept)pool.internClass(c);
        MemoryDataStore data=new MemoryDataStore();pool.writeTo(data);
        byte[] after=Arrays.copyOf(data.getBuffer(),data.getSize());
        DexBackedDexFile verified=new DexBackedDexFile(dex.getOpcodes(),after);
        List<ClassDef> gameBefore=new ArrayList<>(),gameAfter=new ArrayList<>();
        for(ClassDef c:kept)if(c.getType().startsWith("Lcom/quanhoang/greenfarm3/S800x480/")&&!retired(c.getType()))gameBefore.add(c);
        for(ClassDef c:verified.getClasses()) {
            if(c.getType().startsWith("Lcom/quanhoang/greenfarm3/S800x480/")&&!retired(c.getType()))gameAfter.add(c);
            if(removed(c.getType()))throw new IllegalStateException("Firebase definition survived");
            for(Method m:c.getMethods())if(usesFirebase(m))throw new IllegalStateException("Firebase call survived: "+c.getType()+m.getName());
        }
        if(!Arrays.equals(RelocateGamePackage.semanticBytes(gameBefore,dex.getOpcodes()),RelocateGamePackage.semanticBytes(gameAfter,dex.getOpcodes())))
            throw new IllegalStateException("Offline SDK removal changed merged gameplay/storage bytecode");
        Files.createDirectories(output.toAbsolutePath().getParent());Files.write(output,after);
        String report="{\"schema_version\":1,\"input_sha256\":\""+sha256(before)+"\",\"output_sha256\":\""+sha256(after)+"\",\"firebase_classes_removed\":"+firebaseDropped+",\"retired_methods\":"+changed+",\"firebase_executable_references_remaining\":0,\"gameplay_storage_semantics_preserved\":true}";
        Files.write(Paths.get(args[1]+".json"),report.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        System.out.println(report);
    }
}
