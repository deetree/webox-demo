FROM openjdk:17-slim as extractor
WORKDIR /project
ADD target/game*.jar /project

# Break up jar into 4 folders:
#    dependencies --> All jars identified by mvn
#    spring-boot-loader --> System for generating self-contained JAR/WAR
#    snapshot-dependencies--> Any dependency whose version contains SNAPSHOT
#    application --> application classes and resources
RUN java -Djarmode=layertools -jar game*.jar extract

FROM openjdk:17-slim
WORKDIR /app

COPY  --from=extractor /project/dependencies/. /app/.
COPY  --from=extractor /project/spring-boot-loader/. /app/.
COPY  --from=extractor /project/snapshot-dependencies/. /app/.
COPY  --from=extractor /project/application/. /app/.

CMD ["java", "org.springframework.boot.loader.JarLauncher"]