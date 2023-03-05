
docker container run --rm --name maven-build --volume "%USERPROFILE%"\.m2:/root/.m2 --volume "%CD%":/app --workdir /app --cpus 2 maven:3.6.3-jdk-8 mvn clean package