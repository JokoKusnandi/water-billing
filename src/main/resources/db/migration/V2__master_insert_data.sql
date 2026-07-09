-- =====================================================
-- Customer
-- =====================================================

INSERT INTO water_billing.customers
(
    customer_id,
    customer_name,
    email,
    phone,
    address,
    version,
    created_at,
    updated_at
)
VALUES
    (
        'CUST001',
        'Joko Kusnandi',
        'joko@gmail.com',
        '081234567890',
        'Bandung',
        0,
        NOW(),
        NOW()
    ),
    (
        'CUST002',
        'Budi Santoso',
        'budi@gmail.com',
        '081111111111',
        'Jakarta',
        0,
        NOW(),
        NOW()
    ),
    (
        'CUST003',
        'Andi Wijaya',
        'andi@gmail.com',
        '082222222222',
        'Surabaya',
        0,
        NOW(),
        NOW()
    ),
    (
        'CUST004',
        'Siti Aminah',
        'siti@gmail.com',
        '083333333333',
        'Yogyakarta',
        0,
        NOW(),
        NOW()
    );

-- =====================================================
-- Tariff
-- =====================================================

INSERT INTO water_billing.tariffs
(
    tariff_code,
    tariff_name,
    price_per_m3,
    penalty_percents,
    version,
    created_at,
    updated_at
)
VALUES
    ('R1','Rumah Tangga A',2500.00,0.0200,0,NOW(),NOW()),
    ('R2','Rumah Tangga B',3500.00,0.0200,0,NOW(),NOW()),
    ('R3','Rumah Tangga C',5000.00,0.0300,0,NOW(),NOW()),
    ('B1','Bisnis Kecil',7500.00,0.0400,0,NOW(),NOW()),
    ('B2','Bisnis Besar',10000.00,0.0500,0,NOW(),NOW()),
    ('I1','Industri',12500.00,0.0500,0,NOW(),NOW()),
    ('S1','Sosial',1500.00,0.0100,0,NOW(),NOW()),
    ('P1','Pemerintah',4500.00,0.0200,0,NOW(),NOW());

-- =====================================================
-- Meter
-- =====================================================

INSERT INTO water_billing.meters
(
    customer_id,
    tariff_code,
    serial_number,
    version,
    created_at,
    updated_at
)
VALUES
    ('CUST001','R1','WM-202600001',0,NOW(),NOW()),
    ('CUST002','R2','WM-202600002',0,NOW(),NOW()),
    ('CUST003','R3','WM-202600003',0,NOW(),NOW()),
    ('CUST004','B1','WM-202600004',0,NOW(),NOW());

-- =====================================================
-- Meter Reading 202606
-- =====================================================

INSERT INTO water_billing.meter_readings
(
    meter_id,
    period,
    previous_reading,
    current_reading,
    usage,
    version
)
SELECT
    meter_id,
    '202606',
    0,
    25,
    25,
    0
FROM water_billing.meters
WHERE serial_number='WM-202600001';

INSERT INTO water_billing.meter_readings
(
    meter_id,
    period,
    previous_reading,
    current_reading,
    usage,
    version
)
SELECT
    meter_id,
    '202606',
    0,
    18,
    18,
    0
FROM water_billing.meters
WHERE serial_number='WM-202600002';

INSERT INTO water_billing.meter_readings
(
    meter_id,
    period,
    previous_reading,
    current_reading,
    usage,
    version
)
SELECT
    meter_id,
    '202606',
    0,
    42,
    42,
    0
FROM water_billing.meters
WHERE serial_number='WM-202600003';

INSERT INTO water_billing.meter_readings
(
    meter_id,
    period,
    previous_reading,
    current_reading,
    usage,
    version
)
SELECT
    meter_id,
    '202606',
    0,
    60,
    60,
    0
FROM water_billing.meters
WHERE serial_number='WM-202600004';

-- =====================================================
-- Meter Reading 202607
-- =====================================================

INSERT INTO water_billing.meter_readings
(
    meter_id,
    period,
    previous_reading,
    current_reading,
    usage,
    version,
    created_at,
    updated_at
)
SELECT
    meter_id,
    '202607',
    25,
    48,
    23,
    0,
    NOW(),
    NOW()
FROM water_billing.meters
WHERE serial_number='WM-202600001';

INSERT INTO water_billing.meter_readings
(
    meter_id,
    period,
    previous_reading,
    current_reading,
    usage,
    version,
    created_at,
    updated_at
)
SELECT
    meter_id,
    '202607',
    18,
    41,
    23,
    0,
    NOW(),
    NOW()
FROM water_billing.meters
WHERE serial_number='WM-202600002';

INSERT INTO water_billing.meter_readings
(
    meter_id,
    period,
    previous_reading,
    current_reading,
    usage,
    version,
    created_at,
    updated_at
)
SELECT
    meter_id,
    '202607',
    42,
    78,
    36,
    0,
    NOW(),
    NOW()
