ALTER TABLE roles ADD CONSTRAINT UK_name UNIQUE (name);

insert into roles(id, name)
values
('1', 'ROLE_USER'),
('2', 'ROLE_ADMIN');

insert into usr(id, email, name, password, username)
values
('1', 'user@example.com', 'user', '$2a$10$QqbBL3H5a0/f2T3X.Bu0n..bbWd8Ae1ImTo.D/WrCRTUWkBD0/giG', 'User'),
('2', 'admin@example.com', 'admin', '$2a$10$gPsRx5k88TZ9b91Qd6LY.e..fLOVUMqdl9RwlxFOODyRMWl3t3jDW', 'Admin');

insert into user_role(role_id, user_id)
values
('1', '1'),
('2', '2');
