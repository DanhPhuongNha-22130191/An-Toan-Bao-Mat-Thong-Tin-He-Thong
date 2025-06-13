-- Tạo database nếu chưa có
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'WatchShop')
    CREATE DATABASE WatchShop;
GO

USE WatchShop;
GO

-- Nếu các bảng tồn tại, xóa hết FK rồi drop bảng để tạo lại
DECLARE @sql NVARCHAR(MAX) = N'';

-- Xóa FK
SELECT @sql += '
ALTER TABLE [' + s.name + '].[' + t.name + '] DROP CONSTRAINT [' + fk.name + '];'
FROM sys.foreign_keys AS fk
    INNER JOIN sys.tables AS t ON fk.parent_object_id = t.object_id
    INNER JOIN sys.schemas AS s ON t.schema_id = s.schema_id;
EXEC sp_executesql @sql;

-- Drop bảng
SET @sql = N'';
SELECT @sql += '
DROP TABLE IF EXISTS [' + s.name + '].[' + t.name + '];'
FROM sys.tables AS t
    INNER JOIN sys.schemas AS s ON t.schema_id = s.schema_id;
EXEC sp_executesql @sql;
GO

-- Bảng Brand
CREATE TABLE Brand (
                       brandId INT IDENTITY(1,1) PRIMARY KEY,
                       name NVARCHAR(100) NOT NULL
);
GO

-- Bảng Strap
CREATE TABLE Strap (
                       strapId INT IDENTITY(1,1) PRIMARY KEY,
                       color NVARCHAR(50),
                       material NVARCHAR(100),
                       length DECIMAL(5,2)
);
GO

-- Bảng Product
CREATE TABLE Product (
                         productId INT IDENTITY(1,1) PRIMARY KEY,
                         name NVARCHAR(100) NOT NULL,
                         price DECIMAL(10,2) NOT NULL,
                         description NVARCHAR(MAX),
                         stock INT NOT NULL,
                         image VARBINARY(MAX) NULL,
                         haveTrending BIT NOT NULL DEFAULT 0,
                         size DECIMAL(5,2) NULL,
                         waterResistance BIT NOT NULL DEFAULT 0,
                         brandId INT NULL,
                         strapId INT NULL,
                         status NVARCHAR(50) NOT NULL DEFAULT N'Còn hàng',
                         isDeleted BIT NOT NULL DEFAULT 0,
                         CONSTRAINT FK_Product_Brand FOREIGN KEY (brandId) REFERENCES Brand(brandId),
                         CONSTRAINT FK_Product_Strap FOREIGN KEY (strapId) REFERENCES Strap(strapId)
);
GO

-- Bảng Gender
CREATE TABLE Gender (
                        genderId INT IDENTITY(1,1) PRIMARY KEY,
                        name NVARCHAR(50) NOT NULL
);
GO

-- Bảng Account
CREATE TABLE Account (
                         accountId INT IDENTITY(1,1) PRIMARY KEY,
                         username NVARCHAR(50) NOT NULL UNIQUE,
                         password NVARCHAR(255) NOT NULL,
                         email NVARCHAR(100) NOT NULL UNIQUE,
                         publicKeyActive NVARCHAR(MAX) NULL,
                         genderId INT NULL,
                         CONSTRAINT FK_Account_Gender FOREIGN KEY (genderId) REFERENCES Gender(genderId)
);
GO

-- Bảng Voucher
CREATE TABLE Voucher (
                         voucherId INT IDENTITY(1,1) PRIMARY KEY,
                         code NVARCHAR(50) UNIQUE NOT NULL,
                         expirationTime DATE NOT NULL,
                         percentDecrease FLOAT NOT NULL,
                         name NVARCHAR(100) NOT NULL,
                         quantity INT NOT NULL
);
GO

-- Bảng [Order]
CREATE TABLE [Order] (
                         orderId INT IDENTITY(1,1) PRIMARY KEY,
    accountId INT NOT NULL,
    orderDate DATETIME NOT NULL DEFAULT GETDATE(),
    status NVARCHAR(50) NOT NULL DEFAULT N'Mới tạo',
    voucherId INT NULL,
    shipping DECIMAL(10, 2) NOT NULL,
    paymentMethod NVARCHAR(50) NOT NULL
    CONSTRAINT FK_Order_Account FOREIGN KEY (accountId) REFERENCES Account(accountId),
    CONSTRAINT FK_Order_Voucher FOREIGN KEY (voucherId) REFERENCES Voucher(voucherId)
    );
GO

-- Bảng OrderSecurity
CREATE TABLE OrderSecurity (
                               orderId INT PRIMARY KEY,
                               signature NVARCHAR(MAX) NOT NULL,
                               publicKey NVARCHAR(MAX) NOT NULL,
                               FOREIGN KEY (orderId) REFERENCES [Order](orderId)
);
GO

-- Bảng CartItem
CREATE TABLE CartItem (
                          cartItemId INT IDENTITY(1,1) PRIMARY KEY,
                          accountId INT,
                          productId INT,
                          orderId INT NULL,
                          quantity INT NOT NULL,
                          FOREIGN KEY (accountId) REFERENCES Account(accountId),
                          FOREIGN KEY (productId) REFERENCES Product(productId),
                          FOREIGN KEY (orderId) REFERENCES [Order](orderId)
);
GO

-- Bảng OrderDetail
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

