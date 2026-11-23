
-- PulaTrust Bank sample accounts
-- Customers C201 to C210 already exist

INSERT INTO accounts
(account_number, customer_id, account_type,
 branch, balance, open_date,
 employer_name, employer_address)
VALUES

('S201', 'C201', 'SAVINGS',
 'Gaborone', 1200.00, DATE '2026-11-23',
 NULL, NULL),

('I201', 'C201', 'INVESTMENT',
 'Gaborone', 500.00, DATE '2026-11-23',
 NULL, NULL),

('CH202', 'C202', 'CHEQUE',
 'Francistown', 2300.00, DATE '2026-11-23',
 'Example Trading', 'Francistown'),

('S203', 'C203', 'SAVINGS',
 'Maun', 250.00, DATE '2026-11-23',
 NULL, NULL),

('I204', 'C204', 'INVESTMENT',
 'Serowe', 1000.00, DATE '2026-11-23',
 NULL, NULL),

('S205', 'C205', 'SAVINGS',
 'Palapye', 600.00, DATE '2026-11-23',
 NULL, NULL),

('CH206', 'C206', 'CHEQUE',
 'Mahalapye', 1500.00, DATE '2026-11-23',
 'Sample Services', 'Mahalapye'),

('I207', 'C207', 'INVESTMENT',
 'Gaborone', 800.00, DATE '2026-11-23',
 NULL, NULL),

('S208', 'C208', 'SAVINGS',
 'Jwaneng', 300.00, DATE '2026-11-23',
 NULL, NULL),

('CH209', 'C209', 'CHEQUE',
 'Tutume', 3000.00, DATE '2026-11-23',
 'Demo Enterprises', 'Tutume'),

('I210', 'C210', 'INVESTMENT',
 'Mochudi', 900.00, DATE '2026-11-23',
 NULL, NULL);
