insert into roles(id, code)
values
('5edc5c56-8307-49f9-b28b-ab4c1093f073', 'ROLE_USER'),
('ba1792fe-134e-4598-a3c9-6a4f9091818a', 'ROLE_ADMIN');

insert into usr(id, email, first_name, password, username)
values
('c0e322a4-d72e-4b88-a3b8-8d148df2ac34', 'user@example.com', 'user', '$2a$10$QqbBL3H5a0/f2T3X.Bu0n..bbWd8Ae1ImTo.D/WrCRTUWkBD0/giG', 'User'),
('653c7004-0bdd-4e36-bd0e-f3ed6d1b8b5e', 'admin@example.com', 'admin', '$2a$10$gPsRx5k88TZ9b91Qd6LY.e..fLOVUMqdl9RwlxFOODyRMWl3t3jDW', 'Admin');

insert into user_role(role_id, user_id)
values
('5edc5c56-8307-49f9-b28b-ab4c1093f073', 'c0e322a4-d72e-4b88-a3b8-8d148df2ac34'),
('ba1792fe-134e-4598-a3c9-6a4f9091818a', '653c7004-0bdd-4e36-bd0e-f3ed6d1b8b5e');
