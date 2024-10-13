
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
CREATE SCHEMA transactions AUTHORIZATION postgres;

CREATE TABLE transactions.account (
    id BIGSERIAL PRIMARY KEY,  -- Clave primaria autoincremental
    number_account VARCHAR(50) NOT NULL UNIQUE, -- Número de cuenta (máximo 50 caracteres)
    type VARCHAR(20) NOT NULL, -- Tipo de cuenta (máximo 20 caracteres)
    init_balance DECIMAL(15, 2) NOT NULL, -- Saldo inicial con dos decimales
    state BOOLEAN NOT NULL, -- Estado de la cuenta (activo/inactivo)
    client_id INTEGER NOT NULL  -- Identificador del cliente (relación con otra tabla de cliente, si existe)
);

CREATE TABLE transactions.movements (
    id BIGSERIAL PRIMARY KEY,  -- Clave primaria autoincremental
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Fecha de creación, se genera automáticamente
    type VARCHAR(20) NOT NULL,  -- Tipo de movimiento (debe ser representado como texto, ya que es un enum en Java)
    value DECIMAL(15, 2) NOT NULL,  -- Valor del movimiento
    balance DECIMAL(15, 2) NOT NULL,  -- Balance después del movimiento
    description VARCHAR(255),  -- Descripción del movimiento (opcional)
    account_id BIGINT NOT NULL,  -- Referencia a la cuenta (relación con la tabla 'account')
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES transactions.account(id) -- Clave foránea hacia la tabla account
);