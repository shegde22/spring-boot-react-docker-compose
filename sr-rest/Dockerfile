### Build ###
from maven:3.6-jdk-8-alpine
# Copy source files
copy /src /usr/src/studentRegistration/src
# Copy pom
copy pom.xml /usr/src/studentRegistration
# Copy ojdbc driver
copy ojdbc7.jar /usr/src/studentRegistration
# Copy Dockerfile (just)
copy Dockerfile /usr/src/studentRegistration
# Install oracle driver
run mvn install:install-file -Dfile=/usr/src/studentRegistration/ojdbc7.jar -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.2 -Dpackaging=jar
# Build jar
run mvn -f /usr/src/studentRegistration/pom.xml clean install
workdir /usr/src/studentRegistration/
cmd mvn spring-boot:run -Drun.jvmArguments="-Dserver.port=8080"
