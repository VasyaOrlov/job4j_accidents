insert into authorities (authority) values ('ROLE_USER');
insert into authorities (authority) values ('ROLE_ADMIN');

insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$yVUdtLV4HCL7osvZz3by4uhMG0oyBDORmNOp8nZR1a38Xg15/5J3S',
(select id from authorities where authority = 'ROLE_ADMIN'));