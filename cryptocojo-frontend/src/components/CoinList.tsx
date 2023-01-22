import React from 'react';
import { Coin, OwnedCoin } from '../types/User';
import CoinElement from './CoinElement';

type CoinListProps = {
	alignment: string;
	allCoins: Coin[] | undefined;
	// eslint-disable-next-line no-unused-vars
	selectCoin: (coin: Coin) => void;
	owned: OwnedCoin[] | undefined;
};

/**
 * Component that decides what to render in the coin list.
 */

const CoinList = ({ alignment, selectCoin, allCoins, owned }: CoinListProps) => {
	const renderCoinList = () => {
		if (alignment === 'avalible') {
			return allCoins?.map((element) => {
				return (
					<CoinElement
						key={element.id}
						coin={element}
						selectCoin={selectCoin}
						priceMultiplier={1}
					/>
				);
			});
		} else {
			if (owned?.length == 0) {
				return (
					<div
						style={{
							margin: 'auto',
							textAlign: 'center'
						}}>
						<img src="/no_assets.gif" alt="loading..." width={200}></img>
						<h1>No assets to show..</h1>
						<p>Add some assets for stonks</p>
					</div>
				);
			} else {
				return allCoins?.map((element) => {
					if (owned?.some((e) => e.name === element.id)) {
						return (
							<CoinElement
								key={element.id}
								coin={element}
								selectCoin={selectCoin}
								priceMultiplier={1}
							/>
						);
					}
				});
			}
		}
	};

	return <>{renderCoinList()}</>;
};

export default CoinList;
