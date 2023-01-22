import { Link } from 'react-router-dom';
import '../css/Navbar.css';
import { useRecoilState, useResetRecoilState, useSetRecoilState } from 'recoil';
import { loadingState, loggedInState, signedInUser } from '../recoil/atoms/atoms';
import { useCookies } from 'react-cookie';
import { Button, Fade, LinearProgress } from '@mui/material';

/**
 * Renderes the navbar
 */

function Navbar({ isLoggedIn }: { isLoggedIn: boolean }) {
	const setLoggedIn = useSetRecoilState(loggedInState);
	const [loading] = useRecoilState(loadingState);
	const removeUser = useResetRecoilState(signedInUser);
	const [, , removeCookie] = useCookies(['cryptocojo']);

	/**
	 * Signs a user out of the application and removed the cookie that is set so one can sign in with a different user.
	 */
	const handleSignOut = () => {
		setLoggedIn(false);
		removeUser();
		removeCookie('cryptocojo');
	};

	return (
		<>
			{isLoggedIn ? (
				<div className="nav_container">
					<div className="Nav">
						<Link className="Nav-routes" to={'/dashboard'}>
							<img className="Nav-icon" src="/png/personalHoldings.png" alt="Personal Holdings" />
						</Link>
						<Link className="Nav-routes" to={'/dashboard/settings'}>
							<img className="Nav-icon" src="/png/settings.png" alt="Settings" />
						</Link>
						<Link className="Nav-routes" to={'/'}>
							<Button
								onClick={handleSignOut}
								sx={{
									backgroundColor: '#E7F6F2',
									color: 'black',
									':hover': {
										backgroundColor: '#06283D',
										color: '#A5C9CA'
									}
								}}
								variant="contained">
								Sign out
							</Button>
						</Link>
					</div>
					<Fade
						in={loading}
						style={{
							transitionDelay: '1ms',
							backgroundColor: '#395b64'
						}}
						unmountOnExit>
						<LinearProgress
							color="primary"
							sx={{
								'& .MuiLinearProgress-bar': {
									backgroundColor: '#EB6440'
								}
							}}
						/>
					</Fade>
				</div>
			) : (
				<div className="nav_container">
					<div className="Nav">Velkommen</div>
					<Fade
						in={loading}
						style={{
							transitionDelay: '0ms',
							backgroundColor: '#395b64'
						}}
						unmountOnExit>
						<LinearProgress />
					</Fade>
				</div>
			)}
		</>
	);
}

export default Navbar;
