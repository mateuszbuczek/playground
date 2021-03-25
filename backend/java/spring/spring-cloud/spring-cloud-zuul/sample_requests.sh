#!/usr/bin/env bash

curl -X GET -v \
     --url 'http://localhost:8080/api/services/1/users'

curl -X GET -v \
     --url 'http://localhost:8080/api/services/1/users/123'