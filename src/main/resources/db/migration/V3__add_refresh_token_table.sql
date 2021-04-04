create table refresh_tokens (id uuid not null, refresh_token varchar(1000), email varchar(255));
alter table if exists refresh_tokens add constraint UK_g9l96r670qkidthshajdtxabc unique (email);
