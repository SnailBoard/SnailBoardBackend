create table IF NOT EXISTS  board
(
    id          binary(255) not null,
    created_at  date,
    description varchar(255),
    name        varchar(255),
    team_id     binary(255),
    primary key (id)
);

create table IF NOT EXISTS  columns
(
    id         binary(255) not null,
    created_at date,
    name       varchar(255),
    board_id   binary(255),
    primary key (id)
);

create table IF NOT EXISTS  notification
(
    id      binary(255) not null,
    src_url varchar(255),
    text    varchar(255),
    user_id binary(255),
    primary key (id)
);

create table IF NOT EXISTS  roles
(
    id   binary(255) not null,
    name varchar(255),
    primary key (id)
);

create table IF NOT EXISTS  statistic
(
    id       binary(255) not null,
    tasks    integer,
    time     integer,
    board_id binary(255),
    user_id  binary(255),
    primary key (id)
);

create table IF NOT EXISTS  team
(
    id          binary(255) not null,
    created_at  date,
    description varchar(255),
    name        varchar(255),
    primary key (id)
);

create table IF NOT EXISTS  ticket
(
    id        binary(255) not null,
    number    integer,
    text      varchar(255),
    column_id binary(255),
    primary key (id)
);

create table IF NOT EXISTS  ticket_history
(
    id                 binary(255) not null,
    change_date        date,
    text_before_change varchar(255),
    ticket_id          binary(255),
    primary key (id)
);

create table IF NOT EXISTS  user_role
(
    user_id binary(255) not null,
    role_id binary(255) not null
);

create table IF NOT EXISTS  user_team
(
    user_id binary(255) not null,
    team_id binary(255) not null
);

create table IF NOT EXISTS  user_ticket
(
    user_id   binary(255) not null,
    ticket_id binary(255) not null
);

create table IF NOT EXISTS  usr
(
    id       binary(255) not null,
    email    varchar(255),
    name     varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
);

alter table usr
    add constraint UK_useremail unique (email);
alter table usr
    add constraint UK_username unique (username);
alter table board
    add constraint FK_board2teams foreign key (team_id) references team (id);
alter table columns
    add constraint FK_columns2boards foreign key (board_id) references board (id);
alter table notification
    add constraint FK_notification2users foreign key (user_id) references usr (id);
alter table statistic
    add constraint FK_statistic2boards foreign key (board_id) references board (id);
alter table statistic
    add constraint FK_statistic2users foreign key (user_id) references usr (id);
alter table ticket
    add constraint FK_tickets2columns foreign key (column_id) references columns (id);
alter table ticket_history
    add constraint FK_tickets2history2tickets foreign key (ticket_id) references ticket (id);
alter table user_role
    add constraint FK_user2roles2roles foreign key (role_id) references roles (id);
alter table user_role
    add constraint FK_user2roles2users foreign key (user_id) references usr (id);
alter table user_team
    add constraint FK_user2team2teams foreign key (team_id) references team (id);
alter table user_team
    add constraint FK_user2team2users foreign key (user_id) references usr (id);
alter table user_ticket
    add constraint FK_user2ticket2tickets foreign key (ticket_id) references ticket (id);
alter table user_ticket
    add constraint FK_user2ticket2users foreign key (user_id) references usr (id);