-- Bảng ProductSpecification
CREATE TABLE ProductSpecification (
                                      specificationId INT IDENTITY(1,1) PRIMARY KEY,
                                      productId INT NOT NULL,
                                      keySpec NVARCHAR(100) NOT NULL,
                                      valueSpec NVARCHAR(255) NOT NULL,
                                      CONSTRAINT FK_ProductSpecification_Product FOREIGN KEY (productId) REFERENCES Product(productId)
);
GO

-- Bảng WatchType
CREATE TABLE WatchType (
                           watchTypeId INT IDENTITY(1,1) PRIMARY KEY,
                           name NVARCHAR(100) NOT NULL
);
GO


-- === Insert sample data ===

-- Insert Brands
INSERT INTO Brand (name) VALUES
(N'Rolex'),
(N'Omega'),
(N'Seiko'),
(N'Casio'),
(N'Patek Philippe');
GO

-- Insert Straps
INSERT INTO Strap (color, material, length) VALUES
(N'Silver', N'Stainless Steel', 180.00),
(N'Silver', N'Stainless Steel', 190.00),
(N'Black', N'Silicone', 200.00),
(N'Brown', N'Leather', 170.00),
(N'Gold', N'18k Rose Gold', 180.00),
(N'Black', N'Resin', 200.00),
(N'Silver', N'Stainless Steel', 175.00);
GO

-- Insert Products
-- Lưu ý image để NULL hoặc dữ liệu VARBINARY nếu có
INSERT INTO Product (name, price, description, stock, image, haveTrending, size, waterResistance, brandId, strapId, status, isDeleted) VALUES
(N'Submariner Date', 9500.00, N'Iconic diving watch with date function', 10, NULL, 1, 40.00, 1, 1, 1, N'Còn hàng', 0),      -- brandId=1, strapId=1
(N'Seamaster Diver', 5500.00, N'Professional diving watch', 15, NULL, 1, 42.00, 1, 2, 2, N'Còn hàng', 0),                  -- brandId=2, strapId=2
(N'Prospex Turtle', 450.00, N'Affordable diving watch', 20, NULL, 0, 44.00, 1, 3, 3, N'Còn hàng', 0),                     -- brandId=3, strapId=3
(N'Vintage Classic', 25000.00, N'Classic vintage dress watch', 5, NULL, 0, 36.00, 0, NULL, 4, N'Còn hàng', 0),             -- brandId=NULL, strapId=4
(N'Nautilus', 60000.00, N'Luxury sports watch', 3, NULL, 1, 40.00, 1, 5, 5, N'Còn hàng', 0),                              -- brandId=5, strapId=5
(N'G-Shock', 150.00, N'Durable sports watch', 50, NULL, 1, 48.00, 1, 4, 6, N'Còn hàng', 0),                               -- brandId=4, strapId=6
(N'Datejust', 8500.00, N'Elegant dress watch', 12, NULL, 0, 36.00, 1, 1, 7, N'Còn hàng', 0);                              -- brandId=1, strapId=7

GO

-- Insert Genders
INSERT INTO Gender (name) VALUES (N'Men'), (N'Women'), (N'Unisex');
GO

-- Insert Accounts
INSERT INTO Account (username, password, email, publicKeyActive, genderId) VALUES
(N'john_doe', N'hashed_password_1', N'john.doe@example.com', NULL, 1),
(N'jane_smith', N'hashed_password_2', N'jane.smith@example.com', NULL, 2),
(N'robert_johnson', N'hashed_password_3', N'robert.johnson@example.com', NULL, 1),
(N'susan_wilson', N'hashed_password_4', N'susan.wilson@example.com', NULL, 2),
(N'mike_brown', N'hashed_password_5', N'mike.brown@example.com', NULL, 1);
GO

-- Insert ProductSpecifications
INSERT INTO ProductSpecification (productId, keySpec, valueSpec) VALUES
(1, N'Papers', N'Original Box & Papers'),
(1, N'Year', N'2022'),
(1, N'Warranty', N'2 Years'),
(1, N'Case Material', N'904L Stainless Steel'),

(2, N'Papers', N'Original Box & Papers'),
(2, N'Year', N'2021'),
(2, N'Warranty', N'2 Years'),
(2, N'Case Material', N'Stainless Steel'),

(3, N'Papers', N'Original Box & Papers'),
(3, N'Year', N'2022'),
(3, N'Warranty', N'1 Year'),
(3, N'Case Material', N'Stainless Steel'),

(4, N'Papers', N'Original Box & Papers'),
(4, N'Year', N'1969'),
(4, N'Warranty', N'None'),
(4, N'Case Material', N'Stainless Steel'),

(5, N'Papers', N'Full Set'),
(5, N'Year', N'2022'),
(5, N'Warranty', N'2 Years'),
(5, N'Case Material', N'18k Rose Gold'),

(6, N'Papers', N'Original Box & Papers'),
(6, N'Year', N'2022'),
(6, N'Warranty', N'1 Year'),
(6, N'Case Material', N'Resin'),

(7, N'Papers', N'Original Box & Papers'),
(7, N'Year', N'2022'),
(7, N'Warranty', N'2 Years'),
(7, N'Case Material', N'904L Stainless Steel');
GO

-- Insert WatchTypes
INSERT INTO WatchType (name) VALUES
(N'Diving'),
(N'Vintage'),
(N'Luxury'),
(N'Sports'),
(N'Dress');
GO


