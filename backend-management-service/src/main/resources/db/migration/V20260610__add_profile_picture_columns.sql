-- Add profile picture columns (bytea) to clients and partners
ALTER TABLE clients ADD COLUMN IF NOT EXISTS profile_picture bytea;
ALTER TABLE clients ADD COLUMN IF NOT EXISTS profile_picture_content_type varchar(100);

ALTER TABLE partners ADD COLUMN IF NOT EXISTS profile_picture bytea;
ALTER TABLE partners ADD COLUMN IF NOT EXISTS profile_picture_content_type varchar(100);
