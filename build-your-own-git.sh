#!/usr/bin/env bash
set -e

cd "$(dirname "$0")"
mvn --batch-mode clean package

exec java -jar ./target/build-your-own-git-0.0.1-SNAPSHOT.jar