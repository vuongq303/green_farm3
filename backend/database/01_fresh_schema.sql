-- MySQL 8.0.16+/8.4. Select a NEW empty database before running this file.
-- Use the mysql client in batch mode without --force.
-- For an existing installation run npm run migrate instead.

CREATE TEMPORARY TABLE gf_fresh_schema_guard (
    schema_is_empty TINYINT NOT NULL CHECK (schema_is_empty = 1)
);
INSERT INTO gf_fresh_schema_guard (schema_is_empty)
SELECT COUNT(*) = 0 FROM information_schema.tables WHERE table_schema = DATABASE();
DROP TEMPORARY TABLE gf_fresh_schema_guard;

-- Existing M0 has no persistent farm-save format: preserve its identities,
-- active sessions and compliance submissions without inventing game payloads.
CREATE TABLE gf_accounts (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    identity_hash BINARY(32) NOT NULL,
    client_id VARCHAR(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    credential VARCHAR(1034) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    password_salt VARBINARY(16) NOT NULL,
    password_hash VARBINARY(64) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id),
    UNIQUE KEY gf_accounts_identity (identity_hash)
) ENGINE=InnoDB;

CREATE TABLE gf_sessions (
    sid_hash BINARY(32) NOT NULL,
    account_id BIGINT UNSIGNED NOT NULL,
    device_id VARCHAR(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    issued_at BIGINT UNSIGNED NOT NULL,
    expires_at BIGINT UNSIGNED NOT NULL,
    refresh_expires_at BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (sid_hash),
    KEY gf_sessions_expiry (refresh_expires_at),
    KEY gf_sessions_account (account_id),
    CONSTRAINT gf_sessions_account_fk FOREIGN KEY (account_id) REFERENCES gf_accounts (id)
) ENGINE=InnoDB;

CREATE TABLE gf_compliance (
    account_id BIGINT UNSIGNED NOT NULL,
    country CHAR(2) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    age SMALLINT UNSIGNED NOT NULL,
    updated_at BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (account_id),
    CONSTRAINT gf_compliance_account_fk FOREIGN KEY (account_id) REFERENCES gf_accounts (id)
) ENGINE=InnoDB;

-- Authentication only. No farm payload, SQLite schema or legacy identity is changed.
CREATE TABLE users (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    game_id VARCHAR(24) CHARACTER SET ascii COLLATE ascii_bin NULL,
    provider ENUM('facebook', 'google', 'password') NOT NULL,
    provider_user_id VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    display_name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
    created_at BIGINT UNSIGNED NOT NULL,
    updated_at BIGINT UNSIGNED NOT NULL,
    last_login_at BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY users_game_id (game_id),
    UNIQUE KEY users_provider_identity (provider, provider_user_id)
) ENGINE=InnoDB;

CREATE TABLE gf_auth_attempts (
    id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    provider ENUM('facebook', 'google', 'password') NOT NULL,
    app_state VARCHAR(128) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    code_challenge CHAR(43) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    redirect_uri VARCHAR(255) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    oauth_state_hash BINARY(32) NULL,
    nonce VARCHAR(128) CHARACTER SET ascii COLLATE ascii_bin NULL,
    provider_verifier VARCHAR(128) CHARACTER SET ascii COLLATE ascii_bin NULL,
    created_at BIGINT UNSIGNED NOT NULL,
    expires_at BIGINT UNSIGNED NOT NULL,
    started_at BIGINT UNSIGNED NULL,
    consumed_at BIGINT UNSIGNED NULL,
    PRIMARY KEY (id),
    UNIQUE KEY gf_auth_attempt_state (oauth_state_hash),
    KEY gf_auth_attempt_expiry (expires_at)
) ENGINE=InnoDB;

CREATE TABLE gf_auth_codes (
    code_hash BINARY(32) NOT NULL,
    attempt_id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    user_id BIGINT UNSIGNED NOT NULL,
    code_challenge CHAR(43) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    redirect_uri VARCHAR(255) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    expires_at BIGINT UNSIGNED NOT NULL,
    used_at BIGINT UNSIGNED NULL,
    PRIMARY KEY (code_hash),
    UNIQUE KEY gf_auth_code_attempt (attempt_id),
    KEY gf_auth_code_expiry (expires_at),
    CONSTRAINT gf_auth_code_attempt_fk FOREIGN KEY (attempt_id) REFERENCES gf_auth_attempts (id),
    CONSTRAINT gf_auth_code_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE gf_auth_session_families (
    id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    user_id BIGINT UNSIGNED NOT NULL,
    created_at BIGINT UNSIGNED NOT NULL,
    revoked_at BIGINT UNSIGNED NULL,
    PRIMARY KEY (id),
    UNIQUE KEY gf_auth_family_identity (id, user_id),
    KEY gf_auth_family_user (user_id),
    CONSTRAINT gf_auth_family_user_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE gf_auth_sessions (
    id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    user_id BIGINT UNSIGNED NOT NULL,
    family_id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    access_hash BINARY(32) NOT NULL,
    refresh_hash BINARY(32) NOT NULL,
    issued_at BIGINT UNSIGNED NOT NULL,
    access_expires_at BIGINT UNSIGNED NOT NULL,
    refresh_expires_at BIGINT UNSIGNED NOT NULL,
    revoked_at BIGINT UNSIGNED NULL,
    PRIMARY KEY (id),
    UNIQUE KEY gf_auth_session_access (access_hash),
    UNIQUE KEY gf_auth_session_refresh (refresh_hash),
    KEY gf_auth_session_family (family_id, user_id),
    KEY gf_auth_session_user (user_id),
    KEY gf_auth_session_expiry (refresh_expires_at),
    CONSTRAINT gf_auth_session_user_fk FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT gf_auth_session_family_fk FOREIGN KEY (family_id, user_id)
        REFERENCES gf_auth_session_families (id, user_id)
) ENGINE=InnoDB;

CREATE TABLE gf_password_accounts (
    user_id BIGINT UNSIGNED NOT NULL,
    username VARCHAR(32) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    email VARCHAR(254) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    password_hash VARCHAR(512) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    account_status ENUM('active', 'disabled') NOT NULL DEFAULT 'active',
    created_at BIGINT UNSIGNED NOT NULL,
    updated_at BIGINT UNSIGNED NOT NULL,
    last_login_at BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (user_id),
    UNIQUE KEY gf_password_username (username),
    UNIQUE KEY gf_password_email (email),
    CONSTRAINT gf_password_user_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE gf_auth_web_sessions (
    cookie_hash BINARY(32) NOT NULL,
    attempt_id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    csrf_hash BINARY(32) NOT NULL,
    expires_at BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (cookie_hash),
    UNIQUE KEY gf_auth_web_attempt (attempt_id),
    KEY gf_auth_web_expiry (expires_at),
    CONSTRAINT gf_auth_web_attempt_fk FOREIGN KEY (attempt_id) REFERENCES gf_auth_attempts (id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- These ledger entries use the SHA-256 hashes of the exact numbered files.
-- This baseline already incorporates the effective schema of those migrations.
CREATE TABLE gf_schema_migrations (
    version INT UNSIGNED NOT NULL PRIMARY KEY,
    name VARCHAR(128) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    checksum CHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    applied_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
) ENGINE=InnoDB;

INSERT INTO gf_schema_migrations (version, name, checksum) VALUES
    (1, '001_accounts_sessions.sql', 'c07b9db9a92928b65d3143d1142ce83c82f8d73ca66304f7203ad2058c081515'),
    (2, '002_social_auth.sql', 'bcd6791d26b6db50f3f74b7bf8f69b1ebd5b3451c8042843377b1c1e8c7b02c9'),
    (3, '003_auth_session_families.sql', 'df26c17431d1efeadd03bd3e319a1f6d681b038865e3100482cb4f7d303171de'),
    (4, '004_password_accounts.sql', '046b6f548ea055fe8ff0e702b68c4d5549c263d7da0e9e51f8d9c8b8d83d8bea'),
    (5, '005_session_family_membership.sql', '2f71db65d41077a9a0c64a5ac22c1a39a9e4f725d26448bd9bc8b41ff67fd1c8');
