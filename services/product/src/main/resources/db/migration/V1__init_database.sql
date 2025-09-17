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

create sequence if not exists categoryIseq increment by 50;
create sequence if not exists Product_seq increment by 50;