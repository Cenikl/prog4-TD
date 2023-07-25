create type sex as enum ('M','F');
create type csp as enum ('M1','M2','OS1','OS2','OS3','OP1');
create table if not exists "employee"
(
    id serial constraint employee_id primary key ,
    first_name varchar not null,
    last_name varchar not null,
    birth_date date not null,
    sex sex not null,
    csp csp not null,
    matricule varchar not null constraint employee_matricule_unique unique,
    address varchar not null,
    email_pro varchar not null constraint email_pro_unique unique,
    email_perso varchar not null constraint email_perso_unique unique,
    "role" varchar not null,
    child integer,
    employement_date date not null,
    departure_date date,
    cnaps varchar not null,
    cin varchar not null,
    empl_img OID
);