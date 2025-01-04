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
  - Category-based expiration statistics

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

## Prerequisites
- JDK 17 or later
- PostgreSQL 15
- Gradle 8.x

## Project Structure


## API Documentation
The API documentation is available through Swagger UI:
`http://localhost:8080/swagger-ui.html`

## Changelog
### [1.0.0] - 2025-01-04
- Initial release
- Basic inventory management functionality
- User authentication system implemented
