/*
 * cria a tabela transactions para armazenar as transactions
 * cria a tabela transactions_seq para gerar o id da tabela transactions com 1
 */

create table transactions
(
    id              bigint                    not null primary key,
    amount          decimal(35, 13) default 0 not null,
    currency        varchar(3)                not null,
    created_at      datetime(6)               not null,
    recipient_name  varchar(20)               not null,
    recipient_email varchar(100)              null,
    recipient_phone varchar(20)               null,
    cc_number       varchar(150)               not null,
    cc_name         varchar(250)               not null,
    cc_expire_date  varchar(100)               not null,
    cc_cvv          varchar(50)                null
);


create table transactions_seq
(
    next_val bigint null
);

insert into transactions_seq value (1);