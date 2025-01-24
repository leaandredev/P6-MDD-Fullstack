describe('User Details Page', () => {
  beforeEach(() => {
    cy.initIntercepts();
    cy.intercept('PUT', '/api/user/1', {
      id: 1,
      userName: 'DevAliceUpdated',
      email: 'aliceUpdated@mdd.com',
      createdAt: '2024-01-05T14:00:00Z',
      updatedAt: '2024-01-02T14:00:00Z',
    }).as('updateUser');
  });

  describe('User found', () => {
    beforeEach(() => {
      cy.login('alice@mdd.com', 'password123');
      cy.visit('/feed/user-details');
    });

    it('should display the user details page if user found', () => {
      cy.url().should('include', '/feed/user-details');
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

    it('should allow updates and submit the form', () => {
      cy.get('input[formControlName="userName"]')
        .clear()
        .type('DevAliceUpdated');
      cy.get('input[formControlName="email"]')
        .clear()
        .type('aliceUpdated@mdd.com');
      cy.get('button[type="submit"]').click();
      cy.get('snack-bar-container')
        .should('exist')
        .and(
          'contain.text',
          'Informations sauvegardées, veuillez vous reconnecter.'
        );
      cy.url().should('include', '/');
    });

    it('should logout properly', () => {
      cy.contains('a', 'Se déconnecter').click();
      cy.url().should('include', '/');
    });
  });

  describe('User not found', () => {
    beforeEach(() => {
      cy.intercept('GET', '/api/user/*', {
        statusCode: 404,
      });
      cy.login('alice@mdd.com', 'password123');
      cy.visit('/feed/user-details');
    });

    it('should not display user information', () => {
      cy.get('input[formControlName="userName"]').should('have.value', '');
      cy.get('input[formControlName="email"]').should('have.value', '');
    });
  });
});
