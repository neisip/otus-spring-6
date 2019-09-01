INSERT INTO authors(name) VALUES ('Egor Bugaenko');
INSERT INTO authors(name) VALUES ('Mike Brown');
INSERT INTO authors(name) VALUES ('Robert Martin');

INSERT INTO genres(title) VALUES ('Psychological thriller');
INSERT INTO genres(title) VALUES ('Sport');
INSERT INTO genres(title) VALUES ('Programming');

INSERT INTO books(title, author_id, genre_id) VALUES ('Elegant objects', 1, 1);
INSERT INTO books(title, author_id, genre_id) VALUES ('Adaptology for intensive chair pressers', 2, 2);
INSERT INTO books(title, author_id, genre_id) VALUES ('Clean Brain', 3, 3);