-- Create or use the WatchShop database
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'WatchShop')
    CREATE DATABASE WatchShop;
GO
USE WatchShop;
GO

-- Drop all foreign keys
DECLARE @sql NVARCHAR(MAX) = N'';
SELECT @sql += '
ALTER TABLE [' + s.name + '].[' + t.name + '] DROP CONSTRAINT [' + fk.name + '];'
FROM sys.foreign_keys AS fk
    INNER JOIN sys.tables AS t ON fk.parent_object_id = t.object_id
    INNER JOIN sys.schemas AS s ON t.schema_id = s.schema_id;
EXEC sp_executesql @sql;

-- Drop all tables
SET @sql = N'';
SELECT @sql += '
DROP TABLE IF EXISTS [' + s.name + '].[' + t.name + '];'
FROM sys.tables AS t
    INNER JOIN sys.schemas AS s ON t.schema_id = s.schema_id;
EXEC sp_executesql @sql;

-- Create Tables
CREATE TABLE Product (
                         productId INT IDENTITY(1,1) PRIMARY KEY,
                         name NVARCHAR(100) NOT NULL,
                         price DECIMAL(10, 2) NOT NULL,
                         description NVARCHAR(MAX),
                         stock INT NOT NULL,
                         image NVARCHAR(255),
                         haveTrending BIT NOT NULL DEFAULT 0,
                         size DECIMAL(5, 2),
                         waterResistance BIT NOT NULL DEFAULT 0
);

CREATE TABLE Account (
                         accountId INT IDENTITY(1,1) PRIMARY KEY,
                         username NVARCHAR(50) NOT NULL UNIQUE,
                         password NVARCHAR(255) NOT NULL,
                         email NVARCHAR(100) NOT NULL UNIQUE,
                         publicKeyActive NVARCHAR(MAX)
);

CREATE TABLE Gender (
                        genderId INT IDENTITY(1,1) PRIMARY KEY,
                        name NVARCHAR(50) NOT NULL
);

