import { useEffect } from 'react';
import './App.css';
import LoginPage from './pages/LoginPage';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Settings from './pages/Settings';
import Navbar from './components/Navbar';
import PersonalHoldings from './components/PersonalHoldings';
import Footer from './components/Footer';
import { useRecoilState, useSetRecoilState } from 'recoil';
import { loggedInState, signedInUser } from './recoil/atoms/atoms';
import { getSignedInUser } from './axios/UserService';
import { User } from './types/User';
import { useCookies } from 'react-cookie';
import DashBoard from './pages/DashBoard';

function App() {
	const [loggedIn, setLoggedIn] = useRecoilState(loggedInState);
	const setUser = useSetRecoilState(signedInUser);
	const [cookies] = useCookies(['cryptocojo']);

	useEffect(() => {
		if (!loggedIn) {
			const authToken: String | null = cookies.cryptocojo;
			if (authToken != null) {
				getSignedInUser(authToken)?.then((userResponse) => {
					if (userResponse.status == 200) {
						setUser(userResponse.data as User);
						setLoggedIn(true);
					}
				});
			}
		}
	});

	return (
		<div className="main">
			<BrowserRouter>
				<Navbar isLoggedIn={loggedIn} />
				<Routes>
					<Route index element={<LoginPage />} />
					<Route path="/dashboard" element={<DashBoard />}>
						<Route index element={<PersonalHoldings />} />
						<Route path="settings" element={<Settings />} />
						<Route path="*" element={<PersonalHoldings />} />
					</Route>
					<Route path="*" element={<LoginPage />} />
				</Routes>
				<Footer />
			</BrowserRouter>
		</div>
	);
}

export default App;
