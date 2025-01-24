Cypress.Commands.add('interceptWithFixture', (method, url, fixturePath) => {
  cy.fixture(fixturePath).then((data) => {
    cy.intercept(
      {
        method: method,
        url: url,
      },
      data
    ).as(fixturePath);
  });
});

Cypress.Commands.add('initIntercepts', () => {
  //get all
  cy.interceptWithFixture('GET', '/api/topic', 'topics');

  // register response success
  cy.intercept('POST', '/api/auth/register', {
    body: {
      message: 'User registered successfully!',
    },
  }).as('register');

  // get by id
  cy.fixture('users').then((users) => {
    cy.intercept('GET', '/api/user/*', (req) => {
      const userId = Number(req.url.split('/').pop());
      const user = users.find((u) => u.id === userId);
      req.reply(user ? user : { error: 'User not found' });
    }).as('getUser');
  });
});

Cypress.Commands.add('login', (identifier, password) => {
  cy.fixture('users').then((users) => {
    cy.intercept('POST', '/api/auth/login', (req) => {
      const user = users.find(
        (u) => u.email === identifier || u.userName === identifier
      );

      if (user) {
        req.reply({
          body: {
            id: user.id,
            email: user.email,
            userName: user.userName,
          },
        });
      } else {
        req.reply({
          statusCode: 401,
          body: { error: 'Invalid credentials' },
        });
      }
    }).as('login');
  });

  cy.visit('/login');
  cy.get('input[formControlName=identifier]').type(identifier);
  cy.get('input[formControlName=password]').type(`${password}{enter}{enter}`);
});

Cypress.Commands.add('register', (userName, email, password) => {
  cy.visit('/register');
  cy.get('input[formControlName=userName]').type(userName);
  cy.get('input[formControlName=email]').type(email);
  cy.get('input[formControlName=password]').type(`${password}{enter}{enter}`);
});
