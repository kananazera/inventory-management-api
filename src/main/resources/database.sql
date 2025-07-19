INSERT INTO roles (name)
VALUES ('ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO roles (name)
VALUES ('ROLE_USER') ON CONFLICT DO NOTHING;

INSERT INTO users (username, password, email, full_name, active)
VALUES ('admin',
        '$2a$10$BRzsxMP2TSGDHLmyZS/Dde.AcEGwL0NzpBURsEstBumJS9zuiqZPO', -- 12345
        'kananazera@gmail.com',
        'Kanan Rahimli',
        TRUE) ON CONFLICT (username) DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u,
     roles r
WHERE u.username = 'admin'
  AND r.name = 'ROLE_ADMIN' ON CONFLICT DO NOTHING;