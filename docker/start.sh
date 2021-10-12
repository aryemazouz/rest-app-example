#!/bin/bash

#Find and set working directory
DIR=${PWD}

if [[ "${DIR}" =~ ^/[a-zA-X]/ ]];
then
  echo "Windows path extra add leading slash"
  DIR="/${DIR}"
fi

CONF_DIR="${DIR}/conf"
echo "Set Conf Directory: ${CONF_DIR}"

#Running docker command
docker run \
  --name rest-app \
	-e "JAVA_OPTS=-Dlogging.config=./conf/logback.xml" \
	-p 8081:8081 \
	-v ${CONF_DIR}:/conf \
	aryemazouz/rest-app-example \
	./conf/input-example.json --server.port=8081


