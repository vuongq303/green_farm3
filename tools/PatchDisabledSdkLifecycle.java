import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.MethodImplementation;
import org.jf.dexlib2.iface.instruction.Instruction;
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
import org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction;
import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
import org.jf.dexlib2.iface.reference.MethodReference;
import org.jf.dexlib2.immutable.ImmutableClassDef;
import org.jf.dexlib2.immutable.ImmutableMethod;
import org.jf.dexlib2.immutable.ImmutableMethodImplementation;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction10x;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction11n;

/** Disable only verified social/ad lifecycle dispatches, preserving real runtime classes. */
public final class PatchDisabledSdkLifecycle {
    static final String OWNER = "Ljavax/microedition/midlet/MIDlet;";
    static final String FACEBOOK = "Ljavax/microedition/midlet/v;";
    static final String ADS = "Ljavax/microedition/midlet/o;";
    static final String ADS_NATIVE = "Lcom/gameloft/adsmanager/AdsManager;";
    static final String BACK = "onKeyUp(ILandroid/view/KeyEvent;)Z";
    static final List<String> LIFECYCLE = Collections.unmodifiableList(Arrays.asList(
        "onActivityResult(IILandroid/content/Intent;)V", "onConfigurationChanged(Landroid/content/res/Configuration;)V",
        "onCreate(Landroid/os/Bundle;)V", "onDestroy()V", "onNewIntent(Landroid/content/Intent;)V",
        "onPause()V", "onRestart()V", "onResume()V", "onSaveInstanceState(Landroid/os/Bundle;)V",
        "onStart()V", "onStop()V", "onWindowFocusChanged(Z)V"));
    private PatchDisabledSdkLifecycle() {}

    static String signature(MethodReference method) {
        StringBuilder result = new StringBuilder(method.getName()).append('(');
        for (CharSequence type : method.getParameterTypes()) result.append(type);
        return result.append(')').append(method.getReturnType()).toString();
    }
    static MethodReference reference(Instruction instruction) {
        if (!(instruction instanceof ReferenceInstruction)) return null;
        Object value = ((ReferenceInstruction) instruction).getReference();
        return value instanceof MethodReference ? (MethodReference) value : null;
    }
    static final class Edit {
        final int offset, register;
        final List<Instruction> original;
        Edit(int offset, List<Instruction> original, int register) {
            this.offset = offset; this.original = original; this.register = register;
        }
        int width() { int width = 0; for (Instruction i : original) width += i.getCodeUnits(); return width; }
        List<Instruction> disabled() {
            List<Instruction> result = new ArrayList<>();
            for (int n = 0; n < 3; n++) result.add(new ImmutableInstruction10x(Opcode.NOP));
            if (register >= 0) result.add(new ImmutableInstruction11n(Opcode.CONST_4, register, 0));
            return result;
        }
    }

