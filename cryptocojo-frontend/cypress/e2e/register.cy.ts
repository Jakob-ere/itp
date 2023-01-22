/// <reference types="cypress" />
/// <reference types="cypress-xpath" />

describe('A user should be able to sign up', () => {
	const username = 'olanormann1';
	const email = 'ola.normann@mail.no';
	const password = '12345678';
	it('It is possible to register', () => {
		cy.visit('/');

		// Click signup button
		cy.xpath('/html/body/div/div/div[2]/div/div/div[3]/button[2]').click();

		// Fill out sign up form
		cy.xpath('/html/body/div/div/div[2]/div/div/div[1]/div/input').click().type(username);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[2]/div/input').click().type(email);
		cy.xpath('/html/body/div/div/div[2]/div/div/div[3]/div/input').click().type(password);
		// Click register
		cy.xpath('/html/body/div/div/div[2]/div/div/div[4]/button').click();

		// Check that user is signed up and logged in
		cy.xpath('/html/body/div/div/div[1]/div/a[2]/img').click();
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[1]/p[2]').should('have.text', username);
		cy.xpath('/html/body/div/div/div[2]/div[2]/div[2]/p[2]').should('have.text', email);
		// eslint-disable-next-line cypress/no-unnecessary-waiting
		cy.wait(1000);
		cy.getCookie('cryptocojo')
			.should('exist')
			.then((cookie) => {
				cy.request(
					'DELETE',
					'http://localhost:8080/api/v1/user/remove_user_with_id?userId=' +
						cookie?.value +
						'&authToken=123'
				);
			});
	});
});
