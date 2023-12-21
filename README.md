# Start Client Project

## PreRequisites

- Install [NodeJS](https://nodejs.org/en/download/)

## Install

```bash
npm install --prefix client
```

## Run

```bash
npm run dev --prefix client
```

## Quick Start

```bash
cd client && bash quickstart.sh
```

just run this script to install and run the client project

# Start Server Project

## PreRequisites

- Install [Docker](https://docs.docker.com/get-docker/)
- Install [Docker Compose](https://docs.docker.com/compose/install/)
- Install [Java 17](https://www.oracle.com/java/technologies/downloads/#java17)

## Docker Compose up

```bash
docker compose -f server/dev/docker-compose.yml up --build -d
```

## Install

```bash
chmod +x ./server/gradlew && ./server/gradlew build --project-dir server
```

## Run

```bash
java -Dspring.profiles.active=local -jar ./server/build/libs/server-0.0.1-SNAPSHOT.jar
```

## Quick Start

```bash
cd server && bash quickstart.sh
```

just run this script to install and run the server project

## Enter to Site

```bash
http://localhost
```

![login page](login.png)

You will see the login page

![register page](register.png)

You can register in this page

![todo page](todo.png)

You can see your todo list in this page
