-- MANUAL ONLY. Confirm a restore-tested backup, stop all writers, verify v5
-- traffic and integrity checks, and obtain DBA approval before enabling.
-- Run this file over a dedicated DBA connection, with a selected database.
-- The default invocation drops nothing. See 05_rollback_plan.md.
--
-- Set both session variables before sourcing this file to authorize cleanup:
-- SET @greenfarm_cleanup_database = '<verified database name>';
-- SET @greenfarm_cleanup_confirmation = 'BACKUP_RESTORED_AND_CUTOVER_VERIFIED';
--
-- Ongoing v5 writes do not update the retired link archive. The family_id
-- column is the sole source of membership after cutover.

SET @greenfarm_cleanup_ack = COALESCE(
    BINARY @greenfarm_cleanup_database = BINARY DATABASE()
    AND BINARY @greenfarm_cleanup_confirmation = BINARY 'BACKUP_RESTORED_AND_CUTOVER_VERIFIED', 0
);
SET @greenfarm_cleanup_database = NULL;
SET @greenfarm_cleanup_confirmation = NULL;
SET @greenfarm_cleanup_allowed = 0;

SET @greenfarm_link_table_present = EXISTS (
    SELECT 1 FROM information_schema.tables
    WHERE table_schema = DATABASE() AND table_name = 'gf_auth_session_links' AND table_type = 'BASE TABLE'
);
SET @greenfarm_target_schema_ready = (
    EXISTS (SELECT 1 FROM information_schema.columns
            WHERE table_schema = DATABASE() AND table_name = 'gf_auth_sessions'
              AND column_name = 'family_id' AND is_nullable = 'NO')
    AND 2 = (SELECT COUNT(*) FROM information_schema.key_column_usage
             WHERE table_schema = DATABASE() AND table_name = 'gf_auth_sessions'
               AND constraint_name = 'gf_auth_session_family_fk'
               AND referenced_table_schema = DATABASE()
               AND referenced_table_name = 'gf_auth_session_families'
               AND ((ordinal_position = 1 AND column_name = 'family_id' AND referenced_column_name = 'id')
                 OR (ordinal_position = 2 AND column_name = 'user_id' AND referenced_column_name = 'user_id')))
    AND NOT EXISTS (SELECT 1 FROM information_schema.key_column_usage
                    WHERE referenced_table_schema = DATABASE()
                      AND referenced_table_name = 'gf_auth_session_links')
);

-- These queries are prepared only after structural preconditions have passed.
-- If any check errors, cleanup_allowed keeps its initial value of zero.
SET @greenfarm_cleanup_check = IF(
    COALESCE(@greenfarm_cleanup_ack AND @greenfarm_link_table_present AND @greenfarm_target_schema_ready, 0),
    'SELECT (EXISTS (SELECT 1 FROM gf_schema_migrations WHERE version = 5 AND name = ''005_session_family_membership.sql'' AND checksum = ''2f71db65d41077a9a0c64a5ac22c1a39a9e4f725d26448bd9bc8b41ff67fd1c8'') AND NOT EXISTS (SELECT 1 FROM gf_auth_session_links l LEFT JOIN gf_auth_sessions s ON s.id = l.session_id WHERE s.id IS NULL OR NOT (s.family_id <=> l.family_id))) INTO @greenfarm_cleanup_allowed',
    'SELECT ''Cleanup preconditions were not met. No table will be dropped.'' AS result'
);
PREPARE greenfarm_cleanup_check FROM @greenfarm_cleanup_check;
EXECUTE greenfarm_cleanup_check;
DEALLOCATE PREPARE greenfarm_cleanup_check;

SET @greenfarm_cleanup_statement = IF(
    COALESCE(@greenfarm_cleanup_allowed, 0) = 1,
    'DROP TABLE gf_auth_session_links',
    'SELECT ''Skipped gf_auth_session_links cleanup.'' AS result'
);
PREPARE greenfarm_cleanup_statement FROM @greenfarm_cleanup_statement;
EXECUTE greenfarm_cleanup_statement;
DEALLOCATE PREPARE greenfarm_cleanup_statement;
SET @greenfarm_cleanup_allowed = 0;

SELECT COUNT(*) AS retired_link_table_present
FROM information_schema.tables
WHERE table_schema = DATABASE() AND table_name = 'gf_auth_session_links' AND table_type = 'BASE TABLE';
