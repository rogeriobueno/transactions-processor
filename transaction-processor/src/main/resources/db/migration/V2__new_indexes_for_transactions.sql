/*
 * criação de índices para as colunas recipient_name e cc_number
 */
create index recipient_name_index on transactions (recipient_name) using btree;

create index cc_number_index on transactions (cc_number) using btree;



