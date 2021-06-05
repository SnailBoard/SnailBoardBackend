ALTER TABLE columns
ADD COLUMN column_position int4;

ALTER TABLE board
DROP COLUMN board_position;

ALTER TABLE columns
ADD COLUMN description text,
ADD COLUMN "name" varchar(255);

ALTER TABLE columns
DROP COLUMN code;