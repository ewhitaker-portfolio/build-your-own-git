#!/usr/bin/env bash
set -e

cd "$(dirname "$0")"
mvn --batch-mode clean package

sudo rm --recursive --force /usr/local/include/build-your-own-git/ /usr/local/lib/libbuild-your-own-git.so

cmake -DCMAKE_EXPORT_COMPILE_COMMANDS=ON -G Ninja -S . -B ./bin
mv --update ./bin/compile_commands.json ./compile_commands.json
cmake --build ./bin --target build-your-own-git
sudo cmake --install ./bin --prefix "/usr/java/packages"

exec java -jar ./bin/build-your-own-git-0.0.1-SNAPSHOT.jar "$@"
