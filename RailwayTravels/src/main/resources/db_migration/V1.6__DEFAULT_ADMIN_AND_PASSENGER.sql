insert into passenger(name, surname, birthday) values
  ('Name', 'Surname', 946674000000);
insert into user(password, username, passenger_id, email) values
  ('$2a$10$kQgYVMZj8oThCk2.1LoXweAFYYMF4v69Sb7HdOIMovD1mdV8TRMhC', 'passenger', 1, 'passenger@passenger.com');
-- password: passenger
insert into user(password, username, email) values
  ('$2a$10$dulS6Q7MDcxVkeLdLl1nYOhKS5CDcdiYBSBUF0FQd.nSo9Wox5Tdu', 'admin', 'admin@admin.com');
-- password: admin