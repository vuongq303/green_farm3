-- Giftcode metadata, immutable grants and administrative audit.
-- Existing account and session rows are preserved.
CREATE TABLE IF NOT EXISTS gf_giftcodes (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    code VARCHAR(48) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    rewards JSON NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    starts_at BIGINT UNSIGNED NULL,
    expires_at BIGINT UNSIGNED NULL,
    max_redemptions INT UNSIGNED NULL,
    redeemed_count INT UNSIGNED NOT NULL DEFAULT 0,
    created_at BIGINT UNSIGNED NOT NULL,
    updated_at BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY gf_giftcode_code (code),
    CONSTRAINT gf_giftcode_date_range CHECK (starts_at IS NULL OR expires_at IS NULL OR starts_at < expires_at),
    CONSTRAINT gf_giftcode_redemption_limit CHECK (max_redemptions IS NULL OR
        (max_redemptions > 0 AND redeemed_count <= max_redemptions))
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS gf_giftcode_redemptions (
    grant_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    giftcode_id BIGINT UNSIGNED NOT NULL,
    user_id BIGINT UNSIGNED NOT NULL,
    farm_id CHAR(36) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    rewards JSON NOT NULL,
    redeemed_at BIGINT UNSIGNED NOT NULL,
    applied_at BIGINT UNSIGNED NULL,
    PRIMARY KEY (grant_id),
    UNIQUE KEY gf_giftcode_once (giftcode_id, user_id),
    KEY gf_giftcode_pending (user_id, farm_id, applied_at, redeemed_at, grant_id),
    KEY gf_giftcode_history (giftcode_id, redeemed_at, grant_id),
    CONSTRAINT gf_giftcode_redemption_code_fk FOREIGN KEY (giftcode_id) REFERENCES gf_giftcodes (id),
    CONSTRAINT gf_giftcode_redemption_user_fk FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS gf_giftcode_audit (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    giftcode_id BIGINT UNSIGNED NOT NULL,
    actor VARCHAR(48) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    operation ENUM('create', 'update') NOT NULL,
    details JSON NOT NULL,
    created_at BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    KEY gf_giftcode_audit_history (giftcode_id, id),
    CONSTRAINT gf_giftcode_audit_code_fk FOREIGN KEY (giftcode_id) REFERENCES gf_giftcodes (id)
) ENGINE=InnoDB;
