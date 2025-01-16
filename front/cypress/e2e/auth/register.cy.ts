describe('Register spec', () => {
  beforeEach(() => {
    cy.intercept('POST', '/api/auth/register', {
      body: {
        message: 'User registered successfully!',
      },
    }).as('register');
  });

  it('should registered successfully', () => {
    cy.register('newUserName', 'newemail@studio.com', 'newpassword!1234');

    cy.url().should('include', '/');
  });

  it('should display error message if register failed', () => {
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 400,
    });

    cy.register('newUserName', 'newemail@studio.com', 'newpassword!1234');
    cy.contains('An error occurred');
  });
});
