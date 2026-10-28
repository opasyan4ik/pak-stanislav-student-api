# Student API — Stanislav Pak

Stanislav Pak's DevOps project with Spring Boot CRUD REST API, PostgreSQL integration and full documentation.

The project also contains the Linux monitoring script and logs, a Docker Compose setup with PostgreSQL, and a Jenkins pipeline.

## Run locally

Start PostgreSQL and create a database named `studentdb` and a user named `student`, or set the environment variables below to an existing database. Set the database password locally; do not commit it.

```bash
export DB_URL=jdbc:postgresql://localhost:5432/studentdb
export DB_USERNAME=student
export DB_PASSWORD='your-local-database-password'
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

## Docker Compose

Docker Desktop must be running with Ubuntu WSL integration enabled. Copy `.env.example` to `.env`, replace the placeholder password with a private local value, and keep `.env` out of Git. The app uses `http://localhost:8081` by default because Jenkins uses port 8080 on this machine.

```bash
cp .env.example .env
# Edit .env and set a private POSTGRES_PASSWORD.
docker compose up -d --build
docker compose ps
docker compose logs app
docker image inspect pak-stanislav-student-api:v1.0-pak-stanislav
```

The app connects to PostgreSQL at `db:5432` on the dedicated Compose network. The `postgres-data` named volume preserves records after container restarts. Check the API with the same `curl` commands above, replacing port `8080` with `8081`.

```bash
docker compose exec app java -version
docker compose stop app
docker compose start app
docker compose restart
docker compose down          # preserves the database volume
docker compose up -d         # existing student records remain
```

## Jenkins

Job name: `Pak_Stanislav_Student_API`. Configure it as a Pipeline from SCM using this GitHub repository's `main` branch and `Jenkinsfile`. The agent needs Java 21, the Gradle wrapper, Git, and access to a Docker daemon. The pipeline runs Checkout, Build, Test, Docker Build, and Verify; a failed command stops the pipeline. It polls GitHub for new commits every two minutes. Since the repository is public, checkout needs no GitHub credential; private repositories should use Jenkins Credentials.

Jenkins is available at `http://localhost:8080` on the course machine. The application is at `http://localhost:8081` when started with Compose. Jenkins builds the image `pak-stanislav-student-api:v1.0-pak-stanislav`.
