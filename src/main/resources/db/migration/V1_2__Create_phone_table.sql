create table if not exists "phone"(
    id serial primary key,
    phone_number varchar not null
        constraint phone_number_unique unique,
    phone_employee bigint check (phone_enterprise == null)
        constraint fk_employee_id references employee(id),
    phone_enterprise bigint check (phone_employee == null)
        constraint fk_enterprise_id references enterprise(id)
);


