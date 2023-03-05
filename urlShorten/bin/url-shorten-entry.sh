#!/usr/bin/env sh

set -e
echo "Setting up url-shorten service..."
./wait-for-it.sh $DB_SERVICE -s -t 120 -- java -jar url-shorten.jar