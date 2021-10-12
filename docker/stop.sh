#!/bin/bash
docker container stop rest-app
docker container rm rest-app
docker volume prune -f