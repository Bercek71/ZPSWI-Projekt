-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;


insert into app_user (id, email, password, first_name, last_name, role) values(1,'admin@localhost', 'admin', 'Admin', 'Admin', 'ADMIN');
insert into app_user (id, email, password, first_name, last_name, role) values(2, 'user@localhost', 'user', 'User', 'User', 'USER');


insert into Country(id, name, iso_code) values (1,'France', 'FR');
insert into Country(id, name, iso_code) values (2,'Germany', 'DE');
insert into Country(id, name, iso_code) values (3,'United Kingdom', 'UK');
insert into Country(id, name, iso_code) values (4,'United States', 'US');
insert into Country(id, name, iso_code) values (5,'Spain', 'ES');
insert into Country(id, name, iso_code) values (6,'Italy', 'IT');
insert into Country(id, name, iso_code) values (7,'Canada', 'CA');
insert into Country(id, name, iso_code) values (8,'Australia', 'AU');
insert into Country(id, name, iso_code) values (9,'Czech Republic', 'CZE');

insert into City(id, name, zip_code, country_id) values (1,'Paris', 75000, 1);
insert into City(id, name, zip_code, country_id) values (2,'Marseille', 13000, 1);
insert into City(id, name, zip_code, country_id) values (3,'Lyon', 69000, 1);
insert into City(id, name, zip_code, country_id) values (4,'Berlin', 10115, 2);
insert into City(id, name, zip_code, country_id) values (5,'Hamburg', 20095, 2);
insert into City(id, name, zip_code, country_id) values (6,'Munich', 80331, 2);
insert into City(id, name, zip_code, country_id) values (10,'New York', 10001, 4);
insert into City(id, name, zip_code, country_id) values (11,'Los Angeles', 90001, 4);
insert into City(id, name, zip_code, country_id) values (12,'Chicago', 60601, 4);
insert into City(id, name, zip_code, country_id) values (13,'Madrid', 28001, 5);
insert into City(id, name, zip_code, country_id) values (14,'Barcelona', 08001, 5);
insert into City(id, name, zip_code, country_id) values (15,'Valencia', 46001, 5);
insert into City(id, name, zip_code, country_id) values (16,'Rome', 00118, 6);
insert into City(id, name, zip_code, country_id) values (17,'Milan', 20121, 6);
insert into City(id, name, zip_code, country_id) values (18,'Naples', 80100, 6);
insert into City(id, name, zip_code, country_id) values (22,'Sydney', 2000, 8);
insert into City(id, name, zip_code, country_id) values (23,'Melbourne', 3000, 8);
insert into City(id, name, zip_code, country_id) values (24,'Brisbane', 4000, 8);
insert into City(id, name, zip_code, country_id) values (25,'Prague', 11000, 9);
insert into City(id, name, zip_code, country_id) values (26,'Brno', 60200, 9);
insert into City(id, name, zip_code, country_id) values (27,'Ostrava', 70200, 9);



insert into hotel(id, name, country_id) values (1,'Hotel de Crillon', 1);
insert into hotel(id, name, country_id) values (2,'Hotel Ritz', 1);
insert into hotel(id, name, country_id) values (3,'Hotel George V', 1);
insert into hotel(id, name, country_id) values (4,'Hotel Adlon', 2);
insert into hotel(id, name, country_id) values (5,'Hotel Pedro', 2);
insert into hotel(id, name, country_id) values (6,'Hotel Antonio', 3);
insert into hotel(id, name, country_id) values (7,'Hotel Maria', 3);
insert into hotel(id, name, country_id) values (8,'Hotel New York', 4);
insert into hotel(id, name, country_id) values (23,'Hotel Ostrava', 9);


insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (1, 101, 100, 'SINGLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (2, 102, 200, 'DOUBLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (3, 103, 300, 'SINGLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (4, 104, 400, 'DOUBLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (60, 104, 400, 'DOUBLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (5, 105, 500, 'SINGLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (6, 106, 600, 'DOUBLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (7, 107, 700, 'SINGLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (8, 108, 800, 'DOUBLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (9, 109, 900, 'SINGLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (10, 110, 1000, 'DOUBLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (11, 111, 1100, 'SINGLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (12, 112, 1200, 'DOUBLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (13, 113, 1300, 'SINGLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (14, 114, 1400, 'DOUBLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (15, 115, 1500, 'SINGLE', true, 1);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (16, 116, 1600, 'DOUBLE', true, 1);

insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (17, 201, 100, 'SINGLE', true, 2);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (18, 202, 200, 'DOUBLE', true, 2);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (19, 203, 300, 'SINGLE', true, 2);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (20, 204, 400, 'DOUBLE', true, 2);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (21, 205, 500, 'SINGLE', true, 2);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (22, 206, 600, 'DOUBLE', true, 2);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (23, 207, 700, 'SINGLE', true, 2);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (24, 208, 800, 'DOUBLE', true, 2);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (25, 209, 900, 'SINGLE', true, 2);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (26, 210, 1000, 'DOUBLE', true, 2);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (27, 211, 1100, 'SINGLE', true, 2);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (28, 212, 1200, 'DOUBLE', true, 2);

insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (29, 301, 100, 'SINGLE', true, 3);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (30, 302, 200, 'DOUBLE', true, 3);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (31, 303, 300, 'SINGLE', true, 3);

insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (32, 401, 100, 'SINGLE', true, 4);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (33, 402, 200, 'DOUBLE', true, 4);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (34, 403, 300, 'SINGLE', true, 4);

insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (35, 501, 100, 'SINGLE', true, 5);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (36, 502, 200, 'DOUBLE', true, 5);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (59, 502, 200, 'DOUBLE', true, 5);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (37, 503, 300, 'SINGLE', true, 5);

insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (38, 601, 100, 'SINGLE', true, 6);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (39, 602, 200, 'DOUBLE', true, 6);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (40, 603, 300, 'SINGLE', true, 6);

insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (41, 701, 100, 'SINGLE', true, 7);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (42, 702, 200, 'DOUBLE', true, 7);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (43, 703, 300, 'SINGLE', true, 7);

insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (44, 801, 100, 'SINGLE', true, 8);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (45, 802, 200, 'DOUBLE', true, 8);
insert into room(id, room_number, price_per_night, type, is_available, hotel_id) values (46, 803, 300, 'SINGLE', true, 8);

