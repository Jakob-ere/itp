import { Button, TextField } from '@mui/material';

type TransactionContainerProps = {
	buyAmount: number;
	priceUsd: string | number | undefined;
	// eslint-disable-next-line no-unused-vars, no-undef
	handleAmountChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
	handleBuy: () => void;
	handleSell: () => void;
	amountError: boolean;
};

/**
 * Dummy component that renderes the buy/sell functionality. Its state is kept by the CoinDetails component.
 */
const TransactionContainer = ({
	buyAmount,
	priceUsd,
	amountError,
	handleAmountChange,
	handleBuy,
	handleSell
}: TransactionContainerProps) => {
	return (
		<div className="buy_sell_container">
			<TextField
				sx={{
					backgroundColor: '#2C3333',
					input: {
						color: 'white'
					},
					'& .MuiFormControl-root': {
						border: '2px solid white'
					},
					'& .MuiFormLabel-root': {
						color: 'white'
					}
				}}
				id="amount"
				error={amountError}
				helperText={amountError ? 'Amount needs to be positive' : ''}
				type="number"
				label={new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'USD' }).format(
					buyAmount * Number(priceUsd)
				)}
				variant="outlined"
				onChange={handleAmountChange}
			/>
			<div className="buy_sell_buttons">
				<Button
					variant="contained"
					sx={{
						backgroundColor: 'green',
						':hover': {
							backgroundColor: '#35fc03'
						}
					}}
					onClick={handleBuy}>
					Buy
				</Button>
				<Button
					variant="contained"
					sx={{
						backgroundColor: '#fc1e1e',
						':hover': {
							backgroundColor: 'red'
						}
					}}
					onClick={handleSell}>
					Sell
				</Button>
			</div>
		</div>
	);
};

export default TransactionContainer;
