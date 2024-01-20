## Demo application to sync [Canvas](https://canvas.instructure.com) courses to local database
### Installation
1. Clone the repository
2. Add you Canvas Client ID and Client Secret to the `application.properties` file
3. Run `compose.yaml` file with `docker-compose up -d` to start postgres database
4. Run `./gradlew bootRun` to start the application
5. Open `http://localhost:8080` in your browser