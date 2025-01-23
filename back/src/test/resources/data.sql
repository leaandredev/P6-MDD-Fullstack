-- Users
INSERT INTO USERS (user_name, email, password)
VALUES
('DevAlice', 'alice@mdd.com', '$2a$10$aK5yHO5R4wKz/UVCjjqbR..wWhyKW4Laryb208sYC/aTa4TAyGqsm'), -- Password: password123
('DevBob', 'bob@mdd.com', '$2a$10$SxMN1pz5Zd.T2EsGZKh5Iu8VJKJovCfxLeZ7WyhQ7YyT1qMAGoXXS'), -- Password: password456
('DevCharlie', 'charlie@mdd.com', '$2a$10$QlCBKjvaCCRuNGhcavO5zuO59cLS4AmpIXDqfoka5dic0GBbgFYwu'); -- Password: password789

-- -- Insertion des topics (développement)
INSERT INTO TOPICS (title, description)
VALUES
('Java Best Practices', 'Discussion sur les bonnes pratiques de développement en Java.'),
('Frontend Frameworks', 'Comparaison des frameworks front-end comme Angular, React, et Vue.'),
('DevOps Tools', 'Échange sur les outils et techniques en DevOps.');

-- -- Insertion des posts
-- INSERT INTO POSTS (title, content, user_id, topic_id)
-- VALUES
-- ('Singleton ou pas ?', 'Quels sont les avantages et inconvénients des patterns Singleton ?', 1, 1), -- DevAlice sur Java Best Practices
-- ('React vs Angular', 'Selon vous, quel framework est plus adapté pour un projet complexe ?', 2, 2), -- DevBob sur Frontend Frameworks
-- ('Docker Tips', 'Vos meilleurs conseils pour optimiser l\'utilisation de Docker.', 3, 3); -- DevCharlie sur DevOps Tools

-- -- Insertion des commentaires
-- INSERT INTO COMMENTS (user_id, post_id, content)
-- VALUES
-- (2, 1, 'Je pense que le Singleton est souvent sur-utilisé.'), -- DevBob commente sur le post de DevAlice
-- (1, 2, 'Angular est plus adapté pour les grandes applications.'), -- DevAlice commente sur le post de DevBob
-- (3, 3, 'Assurez-vous d\'utiliser des multi-stage builds.'); -- DevCharlie commente sur son propre post

-- -- Insertion des abonnements
-- INSERT INTO SUBSCRIPTIONS (user_id, topic_id)
-- VALUES
-- (1, 1), -- DevAlice s'abonne au topic Java Best Practices
-- (2, 2), -- DevBob s'abonne au topic Frontend Frameworks
-- (3, 3), -- DevCharlie s'abonne au topic DevOps Tools
-- (1, 3); -- DevAlice s'abonne également au topic DevOps Tools

-- -- Insertion dans les feeds
-- INSERT INTO FEEDS (user_id, post_id)
-- VALUES
-- (1, 2), -- DevAlice voit le post de DevBob sur React vs Angular
-- (2, 1), -- DevBob voit le post de DevAlice sur Singleton ou pas
-- (3, 3); -- DevCharlie voit son propre post sur Docker Tips