CREATE SEQUENCE auto_id_location;
CREATE SEQUENCE auto_id_user;
CREATE SEQUENCE auto_id_role;

CREATE TABLE locations(
	location_id bigint DEFAULT nextval('auto_id_location') PRIMARY KEY,
	location_name varchar(5) NOT NULL,
	location_address varchar(25) NOT NULL,
	location_info varchar(50),
	location_isActive boolean NOT NULL DEFAULT TRUE
	version integer 
);

CREATE TABLE user_roles(
	role_id bigint DEFAULT nextval('auto_id_role') PRIMARY KEY,
	role_name varchar(25) NOT NULL
	version integer
);

CREATE TABLE users(
	user_id bigint DEFAULT nextval('auto_id_user') PRIMARY KEY,
	user_login varchar(25) NOT NULL UNIQUE,
	user_password varchar(150) NOT NULL,
	user_name varchar(50) NOT NULL,
	user_phone varchar(13) NOT NULL,
	user_role bigint NOT NULL REFERENCES user_roles(role_id) ON DELETE RESTRICT ON UPDATE CASCADE
	version integer
);

