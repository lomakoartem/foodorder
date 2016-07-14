CREATE SEQUENCE auto_id_location;
CREATE SEQUENCE auto_id_user;
/*CREATE SEQUENCE auto_id_role;*/
CREATE SEQUENCE auto_id_vendor;
CREATE SEQUENCE auto_id_product_type;
CREATE SEQUENCE auto_id_product;
CREATE SEQUENCE auto_id_unit;
CREATE SEQUENCE auto_id_menu;
CREATE SEQUENCE auto_id_order;
CREATE SEQUENCE auto_id_menu_product;


CREATE TABLE locations(
	location_id bigint DEFAULT nextval('auto_id_location') PRIMARY KEY,
	location_name varchar(5) NOT NULL,
	location_address varchar(25) NOT NULL,
	location_floor SMALLINT NOT NULL,
	location_info varchar(50),
	location_isActive boolean NOT NULL DEFAULT TRUE,
	version integer DEFAULT 0
);

/*CREATE TABLE user_roles(
	role_id bigint DEFAULT nextval('auto_id_role') PRIMARY KEY,
	role_name varchar(25) NOT NULL,
	version integer DEFAULT 0
);*/

CREATE TABLE users(
	user_id bigint DEFAULT nextval('auto_id_user') PRIMARY KEY,
	user_name varchar(50) NOT NULL,
	user_email VARCHAR(50) NOT NULL,
	user_isAdmin boolean NOT NULL DEFAULT FALSE,
	user_isActive BOOLEAN NOT NULL DEFAULT FALSE,
	version integer DEFAULT 0
);

ALTER TABLE users ADD COLUMN user_upsaLink varchar(255);
ALTER TABLE users ALTER COLUMN user_isactive SET DEFAULT TRUE;

CREATE TABLE vendors(
	vendor_id bigint DEFAULT nextval('auto_id_vendor') PRIMARY KEY,
	vendor_name varchar(30) NOT NULL,
	vendor_additional_info varchar(255),
	vendor_email varchar(50) NOT NULL,
	vendor_isActive boolean NOT NULL DEFAULT TRUE,
	version integer DEFAULT 0
);

CREATE TABLE locations_vendors(
	location_id bigint NOT NULL REFERENCES locations(location_id) ON DELETE CASCADE ON UPDATE CASCADE,
	vendor_id bigint NOT NULL REFERENCES vendors(vendor_id) ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (location_id, vendor_id),
	verson integer DEFAULT 0
);

CREATE TABLE product_types(
	type_id bigint DEFAULT nextval('auto_id_product_type') PRIMARY KEY,
	type_name varchar(100),
	version integer DEFAULT 0
);

CREATE TABLE products(
	product_id bigint DEFAULT nextval('auto_id_product') PRIMARY KEY,
	product_name varchar(100) NOT NULL,
	product_info varchar(200),
	product_isActive boolean NOT NULL DEFAULT TRUE,
	product_created_on timestamp NOT NULL,
	product_modified_on timestamp NOT NULL,
	product_type bigint NOT NULL REFERENCES product_types(type_id) ON DELETE CASCADE ON UPDATE CASCADE,
	product_vendor bigint NOT NULL REFERENCES vendors(vendor_id) ON DELETE CASCADE ON UPDATE CASCADE,
	version integer DEFAULT 0
);

CREATE TABLE units(
	unit_id bigint DEFAULT nextval('auto_id_unit') PRIMARY KEY,
	unit_name varchar(100) NOT NULL,
	unit_type varchar(100) NOT NULL,
	version integer DEFAULT 0
);

CREATE TABLE menus(
	menu_id bigint DEFAULT nextval('auto_id_menu') PRIMARY KEY,
	menu_name varchar(50) NOT NULL,
	menu_isActive boolean NOT NULL DEFAULT FALSE,
	menu_date_from timestamp NOT NULL,
	menu_date_to timestamp NOT NULL,
	menu_vendor bigint NOT NULL REFERENCES vendors(vendor_id) ON DELETE CASCADE ON UPDATE CASCADE,
	version integer DEFAULT 0
);

CREATE TABLE menus_productds(
	menu_product_id bigint DEFAULT nextval('auto_id_menu_product') PRIMARY KEY,
	menu bigint NOT NULL REFERENCES menus(menu_id) ON DELETE CASCADE ON UPDATE CASCADE,
	product bigint NOT NULL REFERENCES products(product_id) ON DELETE CASCADE ON UPDATE CASCADE,
	unit bigint REFERENCES units(unit_id) ON DELETE CASCADE ON UPDATE CASCADE,
	currency varchar(20) NOT NULL,
	price money NOT NULL,
	version integer DEFAULT 0
);

CREATE TABLE orders(
	order_id bigint DEFAULT nextval('auto_id_order') PRIMARY KEY,
	order_user bigint NOT NULL REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
	order_location bigint NOT NULL REFERENCES locations(location_id) ON DELETE CASCADE ON UPDATE CASCADE,
	order_vendor bigint NOT NULL REFERENCES vendors(vendor_id) ON DELETE CASCADE ON UPDATE CASCADE,
	order_price money NOT NULL,
	order_date timestamp NOT NULL DEFAULT current_timestamp,
	version integer DEFAULT 0
);

CREATE TABLE orderlines(
	order_id bigint NOT NULL REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE CASCADE,
	menu_product_id bigint NOT NULL REFERENCES menus(menu_id) ON DELETE CASCADE ON UPDATE CASCADE,
	quantity integer NOT NULL DEFAULT 0,
	price money NOT NULL,
	version integer DEFAULT 0
);

CREATE TABLE vendors_credentials
(
  vendor_id bigint NOT NULL UNIQUE,
  password character varying(1000),
  version integer DEFAULT 0,
  CONSTRAINT vendors_credentials_pkey PRIMARY KEY (vendor_id),
  CONSTRAINT vendors_credentials_vendor_id_fkey FOREIGN KEY (vendor_id)
      REFERENCES vendors (vendor_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
