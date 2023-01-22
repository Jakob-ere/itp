/// <reference types="cypress" />
/// <reference types="cypress-xpath" />

describe('Login and buy / sell coins', () => {
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

	it('is possible to log in', () => {
		// Fill out username and password
		cy.visit('/');
		cy.xpath('/html/body/div/div/div[2]/div/div/div[1]/div/input').click().type(username);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[2]/div/input').click().type(password);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[3]/button[1]').click();

		// Check that user is logged in
		cy.xpath('/html/body/div/div/div[1]/div/a[2]/img').click();
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[1]/p[2]').should('have.text', username);
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[2]/p[2]').should('have.text', email);
		cy.xpath('/html/body/div/div/div[1]/div/a[1]/img').click();
	});

	it('is possible to buy and sell a coin', () => {
		cy.xpath('/html/body/div/div/div[2]/div[1]/div[2]/div[1]').click();
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[3]/div[2]/div[1]/div/input').click().type('1');
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[3]/div[2]/div[2]/button[1]').click();
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[3]/div[1]/table/tbody/tr/td[1]').should(
			'have.text',
			'1'
		);
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[3]/div[2]/div[2]/button[2]').click();
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[3]/div[1]/table/tbody/tr/td[1]').should(
			'have.text',
			'0'
		);
	});

	it('is possible to have multiple coins in inventory', () => {
		let amountToBuy = 1.01231;

		// Buy first coin
		cy.xpath('/html/body/div/div/div[2]/div[1]/div[2]/div[1]').click();
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[3]/div[2]/div[1]/div/input').click().clear();
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[3]/div[2]/div[1]/div/input')
			.click()
			.type(String(amountToBuy));
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[3]/div[2]/div[2]/button[1]').click();
		// eslint-disable-next-line cypress/no-unnecessary-waiting
		cy.wait(100);
		// Buy second coin
		cy.xpath('/html/body/div/div/div[2]/div[1]/div[2]/div[2]').click();
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[3]/div[2]/div[2]/button[1]').click();
		// eslint-disable-next-line cypress/no-unnecessary-waiting
		cy.wait(100);
		// Check that they both are in inventory with correct amounts
		cy.xpath('/html/body/div/div/div[1]/div/a[2]/img').click();
		// eslint-disable-next-line cypress/no-unnecessary-waiting
		cy.wait(100);
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[3]/div/table/tbody')
			.find('tr > th')
			.each(($el, index) => {
				expect($el.text()).to.not.be.empty;
			});
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[3]/div/table/tbody')
			.find('tr > td')
			.each(($el, index) => {
				if (index % 2 == 0) {
					expect($el.text()).to.equal(String(amountToBuy));
				}
			});
	});

	it('is possible to toggle pro mode', () => {
		cy.visit('/');
		cy.xpath('/html/body/div/div/div[2]/div/div/div[1]/div/input').click().type(username);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[2]/div/input').click().type(password);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[3]/button[1]').click();
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[1]/div[2]/div[4]/p[1]').should('not.exist');
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[1]/div[2]/div[5]/p[1]').should('not.exist');
		cy.xpath('/html/body/div/div/div[1]/div/a[2]/img').click();
		cy.xpath('/html/body/div/div/div[2]/div[1]/div/span/span[1]/input').click();
		cy.xpath('/html/body/div/div/div[1]/div/a[1]/img').click();
		cy.xpath('/html/body/div/div/div[2]/div[1]/div[2]/div[1]').click();
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[1]/div[2]/div[4]/p[1]').should(
			'have.text',
			'Supply'
		);
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[1]/div[2]/div[5]/p[1]').should(
			'have.text',
			'Market-cap (USD)'
		);
	});

	it('is possible to sign-out', () => {
		// Sign in
		cy.visit('/');
		cy.xpath('/html/body/div/div/div[2]/div/div/div[1]/div/input').click().type(username);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[2]/div/input').click().type(password);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[3]/button[1]').click();
		//Confirm that one is signed in
		cy.xpath('/html/body/div/div/div[1]/div/a[2]/img').click();
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[1]/p[2]').should('have.text', username);
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[2]/p[2]').should('have.text', email);
		cy.xpath('/html/body/div/div/div[1]/div/a[1]/img').click();

		// Sign out
		cy.xpath('/html/body/div/div/div[1]/div/a[3]/button').click();

		//Confirm that user is signed out
		cy.xpath('/html/body/div/div/div[2]/div/h1').should('have.text', 'Login to Cryptocojo');
	});

	it('should be set a cookie with userId when a user is logged in', () => {
		cy.visit('/');
		cy.xpath('/html/body/div/div/div[2]/div/div/div[1]/div/input').click().type(username);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[2]/div/input').click().type(password);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[3]/button[1]').click();
		cy.getCookie('cryptocojo').then((cookie) => {
			assert.equal(userId, cookie?.value);
		});
	});

	it('Should not be set a cookie when a user is signed out', () => {
		// Sign in
		cy.visit('/');
		cy.xpath('/html/body/div/div/div[2]/div/div/div[1]/div/input').click().type(username);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[2]/div/input').click().type(password);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[3]/button[1]').click();
		//Confirm that one is signed in
		cy.xpath('/html/body/div/div/div[1]/div/a[2]/img').click();
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[1]/p[2]').should('have.text', username);
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[2]/p[2]').should('have.text', email);
		cy.xpath('/html/body/div/div/div[1]/div/a[1]/img').click();

		// Sign out
		cy.xpath('/html/body/div/div/div[1]/div/a[3]/button').click();
		cy.getCookie('cryptocojo').then((cookie) => {
			assert.isNull(cookie);
		});
	});

	it('should redirect to dashboard when cookie is set', () => {
		cy.visit('/');
		cy.xpath('/html/body/div/div/div[2]/div/div/div[1]/div/input').click().type(username);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[2]/div/input').click().type(password);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[3]/button[1]').click();

		cy.visit('/');
		cy.url().should('equal', 'http://localhost:3000/dashboard');
	});

	after(() => {
		cy.request(
			'DELETE',
			'http://localhost:8080/api/v1/user/remove_user_with_id?userId=' + userId + '&authToken=123'
		);
	});
});
