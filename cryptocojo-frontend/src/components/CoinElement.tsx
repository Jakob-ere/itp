import React from 'react';
import '../css/personal_holdings.css';
import { Coin } from '../types/User';

type CoinProps = {
	coin: Coin;
	// eslint-disable-next-line no-unused-vars
	selectCoin: (coin: Coin) => void;
	priceMultiplier: Number | 1;
};

/**
 * "Dummy" component that renderes the coinelement in the list of avalible/owned coins.
 */

const CoinElement: React.FC<CoinProps> = ({ coin, selectCoin, priceMultiplier }) => {
	const handleClick = () => {
		selectCoin(coin);
	};

	return (
		<div className="list_coin_element" onClick={handleClick}>
			<div
				className="circle"
				style={{
					background: '#F10086'
				}}>
				{coin.name[0]}
			</div>
			<h2>{coin.name}</h2>
			<p>
				{new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'USD' }).format(
					coin?.priceUsd == undefined ? 0 : Number(coin?.priceUsd) * Number(priceMultiplier)
				)}
			</p>
		</div>
	);
};

export default CoinElement;
