CREATE TABLE Product (
    productId BIGINT PRIMARY KEY IDENTITY(1,1),
    categoryId BIGINT NOT NULL,
    name NVARCHAR(255) NOT NULL,
    price FLOAT NOT NULL,
    description NVARCHAR(MAX) NULL,
    stock INT NOT NULL,
    image NVARCHAR(500) NULL,
    haveTrending BIT NOT NULL,
    --	FOREIGN KEY (categoryId) REFERENCES Category(categoryId)
);

CREATE TABLE Voucher (
    voucherId BIGINT IDENTITY(1,1) PRIMARY KEY,
    code NVARCHAR(255) NOT NULL UNIQUE,
    expirationTime DATE NOT NULL,
    percentDescrease FLOAT NOT NULL,
    name NVARCHAR(255) NOT NULL,
    quantity INT NOT NULL
);


CREATE TABLE Orders (
    orderId BIGINT PRIMARY KEY IDENTITY(1,1),
    accountId BIGINT NOT NULL,
    shipping FLOAT NOT NULL,
    paymentMethod NVARCHAR(255) NOT NULL,
    voucherId BIGINT NULL,
    --	FOREIGN KEY (accountId) REFERENCES Account(accountId),
    FOREIGN KEY (voucherId) REFERENCES Voucher(voucherId)
);

CREATE TABLE OrderDetail (
    orderDetailId BIGINT PRIMARY KEY IDENTITY(1,1),
    orderId BIGINT NOT NULL,
    fullName NVARCHAR(255) NOT NULL,
    phone NVARCHAR(50) NOT NULL,
    email NVARCHAR(255) NOT NULL,
    address NVARCHAR(500) NOT NULL,
    orderNote NVARCHAR(MAX) NULL,
    FOREIGN KEY (orderId) REFERENCES Orders(orderId)
);

CREATE TABLE CartItem (
    cartItemId BIGINT PRIMARY KEY IDENTITY(1,1),
    accountId BIGINT NOT NULL,
    productId BIGINT NOT NULL,
    orderId BIGINT NULL,
    quantity INT NOT NULL
	--	FOREIGN KEY (productId) REFERENCES Product(productId),
    FOREIGN KEY (orderId) REFERENCES Orders(orderId)
);

CREATE TABLE UsedVouchers (
    accountId BIGINT NOT NULL,
    code NVARCHAR(255) NOT NULL,
    usedAt DATE NOT NULL,
    PRIMARY KEY (accountId, code)
);

--Dữ liệu mẫu
-- Chèn dữ liệu mẫu vào bảng Product
INSERT INTO Product (categoryId, name, price, description, stock, image, haveTrending) 
VALUES 
( 1,N'Rượu Vang Đỏ', 1200000, N'Rượu vang đỏ cao cấp nhập khẩu', 50, 'images/vangdo.jpg', 1),
( 1,N'Rượu Whisky', 1500000, N'Rượu Whisky hảo hạng', 30, 'images/whisky.jpg', 1),
( 1,N'Rượu Sake Nhật Bản', 900000, N'Rượu sake truyền thống Nhật Bản', 40, 'images/sake.jpg', 0);

-- Chèn dữ liệu mẫu vào bảng CartItem
INSERT INTO CartItem ( accountId, productId, orderId, quantity) 
VALUES 
( 101, 1, null, 2),  -- Người dùng 101 mua 2 chai Rượu Vang Đỏ
( 101, 2, null, 1),  -- Người dùng 102 mua 1 chai Rượu Whisky
( 101, 3, null, 3);  -- Người dùng 103 mua 3 chai Rượu Sake Nhật Bản

-- Chèn dữ liệu mẫu vào bảng Voucher
INSERT INTO Voucher (code, expirationTime, percentDescrease, name, quantity) 
VALUES 
(N'NEWYEAR2025', '2025-01-01', 10, N'Khuyến mãi năm mới', 100),
(N'SUMMER50', '2025-06-30', 5, N'Giảm giá mùa hè', 200),
(N'VIPCUSTOMER', '2025-12-31', 15, N'Ưu đãi khách hàng VIP', 50);
