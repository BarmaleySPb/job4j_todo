create table if not exists j_role (
    id serial primary key,
    name varchar(2000)
);

create table if not exists j_user (
    id serial primary key,
    name varchar(2000),
    role_id int not null references j_role(id)
);

create table if not exists task (
    id serial primary key,
    description varchar(2000),
    created timestamp,
    done boolean,
    user_id int not null references j_user(id)
);

create table if not exists category (
    id serial primary key,
    name varchar(2000)
);

insert into j_role (name)
    values ('admin'), ('user');

insert into category (name)
    values ('first category'), ('second category'), ('third category');