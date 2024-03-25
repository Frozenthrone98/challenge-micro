create table account_type
(
    account_type_id serial
        primary key,
    description     varchar(255) not null
);

alter table account_type
    owner to aldo;

create table account
(
    account_id      uuid           not null
        primary key,
    account_number  varchar(255)   not null,
    created_at      timestamp(6)   not null,
    customer_id     uuid           not null,
    initial_balance numeric(38, 2) not null,
    status          boolean        not null,
    account_type_id integer        not null
        constraint fk_account_type
            references account_type
);

alter table account
    owner to aldo;

create table movement_type
(
    movement_type_id serial
        primary key,
    description      varchar(255) not null
);

alter table movement_type
    owner to aldo;

create table movement
(
    movement_id      bigserial
        primary key,
    balance          numeric(38, 2) not null,
    created_at       timestamp(6)   not null,
    value            numeric(38, 2) not null,
    account_id       uuid           not null
        constraint fk_account_id
            references account,
    movement_type_id integer        not null
        constraint fk_movement_type
            references movement_type
);

alter table movement
    owner to aldo;


INSERT INTO movement_type (movement_type_id, description)
VALUES (1, 'RETIRO'),
       (2, 'DEPOSITO');

INSERT INTO account_type (account_type_id, description)
VALUES
    (1, 'Cuenta de ahorros'),
    (2, 'Cuenta corriente'),
    (3, 'Cuenta de inversión'),
    (4, 'Cuenta de préstamo');