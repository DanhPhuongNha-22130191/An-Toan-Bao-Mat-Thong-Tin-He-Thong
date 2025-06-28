create table Account
(
    accountId       bigint identity
        primary key,
    username        varchar(50)  not null,
    password        varchar(255) not null,
    email           varchar(100) not null,
    publicKeyActive text,
    role            varchar(20)  not null,
    isDeleted       bit default 0
)
    go

create table Brand
(
    brandId bigint identity
        primary key,
    name    nvarchar(255) not null
)
    go

create table Cart
(
    cartId     bigint identity
        primary key,
    accountId  bigint
        references Account,
    totalPrice decimal(18, 2),
    updateAt   date
)
    go

create table Category
(
    categoryId bigint identity
        primary key,
    name       nvarchar(255) not null,
    image      text
)
    go

create table OrderSecurity
(
    orderSecurityId bigint identity
        primary key,
    publicKey       text,
    signature       text
)
    go

create table Province
(
    id   bigint identity
        primary key,
    code int,
    name nvarchar(100)
)
    go

create table District
(
    id         bigint identity
        primary key,
    provinceId bigint
        references Province,
    name       nvarchar(100),
    code       int
)
    go

create table Strap
(
    strapId  bigint identity
        primary key,
    color    nvarchar(50),
    material nvarchar(50),
    length   decimal(18, 2)
)
    go

create table Product
(
    productId       bigint identity
        primary key,
    name            nvarchar(255)  not null,
    price           decimal(18, 2) not null,
    description     text,
    stock           int,
    image           varbinary(max),
    isTrending      bit default 1,
    size            decimal(18, 2),
    waterResistance bit,
    brandId         bigint
        references Brand,
    strapId         bigint
        references Strap,
    isDeleted       bit default 0
)
    go

create table CartItem
(
    cartItemId    bigint identity
        primary key,
    cartId        bigint
        references Cart,
    productId     bigint
        references Product,
    quantity      int,
    priceSnapshot decimal(18, 2),
    nameSnapshot  nvarchar(255),
    imageSnapshot varbinary(max)
)
    go

create table Ward
(
    id         bigint identity
        primary key,
    name       nvarchar(100),
    districtId bigint
        references District,
    code       int
)
    go

create table ShippingInfo
(
    shippingInfoId bigint identity
        primary key,
    recipientName  nvarchar(255),
    phoneNumber    varchar(20),
    addressLine    text,
    district       nvarchar(100),
    province       nvarchar(100),
    ward           nvarchar(100),
    note           text,
    shippingMethod varchar(100),
    shippingFee    decimal(18, 2),
    provinceId     bigint
        references Province,
    districtId     bigint
        references District,
    wardId         bigint
        references Ward
)
    go

create table Orders
(
    orderId         bigint identity
        primary key,
    accountId       bigint
        references Account,
    orderSecurityId bigint
        references OrderSecurity,
    shippingInfoId  bigint
        references ShippingInfo,
    totalPrice      decimal(18, 2),
    status          varchar(20),
    paymentMethod   varchar(20),
    orderAt         datetime
)
    go

create table OrderItem
(
    orderItemId   bigint identity
        primary key,
    orderId       bigint
        references Orders,
    productId     bigint
        references Product,
    quantity      int,
    nameSnapshot  nvarchar(255),
    priceSnapshot decimal(18, 2),
    imageSnapshot varbinary(max)
)
    go


insert into Account (username, password, email, publicKeyActive, role, isDeleted)
values
    ('admin', 'hashed_password_123', 'admin@example.com', null, 'ADMIN', 0),
    ('user1', 'hashed_password_456', 'user1@example.com', null, 'USER', 0);

insert into Brand (name)
values
    (N'Rolex'),
    (N'Omega'),
    (N'Casio');

insert into Category (name, image)
values
    (N'Luxury Watches', null),
    (N'Sport Watches', null);

insert into Strap (color, material, length)
values
    (N'Black', N'Leather', 22.0),
    (N'Silver', N'Steel', 20.0);

insert into Product (name, price, description, stock, image, size, waterResistance, brandId, strapId, isDeleted)
values
    (N'Rolex Submariner', 8500.00, N'Luxury diver''s watch', 10, null, 40.0, 1, 1, 1, 0),
    (N'Casio G-Shock', 150.00, N'Rugged and affordable watch', 25, null, 45.0, 1, 3, 2, 0);

insert into Cart (accountId, totalPrice, updateAt)
values
    (2, 0, GETDATE());

