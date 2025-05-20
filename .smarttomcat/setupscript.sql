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

       -- Drop tables if they exist
DROP TABLE IF EXISTS AccessLogs;
DROP TABLE IF EXISTS Users;

-- Create Users table with column names as used in your Java code (case-sensitive)
CREATE TABLE Users (
                       UserId INTEGER PRIMARY KEY AUTOINCREMENT,
                       Username VARCHAR(100),
                       Password VARCHAR(100),
                       Email VARCHAR(100) UNIQUE,
                       Phone VARCHAR(20),
                       Address VARCHAR(255),
                       RegistrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       Role VARCHAR(20)  -- Staff, Customer, Unknown
);

-- Create AccessLogs table with PascalCase columns to match your DAO class
CREATE TABLE AccessLogs (
                            LogId INTEGER PRIMARY KEY AUTOINCREMENT,
                            UserId INTEGER NOT NULL,
                            LoginTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            LogoutTime TIMESTAMP,
                            FOREIGN KEY (UserId) REFERENCES Users(UserId) ON DELETE CASCADE
);


-- Insert 10 sample users (example data)
INSERT INTO Users (Username, Password, Email, Phone, Address, Role)
VALUES
    ('Kelly Wu', '4444', 'almondmilk77@gmail.com', '1234567890', 'Space', 'Customer'),
    ('Alice Moo', '333', 'alice@example.com', '2345678901', '456 Orange Ave', 'Staff');


-- Insert 20 access log records (2 per user)
INSERT INTO AccessLogs (UserId, LoginTime, LogoutTime) VALUES
                                                           (1, '2025-05-17 09:00:00', '2025-05-17 11:30:00'),
                                                           (1, '2025-05-18 08:30:00', '2025-05-18 12:00:00'),

                                                           (2, '2025-05-17 10:00:00', '2025-05-17 18:00:00'),
                                                           (2, '2025-05-18 09:15:00', '2025-05-18 17:00:00');

PRAGMA table_info(AccessLogs);
PRAGMA table_info(Users);
