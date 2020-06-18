#!/bin/bash
BASEDIR=$(dirname "$0")
cd "$BASEDIR" || exit
SECONDS=0

source ~/.bash_profile

./gradlew installDist
build/install/fbwisher/bin/fbwisher

echo -e "\033[0;35m Time Elapsed: $((SECONDS/3600)) Hrs, $(((SECONDS/60)%60)) Mins, $((SECONDS%60)) Sec \033[0;37m"