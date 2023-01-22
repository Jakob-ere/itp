export type Coin = {
	changePercent24Hr: number | string;
	id: string;
	marketCapUsd: number | string;
	maxSupply: number | string | null;
	name: string;
	priceUsd: number | string;
	rank: string;
	supply: number | string;
	symbol: string;
	volumeUsd24Hr: number | string;
	vwap24Hr: number | string;
	explorer: number | string;
};

export type User = {
	name: string;
	password: string;
	email: string;
	userId: string;
	ownedCoins: Coin[];
	pro: boolean;
};

export type OwnedCoin = {
	name: string | undefined;
	amount: number;
	value: number;
};

export type ChartData = {
	priceUsd: number;
	x_date: string;
	date: string;
};

export type PortFolioData = {
	portFolioValue: number | undefined;
	changeInInterval: number | undefined;
};
