-- Inserting test values into the table user
INSERT INTO t_user (first_name, middle_name, last_name, username, email, password, phone)
    VALUES ('User1', 'Test', 'User1', 't_user1', 't_user1@pizzashop.com',
    '$2a$10$X9MKk8buP915P9tDBqcTaeeTFOsiuYiaHTupBr6HDdMbBcHe5WvSy', '534-565-5555');
INSERT INTO t_user (first_name, middle_name, last_name, username, email, password, phone)
    VALUES ('User2', 'Test', 'User2', 't_user2', 't_user2@pizzashop.com',
    '$2a$10$X9MKk8buP915P9tDBqcTaeeTFOsiuYiaHTupBr6HDdMbBcHe5WvSy', '534-565-5555');

INSERT INTO t_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO t_user_role (user_id, role_id) VALUES (3, 1);
