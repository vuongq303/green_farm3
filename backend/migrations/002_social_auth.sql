-- Authentication only. No farm payload, SQLite schema or legacy identity is changed.
CREATE TABLE IF NOT EXISTS users (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    game_id VARCHAR(24) CHARACTER SET ascii COLLATE ascii_bin NULL,
    provider ENUM('facebook', 'google') NOT NULL,
    provider_user_id VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
    display_name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
    created_at BIGINT UNSIGNED NOT NULL,
    updated_at BIGINT UNSIGNED NOT NULL,
    last_login_at BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY users_game_id (game_id),
    UNIQUE KEY users_provider_identity (provider, provider_user_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS gf_auth_attempts (
    id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    provider ENUM('facebook', 'google') NOT NULL,
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

CREATE TABLE IF NOT EXISTS gf_auth_codes (
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

CREATE TABLE IF NOT EXISTS gf_auth_sessions (
    id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    user_id BIGINT UNSIGNED NOT NULL,
    access_hash BINARY(32) NOT NULL,
    refresh_hash BINARY(32) NOT NULL,
    issued_at BIGINT UNSIGNED NOT NULL,
    access_expires_at BIGINT UNSIGNED NOT NULL,
    refresh_expires_at BIGINT UNSIGNED NOT NULL,
    revoked_at BIGINT UNSIGNED NULL,
    PRIMARY KEY (id),
    UNIQUE KEY gf_auth_session_access (access_hash),
    UNIQUE KEY gf_auth_session_refresh (refresh_hash),
    KEY gf_auth_session_user (user_id),
    KEY gf_auth_session_expiry (refresh_expires_at),
    CONSTRAINT gf_auth_session_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;
