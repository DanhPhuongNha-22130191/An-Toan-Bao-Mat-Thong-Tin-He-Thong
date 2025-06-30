CREATE TABLE Account
(
    accountId       bigint IDENTITY PRIMARY KEY,
    username        varchar(50) NOT NULL,
    password        varchar(255) NOT NULL,
    email           varchar(100) NOT NULL,
    publicKeyActive nvarchar(max),
    role            varchar(20) NOT NULL,
    isDeleted       bit DEFAULT 0
);
GO

CREATE TABLE Brand
(
    brandId bigint IDENTITY PRIMARY KEY,
    name    nvarchar(255) NOT NULL
);
GO

CREATE TABLE Cart
(
    cartId     bigint IDENTITY PRIMARY KEY,
    accountId  bigint REFERENCES Account(accountId),
    totalPrice decimal(18, 2),
    updateAt   date
);
GO

CREATE TABLE Category
(
    categoryId bigint IDENTITY PRIMARY KEY,
    name       nvarchar(255) NOT NULL,
    image      nvarchar(max)
);
GO

CREATE TABLE OrderSecurity
(
    orderSecurityId bigint IDENTITY PRIMARY KEY,
    publicKey       nvarchar(max),
    signature       nvarchar(max)
);
GO

CREATE TABLE Province
(
    id   bigint IDENTITY PRIMARY KEY,
    code int,
    name nvarchar(100)
);
GO

CREATE TABLE District
(
    id         bigint IDENTITY PRIMARY KEY,
    provinceId bigint REFERENCES Province(id),
    name       nvarchar(100),
    code       int
);
GO

CREATE TABLE Strap
(
    strapId  bigint IDENTITY PRIMARY KEY,
    color    nvarchar(50),
    material nvarchar(50),
    length   decimal(18, 2)
);
GO

CREATE TABLE Product
(
    productId       bigint IDENTITY PRIMARY KEY,
    name            nvarchar(255) NOT NULL,
    price           decimal(18, 2) NOT NULL,
    description     nvarchar(max),
    stock           int,
    image           varbinary(max),
    isTrending      bit DEFAULT 1,
    size            decimal(18, 2),
    waterResistance bit,
    brandId         bigint REFERENCES Brand(brandId),
    strapId         bigint REFERENCES Strap(strapId),
    isDeleted       bit DEFAULT 0
);
GO

CREATE TABLE CartItem
(
    cartItemId    bigint IDENTITY PRIMARY KEY,
    cartId        bigint REFERENCES Cart(cartId),
    productId     bigint REFERENCES Product(productId),
    quantity      int,
    priceSnapshot decimal(18, 2),
    nameSnapshot  nvarchar(255),
    imageSnapshot varbinary(max)
);
GO

CREATE TABLE Ward
(
    id         bigint IDENTITY PRIMARY KEY,
    name       nvarchar(100),
    districtId bigint REFERENCES District(id),
    code       int
);
GO

CREATE TABLE ShippingInfo
(
    shippingInfoId bigint IDENTITY PRIMARY KEY,
    recipientName  nvarchar(255),
    phoneNumber    varchar(20),
    addressLine    nvarchar(max),
    district       nvarchar(100),
    province       nvarchar(100),
    ward           nvarchar(100),
    note           nvarchar(max),
    shippingMethod varchar(100),
    shippingFee    decimal(18, 2),
    provinceId     bigint REFERENCES Province(id),
    districtId     bigint REFERENCES District(id),
    wardId         bigint REFERENCES Ward(id)
);
GO

CREATE TABLE Orders
(
    orderId         bigint IDENTITY PRIMARY KEY,
    accountId       bigint REFERENCES Account(accountId),
    orderSecurityId bigint REFERENCES OrderSecurity(orderSecurityId),
    shippingInfoId  bigint REFERENCES ShippingInfo(shippingInfoId),
    totalPrice      decimal(18, 2),
    status          varchar(20),
    paymentMethod   varchar(20),
    orderAt         datetime
);
GO

