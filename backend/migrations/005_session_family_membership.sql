-- Run with all backend writers stopped. Existing hashes, token lifetimes and
-- revocation timestamps stay in their original rows. Keep the legacy links
-- for verification and recovery until an operator approves cleanup.

SET @gf_ddl = IF(EXISTS(
    SELECT 1 FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'gf_auth_sessions' AND COLUMN_NAME = 'family_id'
), 'DO 0',
    'ALTER TABLE gf_auth_sessions ADD COLUMN family_id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NULL AFTER user_id'
);
PREPARE gf_session_ddl FROM @gf_ddl;
EXECUTE gf_session_ddl;
DEALLOCATE PREPARE gf_session_ddl;

UPDATE gf_auth_sessions s
INNER JOIN gf_auth_session_links l ON l.session_id = s.id
INNER JOIN gf_auth_session_families f ON f.id = l.family_id AND f.user_id = s.user_id
SET s.family_id = l.family_id
WHERE NOT (s.family_id <=> l.family_id);

-- Reject missing or cross-account links even when the server is configured
-- with a non-strict SQL mode. Invalid JSON raises an error without exposing rows.
SELECT JSON_EXTRACT(IF(EXISTS(
    SELECT 1 FROM gf_auth_sessions s
    LEFT JOIN gf_auth_session_links l ON l.session_id = s.id AND l.family_id = s.family_id
    LEFT JOIN gf_auth_session_families f ON f.id = s.family_id AND f.user_id = s.user_id
    WHERE l.session_id IS NULL OR f.id IS NULL
), 'Migration 005 requires a valid legacy family for every session', 'null'), '$');

SET @gf_ddl = IF(EXISTS(
    SELECT 1 FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'gf_auth_sessions' AND COLUMN_NAME = 'family_id'
        AND IS_NULLABLE = 'YES'
), 'ALTER TABLE gf_auth_sessions MODIFY COLUMN family_id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL',
    'DO 0'
);
PREPARE gf_session_ddl FROM @gf_ddl;
EXECUTE gf_session_ddl;
DEALLOCATE PREPARE gf_session_ddl;

SET @gf_ddl = IF(EXISTS(
    SELECT 1 FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'gf_auth_session_families'
        AND INDEX_NAME = 'gf_auth_family_identity'
), 'DO 0',
    'ALTER TABLE gf_auth_session_families ADD UNIQUE KEY gf_auth_family_identity (id, user_id)'
);
PREPARE gf_session_ddl FROM @gf_ddl;
EXECUTE gf_session_ddl;
DEALLOCATE PREPARE gf_session_ddl;

SET @gf_ddl = IF(EXISTS(
    SELECT 1 FROM information_schema.TABLE_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE() AND TABLE_NAME = 'gf_auth_sessions'
        AND CONSTRAINT_NAME = 'gf_auth_session_family_fk' AND CONSTRAINT_TYPE = 'FOREIGN KEY'
), 'DO 0',
    'ALTER TABLE gf_auth_sessions ADD KEY gf_auth_session_family (family_id, user_id), ADD CONSTRAINT gf_auth_session_family_fk FOREIGN KEY (family_id, user_id) REFERENCES gf_auth_session_families (id, user_id)'
);
PREPARE gf_session_ddl FROM @gf_ddl;
EXECUTE gf_session_ddl;
DEALLOCATE PREPARE gf_session_ddl;
