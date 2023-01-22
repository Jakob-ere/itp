import { LineChart, Line, XAxis, YAxis, Tooltip, ResponsiveContainer } from 'recharts';
import { useRecoilState } from 'recoil';
import { chartData } from '../recoil/atoms/atoms';

const CustomTooltip = ({ active, payload }: any) => {
	if (active && payload && payload.length) {
		return (
			<div className="custom-tooltip">
				<p className="price_on_date">{`${new Intl.NumberFormat('de-DE', {
					style: 'currency',
					currency: 'USD'
				}).format(Number(payload[0].value))}`}</p>
				<p>{payload[0].payload.date}</p>
			</div>
		);
	}

	return null;
};

/**
 * A "dummy" chart component that displays the data in the global state of chartData
 */

const Chart = () => {
	const [data] = useRecoilState(chartData);

	return (
		<ResponsiveContainer width="90%" height="50%">
			<LineChart
				id="chart"
				width={100}
				height={500}
				data={data}
				margin={{
					top: 10,
					right: 10,
					left: 10,
					bottom: 10
				}}>
				<YAxis
					dataKey="priceUsd"
					domain={['auto', 'auto']}
					padding={{ top: 20, bottom: 20 }}
					stroke="#F1F1F1"
				/>
				<XAxis dataKey="x_date" minTickGap={100} stroke="#F1F1F1" />
				<Tooltip
					wrapperStyle={{ border: '1px solid rgba(0, 0, 0, 0.05)', backgroundColor: '#395B64' }}
					content={<CustomTooltip />}
				/>
				<Line
					type="monotone"
					dataKey="priceUsd"
					stroke="blue"
					activeDot={{ r: 5 }}
					legendType="square"
					dot={false}
				/>
			</LineChart>
		</ResponsiveContainer>
	);
};

export default Chart;