CREATE TABLE OrderItem
(
    orderItemId   bigint IDENTITY PRIMARY KEY,
    orderId       bigint REFERENCES Orders(orderId),
    productId     bigint REFERENCES Product(productId),
    quantity      int,
    nameSnapshot  nvarchar(255),
    priceSnapshot decimal(18, 2),
    imageSnapshot varbinary(max)
);
GO


-- Insert sample data

INSERT INTO Account (username, password, email, publicKeyActive, role, isDeleted)
VALUES
    ('admin', 'hashed_password_123', 'admin@example.com', NULL, 'ADMIN', 0),
    ('user1', 'hashed_password_456', 'user1@example.com', NULL, 'USER', 0);

INSERT INTO Brand (name)
VALUES
    (N'Casio Edifice'),
    (N'Swatch'),
    (N'Citizen'),
    (N'Orient'),
    (N'Tissot');

INSERT INTO Category (name, image)
VALUES
    (N'Luxury Watches', NULL),
    (N'Sport Watches', NULL);

INSERT INTO Strap (color, material, length)
VALUES
    (N'Đen', N'Da bò', 22.0),
    (N'Nâu', N'Da cá sấu', 21.0),
    (N'Bạc', N'Thép không gỉ', 20.0),
    (N'Xanh', N'Vải Nato', 24.0),
    (N'Đỏ', N'Cao su', 22.0);


