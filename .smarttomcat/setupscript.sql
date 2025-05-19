-- Drop table if exists
DROP TABLE IF EXISTS DEVICES;

-- Create DEVICES table
CREATE TABLE DEVICES (
                         DeviceId INTEGER PRIMARY KEY AUTOINCREMENT,
                         DeviceName VARCHAR(50),
                         DeviceType VARCHAR(30),
                         UnitPrice DECIMAL(10, 2),
                         Quantity INTEGER
);

-- Insert sample data
INSERT INTO DEVICES (DeviceName, DeviceType, UnitPrice, Quantity) VALUES
                                                                      ('Smart Thermostat', 'Temperature Sensor', 99.99, 50),
                                                                      ('WiFi Light Bulb', 'Lighting', 29.99, 150),
                                                                      ('Security Camera', 'Surveillance', 199.99, 25),
                                                                      ('Smart Door Lock', 'Access Control', 149.99, 40),
                                                                      ('Air Quality Monitor', 'Environmental Sensor', 89.99, 60);

-- Create Staff table
CREATE TABLE IF NOT EXISTS Staff (
                                     StaffId INTEGER PRIMARY KEY AUTOINCREMENT,
                                     Username TEXT NOT NULL UNIQUE,
                                     Password TEXT NOT NULL,
                                     FullName TEXT NOT NULL,
                                     Role TEXT NOT NULL
);

-- Insert one staff account
INSERT OR IGNORE INTO Staff (Username, Password, FullName, Role)
VALUES ('admin', 'admin123', 'Admin User', 'staff');
