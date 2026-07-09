--------------------------------------------------------------------------------------------------------
  TUGAS - Simple Billing System Application
--------------------------------------------------------------------------------------------------------
```
    1. CREATE BILLING
    Buat Web Form untuk membuat tagihan pelanggan, dengan parameter:
      - customer_id
      - period
      - current_reading
    * Nilai usage adalah selisih current_reading dan previous_reading.
    * Nilai previous_reading adalah nilai current_reading dari periode sebelumnya, 
      untuk pelanggan baru maka nilai dari previous_reading = 0
    * Nilai status billing awal = UNPAID

    2. BILL INQUIRY 
    Buat Web Form untuk melakukan cek tagihan, dengan customer_id dan period sebagai parameternya.
    * Jika tagihan tidak ditemukan atau sudah terbayar pada periode tersebut maka tampilkan pesan yang
      informatif.
    * Jika tagihan ditemukan pada periode tersebut, tampilkan informasi:
      - billing_id
      - Tanggal jatuh tempo tagihan (due_date)
      - Nilai indeks pemakaian air (usage) pada periode tersebut
      - Biaya tertagih pada periode tersebut
        Rumus: meter_readings.usage * tarrifs.price_per_m3
      - No. Pelanggan, Nama Pelanggan dan Alamat Pelanggan
      - Meter SN
      - Kode Tarif, Nama Tarif & Biaya Tarif per m3
      - Buat dan tampilkan tombol untuk melakukan Pembayaran

    3. PAYMENT
    Buatkan fungsi tombol untuk mengeksekusi pembayaran pada tagihan periode pelanggan.
    Payment Action:
    * Buat record payment:
      - Hitung biaya penalty (jika pembayaran melampaui batas tanggal jatuh tempo).
        Rumus: tariffs.penalty_percents * billings.amount
      - Hitung total biaya yang harus dibayar ((amount + penalty) = amount_totals)
    * Ubah status billing menjadi PAID

    4. BILLING HISTORY
    Buatkan web view untuk menampilkan riwayat tagihan berdasarkan ID pelanggan, 
    terurut dari tagihan yang terbaru hingga yang terlama.
    Informasi yang ditampilkan:
      - Periode Tagihan
      - ID Tagihan
      - ID Pelanggan
      - Nama Pelanggan
      - Jumlah Pemakaian Indeks Pemakaian Air
      - Biaya Tagihan
      - Tanggal Jatuh Tempo
      - Meter SN
      - Kode dan Nama Tarif
      - Status Tagihan
```


```
Table tariffs {
tariff_code varchar [pk]
tariff_name varchar
price_per_m3 decimal
penalty_percents decimal
}
```
```
Table customers {
customer_id varchar [pk]
customer_name varchar
email varchar
phone varchar
address varchar
}
```
```
Table meters {
meter_id int [pk, increment]
customer_id varchar
tariff_code varchar
serial_number varchar
}
```
```
Table meter_readings {
reading_id int [pk, increment]
meter_id int
period varchar
previous_reading decimal
current_reading decimal
usage decimal
}
```
```
Table billings {
billing_id varchar [pk]
reading_id int
amount decimal
due_date date
status varchar
}
```
```
Table payments {
payment_id varchar [pk]
billing_id varchar
pay_datetime datetime
penalty decimal
amount_totals decimal
}
```
```
// References
Ref: customers.customer_id < meters.customer_id
Ref: tariffs.tariff_code < meters.tariff_code
Ref: meters.meter_id < meter_readings.meter_id
Ref: meter_readings.reading_id < billings.reading_id
Ref: billings.billing_id < payments.billing_id
```
```
// Records
Records tariffs(tariff_code, tariff_name, price_per_m3, penalty_percents){
'2A', 'Rumah Tangga', 3250, 0.015
'1C', 'Tempat Ibadah', 2100, 0.025
'2E', 'Bisnis Perkantoran', 10325, 5.0
}
```
```
Records customers(customer_id, customer_name, email, phone, address) {
'RT0001', 'Budiman', 'budiman@webmail.co.id', '+62 820 1235 3448', 'Jl. Rajawali Raya Blok D No 8, Jakarta, 11100'
'TI0001', 'Musholla Al-Huda', '-', '+62 815 7701 1153', 'Jl. Pengairan Baru Blok C No. 3, Jakarta, 11101'
'BP0001', 'PT Jaya Kencana', 'hrd@jayakencana.co.id', '+62 511 8811', 'Jl. Barada Karya Blok A No. 8, Jakarta, 11107'
}
```
```
Records meters(meter_id, customer_id, tariff_code, serial_number) {
1, 'RT0001', '2A', 'RT0001CA0017'
2, 'TI0001', '1C', 'TI0001FC0023'
3, 'BP0001', '2E', 'BP0001OF0075'
}
```
```
Records meter_readings(reading_id, meter_id, period, previous_reading, current_reading, usage) {
1, 1, '202606', 100, 150, 50
2, 1, '202607', 150, 225, 75
}
```
```
Records billings(billing_id, reading_id, amount, due_date, status) {
'BL0001', 1, 162500, '2026-06-20', 'PAID'
'BL0002', 1, 243750, '2026-07-20', 'UNPAID'
}
```
```
Records payments(payment_id, billing_id, pay_datetime, penalty, amount_totals) {
'ab76dc5b-9510-430d-9349-aa8f95963821', 'BL0001', '2026-06-07 10:33:10', 0, 162500
}
```

