create table if not exists employee (
    id varchar
        constraint employee_id primary key default uuid_generate_v4(),
    firstName varchar not null,
    lastName varchar not null,
    matricule varchar not null
        constraint employee_matricule_unique unique,
    emplImg BYTEA
    );