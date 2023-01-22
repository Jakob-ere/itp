import { OwnedCoin } from '../types/User';
import React from 'react';
import TransactionContainer from './TransactionContainer';
import { useRecoilState } from 'recoil';
import { ownedCoins, selectedCoinState } from '../recoil/atoms/atoms';
import CoinTable from './CoinTable';

type BuyAndPortFolioDetailsContainerProps = {
	handleSell: () => void;
	handleBuy: () => void;
	// eslint-disable-next-line no-unused-vars
	setBuyAmount: (value: number) => void;
	// eslint-disable-next-line no-unused-vars, no-undef
	handleAmountChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
	buyAmount: number;
	priceUsd: string | number | undefined;
	showTotal: boolean;
	amountError: boolean;
};

/**
 * Component that renderes the lower part of the coindetails component.
 */

const BuyAndPortFolioDetailsContainer = ({
	handleSell,
	handleBuy,
	handleAmountChange,
	buyAmount,
	amountError,
	priceUsd,
	showTotal
}: BuyAndPortFolioDetailsContainerProps) => {
	const [owned] = useRecoilState(ownedCoins);
	const [selectedCoin] = useRecoilState(selectedCoinState);

	/**
	 * Method for deciding what coins to display in the owned-coin-information section.
	 *
	 * @returns the list of coins that is to be displayed in the table
	 */
	const getCoinList = (): OwnedCoin[] => {
		if (showTotal) {
			return owned;
		} else {
			let res = owned?.find((ownedCoin) => ownedCoin.name === selectedCoin?.id);
			if (res != undefined) {
				return [res];
			} else {
				return [
					{
						name: selectedCoin?.name,
						amount: 0,
						value: 0
					}
				];
			}
		}
	};

	return (
		<div className="personal_coin_info_container">
			<CoinTable coinList={getCoinList()} />
			{showTotal ? (
				<></>
			) : (
				<TransactionContainer
					buyAmount={buyAmount}
					amountError={amountError}
					handleBuy={handleBuy}
					handleSell={handleSell}
					handleAmountChange={handleAmountChange}
					priceUsd={priceUsd}
				/>
			)}
		</div>
	);
};

export default BuyAndPortFolioDetailsContainer;