Jika database ini hanya untuk development/testing, reset seluruh schema.
```text
DROP SCHEMA water_billing CASCADE;

CREATE SCHEMA water_billing;
```
```text
DROP TABLE flyway_schema_history CASCADE;
DROP TABLE payment_idempotencies CASCADE;
DROP TABLE payments CASCADE;
DROP TABLE billings CASCADE;
DROP TABLE meter_readings CASCADE;
DROP TABLE meters CASCADE;
DROP TABLE tariffs CASCADE;
DROP TABLE customers CASCADE;
```

Kemudian jalankan aplikasi lagi.
Flyway akan menjalankan
```text
V1__init.sql
V2__master_data.sql
```
sekali saja.



## Verifikasi Data

```
SELECT
mr.reading_id,
c.customer_name,
m.serial_number,
t.tariff_name,
mr.period,
mr.previous_reading,
mr.current_reading,
mr.usage
FROM water_billing.meter_readings mr
JOIN water_billing.meters m
ON mr.meter_id = m.meter_id
JOIN water_billing.customers c
ON m.customer_id = c.customer_id
JOIN water_billing.tariffs t
ON m.tariff_code = t.tariff_code
ORDER BY mr.period, c.customer_name;
```

```
-- =====================================================
-- Jika sudah ada meter, lihat ID sebenarnya
-- =====================================================
```
```
SELECT
meter_id,
customer_id,
serial_number
FROM water_billing.meters
ORDER BY meter_id;
```
```
-- =====================================================
-- Jika sudah ada billing, lihat ID sebenarnya
-- =====================================================
```
```
SELECT mr.reading_id
FROM meter_readings mr
JOIN meters m
ON mr.meter_id = m.meter_id
WHERE m.serial_number='WM-202600001'
AND mr.period='202607';
```
```
-- =====================================================
-- Verifikasi hasil migration
-- =====================================================
```
```
SELECT
p.payment_number,
p.payment_id,
p.billing_id,
b.status,
b.amount,
p.penalty,
p.amount_total,
p.pay_datetime
FROM water_billing.payments p
JOIN water_billing.billings b
ON p.billing_id = b.billing_id
ORDER BY p.payment_number;
```

# Urutan Pengujian End-to-End API ENDPOINT

Untuk memastikan seluruh alur aplikasi berjalan dengan benar, lakukan pengujian dengan urutan berikut: http://localhost:9080

| No | Endpoint                                | Method | Tujuan                                                                      |
| -- | --------------------------------------- | ------ | --------------------------------------------------------------------------- |
| 1  | `/api/v1/customers`                     | POST   | Membuat customer                                                            |
| 2  | `/api/v1/meters/customer/{customerId}`  | GET    | Memastikan meter tersedia                                                   |
| 3  | `/api/v1/billings`                      | POST   | Membuat billing baru                                                        |
| 4  | `/api/v1/billings/inquiry`              | POST   | Mengecek billing                                                            |
| 5  | `/api/v1/payments`                      | POST   | Membayar billing                                                            |
| 6  | `/api/v1/payments`                      | POST   | Menguji retry/idempotency dengan key yang sama                              |
| 7  | `/api/v1/payments/{paymentId}`          | GET    | Mengambil detail pembayaran                                                 |
| 8  | `/api/v1/payments/{paymentId}/receipt`  | GET    | Mengambil bukti pembayaran                                                  |
| 9  | `/api/v1/billings/history/{customerId}` | GET    | Memastikan status billing telah berubah menjadi **PAID** setelah pembayaran |




