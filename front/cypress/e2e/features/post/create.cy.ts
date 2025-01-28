describe('Create post (form spec)', () => {
  beforeEach(() => {
    cy.initIntercepts();
    cy.intercept('POST', '/api/post', {
      id: 1,
      title: 'Post title',
      content: 'Post content',
      userId: 1,
      topicId: 1,
      createdAt: new Date().toISOString(),
      updatedAt: new Date().toISOString(),
    }).as('createPost');
    cy.login('alice@mdd.com', 'password123');
    cy.visit('/post/create');
  });

  it('should go back on post feed if back button clicked', () => {
    cy.contains('button', 'arrow_back').click();
    cy.url().should('include', '/post/feed');
  });

  it('should display the create post form', () => {
    cy.get('form').should('exist');
    cy.get('input[formControlName=title]').should('exist');
    cy.get('textarea[formControlName=content]').should('exist');
    cy.get('mat-select[formControlName=topicId]').should('exist');
    cy.get('button[type=submit]').should('exist');
  });

  it('should display an error message if form is invalid', () => {
    cy.get('button[type=submit]').click();
    cy.get('error').should('have.length', 1);
    cy.get('error').and('contain.text', 'Une erreur est survenu');
  });

  it('should create a new post', () => {
    cy.get('input[formControlName=title]').type('Post title');
    cy.get('textarea[formControlName=content]').type('Post content');
    cy.get('mat-select[formControlName=topicId]').click();
    cy.get('mat-option').first().click();
    cy.get('button[type=submit]').click();
    cy.get('snack-bar-container').and(
      'contain.text',
      'Votre article a bien été créé.'
    );
    cy.url().should('include', '/post/feed');
  });
});
