#!/usr/bin/env bash

HTTP_RESP=0
CRUD_URL="http://localhost:8080/crud/v1/task/getTasks"

check_crud() {
    HTTP_CODE=$(
    curl ${CRUD_URL} \
        --write-out %{http_code} \
        --silent \
        --output /dev/null \
    )
}

if ./runcrud.sh; then
    while [ "$HTTP_CODE" != 200 ]
    do
        check_crud
        echo "Waiting for crud tasks to be up and running... [$HTTP_CODE]"
        sleep 1
    done
    firefox ${CRUD_URL} &
    disown -h
    exit 0
else
    echo "There were build errors!"
fi
