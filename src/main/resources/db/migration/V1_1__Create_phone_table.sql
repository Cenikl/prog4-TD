create table if not exists "phone"(
    id serial
        constraint phone_id primary key,
    phone_number varchar not null
        constraint phone_number_unique unique,
    phone_employee bigint not null
        constraint fk_employee_number_id references employee(id)
);