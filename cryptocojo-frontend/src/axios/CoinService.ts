import axios from 'axios';

const baseUrlCoinCap: String = 'https://api.coincap.io/v2';
const baseUrlApi: String = 'https://8080-it1901groups2022-gr2204-wyl1msh3slz.ws.gitpod.stud.ntnu.no/api/v1';

/**
 * Returns a promise containing history data about a spesific coin in a spesific interval.
 *
 * @param coinName id of coin
 * @param interval interval accepted (m1, m5, m15, m30, h1, h2, h6, h12, d1)
 * @returns a promise containing history data
 */
export const getHistoryData = (coinName: String | undefined, interval: String) => {
	if ((coinName && interval) != null) {
		let data = axios
			.get(baseUrlCoinCap + '/assets/' + coinName?.toLowerCase() + '/history?interval=' + interval)
			.then((response: any) => {
				return response;
			})
			.catch((e) => {
				return e;
			});
		return data;
	}
};

/**
 * Returns a promise containing data points on total portfolio value. Used for displaying a graph of total portfolio value.
 *
 * @param userId id of user to get data about
 * @param interval interval accepted (m1, m5, m15, m30, h1, h2, h6, h12, d1)
 * @returns a promise containing history data
 */
export const getTotalPortFolioValue = (userId: string, interval: string) => {
	if ((userId && interval) != null) {
		let data = axios
			.get(baseUrlApi + '/user/get_account_interval?timeStamp=' + interval + '&userId=' + userId)
			.then((res) => {
				return res;
			})
			.catch((e) => {
				return e;
			});
		return data;
	}
};

/**
 * A method for fetching all avalible coins from coincap along with prices and other information
 *
 * @returns A promise containing all avalible coins
 */
export const getAllAvalibleCoins = () => {
	let data = axios
		.get(baseUrlCoinCap + '/assets')
		.then((response: any) => {
			return response;
		})
		.catch((e) => {
			return e;
		});
	return data;
};

/**
 * Fetches owned coins from backend for a spesific user
 *
 * @param userId the ID of the user to fetch coins from
 * @returns Promise containing coins owned by user
 */
export const getOwnedCoins = (userId: String | undefined) => {
	let data = axios
		.get(baseUrlApi + '/coin/get_owned_coins?userId=' + userId)
		.then((response: any) => {
			return response;
		})
		.catch((e) => {
			return e;
		});
	return data;
};

/**
 * Method for buying a coin
 *
 * @param coin the ID of the coin that is to be bought
 * @param str_amount amount of this coin to buy
 * @param userId ID of the user that is performing the transaction
 * @returns a Promise containing status about the transaction. (200 = Transaction went through)
 */
export const buyCoin = (coin: string, str_amount: string, userId: string) => {
	let data = axios
		.post(
			baseUrlApi +
				'/user/add_currency?userId=' +
				userId +
				'&coin=' +
				coin.toLowerCase() +
				'&amount=' +
				str_amount
		)
		.then((response: any) => {
			return response;
		})
		.catch((e) => {
			return e;
		});
	return data;
};

/**
 * Method for selling a coin
 *
 * @param coin ID of the coin that is to be sold
 * @param str_amount amount that is to be sold
 * @param userId ID of the user that is performing the transaction
 * @returns a Promise containing status about the transaction (200 = Transaction went through)
 */
export const sellCoin = (coin: string, str_amount: string, userId: string) => {
	let data = axios
		.post(
			baseUrlApi +
				'/user/remove_currency?userId=' +
				userId +
				'&coin=' +
				coin.toLowerCase() +
				'&amount=' +
				str_amount
		)
		.then((response: any) => {
			return response;
		})
		.catch((e) => {
			return e;
		});
	return data;
};
