create table if not exists "enterpriseConf"(
    id serial constraint enterprise_id primary key,
    name varchar not null,
    desc varchar not null,
    slogan varchar not null,
    address varchar not null,
    email varchar not null,
    nit varchar not null,
    stat varchar not null,
    rcs varchar not null,
    logo BYTEA
);