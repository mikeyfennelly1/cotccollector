#!/bin/bash

################################################
# SETUP
################################################
OS=$(uname)
if [[ "$OS" == "Darwin" ]]; then
	# OSX uses BSD readlink
	BASEDIR="$(dirname "$0")"
else
	BASEDIR=$(readlink -e "$(dirname "$0")/")
fi
cd "${BASEDIR}"

IMAGE_NAME="mikeyfennelly/ise-y2-b3-project-collector:dev"

${BASEDIR}/gradlew bootJar
cp "${BASEDIR}/app/build/libs/*SNAPSHOT.jar" "${BASEDIR}/app.jar"
docker build -t "${IMAGE_NAME}" "${BASEDIR}/.."

docker login
docker push "${IMAGE_NAME}"