    /** Fail closed if the retained runtime differs from all 27 reviewed sites. */
    static Map<String, List<Edit>> plan(ClassDef definition) throws IOException {
        if (definition == null || !definition.getType().equals(OWNER)) throw new IOException("Missing original MIDlet runtime");
        Map<String, Integer> counts = new TreeMap<>();
        for (String owner : Arrays.asList(FACEBOOK, ADS)) for (String method : LIFECYCLE) counts.put(owner + "->" + method, 0);
        counts.put("fullscreen-query", 0); counts.put("ad-back-handler", 0);
        Map<String, List<Edit>> result = new TreeMap<>();
        for (Method method : definition.getMethods()) {
            if (method.getImplementation() == null) continue;
            String caller = signature(method);
            List<Instruction> instructions = new ArrayList<>();
            for (Instruction i : method.getImplementation().getInstructions()) instructions.add(i);
            int offset = 0;
            List<Edit> edits = new ArrayList<>();
            for (int index = 0; index < instructions.size(); index++) {
                Instruction instruction = instructions.get(index);
                MethodReference ref = reference(instruction);
                if (ref == null) { offset += instruction.getCodeUnits(); continue; }
                String target = signature(ref), owner = ref.getDefiningClass();
                if (owner.equals(FACEBOOK) || owner.equals(ADS) || owner.equals(ADS_NATIVE)) {
                    if (!(instruction.getOpcode() == Opcode.INVOKE_STATIC || instruction.getOpcode() == Opcode.INVOKE_STATIC_RANGE)
                            || instruction.getCodeUnits() != 3) throw new IOException("Unreviewed SDK invoke encoding");
                    if (owner.equals(FACEBOOK) || owner.equals(ADS)) {
                        String key = owner + "->" + target;
                        if (!counts.containsKey(key) || !caller.equals(target)) throw new IOException("SDK lifecycle dispatch outside reviewed caller: " + caller);
                        counts.put(key, counts.get(key) + 1);
                        edits.add(new Edit(offset, Collections.singletonList(instruction), -1));
                    } else {
                        if (!caller.equals(BACK)) throw new IOException("Ad native invocation outside reviewed back handler");
                        if (target.equals("IsFullScreenAdOnScreen()Z")) {
                            if (index + 1 >= instructions.size()) throw new IOException("Missing native query result");
                            Instruction next = instructions.get(index + 1);
                            if (next.getOpcode() != Opcode.MOVE_RESULT || next.getCodeUnits() != 1
                                    || ((OneRegisterInstruction) next).getRegisterA() > 15) throw new IOException("Unreviewed native query result register");
                            counts.put("fullscreen-query", counts.get("fullscreen-query") + 1);
                            edits.add(new Edit(offset, Arrays.asList(instruction, next), ((OneRegisterInstruction) next).getRegisterA()));
                        } else if (target.equals("HandleBackKey()V")) {
                            counts.put("ad-back-handler", counts.get("ad-back-handler") + 1);
                            edits.add(new Edit(offset, Collections.singletonList(instruction), -1));
                        } else throw new IOException("Unreviewed native ads call: " + target);
                    }
                }
                offset += instruction.getCodeUnits();
            }
            if (!edits.isEmpty()) result.put(caller, edits);
        }
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            int expected = entry.getKey().equals("fullscreen-query") ? 2 : 1;
            if (entry.getValue() != expected) throw new IOException("Expected " + expected + " reviewed SDK sites for " + entry.getKey() + "; found " + entry.getValue());
        }
        return result;
    }

    static ClassDef apply(ClassDef definition, Map<String, List<Edit>> edits, boolean reverse) throws IOException {
        List<Method> methods = new ArrayList<>();
        for (Method method : definition.getMethods()) {
            List<Edit> changes = edits.get(signature(method));
            if (changes == null) { methods.add(method); continue; }
            MethodImplementation body = method.getImplementation();
            List<Instruction> input = new ArrayList<>(), output = new ArrayList<>();
            for (Instruction i : body.getInstructions()) input.add(i);
            Map<Integer, Edit> byOffset = new TreeMap<>();
            for (Edit edit : changes) byOffset.put(edit.offset, edit);
            int offset = 0, seen = 0;
            for (int index = 0; index < input.size();) {
                Edit edit = byOffset.get(offset);
                if (edit == null) { Instruction i = input.get(index++); output.add(i); offset += i.getCodeUnits(); continue; }
                List<Instruction> expected = reverse ? edit.disabled() : edit.original;
                for (Instruction wanted : expected) {
                    if (index >= input.size()) throw new IOException("SDK edit extends beyond method");
                    Instruction actual = input.get(index++);
                    if (actual.getOpcode() != wanted.getOpcode() || actual.getCodeUnits() != wanted.getCodeUnits()
                            || (actual instanceof OneRegisterInstruction && wanted instanceof OneRegisterInstruction
                                && ((OneRegisterInstruction) actual).getRegisterA() != ((OneRegisterInstruction) wanted).getRegisterA())
                            || (actual instanceof NarrowLiteralInstruction && wanted instanceof NarrowLiteralInstruction
                                && ((NarrowLiteralInstruction) actual).getNarrowLiteral() != ((NarrowLiteralInstruction) wanted).getNarrowLiteral())) {
                        throw new IOException("SDK edit read-back differs at " + signature(method) + ":" + offset);
                    }
                }
                output.addAll(reverse ? edit.original : edit.disabled());
                offset += edit.width(); seen++;
            }
            if (seen != changes.size()) throw new IOException("Missing SDK edit sites after serialization");
            methods.add(new ImmutableMethod(method.getDefiningClass(), method.getName(), method.getParameters(),
                    method.getReturnType(), method.getAccessFlags(), method.getAnnotations(), method.getHiddenApiRestrictions(),
                    new ImmutableMethodImplementation(body.getRegisterCount(), output, body.getTryBlocks(), body.getDebugItems())));
        }
        return new ImmutableClassDef(definition.getType(), definition.getAccessFlags(), definition.getSuperclass(),
                definition.getInterfaces(), definition.getSourceFile(), definition.getAnnotations(), definition.getFields(), methods);
    }
    static Map<String, ClassDef> classes(byte[] data, Opcodes opcodes) throws IOException {
        Map<String, ClassDef> result = new TreeMap<>();
        for (ClassDef c : new DexBackedDexFile(opcodes, data).getClasses()) if (result.put(c.getType(), c) != null) throw new IOException("Duplicate DEX class");
        return result;
    }
    static String sha256(byte[] data) throws Exception {
        StringBuilder result = new StringBuilder();
        for (byte value : MessageDigest.getInstance("SHA-256").digest(data)) result.append(String.format("%02x", value & 255));
        return result.toString();
    }
    static void publish(Path destination, byte[] bytes) throws IOException {
        Files.createDirectories(destination.getParent()); Path temp = Files.createTempFile(destination.getParent(), ".sdk-policy-", ".tmp");
        try {
            Files.write(temp, bytes);
            Files.move(temp, destination, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
        } finally { Files.deleteIfExists(temp); }
    }
    public static void main(String[] args) throws Exception {
        if (args.length != 2) throw new IllegalArgumentException("Usage: PatchDisabledSdkLifecycle input.dex output.dex");
        Path input = Paths.get(args[0]).toRealPath(), output = Paths.get(args[1]).toAbsolutePath().normalize();
        Path report = output.resolveSibling(output.getFileName() + ".json");
        for (Path destination : Arrays.asList(output, report)) {
            if (input.equals(destination) || (Files.exists(destination) && Files.isSameFile(input, destination))) throw new IOException("SDK policy input/output/report must be separate");
        }
        byte[] before = Files.readAllBytes(input);
        if (before.length < 112 || !new String(before, 0, 4, StandardCharsets.US_ASCII).equals("dex\n")) throw new IOException("Expected standard DEX");
        Opcodes ops = Opcodes.forDexVersion(Integer.parseInt(new String(before, 4, 3, StandardCharsets.US_ASCII)));
        if (ops == null) throw new IOException("Unsupported DEX version");
        Map<String, ClassDef> original = classes(before, ops), modified = new TreeMap<>(original);
        Map<String, List<Edit>> edits = plan(original.get(OWNER));
        modified.put(OWNER, apply(original.get(OWNER), edits, false));
        byte[] bytes = RelocateGamePackage.serialize(modified.values(), ops);
        Map<String, ClassDef> actual = classes(bytes, ops);
        if (!original.keySet().equals(actual.keySet())) throw new IOException("SDK policy changed class definitions");
        for (Method method : actual.get(OWNER).getMethods()) if (method.getImplementation() != null) {
            for (Instruction instruction : method.getImplementation().getInstructions()) {
                MethodReference ref = reference(instruction);
                if (ref != null && Arrays.asList(FACEBOOK, ADS, ADS_NATIVE).contains(ref.getDefiningClass())) {
                    throw new IOException("Disabled SDK dispatch remains after serialization");
                }
            }
        }
        ClassDef restored = apply(actual.get(OWNER), edits, true);
        if (!Arrays.equals(RelocateGamePackage.semanticBytes(Collections.singletonList(original.get(OWNER)), ops),
                RelocateGamePackage.semanticBytes(Collections.singletonList(restored), ops))) throw new IOException("SDK policy inverse semantics mismatch");
        Map<String, ClassDef> oldOthers = new TreeMap<>(original), newOthers = new TreeMap<>(actual);
        oldOthers.remove(OWNER); newOthers.remove(OWNER);
        byte[] canonical = RelocateGamePackage.semanticBytes(oldOthers.values(), ops);
        if (!Arrays.equals(canonical, RelocateGamePackage.semanticBytes(newOthers.values(), ops))) throw new IOException("SDK policy changed another class");
        String json = "{\n  \"schema_version\": 1,\n  \"policy\": \"facebook-and-ads-lifecycle-disabled\",\n"
            + "  \"input_dex_sha256\": \"" + sha256(before) + "\",\n  \"output_dex_sha256\": \"" + sha256(bytes) + "\",\n"
            + "  \"target_class\": \"" + OWNER + "\",\n  \"facebook_lifecycle_calls_disabled\": 12,\n"
            + "  \"ads_lifecycle_calls_disabled\": 12,\n  \"native_fullscreen_queries_disabled\": 2,\n"
            + "  \"native_back_handlers_disabled\": 1,\n  \"instruction_widths_preserved\": true,\n"
            + "  \"inverse_target_semantics_verified\": true,\n  \"unchanged_other_classes_verified\": true,\n"
            + "  \"unchanged_classes_canonical_sha256\": \"" + sha256(canonical) + "\"\n}\n";
        publish(output, bytes); publish(report, json.getBytes(StandardCharsets.UTF_8)); System.out.print(json);
    }
}
