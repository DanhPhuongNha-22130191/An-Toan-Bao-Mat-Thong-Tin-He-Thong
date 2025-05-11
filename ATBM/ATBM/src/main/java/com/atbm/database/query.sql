--CREATE DATABASE WatchShop;
-- GO
USE WatchShop;
-- GO

-- Create tables
CREATE TABLE Brand (
                       brandId INT PRIMARY KEY,
                       name NVARCHAR(100) NOT NULL
);

CREATE TABLE Gender (
                        genderId INT PRIMARY KEY,
                        name NVARCHAR(50) NOT NULL
);

CREATE TABLE Category (
                          categoryId INT PRIMARY KEY,
                          name NVARCHAR(100) NOT NULL,
                          image NVARCHAR(255)
);

CREATE TABLE Account (
                         accountId INT PRIMARY KEY,
                         username NVARCHAR(50) NOT NULL,
                         password NVARCHAR(100) NOT NULL,
                         email NVARCHAR(100) NOT NULL
);

CREATE TABLE Product (
                         productId INT PRIMARY KEY,
                         categoryId INT,
                         name NVARCHAR(100) NOT NULL,
                         price DECIMAL(10, 2) NOT NULL,
                         description NTEXT,
                         stock INT NOT NULL,
                         image NVARCHAR(255),
                         haveTrending BIT NOT NULL DEFAULT 0,
                         FOREIGN KEY (categoryId) REFERENCES Category(categoryId)
);

CREATE TABLE ProductSpecification (
                                      specId INT PRIMARY KEY,
                                      productId INT,
                                      papers NVARCHAR(100),
                                      year INT,
                                      watchFinderWarranty NVARCHAR(100),
                                      caseSize NVARCHAR(50),
                                      caseMaterial NVARCHAR(100),
                                      bracelet NVARCHAR(100),
                                      waterResistance NVARCHAR(100),
                                      FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE ProductFilter (
                               productFilterId INT PRIMARY KEY,
                               productId INT,
                               typeName NVARCHAR(100),
                               filterTypeId INT,
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
-- Brands
INSERT INTO Brand (brandId, name) VALUES
                                      (1, N'Rolex'),
                                      (2, N'Omega'),
                                      (3, N'Seiko'),
                                      (4, N'Casio'),
                                      (5, N'Patek Philippe');

-- Gender
INSERT INTO Gender (genderId, name) VALUES
                                        (1, N'Men'),
                                        (2, N'Women'),
                                        (3, N'Unisex');

-- Categories
INSERT INTO Category (categoryId, name, image) VALUES
                                                   (1, N'Luxury Watches', N'/images/categories/luxury.jpg'),
                                                   (2, N'Sport Watches', N'/images/categories/sport.jpg'),
                                                   (3, N'Smart Watches', N'/images/categories/smart.jpg'),
                                                   (4, N'Vintage Watches', N'/images/categories/vintage.jpg'),
                                                   (5, N'Limited Edition', N'/images/categories/limited.jpg');

-- Accounts
INSERT INTO Account (accountId, username, password, email) VALUES
                                                               (1, N'john_doe', N'hashed_password_1', N'john.doe@example.com'),
                                                               (2, N'jane_smith', N'hashed_password_2', N'jane.smith@example.com'),
                                                               (3, N'robert_johnson', N'hashed_password_3', N'robert.johnson@example.com'),
                                                               (4, N'susan_wilson', N'hashed_password_4', N'susan.wilson@example.com'),
                                                               (5, N'mike_brown', N'hashed_password_5', N'mike.brown@example.com');

-- Products
INSERT INTO Product (productId, categoryId, name, price, description, stock, image, haveTrending) VALUES
                                                                                                      (1, 1, N'Rolex Submariner', 8999.99, N'Iconic diving watch with unidirectional rotatable bezel and Chromalight display', 10, N'/images/products/rolex_submariner.jpg', 1),
                                                                                                      (2, 1, N'Omega Seamaster', 4500.00, N'Professional diver''s watch with helium escape valve and ceramic bezel', 15, N'/images/products/omega_seamaster.jpg', 1),
                                                                                                      (3, 2, N'Seiko Prospex', 499.99, N'Automatic diving watch with 200m water resistance', 30, N'/images/products/seiko_prospex.jpg', 0),
                                                                                                      (4, 3, N'Apple Watch Series 7', 399.99, N'Always-on Retina display with health and fitness tracking features', 50, N'/images/products/apple_watch.jpg', 1),
                                                                                                      (5, 4, N'Omega Speedmaster Vintage', 6500.00, N'The first watch worn on the moon with manual-winding chronograph movement', 5, N'/images/products/omega_speedmaster.jpg', 0),
                                                                                                      (6, 5, N'Patek Philippe Nautilus', 35000.00, N'Limited edition luxury sports watch with blue dial', 3, N'/images/products/patek_nautilus.jpg', 1),
                                                                                                      (7, 2, N'Casio G-Shock', 120.00, N'Shock-resistant watch with 200m water resistance', 45, N'/images/products/casio_gshock.jpg', 0),
                                                                                                      (8, 1, N'Rolex Datejust', 7500.00, N'Classic luxury watch with date display and cyclops lens', 12, N'/images/products/rolex_datejust.jpg', 0);

-- Product Specifications
INSERT INTO ProductSpecification (specId, productId, papers, year, watchFinderWarranty, caseSize, caseMaterial, bracelet, waterResistance) VALUES
                                                                                                                                               (1, 1, N'Original Box & Papers', 2022, N'2 Years', N'41mm', N'904L Stainless Steel', N'Oyster, Stainless Steel', N'300m'),
                                                                                                                                               (2, 2, N'Original Box & Papers', 2021, N'2 Years', N'42mm', N'Stainless Steel', N'Stainless Steel', N'300m'),
                                                                                                                                               (3, 3, N'Original Box & Papers', 2022, N'1 Year', N'42mm', N'Stainless Steel', N'Silicone', N'200m'),
                                                                                                                                               (4, 5, N'Original Box & Papers', 1969, N'None', N'39mm', N'Stainless Steel', N'Leather', N'50m'),
                                                                                                                                               (5, 6, N'Full Set', 2022, N'2 Years', N'40mm', N'18k Rose Gold', N'18k Rose Gold', N'120m'),
                                                                                                                                               (6, 7, N'Original Box & Papers', 2022, N'1 Year', N'45mm', N'Resin', N'Resin', N'200m'),
                                                                                                                                               (7, 8, N'Original Box & Papers', 2022, N'2 Years', N'36mm', N'904L Stainless Steel', N'Jubilee, Stainless Steel', N'100m');

-- Product Filters
INSERT INTO ProductFilter (productFilterId, productId, typeName, filterTypeId) VALUES
                                                                                   (1, 1, N'Style', 1), -- Diving
                                                                                   (2, 1, N'Material', 2), -- Stainless Steel
                                                                                   (3, 2, N'Style', 1), -- Diving
                                                                                   (4, 3, N'Style', 1), -- Diving
                                                                                   (5, 4, N'Style', 3), -- Smart
                                                                                   (6, 5, N'Style', 4), -- Vintage
                                                                                   (7, 6, N'Material', 3), -- Gold
                                                                                   (8, 7, N'Style', 5), -- Sports
                                                                                   (9, 8, N'Style', 6); -- Dress

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