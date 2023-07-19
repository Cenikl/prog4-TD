create table employee
(
    id varchar constraint employee_id primary key default uuid_generate_v4(),
    first_name varchar not null,
    last_name varchar not null,
    matricule varchar not null constraint employee_matricule_unique unique,
    empl_img BYTEA
);