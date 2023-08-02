create table if not exists "phone"(
    id serial primary key,
    phone_number varchar(10) not null,
    phone_employee bigint
        constraint fk_employee_id references employee(id),
    phone_enterprise bigint
        constraint fk_enterprise_id references enterprise(id)
);
alter table "phone" add constraint phone_check
check ( (phone_employee is null and phone_enterprise is not null) or
        (phone_employee is not null and phone_enterprise is null)
    );


