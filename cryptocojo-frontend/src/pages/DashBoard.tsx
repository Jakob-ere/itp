import { Outlet, useNavigate } from 'react-router-dom';
import { useRecoilState, useSetRecoilState } from 'recoil';
import {
	chartData,
	loadingState,
	loggedInState,
	signedInUser,
	interval,
	selectedCoinState,
	showTotalState,
	ownedCoins,
	allAvailableCoins,
	triggerCoinUpdateState,
	statusMessageState
} from '../recoil/atoms/atoms';
import { useEffect } from 'react';
import {
	getAllAvalibleCoins,
	getHistoryData,
	getOwnedCoins,
	getTotalPortFolioValue
} from '../axios/CoinService';
import { ChartData, Coin, OwnedCoin } from '../types/User';

/**
 * Dashboard page is a component that only gets rendered when a user is authenticated. It provides a outlet for react-router to render components based on URL.
 */
const DashBoard = () => {
	const [loggedIn] = useRecoilState(loggedInState);
	const navigate = useNavigate();
	const setData = useSetRecoilState(chartData);
	const [timeInterval] = useRecoilState(interval);
	const [avalibleCoins, setAvalibleCoins] = useRecoilState(allAvailableCoins);
	const [showTotal] = useRecoilState(showTotalState);
	const [selectedCoin] = useRecoilState(selectedCoinState);
	const [user] = useRecoilState(signedInUser);
	const setOwned = useSetRecoilState(ownedCoins);
	const setLoading = useSetRecoilState(loadingState);
	const [triggerCoinUpdate] = useRecoilState(triggerCoinUpdateState);
	const setStatusMessage = useSetRecoilState(statusMessageState);

	/**
	 * Sub-routine for parsing of history data for the chart component.
	 */
	const getInterval = (time: number) => {
		if (timeInterval === 'm1') {
			return new Date(time).getUTCHours() + ':00';
		}
		if (timeInterval == 'm15' || timeInterval == 'm30') {
			return String(new Date(time).getDate());
		}
		if (timeInterval == 'h1' || timeInterval == 'h2') {
			let date = new Date(time);
			return date.getDate() + '/' + date.getMonth();
		}
		if (timeInterval === 'h12' || timeInterval == 'd1') {
			let date = new Date(time);
			return date.getMonth() + 1 + '/' + date.getUTCFullYear();
		}
		return String(new Date(time).getMonth());
	};

	/**
	 * Side-effect for fetching chart data for both a single coin and total portfolio value. Listens to changes to selectedCoin, timeInterval and if total portfolio is selected.
	 */
	useEffect(() => {
		setLoading(true);
		if (showTotal) {
			if (user != undefined) {
				getTotalPortFolioValue(user?.userId, timeInterval)?.then((res) => {
					if (res.status == 200) {
						let parsed_data: ChartData[] = [];
						res.data.map((stamp: any) => {
							parsed_data.push({
								priceUsd: Number(stamp.priceUsd),
								x_date: getInterval(Number(stamp.time)),
								date: new Date(Number(stamp.time)).toUTCString()
							});
						});
						setData(parsed_data as ChartData[]);
					} else {
						setStatusMessage('Something went wrong with fetching your portfolio');
					}
					setLoading(false);
				});
			}
		} else {
			getHistoryData(selectedCoin?.id, timeInterval)?.then((res) => {
				if (res.status == 200) {
					let parsed_data: ChartData[] = [];
					(res.data.data as unknown as ChartData[]).map((stamp: any) => {
						parsed_data.push({
							priceUsd: Number(stamp.priceUsd),
							x_date: getInterval(stamp.time),
							date: new Date(stamp.time).toUTCString()
						});
					});

					setData(parsed_data as ChartData[]);
				} else {
					setStatusMessage('Something went wrong with fetching history data');
				}
				setLoading(false);
			});
		}
	}, [selectedCoin, timeInterval, showTotal]);

	/**
	 * Side-effect for fetching all avalible coins. This only runs when component is mounted.
	 */
	useEffect(() => {
		setLoading(true);
		getAllAvalibleCoins().then((response: any) => {
			if (response.status == 200) {
				setAvalibleCoins(response.data.data as unknown as Coin[]);
			} else {
				setStatusMessage('Could not fetch coins');
			}
		});
	}, []);

	/**
	 * Side-effect for fetching owned coins. This is run when component is mounted and when a user buy/sells a coin.
	 */
	useEffect(() => {
		setLoading(true);
		if (user != null && avalibleCoins.length > 0) {
			getOwnedCoins(user?.userId).then((response: any) => {
				if (response.status == 200) {
					let parsed: OwnedCoin[] = [];
					response.data.forEach((data: any) => {
						let value = 0;

						avalibleCoins.forEach((l) => {
							if (l.id == data.name) {
								value = Number(l.priceUsd) * data.amount;
							}
						});

						parsed.push({
							name: data.name,
							amount: data.amount,
							value: value
						});
					});
					setOwned(parsed);
					setLoading(false);
				} else {
					setStatusMessage('Could not access you coins');
				}
			});
		}
	}, [triggerCoinUpdate, avalibleCoins]);

	useEffect(() => {
		if (!loggedIn) {
			navigate('/');
		}
	});

	return <Outlet />;
};

export default DashBoard;
