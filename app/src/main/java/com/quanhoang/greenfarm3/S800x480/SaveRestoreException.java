package com.quanhoang.greenfarm3.S800x480;

/** Restore diagnostics contain no player payload, account identifier or token. */
public class SaveRestoreException extends IllegalStateException {
    public enum Code {
        SAVE_NOT_FOUND, SQLITE_OPEN_FAILED, SQLITE_READ_FAILED, SQLITE_WRITE_FAILED,
        CHECKSUM_MISMATCH, SAVE_DECODE_FAILED, SAVE_VERSION_UNSUPPORTED,
        IDENTITY_MISMATCH, ENGINE_RESTORE_FAILED, RESTORE_SUCCESS
    }

    private final Code code;

    public SaveRestoreException(Code code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public Code getCode() { return code; }

    public static Code classify(Throwable failure, Code fallback) {
        // Keep the original, most specific diagnosis through storage wrappers.
        for (int depth = 0; failure != null && depth < 32; depth++, failure = failure.getCause()) {
            if (failure instanceof SaveRestoreException) return ((SaveRestoreException) failure).getCode();
        }
        return fallback;
    }

    /** Metadata only: Throwable messages can contain player/account payloads. */
    public static String diagnostics(Throwable failure, Code fallback) {
        StringBuilder result = new StringBuilder("result=").append(classify(failure, fallback));
        java.util.IdentityHashMap<Throwable, Boolean> seen = new java.util.IdentityHashMap<>();
        for (int depth = 0; failure != null && depth < 8; depth++, failure = failure.getCause()) {
            if (seen.put(failure, Boolean.TRUE) != null) {
                result.append(" cause_cycle=true");
                break;
            }
            result.append(" cause").append(depth).append('=').append(safeSymbol(failure.getClass().getName()));
            StackTraceElement[] frames = failure.getStackTrace();
            for (int frame = 0; frame < Math.min(frames.length, 2); frame++) {
                result.append(" at").append(depth).append('_').append(frame).append('=')
                        .append(safeSymbol(frames[frame].getClassName())).append('.')
                        .append(safeSymbol(frames[frame].getMethodName())).append(':')
                        .append(frames[frame].getLineNumber());
            }
        }
        return result.toString();
    }

    /** The logical records used by known critical load steps, never player paths. */
    public static String recordsForLoadStep(int step) {
        switch (step) {
            case 38: return "RMSHeader";
            case 44: return "save_gameplay";
            case 47: return "save_farm0,pending_rewards_v1,entity_pose_v1";
            default: return "unknown";
        }
    }

    private static String safeSymbol(String value) {
        // Exception types and methods are useful; filenames and free-form text
        // are deliberately omitted, including messages on wrapped causes.
        String safe = value.replaceAll("[^A-Za-z0-9_.$<>]", "_");
        return safe.substring(0, Math.min(safe.length(), 180));
    }
}
