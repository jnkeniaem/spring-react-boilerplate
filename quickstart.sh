#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
WHITE='\033[0;37m'
RESET='\033[0m'

echo -e $GREEN "이 스크립트는 spring-react-boilerplate 루트 디렉토리에서 실행되어야 합니다!!" $RESET

# cd frontend

# echo -e $GREEN "1. 클라이언트 애플리케이션 의존성 설치" $RESET
# npm install

# echo -e $GREEN "2. 클라이언트 애플리케이션 실행" $RESET
# npm run dev

# cd ..
cd server

echo -e $PURPLE "3. Docker 컨테이너 실행" $RESET
# # docker compose 명령어를 찾을 수 없다면, docker-compose로 실행
if command -v docker compose &> /dev/null
then
    docker compose -f ./dev/docker-compose.yml down --rmi all
    docker compose -f ./dev/docker-compose.yml up --build -d
else
    echo "docker compose 명령어를 찾을 수 없습니다. docker-compose로 실행합니다."
    docker-compose -f ./dev/docker-compose.yml down --rmi all
    docker-compose -f ./dev/docker-compose.yml up --build -d
fi

echo -e $PURPLE "4. 서버 애플리케이션 의존성 설치" $RESET
./gradlew build -x test

echo -e $PURPLE "5. 서버 애플리케이션 실행" $RESET
java -Dspring.profiles.active=local -jar build/libs/server-0.0.1-SNAPSHOT.jar
