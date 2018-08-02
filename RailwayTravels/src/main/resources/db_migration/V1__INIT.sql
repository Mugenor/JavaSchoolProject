create table passenger
(
	id int not null
		primary key AUTO_INCREMENT,
	name varchar(255) not null,
	surname varchar(255) not null,
	birthday date not null,
	constraint UKiv5ftvs79pr8y54jajudpckof
		unique (name, surname, birthday)
);

create table station
(
	id int not null
		primary key auto_increment,
	title varchar(255) not null,
	constraint UK_611ilhku3n66faf63y237pmj
		unique (title)
);


create table trip
(
 id int not null primary key auto_increment
);

create table departure
(
	id int not null
		primary key auto_increment,
	dateTimeFrom datetime not null,
	dateTimeTo datetime not null,
	freeSitsCount int not null,
	sitsCount int not null,
	station_from int not null,
	station_to int not null,
	numberInTrip int not null,
	trip_id int not null,
	constraint UK733kvx0xmaqbsnfkf0do3458r
		unique (station_from, station_to, dateTimeFrom, dateTimeTo),
	constraint FKm9uo504d11c7dvhxm6jiejryo
		foreign key (station_from) references station (id),
	constraint FKj8oeidcmw26i2cs7w6vyl3370
		foreign key (station_to) references station (id),
	constraint TRIP_FOREIGN_KEY
	  foreign key (trip_id) references trip(id)
);


create index FKj8oeidcmw26i2cs7w6vyl3370
	on departure (station_to);

create index DEPARTURE_TRIP_INDEX
  on departure (trip_id);

create table coach
(
	id int not null primary key auto_increment,
	departure_id int not null,
	coach_number int not null,
	constraint C_DID_FOREIGN_KEY_CONSTRAINT
	  foreign key (departure_id) references departure (id)
);

create table seat
(
	id int not null
		primary key auto_increment,
	siteNum int not null,
	coach_id int not null,
	constraint UKoueqw8wdsknigkwchpv0c8s53
		unique (siteNum, coach_id),
	constraint FK6jtu14ysy67xmci4u9agei7me
		foreign key (coach_id) references coach (id)
);

create index FK6jtu14ysy67xmci4u9agei7me
	on seat (coach_id);

create table ticket
(
  id int not null primary key auto_increment,
  trip_id int not null,
  passenger_id int not null,
  constraint TRIP_PASSENGER_UNIQUE
    unique (trip_id, passenger_id),
  constraint TRIP_FK
    foreign key (trip_id) references trip(id),
  constraint PASSENGER_FK
    foreign key (passenger_id) references passenger(id)
);

create table occupied_seat
(
  seat_id int not null primary key,
  ticket_id int not null,
  constraint SEAT_FK
    foreign key (seat_id) references seat(id),
  constraint TICKET_FK
    foreign key (ticket_id) references ticket(id)
);





create table user
(
	id int not null
		primary key auto_increment,
	password varchar(255) not null,
	username varchar(255) not null,
	passenger_id int null unique,
	email varchar(255) not null unique,
	constraint UK_jreodf78a7pl5qidfh43axdfb
		unique (username),
	constraint FKfq0a97ispsqdvk2buxdme7ufp
		foreign key (passenger_id) references passenger (id)
);

create index FKfq0a97ispsqdvk2buxdme7ufp
	on user (passenger_id);

create index EMAIL_INDEX
	on user (email);

create table almost_user
(
	hash varchar(255) primary key not null unique,
	password varchar(255) not null,
	username varchar(255) not null unique,
	email varchar(255) not null unique,
	name varchar(255) not null,
	surname varchar(255) NOT NULL,
	birthday date not null,
	registered datetime not null,
	constraint NAME_SURNAME_BIRTHDAY_UNIQUE_CONSTRAINT
		unique (name, surname, birthday)
);

