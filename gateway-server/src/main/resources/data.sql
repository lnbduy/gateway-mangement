INSERT INTO gateway(serial_number, name, ipv4_address) VALUES('ABC123456', 'Gateway 1', '192.168.1.3');
INSERT INTO gateway(serial_number, name, ipv4_address) VALUES('ACD126792', 'Gateway 2', '192.168.1.10');

INSERT INTO device(vendor, created_date, status, gateway_serial_number) VALUES ('Vendor 1', '2021-11-01', 'ONLINE', 'ABC123456');
INSERT INTO device(vendor, created_date, status, gateway_serial_number) VALUES ('Vendor 2', '2021-11-01', 'ONLINE', 'ABC123456');
INSERT INTO device(vendor, created_date, status, gateway_serial_number) VALUES ('Vendor ABC', '2021-11-01', 'ONLINE', 'ACD126792');
INSERT INTO device(vendor, created_date, status, gateway_serial_number) VALUES ('Vendor XYZ', '2021-11-01', 'ONLINE', 'ACD126792');