CREATE TABLE Brand (
                       brandId INT IDENTITY(1,1) PRIMARY KEY,
                       productId INT,
                       name NVARCHAR(100) NOT NULL,
                       FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE ProductSpecification (
                                      specId INT IDENTITY(1,1) PRIMARY KEY,
                                      productId INT,
                                      papers NVARCHAR(100),
                                      year INT,
                                      watchFinderWarranty NVARCHAR(100),
                                      caseMaterial NVARCHAR(100),
                                      FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE WatchType (
                           typeId INT IDENTITY(1,1) PRIMARY KEY,
                           productId INT,
                           name NVARCHAR(100) NOT NULL,
                           FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE Strap (
                       strapId INT IDENTITY(1,1) PRIMARY KEY,
                       productId INT,
                       color NVARCHAR(50),
                       material NVARCHAR(100),
                       length DECIMAL(5, 2),
                       FOREIGN KEY (productId) REFERENCES Product(productId)
);

CREATE TABLE Voucher (
                         voucherId BIGINT IDENTITY(1,1) PRIMARY KEY,
                         code NVARCHAR(50) UNIQUE NOT NULL,
                         expirationTime DATE NOT NULL,
                         percentDecrease FLOAT NOT NULL,
                         name NVARCHAR(100) NOT NULL,
                         quantity INT NOT NULL
);

CREATE TABLE [Order] (
                         orderId INT IDENTITY(1,1) PRIMARY KEY,
    accountId INT,
    voucherId BIGINT NULL,
    shipping DECIMAL(10, 2) NOT NULL,
    status NVARCHAR(50) NOT NULL,
    orderDate DATE NOT NULL,
    paymentMethod NVARCHAR(50) NOT NULL,
    FOREIGN KEY (accountId) REFERENCES Account(accountId),
    FOREIGN KEY (voucherId) REFERENCES Voucher(voucherId)
    );

CREATE TABLE OrderSecurity (
                               orderId INT PRIMARY KEY,
                               signature NVARCHAR(MAX) NOT NULL,
                               publicKey NVARCHAR(MAX) NOT NULL,
                               FOREIGN KEY (orderId) REFERENCES [Order](orderId)
);

CREATE TABLE CartItem (
                          cartItemId BIGINT IDENTITY(1,1) PRIMARY KEY,
                          accountId INT,
                          productId INT,
                          orderId INT NULL,
                          quantity INT NOT NULL,
                          FOREIGN KEY (accountId) REFERENCES Account(accountId),
                          FOREIGN KEY (productId) REFERENCES Product(productId),
                          FOREIGN KEY (orderId) REFERENCES [Order](orderId)
);

CREATE TABLE OrderDetail (
                             orderDetailId INT IDENTITY(1,1) PRIMARY KEY,
                             orderId INT,
                             fullName NVARCHAR(100) NOT NULL,
                             phone NVARCHAR(20) NOT NULL,
                             email NVARCHAR(100) NOT NULL,
                             address NVARCHAR(255) NOT NULL,
                             orderNote NVARCHAR(MAX),
                             FOREIGN KEY (orderId) REFERENCES [Order](orderId)
);
GO

ALTER TABLE Product
    ADD isDeleted BIT NOT NULL DEFAULT 0;

-- Insert Products
INSERT INTO Product (name, price, description, stock, image, haveTrending, size, waterResistance) VALUES
                                                                                                      (N'Submariner Date', 9500.00, N'Iconic diving watch with date function', 10, N'submariner.jpg', 1, 40.00, 1),
                                                                                                      (N'Seamaster Diver', 5500.00, N'Professional diving watch', 15, N'seamaster.jpg', 1, 42.00, 1),
                                                                                                      (N'Prospex Turtle', 450.00, N'Affordable diving watch', 20, N'turtle.jpg', 0, 44.00, 1),
                                                                                                      (N'Vintage Classic', 25000.00, N'Classic vintage dress watch', 5, N'vintage.jpg', 0, 36.00, 0),
                                                                                                      (N'Nautilus', 60000.00, N'Luxury sports watch', 3, N'nautilus.jpg', 1, 40.00, 1),
                                                                                                      (N'G-Shock', 150.00, N'Durable sports watch', 50, N'gshock.jpg', 1, 48.00, 1),
                                                                                                      (N'Datejust', 8500.00, N'Elegant dress watch', 12, N'datejust.jpg', 0, 36.00, 1);

-- Insert Accounts
INSERT INTO Account (username, password, email, publicKeyActive) VALUES
                                                                     (N'john_doe', N'hashed_password_1', N'john.doe@example.com', NULL),
                                                                     (N'jane_smith', N'hashed_password_2', N'jane.smith@example.com', NULL),
                                                                     (N'robert_johnson', N'hashed_password_3', N'robert.johnson@example.com', NULL),
                                                                     (N'susan_wilson', N'hashed_password_4', N'susan.wilson@example.com', NULL),
                                                                     (N'mike_brown', N'hashed_password_5', N'mike.brown@example.com', NULL);

-- Insert Genders
INSERT INTO Gender (name) VALUES (N'Men'), (N'Women'), (N'Unisex');

-- Insert Brands (phải tương ứng với Product.productId)
INSERT INTO Brand (productId, name) VALUES
                                        (1, N'Rolex'),
                                        (2, N'Omega'),
                                        (3, N'Seiko'),
                                        (6, N'Casio'),
                                        (5, N'Patek Philippe'),
                                        (7, N'Rolex');

-- Insert Product Specifications
INSERT INTO ProductSpecification (productId, papers, year, watchFinderWarranty, caseMaterial) VALUES
                                                                                                  (1, N'Original Box & Papers', 2022, N'2 Years', N'904L Stainless Steel'),
                                                                                                  (2, N'Original Box & Papers', 2021, N'2 Years', N'Stainless Steel'),
                                                                                                  (3, N'Original Box & Papers', 2022, N'1 Year', N'Stainless Steel'),
                                                                                                  (4, N'Original Box & Papers', 1969, N'None', N'Stainless Steel'),
                                                                                                  (5, N'Full Set', 2022, N'2 Years', N'18k Rose Gold'),
                                                                                                  (6, N'Original Box & Papers', 2022, N'1 Year', N'Resin'),
                                                                                                  (7, N'Original Box & Papers', 2022, N'2 Years', N'904L Stainless Steel');

-- Insert Watch Types
INSERT INTO WatchType (productId, name) VALUES
                                            (1, N'Diving'),
                                            (2, N'Diving'),
                                            (3, N'Diving'),
                                            (4, N'Vintage'),
                                            (5, N'Luxury'),
                                            (6, N'Sports'),
                                            (7, N'Dress');

-- Insert Straps
INSERT INTO Strap (productId, color, material, length) VALUES
                                                           (1, N'Silver', N'Stainless Steel', 180.00),
                                                           (2, N'Silver', N'Stainless Steel', 190.00),
                                                           (3, N'Black', N'Silicone', 200.00),
                                                           (4, N'Brown', N'Leather', 170.00),
                                                           (5, N'Gold', N'18k Rose Gold', 180.00),
                                                           (6, N'Black', N'Resin', 200.00),
                                                           (7, N'Silver', N'Stainless Steel', 175.00);

-- Insert Vouchers
INSERT INTO Voucher (code, expirationTime, percentDecrease, name, quantity) VALUES
                                                                                (N'WELCOME10', '2025-12-31', 10.00, N'Welcome Discount', 1000),
                                                                                (N'SUMMER25', '2025-08-31', 25.00, N'Summer Sale', 500),
                                                                                (N'VIP15', '2025-12-31', 15.00, N'VIP Discount', 200),
                                                                                (N'FLASH50', '2025-06-01', 50.00, N'Flash Sale', 50);

-- Insert Orders
INSERT INTO [Order] (accountId, voucherId, shipping, status, orderDate, paymentMethod) VALUES
    (1, 1, 15.00, N'Pending', '2025-05-30', N'Credit Card'),
    (2, NULL, 10.00, N'Shipped', '2025-05-29', N'PayPal'),
    (3, 2, 0.00, N'Cancelled', '2025-05-28', N'Bank Transfer'),
    (1, NULL, 15.00, N'Completed', '2025-05-27', N'Credit Card'),
    (4, 3, 20.00, N'Pending', '2025-05-26', N'Credit Card');

-- Insert OrderSecurity
INSERT INTO OrderSecurity (orderId, signature, publicKey) VALUES
                                                              (1, N'sample_signature_1', N'sample_public_key_1'),
                                                              (2, N'sample_signature_2', N'sample_public_key_2'),
                                                              (3, N'sample_signature_3', N'sample_public_key_3'),
                                                              (4, N'sample_signature_4', N'sample_public_key_4'),
                                                              (5, N'sample_signature_5', N'sample_public_key_5');

-- Insert CartItems
INSERT INTO CartItem (accountId, productId, orderId, quantity) VALUES
                                                                   (1, 1, 1, 1),
                                                                   (1, 3, 1, 2),
                                                                   (2, 2, 2, 1),
                                                                   (3, 4, 3, 1),
                                                                   (4, 6, 5, 3);

-- Insert OrderDetails
INSERT INTO OrderDetail (orderId, fullName, phone, email, address, orderNote) VALUES
                                                                                  (1, N'John Doe', N'+1-555-123-4567', N'john.doe@example.com', N'123 Main St, New York, NY 10001', N'Please gift wrap'),
                                                                                  (2, N'Jane Smith', N'+1-555-987-6543', N'jane.smith@example.com', N'456 Oak Ave, Los Angeles, CA 90001', NULL),
                                                                                  (3, N'Robert Johnson', N'+1-555-246-8135', N'robert.johnson@example.com', N'789 Pine Rd, Chicago, IL 60007', N'Call before delivery'),
                                                                                  (4, N'John Doe', N'+1-555-123-4567', N'john.doe@example.com', N'123 Main St, New York, NY 10001', NULL),
                                                                                  (5, N'Susan Wilson', N'+1-555-369-8521', N'susan.wilson@example.com', N'321 Cedar Ln, Miami, FL 33101', N'Leave with the doorman');


select * from Product;
select * from brand;


ALTER TABLE Product
    ADD brandId INT;

ALTER TABLE Product
    ADD CONSTRAINT FK_Product_Brand
        FOREIGN KEY (brandId) REFERENCES Brand(brandId);

UPDATE Product SET brandId = 1 WHERE productId = 1;
UPDATE Product SET brandId = 2 WHERE productId = 2;
-- v.v...

ALTER TABLE Product
    ADD strapId INT;

ALTER TABLE Product
    ADD CONSTRAINT FK_Product_Strap
        FOREIGN KEY (strapId) REFERENCES Strap(strapId);

UPDATE Product SET strapId = 1 WHERE productId = 1;
UPDATE Product SET strapId = 2 WHERE productId = 2;
-- ...
ALTER TABLE Product
    ADD status NVARCHAR(50) NOT NULL DEFAULT N'Còn hàng';
