INSERT INTO roles (name)
VALUES ('Administrator'),
       ('Operator');

INSERT INTO users (username, password, email, full_name, active)
VALUES ('admin',
        '$2a$10$BRzsxMP2TSGDHLmyZS/Dde.AcEGwL0NzpBURsEstBumJS9zuiqZPO', -- 12345
        'kananazera@gmail.com',
        'Kanan Rahimli',
        TRUE);

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u,
     roles r
WHERE u.username = 'admin'
  AND r.name = 'ROLE_ADMIN';

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

INSERT INTO currencies (code, symbol, name)
VALUES ('AZN', '₼', 'Azərbaycan Manatı'),
       ('USD', '$', 'United States Dollar'),
       ('EUR', '€', 'Euro'),
       ('RUB', '₽', 'Русский Рубль'),
       ('TRY', '₺', 'Türk Lirası');

INSERT INTO settings (key, value, description)
VALUES ('default_currency', 'AZN', 'Standart valyuta'),
       ('base_backend_url', 'http://localhost:8080', 'Əsas backend ünvanı'),
       ('app_url', 'http://localhost:3000', 'Proqramın ünvanı'),
       ('app_name', 'Inventory Management System', 'Proqramın adı'),
       ('app_short_name', 'IMS', 'Proqramın qısa adı')
