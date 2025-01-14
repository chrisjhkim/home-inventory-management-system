# Home Inventory Management System

## Overview
Home Inventory Management System is a web-based application designed to help users efficiently manage their household items. The system enables users to track inventory levels, manage shopping lists, and monitor expiration dates of various household items.

## Features
- Item Management
    - Categorize items by type
    - Track quantity, purchase date, and expiration date
    - Barcode scanning for quick item registration
- Inventory Control
    - Real-time inventory status
    - Low stock alerts
    - Automatic notifications for items running low
- Shopping List Management
    - Create and manage shopping lists
    - Automatic addition of regularly purchased items
- Expiration Date Tracking
    - Notifications for items approaching expiration
    - Tag-based expiration statistics

## Getting Started
### Prerequisites
- JDK 17 or higher
- PostgreSQL 15
- Docker (optional)


## Technology Stack
- Backend
    - Spring Boot 3.4
    - Spring Data JPA
    - QueryDSL
    - PostgreSQL 15
- Frontend
    - Thymeleaf
    - Bootstrap 5
    - jQuery
- Build Tool
    - Gradle
- Testing
    - JUnit 5
    - Mockito
- Devops
    - docker


## Project Structure

## ERD

## Database Schema

### Users
| Column     | Type         | Constraints      |
|------------|--------------|------------------|
| id         | bigint       | PRIMARY KEY      |
| email      | varchar(255) | NOT NULL, UNIQUE |
| password   | varchar(255) | NOT NULL         |
| name       | varchar(50)  | NOT NULL         |
| created_at | timestamp    | NOT NULL         |
| updated_at | timestamp    | NOT NULL         |

### Tags
| Column      | Type        | Constraints |
|-------------|-------------|-------------|
| id          | bigint      | PRIMARY KEY |
| name        | varchar(50) | NOT NULL    |
| description | text        |             |
| created_at  | timestamp   | NOT NULL    |
| updated_at  | timestamp   | NOT NULL    |

### Items
| Column           | Type         | Constraints           |
|------------------|--------------|-----------------------|
| id               | bigint       | PRIMARY KEY           |
| tag_id      | bigint       | FOREIGN KEY (tags.id) |
| name             | varchar(255) | NOT NULL              |
| barcode          | varchar(100) | UNIQUE                |
| description      | text         |                       |
| minimum_quantity | int          | DEFAULT 0             |
| created_at       | timestamp    | NOT NULL              |
| updated_at       | timestamp    | NOT NULL              |

### Places
| Column      | Type         | Constraints |
|-------------|--------------|-------------|
| id          | bigint       | PRIMARY KEY |
| name        | varchar(100) | NOT NULL    |
| description | text         |             |
| created_at  | timestamp    | NOT NULL    |
| updated_at  | timestamp    | NOT NULL    |

### Item_Places
| Column          | Type      | Constraints             |
|-----------------|-----------|-------------------------|
| id              | bigint    | PRIMARY KEY             |
| item_id         | bigint    | FOREIGN KEY (items.id)  |
| place_id        | bigint    | FOREIGN KEY (places.id) |
| quantity        | int       | NOT NULL, DEFAULT 0     |
| expiration_date | date      |                         |
| purchase_date   | date      | NOT NULL                |
| created_at      | timestamp | NOT NULL                |
| updated_at      | timestamp | NOT NULL                |

### Notifications
| Column     | Type        | Constraints             |
|------------|-------------|-------------------------|
| id         | bigint      | PRIMARY KEY             |
| user_id    | bigint      | FOREIGN KEY (users.id)  |
| item_id    | bigint      | FOREIGN KEY (items.id)  |
| type       | varchar(50) | NOT NULL                |
| message    | text        | NOT NULL                |
| read       | boolean     | NOT NULL, DEFAULT false |
| created_at | timestamp   | NOT NULL                |
| updated_at | timestamp   | NOT NULL                |

## API Documentation
The API documentation is available through Swagger UI:
`http://localhost:8080/swagger-ui.html`

## Changelog

