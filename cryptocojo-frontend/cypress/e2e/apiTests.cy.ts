describe('Stress testing of api', () => {
	const username = 'olanormann1';
	const email = 'ola.normann@mail.no';
	const password = '12345678';
	const userId = '85f747bd-5963-47e0-b146-d82e4b177a25';

	before(() => {
		cy.request(
			'POST',
			'http://localhost:8080/api/v1/user/create_user?username=' +
				username +
				'&password=' +
				password +
				'&email=' +
				email +
				'&userId=' +
				userId
		);
	});

	it('is possible to buy all coins', () => {
		cy.fixture('testData.json').then((data) => {
			data.data.forEach((element) => {
				cy.request(
					'POST',
					'http://localhost:8080/api/v1/user/add_currency?userId=' +
						userId +
						'&coin=' +
						element.id +
						'&amount=' +
						String(100.123012)
				);
			});
		});
	});

	it('is not possible to sell more of a coin than you own', () => {
		cy.fixture('testData.json').then((data) => {
			data.data.forEach((element) => {
				cy.request({
					method: 'POST',
					failOnStatusCode: false,
					url:
						'http://localhost:8080/api/v1/user/remove_currency?userId=' +
						userId +
						'&coin=' +
						element.id +
						'&amount=' +
						String(1000)
				}).then((res) => expect(res.status).to.equal(400));
			});
		});
	});

	it('is possible to sell all coins', () => {
		cy.fixture('testData.json').then((data) => {
			data.data.forEach((element) => {
				cy.request(
					'POST',
					'http://localhost:8080/api/v1/user/remove_currency?userId=' +
						userId +
						'&coin=' +
						element.id +
						'&amount=' +
						String(100.123012)
				);
			});
		});
	});

	it('is not possible to buy negative amount of all coins', () => {
		cy.fixture('testData.json').then((data) => {
			data.data.forEach((element) => {
				cy.request({
					method: 'POST',
					failOnStatusCode: false,
					url:
						'http://localhost:8080/api/v1/user/add_currency?userId=' +
						userId +
						'&coin=' +
						element.id +
						'&amount=' +
						String(-100.123012)
				}).then((res) => expect(res.status).to.equal(400));
			});
		});
	});

	after(() => {
		cy.request(
			'DELETE',
			'http://localhost:8080/api/v1/user/remove_user_with_id?userId=' + userId + '&authToken=123'
		);
	});
});
