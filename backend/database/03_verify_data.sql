-- Read-only checks for the selected v5 database.
-- Compare counts by table and provider to verified pre-upgrade backup counts.
-- Every row returned as failed_checks must have violations = 0.
-- Results contain counts and schema metadata only.

START TRANSACTION WITH CONSISTENT SNAPSHOT, READ ONLY;

SELECT version, name FROM gf_schema_migrations ORDER BY version;

SELECT provider, COUNT(*) AS account_count,
       SUM(game_id IS NOT NULL) AS accounts_with_game_id
FROM users GROUP BY provider ORDER BY provider;

SELECT account_status, COUNT(*) AS website_account_count
FROM gf_password_accounts GROUP BY account_status ORDER BY account_status;

SELECT object, row_count
FROM (
    SELECT 'users' AS object, COUNT(*) AS row_count FROM users
    UNION ALL SELECT 'gf_password_accounts', COUNT(*) FROM gf_password_accounts
    UNION ALL SELECT 'gf_auth_attempts', COUNT(*) FROM gf_auth_attempts
    UNION ALL SELECT 'gf_auth_codes', COUNT(*) FROM gf_auth_codes
    UNION ALL SELECT 'gf_auth_web_sessions', COUNT(*) FROM gf_auth_web_sessions
    UNION ALL SELECT 'gf_auth_sessions', COUNT(*) FROM gf_auth_sessions
    UNION ALL SELECT 'gf_auth_session_families', COUNT(*) FROM gf_auth_session_families
    UNION ALL SELECT 'gf_accounts', COUNT(*) FROM gf_accounts
    UNION ALL SELECT 'gf_sessions', COUNT(*) FROM gf_sessions
    UNION ALL SELECT 'gf_compliance', COUNT(*) FROM gf_compliance
) AS table_counts ORDER BY object;

SELECT COUNT(*) AS unexpired_unrevoked_access_sessions,
       COUNT(DISTINCT s.family_id) AS families_with_unexpired_unrevoked_access
FROM gf_auth_sessions s
JOIN gf_auth_session_families f ON f.id = s.family_id AND f.user_id = s.user_id
WHERE s.revoked_at IS NULL AND f.revoked_at IS NULL AND s.access_expires_at > UNIX_TIMESTAMP();

