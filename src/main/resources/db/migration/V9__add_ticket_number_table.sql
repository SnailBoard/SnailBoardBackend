create table ticket_number(id uuid not null, board_id uuid not null, number int4);
alter table if exists ticket_number add constraint board_cons foreign key (board_id) references board;
