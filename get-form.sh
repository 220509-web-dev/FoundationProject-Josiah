#!/bin/bash
function check4comma() {
if echo "$1" | grep -q ","
then
echo "Invalid input. No commas allowed."
echo Your entry was: $1
exit 1
fi
}

# Ask the user for login details
read -p 'Username: ' uservar
check4comma $uservar
read -sp 'Password: ' passvar
check4comma $passvar
echo
echo Thank you, $uservar we now have your login details
var=$uservar,$passvar
echo $var is the final output
echo $var>>users.csv