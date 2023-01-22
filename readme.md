Cryptocojo is a app made for the course IT1901. The app was made by a group of 4 during fall of 2022. The development process followed the scrum method, and this repository included the final product.

The project was made by
- Ole
- Jakob
- Oskar
- Casper

# DEMO

Klikk på bildet så åpned videoen på YouTube

[![movsearch demo](https://img.youtube.com/vi/dXe-v0XUmVQ/0.jpg)](https://www.youtube.com/watch?v=dXe-v0XUmVQ)



[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2022/gr2204/gr2204)

# Cryptocojo

## Project structure

To get started make sure you are in the **gr2204** directory.
Here you can find:

- [Templates for git](.gitlab/)
- [Userstories](brukerhistorier_personas/)
- [Backend module of this project](cryptocojo_backend/)
- [Frontend module of this project](cryptocojo-frontend/)
- [Documentation](docs/)
- [Diagrams](diagrams/)

## Templates for git

`gr2204/.gitlab/` Here you can find the different templates we have used for issue description. Such as a template for bugs and features. You can also find a template for merging. These describe how to structure a merge message description.

## User stories - "brukerhistorier_personas"

`gr2204/brukerhistorier_personas/` Under user stories we have a personas character description, as well as our user stories used to decide what features we wanted in our application.

## Backend - "cryptocojo_backend"

`gr2204/cryptocojo_backend/` you have three modules.

- Core which has all the logic connected to our project. It also takes care of storing data.
- Rest which is an API used to connect our frontend to the backend logic. Here you will also find tests.
- UI witch contains all the javafx logic and fxml files.

## Frontend - "cryptocojo-frontend"

Under `gr2204/cryptocojo_frontend/` you will find our React app with Typescript. You will also find end to end tests here.

## Documentation - "docs"

Under `gr2204/docs/` you will find release documents for each of the releases.(1,2,3)

## Diagrams - "diagrams"

Under `gr2204/diagrams/` you will find three different diagrams. A class diagram visualizing how all our classes are connected.

## Running the application

In order to start our application you have **1.** start the REST API at **localhost:8080/**. After this is done you have to **2.** start our react application at **localhost:3000/**.

### 1. REST API

To start the REST API you have to go into cryptocojo_backend/

```bash
cd cryptocojo_backend/
```

Then do a clean install with maven

```bash
mvn clean install
```

Then go to the rest module

```bash
cd rest/
```

From here you can start the application by typing

```bash
mvn spring-boot:run
```

### 2. JavaFx

To view the javafx application navigate to

```bash
/cryptocojo_backend
```

and run the following commands

```bash
mvn javafx:jlink -f ./ui/pom.xml
```

and

```bash
mvn jpackage:jpackage -f ./ui/pom.xml
```

The executable file that is generated should launch automatically. If it doesn't you can find the file in this folder.

```bash
cryptocojo_backend/ui/target/dist
```

### 3. React

To get started with the react app navigate to

```bash
/cryptocojo-frontend
```

#### Yarn is recommended due to peer-dependency conflicts with npm

NB! Make sure yarn is installed with npm

```bash
yarn --version
```

If yarn is not installed this can be installed globally with

```bash
npm install --global yarn
```

### Installing dependencies

To install all dependencies needed for this app run

```bash
yarn install || npm install
```

### Starting app

This app is dependant on the cryptocojo backend to be running

Start the app with

```bash
yarn start || npm start
```

### Linting and rules

Run the linting script to see style errors

```bash
yarn run lint || npm run lint
```

Run prettier to format all code to the linting standard. NB! This does not fix all errors from eslint but most of them.

```bash
yarn run format || npm run format
```

### Login

To log in with a user that has some test-data use the test account you can use the account below. This account have bought coins over a period of time which allows you to view your account balance over time.

Username: ola

Password: 12345678

If you want to start fresh, create an account.

Once you are logged inn you can view different crypto coins and see their real price. You also have the opportunity to buy and sell coin which. All your activity will be stored to your account.

To get a more detailed view over the different coins, you can go to the settings page and click on pro mode.

## Testing

After implementing a frontend with React we have two different sets of test. The first one is located in cryptocojo_backend/ and is related to our backend. Here you can find tests for core, ui and rest modules. The second sets of test is in the frontend application.

### Backend

In order to test our backend you have to:

```bash
cd ../cryptocojo_backend
mvn test
```

#### Jacoco

When tests have been run, there will be two test reports from jacoco. One for each module. In order to view the tests for the core module you have to go to:

```bash
cryptocojo_backend/core/target/site/jacoco/index.html
```

In order to view the tests for the rest modules you have to go to:

```bash
cryptocojo_backend/rest/target/site/jacoco/index.html
```

#### Checkstyle

To generate a code quality report using checkstyle

```bash
mvn checkstyle:checkstyle
mvn site
```

The report can be found at

```bash
cryptocojo_backend/core/target/site/checkstyle.html
```

and

```bash
cryptocojo_backend/rest/target/site/checkstyle.html
```

#### Spotbugs

To check the project for bugs using spotbugs

```bash
mvn spotbugs:gui
```

Then close out the GUI to see bugs for each module until the command finishes.

## Frontend Testing

### E2E testing with Cypress

Make sure the app is running

To open the cypress app run

```bash
yarn run cypress open || npm run cypress open
```

then navigate to E2E tests and run the three different specs `apiTests.cy.ts`, `cryptocojo.cy.ts` and `register.cy.ts`

## Documentation

[Release 1](docs/0.1.md)

[Release 2](docs/0.2.md)

[Release 3](docs/0.3.md)
