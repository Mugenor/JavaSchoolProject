insert into passenger(name, surname, birthday) values
  ('Name', 'Surname', '2000-01-01');
insert into user(password, username, passenger_id) values
  ('$2a$10$kQgYVMZj8oThCk2.1LoXweAFYYMF4v69Sb7HdOIMovD1mdV8TRMhC', 'passenger', 1);
-- password: passenger
insert into user(password, username) values
  ('$2a$10$dulS6Q7MDcxVkeLdLl1nYOhKS5CDcdiYBSBUF0FQd.nSo9Wox5Tdu', 'admin');
-- password: admin