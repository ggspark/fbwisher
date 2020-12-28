#!/usr/bin/env bash
# Download facebook information with friends and messages selected https://www.facebook.com/dyi
# Cd to the directory downloaded from facebook
# bash findFacebookUnfriends.sh | grep title
SECONDS=0

for i in $(find ~/Downloads/facebook-messages -name '*.json' -type f); do
  MESSENGER_NAME=$(cat "$i" | jq 'if (.messages[0].content == "Happy\nBirthday\n:)") and (.messages[0].sender_name == "Gaurav Gupta") and (.messages | length) < 5 then .participants[0].name else null end')
  grep "$MESSENGER_NAME" ./friends/friends.json
done

# Complete
echo -e "\033[1;31m \nTime Elapsed: $((SECONDS / 3600)) Hrs, $(((SECONDS / 60) % 60)) Mins, $((SECONDS % 60)) Sec \033[0m"
echo -e "\033[1;31m \nCompleted \033[0m"
say "Completed"