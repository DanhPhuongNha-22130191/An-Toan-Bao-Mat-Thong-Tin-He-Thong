USE
WatchShop;
GO

-- Bước 1: Xóa toàn bộ khóa ngoại trước
DECLARE
@sql NVARCHAR(MAX) = N'';

SELECT @sql += '
ALTER TABLE [' + s.name + '].[' + t.name + '] DROP CONSTRAINT [' + fk.name + '];'
FROM sys.foreign_keys AS fk
    INNER JOIN sys.tables AS t
ON fk.parent_object_id = t.object_id
    INNER JOIN sys.schemas AS s ON t.schema_id = s.schema_id;

EXEC sp_executesql @sql;

-- Bước 2: Xóa toàn bộ bảng
SET
@sql = N'';

SELECT @sql += '
DROP TABLE [' + s.name + '].[' + t.name + '];'
FROM sys.tables AS t
    INNER JOIN sys.schemas AS s
ON t.schema_id = s.schema_id;

EXEC sp_executesql @sql;

-- Tạo bảng Brand (danh mục thương hiệu)
CREATE TABLE Brand
(
    brandId INT PRIMARY KEY,
    name    NVARCHAR(100) NOT NULL
);

-- Tạo bảng Strap (danh mục dây đeo)
CREATE TABLE Strap
(
    strapId  INT PRIMARY KEY,
    color    NVARCHAR(50),
    material NVARCHAR(100),
    length   DECIMAL(5, 2)
);

-- Tạo bảng Product (sản phẩm) với FK brandId và strapId
CREATE TABLE Product
(
    productId       INT PRIMARY KEY,
    name            NVARCHAR(100) NOT NULL,
    price           DECIMAL(10, 2) NOT NULL,
    description     NVARCHAR(MAX),
    stock           INT            NOT NULL,
    image           NVARCHAR(255),
    haveTrending    BIT            NOT NULL DEFAULT 0,
    size            DECIMAL(5, 2),
    waterResistance BIT            NOT NULL DEFAULT 0,
    brandId         INT NULL,
    strapId         INT NULL,
    FOREIGN KEY (brandId) REFERENCES Brand (brandId),
    FOREIGN KEY (strapId) REFERENCES Strap (strapId)
);

-- Tạo bảng Account (tài khoản người dùng)
CREATE TABLE Account
(
    accountId INT PRIMARY KEY,
    username  NVARCHAR(50) NOT NULL,
    password  NVARCHAR(100) NOT NULL,
    email     NVARCHAR(100) NOT NULL
);

-- Tạo bảng Gender (giới tính)
CREATE TABLE Gender
(
    genderId INT PRIMARY KEY,
    name     NVARCHAR(50) NOT NULL
);

-- Tạo bảng ProductSpecification (thông số kỹ thuật)
CREATE TABLE ProductSpecification
(
    specId              INT PRIMARY KEY,
    productId           INT,
    papers              NVARCHAR(100),
    year                INT,
    watchFinderWarranty NVARCHAR(100),
    caseMaterial        NVARCHAR(100),
    FOREIGN KEY (productId) REFERENCES Product (productId)
);

-- Tạo bảng WatchType (loại đồng hồ)
CREATE TABLE WatchType
(
    typeId    INT PRIMARY KEY,
    productId INT,
    name      NVARCHAR(100) NOT NULL,
    FOREIGN KEY (productId) REFERENCES Product (productId)
);

-- Tạo bảng Voucher (phiếu giảm giá)
CREATE TABLE Voucher
(
    voucherId       BIGINT PRIMARY KEY IDENTITY(1,1),
    code            NVARCHAR(50) UNIQUE NOT NULL,
    expirationTime  DATE  NOT NULL,
    percentDecrease FLOAT NOT NULL,
    name            NVARCHAR(100) NOT NULL,
    quantity        INT   NOT NULL
);