SELECT check_name, violations
FROM (
    SELECT 'migration_5_missing_or_wrong_checksum' AS check_name, 1 - COUNT(*) AS violations
    FROM gf_schema_migrations WHERE version = 5 AND name = '005_session_family_membership.sql'
      AND checksum = '2f71db65d41077a9a0c64a5ac22c1a39a9e4f725d26448bd9bc8b41ff67fd1c8'
    UNION ALL
    SELECT 'duplicate_normalized_usernames', COUNT(*) FROM (
        SELECT 1 FROM gf_password_accounts GROUP BY LOWER(TRIM(username)) HAVING COUNT(*) > 1
    ) AS duplicate_usernames
    UNION ALL
    SELECT 'duplicate_normalized_emails', COUNT(*) FROM (
        SELECT 1 FROM gf_password_accounts GROUP BY LOWER(TRIM(email)) HAVING COUNT(*) > 1
    ) AS duplicate_emails
    UNION ALL
    SELECT 'duplicate_game_ids', COUNT(*) FROM (
        SELECT 1 FROM users WHERE game_id IS NOT NULL GROUP BY game_id HAVING COUNT(*) > 1
    ) AS duplicate_game_ids
    UNION ALL SELECT 'missing_game_ids', COUNT(*) FROM users WHERE game_id IS NULL OR game_id = ''
    UNION ALL SELECT 'website_account_missing_credentials', COUNT(*)
        FROM users u LEFT JOIN gf_password_accounts p ON p.user_id = u.id
        WHERE u.provider = 'password' AND p.user_id IS NULL
    UNION ALL SELECT 'website_credentials_invalid_owner', COUNT(*)
        FROM gf_password_accounts p LEFT JOIN users u ON u.id = p.user_id
        WHERE u.id IS NULL OR u.provider <> 'password'
    UNION ALL SELECT 'non_argon2id_website_credentials', COUNT(*)
        FROM gf_password_accounts WHERE LEFT(password_hash, 10) <> '$argon2id$'
    UNION ALL SELECT 'session_orphaned_or_family_owner_mismatch', COUNT(*)
        FROM gf_auth_sessions s
        LEFT JOIN users u ON u.id = s.user_id
        LEFT JOIN gf_auth_session_families f ON f.id = s.family_id AND f.user_id = s.user_id
        WHERE u.id IS NULL OR f.id IS NULL
    UNION ALL SELECT 'session_family_fk_missing_or_wrong_columns', 2 - COUNT(*)
        FROM information_schema.key_column_usage
        WHERE table_schema = DATABASE() AND table_name = 'gf_auth_sessions'
          AND constraint_name = 'gf_auth_session_family_fk'
          AND referenced_table_schema = DATABASE() AND referenced_table_name = 'gf_auth_session_families'
          AND ((ordinal_position = 1 AND column_name = 'family_id' AND referenced_column_name = 'id')
            OR (ordinal_position = 2 AND column_name = 'user_id' AND referenced_column_name = 'user_id'))
    UNION ALL SELECT 'family_orphaned', COUNT(*)
        FROM gf_auth_session_families f LEFT JOIN users u ON u.id = f.user_id WHERE u.id IS NULL
    UNION ALL SELECT 'revoked_family_with_unrevoked_session', COUNT(*)
        FROM gf_auth_sessions s JOIN gf_auth_session_families f ON f.id = s.family_id
        WHERE f.revoked_at IS NOT NULL AND s.revoked_at IS NULL
    UNION ALL SELECT 'code_orphaned_or_provider_mismatch', COUNT(*)
        FROM gf_auth_codes c
        LEFT JOIN users u ON u.id = c.user_id
        LEFT JOIN gf_auth_attempts a ON a.id = c.attempt_id
        WHERE u.id IS NULL OR a.id IS NULL OR u.provider <> a.provider
    UNION ALL SELECT 'browser_session_orphaned_or_provider_mismatch', COUNT(*)
        FROM gf_auth_web_sessions w LEFT JOIN gf_auth_attempts a ON a.id = w.attempt_id
        WHERE a.id IS NULL OR a.provider <> 'password'
    UNION ALL SELECT 'legacy_session_orphaned', COUNT(*)
        FROM gf_sessions s LEFT JOIN gf_accounts a ON a.id = s.account_id WHERE a.id IS NULL
    UNION ALL SELECT 'legacy_compliance_orphaned', COUNT(*)
        FROM gf_compliance c LEFT JOIN gf_accounts a ON a.id = c.account_id WHERE a.id IS NULL
) AS failed_checks ORDER BY check_name;

-- Check FK definitions even when all tables are empty.
SELECT table_name, constraint_name, referenced_table_name,
       GROUP_CONCAT(column_name ORDER BY ordinal_position SEPARATOR ',') AS child_columns,
       GROUP_CONCAT(referenced_column_name ORDER BY ordinal_position SEPARATOR ',') AS parent_columns
FROM information_schema.key_column_usage
WHERE table_schema = DATABASE() AND referenced_table_name IS NOT NULL
GROUP BY table_name, constraint_name, referenced_table_name
ORDER BY table_name, constraint_name;

-- Only evaluate archived rows still present after cutover. New sessions
-- created under v5 have no row in the archive by design.
SET @greenfarm_verify_link_rows = IF(
    EXISTS (SELECT 1 FROM information_schema.tables
            WHERE table_schema = DATABASE() AND table_name = 'gf_auth_session_links'
              AND table_type = 'BASE TABLE'),
    'SELECT COUNT(*) AS archived_link_count, COUNT(CASE WHEN s.id IS NULL OR NOT (s.family_id <=> l.family_id) THEN 1 END) AS archive_membership_mismatches FROM gf_auth_session_links l LEFT JOIN gf_auth_sessions s ON s.id = l.session_id',
    'SELECT 0 AS archived_link_count, 0 AS archive_membership_mismatches'
);
PREPARE greenfarm_verify_link_rows FROM @greenfarm_verify_link_rows;
EXECUTE greenfarm_verify_link_rows;
DEALLOCATE PREPARE greenfarm_verify_link_rows;

COMMIT;
