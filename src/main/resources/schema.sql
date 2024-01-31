create schema if not exists DOCTOR_APPOINTMENT;
set schema DOCTOR_APPOINTMENT;
create table if not exists PATIENTS (
    id uuid not null,
    name varchar(255) not null,
    mobileno varchar(255) not null,
    primary key (id)
);
create table if not exists APPOINTMENTS (
    id uuid not null,
    commence_time date not null,
    length integer not null,
    status int2 not null,
    patient uuid,
    primary key (id),
    foreign key (patient) references PATIENTS (id)
);

