import { atom } from 'recoil';
import { User, Coin, OwnedCoin, ChartData } from '../../types/User';

export const loggedInState = atom({
	key: 'loggedIn',
	default: false
});

export const signedInUser = atom({
	key: 'signedInUser',
	default: null as User | null
});

export const allAvailableCoins = atom({
	key: 'allCoins',
	default: [] as Coin[] | []
});

export const ownedCoins = atom({
	key: 'ownedCoins',
	default: [] as OwnedCoin[] | []
});

export const loadingState = atom({
	key: 'loadingState',
	default: false
});

export const chartData = atom({
	key: 'chartData',
	default: [] as ChartData[] | []
});

export const interval = atom({
	key: 'interval',
	default: 'm1'
});

export const selectedCoinState = atom({
	key: 'selectedCoin',
	default: undefined as Coin | undefined
});

export const showTotalState = atom({
	key: 'showTotal',
	default: true
});

export const triggerCoinUpdateState = atom({
	key: 'triggerCoinUpdateState',
	default: false
});

export const statusMessageState = atom({
	key: 'statusMessage',
	default: ''
});
