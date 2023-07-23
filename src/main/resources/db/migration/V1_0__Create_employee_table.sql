create table if not exists "employee"
(
    id serial constraint employee_id primary key ,
    first_name varchar not null,
    last_name varchar not null,
    birth_date varchar not null,
    matricule varchar not null constraint employee_matricule_unique unique,
    empl_img BYTEA
);