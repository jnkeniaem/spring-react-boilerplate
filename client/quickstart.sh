#!/bin/bash
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[0;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
WHITE='\033[0;37m'
RESET='\033[0m'

echo -e $GREEN "이 스크립트는 spring-react-boilerplate/client 디렉토리에서 실행되어야 합니다!!" $RESET

echo -e $GREEN "1. 클라이언트 애플리케이션 의존성 설치" $RESET
npm install

echo -e $GREEN "2. 클라이언트 애플리케이션 실행" $RESET
npm run dev
