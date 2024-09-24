-- Insert users into app_user table
INSERT INTO app_user(name, email, password) VALUES
('a', '1.doe@example.com', '1'),
('b', '2.doe@example.com', '2'),
('c', '3.doe@example.com', '3'),
('d', '4.doe@example.com', '4'),
('e', '5.doe@example.com', '5');

-- Insert user roles into user_roles table
INSERT INTO user_roles(user_id, roles) VALUES
(1, 'Rider'),
(2, 'Driver'),
(3, 'Rider'),
(4, 'Rider'),
(5, 'Driver');

-- Insert drivers into driver table
INSERT INTO driver(user_id, rating, available, current_location) VALUES
(2, 4.9, 'true', ST_GeomFromText('POINT(77.2167 28.6667)', 4326)),
(3, 4.6, 'true', ST_GeomFromText('POINT(77.2273 28.6353)', 4326)),
(4, 5.0, 'true', ST_GeomFromText('POINT(77.2500 28.5500)', 4326)),
(5, 4.2, 'true', ST_GeomFromText('POINT(77.2000 28.6200)', 4326));
--(6, 6, 4.9, 'true', ST_GeomFromText('POINT(77.1025 28.7041)', 4326));

-- Insert riders into rider table
INSERT INTO rider(user_id, rating) VALUES
(1, 4.9);

INSERT INTO wallet(user_id, balance) VALUES
(1, 100);
INSERT INTO wallet(user_id, balance) VALUES
(2, 500);
