# Cryptocojo frontend

The frontend for cryptocojo is made with React 18 and TypeScript.

Special dependencies for frontend

- [Recharts](https://recharts.org/en-US/) for creating a graph of coin/portfolio value over time.
- [MUI Material](https://mui.com/) for different pre-made components
- [React Cookie](https://www.npmjs.com/package/react-cookie) for setting and accessing userId in a cookie.
- [Recoil](https://recoiljs.org/) for global state management

## Development

Development of the React app started with create-react-app and has been setup with linting to ensure consistent code style and format. Components have been designed with reusability in mind. Many of the components are not specific to some jsx, but are rather split up into a data-component and jsx-component, such that another jsx-component easily can be made and extended with all the app logic contained in the data-component.

The strong type system of TypeScript have also been utilized to provide type safety throughout the application. This has made it easier to validate that the data that comes in to the components is of the right type. This was especially useful with processing of api data.

## Testing

To the test the app we have utilized Cypress and E2E testing. This gives us the ability to check that the whole application works as intended. Component testing was emitted due to time constraints and the fact that the E2E tests already tests many of the elements that we would test with component testing. The E2E test fail if components aren't mounted properly in the DOM. Of course having component and unit testing in addition would make testing of the React app more robust and flexible, but we decided to rather write good E2E tests that test all aspects of the app instead.

## Structure

- `/axios/`
  - provides methods for communicating with both the backend API and the coincap API
- `/components/`
  - Contains all components
- `/css/`
  - Stylesheets for app
- `/pages/`
  - Contains main pages for the app
- `/recoil/atoms/atoms.ts`
  - Atoms for state management through recoil
- `/types/`
  - TypeScript types for data objects

You will also find

`App.tsx` witch contains routes and providers

`.eslintrc`, `.eslintignore` and `.prettierrc` witch is config files for linting

`package.json` contains all dependencies for the project and is what gets installed with `yarn start`

## To get started with the react app navigate to

```bash
/cryptocojo-frontend
```

NB! If using yarn make sure its installed with

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