FROM water_billing.meters
WHERE serial_number='WM-202600003';

INSERT INTO water_billing.meter_readings
(
    meter_id,
    period,
    previous_reading,
    current_reading,
    usage,
    version,
    created_at,
    updated_at
)
SELECT
    meter_id,
    '202607',
    60,
    105,
    45,
    0,
    NOW(),
    NOW()
FROM water_billing.meters
WHERE serial_number='WM-202600004';

-- =====================================================
-- BILLINGS
-- =====================================================

INSERT INTO water_billing.billings
(
    billing_id,
    reading_id,
    amount,
    due_date,
    status,
    version,
    created_at,
    updated_at
)
SELECT
    'BILL202606001',
    mr.reading_id,
    62500.00,
    DATE '2026-06-20',
    'PAID',
    0,
    NOW(),
    NOW()
FROM water_billing.meter_readings mr
         JOIN water_billing.meters m
              ON mr.meter_id = m.meter_id
WHERE m.serial_number = 'WM-202600001'
  AND mr.period = '202606';


INSERT INTO water_billing.billings
(
    billing_id,
    reading_id,
    amount,
    due_date,
    status,
    version,
    created_at,
    updated_at
)
SELECT
    'BILL202606002',
    mr.reading_id,
    63000.00,
    DATE '2026-06-20',
    'PAID',
    0,
    NOW(),
    NOW()
FROM water_billing.meter_readings mr
         JOIN water_billing.meters m
              ON mr.meter_id = m.meter_id
WHERE m.serial_number = 'WM-202600002'
  AND mr.period = '202606';


INSERT INTO water_billing.billings
(
    billing_id,
    reading_id,
    amount,
    due_date,
    status,
    version,
    created_at,
    updated_at
)
SELECT
    'BILL202606003',
    mr.reading_id,
    210000.00,
    DATE '2026-06-20',
    'PAID',
    0,
    NOW(),
    NOW()
FROM water_billing.meter_readings mr
         JOIN water_billing.meters m
              ON mr.meter_id = m.meter_id
WHERE m.serial_number = 'WM-202600003'
  AND mr.period = '202606';


INSERT INTO water_billing.billings
(
    billing_id,
    reading_id,
    amount,
    due_date,
    status,
    version,
    created_at,
    updated_at
)
SELECT
    'BILL202606004',
    mr.reading_id,
    450000.00,
    DATE '2026-06-20',
    'PAID',
    0,
    NOW(),
    NOW()
FROM water_billing.meter_readings mr
         JOIN water_billing.meters m
              ON mr.meter_id = m.meter_id
WHERE m.serial_number = 'WM-202600004'
  AND mr.period = '202606';


-- =====================================================
-- BILLINGS JULY (UNPAID)
-- =====================================================

INSERT INTO water_billing.billings
(
    billing_id,
    reading_id,
    amount,
    due_date,
    status,
    version,
    created_at,
    updated_at
)
SELECT
    'BILL202607001',
    mr.reading_id,
    57500.00,
    DATE '2026-07-20',
    'UNPAID',
    0,
    NOW(),
    NOW()
FROM water_billing.meter_readings mr
         JOIN water_billing.meters m
              ON mr.meter_id = m.meter_id
WHERE m.serial_number = 'WM-202600001'
  AND mr.period = '202607';


INSERT INTO water_billing.billings
(
    billing_id,
    reading_id,
    amount,
    due_date,
    status,
    version,
    created_at,
    updated_at
)
SELECT
    'BILL202607002',
    mr.reading_id,
    80500.00,
    DATE '2026-07-20',
    'UNPAID',
    0,
    NOW(),
    NOW()
FROM water_billing.meter_readings mr
         JOIN water_billing.meters m
              ON mr.meter_id = m.meter_id
WHERE m.serial_number = 'WM-202600002'
  AND mr.period = '202607';


INSERT INTO water_billing.billings
(
    billing_id,
    reading_id,
    amount,
    due_date,
    status,
    version,
    created_at,
    updated_at
)
SELECT
    'BILL202607003',
    mr.reading_id,
    180000.00,
    DATE '2026-07-20',
    'UNPAID',
    0,
    NOW(),
    NOW()
FROM water_billing.meter_readings mr
         JOIN water_billing.meters m
              ON mr.meter_id = m.meter_id
WHERE m.serial_number = 'WM-202600003'
  AND mr.period = '202607';


INSERT INTO water_billing.billings
(
    billing_id,
    reading_id,
    amount,
    due_date,
    status,
    version,
    created_at,
    updated_at
)
SELECT
    'BILL202607004',
    mr.reading_id,
    337500.00,
    DATE '2026-07-20',
    'UNPAID',
    0,
    NOW(),
    NOW()
FROM water_billing.meter_readings mr
         JOIN water_billing.meters m
              ON mr.meter_id = m.meter_id
