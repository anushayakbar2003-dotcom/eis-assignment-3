-- Database Schema for Automotive EIS System
-- Entity Relationship Diagram: 
-- Suppliers -> Purchase Orders -> Goods Receipt Notes (GRN) -> Inventory
-- Inventory -> Orders -> Fulfillment -> Shipments
-- Suppliers -> ASN -> GRN -> Quality Inspection -> Inventory
-- Purchase Orders -> Invoices -> 3-Way Match

-- Suppliers Table
CREATE TABLE IF NOT EXISTS suppliers (
    supplier_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact VARCHAR(100),
    lead_time_days INT,
    certifications TEXT,
    on_time_delivery_percent DECIMAL(5,2),
    defect_rate DECIMAL(5,2),
    status VARCHAR(20) DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Parts/Inventory Items Table
CREATE TABLE IF NOT EXISTS parts (
    part_id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    unit_price DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Inventory Table
CREATE TABLE IF NOT EXISTS inventory (
    inventory_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    part_id VARCHAR(50) NOT NULL,
    on_hand INT DEFAULT 0,
    reserved INT DEFAULT 0,
    available INT DEFAULT 0,
    reorder_point INT DEFAULT 0,
    safety_stock INT DEFAULT 0,
    lot_number VARCHAR(50),
    bin_location VARCHAR(50),
    expiry_date DATE,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (part_id) REFERENCES parts(part_id)
);

-- Purchase Orders Table
CREATE TABLE IF NOT EXISTS purchase_orders (
    po_id VARCHAR(50) PRIMARY KEY,
    supplier_id VARCHAR(50) NOT NULL,
    part_id VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'CREATED',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expected_delivery_date DATE,
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id),
    FOREIGN KEY (part_id) REFERENCES parts(part_id)
);

-- Goods Received Notes (GRN) Table
CREATE TABLE IF NOT EXISTS grns (
    grn_id VARCHAR(50) PRIMARY KEY,
    po_id VARCHAR(50) NOT NULL,
    asn_id VARCHAR(50),
    received_qty INT NOT NULL,
    inspected_qty INT,
    received_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    inspector VARCHAR(100),
    status VARCHAR(20) DEFAULT 'POSTED',
    FOREIGN KEY (po_id) REFERENCES purchase_orders(po_id)
);

-- Quality Inspection Table
CREATE TABLE IF NOT EXISTS quality_inspections (
    inspection_id VARCHAR(50) PRIMARY KEY,
    grn_id VARCHAR(50),
    part_id VARCHAR(50) NOT NULL,
    lot_number VARCHAR(50),
    inspector VARCHAR(100),
    result VARCHAR(20) NOT NULL,
    defect_code VARCHAR(50),
    sample_size INT,
    failed_qty INT,
    notes TEXT,
    inspection_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (grn_id) REFERENCES grns(grn_id),
    FOREIGN KEY (part_id) REFERENCES parts(part_id)
);

-- ASN (Advance Shipment Notice) Table
CREATE TABLE IF NOT EXISTS asns (
    asn_id VARCHAR(50) PRIMARY KEY,
    po_id VARCHAR(50) NOT NULL,
    supplier_id VARCHAR(50) NOT NULL,
    eta TIMESTAMP,
    carrier VARCHAR(100),
    status VARCHAR(20) DEFAULT 'RECEIVED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (po_id) REFERENCES purchase_orders(po_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id)
);

-- Orders (Customer/Production Orders) Table
CREATE TABLE IF NOT EXISTS orders (
    order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255),
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expected_delivery_date DATE,
    promised_date DATE,
    actual_delivery_date DATE,
    status VARCHAR(20) DEFAULT 'CREATED',
    sla_compliance VARCHAR(20)
);

-- Order Lines Table
CREATE TABLE IF NOT EXISTS order_lines (
    line_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    part_id VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (part_id) REFERENCES parts(part_id)
);

-- Shipments Table
CREATE TABLE IF NOT EXISTS shipments (
    shipment_id VARCHAR(50) PRIMARY KEY,
    order_id BIGINT,
    part_id VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    destination VARCHAR(255),
    carrier VARCHAR(100),
    ship_date DATE,
    eta TIMESTAMP,
    status VARCHAR(20) DEFAULT 'SCHEDULED',
    tracking_number VARCHAR(100),
    delivered_by VARCHAR(100),
    delivery_time TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (part_id) REFERENCES parts(part_id)
);

