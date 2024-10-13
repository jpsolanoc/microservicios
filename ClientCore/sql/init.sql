CREATE SCHEMA client_core AUTHORIZATION postgres;
CREATE TABLE client_core.person (
    id BIGSERIAL PRIMARY KEY,  -- Clave primaria, se comparte con la tabla customer
    name VARCHAR(255) NOT NULL, -- Nombre
    gender VARCHAR(10),         -- Género
    age INTEGER,                -- Edad
    dni VARCHAR(20) UNIQUE,     -- Documento de identificación, único
    address VARCHAR(255),       -- Dirección
    phone VARCHAR(20),          -- Teléfono
    delete TIMESTAMP            -- Fecha de eliminación (soft delete)
);

CREATE TABLE client_core.customer (
    id BIGINT PRIMARY KEY,      -- Clave primaria, misma que en la tabla person (herencia JOINED)
    password VARCHAR(255) NOT NULL, -- Contraseña del cliente
    state BOOLEAN NOT NULL,     -- Estado (activo/inactivo)
    CONSTRAINT fk_person FOREIGN KEY (id) REFERENCES person(id) -- Relación con la tabla person
);