DROP TABLE user_ticket;

ALTER TABLE ticket
ADD COLUMN reporter_id uuid,
ADD COLUMN assignee_id uuid,
ADD COLUMN task_id uuid,
ADD COLUMN created_at date,
ADD COLUMN updated_at date,
ADD COLUMN "name" varchar(255),
ADD COLUMN story_points integer;

ALTER TABLE team
DROP COLUMN naming;

ALTER TABLE team
ADD COLUMN "name" varchar(255),
ADD COLUMN updated_at date;

ALTER TABLE board
ADD COLUMN "name" varchar(255),
ADD COLUMN updated_at date;

ALTER TABLE ticket
ALTER COLUMN description TYPE text;

ALTER TABLE board
ALTER COLUMN description TYPE text;

ALTER TABLE notification
ALTER COLUMN message TYPE text;

ALTER TABLE team
ALTER COLUMN description TYPE text;

ALTER TABLE ticket_history
ALTER COLUMN text_before_change TYPE text;

alter table if exists ticket add constraint assignee_id foreign key (assignee_id) references usr;
alter table if exists ticket add constraint reporter_id foreign key (reporter_id) references usr;
alter table if exists ticket add constraint task_id foreign key (task_id) references ticket;
