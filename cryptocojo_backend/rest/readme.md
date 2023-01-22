# Module: rest

This module contains the REST API used to connect our frontend with our backend.

## Controllers and Service classes

In rest we have springboot restcontrollers annotated with @RestController and service classes annotated with @Service.
These can be found at ->

```bash
./src/main/java/rest/
```

In this folder we have:

- CoinController.java - Controller for fetch data that belongs to a user.
- CoinService.java - Service class for CoinController.java
- CryptocojoBackendApplication.java - Launches the springboot application
- LoginController.java - Logs in a user via username / password
- LoginService.java - Service class for LoginController.java
- UserController.java - Controller for creating / deleting users, buying/selling coins, toggling pro mode, getting a user with userId and getting account balance over time.
- UserService.java - Service class for UserController.java

## Choices

We chose to use Spring-boot for our rest api service. This is because we read that is was great for productivety and would not require a lot of boiler plate code. In addition some of the group members had some experience with it from before.

## Format for requests

For our REST API we support GET and POST requests. The GET request are on the format:

### Base URL

- http://localhost:8080/api/v1/

### LoginController - GET

We have three different controller for three different types of actions. For logging in we have LoginController. For this one the format would be:

- http://localhost:8080/api/v1/login + username + password as paramters

### CoinController - GET

For getting coins that belong to a user you would use the CoinController. The format for this controller would be:

- http://localhost:8080/api/v1/coin/get_owned_coins + userId as parameters

### UserController

For getting information about a user via userId, buying / selling coins, creating / deleting a user, toggling pro mode and getting account value over an interval you would user the format:

#### GET

- http://localhost:8080/api/v1/user/get_user? + userId as parameter
- http://localhost:8080/api/v1/user/get_account_interval? + timeStamp + userId as parameters

#### POST

- http://localhost:8080/api/v1/user/create_user? + username + password + email as parameters
- http://localhost:8080/api/v1/user/add_currency? + userId + coin + amount as parameters
- http://localhost:8080/api/v1/user/remove_currency? + userId + coin + amount as parameters
- http://localhost:8080/api/v1/user/toggle_pro? + userId as parameter
