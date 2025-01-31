describe('Post details', () => {
  beforeEach(() => {
    cy.initIntercepts();
    cy.login('alice@mdd.com', 'password123');
    cy.visit('/post');
  });

  it('should display details when a post is clicked', () => {
    cy.get('mat-card').first().click();
    cy.url().should('include', '/post/detail/1');

    cy.get('h1').should('contain.text', 'Singleton ou pas ?');
    cy.contains('01/01/2025');
    cy.contains('DevAlice');
    cy.contains('JavaScript');
    cy.get('p').should(
      'contain.text',
      'Quels sont les avantages et inconvénients des patterns Singleton ?'
    );
  });

  it('should go back on post feed if back button clicked', () => {
    cy.visit('/post/detail/1');
    cy.contains('button', 'arrow_back').click();
    cy.url().should('include', '/post');
  });
});
