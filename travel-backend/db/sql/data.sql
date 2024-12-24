INSERT INTO hotel (name, address, rating, price) VALUES
                                                     ('Golden', 'Greece 21', 5, 70.05),
                                                     ('El mirador', 'Greece 98', 4, 100.45),
                                                     ('La Odisea', 'Greece 99', 5, 220.00),
                                                     ('Olimpo', 'Greece 29', 5, 221.21),
                                                     ('Atenea', 'Greece 43', 2, 56.43),
                                                     ('Ice and fire', 'Iceland 49', 3, 23.43),
                                                     ('Sky lights', 'Iceland 98', 5, 130.45),
                                                     ('Vikings', 'Iceland 01', 5, 99.99),
                                                     ('Dark ocean', 'Iceland 100', 2, 199.99),
                                                     ('Nordic', 'Iceland 300', 3, 109.99),
                                                     ('LUX', 'Mexico 53', 4, 10.88),
                                                     ('Los chiles', 'Mexico 9', 3, 21.19),
                                                     ('Reforma', 'Mexico 922', 1, 33.33),
                                                     ('Catrina', 'Mexico 88', 5, 22.87),
                                                     ('Tequila', 'Mexico 348', 5, 32.42),
                                                     ('The lake', 'Canada 22', 5, 63.54),
                                                     ('Cascade', 'Canada 89', 4, 22.99),
                                                     ('Pino', 'Canada 38', 4, 99.42);

insert into flight (origin_lat, origin_lng, destination_lng, destination_lat, origin_name, destination_name, aero_line, price) VALUES
                                                    (65.9999, -35.8888, 11.1111, 22.2222, 'Mexico', 'Greece', 'aero_gold', 43.00),
                                                    (11.1111, 22.2222, 10.9999, -14.8888, 'Greece', 'Mexico','aero_gold', 33.33),
                                                    (13.9999, 46.8888, 58.8888, 77.7777, 'Mexico', 'Iceland', 'aero_gold', 48.70),
                                                    (85.9999, 75.8888, -54.8888, 77.7777, 'Iceland', 'Mexico', 'aero_gold', 12.99),
                                                    (56.8888, 77.7777,  11.1111, 22.2222, 'Iceland', 'Gracia', 'aero_gold', 85.98),
                                                    (11.1111, 22.2222, -64.8888, 75.7777, 'Gracia', 'Iceland', 'aero_gold', 29.99),
                                                    (67.9999, 88.8888, 11.1111, 22.2222, 'Mexico', 'Greece', 'blue_sky', 25.65),
                                                    (11.1111, 22.2222, 49.9999, 54.8888, 'Greece', 'Mexico', 'blue_sky', 12.99),
                                                    (44.4444, 55.555,  11.1111, 22.2222, 'Canada', 'Mexico', 'aero_gold', 19.99),
                                                    (11.1111, -22.2222, 44.4444, 55.5555, 'Mexico', 'Canada', 'aero_gold', 15.65),
                                                    (54.9999, -54.7755, -27.8888, 77.7777, 'Mexico', 'Iceland', 'blue_sky', 42.99),
                                                    (-12.9999, 54.8888, 12.8888, 77.7777, 'Iceland', 'Mexico', 'blue_sky', 21.54),
                                                    (-54.8888, 19.5544,  11.1111, 22.2222, 'Iceland', 'Gracia', 'blue_sky', 12.00),
                                                    (44.4444, 55.555,  11.1111, 22.2222, 'Canada', 'Mexico', 'blue_sky', 16.99),
                                                    (11.1111, 22.2222, 54.4444, 55.5555, 'Mexico', 'Canada', 'blue_sky', 14.65);

insert into customer (dni, full_name, total_flights, total_lodgings, total_tours, phone_number, email) VALUES
                                                    ('CHJF982241TXFEV672', 'Chris Jeff', 0, 0, 0, '24-12-45-32', 'jeffch@travel.com'),
                                                    ('RLMY932893CAFEV730', 'Riley Thomas', 0, 0, 0, '23-56-56-34', 'rileyt@travel.com'),
                                                    ('KEMI771012HMCRG004', 'Kenan Michel', 2, 1, 0, '33-74-58-43', 'kmichel@gmail.com'),
                                                    ('GUFL781012HCRGR426', 'Gunter Fl', 0, 2, 0, '55-33-66-26', 'gunterfl@gmail.com'),
                                                    ('WAKT771012HMCRG472', 'Walt Knut', 1, 1, 0, '55-85-31-73', 'waltk@gmail.com'),
                                                    ('RKKA771012HMCRR118', 'Karen RK', 4, 1, 0, '55-27-27-78', 'rxkaren@gmail.com'),
                                                    ('WIKA771012HCRGR980', 'Kathy Williamson', 0, 2, 0, '33-24-41-54', 'kathyw@gmail.com'),
                                                    ('MASK771012HCRGR426', 'Masada K', 1, 1, 0, '23-92-94-58', 'kmasada@gmail.com'),
                                                    ('KHXH771012HMRGR663', 'Khal Xu', 2, 1, 1, '55-78-33-11', 'khalxu@gmail.com'),
                                                    ('CRMA625312UTSGE531', 'Marcel Craig', 2, 1, 1, '24-78-85-63', 'marcercr@gmail.com');

