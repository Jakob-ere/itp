import axios from 'axios';

const baseUrl: String = 'https://8080-it1901groups2022-gr2204-wyl1msh3slz.ws.gitpod.stud.ntnu.no/api/v1';

/**
<<<<<<< Updated upstream
 * Method for signing in a user with username and password
 *
 * @param username username of the user to log in
 * @param password password of the user to log in
 * @returns A promise containing data about request
=======
 * Method for singing in a user
 *
 * @param username username of user to sign-in
 * @param password password of user to sign-in
 * @returns a promise containing data about the request
>>>>>>> Stashed changes
 */
export const loginUser = (username: String, password: String) => {
	if ((username && password) != null) {
		let data = axios
			.get(baseUrl + '/login?username=' + username + '&password=' + password)
			.then((response: any) => {
				return response;
			})
			.catch((e) => {
				return e;
			});
		return data;
	}
};

/**
<<<<<<< Updated upstream
 * Method for getting userinfo when userid is stored as a cookie
 *
 * @param userId the userid for authentication with backend
=======
 * Method for authentication a user with the backend from a cookie
 *
 * @param userId of the user to get data about
>>>>>>> Stashed changes
 * @returns a promise containing data about the request
 */
export const getSignedInUser = (userId: String) => {
	if (userId != null) {
		let data = axios
			.get(baseUrl + '/user/get_user?userId=' + userId)
			.then((response: any) => {
				return response;
			})
			.catch((e) => {
				return e;
			});
		return data;
	}
};

/**
<<<<<<< Updated upstream
 * Method for signing up a user
 *
 * @param username username of new user
 * @param password password of new user
 * @param email email of new user
=======
 * Method for singing-up a user
 *
 * @param username of the new user
 * @param password of the new user
 * @param email if the new user
>>>>>>> Stashed changes
 * @returns a promise containing data about the request
 */
export const signUpUser = (username: String, password: String, email: String) => {
	if (username != null && email != null && password != null) {
		let data = axios
			.post(
				baseUrl +
					'/user/create_user?username=' +
					username +
					'&password=' +
					password +
					'&email=' +
					email
			)
			.then((response: any) => {
				return response;
			})
			.catch((e) => {
				return e;
			});
		return data;
	}
};

/**
<<<<<<< Updated upstream
 * Method for turning off and on pro mode for a user
 *
 * @param userId userId of user to toggle pro mode
=======
 * Method for tuning on/off pro mode for a user
 *
 * @param userId of the user to toggle pro mode
>>>>>>> Stashed changes
 * @returns a promise containing data about the request
 */
export const toggleProMode = (userId: string) => {
	if (userId != null) {
		let data = axios
			.post(baseUrl + '/user/toggle_pro?userId=' + userId)
			.then((res) => {
				return res;
			})
			.catch((e) => {
				return e;
			});
		return data;
	}
};
