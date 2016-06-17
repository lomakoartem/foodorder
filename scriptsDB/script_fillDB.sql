/*INSERT INTO user_roles ("role_name") VALUES ('ROLE_ADMIN');
INSERT INTO user_roles ("role_name") VALUES ('ROLE_EMPLOYEE');*/

INSERT INTO users ( "user_name", "user_email", "user_isadmin") VALUES ('admin','admin@epam.com', 'true');
INSERT INTO users (  "user_name", "user_email") VALUES ('employee', 'employee@epam.com');


INSERT INTO locations ("location_name", "location_address", "location_floor", "location_info") VALUES ('K14', 'vul. Kudriashova 14','1', 'Main office/ qty of people');
INSERT INTO locations ("location_name", "location_address", "location_floor", "location_info") VALUES ('K18', 'vul. Kudriashova 18','1', 'New office');
INSERT INTO locations ("location_name", "location_address", "location_floor","location_info") VALUES ('F28', 'vul. Fizkultury 28','1', '2nd 3rd and 4th floors');
INSERT INTO locations ("location_name", "location_address", "location_floor","location_info") VALUES ('F30', 'vul. Fizkultury 30', '1','2nd 3rd and 5th floors');
INSERT INTO locations ("location_name", "location_address", "location_floor", "location_info") VALUES ('Z74', 'vul. Zhylanskaya 75','1', '1 floor');
