create table if not exists category
(
    id integer not null primary key,
    description varchar(255),
    name varchar(255)
);

create table if not exists product
(
     id integer not null primary key,
     description varchar(255),
     name varchar(255),
     available_quantity double precision not null,
     price numeric(38, 2),
     category_id integer
            constraint fk1slkfje8rou8cmn references category
);

drop sequence if exists category_seq;
create sequence category_seq start 1 increment 1;

drop sequence if exists product_seq;
create sequence product_seq start 1 increment 1;