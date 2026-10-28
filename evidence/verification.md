# DevOps midterm verification — Stanislav Pak

Verified on Ubuntu WSL with Java 21, Gradle 9.8.2, Docker Desktop 29.8.2, and PostgreSQL 17.

## Build and API

- `./gradlew clean build --no-daemon`: BUILD SUCCESSFUL; four tests passed.
- `docker compose up -d --build`: the app and PostgreSQL containers started; PostgreSQL health check passed.
- Application log confirms `jdbc:postgresql://db:5432/studentdb` and prints `Student API for Stanislav Pak is ready`.
- `docker compose exec -T app java -version`: Temurin Java 21.
- Initial image `pak-stanislav-student-api:v0.0.1-pak-stanislav` built successfully. After updating the Gradle application version to `1.0.0`, image `pak-stanislav-student-api:v1.0-pak-stanislav` was built.
- The final image size reported by `docker image inspect` is 557,035,667 bytes (about 557 MB).

API responses at `http://localhost:8081/api/students`:

| Action | Result |
| --- | --- |
| Create Stanislav Pak | 201 Created, `Location: /api/students/1` |
| List students | 200 OK, contains Stanislav Pak |
| Read student 1 | 200 OK |
| Update email | 200 OK, `pak@example.com` |
| Read missing student 999999 | 404 Not Found |
| Create invalid student | 400 Bad Request |
| Delete student 1 | 204 No Content |
| Read deleted student 1 | 404 Not Found |

The updated student was still present after `docker compose down` followed by `docker compose up -d`, confirming that the named PostgreSQL volume persists data. The record was then deleted as part of the CRUD demonstration.

## Commands for a live demonstration

```bash
git log --graph --oneline --decorate --all
git branch -avv
git tag -n
git remote -v
ls -l scripts/Pak_Stanislav_system_report.sh
tail -n 5 logs/Pak_Stanislav_system_report.log
grep 'Pak' logs/Pak_Stanislav_system_report.log
docker compose ps
docker compose logs --tail=30 app
docker image inspect pak-stanislav-student-api:v1.0-pak-stanislav
docker compose exec app java -version
```

The local `.env` contains the database password and is ignored by Git. It must stay private.
