Cypress.Commands.add('initIntercepts', () => {
  // register response success
  cy.intercept('POST', '/api/auth/register', {
    body: {
      message: 'User registered successfully!',
    },
  }).as('register');
});

Cypress.Commands.add('register', (userName, email, password) => {
  cy.visit('/register');
  cy.get('input[formControlName=userName]').type(userName);
  cy.get('input[formControlName=email]').type(email);
  cy.get('input[formControlName=password]').type(`${password}{enter}{enter}`);
});