WHERE m.serial_number = 'WM-202600004'
  AND mr.period = '202607';

-- =====================================================
-- PAYMENTS
-- =====================================================

INSERT INTO water_billing.payments
(
    payment_id,
    payment_number,
    billing_id,
    pay_datetime,
    penalty,
    amount_total,
    version,
    created_at,
    updated_at
)
SELECT
    gen_random_uuid(),
    'PAY-2026060001',
    billing_id,
    TIMESTAMP '2026-06-15 09:15:00',
    0,
    amount,
    0,
    NOW(),
    NOW()
FROM water_billing.billings
WHERE billing_id = 'BILL202606001';


INSERT INTO water_billing.payments
(
    payment_id,
    payment_number,
    billing_id,
    pay_datetime,
    penalty,
    amount_total,
    version,
    created_at,
    updated_at
)
SELECT
    gen_random_uuid(),
    'PAY-2026060002',
    billing_id,
    TIMESTAMP '2026-06-15 10:20:00',
    0,
    amount,
    0,
    NOW(),
    NOW()
FROM water_billing.billings
WHERE billing_id = 'BILL202606002';


INSERT INTO water_billing.payments
(
    payment_id,
    payment_number,
    billing_id,
    pay_datetime,
    penalty,
    amount_total,
    version,
    created_at,
    updated_at
)
SELECT
    gen_random_uuid(),
    'PAY-2026060003',
    billing_id,
    TIMESTAMP '2026-06-15 11:00:00',
    0,
    amount,
    0,
    NOW(),
    NOW()
FROM water_billing.billings
WHERE billing_id = 'BILL202606003';


INSERT INTO water_billing.payments
(
    payment_id,
    payment_number,
    billing_id,
    pay_datetime,
    penalty,
    amount_total,
    version,
    created_at,
    updated_at
)
SELECT
    gen_random_uuid(),
    'PAY-2026060004',
    billing_id,
    TIMESTAMP '2026-06-15 14:30:00',
    0,
    amount,
    0,
    NOW(),
    NOW()
FROM water_billing.billings
WHERE billing_id = 'BILL202606004';

-- =====================================================
-- PAYMENT IDEMPOTENCIES
-- =====================================================

INSERT INTO water_billing.payment_idempotencies
(
    idempotency_key,
    billing_id,
    payment_id,
    endpoint,
    response_body,
    status_code,
    created_at
)
SELECT
    'PAY-REQ-000001',
    p.billing_id,
    p.payment_id,
    '/api/v1/payments',
    '{"status":"SUCCESS"}',
    201,
    NOW()
FROM water_billing.payments p
WHERE p.payment_number = 'PAY-2026060001';


INSERT INTO water_billing.payment_idempotencies
(
    idempotency_key,
    billing_id,
    payment_id,
    endpoint,
    response_body,
    status_code,
    created_at
)
SELECT
    'PAY-REQ-000002',
    p.billing_id,
    p.payment_id,
    '/api/v1/payments',
    '{"status":"SUCCESS"}',
    201,
    NOW()
FROM water_billing.payments p
WHERE p.payment_number = 'PAY-2026060002';


INSERT INTO water_billing.payment_idempotencies
(
    idempotency_key,
    billing_id,
    payment_id,
    endpoint,
    response_body,
    status_code,
    created_at
)
SELECT
    'PAY-REQ-000003',
    p.billing_id,
    p.payment_id,
    '/api/v1/payments',
    '{"status":"SUCCESS"}',
    201,
    NOW()
FROM water_billing.payments p
WHERE p.payment_number = 'PAY-2026060003';


INSERT INTO water_billing.payment_idempotencies
(
    idempotency_key,
    billing_id,
    payment_id,
    endpoint,
    response_body,
    status_code,
    created_at
)
SELECT
    'PAY-REQ-000004',
    p.billing_id,
    p.payment_id,
    '/api/v1/payments',
    '{"status":"SUCCESS"}',
    201,
    NOW()
FROM water_billing.payments p
WHERE p.payment_number = 'PAY-2026060004';


-- =====================================================
-- AUDIT LOGS
-- =====================================================

INSERT INTO water_billing.audit_logs
(
    audit_id,
    event,
    reference_id,
    customer_id,
    username,
    ip_address,
    device,
    version,
    created_at,
    updated_at
)
SELECT
    gen_random_uuid(),
    'PAYMENT_CREATED',
    p.payment_id::text,
    m.customer_id,
    'SYSTEM',
    '127.0.0.1',
    'Flyway Seeder',
    0,
    NOW(),
    NOW()
FROM water_billing.payments p
         JOIN water_billing.billings b
              ON p.billing_id = b.billing_id
         JOIN water_billing.meter_readings mr
              ON b.reading_id = mr.reading_id
         JOIN water_billing.meters m
              ON mr.meter_id = m.meter_id;