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

if ! chmod +x "$TOMCAT_HOME/bin"/*.sh; then
  echo "Nao foi possivel ajustar permissao em $TOMCAT_HOME/bin/*.sh" >&2
  exit 1
fi

DB_USER="${DB_USER:-app}"
DB_PASS="${DB_PASS:-app123}"
DB_NAME="${DB_NAME:-dbteste}"

# Create dynamic SQL to set up user from .env variables
DYNAMIC_SQL=$(cat <<EOF
CREATE DATABASE IF NOT EXISTS \`$DB_NAME\`;
CREATE USER IF NOT EXISTS '$DB_USER'@'localhost' IDENTIFIED BY '$DB_PASS';
GRANT ALL PRIVILEGES ON \`$DB_NAME\`.* TO '$DB_USER'@'localhost';
FLUSH PRIVILEGES;
EOF
)

if ! echo "$DYNAMIC_SQL" | sudo mysql; then
  echo "Falha ao criar usuario e banco de dados" >&2
  exit 1
fi

SETUP_SQL="$PROJECT_ROOT/setup.sql"
if [[ -f "$SETUP_SQL" ]]; then
  if ! sudo mysql "$DB_NAME" < "$SETUP_SQL"; then
    echo "Falha ao executar setup.sql" >&2
    exit 1
  fi
fi

BUILD_DIR="$PROJECT_ROOT/build/WEB-INF/classes"
WEBAPP_DIR="$PROJECT_ROOT/src/main/webapp"
LIB_DIR="$WEBAPP_DIR/WEB-INF/lib"
WAR_NAME="proj_dev_web.war"
WAR_PATH="$PROJECT_ROOT/$WAR_NAME"

mkdir -p "$BUILD_DIR"

find "$PROJECT_ROOT/src/main/java" -name "*.java" > "$PROJECT_ROOT/.sources.txt"

javac -encoding UTF-8 -d "$BUILD_DIR" \
  -classpath "$TOMCAT_HOME/lib/*:$LIB_DIR/*" \
  @"$PROJECT_ROOT/.sources.txt"

jar -cvf "$WAR_PATH" -C "$WEBAPP_DIR" . -C "$PROJECT_ROOT/build" WEB-INF

cp "$WAR_PATH" "$TOMCAT_HOME/webapps/"
"$TOMCAT_HOME/bin/catalina.sh" run
