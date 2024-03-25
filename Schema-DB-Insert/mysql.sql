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
(1,'Calle Principal 123, Lima, Perú','30,2024-03-23 17:23:37.336958','Juan Pérez,Masculino','123456789','987654321'),
(2,'Calle Principal 123, Lima, Perú','30,2024-03-23 17:23:46.929779','Aldo Mamani,Masculino','712345678','987654321'),
(3,'Calle Principal 123, Lima, Perú','30,2024-03-23 17:24:02.924031','Sofia Mendoza,Femenino','212345176','987654321'),
(4,'Calle Principal 123, Lima, Perú','30,2024-03-23 17:24:12.109783','Roberto Palao,Masculino','823451784','987654321');

INSERT INTO customer VALUES
('0de7b77f-d864-49ab-acdc-7f20e3deaed4','mi_contraseña_secreta',true,1)
('1701280c-02a2-4fa6-b75c-dd1d7eb4cc42','mi_contraseña_password',true,2)
('1c7c05bd-9960-428a-a629-b0f729f5756f','mi_contraseña_asdw',true,4)
('871539b9-9257-42c3-aa8e-1a1d62450d96','mi_contraseña_password',true,3)
