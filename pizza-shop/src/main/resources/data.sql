-- Inserting test values into the table role
INSERT INTO t_role (name, description) VALUES ('USER', 'USER');
INSERT INTO t_role (name, description) VALUES ('ADMIN', 'ADMIN');

-- Inserting test values into the table user
INSERT INTO t_user (first_name, middle_name, last_name, username, email, password, phone)
    VALUES ('Admin', 'Test', 'User', 't_admin', 't_admin@pizzashop.com',
    '$2a$10$bJaL01Zp202GdNK0Z0oBe.PLKwXnPj0eFuBKhyTJu2WqtQvMoqc7q', '');

-- Inserting test values into the table user-role
INSERT INTO t_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO t_user_role (user_id, role_id) VALUES (1, 2);