INSERT INTO Product (name, price, description, stock, image, size, waterResistance, brandId, strapId, isDeleted)
VALUES
    (N'Casio Edifice EFV-540D', 4500000, N'Đồng hồ thể thao nam Casio chính hãng', 15, NULL, 44.0, 1, (SELECT brandId FROM Brand WHERE name = N'Casio Edifice'), (SELECT strapId FROM Strap WHERE color = N'Bạc' AND material = N'Thép không gỉ'), 0),
    (N'Swatch Sistem51', 3500000, N'Đồng hồ cơ tự động Swatch', 20, NULL, 42.0, 1, (SELECT brandId FROM Brand WHERE name = N'Swatch'), (SELECT strapId FROM Strap WHERE color = N'Đen' AND material = N'Da bò'), 0),
    (N'Citizen Eco-Drive BM7480-03E', 6200000, N'Đồng hồ năng lượng ánh sáng Citizen', 10, NULL, 40.0, 1, (SELECT brandId FROM Brand WHERE name = N'Citizen'), (SELECT strapId FROM Strap WHERE color = N'Nâu' AND material = N'Da cá sấu'), 0),
    (N'Orient Bambino V4', 4200000, N'Đồng hồ cơ thanh lịch của Orient', 12, NULL, 40.5, 1, (SELECT brandId FROM Brand WHERE name = N'Orient'), (SELECT strapId FROM Strap WHERE color = N'Nâu' AND material = N'Da cá sấu'), 0),
    (N'Tissot PR100', 9000000, N'Dòng đồng hồ sang trọng Tissot', 8, NULL, 39.0, 1, (SELECT brandId FROM Brand WHERE name = N'Tissot'), (SELECT strapId FROM Strap WHERE color = N'Bạc' AND material = N'Thép không gỉ'), 0),
    (N'Casio G-Shock GA-2100', 3500000, N'Đồng hồ chống sốc G-Shock', 30, NULL, 45.4, 1, (SELECT brandId FROM Brand WHERE name = N'Casio Edifice'), (SELECT strapId FROM Strap WHERE color = N'Đỏ' AND material = N'Cao su'), 0),
    (N'Swatch Irony', 3200000, N'Đồng hồ Swatch chất liệu kim loại', 18, NULL, 41.0, 1, (SELECT brandId FROM Brand WHERE name = N'Swatch'), (SELECT strapId FROM Strap WHERE color = N'Xanh' AND material = N'Vải Nato'), 0),
    (N'Citizen Promaster Diver', 7800000, N'Đồng hồ lặn Citizen', 10, NULL, 44.0, 1, (SELECT brandId FROM Brand WHERE name = N'Citizen'), (SELECT strapId FROM Strap WHERE color = N'Đen' AND material = N'Da bò'), 0),
    (N'Orient Star Classic', 9500000, N'Dòng đồng hồ cơ cao cấp của Orient', 6, NULL, 41.0, 1, (SELECT brandId FROM Brand WHERE name = N'Orient'), (SELECT strapId FROM Strap WHERE color = N'Nâu' AND material = N'Da cá sấu'), 0),
    (N'Tissot Le Locle', 9999999, N'Đồng hồ Tissot sang trọng', 7, NULL, 39.3, 1, (SELECT brandId FROM Brand WHERE name = N'Tissot'), (SELECT strapId FROM Strap WHERE color = N'Bạc' AND material = N'Thép không gỉ'), 0),
    (N'Casio Vintage', 1200000, N'Đồng hồ điện tử Casio cổ điển', 50, NULL, 36.0, 0, (SELECT brandId FROM Brand WHERE name = N'Casio Edifice'), (SELECT strapId FROM Strap WHERE color = N'Đen' AND material = N'Da bò'), 0),
    (N'Swatch Chrono', 4300000, N'Đồng hồ Swatch có chức năng chronograph', 14, NULL, 42.0, 1, (SELECT brandId FROM Brand WHERE name = N'Swatch'), (SELECT strapId FROM Strap WHERE color = N'Xanh' AND material = N'Vải Nato'), 0),
    (N'Citizen Automatic', 5200000, N'Đồng hồ Citizen tự động', 11, NULL, 40.0, 1, (SELECT brandId FROM Brand WHERE name = N'Citizen'), (SELECT strapId FROM Strap WHERE color = N'Nâu' AND material = N'Da cá sấu'), 0),
    (N'Orient Ray II', 4000000, N'Đồng hồ lặn Orient', 9, NULL, 41.5, 1, (SELECT brandId FROM Brand WHERE name = N'Orient'), (SELECT strapId FROM Strap WHERE color = N'Đen' AND material = N'Da bò'), 0),
    (N'Tissot T-Touch Expert', 9999999, N'Đồng hồ đa chức năng Tissot', 5, NULL, 45.5, 1, (SELECT brandId FROM Brand WHERE name = N'Tissot'), (SELECT strapId FROM Strap WHERE color = N'Bạc' AND material = N'Thép không gỉ'), 0),
    (N'Casio Pro Trek', 7000000, N'Đồng hồ dành cho dân du lịch, leo núi', 10, NULL, 47.0, 1, (SELECT brandId FROM Brand WHERE name = N'Casio Edifice'), (SELECT strapId FROM Strap WHERE color = N'Cao su' AND material = N'Cao su'), 0),
    (N'Swatch Sistem51 Irony', 4800000, N'Đồng hồ cơ Swatch hệ Irony', 13, NULL, 43.0, 1, (SELECT brandId FROM Brand WHERE name = N'Swatch'), (SELECT strapId FROM Strap WHERE color = N'Bạc' AND material = N'Thép không gỉ'), 0),
    (N'Citizen Satellite Wave', 9999999, N'Đồng hồ Citizen công nghệ cao', 4, NULL, 42.0, 1, (SELECT brandId FROM Brand WHERE name = N'Citizen'), (SELECT strapId FROM Strap WHERE color = N'Đen' AND material = N'Da bò'), 0),
    (N'Orient Mako II', 4300000, N'Đồng hồ lặn Orient Mako', 8, NULL, 41.5, 1, (SELECT brandId FROM Brand WHERE name = N'Orient'), (SELECT strapId FROM Strap WHERE color = N'Nâu' AND material = N'Da cá sấu'), 0),
    (N'Tissot PRC 200', 8000000, N'Dòng thể thao Tissot PRC 200', 10, NULL, 41.0, 1, (SELECT brandId FROM Brand WHERE name = N'Tissot'), (SELECT strapId FROM Strap WHERE color = N'Xanh' AND material = N'Vải Nato'), 0);

INSERT INTO Cart (accountId, totalPrice, updateAt)
VALUES
    (2, 0, CAST(GETDATE() AS date));
