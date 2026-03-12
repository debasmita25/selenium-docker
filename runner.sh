#!/bin/sh

#-------------------------------------------------------------------
#  This script expects the following environment variables
#     HUB_HOST
#     BROWSER
#     THREAD_COUNT
#     TEST_SUITE
#     TEST_ENV
#-------------------------------------------------------------------

# Print received variables
echo "-------------------------------------------"
echo "HUB_HOST      : ${HUB_HOST:-hub}"
echo "BROWSER       : ${BROWSER:-chrome}"
echo "THREAD_COUNT  : ${THREAD_COUNT:-1}"
echo "TEST_SUITE    : ${TEST_SUITE}"
echo "TEST_ENV      : ${TEST_ENV:-qa}"
echo "-------------------------------------------"

# Wait for Selenium Grid hub to be ready
echo "Checking if hub is ready..!"
count=0

while [ "$(curl -s http://${HUB_HOST:-hub}:4444/status | jq -r .value.ready)" != "true" ]
do
  count=$((count+1))
  echo "Attempt: ${count}"

  if [ "$count" -ge 30 ]
  then
      echo "**** HUB IS NOT READY WITHIN 30 SECONDS ****"
      exit 1
  fi

  sleep 1
done

# Hub ready
echo "Selenium Grid is up and running. Running the tests..."

# Run TestNG
java -cp "libs/*" \
     -Dselenium.grid.enabled=true \
     -Dselenium.grid.hubHost="${HUB_HOST:-hub}" \
     -Dtest.environment="${TEST_ENV:-qa}" \
     -Dbrowser="${BROWSER:-chrome}" \
     org.testng.TestNG \
     -threadcount "${THREAD_COUNT:-1}" \
     "test-suites/${TEST_SUITE}"