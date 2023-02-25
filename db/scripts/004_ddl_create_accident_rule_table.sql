create table if not exists accident_rule (
id serial primary key,
accident_id int not null references accident(id),
rule_id int not null references rule(id)
);