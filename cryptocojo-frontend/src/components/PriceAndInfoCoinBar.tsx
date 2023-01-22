import { useRecoilState } from 'recoil';
import { signedInUser } from '../recoil/atoms/atoms';
import { Coin } from '../types/User';

type PriceAndInfoBarCoinProps = {
	coin: Coin | undefined;
};

/**
 * The bar of data that is displayed when a coin is selected.
 */
const PriceAndInfoCoinBar = ({ coin }: PriceAndInfoBarCoinProps) => {
	const [user] = useRecoilState(signedInUser);
	return (
		<>
			<div className="price_bar_item">
				<p className="price_bar_item_describer">Price</p>
				<p className="price_bar_item_value">
					{new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'USD' }).format(
						Number(coin?.priceUsd == undefined ? 0 : coin?.priceUsd)
					)}
				</p>
			</div>
			<div className="price_bar_item">
				<p className="price_bar_item_describer">Today</p>
				<p
					className="price_bar_item_value"
					style={{
						color: Number(coin?.changePercent24Hr) < 0 ? '#FF2442' : '#54B435'
					}}>
					{Number(coin?.changePercent24Hr).toFixed(2) + '%'}
				</p>
			</div>
			<div className="price_bar_item">
				<p className="price_bar_item_describer">Symbol</p>
				<p className="price_bar_item_value">{coin?.symbol}</p>
			</div>
			<div
				className="price_bar_item"
				style={{
					visibility: user?.pro ? 'visible' : 'hidden'
				}}>
				<p className="price_bar_item_describer">Supply</p>
				<p className="price_bar_item_value">
					{new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'USD' }).format(
						Number(coin?.supply == undefined ? 0 : coin?.supply)
					)}
				</p>
			</div>
			<div
				className="price_bar_item"
				style={{
					visibility: user?.pro ? 'visible' : 'hidden'
				}}>
				<p className="price_bar_item_describer">Market-cap (USD)</p>
				<p className="price_bar_item_value">
					{new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'USD' }).format(
						Number(coin?.marketCapUsd == undefined ? 0 : coin?.marketCapUsd)
					)}
				</p>
			</div>
		</>
	);
};

export default PriceAndInfoCoinBar;
