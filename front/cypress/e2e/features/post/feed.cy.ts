describe('Display feed', () => {
  beforeEach(() => {
    cy.initIntercepts();
    cy.login('alice@mdd.com', 'password123');
    cy.visit('/post');
  });

  it('should display create button and user feed posts', () => {
    cy.url().should('include', '/post');
    cy.get('button').should('contain.text', 'Créer un article');
    cy.get('mat-card').should('have.length', 2);

    // First post
    cy.get('mat-card')
      .first()
      .find('mat-card-title')
      .should('contain.text', 'Singleton ou pas ?');
    cy.get('mat-card')
      .first()
      .find('[name="createDate"]')
      .should('contain', '01/01/2025');
    cy.get('mat-card')
      .first()
      .find('[name="userName"]')
      .should('contain', 'DevAlice');
    cy.get('mat-card')
      .first()
      .find('mat-card-content')
      .should(
        'contain.text',
        'Quels sont les avantages et inconvénients des patterns Singleton ?'
      );

    //Second post
    cy.get('mat-card')
      .eq(1)
      .find('mat-card-title')
      .should('contain.text', 'React vs Angular');
    cy.get('mat-card')
      .eq(1)
      .find('[name="createDate"]')
      .should('contain', '15/01/2025');
    cy.get('mat-card')
      .eq(1)
      .find('[name="userName"]')
      .should('contain', 'DevBob');
    cy.get('mat-card')
      .eq(1)
      .find('mat-card-content')
      .should(
        'contain.text',
        'Selon vous, quel framework est plus adapté pour un projet complexe ?'
      );
  });

  it('should go to create page when create button clicked', () => {
    cy.get('button').contains('Créer un article').click();
    cy.url().should('include', '/post/create');
  });
});
