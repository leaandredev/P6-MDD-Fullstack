describe('User Details Page', () => {
  beforeEach(() => {
    cy.initIntercepts();
  });

  describe('User found', () => {
    beforeEach(() => {
      cy.login('alice@mdd.com', 'password123');
      cy.visit('/user-details');
    });
    it('should display the user details page if user found', () => {
      cy.url().should('include', '/user-details');
      cy.get('h1').should('contain', 'Profil utilisateur');
      cy.get('input[formControlName="userName"]').should(
        'have.value',
        'DevAlice'
      );
      cy.get('input[formControlName="email"]').should(
        'have.value',
        'alice@mdd.com'
      );
    });
  });

  describe('User not found', () => {
    beforeEach(() => {
      cy.intercept('GET', '/api/user/*', {
        statusCode: 404,
      });
      cy.login('alice@mdd.com', 'password123');
      cy.visit('/user-details');
    });

    it('should not display user information', () => {
      cy.get('input[formControlName="userName"]').should('have.value', '');
      cy.get('input[formControlName="email"]').should('have.value', '');
    });
  });
});
