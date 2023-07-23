create table if not exists "cin"(
    id serial
        constraint cin_pk primary key,
    cin_number varchar not null
        constraint cin_number_unique unique ,
    issue_date varchar not null,
    issue_location varchar not null,
    cin_employee bigint not null
        constraint fk_employee_cin_id references employee(id)
)