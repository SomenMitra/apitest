# Device Notes Service

## Overview

This project implements a backend feature to store and retrieve notes for devices.  
The application is built using **Java, Spring Boot, Liquibase, and MySQL**.

The feature allows users to:

- Create notes for a specific device
- Retrieve notes for a device with pagination support
- Enforce validation rules
- Maintain database schema changes using Liquibase

---

## Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- Liquibase
- MySQL
- Maven

---

## Project Structure

src/main/java/com/example/devicenotes

controller  
  DeviceNoteController.java  

service  
  DeviceNoteService.java  

repository  
  DeviceNoteRepository.java  

entity  
  DeviceNote.java  

dto  
  CreateNoteRequest.java  
  NoteResponse.java  

exception  
  GlobalExceptionHandler.java  


Liquibase scripts:

src/main/resources/db/changelog

db.changelog-master.xml  
001-create-device-note-table.xml

---

## Database Setup

Create the database before running the application.

```sql
CREATE DATABASE device_notes_db;

Liquibase will automatically create the required tables on application startup.

Tables created:

device_note

databasechangelog

databasechangeloglock

API Endpoints
1. Create Note

POST /api/v1/devices/{deviceId}/notes

Header:

X-User: username

Request Body:

{
  "note": "Device rebooted during maintenance"
}

Validation Rules:

note must not be blank

note maximum length is 1000 characters

X-User header is mandatory

Response Example:

{
  "id": 1,
  "deviceId": 1,
  "note": "Device rebooted during maintenance",
  "createdBy": "admin",
  "createdAt": "2026-03-10T18:25:12"
}
2. List Notes

GET /api/v1/devices/{deviceId}/notes?limit=20

Query Parameters:

Parameter	Description
limit	Number of records returned

Rules:

Default limit = 20

Limit range = 1–100

Results are ordered by created_at DESC

Example:

GET /api/v1/devices/1/notes?limit=10

Response:

[
  {
    "id": 1,
    "deviceId": 1,
    "note": "Device rebooted during maintenance",
    "createdBy": "admin",
    "createdAt": "2026-03-10T18:25:12"
  }
]
Error Handling

The application uses a global exception handler to return predictable API errors.

Example error response:

{
  "error": "X-User header is required"
}

Returned with:

HTTP 400 Bad Request
Logging

Logging has been added at key points:

Controller:

Logs incoming requests with deviceId and username

Service:

Logs successful note creation

Logs validation failures

Example logs:

INFO  DeviceNoteController : Create note request received deviceId=1 user=admin
INFO  DeviceNoteService : Note created successfully noteId=1 deviceId=1
WARN  DeviceNoteService : Missing X-User header
Assumptions

The environment does not contain a device table.
Therefore, no foreign key constraint was added for device_id.

Running the Application

Clone the repository

git clone <repo-url>

Navigate to project directory

cd device-notes-service

Create database

CREATE DATABASE device_notes_db;

Run the application

mvn spring-boot:run
Testing APIs

The APIs can be tested using:

Postman

curl

REST client tools
