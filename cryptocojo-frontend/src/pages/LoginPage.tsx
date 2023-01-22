import { useEffect, useState } from 'react';
import Login from '../components/Login';
import SignUp from '../components/SignUp';
import { useRecoilState } from 'recoil';
import { useNavigate } from 'react-router-dom';
import { loggedInState } from '../recoil/atoms/atoms';

/**
 * Component that renderes the loginPage
 */
const LoginPage = () => {
	const [signInFlowCreate, setSignInFlowCreate] = useState(false);
	const [loggedIn] = useRecoilState(loggedInState);

	const navigate = useNavigate();

	const flow = () => {
		if (!signInFlowCreate) {
			return <Login setSignInFlowCreate={setSignInFlowCreate} />;
		} else {
			return <SignUp setSignInFlowCreate={setSignInFlowCreate} />;
		}
	};

	useEffect(() => {
		if (loggedIn) {
			navigate('/dashboard');
		}
	});

	return <div style={{ textAlign: 'center' }}>{flow()}</div>;
};

export default LoginPage;
