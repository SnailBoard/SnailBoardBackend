ALTER TABLE board
ALTER COLUMN created_at TYPE timestamp,
ALTER COLUMN updated_at TYPE timestamp;

ALTER TABLE columns
ALTER COLUMN created_at TYPE timestamp;

ALTER TABLE team
ALTER COLUMN created_at TYPE timestamp,
ALTER COLUMN updated_at TYPE timestamp;

ALTER TABLE ticket
ALTER COLUMN created_at TYPE timestamp,
ALTER COLUMN updated_at TYPE timestamp;

ALTER TABLE ticket_history
ALTER COLUMN change_date TYPE timestamp;

ALTER TABLE columns
ADD COLUMN updated_at timestamp;

ALTER TABLE notification
ADD COLUMN created_at timestamp,
ADD COLUMN updated_at timestamp;

ALTER TABLE roles
ADD COLUMN created_at timestamp,
ADD COLUMN updated_at timestamp;

ALTER TABLE statistic
ADD COLUMN created_at timestamp,
ADD COLUMN updated_at timestamp;

ALTER TABLE refresh_tokens
ADD COLUMN created_at timestamp,
ADD COLUMN updated_at timestamp;

ALTER TABLE usr
ADD COLUMN created_at timestamp,
ADD COLUMN updated_at timestamp;

ALTER TABLE ticket_history
ADD COLUMN created_at timestamp,
ADD COLUMN updated_at timestamp;
