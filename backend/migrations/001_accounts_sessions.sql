-- Existing M0 has no persistent farm-save format: preserve its identities,
-- active sessions and compliance submissions without inventing game payloads.
CREATE TABLE IF NOT EXISTS gf_accounts (
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

CREATE TABLE IF NOT EXISTS gf_sessions (
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

CREATE TABLE IF NOT EXISTS gf_compliance (
    account_id BIGINT UNSIGNED NOT NULL,
    country CHAR(2) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    age SMALLINT UNSIGNED NOT NULL,
    updated_at BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (account_id),
    CONSTRAINT gf_compliance_account_fk FOREIGN KEY (account_id) REFERENCES gf_accounts (id)
) ENGINE=InnoDB;
