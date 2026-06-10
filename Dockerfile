FROM tomcat:10.1-jdk17-temurin-jammy AS build

WORKDIR /app

RUN apt-get update && apt-get install -y curl ca-certificates && rm -rf /var/lib/apt/lists/*

COPY src/main/java ./src/main/java
COPY src/main/webapp ./src/main/webapp

RUN mkdir -p src/main/webapp/WEB-INF/lib \
    && curl -fSL -o src/main/webapp/WEB-INF/lib/gson-2.14.0.jar https://repo1.maven.org/maven2/com/google/code/gson/gson/2.14.0/gson-2.14.0.jar \
    && curl -fSL -o src/main/webapp/WEB-INF/lib/jbcrypt-0.4.jar https://repo1.maven.org/maven2/org/mindrot/jbcrypt/0.4/jbcrypt-0.4.jar

RUN mkdir -p build/WEB-INF/classes \
    && find src/main/java -name "*.java" > .sources.txt \
    && javac -encoding UTF-8 -d build/WEB-INF/classes -classpath "/usr/local/tomcat/lib/*:src/main/webapp/WEB-INF/lib/gson-2.14.0.jar:src/main/webapp/WEB-INF/lib/jbcrypt-0.4.jar" @.sources.txt \
    && jar -cvf proj_dev_web.war -C src/main/webapp . -C build WEB-INF

FROM tomcat:10.1-jdk17-temurin-jammy AS runtime

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/proj_dev_web.war /usr/local/tomcat/webapps/proj_dev_web.war

EXPOSE 8080

CMD ["catalina.sh", "run"]