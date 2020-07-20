#!/bin/bash
BASEDIR=$(dirname "$0")
cd "$BASEDIR" || exit
SECONDS=0

source ~/.bash_profile

#Exit script if /tmp/fbwisher_date.txt date is today date
TODAY=$(date '+%Y-%m-%d')
FILE="/tmp/fbwisher_date.txt"
if grep -q "$TODAY" "$FILE"; then
  exit
fi
echo "$TODAY" >/tmp/fbwisher_date.txt

./gradlew installDist
build/install/fbwisher/bin/fbwisher

echo -e "\033[1;31m Time Elapsed: $((SECONDS / 3600)) Hrs, $(((SECONDS / 60) % 60)) Mins, $((SECONDS % 60)) Sec \033[0m"
