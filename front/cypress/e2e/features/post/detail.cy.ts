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
    cy.visit('/post/detail/1');
  });

  it('should display details when a post is clicked', () => {
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
    cy.get('div.user-comment').should('exist');
    cy.get('div.user-comment').should('have.length', 3);
    cy.get('textarea[formControlName=content]').should('exist');
    cy.get('button[type=submit]').should('exist');
  });

  it('should send a new comment', () => {
    cy.get('textarea[formControlName=content]').type('a new comment');
    cy.get('button[type=submit]').click();
    cy.get('snack-bar-container').and(
      'contain.text',
      'Votre commentaire a bien été ajouté.'
    );
    cy.get('div.user-comment').should('have.length', 3);
  });

  it('should display error message if send a new comment failed', () => {
    cy.intercept('POST', '/api/comment', {
      statusCode: 500,
    });
    cy.get('textarea[formControlName=content]').type('a new comment');
    cy.get('button[type=submit]').click();
    cy.get('snack-bar-container').and(
      'contain.text',
      'Une erreur est survenu.'
    );
  });

  it('should go back on post feed if back button clicked', () => {
    cy.contains('button', 'arrow_back').click();
    cy.url().should('include', '/post');
  });
});
