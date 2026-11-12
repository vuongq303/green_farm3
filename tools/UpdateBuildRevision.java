import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/** Regenerate one build-size property; keep the original runtime integrity guard. */
public final class UpdateBuildRevision {
    // Public format constants from wrapper.au.bih, wrapper.h and Pt.EW in the APK.
    private static final String FORMAT_SEED = "19GmLfT99";
    private static final long SIZE_XOR = 0x000226bcL;
    private static final Pattern REVISION = Pattern.compile(
            "(^|[\\r\\n])([ \\t]*REVISION-2408[ \\t]*:[ \\t]*)([0-9]+)(?=[ \\t]*(?:[\\r\\n]|$))");
    private static final Pattern REVISION_KEY = Pattern.compile(
            "(^|[\\r\\n])[ \\t]*REVISION-2408[ \\t]*:");

    private static String hash(byte[] bytes) throws Exception {
        StringBuilder result = new StringBuilder();
        for (byte value : MessageDigest.getInstance("SHA-256").digest(bytes))
            result.append(String.format("%02x", value & 255));
        return result.toString();
    }

    private static byte[] crypt(int mode, byte[] bytes) throws Exception {
        byte[] key = hash(FORMAT_SEED.getBytes(StandardCharsets.UTF_8))
                .substring(0, 16).getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(mode, new SecretKeySpec(key, "AES"));
        return cipher.doFinal(bytes);
    }

    private static Matcher oneRevision(String definition) {
        Matcher keys = REVISION_KEY.matcher(definition);
        int count = 0;
        while (keys.find()) count++;
        if (count != 1) throw new IllegalArgumentException("Expected exactly one revision property key");
        Matcher match = REVISION.matcher(definition);
        if (!match.find()) throw new IllegalArgumentException("Revision property missing");
        int start = match.start();
        if (match.find()) throw new IllegalArgumentException("Duplicate revision property");
        match.reset();
        if (!match.find(start)) throw new IllegalStateException("Revision location changed");
        return match;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4)
            throw new IllegalArgumentException("Usage: UpdateBuildRevision input.bin output.bin originalDexBytes newDexBytes");
        Path input = Paths.get(args[0]).toAbsolutePath().normalize();
        Path output = Paths.get(args[1]).toAbsolutePath().normalize();
        if (input.equals(output) || (Files.exists(output) && Files.isSameFile(input, output)))
            throw new IllegalArgumentException("Do not overwrite original build metadata");
        long originalSize = Long.parseLong(args[2]);
        long rebuiltSize = Long.parseLong(args[3]);
        if (originalSize <= 0 || rebuiltSize <= 0) throw new IllegalArgumentException("Invalid DEX size");
        byte[] encoded = Files.readAllBytes(input);
        byte[] ciphertext = Base64.getMimeDecoder().decode(encoded);
        byte[] plain = crypt(Cipher.DECRYPT_MODE, ciphertext);
        if (!Arrays.equals(ciphertext, crypt(Cipher.ENCRYPT_MODE, plain)))
            throw new IllegalArgumentException("Original metadata crypto round-trip failed");
        // Latin-1 is a byte-preserving carrier, not a re-encoding of other values.
        String definition = new String(plain, StandardCharsets.ISO_8859_1);
        Matcher match = oneRevision(definition);
        long originalRevision = Long.parseLong(match.group(3));
        if ((originalRevision ^ SIZE_XOR) != originalSize)
            throw new IllegalArgumentException("Original revision does not match the original DEX size");
        long revision = rebuiltSize ^ SIZE_XOR;
        String updated = definition.substring(0, match.start(3)) + revision
                + definition.substring(match.end(3));
        byte[] changed = updated.getBytes(StandardCharsets.ISO_8859_1);
        byte[] resultCiphertext = crypt(Cipher.ENCRYPT_MODE, changed);
        byte[] result = Base64.getEncoder().encode(resultCiphertext);
        if (!Arrays.equals(changed, crypt(Cipher.DECRYPT_MODE, resultCiphertext)))
            throw new IllegalArgumentException("Generated metadata crypto round-trip failed");
        Matcher updatedMatch = oneRevision(updated);
        String beforeOther = definition.substring(0, match.start(3)) + definition.substring(match.end(3));
        String afterOther = updated.substring(0, updatedMatch.start(3)) + updated.substring(updatedMatch.end(3));
        if (!beforeOther.equals(afterOther) || (Long.parseLong(updatedMatch.group(3)) ^ SIZE_XOR) != rebuiltSize)
            throw new IllegalArgumentException("Generated revision changed unrelated metadata or failed original guard");
        Files.createDirectories(output.getParent());
        Files.write(output, result);
        String report = "{\n"
                + "  \"property\": \"REVISION-2408\",\n"
                + "  \"original_dex_bytes\": " + originalSize + ",\n"
                + "  \"rebuilt_dex_bytes\": " + rebuiltSize + ",\n"
                + "  \"original_revision\": " + originalRevision + ",\n"
                + "  \"rebuilt_revision\": " + revision + ",\n"
                + "  \"size_xor\": " + SIZE_XOR + ",\n"
                + "  \"original_metadata_sha256\": \"" + hash(encoded) + "\",\n"
                + "  \"generated_metadata_sha256\": \"" + hash(result) + "\",\n"
                + "  \"other_definition_bytes_preserved\": true,\n"
                + "  \"original_guard_accepts_generated_revision\": true\n"
                + "}\n";
        Files.write(Paths.get(output + ".json"), report.getBytes(StandardCharsets.UTF_8));
        System.out.print(report);
    }
}
