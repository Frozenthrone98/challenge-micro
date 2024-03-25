create table person
(
    id                    bigint auto_increment
        primary key,
    address               varchar(100) not null,
    age                   int          not null,
    created_at            datetime(6)  not null,
    full_name             varchar(50)  not null,
    gender                varchar(20)  not null,
    identification_number varchar(12)  not null,
    phone_number          varchar(9)   not null
);

create table customer
(
    customer_id binary(16)  not null
        primary key,
    password    varchar(60) not null,
    status      bit         not null,
    person_id   bigint      null,
    constraint UK_ie7rkxg6fmtxx1aes55ox9yxd
        unique (person_id),
    constraint FKnvxfigj5o3te7ig37cq7qo0bc
        foreign key (person_id) references person (id)
);

INSERT INTO person VALUES
(1,'Calle Principal 123, Lima, Perú','30,2024-03-23 17:23:37.336958','Pedro Juan,Masculino','123456789','987654321'),
(2,'Calle Principal 123, Lima, Perú','30,2024-03-23 17:23:46.929779','Robert Huaman,Masculino','712345678','987654321'),

INSERT INTO customer VALUES
('0de7b77f-d864-49ab-acdc-6f20e3deaed0','mi_contraseña_secreta',true,1)
('1701280c-02a2-4fa6-b65c-dd1d7eb4cc41','mi_contraseña_password',true,2)