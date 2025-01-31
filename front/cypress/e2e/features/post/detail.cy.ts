describe('Post details', () => {
  beforeEach(() => {
    cy.initIntercepts();
    cy.intercept('POST', '/api/comment', {
      id: 1,
      content: 'Post content',
      userId: 1,
      postId: 1,
      createdAt: new Date().toISOString(),
    }).as('createPost');
    cy.login('alice@mdd.com', 'password123');
    cy.visit('/post');
  });

  it('should display details when a post is clicked', () => {
    cy.get('mat-card').first().click();
    cy.url().should('include', '/post/detail/1');

    // Post details
    cy.get('h1').should('contain.text', 'Singleton ou pas ?');
    cy.contains('01/01/2025');
    cy.contains('DevAlice');
    cy.contains('JavaScript');
    cy.get('p').should(
      'contain.text',
      'Quels sont les avantages et inconvénients des patterns Singleton ?'
    );

    // Comments
    cy.get('h3').should('contain.text', 'Commentaires');
    cy.get('textarea[formControlName=content]').should('exist');
    cy.get('button[type=submit]').should('exist');
  });

  it('should send a new comment', () => {
    cy.get('mat-card').first().click();
    cy.get('textarea[formControlName=content]').type('a new comment');
    cy.get('button[type=submit]').click();
    cy.get('snack-bar-container').and(
      'contain.text',
      'Votre commentaire a bien été ajouté.'
    );
  });

  it('should go back on post feed if back button clicked', () => {
    cy.visit('/post/detail/1');
    cy.contains('button', 'arrow_back').click();
    cy.url().should('include', '/post');
  });
});
