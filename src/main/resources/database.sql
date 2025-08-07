INSERT INTO roles (name)
VALUES ('Administrator'),
       ('Operator');

INSERT INTO users (username, password, email, full_name, active)
VALUES ('admin',
        '$2a$10$BRzsxMP2TSGDHLmyZS/Dde.AcEGwL0NzpBURsEstBumJS9zuiqZPO', -- 12345
        'kananazera@gmail.com',
        'Kanan Rahimli',
        TRUE),
       ('kananazera',
        '$2a$10$BRzsxMP2TSGDHLmyZS/Dde.AcEGwL0NzpBURsEstBumJS9zuiqZPO', -- 12345
        'kananazera@mail.ru',
        'Kanan Rahimli',
        TRUE);

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u,
     roles r
WHERE u.username = 'admin'
  AND r.name = 'ROLE_ADMIN';

INSERT INTO customers (email, full_name, phone, address, gender, birth_date, active, contact_type, tin)
VALUES ('ali@gmail.com', 'Ali Məmmədov', '+994501112233', 'Bakı, Nərimanov', '1', '1990-03-15', true, 'INDIVIDUAL',
        123456789),

       ('leyla@gmail.com', 'Leyla Quliyeva', '+994501223344', 'Gəncə, Kəpəz', '0', '1995-06-22', true,
        'INDIVIDUAL', 223344556),

       ('farida@gmail.com', 'Farida Agayeva', '+994501334455', 'Sumqayıt şəhəri', null, null, true, 'COMPANY',
        null),

       ('orxan@gmail.com', 'Orxan MMC', '+994501445566', 'Bakı, Yasamal', null, null, true, 'COMPANY',
        998877665),

       ('nurlan@gmail.com', 'Nurlan İsmayılov', '+994501556677', 'Şəki şəhəri', '1', '1988-11-01', true,
        'INDIVIDUAL', 334455667),

       ('nigar@gmail.com', 'Nigar Hüseynova', '+994501667788', 'Bərdə rayonu', '0', '1992-08-05', true,
        'INDIVIDUAL', 445566778);

INSERT INTO suppliers (email, full_name, phone, address, gender, birth_date, active, contact_type, tin)
VALUES ('murad@gmail.com', 'Murad Logistics MMC', '+994501778899', 'Bakı, Xətai', null, null, true, 'COMPANY',
        112233445),

       ('aygun@gmail.com', 'Aygün Əliyeva', '+994501889900', 'Mingəçevir', '0', '1985-01-30', true, 'INDIVIDUAL',
        556677889),

       ('rza@gmail.com', 'Rza Rzayev', '+994501991122', 'Lənkəran', '1', '1993-04-10', true, 'INDIVIDUAL',
        null),

       ('kanan@gmail.com', 'Kanan MMC', '+994501234567', 'Bakı, Sabunçu', null, null, true, 'COMPANY',
        889900112);

INSERT INTO product_categories (name)
VALUES ('Qida'),
       ('Qeyri-qida'),
       ('İçki'),
       ('Elektronika'),
       ('Geyim'),
       ('Ayaqqabı'),
       ('Mebel'),
       ('Məişət texnikası'),
       ('Kitab'),
       ('Oyuncaq'),
       ('Kosmetika'),
       ('Şəxsi qulluq'),
       ('İdman avadanlıqları'),
       ('Avtomobil aksesuarları'),
       ('Ofis və məktəb ləvazimatları'),
       ('Ev dekoru'),
       ('Bağ məhsulları');

INSERT INTO product_brands (name)
VALUES ('Samsung'),
       ('Apple'),
       ('LG'),
       ('Sony'),
       ('Panasonic'),
       ('HP'),
       ('Dell'),
       ('Adidas'),
       ('Nike'),
       ('Puma'),
       ('Zara'),
       ('IKEA'),
       ('Bosch'),
       ('Philips'),
       ('Coca-Cola'),
       ('Pepsi'),
       ('Nestle'),
       ('Milla'),
       ('Red Bull'),
       ('Toyota'),
       ('Ford');

INSERT INTO product_units (name)
VALUES ('Ədəd'),
       ('Qutu'),
       ('Kilogram'),
       ('Litr'),
       ('Paket'),
       ('Cüt'),
       ('Dəst');

INSERT INTO products (name, sku, description, price, active, category_id, brand_id, unit_id, image_url)
VALUES ('Nar Şirəsi 1L', 'UQ2KDR7F1E', 'Təbii Azərbaycan narı ilə hazırlanmış 1 litrlik şirə', 2.50, true, 1, 1, 1,
        'http://localhost:8080/upload/products/30f897ab-dcdd-439c-91e1-563599cd2204_1.png'),

       ('Ağ şokalad 90g', 'MPFIHY5AV3', 'Azərbaycan istehsalı ağ şokolad 90 qram', 1.20, true, 2, 2, 2,
        'http://localhost:8080/upload/products/89800312-3632-4754-8fb9-052ccbf3953c_2.png'),

       ('Qurut 250g', '7EI00CB81W', 'Ənənəvi üsulla hazırlanmış təbii qurut 250 qram', 3.00, true, 3, 3, 2,
        'http://localhost:8080/upload/products/0ba24106-d7a0-49ae-b264-9e39a26b5203_3.png'),

       ('AzPhone X1', '8URS64N9DK', 'Azərbaycan brendi olan AzPhone yeni smartfon modeli.', 399.99, true, 4, 4, 4,
        'http://localhost:8080/upload/products/9723b559-51e9-402f-9b84-a292cae94d3e_4.png'),

       ('LED Masa Lampası', '2ZZTE565OM', 'Enerji sərfiyyatı az olan LED masa lampası', 14.75, true, 5, 5, 4,
        'http://localhost:8080/upload/products/77460f68-5043-4aad-a2bf-704ee28c08f9_5.png');

INSERT INTO currencies (code, symbol, name)
VALUES ('AZN', '₼', 'Azərbaycan Manatı'),
       ('USD', '$', 'United States Dollar'),
       ('EUR', '€', 'Euro'),
       ('RUB', '₽', 'Русский Рубль'),
       ('TRY', '₺', 'Türk Lirası');

INSERT INTO settings (key, value, description)
VALUES ('default_currency', '₼', 'Standart valyuta'),
       ('app_name', 'Inventory Management System', 'Proqramın adı'),
       ('app_short_name', 'IMS', 'Proqramın qısa adı'),
       ('default_language', 'az', 'Proqramın standart dili'),
       ('items_per_page', '50', 'Hər səhifədə məlumat sayı'),
       ('default_markup_rate ', '10', 'Standart qazanc faizi');

INSERT INTO warehouses (name, phone, email, address)
VALUES ('Nizami', '0501112233', 'nizami@inventory.az', 'C. Cabbarlı 44'),
       ('Yasamal', '0501224455', 'yasamal@inventory.az', 'Mirəli Qaşqay 148B');

INSERT INTO taxes (name, rate)
VALUES ('ƏDV', '18'),
       ('Gömrük rüsumu', '20');