# Digital Content Rights & Royalty Distribution Management System

## Project Overview
This is a Full Stack system designed to manage ownership rights of digital content (music, videos, articles, etc.) and automate royalty payments to creators.

## 🛠 What We Have Done So Far

### 1. Project Initialization
- Spring Boot 3.x Backend setup with Java 17.
- Dependencies added to `pom.xml`:
  - `Spring Boot Starter Web` (REST APIs)
  - `Spring Data JPA` (Database ORM)
  - `MySQL Connector` (Database Driver)
  - `Lombok` (Boilerplate reduction)
  - `Validation` (Request body validation)
  - `SpringDoc OpenAPI` (Swagger UI for API testing)

### 2. Domain Model Implementation
We have implemented the Core Domain Entities and Enums in the package `com.Digital_Content.Digital_Content_Rights`.

**Enums Created:**
- `Role`: ADMIN, CREATOR, DISTRIBUTOR
- `ContentStatus`: DRAFT, REGISTERED, ACTIVE, INACTIVE, ARCHIVED
- `ContentType`: MUSIC, VIDEO, ARTICLE, COURSE, PODCAST
- `RightsStatus`: ACTIVE, EXPIRED, TRANSFERRED
- `UsageType`: STREAM, DOWNLOAD, VIEW, SUBSCRIPTION_ACCESS
- `TransactionStatus`: RECORDED, VERIFIED, SETTLED
- `CalculationStatus`: PENDING, CALCULATED, APPROVED
- `PaymentStatus`: INITIATED, SUCCESS, FAILED

**Entities Created:**
- `User`: Handles user profiles and roles.
- `DigitalContent`: Metadata for uploaded content and its lifecycle status.
- `ContentRights`: Tracks ownership percentages and validity periods.
- `UsageTransaction`: Logs content usage by distributors and revenue generated.
- `RoyaltyCalculation`: Stores calculated royalty amounts based on revenue share.
- `RoyaltyPayment`: Tracks the actual payment transactions to creators.
- `RightsTransferHistory`: Maintains a history of ownership changes.

---

## 🚀 What Needs To Be Done Next

### 1. Database Configuration
- **File:** `backend/src/main/resources/application.properties`
- **Task:** Configure the MySQL datasource URL, username, and password. 
- **Specification:** Ensure `spring.jpa.hibernate.ddl-auto=update` is set during development.

### 2. Data Access Layer (Repositories)
- **Package:** `com.Digital_Content.Digital_Content_Rights.Repository`
- **Format:** `public interface [EntityName]Repository extends JpaRepository<[EntityName], Integer> {}`
- **Specific Requirements:**
  - `UserRepository`: `Optional<User> findByEmail(String email);`
  - `DigitalContentRepository`: `List<DigitalContent> findByCreatedBy_Id(Integer userId);`
  - `ContentRightsRepository`: `List<ContentRights> findByDigitalContent_Id(Integer contentId);`
  - `UsageTransactionRepository`: `List<UsageTransaction> findByTransactionStatus(TransactionStatus status);`

### 3. Data Transfer Objects (DTOs)
- **Package:** `com.Digital_Content.Digital_Content_Rights.DTO`
- **Format:** Use `@Data` and validation annotations (`@NotBlank`, `@Email`, `@Positive`).
- **Requirement:** Create separate `Request` (for input) and `Response` (for output) DTOs to avoid exposing internal entity logic.

### 4. Service Layer (Business Logic)
- **Package:** `com.Digital_Content.Digital_Content_Rights.Service`
- **Format:** Use `@Service` and `@Transactional` for methods that modify multiple tables.
- **Specific Logic to Implement:**
  - `ContentService`: Handle status transitions (DRAFT -> REGISTERED -> ACTIVE).
  - `RoyaltyService`: 
    - `calculateRoyalty(Integer contentId)`: Fetch all `VERIFIED` transactions, sum revenue, and multiply by `ownershipPercentage` for each owner.
    - `approveCalculation(Integer calcId)`: Move status to `APPROVED`.
  - `PaymentService`: Logic to ensure `paidAmount` doesn't exceed `calculatedAmount`.

### 5. API Layer (Controllers)
- **Package:** `com.Digital_Content.Digital_Content_Rights.Controller`
- **Format:** Use `@RestController`, `@RequestMapping("/api/...")`, and `ResponseEntity<T>`.
- **Sample Endpoints:**
  - `GET /api/content`: Return list of active content.
  - `POST /api/usage`: Distributor submits usage (Status: 201 Created).
  - `PUT /api/content/{id}/approve`: Admin approves content (Status: 200 OK).
  - `GET /api/royalty/creator/{id}`: Fetch earnings report for a specific creator.

---

## 📝 Coding Standards
- **Naming:** CamelCase for variables/methods, PascalCase for classes.
- **IDs:** Always use `Integer` for primary keys.
- **Formatting:** Use Lombok `@Data` for entities/DTOs.
- **API Responses:** Use a global exception handler (`@RestControllerAdvice`) to return consistent error messages:
  ```json
  {
    "status": "error",
    "message": "Specific error message here"
  }
  ```
- **Persistence:** Use `@ManyToOne` and `@JoinColumn` for foreign key relationships.
