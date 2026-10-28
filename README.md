# Student API — Stanislav Pak

Stanislav Pak's DevOps project with full Spring Boot CRUD documentation.

The project also contains the Linux monitoring script and logs from the first part of the course. Docker and Jenkins files are reserved for the next practical lessons.

## Run locally

Start PostgreSQL and create a database named `studentdb` and a user named `student`, or set the environment variables below to an existing database. The default connection is `jdbc:postgresql://localhost:5432/studentdb` with user/password `student`/`student`.

```bash
export DB_URL=jdbc:postgresql://localhost:5432/studentdb
export DB_USERNAME=student
export DB_PASSWORD=student
./gradlew bootRun
```

The application creates/updates the `students` table using Hibernate for this course project. No sample student is inserted automatically; create Stanislav Pak through the API below.

## REST API

Base URL: `http://localhost:8080/api/students`

| Method | Path | Result |
| --- | --- | --- |
| POST | `/api/students` | 201 Created, `Location` header |
| GET | `/api/students` | 200 OK, list |
| GET | `/api/students/{id}` | 200 OK or 404 Not Found |
| PUT | `/api/students/{id}` | 200 OK or 404 Not Found |
| DELETE | `/api/students/{id}` | 204 No Content or 404 Not Found |

`name` and `email` are required; `email` must be valid. Invalid JSON or fields return 400 Bad Request.

```bash
# Create Stanislav Pak; note the returned id (for example 1).
curl -i -X POST http://localhost:8080/api/students \
  -H 'Content-Type: application/json' \
  -d '{"name":"Stanislav Pak","email":"stanislav@example.com"}'

curl -i http://localhost:8080/api/students
curl -i http://localhost:8080/api/students/1

curl -i -X PUT http://localhost:8080/api/students/1 \
  -H 'Content-Type: application/json' \
  -d '{"name":"Stanislav Pak","email":"pak@example.com"}'

curl -i -X DELETE http://localhost:8080/api/students/1
curl -i http://localhost:8080/api/students/1  # 404 after deletion
```

## Build and test

```bash
./gradlew clean build
```

Tests use an isolated in-memory H2 database. The running application uses PostgreSQL through the `DB_*` variables.
