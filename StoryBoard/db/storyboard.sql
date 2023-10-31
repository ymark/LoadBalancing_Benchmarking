CREATE TABLE user(
	id int NOT NULL, 
	username VARCHAR(128) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE story(
	id int NOT NULL, 
	contents VARCHAR(256) NOT NULL,
	post_date DATE NOT NULL,
	by_user varchar(36) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (by_user) REFERENCES user(id)
);

CREATE TABLE storylike(
	like_date DATE NOT NULL,
	by_user int NOT NULL,
	for_story int NOT NULL,
	PRIMARY KEY(by_user,for_story),
	FOREIGN KEY(by_user) REFERENCES user(id),
	FOREIGN KEY(for_story) REFERENCES story(id),
);