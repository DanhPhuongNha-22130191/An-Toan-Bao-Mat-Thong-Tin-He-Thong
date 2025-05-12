-- Create or use the WatchShop database
-- CREATE DATABASE WatchShop;
-- GO
USE WatchShop;
GO

-- Drop tables in reverse order to handle foreign key dependencies
DROP TABLE IF EXISTS OrderDetail;
DROP TABLE IF EXISTS [Order];
DROP TABLE IF EXISTS CartItem;
DROP TABLE IF EXISTS Voucher;
DROP TABLE IF EXISTS Strap;
DROP TABLE IF EXISTS WatchType;
DROP TABLE IF EXISTS ProductSpecification;
DROP TABLE IF EXISTS Brand;
DROP TABLE IF EXISTS Account;
DROP TABLE IF EXISTS Gender;
DROP TABLE IF EXISTS Product;

-- Create tables
-- Product table must be created first since it is referenced by other tables
CREATE TABLE Product (
                         productId INT PRIMARY KEY,
                         name NVARCHAR(100) NOT NULL,
                         price DECIMAL(10, 2) NOT NULL,
                         description NVARCHAR(MAX),
                         stock INT NOT NULL,
                         image NVARCHAR(255),
                         haveTrending BIT NOT NULL DEFAULT 0,
                         size DECIMAL(5, 2), -- e.g., 40.00 mm for watch face size
                         waterResistance BIT NOT NULL DEFAULT 0
);

CREATE TABLE Account (
                         accountId INT PRIMARY KEY,
                         username NVARCHAR(50) NOT NULL,
                         password NVARCHAR(100) NOT NULL,
                         email NVARCHAR(100) NOT NULL
);

CREATE TABLE Gender (
                        genderId INT PRIMARY KEY,
                        name NVARCHAR(50) NOT NULL
);

