import { Button, MenuItem, Select, SelectChangeEvent } from '@mui/material';
import { useRecoilState } from 'recoil';
import { statusMessageState } from '../recoil/atoms/atoms';
import { Coin } from '../types/User';
import '../css/personal_holdings.css';
import PriceAndInfoCoinBar from './PriceAndInfoCoinBar';
import PriceAndInfoPortFolioBar from './PriceAndInfoPortFolioBar';

type CoinDetailsInfoBarContainerProps = {
	coin: Coin | undefined;
	deselectCoin: () => void;
	interval: string;
	// eslint-disable-next-line no-unused-vars
	handleIntervalChange: (event: SelectChangeEvent) => void;
	showTotal: boolean;
};
/**
 * "Dummy" component that displays coin data that is provided.
 */

// eslint-disable-next-line no-undef
const CoinDetailsInfoBarContainer: React.FC<CoinDetailsInfoBarContainerProps> = ({
	coin,
	deselectCoin,
	interval,
	handleIntervalChange,
	showTotal
}: CoinDetailsInfoBarContainerProps) => {
	const [statusMessage] = useRecoilState(statusMessageState);

	return (
		<div className="current_info_container">
			<div className="selected_top_bar">
				<h1>{showTotal ? 'Your portfolio' : coin?.name}</h1>
				<p className="statusMessage">{statusMessage}</p>
				<div
					style={{
						display: 'flex',
						flexDirection: 'row',
						gap: '20px'
					}}>
					<Button
						variant="contained"
						onClick={deselectCoin}
						size="small"
						sx={{
							backgroundColor: '#395B64',
							color: '#A5C9CA',
							'&:hover': {
								backgroundColor: '#06283D'
							}
						}}>
						Deselect coin
					</Button>
					<Select
						labelId="demo-simple-select-label"
						id="demo-simple-select"
						size="small"
						value={interval}
						label="Interval"
						sx={{
							color: '#A5C9CA',
							backgroundColor: '#395B64'
						}}
						onChange={handleIntervalChange}>
						<MenuItem value={'d1'}>1 Year</MenuItem>
						<MenuItem value={'h12'}>6 Months</MenuItem>
						<MenuItem value={'h2'}>2 Months</MenuItem>
						<MenuItem value={'h1'}>1 Month</MenuItem>
						<MenuItem value={'m30'}>2 Weeks</MenuItem>
						<MenuItem value={'m15'}>1 Week</MenuItem>
						<MenuItem value={'m1'}>1 Day</MenuItem>
					</Select>
				</div>
			</div>
			<div className="selected_info">
				{showTotal ? <PriceAndInfoPortFolioBar /> : <PriceAndInfoCoinBar coin={coin} />}
			</div>
		</div>
	);
};

export default CoinDetailsInfoBarContainer;
