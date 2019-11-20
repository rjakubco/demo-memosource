# Memsource Third Party App

This is just quick application to showcase usage of Memsource API, so only in-memory H2 database is used. Restarting the app also deletes all saved users.

The application is using Spring security with Thymeleaf, with fully functional login and registration of users. 

## Running from source

To run the application just execute:

```./gradlew bootRun```

This will start the application using gradle. The application runs by default on port `8081`. This configuration can be changed in [application.yml](/src/main/resources/application.yml)

## Running the application from jar

First, you need to build the application using gradle:
```
./gradlew bootJar
```

This will build runnable jar from the source that will be located in `build/libs/`. To successfully to execute jar you need to have installed java on your system and just execute:
```
java -jar memsource-0.0.1-SNAPSHOT.jar
```

It is also possible to change the server port for the SpringBoot, by adding `--server.port=9000` to the command.