# 💧 Water Billing Management System

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.5.x-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)
![Flyway](https://img.shields.io/badge/Flyway-11-red)
![MapStruct](https://img.shields.io/badge/MapStruct-1.6-green)
![Maven](https://img.shields.io/badge/Maven-3.9-blue)

---

# Overview

Water Billing Management System adalah aplikasi backend berbasis **Spring Boot** untuk mengelola proses penagihan air pelanggan mulai dari:

- Customer Management
- Tariff Management
- Meter Management
- Meter Reading
- Billing
- Payment
- Receipt
- Audit Log
- Idempotency Payment

Project ini dibangun menggunakan pendekatan **Clean Architecture**, **Layered Architecture**, dan **REST API**.

---

# Technology Stack

| Technology | Version            |
|------------|--------------------|
| Java | 17/21              |
| Spring Boot | 3.5.x              |
| Spring Data JPA | Latest             |
| Spring Validation | Jakarta Validation |
| PostgreSQL | 17                 |
| Flyway | 11                 |
| Lombok | Latest             |
| MapStruct | Latest             |
| Maven | 3.9                |

---

# Project Structure

```
waterbilling
├src/main/java/com/billing/waterbilling
├── config
├── contants
├── controller
├── dto
│ ├── request
│ └── response
├── entity
├── exception
├── idempotency
├── mapper
├── repository
├── service
│ ├── impl
├── util
├── validator
├─WaterbillingApplication.java
 
├src/main/resources
├── static
├── templates
├── db.migration
│ ├── V1__init.sql
│ └── V2__master_insert_data.sql
├─application.yml
docker-compose.yml
pom.xml
````

---

# Database Schema

```

Customer
│
│1
│
▼
Meter
│
│1
│
▼
Meter Reading
│
│1
│
▼
Billing
│
│1
│
▼
Payment
│
│
├── Receipt
└── Payment Idempotency

```

---

# Modules

## 1. Customer

Mengelola data pelanggan.

### API

| Method | Endpoint               |
| ------ | ---------------------- |
| POST   | /api/v1/customers      |
| PUT    | /api/v1/customers/{id} |
| GET    | /api/v1/customers/{id} |
| GET    | /api/v1/customers      |
| DELETE | /api/v1/customers/{id} |

---

## 2. Tariff

Mengelola tarif air.

Entity:

```

Tariff

* tariffCode
* tariffName
* pricePerM3
* penaltyPercent

```

---

## 3. Meter

Mengelola meter pelanggan.

### API

| Method | Endpoint                             |
| ------ | ------------------------------------ |
| POST   | /api/v1/meters                       |
| GET    | /api/v1/meters/{meterId}             |
| GET    | /api/v1/meters/customer/{customerId} |
| GET    | /api/v1/meters/mreading/{meterId}    |

### Validation

* Customer harus ada
* Tariff harus ada
* Customer hanya boleh memiliki satu meter
* Serial Number tidak boleh duplicate

---

## 4. Meter Reading

Mencatat pembacaan meter.

### API

| Method | Endpoint                     |
| ------ | ---------------------------- |
| POST   | /api/v1/readings             |
| PUT    | /api/v1/readings/{readingId} |

### Validation

* Period format YYYYMM
* Reading >= 0
* Current Reading >= Previous Reading

---

## 5. Billing

Generate tagihan pelanggan.

### API

| Method | Endpoint                              |
| ------ | ------------------------------------- |
| POST   | /api/v1/billings                      |
| POST   | /api/v1/billings/inquiry              |
| GET    | /api/v1/billings/history/{customerId} |
| GET    | /api/v1/billings/{billingId}          |

### Business Flow

```

Customer Validation

↓

Find Meter

↓

Check Duplicate Billing

↓

Load Latest Meter Reading

↓

Validate Reading

↓

Calculate Usage

↓

Calculate Amount

↓

Save Meter Reading

↓

Generate Billing

```

---

# Billing Formula

```

Usage = Current Reading - Previous Reading

Amount = Usage × Price per m3

```

---

# Billing Status

```

UNPAID

PAID

```

---

## 6. Payment

Melakukan pembayaran tagihan.

### API

| Method | Endpoint                             |
| ------ | ------------------------------------ |
| POST   | /api/v1/payments                     |
| GET    | /api/v1/payments/{paymentId}         |
| GET    | /api/v1/payments/{paymentId}/receipt |

---

# Payment Flow

```

Validate Request

↓

Register Idempotency

↓

Retry?

↓

Lock Billing

↓

Already Paid?

↓

Calculate Penalty

↓

Calculate Total

↓

Create Payment

↓

Update Billing

↓

Save Idempotency

↓

Audit Log

↓

Return Response

```

---

# Payment Formula

```

Penalty

↓

Amount Total

↓

Save Payment

```

---

## 7. Receipt

Menghasilkan struk pembayaran.

Response berisi:

* Receipt Number
* Payment Number
* Billing Id
* Customer
* Meter
* Tariff
* Usage
* Amount
* Penalty
* Total
* Payment Date

---

## 8. Audit Log

Semua transaksi pembayaran dicatat.

Contoh Event

```

PAYMENT_CREATED

```

Data yang dicatat

* Event
* Customer
* Reference Id
* Username
* Device
* IP Address
* Timestamp

---

## 9. Payment Idempotency

Digunakan agar pembayaran tidak diproses dua kali.

Flow

```

Request

↓

Check Idempotency Key

↓

Already Exists?

↓

Return Existing Payment

↓

Else

↓

Continue Payment

```

---

# Entity Relationship

```

Customer

↓

Meter

↓

Meter Reading

↓

Billing

↓

Payment

↓

Receipt

```

---

# Database Migration

Menggunakan Flyway.

```

V1__init.sql

V2__master_insert_data.sql

```

---

# Sample Master Data

## Customers

```

CUST001

CUST002

CUST003

CUST004

```

## Tariffs

```

R1

R2

R3

B1

B2

I1

S1

P1

```

---

# Build Project

```

mvn clean install

```

---

# Run

```

mvn spring-boot:run

```

atau

```

java -jar target/waterbilling.jar

```

---

# Server

```

http://localhost:9080

```

---

# API Testing

## Customer

```

POST /api/v1/customers

GET /api/v1/customers

GET /api/v1/customers/{id}

PUT /api/v1/customers/{id}

DELETE /api/v1/customers/{id}

```

---

## Meter

```

POST /api/v1/meters

GET /api/v1/meters/{id}

GET /api/v1/meters/customer/{customerId}

GET /api/v1/meters/mreading/{meterId}

```

---

## Meter Reading

```

POST /api/v1/readings

PUT /api/v1/readings/{id}

```

---

## Billing

```

POST /api/v1/billings

POST /api/v1/billings/inquiry

GET /api/v1/billings/history/{customerId}

GET /api/v1/billings/{billingId}

```

---

## Payment

```

POST /api/v1/payments

GET /api/v1/payments/{paymentId}

GET /api/v1/payments/{paymentId}/receipt

```

---

# Validation

✔ Bean Validation

* @NotBlank

* @Positive

* @Digits

* @Email

* Custom Validation

    * @ValidPeriod

---

# Exception Handling

Custom Exception

* CustomerNotFoundException

* MeterNotFoundException

* TariffNotFoundException

* BillingNotFoundException

* PaymentNotFoundException

* DuplicateBillingException

* DuplicateSerialNumberException

* MeterAlreadyExistsException

* ConcurrentPaymentException

* InvalidRequestException

---

# Logging

Menggunakan Lombok Slf4j.

Contoh

```

========== CREATE BILLING ==========

Customer : CUST001

Current Reading : 150

Billing Created : BILL-2026070001

```

---

# Features

* RESTful API
* Layered Architecture
* DTO Pattern
* Repository Pattern
* Service Pattern
* Mapper Pattern (MapStruct)
* Flyway Migration
* PostgreSQL
* Payment Idempotency
* Audit Logging
* Optimistic Locking Ready
* Transaction Management
* Validation
* Exception Handling
* Receipt Generation

---

# Future Enhancement

* JWT Authentication
* Spring Security
* Refresh Token
* Role Management
* QRIS Payment
* Midtrans Integration
* Xendit Integration
* Email Notification
* WhatsApp Notification
* Dashboard Analytics
* Docker
* Kubernetes
* Prometheus
* Grafana
* Redis Cache
* Kafka Event Driven
* Microservices Architecture

---

# Author

**Joko Kusnandi**

Backend Developer

Java • Spring Boot • PostgreSQL • Microservices • Clean Architecture

