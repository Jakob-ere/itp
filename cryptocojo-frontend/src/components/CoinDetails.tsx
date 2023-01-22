import { SelectChangeEvent } from '@mui/material';
import React, { useState } from 'react';
import { Coin, OwnedCoin } from '../types/User';
import Chart from './Chart';
import { useRecoilState, useSetRecoilState } from 'recoil';
import {
	interval,
	loadingState,
	ownedCoins,
	signedInUser,
	statusMessageState
} from '../recoil/atoms/atoms';
import { buyCoin, sellCoin } from '../axios/CoinService';
import { getOwnedCoins } from '../axios/CoinService';
import CoinDetailsInfoBarContainer from './CoinDetailsInfoBarContainer';
import BuyAndPortFolioDetailsContainer from './BuyAndPortFolioDetailsContainer';

type CoinDetailsProps = {
	coin: Coin | undefined;
	deselectCoin: () => void;
	// eslint-disable-next-line no-unused-vars
	setTriggerCoinUpdate: (value: boolean) => void;
	triggerCoinUpdate: boolean;
	showTotal: boolean;
};

/**
 * A component that is responsible for rendering details about a spesific coin.
 */

const CoinDetails: React.FC<CoinDetailsProps> = ({
	coin,
	deselectCoin,
	triggerCoinUpdate,
	setTriggerCoinUpdate,
	showTotal
}) => {
	const [buyAmount, setBuyAmount] = useState<number>(0);
	const [timeInterval, setTimeInterval] = useRecoilState(interval);
	const setOwned = useSetRecoilState(ownedCoins);
	const [user] = useRecoilState(signedInUser);
	const setLoading = useSetRecoilState(loadingState);
	const [amountError, setAmountError] = useState<boolean>(false);
	const setStatusMessage = useSetRecoilState(statusMessageState);

	/**
	 * Sends a request to backend to buy a coin. Refetches owned coins to update state.
	 */
	const handleBuy = () => {
		setLoading(true);
		const userId = user?.userId;
		if (userId != null && buyAmount > 0 && coin != undefined) {
			buyCoin(coin.name, String(buyAmount), userId).then((res) => {
				if (res.status == 200) {
					getOwnedCoins(user?.userId).then((res) => {
						if (res.status == 200) {
							setOwned(res.data as unknown as OwnedCoin[]);
							setTriggerCoinUpdate(!triggerCoinUpdate);
						}
					});
				} else {
					setStatusMessage('Could not buy coin, try again later');
				}
				setLoading(false);
			});
		} else {
			setLoading(false);
		}
	};

	/**
	 * Sends a request to backend to sell a coin. Refetches owned coins to update state.
	 */
	const handleSell = () => {
		setLoading(true);
		const userId = user?.userId;
		if (userId != null && buyAmount != undefined && coin != undefined) {
			sellCoin(coin.name, String(buyAmount), userId).then((res) => {
				if (res.status == 200) {
					getOwnedCoins(user?.userId).then((res) => {
						if (res.status == 200) {
							setOwned(res.data as unknown as OwnedCoin[]);
							setTriggerCoinUpdate(!triggerCoinUpdate);
						}
					});
				} else {
					setStatusMessage('Could not sell coin, try again later');
				}
				setLoading(false);
			});
		}
	};

	/**
	 * Updates state to match what user inputs in the but amount field.
	 */
	// eslint-disable-next-line no-undef
	const handleAmountChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		if (Number(event.target.value) < 0) {
			setBuyAmount(0);
			setAmountError(true);
		} else {
			setBuyAmount(Number(event.target.value));
			setAmountError(false);
		}
	};

	const handleIntervalChange = (event: SelectChangeEvent) => {
		setTimeInterval(event.target.value as string);
	};

	return (
		<div className="detailed_information_container">
			<CoinDetailsInfoBarContainer
				showTotal={showTotal}
				coin={coin}
				deselectCoin={deselectCoin}
				interval={timeInterval}
				handleIntervalChange={handleIntervalChange}
			/>
			<Chart />
			<BuyAndPortFolioDetailsContainer
				handleBuy={handleBuy}
				handleSell={handleSell}
				showTotal={showTotal}
				amountError={amountError}
				setBuyAmount={setBuyAmount}
				handleAmountChange={handleAmountChange}
				buyAmount={buyAmount}
				priceUsd={coin?.priceUsd}
			/>
		</div>
	);
};

export default CoinDetails;
