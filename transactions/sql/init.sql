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