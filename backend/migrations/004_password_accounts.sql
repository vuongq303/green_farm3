-- Extend authentication identities only. Existing provider rows and game IDs
-- remain unchanged and gameplay data is never read by these tables.
ALTER TABLE users MODIFY COLUMN provider ENUM('facebook', 'google', 'password') NOT NULL;
ALTER TABLE gf_auth_attempts MODIFY COLUMN provider ENUM('facebook', 'google', 'password') NOT NULL;

CREATE TABLE IF NOT EXISTS gf_password_accounts (
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

CREATE TABLE IF NOT EXISTS gf_auth_web_sessions (
    cookie_hash BINARY(32) NOT NULL,
    attempt_id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    csrf_hash BINARY(32) NOT NULL,
    expires_at BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (cookie_hash),
    UNIQUE KEY gf_auth_web_attempt (attempt_id),
    KEY gf_auth_web_expiry (expires_at),
    CONSTRAINT gf_auth_web_attempt_fk FOREIGN KEY (attempt_id) REFERENCES gf_auth_attempts (id) ON DELETE CASCADE
) ENGINE=InnoDB;
