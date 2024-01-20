## Demo application to sync [Canvas](https://canvas.instructure.com) courses to local database

### To run the application:
1. Clone the repository
2. Add your Canvas Host, Client ID and Client Secret to the `application.properties` file
3. Run `compose.yaml` file with `docker-compose up -d` to start postgres database
4. Run `./gradlew bootRun` to start the application
5. Open `http://localhost:8080` in your browser

### To run tests:
1. Make sure you have docker up and running
2. Run `./gradlew check` to run all tests