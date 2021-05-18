create table team_invite (id uuid not null, invited_email varchar(255), team_id uuid, primary key (id));alter table if exists team_invite add constraint UK_teaminviteuniqueid unique (invited_email);
alter table if exists team_invite drop constraint if exists UK_d7npm18odtr8jo4kjptj0wnr4;
alter table if exists team_invite add constraint UK_d7npm18odtr8jo4kjptj0wnr4 unique (invited_email);
alter table if exists team_invite add constraint FKp9wtc66edyxm3i0mshq108h77 foreign key (team_id) references team;