insert into reservation (id, date_reservation,  hotel_id, customer_id, date_start, date_end, total_days, price) VALUES
                                                    ('12345678-1234-5678-1234-567812345678', now(), 1, 'WIKA771012HCRGR980', '2024-08-01', '2024-09-07', 7, 77.00),
                                                    ('22345678-1234-5678-1234-567812345678', now(), 2, 'WIKA771012HCRGR980', '2024-08-01', '2024-09-07', 7, 225.00),
                                                    ('32345678-1234-5678-1234-567812345678', now(), 3, 'MASK771012HCRGR426', '2024-08-01', '2024-10-01', 4, 112.00),
                                                    ('42345678-1234-5678-1234-567812345678', now(), 3, 'GUFL781012HCRGR426', '2024-08-12', '2024-11-14', 2, 74.00),
                                                    ('52345678-1234-5678-1234-567812345678', now(), 4, 'GUFL781012HCRGR426', '2024-08-01', '2024-10-01', 4, 98.00),
                                                    ('62345678-1234-5678-1234-567812345678', now(), 5, 'WAKT771012HMCRG472', '2024-08-01', '2024-10-01', 4, 160.00),
                                                    ('72345678-1234-5678-1234-567812345678', now(), 5, 'KEMI771012HMCRG004', '2024-08-01', '2024-10-01', 4, 600.00);

insert into ticket (id, price, flight_id, customer_id, departure_date, arrival_date, purchase_date) VALUES
                                                    ('12345678-1234-5678-2236-567812345678', 330.05, 1, 'WAKT771012HMCRG472', '2024-08-01 14:00:00', '2024-08-01 16:00:00',now()),
                                                    ('22345678-1234-5678-3235-567812345678', 220.33, 1, 'KEMI771012HMCRG004', '2024-08-01 14:00:00', '2024-08-01 16:00:00', now()),
                                                    ('32345678-1234-5678-4234-567812345678', 320.00, 4, 'KEMI771012HMCRG004', '2024-08-01 12:00:00', '2024-08-01 15:00:00', now()),
                                                    ('42345678-1234-5678-5233-567812345678', 560.77, 7, 'MASK771012HCRGR426', '2024-08-12 20:00:00', '2024-01-14 22:00:00', now()),
                                                    ('52345678-1234-5678-5233-567812345678', 135.50, 2, 'RKKA771012HMCRR118', '2024-09-13 20:00:00', '2024-09-14 01:00:00', now()),
                                                    ('62345678-1234-5678-5233-567812345678', 260.24, 1, 'RKKA771012HMCRR118', '2024-09-14 20:00:00', '2024-09-14 23:00:00', now()),
                                                    ('72345678-1234-5678-5233-567812345678', 560.79, 5, 'RKKA771012HMCRR118', '2024-09-15 20:00:00', '2024-09-15 22:00:00', now()),
                                                    ('82345678-1234-5678-5233-567812345678', 560.95, 7, 'RKKA771012HMCRR118', '2024-09-16 20:00:00', '2024-09-16 01:00:00', now());
--TOURS

insert into tour(id_customer, name) VALUES
                                                    ('KHXH771012HMRGR663', 'Honey Moon'),
                                                    ('CRMA625312UTSGE531', 'Magical Tour');

insert into ticket (id, price, flight_id, customer_id, departure_date, arrival_date, purchase_date, tour_id) VALUES
                                                    ('92345678-1234-5678-2236-567812345678', 320.24, 10, 'KHXH771012HMRGR663', '2024-06-07 14:00:00', '2024-06-07 16:00:00', now(), 1),
                                                    ('90345678-1234-5678-3235-567812345679', 243.64, 11, 'KHXH771012HMRGR663', '2024-06-07 14:00:00', '2024-06-07 16:00:00', now(), 1),
                                                    ('91345678-1234-5678-2236-567812345680', 230.80, 12, 'CRMA625312UTSGE531', '2024-06-07 14:00:00', '2024-06-07 16:00:00', now(), 2),
                                                    ('92345678-1234-5678-3235-567812345681', 532.35, 13, 'CRMA625312UTSGE531', '2024-06-07 14:00:00', '2024-06-07 16:00:00', now(), 2);

insert into reservation (id, date_reservation,  hotel_id, customer_id, date_start, date_end, total_days, price, tour_id) VALUES
                                                    ('82345678-1234-5678-1234-567812345678', now(), 7, 'KHXH771012HMRGR663', '2024-08-01', '2024-10-01', 4, 600.00, 1),
                                                    ('92345678-1234-5678-1234-567812345678', now(), 8, 'CRMA625312UTSGE531', '2024-08-01', '2024-10-01', 4, 600.00, 2)