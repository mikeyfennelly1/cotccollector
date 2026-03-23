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
pushd "${BASEDIR}/.."

set -eou pipefail

./gradlew bootJar
cp ./build/libs/*SNAPSHOT.jar ./app.jar
VERSION=$(cat VERSION | tr -d '[:space:]')
IMAGE_NAME="mikeyfennelly/cotccollector:${VERSION}"
docker build -t "${IMAGE_NAME}" -t "mikeyfennelly/cotccollector:latest" .

docker login
docker push "${IMAGE_NAME}"
docker push "mikeyfennelly/cotccollector:latest"

popd