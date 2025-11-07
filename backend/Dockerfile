# Step 1: Build jar file
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /workspace
COPY . .
RUN ./mvnw -B -DskipTests package

# Step 2: Run jar file
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /workspace/target/*.jar
app.jar
ENV JAVA_OPTS="-Xmx512m"
ENV PORT=8081
EXPOSE 8081
ENTRYPOINT ["sh","-c","java $JAVA_OPTS-Dserver.port=${PORT} -jar /app/app.jar"]