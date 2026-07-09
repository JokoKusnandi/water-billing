CREATE SCHEMA IF NOT EXISTS water_billing;

SET search_path TO water_billing;

-- =====================================================
-- CUSTOMERS
-- =====================================================

CREATE TABLE customers
(
    customer_id VARCHAR(20) PRIMARY KEY,
    customer_name VARCHAR(150) NOT NULL,
    email VARCHAR(120),
    phone VARCHAR(30),
    address TEXT,
    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP
);

-- =====================================================
-- TARIFFS
-- =====================================================

CREATE TABLE tariffs
(
    tariff_code         VARCHAR(10) PRIMARY KEY,
    tariff_name         VARCHAR(100) NOT NULL,

    price_per_m3        NUMERIC(18,2) NOT NULL,

    penalty_percents     NUMERIC(8,4) NOT NULL,

    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP
);

-- =====================================================
-- METERS
-- =====================================================

CREATE TABLE meters
(
    meter_id        BIGSERIAL PRIMARY KEY,

    customer_id     VARCHAR(20) NOT NULL,

    tariff_code     VARCHAR(10) NOT NULL,

    serial_number   VARCHAR(100) NOT NULL,

    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP,

    CONSTRAINT uk_meter_sn
        UNIQUE(serial_number),

    CONSTRAINT fk_meter_customer
        FOREIGN KEY(customer_id)
            REFERENCES customers(customer_id),

    CONSTRAINT fk_meter_tariff
        FOREIGN KEY(tariff_code)
            REFERENCES tariffs(tariff_code)
);

-- =====================================================
-- METER READINGS
-- =====================================================

CREATE TABLE meter_readings
(
    reading_id          BIGSERIAL PRIMARY KEY,

    meter_id            BIGINT NOT NULL,

    period              VARCHAR(6) NOT NULL,

    previous_reading    NUMERIC(18,2) NOT NULL,

    current_reading     NUMERIC(18,2) NOT NULL,

    usage               NUMERIC(18,2) NOT NULL,

    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP,

    CONSTRAINT uk_meter_period
        UNIQUE(meter_id, period),

    CONSTRAINT fk_reading_meter
        FOREIGN KEY(meter_id)
            REFERENCES meters(meter_id)
);

-- =====================================================
-- BILLINGS
-- =====================================================

CREATE TABLE billings
(
    billing_id      VARCHAR(30) PRIMARY KEY,

    reading_id      BIGINT NOT NULL UNIQUE,

    amount          NUMERIC(18,2) NOT NULL,

    due_date        DATE NOT NULL,

    status          VARCHAR(20) NOT NULL,

    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP,

    CONSTRAINT fk_billing_reading
        FOREIGN KEY(reading_id)
            REFERENCES meter_readings(reading_id)
);

-- =====================================================
-- PAYMENTS
-- =====================================================

CREATE TABLE payments
(
    payment_id          UUID PRIMARY KEY,

    payment_number      VARCHAR(50) NOT NULL UNIQUE,

    billing_id          VARCHAR(30) NOT NULL UNIQUE,

    pay_datetime        TIMESTAMP NOT NULL,

    penalty             NUMERIC(18,2) NOT NULL,

    amount_total        NUMERIC(18,2) NOT NULL,

    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP,

    CONSTRAINT fk_payment_billing
        FOREIGN KEY(billing_id)
            REFERENCES billings(billing_id)
);

-- =====================================================
-- PAYMENT IDEMPOTENCY
-- =====================================================

CREATE TABLE payment_idempotencies
(
    id                  BIGSERIAL PRIMARY KEY,

    idempotency_key     VARCHAR(120) NOT NULL,

    billing_id          VARCHAR(30) NOT NULL,

    payment_id          UUID,

    endpoint            VARCHAR(255),

    response_body       TEXT,

    status_code         INTEGER,

    created_at          TIMESTAMP NOT NULL,

    CONSTRAINT uk_payment_idempotency_key
        UNIQUE(idempotency_key),

    CONSTRAINT fk_payment_idempotency_billing
        FOREIGN KEY (billing_id)
            REFERENCES billings(billing_id),

    CONSTRAINT fk_payment_idempotency_payment
        FOREIGN KEY (payment_id)
            REFERENCES payments(payment_id)
);

-- =====================================================
-- AUDIT LOGS
-- =====================================================

CREATE TABLE audit_logs
(
    audit_id            UUID PRIMARY KEY,
    event               VARCHAR(100) NOT NULL,
    reference_id        VARCHAR(100),
    customer_id         VARCHAR(20),
    username            VARCHAR(100),
    ip_address          VARCHAR(50),
    device              VARCHAR(255),
    version             BIGINT DEFAULT 0,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP
);

-- =====================================================
-- INDEXES
-- =====================================================
CREATE INDEX idx_customer_name
    ON customers(customer_name);

CREATE INDEX idx_customer_email
    ON customers(email);

CREATE INDEX idx_meter_customer
    ON meters(customer_id);

CREATE INDEX idx_meter_tariff
    ON meters(tariff_code);

CREATE INDEX idx_reading_period
    ON meter_readings(period);

CREATE INDEX idx_reading_meter
    ON meter_readings(meter_id);

CREATE INDEX idx_billing_status
    ON billings(status);

CREATE INDEX idx_due_date
    ON billings(due_date);

CREATE INDEX idx_payment_datetime
    ON payments(pay_datetime);

CREATE INDEX idx_payment_number
    ON payments(payment_number);

CREATE INDEX idx_idempotency_key
    ON payment_idempotencies(idempotency_key);

CREATE INDEX idx_idempotency_payment
    ON payment_idempotencies(payment_id);

CREATE INDEX idx_idempotency_billing
    ON payment_idempotencies(billing_id);

CREATE INDEX idx_idempotency_created_at
    ON payment_idempotencies(created_at);

CREATE INDEX idx_audit_event
    ON audit_logs(event);

CREATE INDEX idx_audit_reference
    ON audit_logs(reference_id);

CREATE INDEX idx_audit_customer
    ON audit_logs(customer_id);

CREATE INDEX idx_audit_created
    ON audit_logs(created_at);