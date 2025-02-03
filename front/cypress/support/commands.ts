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

  // user subscribe to topic
  cy.intercept('POST', '/api/topic/*/subscribe/*', {
    statusCode: 200,
  }).as('subscribe');

  //user unsubscribe from topic
  cy.intercept('DELETE', '/api/topic/*/unsubscribe/*', {
    statusCode: 200,
  }).as('unsubscribe');

  // register response success
  cy.intercept('POST', '/api/auth/register', {
    body: {
      message: 'User registered successfully!',
    },
  }).as('register');

  Promise.all([
    cy.fixture('users'),
    cy.fixture('topics'),
    cy.fixture('posts'),
    cy.fixture('comments'),
  ]).then(([users, topics, posts, comments]) => {
    // user get subscriptions
    cy.intercept('GET', '/api/user/*/subscriptions', (req) => {
      const userId = Number(req.url.split('/').slice(-2)[0]);
      const user = users.find((u) => u.id === userId);
      if (user) {
        const userTopics = topics.filter((t) =>
          user.subscriptions.includes(t.id)
        );
        req.reply(userTopics);
      } else {
        req.reply([]);
      }
    }).as('getSubscriptions');

    // user get feed
    cy.intercept('GET', '/api/user/*/feed*', (req) => {
      const userId = Number(req.url.split('/').slice(-2)[0]);
      const user = users.find((u) => u.id === userId);
      if (user) {
        const userFeedPosts = posts.filter((p) => user.feed.includes(p.id));
        req.reply(userFeedPosts);
      } else {
        req.reply([]);
      }
    }).as('getFeed');

    // get user by id
    cy.intercept('GET', '/api/user/*', (req) => {
      const userId = Number(req.url.split('/').pop());
      const user = users.find((u) => u.id === userId);
      req.reply(user ? user : { error: 'User not found' });
    }).as('getUser');

    // get post by id
    cy.intercept('GET', '/api/post/*', (req) => {
      const postId = Number(req.url.split('/').pop());
      const post = posts.find((p) => p.id === postId);
      req.reply(post ? post : { error: 'Post not found' });
    }).as('getPost');

    // get comments by post
    cy.intercept('GET', '/api/post/*/comments', (req) => {
      const postId = Number(req.url.split('/').slice(-2)[0]);
      const post = posts.find((p) => p.id === postId);
      if (post) {
        const postComments = comments.filter((c) => c.postId === postId);
        req.reply(postComments);
      } else {
        req.reply([]);
      }
    }).as('getComments');
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