-- Tạo bảng [Order] (đơn hàng)
CREATE TABLE [Order]
(
    orderId
    INT
    PRIMARY
    KEY
    IDENTITY
(
    1,
    1
),
    accountId INT,
    voucherId BIGINT NULL,
    shipping DECIMAL
(
    10,
    2
) NOT NULL,
    status NVARCHAR
(
    50
) NOT NULL,
    orderDate DATE NOT NULL,
    paymentMethod NVARCHAR
(
    50
) NOT NULL,
    FOREIGN KEY
(
    accountId
) REFERENCES Account
(
    accountId
),
    FOREIGN KEY
(
    voucherId
) REFERENCES Voucher
(
    voucherId
)
    );

-- Tạo bảng OrderSecurity
CREATE TABLE OrderSecurity
(
    orderId   INT PRIMARY KEY,
    signature NVARCHAR(50) NOT NULL,
    publicKey NVARCHAR(50) NOT NULL,
    FOREIGN KEY (orderId) REFERENCES [Order] (orderId)
);

-- Tạo bảng CartItem (giỏ hàng)
CREATE TABLE CartItem
(
    cartItemId BIGINT PRIMARY KEY IDENTITY(1,1),
    accountId  INT,
    productId  INT,
    orderId    INT NULL,
    quantity   INT NOT NULL,
    FOREIGN KEY (accountId) REFERENCES Account (accountId),
    FOREIGN KEY (productId) REFERENCES Product (productId),
    FOREIGN KEY (orderId) REFERENCES [Order](orderId)
);

-- Tạo bảng OrderDetail (chi tiết đơn hàng)
CREATE TABLE OrderDetail
(
    orderDetailId INT PRIMARY KEY IDENTITY(1,1),
    orderId       INT,
    fullName      NVARCHAR(100) NOT NULL,
    phone         NVARCHAR(20) NOT NULL,
    email         NVARCHAR(100) NOT NULL,
    address       NVARCHAR(255) NOT NULL,
    orderNote     NVARCHAR(MAX),
    FOREIGN KEY (orderId) REFERENCES [Order](orderId)
);
GO

-- Thêm dữ liệu mẫu

-- Brands
INSERT INTO Brand (brandId, name) VALUES
(1, N'Rolex'),
(2, N'Omega'),
(3, N'Seiko'),
(4, N'Casio'),
(5, N'Patek Philippe');

-- Straps
INSERT INTO Strap (strapId, color, material, length)
VALUES (1, N'Silver', N'Stainless Steel', 180.00),
       (2, N'Silver', N'Stainless Steel', 190.00),
       (3, N'Black', N'Silicone', 200.00),
       (4, N'Brown', N'Leather', 170.00),
       (5, N'Gold', N'18k Rose Gold', 180.00),
       (6, N'Black', N'Resin', 200.00),
       (7, N'Silver', N'Stainless Steel', 175.00);

-- Products (có brandId và strapId)
INSERT INTO Product (productId, name, price, description, stock, image, haveTrending, size, waterResistance, brandId,
                     strapId)
VALUES (1, N'Submariner Date', 9500.00, N'Iconic diving watch with date function', 10, N'submariner.jpg', 1, 40.00, 1,
        1, 1),
       (2, N'Seamaster Diver', 5500.00, N'Iconic diving watch with date function', 10, N'submariner.jpg', 1, 40.00, 1,
        2, 2),
       (3, N'Prospex Turtle', 450.00, N'Affordable diving watch', 20, N'turtle.jpg', 0, 44.00, 1, 3, 3),
       (5, N'Vintage Classic', 25000.00, N'Classic vintage dress watch', 5, N'vintage.jpg', 0, 36.00, 0, NULL, 4),
       (6, N'Nautilus', 60000.00, N'Luxury sports watch', 3, N'nautilus.jpg', 1, 40.00, 1, 5, 5),
       (7, N'G-Shock', 150.00, N'Durable sports watch', 50, N'gshock.jpg', 1, 48.00, 1, 4, 6),
       (8, N'Datejust', 8500.00, N'Elegant dress watch', 12, N'datejust.jpg', 0, 36.00, 1, 1, 7);

