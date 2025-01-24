describe('Topics List Page', () => {
  beforeEach(() => {
    cy.initIntercepts();
    cy.login('alice@mdd.com', 'password123');
    cy.visit('/feed/topics');
  });

  it('should display the topics list page', () => {
    cy.url().should('include', '/feed/topics');
    cy.get('mat-card').should('have.length', 3);
    cy.get('mat-card-title').first().should('contain.text', 'JavaScript');
    cy.get('mat-card-content')
      .first()
      .should('contain.text', 'Un guide complet pour débuter avec JavaScript.');
    cy.get('mat-card-actions button').first().should('be.disabled');
    cy.get('mat-card-title').eq(1).should('contain.text', 'Python');
    cy.get('mat-card-content')
      .eq(1)
      .should(
        'contain.text',
        'Apprendre les bases de la programmation en Python.'
      );
    cy.get('mat-card-actions button').eq(1).should('be.disabled');
    cy.get('mat-card-title').eq(2).should('contain.text', 'React');
    cy.get('mat-card-content')
      .eq(2)
      .should(
        'contain.text',
        'Construire des applications web modernes avec React.'
      );
    cy.get('mat-card-actions button').eq(2).should('not.be.disabled');
  });
});
