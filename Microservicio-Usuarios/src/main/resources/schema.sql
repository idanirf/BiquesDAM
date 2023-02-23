DROP TABLE IF EXISTS USERS;

CREATE TABLE IF NOT EXISTS USERS
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    uuid UUID UNIQUE NOT NULL,
    image TEXT,
    type TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    address TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE,
    last_password_changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- USERS
-- Contraseña: pepe1234
INSERT INTO USERS (uuid, image, type, email, username, password, address)
VALUES (
'b39a2fd2-f7d7-405d-b73c-b68a8dedbcdf',
'https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png',
'ADMIN, USER',
'pepe@perez.com',
'pepe',
'$2a$12$249dkPGBT6dH46f4Dbu7ouEuO8eZ7joonzWGefPJbHH8eDpJy0oCq',
'Pepe'
);

-- Contraseña: ana1234
INSERT INTO USERS (uuid, image, type, email, username, password, address)
VALUES (
'c53062e4-31ea-4f5e-a99d-36c228ed01a3',
'https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png',
'USER',
'ana@lopez.com',
'ana',
'$2a$12$ZymlZf4Ja48WpBliFEU0qOUwb6HEJnhzlKYUoywhCxutkf1BzMbW2',
'Ana'
);