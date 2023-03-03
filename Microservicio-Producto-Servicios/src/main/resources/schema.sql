DROP TABLE IF EXISTS Services;
DROP TABLE IF EXISTS Appointments;
DROP TABLE IF EXISTS Products;

CREATE TABLE IF NOT EXISTS Appointments(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid UUID NOT NULL UNIQUE,
    user_id TEXT NOT NULL,
    assistance TEXT NOT NULL,
    date DATE NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Products(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid UUID NOT NULL,
    image TEXT NOT NULL,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    description TEXT NOT NULL,
    price FLOAT,
    discount_percentage FLOAT,
    stock TEXT NOT NULL,
    is_available BOOLEAN NOT NULL,
    type TEXT NOT NULL
    );

CREATE TABLE IF NOT EXISTS Services(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid UUID NOT NULL,
    image TEXT NOT NULL,
    price FLOAT,
    appointment UUID REFERENCES Appointments (uuid),
    type TEXT NOT NULL
);
