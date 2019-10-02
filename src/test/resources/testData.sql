INSERT INTO authors(name)
VALUES ('Egor Bugaenko'), ('Mike Brown'), ('Robert Martin');


INSERT INTO genres(title)
VALUES ('Psychological thriller'), ('Sport'), ('Programming');

INSERT INTO books(title, author_id, genre_id)
VALUES ('Elegant objects', 1, 1),  ('Adaptology for intensive chair pressers', 2, 2),  ('Clean Brain', 3, 3);
