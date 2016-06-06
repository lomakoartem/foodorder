INSERT INTO user_roles ("role_name") VALUES ('ROLE_ADMIN');
INSERT INTO user_roles ("role_name") VALUES ('ROLE_EMPLOYEE');

INSERT INTO users ("user_login", "user_password", "user_name", "user_phone", "user_role") VALUES ('admin', 'admin', 'Anton', '095', '1');
INSERT INTO users ("user_login", "user_password", "user_name", "user_phone", "user_role") VALUES ('employee', 'employee', 'Anton', '095', '2');


INSERT INTO locations ("location_name", "location_address", "location_floor", "location_info") VALUES ('K14', 'vul. Kudriashova 14','1', 'Main office/ qty of people');
INSERT INTO locations ("location_name", "location_address", "location_floor", "location_info") VALUES ('K18', 'vul. Kudriashova 18','1', 'New office');
INSERT INTO locations ("location_name", "location_address", "location_floor","location_info") VALUES ('F28', 'vul. Fizkultury 28','1', '2nd 3rd and 4th floors');
INSERT INTO locations ("location_name", "location_address", "location_floor","location_info") VALUES ('F30', 'vul. Fizkultury 30', '1','2nd 3rd and 5th floors');
INSERT INTO locations ("location_name", "location_address", "location_floor", "location_info") VALUES ('Z74', 'vul. Zhylanskaya 75','1', '1 floor');