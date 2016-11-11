#!/usr/bin/env bash
# To use: start RabbitMQ, MongoDB, the FrontendApplication, the Dispatcher, then run this script
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

curl localhost:8080/api/submit -H "Content-Type: application/json" -X POST -d "@$DIR/s2.json"
