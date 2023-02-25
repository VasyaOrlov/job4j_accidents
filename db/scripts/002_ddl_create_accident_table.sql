create table if not exists accident (
id serial primary key,
name varchar not null,
text varchar not null,
address varchar not null,
accident_type_id int not null references accident_type(id)
);