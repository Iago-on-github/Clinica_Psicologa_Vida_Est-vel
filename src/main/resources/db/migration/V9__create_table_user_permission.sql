CREATE TABLE IF NOT EXISTS user_permissions (
    id_user BIGINT NOT NULL,
    id_permission BIGINT NOT NULL,
    PRIMARY KEY (id_user, id_permission),
    CONSTRAINT fk_user_permissions_user FOREIGN KEY (id_user) REFERENCES user_table (id),
    CONSTRAINT fk_user_permissions_permission FOREIGN KEY (id_permission) REFERENCES permission (id)
);