CREATE TABLE Brand (
                       brandId INT PRIMARY KEY,
                       productId INT,
                       name NVARCHAR(100) NOT NULL,
                       FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE ProductSpecification (
                                      specId INT PRIMARY KEY,
                                      productId INT,
                                      papers NVARCHAR(100),
                                      year INT,
                                      watchFinderWarranty NVARCHAR(100),
                                      caseMaterial NVARCHAR(100),
                                      FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE WatchType (
                           typeId INT PRIMARY KEY,
                           productId INT,
                           name NVARCHAR(100) NOT NULL,
                           FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE Strap (
                       strapId INT PRIMARY KEY,
                       productId INT,
                       color NVARCHAR(50),
                       material NVARCHAR(100),
                       length DECIMAL(5, 2), -- e.g., 120.00 mm
                       FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE CartItem (
                          accountId INT,
                          productId INT,
                          quantity INT NOT NULL,
                          PRIMARY KEY (accountId, productId),
                          FOREIGN KEY (accountId) REFERENCES Account(accountId),
                          FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE Voucher (
                         voucherId INT PRIMARY KEY,
                         code NVARCHAR(50) NOT NULL,
                         expirationTime DATETIME NOT NULL,
                         percentDecrease DECIMAL(5, 2) NOT NULL,
                         name NVARCHAR(100) NOT NULL,
                         quantity INT NOT NULL
);

CREATE TABLE [Order] (
                         orderId INT PRIMARY KEY,
                         accountId INT,
                         voucherId INT NULL,
                         shipping DECIMAL(10, 2) NOT NULL,
    paymentMethod NVARCHAR(50) NOT NULL,
    FOREIGN KEY (accountId) REFERENCES Account(accountId),
    FOREIGN KEY (voucherId) REFERENCES Voucher(voucherId)
    );

CREATE TABLE OrderDetail (
                             orderDetailId INT PRIMARY KEY,
                             orderId INT,
                             productId INT,
                             fullName NVARCHAR(100) NOT NULL,
                             phone NVARCHAR(20) NOT NULL,
                             email NVARCHAR(100) NOT NULL,
                             address NVARCHAR(255) NOT NULL,
                             orderNote NTEXT,
                             FOREIGN KEY (orderId) REFERENCES [Order](orderId),
                             FOREIGN KEY (productId) REFERENCES Product(productId)
);

-- Insert sample data
-- Products
INSERT INTO Product (productId, name, price, description, stock, image, haveTrending, size, waterResistance) VALUES
                                                                                                                 (1, N'Submariner Date', 9500.00, N'Iconic diving watch with date function', 10, N'submariner.jpg', 1, 40.00, 1),
                                                                                                                 (2, N'Seamaster Diver', 5500, N'Iconic diving watch with date function', 10, N'submariner.jpg', 1, 40.00, 1),
                                                                                                                 (3, N'Prospex Turtle', 450.00, N'Affordable diving watch', 20, N'turtle.jpg', 0, 44.00, 1),
                                                                                                                 (5, N'Vintage Classic', 25000.00, N'Classic vintage dress watch', 5, N'vintage.jpg', 0, 36.00, 0),
                                                                                                                 (6, N'Nautilus', 60000.00, N'Luxury sports watch', 3, N'nautilus.jpg', 1, 40.00, 1),
                                                                                                                 (7, N'G-Shock', 150.00, N'Durable sports watch', 50, N'gshock.jpg', 1, 48.00, 1),
                                                                                                                 (8, N'Datejust', 8500.00, N'Elegant dress watch', 12, N'datejust.jpg', 0, 36.00, 1);
-- Brands
INSERT INTO Brand (brandId, productId, name) VALUES
                                                 (1, 1, N'Rolex'),
                                                 (2, 2, N'Omega'),
                                                 (3, 3, N'Seiko'),
                                                 (4, 7, N'Casio'),
                                                 (5, 6, N'Patek Philippe'),
                                                 (6, 8, N'Rolex');

-- Gender
INSERT INTO Gender (genderId, name) VALUES
                                        (1, N'Men'),
                                        (2, N'Women'),
                                        (3, N'Unisex');

-- Accounts
INSERT INTO Account (accountId, username, password, email) VALUES
                                                               (1, N'john_doe', N'hashed_password_1', N'john.doe@example.com'),
                                                               (2, N'jane_smith', N'hashed_password_2', N'jane.smith@example.com'),
                                                               (3, N'robert_johnson', N'hashed_password_3', N'robert.johnson@example.com'),
                                                               (4, N'susan_wilson', N'hashed_password_4', N'susan.wilson@example.com'),
                                                               (5, N'mike_brown', N'hashed_password_5', N'mike.brown@example.com');



-- Product Specifications
INSERT INTO ProductSpecification (specId, productId, papers, year, watchFinderWarranty, caseMaterial) VALUES
                                                                                                          (1, 1, N'Original Box & Papers', 2022, N'2 Years', N'904L Stainless Steel'),
                                                                                                          (2, 2, N'Original Box & Papers', 2021, N'2 Years', N'Stainless Steel'),
                                                                                                          (3, 3, N'Original Box & Papers', 2022, N'1 Year', N'Stainless Steel'),
                                                                                                          (4, 5, N'Original Box & Papers', 1969, N'None', N'Stainless Steel'),
                                                                                                          (5, 6, N'Full Set', 2022, N'2 Years', N'18k Rose Gold'),
                                                                                                          (6, 7, N'Original Box & Papers', 2022, N'1 Year', N'Resin'),
                                                                                                          (7, 8, N'Original Box & Papers', 2022, N'2 Years', N'904L Stainless Steel');

-- WatchType
INSERT INTO WatchType (typeId, productId, name) VALUES
                                                    (1, 1, N'Diving'),
                                                    (2, 2, N'Diving'),
                                                    (3, 3, N'Diving'),
                                                    (4, 7, N'Sports'),
                                                    (5, 5, N'Vintage'),
                                                    (6, 6, N'Luxury'),
                                                    (7, 7, N'Sports'),
                                                    (8, 8, N'Dress');

-- Strap
INSERT INTO Strap (strapId, productId, color, material, length) VALUES
                                                                    (1, 1, N'Silver', N'Stainless Steel', 180.00),
                                                                    (2, 2, N'Silver', N'Stainless Steel', 190.00),
                                                                    (3, 3, N'Black', N'Silicone', 200.00),
                                                                    (4, 5, N'Brown', N'Leather', 170.00),
                                                                    (5, 6, N'Gold', N'18k Rose Gold', 180.00),
                                                                    (6, 7, N'Black', N'Resin', 200.00),
                                                                    (7, 8, N'Silver', N'Stainless Steel', 175.00);

-- Cart Items
INSERT INTO CartItem (accountId, productId, quantity) VALUES
                                                          (1, 1, 1),
                                                          (1, 3, 2),
                                                          (2, 2, 1),
                                                          (3, 5, 1),
                                                          (4, 7, 3);

-- Vouchers
INSERT INTO Voucher (voucherId, code, expirationTime, percentDecrease, name, quantity) VALUES
                                                                                           (1, N'WELCOME10', '2025-12-31 23:59:59', 10.00, N'Welcome Discount', 1000),
                                                                                           (2, N'SUMMER25', '2025-08-31 23:59:59', 25.00, N'Summer Sale', 500),
                                                                                           (3, N'VIP15', '2025-12-31 23:59:59', 15.00, N'VIP Discount', 200),
                                                                                           (4, N'FLASH50', '2025-06-01 23:59:59', 50.00, N'Flash Sale', 50);

-- Orders
INSERT INTO [Order] (orderId, accountId, voucherId, shipping, paymentMethod) VALUES
    (1, 1, 1, 15.00, N'Credit Card'),
    (2, 2, NULL, 10.00, N'PayPal'),
    (3, 3, 2, 0.00, N'Bank Transfer'),
    (4, 1, NULL, 15.00, N'Credit Card'),
    (5, 4, 3, 20.00, N'Credit Card');

-- Order Details
INSERT INTO OrderDetail (orderDetailId, orderId, productId, fullName, phone, email, address, orderNote) VALUES
                                                                                                            (1, 1, 1, N'John Doe', N'+1-555-123-4567', N'john.doe@example.com', N'123 Main St, New York, NY 10001', N'Please gift wrap'),
                                                                                                            (2, 1, 3, N'John Doe', N'+1-555-123-4567', N'john.doe@example.com', N'123 Main St, New York, NY 10001', NULL),
                                                                                                            (3, 2, 2, N'Jane Smith', N'+1-555-987-6543', N'jane.smith@example.com', N'456 Oak Ave, Los Angeles, CA 90001', NULL),
                                                                                                            (4, 3, 5, N'Robert Johnson', N'+1-555-246-8135', N'robert.johnson@example.com', N'789 Pine Rd, Chicago, IL 60007', N'Call before delivery'),
                                                                                                            (5, 4, 8, N'John Doe', N'+1-555-123-4567', N'john.doe@example.com', N'123 Main St, New York, NY 10001', NULL),
                                                                                                            (6, 5, 7, N'Susan Wilson', N'+1-555-369-8521', N'susan.wilson@example.com', N'321 Cedar Ln, Miami, FL 33101', N'Leave with the doorman');