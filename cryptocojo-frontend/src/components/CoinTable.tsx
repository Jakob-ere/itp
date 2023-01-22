import {
	Paper,
	Table,
	TableBody,
	TableCell,
	TableContainer,
	TableHead,
	TableRow
} from '@mui/material';
import { OwnedCoin } from '../types/User';

type CoinTableProps = {
	coinList: OwnedCoin[];
	height?: number;
};

/**
 * "Dummy" component that renders a table of coinData. Made to be reused.
 */

const CoinTable = ({ coinList, height }: CoinTableProps) => {
	return (
		<TableContainer
			component={Paper}
			sx={{ maxHeight: height == null ? 180 : height, width: '50%' }}>
			<Table sx={{ backgroundColor: '#2C3333', width: '100%' }} aria-label="simple table">
				<TableHead>
					<TableRow>
						<TableCell sx={{ color: 'white' }}>Coin</TableCell>
						<TableCell sx={{ color: 'white' }}>Amount</TableCell>
						<TableCell sx={{ color: 'white' }}>Value</TableCell>
					</TableRow>
				</TableHead>
				<TableBody>
					{coinList.map((row) => (
						<TableRow key={row.name} sx={{ '&:last-child td, &:last-child th': { border: 0 } }}>
							<TableCell sx={{ color: 'white', width: '100%' }} component="th" scope="row">
								{row.name}
							</TableCell>
							<TableCell sx={{ color: 'white' }}>{row.amount}</TableCell>
							<TableCell sx={{ color: 'white' }}>
								{new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'USD' }).format(
									row.value
								)}
							</TableCell>
						</TableRow>
					))}
				</TableBody>
			</Table>
		</TableContainer>
	);
};

export default CoinTable;
