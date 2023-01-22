import { Stack, Switch, Typography } from '@mui/material';
import { useRecoilState, useSetRecoilState } from 'recoil';
import { getSignedInUser, toggleProMode } from '../axios/UserService';
import CoinTable from '../components/CoinTable';
import '../css/settings.css';
import { loadingState, ownedCoins, signedInUser } from '../recoil/atoms/atoms';
import { User } from '../types/User';

/**
 * Component that renderes the settings page.
 */
const Settings = () => {
	const [user, setUser] = useRecoilState(signedInUser);
	const [owned] = useRecoilState(ownedCoins);
	const setLoading = useSetRecoilState(loadingState);

	/**
	 * Sends a request to backend to toggle pro-mode for a user.
	 */
	const handleToggleProMode = () => {
		setLoading(true);
		if (user?.userId != undefined) {
			toggleProMode(user?.userId)?.then((res) => {
				if (res.status == 200) {
					getSignedInUser(user.userId)?.then((result) => {
						if (result.status == 200) {
							setUser(result.data as User);
						}
					});
				}
			});
		}
		setLoading(false);
	};

	return (
		<div className="settingsContainer">
			<div>
				<h1>Settings</h1>
				<p style={{ margin: 0, color: 'gray' }}>Pro mode</p>
				<Stack direction="row" spacing={1} alignItems="center">
					<Typography sx={{ color: 'white' }}>No-Pro</Typography>
					<Switch
						sx={{
							'& .css-1yjjitx-MuiSwitch-track': {
								backgroundColor: 'white'
							}
						}}
						aria-label="toggle pro mode"
						onChange={handleToggleProMode}
						checked={user?.pro}
					/>
					<Typography sx={{ color: 'white' }}>Pro</Typography>
				</Stack>
			</div>
			<div
				style={{
					width: '70%',
					display: 'flex',
					flexDirection: 'column',
					alignContent: 'center'
				}}>
				<h1>You</h1>
				<div>
					<p style={{ margin: 0, color: 'gray' }}>Username</p>
					<p style={{ margin: 0 }}>{user?.name}</p>
				</div>
				<div>
					<p style={{ margin: 0, color: 'gray' }}>Email</p>
					<p style={{ margin: 0 }}>{user?.email}</p>
				</div>
				<div>
					<p>Owned coins</p>
					<CoinTable coinList={owned} height={500} />
				</div>
			</div>
		</div>
	);
};

export default Settings;
