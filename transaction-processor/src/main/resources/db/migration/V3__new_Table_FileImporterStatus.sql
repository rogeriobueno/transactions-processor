/*
 * criação de índices para as colunas recipient_name e cc_number
 */
create table file_status
(
    id          bigint       not null primary key,

    file_name   varchar(255) not null,
    import_date datetime(6) not null,
    description text,
    status      varchar(30)
);


create table file_status_seq
(
    next_val bigint null
);

insert into file_status_seq value (1);

create index file_name_index on file_status (file_name) using btree;

