import React, { useState } from 'react';
import '../css/login.styles.css';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import { signUpUser } from '../axios/UserService';
import { User } from '../types/User';
import { useSetRecoilState } from 'recoil';
import { loadingState, loggedInState, signedInUser } from '../recoil/atoms/atoms';
import { useNavigate } from 'react-router-dom';
import { useCookies } from 'react-cookie';

type SignupProps = {
	setSignInFlowCreate: React.Dispatch<React.SetStateAction<boolean>>;
};

/**
 * Component that signs-up a user.
 */
const SignUp: React.FC<SignupProps> = ({ setSignInFlowCreate }) => {
	const [username, setUsername] = useState<string>('');
	const [email, setEmail] = useState<string>('');
	const [password, setPassword] = useState<string>('');
	const [validEmail, setValidEmail] = useState<boolean>(false);
	const [validPassword, setValidPassword] = useState<boolean>(false);
	const [statusMessage, setStatusMessage] = useState<string>('');
	const [, setCookie] = useCookies(['cryptocojo']);
	const setLoading = useSetRecoilState(loadingState);

	const setUser = useSetRecoilState(signedInUser);
	const setLoggedIn = useSetRecoilState(loggedInState);

	const nav = useNavigate();

	/**
	 * Sends request to backend to sign up a user, sets a cookie with userid and navigates the user to the dashboard page.
	 */
	const handleCreate = () => {
		setLoading(true);
		if (validEmail && validPassword && username != null) {
			signUpUser(username, password, email)?.then((signUpResponse) => {
				// eslint-disable-next-line no-console
				console.log(signUpResponse);
				if (signUpResponse.status == 201) {
					setUser(signUpResponse.data as User);
					setLoggedIn(true);
					setCookie('cryptocojo', (signUpResponse.data as User).userId, { maxAge: 7200 });
					setLoading(false);
					nav('/PersonalHoldings');
				} else if (signUpResponse.response.status == 500) {
					setStatusMessage('Unkown server error');
					setLoading(false);
				} else {
					setStatusMessage('Could not create user');
					setLoading(false);
				}
			});
		}
	};

	return (
		<div className="inputContainer">
			<h1 style={{ color: 'black' }}>Sign up to Cryptocojo</h1>
			<p style={{ color: 'black', height: 20 }}>{statusMessage}</p>
			<a
				style={{ color: 'black' }}
				className="clickable_text"
				onClick={() => setSignInFlowCreate(false)}>
				{'<< Back To Login'}
			</a>
			<div className="inputForm">
				<TextField
					id="username_input"
					type="email"
					label="Username"
					size="small"
					variant="outlined"
					// eslint-disable-next-line no-undef
					onChange={(event: React.ChangeEvent<HTMLInputElement>) => setUsername(event.target.value)}
				/>
				<TextField
					id="email_input"
					color={validEmail ? 'success' : 'error'}
					type="email"
					label="Email"
					size="small"
					variant="outlined"
					// eslint-disable-next-line no-undef
					onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
						const regex: RegExp = new RegExp(
							'^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$'
						);
						if (regex.test(event.target.value)) {
							setEmail(event.target.value);
							setValidEmail(true);
						} else {
							setValidEmail(false);
						}
					}}
				/>
				<TextField
					id="password_input"
					color={validPassword ? 'success' : 'error'}
					type="password"
					label="Password"
					size="small"
					variant="outlined"
					// eslint-disable-next-line no-undef
					onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
						if (event.target.value.length > 7) {
							setPassword(event.target.value);
							setValidPassword(true);
						} else {
							setValidPassword(false);
						}
					}}
				/>
				<div className="buttonDiv">
					<Button variant="contained" onClick={handleCreate}>
						Create User
					</Button>
				</div>
			</div>
		</div>
	);
};

export default SignUp;
