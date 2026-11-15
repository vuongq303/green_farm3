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
}
