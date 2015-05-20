CREATE TABLE Users (
	user_id VARCHAR(50) NOT NULL,
	user_name VARCHAR(50) NOT NULL,
	PRIMARY KEY (user_id)
);

CREATE TABLE Routes (
	route_id VARCHAR(100) NOT NULL,
	user_id VARCHAR(50) NOT NULL,
	city VARCHAR(50),
	title VARCHAR(50) NOT NULL,
	keywords VARCHAR(200),
	created_time TIMESTAMP NOT NULL,
	last_modified_time TIMESTAMP NOT NULL,
	share_flag BOOLEAN NOT NULL,
	PRIMARY KEY (route_id),
	FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE MapPoints (
	map_point_id VARCHAR(50) NOT NULL,
	route_id VARCHAR(100) NOT NULL,
	longitude DOUBLE PRECISION NOT NULL,
	latitude DOUBLE PRECISION NOT NULL,
	created_time TIMESTAMP NOT NULL,
	FOREIGN KEY (route_id) REFERENCES Routes(route_id),
	PRIMARY KEY (map_point_id, route_id)
);

CREATE TABLE KeyPoints (
	key_point_id VARCHAR(50) NOT NULL,
	route_id VARCHAR(100) NOT NULL,
	longitude DOUBLE PRECISION NOT NULL,
	latitude DOUBLE PRECISION NOT NULL,
	created_time TIMESTAMP NOT NULL,
	title VARCHAR(50) NOT NULL,
	notation VARCHAR(200),
	FOREIGN KEY (route_id) REFERENCES Routes(route_id),
	PRIMARY KEY (key_point_id, route_id)
);

CREATE TABLE Comments (
	comment_id VARCHAR(50) NOT NULL,
	route_id VARCHAR(100) NOT NULL,
	user_id VARCHAR(50) NOT NULL,
	content VARCHAR(300) NOT NULL,
	created_time TIMESTAMP NOT NULL,
	FOREIGN KEY (route_id) REFERENCES Routes(route_id),
	FOREIGN KEY (user_id) REFERENCES Users(user_id),
	PRIMARY KEY (comment_id, route_id)
);

CREATE TABLE Likes (
	user_id VARCHAR(50) NOT NULL,
	route_id VARCHAR(100) NOT NULL,
	FOREIGN KEY (route_id) REFERENCES Routes(route_id),
	FOREIGN KEY (user_id) REFERENCES Users(user_id),
	PRIMARY KEY (user_id, route_id)
);