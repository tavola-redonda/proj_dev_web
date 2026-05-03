#!/usr/bin/env bash
set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ENV_FILE="$PROJECT_ROOT/.env"

if [[ -f "$ENV_FILE" ]]; then
  set -a
  # shellcheck disable=SC1090
  source "$ENV_FILE"
  set +a
fi

TOMCAT_HOME="${TOMCAT_HOME:-}"

if [[ -z "$TOMCAT_HOME" ]]; then
  echo "TOMCAT_HOME nao definido. Ex: export TOMCAT_HOME=/caminho/para/apache-tomcat-10.1.x" >&2
  exit 1
fi

if [[ ! -d "$TOMCAT_HOME" ]]; then
  echo "TOMCAT_HOME invalido: $TOMCAT_HOME" >&2
  exit 1
fi

BUILD_DIR="$PROJECT_ROOT/build/classes"
WEBAPP_DIR="$PROJECT_ROOT/src/main/webapp"
LIB_DIR="$WEBAPP_DIR/WEB-INF/lib"
WAR_NAME="proj_dev_web.war"
WAR_PATH="$PROJECT_ROOT/$WAR_NAME"

mkdir -p "$BUILD_DIR"

find "$PROJECT_ROOT/src/main/java" -name "*.java" > "$PROJECT_ROOT/.sources.txt"

javac -encoding UTF-8 -d "$BUILD_DIR" \
  -classpath "$TOMCAT_HOME/lib/*:$LIB_DIR/*" \
  @"$PROJECT_ROOT/.sources.txt"

jar -cvf "$WAR_PATH" -C "$WEBAPP_DIR" . -C "$BUILD_DIR" .

cp "$WAR_PATH" "$TOMCAT_HOME/webapps/"
"$TOMCAT_HOME/bin/catalina.sh" run
