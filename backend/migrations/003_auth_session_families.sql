-- Add a persistent revocation boundary shared by every refresh rotation.
-- Static additive statements and INSERT IGNORE make interrupted migration retry safe.
CREATE TABLE IF NOT EXISTS gf_auth_session_families (
    id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    user_id BIGINT UNSIGNED NOT NULL,
    created_at BIGINT UNSIGNED NOT NULL,
    revoked_at BIGINT UNSIGNED NULL,
    PRIMARY KEY (id),
    KEY gf_auth_family_user (user_id),
    CONSTRAINT gf_auth_family_user_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS gf_auth_session_links (
    session_id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    family_id VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
    PRIMARY KEY (session_id),
    KEY gf_auth_link_family (family_id),
    CONSTRAINT gf_auth_link_session_fk FOREIGN KEY (session_id) REFERENCES gf_auth_sessions (id) ON DELETE CASCADE,
    CONSTRAINT gf_auth_link_family_fk FOREIGN KEY (family_id) REFERENCES gf_auth_session_families (id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Older schemas did not retain refresh lineage. Preserve those stored sessions
-- without inventing relationships between independent account logins.
INSERT IGNORE INTO gf_auth_session_families (id, user_id, created_at, revoked_at)
    SELECT id, user_id, issued_at, revoked_at FROM gf_auth_sessions;

INSERT IGNORE INTO gf_auth_session_links (session_id, family_id)
    SELECT id, id FROM gf_auth_sessions;