-- Gender
INSERT INTO Gender (genderId, name)
VALUES (1, N'Men'),
       (2, N'Women'),
       (3, N'Unisex');

-- Accounts
INSERT INTO Account (accountId, username, password, email)
VALUES (1, N'john_doe', N'hashed_password_1', N'john.doe@example.com'),
       (2, N'jane_smith', N'hashed_password_2', N'jane.smith@example.com'),
       (3, N'robert_johnson', N'hashed_password_3', N'robert.johnson@example.com'),
       (4, N'susan_wilson', N'hashed_password_4', N'susan.wilson@example.com'),
       (5, N'mike_brown', N'hashed_password_5', N'mike.brown@example.com');

-- Product Specifications
INSERT INTO ProductSpecification (specId, productId, papers, year, watchFinderWarranty, caseMaterial)
VALUES (1, 1, N'Original Box & Papers', 2022, N'2 Years', N'904L Stainless Steel'),
       (2, 2, N'Original Box & Papers', 2021, N'2 Years', N'Stainless Steel'),
       (3, 3, N'Original Box & Papers', 2022, N'1 Year', N'Stainless Steel'),
       (4, 5, N'Original Box & Papers', 1969, N'None', N'Stainless Steel'),
       (5, 6, N'Full Set', 2022, N'2 Years', N'18k Rose Gold'),
       (6, 7, N'Original Box & Papers', 2022, N'1 Year', N'Resin'),
       (7, 8, N'Original Box & Papers', 2022, N'2 Years', N'904L Stainless Steel');

-- Watch Type
INSERT INTO WatchType (typeId, productId, name)
VALUES (1, 1, N'Diving'),
       (2, 2, N'Diving'),
       (3, 3, N'Diving'),
       (4, 7, N'Sports'),
       (5, 5, N'Vintage'),
       (6, 6, N'Luxury'),
       (7, 7, N'Sports'),
       (8, 8, N'Dress');

-- Vouchers
INSERT INTO Voucher (code, expirationTime, percentDecrease, name, quantity)
VALUES (N'WELCOME10', '2025-12-31', 10.00, N'Welcome Discount', 1000),
       (N'SUMMER25', '2025-08-31', 25.00, N'Summer Sale', 500),
       (N'VIP15', '2025-12-31', 15.00, N'VIP Discount', 200),
       (N'FLASH50', '2025-06-01', 50.00, N'Flash Sale', 50);

-- Orders
INSERT INTO [
Order] (accountId, voucherId, shipping, paymentMethod, status, orderDate)
VALUES
    (1, 1, 15.00, N'Credit Card', N'Pending', '2025-05-30'), (2, NULL, 10.00, N'PayPal', N'Shipped', '2025-05-29'), (3, 2, 0.00, N'Bank Transfer', N'Cancelled', '2025-05-28'), (1, NULL, 15.00, N'Credit Card', N'Completed', '2025-05-27'), (4, 3, 20.00, N'Credit Card', N'Pending', '2025-05-26');

-- Cart Items
INSERT INTO CartItem (accountId, productId, orderId, quantity)
VALUES (1, 1, NULL, 1),
       (1, 3, NULL, 1),
       (2, 2, 1, 1),
       (3, 5, 1, 1),
       (4, 7, 3, 1);

-- Order Details
INSERT INTO OrderDetail (orderId, fullName, phone, email, address, orderNote)
VALUES (1, N'John Doe', N'+1-555-123-4567', N'john.doe@example.com', N'123 Main St, New York, NY 10001',
        N'Please gift wrap'),
       (3, N'John Doe', N'+1-555-123-4567', N'john.doe@example.com', N'123 Main St, New York, NY 10001', NULL),
       (2, N'Jane Smith', N'+1-555-987-6543', N'jane.smith@example.com', N'456 Oak Ave, Los Angeles, CA 90001', NULL),
       (5, N'Robert Johnson', N'+1-555-246-8135', N'robert.johnson@example.com', N'789 Pine Rd, Chicago, IL 60007',
        N'Call before delivery');
GO
