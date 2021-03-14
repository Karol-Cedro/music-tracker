DROP DATABASE IF EXISTS music_tracker;
CREATE USER musictrackeruser WITH PASSWORD 'password';
CREATE DATABASE music_tracker WITH TEMPLATE =template0 OWNER= musictrackeruser;
\c music_tracker;

ALTER DEFAULT PRIVILEGES GRANT ALL ON TABLES TO musictrackeruser;
ALTER DEFAULT PRIVILEGES GRANT ALL ON SEQUENCES TO musictrackeruser;

CREATE TABLE mt_users(
    user_id SERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    email VARCHAR(30) NOT NULL,
    password text NOT NULL
);

CREATE TABLE mt_playlists(
  playlist_id SERIAL PRIMARY KEY NOT NULL,
  user_id INTEGER NOT NULL,
  title VARCHAR(50) NOT NULL,
  description VARCHAR(80) DEFAULT NULL
);

ALTER TABLE mt_playlists ADD CONSTRAINT play_user_fk FOREIGN KEY (user_id) REFERENCES mt_users(user_id);

CREATE TABLE mt_songs(
    song_id SERIAL PRIMARY KEY NOT NULL,
    user_id INTEGER NOT NULL,
    artist VARCHAR(30) NOT NULL,
    title VARCHAR(50) NOT NULL
);

ALTER TABLE mt_songs ADD CONSTRAINT song_user_fk FOREIGN KEY (user_id) REFERENCES mt_users(user_id);

CREATE TABLE mt_playlist_song(
    song_id INTEGER  NOT NULL,
    playlist_id INTEGER  NOT NULL
);

ALTER TABLE mt_playlist_song ADD CONSTRAINT playlist_fk FOREIGN KEY (playlist_id) REFERENCES mt_playlists(playlist_id);
ALTER TABLE mt_playlist_song ADD CONSTRAINT song_fk FOREIGN KEY (song_id) REFERENCES mt_songs(song_id);