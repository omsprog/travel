#!/bin/bash
mvn clean package -DskipTests
docker compose --profile integration up
ng serve -o