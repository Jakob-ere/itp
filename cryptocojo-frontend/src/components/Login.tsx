import React, { useState } from 'react';
import '../css/login.styles.css';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { useSetRecoilState } from 'recoil';
import { loadingState, loggedInState, signedInUser } from '../recoil/atoms/atoms';
import { User } from '../types/User';
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';
import { loginUser } from '../axios/UserService';

type LoginProps = {
	setSignInFlowCreate: React.Dispatch<React.SetStateAction<boolean>>;
};

/**
 * Component that is responsible for logging in a user
 */

const Login: React.FC<LoginProps> = ({ setSignInFlowCreate }) => {
	const [username, setUsername] = useState('');
	const [password, setPassword] = useState('');
	const setUser = useSetRecoilState(signedInUser);
	const setLoggedIn = useSetRecoilState(loggedInState);
	// eslint-disable-next-line no-unused-vars
	const [cookies, setCookie, removeCookie] = useCookies(['cryptocojo']);
	const setLoading = useSetRecoilState(loadingState);
	const [statusMessage, setStatusMessage] = useState('');

	const nav = useNavigate();

	/**
	 * Logs-in a user and sets a cookie so user can be authenticated even if tab is refreshed or exited.
	 */
	const handleLogin = () => {
		setLoading(true);
		if ((username && password) != null) {
			loginUser(username, password)?.then((userResponse) => {
				//? axios http request
				if (userResponse.status == 200) {
					setUser(userResponse.data as User);
					setLoggedIn(true);
					setCookie('cryptocojo', (userResponse.data as User).userId, { maxAge: 7200 });
					setLoading(false);
					nav('/dashboard');
				} else if (userResponse.response.status == 500) {
					setStatusMessage('Server error');
					setLoading(false);
				} else {
					setStatusMessage('Wrong username or password');
					setLoading(false);
				}
			});
		}
	};

	return (
		<div className="inputContainer">
			<h1
				style={{
					color: 'black'
				}}>
				Login to Cryptocojo
			</h1>
			<p style={{ color: 'black', height: 20 }}>{statusMessage}</p>
			<div className="inputForm">
				<TextField
					type="email"
					id="username_input"
					label="Username"
					size="small"
					variant="outlined"
					// eslint-disable-next-line no-undef
					onChange={(event: React.ChangeEvent<HTMLInputElement>) => setUsername(event.target.value)}
				/>
				<TextField
					type="password"
					id="password_input"
					label="Password"
					size="small"
					variant="outlined"
					// eslint-disable-next-line no-undef
					onChange={(event: React.ChangeEvent<HTMLInputElement>) => setPassword(event.target.value)}
				/>
				<div className="buttonDiv">
					<Button variant="contained" onClick={handleLogin}>
						Login
					</Button>
					<Button variant="contained" onClick={() => setSignInFlowCreate(true)}>
						Sign up
					</Button>
				</div>
			</div>
		</div>
	);
};

export default Login;
