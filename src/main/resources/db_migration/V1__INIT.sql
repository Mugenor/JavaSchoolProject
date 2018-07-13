create table passenger
(
	id int not null
		primary key AUTO_INCREMENT,
	name varchar(255) null,
	surname varchar(255) null,
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
	constraint UK733kvx0xmaqbsnfkf0do3458r
		unique (station_from, station_to, dateTimeFrom, dateTimeTo),
	constraint FKm9uo504d11c7dvhxm6jiejryo
		foreign key (station_from) references station (id),
	constraint FKj8oeidcmw26i2cs7w6vyl3370
		foreign key (station_to) references station (id)
);

create index FKj8oeidcmw26i2cs7w6vyl3370
	on departure (station_to);

create table coach
(
	id int not null primary key auto_increment,
	departure_id int not null,
	coach_number int not null,
	constraint C_DID_FOREIGN_KEY_CONSTRAINT
	  foreign key (departure_id) references departure (id)
);

create table ticket
(
	id int not null
		primary key auto_increment,
	siteNum int not null,
	coach_id int not null,
	passenger_id int null,
	constraint UKoueqw8wdsknigkwchpv0c8s53
		unique (siteNum, coach_id),
	constraint FK6jtu14ysy67xmci4u9agei7me
		foreign key (coach_id) references coach (id),
	constraint FK7xdva6e0sknsfbit0xop9y050
		foreign key (passenger_id) references passenger (id)
);

create table ticket_passenger_departure
(
  ticket_id int not null,
  passenger_id int not null,
  departure_id int not null,
  constraint TICKET_PASSENGER_UNIQUE_CONSTRAINT
    unique (ticket_id, passenger_id),
  constraint PASSENGER_DEPARTURE_UNIQUE_CONSTRAINT
    unique (passenger_id, departure_id),
  constraint TPT_CONSTRAINT
    foreign key (ticket_id) references ticket (id),
  constraint TPP_CONSTRAINT
    foreign key (passenger_id) references passenger (id)
);


create index TICKET_PASSENGER_INDEX
  on ticket_passenger_departure (ticket_id, passenger_id);

create index DEPARTURE_PASSENGER_INDEX
  on ticket_passenger_departure (departure_id, passenger_id);


create index FK6jtu14ysy67xmci4u9agei7me
	on ticket (coach_id);

create index FK7xdva6e0sknsfbit0xop9y050
	on ticket (passenger_id);

create table user
(
	id int not null
		primary key auto_increment,
	password varchar(255) not null,
	username varchar(255) not null,
	passenger_id int null unique,
	constraint UK_jreodf78a7pl5qidfh43axdfb
		unique (username),
	constraint FKfq0a97ispsqdvk2buxdme7ufp
		foreign key (passenger_id) references passenger (id)
);

create index FKfq0a97ispsqdvk2buxdme7ufp
	on user (passenger_id);

