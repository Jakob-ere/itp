/* eslint-disable no-console */
import { Button, ToggleButton, ToggleButtonGroup } from '@mui/material';
import React, { useState } from 'react';
import '../css/personal_holdings.css';
import {
	ownedCoins,
	showTotalState,
	selectedCoinState,
	triggerCoinUpdateState,
	allAvailableCoins
} from '../recoil/atoms/atoms';
import { useRecoilState } from 'recoil';
import { Coin } from '../types/User';
import CoinDetails from './CoinDetails';
import { styled } from '@mui/material/styles';
import CoinList from './CoinList';

export const StyledToggleButtonGroup = styled(ToggleButtonGroup)(({ theme }) => ({
	'& .MuiToggleButtonGroup-grouped': {
		margin: theme.spacing(0.5),
		border: 0,
		color: '#A5C9CA',
		'&.Mui-disabled': {
			border: 0
		},
		'&:not(:first-of-type)': {
			borderRadius: theme.shape.borderRadius
		},
		'&:first-of-type': {
			borderRadius: theme.shape.borderRadius
		}
	}
}));

/**
 * Component that renderes much of the components in the main dashboard page.
 */
const PersonalHoldings = () => {
	const [selectedCoin, setSelectedCoin] = useRecoilState(selectedCoinState);
	const [owned] = useRecoilState(ownedCoins);
	const [alignment, setAlignment] = useState<string>('avalible');
	const [avalibleCoins] = useRecoilState(allAvailableCoins);
	const [showTotal, setShowTotal] = useRecoilState(showTotalState);
	const [triggerCoinUpdate, setTriggerCoinUpdate] = useRecoilState(triggerCoinUpdateState);

	const deselectCoin = () => {
		setSelectedCoin(undefined);
	};

	/**
	 * Sets the alignment of the top-left toggle switch.
	 */
	// eslint-disable-next-line no-undef
	const handleChange = (event: React.MouseEvent<HTMLElement>, newAlignment: string) => {
		if (newAlignment != null) {
			setAlignment(newAlignment);
		}
	};

	const selectCoin = (coin: Coin) => {
		setSelectedCoin(coin);
		setShowTotal(false);
	};

	return (
		<div
			className="container"
			style={{
				width: '100%',
				height: '100%',
				backgroundColor: '#2C3333',
				display: 'grid',
				gridTemplateColumns: '[first] auto [line2] 25% [line3] 70% [line4] auto [end]'
			}}>
			<div className="coins_lists_container">
				<Button
					variant="contained"
					sx={{
						width: '70%',
						margin: 'auto',
						color: '#A5C9CA',
						backgroundColor: '#395B64',
						':hover': {
							backgroundColor: '#06283D'
						}
					}}
					onClick={() => setShowTotal(true)}>
					Total portfolio value
				</Button>
				<div className="coins_list_select_buttons">
					<StyledToggleButtonGroup
						id="list_view_select_buttons"
						value={alignment}
						exclusive
						sx={{
							backgroundColor: '#395B64'
						}}
						onChange={handleChange}
						aria-label="Platform">
						<ToggleButton value="avalible">Avalible coins</ToggleButton>
						<ToggleButton value="owned">Owned coins</ToggleButton>
					</StyledToggleButtonGroup>
				</div>
				<div className="coin_list">
					<CoinList
						alignment={alignment}
						selectCoin={selectCoin}
						allCoins={avalibleCoins}
						owned={owned}
					/>
				</div>
			</div>
			<CoinDetails
				triggerCoinUpdate={triggerCoinUpdate}
				setTriggerCoinUpdate={setTriggerCoinUpdate}
				coin={selectedCoin}
				deselectCoin={deselectCoin}
				showTotal={showTotal}
			/>
		</div>
	);
};

export default PersonalHoldings;