-- Returns/RMA Table
CREATE TABLE IF NOT EXISTS returns (
    return_id VARCHAR(50) PRIMARY KEY,
    rma_id VARCHAR(50) UNIQUE,
    order_id BIGINT,
    customer_id INT,
    part_id VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    reason TEXT,
    disposition VARCHAR(50),
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (part_id) REFERENCES parts(part_id)
);

-- Recalls Table
CREATE TABLE IF NOT EXISTS recalls (
    recall_id VARCHAR(50) PRIMARY KEY,
    part_id VARCHAR(50) NOT NULL,
    lot_number VARCHAR(50),
    reason TEXT,
    scope TEXT,
    status VARCHAR(20) DEFAULT 'ISSUED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (part_id) REFERENCES parts(part_id)
);

-- Invoices Table
CREATE TABLE IF NOT EXISTS invoices (
    invoice_id VARCHAR(50) PRIMARY KEY,
    po_id VARCHAR(50),
    supplier_id VARCHAR(50),
    amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'DRAFT',
    invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (po_id) REFERENCES purchase_orders(po_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id)
);

-- Invoice Matching (3-Way Match) Table
CREATE TABLE IF NOT EXISTS invoice_matches (
    match_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    invoice_id VARCHAR(50) NOT NULL,
    po_id VARCHAR(50) NOT NULL,
    grn_id VARCHAR(50) NOT NULL,
    match_result VARCHAR(20),
    discrepancies INT DEFAULT 0,
    matched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id),
    FOREIGN KEY (po_id) REFERENCES purchase_orders(po_id),
    FOREIGN KEY (grn_id) REFERENCES grns(grn_id)
);

-- Credit/Refund Table
CREATE TABLE IF NOT EXISTS credits (
    credit_id VARCHAR(50) PRIMARY KEY,
    invoice_id VARCHAR(50),
    amount DECIMAL(10,2) NOT NULL,
    reason TEXT,
    status VARCHAR(20) DEFAULT 'PROCESSED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (invoice_id) REFERENCES invoices(invoice_id)
);

-- Inventory Adjustments Table
CREATE TABLE IF NOT EXISTS inventory_adjustments (
    adjustment_id VARCHAR(50) PRIMARY KEY,
    part_id VARCHAR(50) NOT NULL,
    delta INT NOT NULL,
    reason TEXT,
    lot_number VARCHAR(50),
    user_id VARCHAR(100),
    adjustment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    previous_on_hand INT,
    new_on_hand INT,
    FOREIGN KEY (part_id) REFERENCES parts(part_id)
);

-- Audit Log Table
CREATE TABLE IF NOT EXISTS audit_logs (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    entity_type VARCHAR(50) NOT NULL,
    entity_id VARCHAR(50) NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    user_id VARCHAR(100),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    details TEXT
);

-- Demand Forecast Table
CREATE TABLE IF NOT EXISTS demand_forecasts (
    forecast_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    part_id VARCHAR(50) NOT NULL,
    forecast_units INT NOT NULL,
    confidence_level VARCHAR(20),
    forecast_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (part_id) REFERENCES parts(part_id)
);

-- Sample Data Insertion
INSERT INTO parts (part_id, name, description, category, unit_price) VALUES
('P-123', 'Brake Pad', 'Automotive brake pad set', 'Brakes', 45.00),
('P-456', 'Oil Filter', 'Standard engine oil filter', 'Engine', 12.50),
('P-789', 'Air Filter', 'High-performance air filter', 'Engine', 25.00),
('P-999', 'Engine Oil', '5W-30 Synthetic Engine Oil', 'Fluids', 35.00);

INSERT INTO suppliers (supplier_id, name, contact, lead_time_days, certifications, status) VALUES
('SUP-001', 'AutoParts Co.', 'contact@autoparts.com', 7, 'ISO 9001, IATF 16949', 'active'),
('SUP-002', 'Quality Components Ltd', 'info@qualitycomp.com', 5, 'ISO 9001', 'active'),
('SUP-003', 'Global Automotive Supply', 'sales@globauto.com', 10, 'IATF 16949', 'active');

