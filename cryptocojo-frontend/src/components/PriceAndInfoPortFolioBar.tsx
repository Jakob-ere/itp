import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import { chartData } from '../recoil/atoms/atoms';
import { PortFolioData } from '../types/User';

/**
 * The bar of data that is displayed when total portfolio is selected.
 */

const PriceAndInfoPortFolioBar = () => {
	const [portFolioData, setPortFolioData] = useState<PortFolioData>();
	const [data] = useRecoilState(chartData);

	/**
	 * Side effect that listens to changes to the global stat data. It parses the data to display the total value and change in percent.
	 */
	useEffect(() => {
		if (data.length > 0) {
			let newPortFolioData: PortFolioData = {
				portFolioValue: 0,
				changeInInterval: 0
			};
			let changePercent =
				((Number(data.at(data.length - 1)?.priceUsd) - Number(data.at(0)?.priceUsd)) /
					Number(data.at(0)?.priceUsd)) *
				100;
			if (changePercent == Infinity) {
				changePercent = 100;
			}
			newPortFolioData.portFolioValue = data.at(data.length - 1)?.priceUsd;
			newPortFolioData.changeInInterval = changePercent;
			setPortFolioData(newPortFolioData);
		}
	}, [data]);

	return (
		<>
			<div className="price_bar_item">
				<p className="price_bar_item_describer">Total value</p>
				<p className="price_bar_item_value">
					{new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'USD' }).format(
						isNaN(Number(portFolioData?.portFolioValue)) ? 0 : Number(portFolioData?.portFolioValue)
					)}
				</p>
			</div>
			<div className="price_bar_item">
				<p className="price_bar_item_describer">Today</p>
				<p
					className="price_bar_item_value"
					style={{
						color: Number(portFolioData?.changeInInterval) < 0 ? '#FF2442' : '#54B435'
					}}>
					{(isNaN(Number(portFolioData?.changeInInterval))
						? 0
						: Number(portFolioData?.changeInInterval).toFixed(2)) + '%'}
				</p>
			</div>
		</>
	);
};

export default PriceAndInfoPortFolioBar;
