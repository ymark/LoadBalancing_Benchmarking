CREATE TABLE story(
	id int NOT NULL AUTO_INCREMENT,
	title VARCHAR(256) NOT NULL,
	contents VARCHAR(1024) NOT NULL,
	post_date DATE NOT NULL,
	number_of_likes int NOT NULL,
	PRIMARY KEY (id)
);
