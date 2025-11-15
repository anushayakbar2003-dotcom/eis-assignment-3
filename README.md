# Automotive EIS System - Assignment 3

## Enterprise Information System Implementation

This is a Spring Boot REST API implementation for the Automotive Supply Management System, implementing the APIs designed in Assignment 2.

## Project Structure

```
eis/
├── src/
│   ├── main/
│   │   ├── java/com/automotive/eis/
│   │   │   ├── entity/          # JPA Entities (Supplier, Part, Inventory, etc.)
│   │   │   ├── repository/      # JPA Repositories
│   │   │   ├── service/         # Business Logic Layer
│   │   │   ├── controller/      # REST Controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   └── config/          # Configuration Classes
│   │   └── resources/
│   │       ├── static/          # Front-end HTML files
│   │       ├── schema.sql       # Database Schema
│   │       └── application.properties
├── pom.xml
└── README.md
```

## Features Implemented

### 1. API Implementation
- **Supplier Registry API** (POST, GET, PUT /api/suppliers)
- **Supplier Performance API** (POST /api/suppliers/performance/event)
- **Inventory Query API** (GET /api/v1/inventory/{partId})
- **Inventory Adjustment API** (POST /api/v1/inventory/adjust)
- **Purchase Order Lifecycle API** (POST, GET, PUT /api/purchase-orders)
- **PO Matching & GRN API** (POST /api/purchase-orders/{poId}/match-receipt)

### 2. Database Schema
- Comprehensive ERD with tables for:
  - Suppliers
  - Parts
  - Inventory
  - Purchase Orders
  - GRNs (Goods Received Notes)
  - Quality Inspections
  - ASNs (Advance Shipment Notices)
  - Orders
  - Shipments
  - Returns/RMA
  - Recalls
  - Invoices
  - Audit Logs
  - And more...

### 3. Front-End Interface
- HTML/JavaScript dashboard
- Interactive forms for API testing
- Real-time API response display
- Organized tabs for different API categories

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Internet connection (for downloading dependencies)

## How to Run

1. **Clone/Navigate to project directory:**
   ```bash
   cd eis
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application:**
   - Front-end Dashboard: http://localhost:8080
   - H2 Database Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:eisdb`
     - Username: `sa`
     - Password: (leave empty)

## API Endpoints

### Supplier Management

1. **Create Supplier**
   - `POST /api/suppliers`
   - Request Body:
     ```json
     {
       "supplierId": "SUP-001",
       "name": "AutoParts Co.",
       "contact": "contact@autoparts.com",
       "leadTimeDays": 7,
       "certifications": "ISO 9001, IATF 16949"
     }
     ```

2. **Get All Suppliers**
   - `GET /api/suppliers`

3. **Get Supplier by ID**
   - `GET /api/suppliers/{supplierId}`

4. **Update Supplier**
   - `PUT /api/suppliers/{supplierId}`

5. **Update Supplier Performance**
   - `POST /api/suppliers/performance/event`
   - Request Body:
     ```json
     {
       "supplierId": "SUP-001",
       "onTimeDeliveryPercent": 95.5,
       "defectRate": 2.0
     }
     ```

### Inventory Management

1. **Query Inventory**
   - `GET /api/v1/inventory/{partId}`

2. **Adjust Inventory**
   - `POST /api/v1/inventory/adjust`
   - Request Body:
     ```json
     {
       "adjustmentId": "ADJ-001",
       "partId": "P-123",
       "delta": -5,
       "reason": "QC failed - scrapped",
       "lot": "LOT-445"
     }
     ```

### Purchase Order Management

1. **Create Purchase Order**
   - `POST /api/purchase-orders`
   - Request Body:
     ```json
     {
       "poId": "PO-001",
       "supplierId": "SUP-001",
       "partId": "P-123",
       "quantity": 100,
       "status": "CREATED"
     }
     ```

2. **Get Purchase Order**
   - `GET /api/purchase-orders/{poId}`

3. **Update PO Status**
   - `PUT /api/purchase-orders/{poId}/status`
   - Request Body:
     ```json
     {
       "status": "APPROVED"
     }
     ```

4. **Match Receipt & Generate GRN**
   - `POST /api/purchase-orders/{poId}/match-receipt`
   - Request Body:
     ```json
     {
       "receivedQty": 95,
       "inspectedQty": 95
     }
     ```

## Testing

### Using the Web Interface
1. Open http://localhost:8080 in your browser
2. Use the interactive forms to test APIs
3. View responses in JSON format

### Using Postman
1. Import the API endpoints from the documentation above
2. Test each endpoint with sample data
3. Verify responses match expected format

### Using cURL

**Create Supplier:**
```bash
curl -X POST http://localhost:8080/api/suppliers \
  -H "Content-Type: application/json" \
  -d '{
    "supplierId": "SUP-001",
    "name": "AutoParts Co.",
    "contact": "contact@autoparts.com",
    "leadTimeDays": 7
  }'
```

**Query Inventory:**
```bash
curl http://localhost:8080/api/v1/inventory/P-123
```

## Database Schema

The database schema is defined in `src/main/resources/schema.sql` and includes:

- **Suppliers**: Supplier information and performance metrics
- **Parts**: Part/component master data
- **Inventory**: Stock levels, lots, and bin locations
- **Purchase Orders**: PO creation and lifecycle management
- **GRNs**: Goods received notes linked to POs
- **Quality Inspections**: QC results and defect tracking
- **Orders**: Customer/production orders
- **Shipments**: Shipment scheduling and tracking
- **Returns**: RMA and return processing
- **Invoices**: Invoice generation and matching
- **Audit Logs**: System event logging

## Architecture

- **Controller Layer**: Handles HTTP requests/responses
- **Service Layer**: Contains business logic
- **Repository Layer**: Database access using JPA
- **Entity Layer**: JPA entities representing database tables
- **DTO Layer**: Data transfer objects for API requests/responses

## Technologies Used

- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- HTML/CSS/JavaScript (Front-end)

## Future Enhancements

- Implement remaining APIs from Assignment 2
- Add authentication and authorization
- Implement comprehensive error handling
- Add unit and integration tests
- Deploy to cloud (AWS) for bonus marks
- Add CI/CD pipeline

## Author

Assignment 3 - Automotive EIS System Implementation

## License

This project is for educational purposes.

