# Defense notes — Stanislav Pak

These are short explanations for the live demonstration. Run the commands yourself and explain the output in your own words.

## Linux and Git

- `>` replaces a file's contents; `>>` appends to the end. The monitoring script uses `tee -a` so previous log entries remain.
- `chmod 744 script.sh` gives the owner read, write, and execute permissions, while group and others can only read. `ls -l` displays the permissions.
- `ps` lists processes. `tail -n 5 file` shows the last five lines. `grep Pak file` finds the surname in a log.
- Git stores commits locally; GitHub hosts a remote copy. `git status` shows the current branch and uncommitted changes. `git add` moves changes to the staging area; `git commit` records a snapshot.
- A branch lets you work without changing `main`. A merge combines histories; a conflict occurs when Git cannot choose between edits to the same lines. Resolve it by editing the file, then add and commit the result.
- `git fetch` downloads remote changes without merging. `git pull` fetches and then integrates them. A merge retains branch history; a rebase replays commits on a new base.
- A tag names a specific commit, such as `v1.0-pak-stanislav`. A commit records a change; a tag marks a version.
- `.env`, passwords, and database credentials must stay out of Git because a committed secret can be copied from history even after it is removed from the latest file. Use local environment variables and Jenkins Credentials when a secret is required.

## Docker and PostgreSQL

- An image is the packaged template; a container is a running instance. `docker build` creates the image, and `docker compose up` starts the app and database services.
- A multi-stage Dockerfile compiles with a JDK, then copies the JAR into a smaller runtime image. `FROM` selects an image; `WORKDIR` sets the working directory; `COPY` copies files; `RUN` executes during image build; `CMD` runs when the container starts; `EXPOSE` documents the application port.
- The app uses `db` instead of `localhost` because each container has its own localhost. Docker's dedicated network resolves the database service name. The network connects containers; the named volume stores PostgreSQL data across container recreation.
- The database health check runs `pg_isready`. Compose starts the app after PostgreSQL reports healthy. If connection fails, inspect `docker compose ps`, app and db logs, the `DB_URL` service name, database name, and environment variables.
- `docker stop` keeps a container for later restart; `docker rm` removes it. `docker compose down` removes containers and network, while the named volume remains unless `-v` is used.

## Jenkins

- Continuous Integration (CI) regularly builds and tests new commits. Continuous Delivery keeps a release ready for a manual deployment decision. Continuous Deployment automatically releases each passing change.
- A Jenkinsfile defines the pipeline in Git. The controller schedules work; an agent executes it. This machine's Jenkins node has Java 21 and Docker access so Gradle tests and `docker build` can run.
- Checkout retrieves code from GitHub. Build compiles it. Test runs automated checks before Docker Build. If a stage fails, later stages are skipped, which prevents packaging broken code.
- A public repository needs no checkout credential. A private repository needs an authorized GitHub credential stored in Jenkins Credentials, never written in the Jenkinsfile.
- Polling checks GitHub for new commits every two minutes. A webhook instead sends Jenkins an event immediately after a push.
- For a failed Gradle build, read the failing stage and test report in Jenkins console output, then reproduce with `./gradlew clean build` locally. For Docker Build failures, check daemon access, Dockerfile paths, build context, and the first failing Docker log line.
