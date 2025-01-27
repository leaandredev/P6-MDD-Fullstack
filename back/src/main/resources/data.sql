CREATE TABLE `USERS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_name` VARCHAR(40),
  `email` VARCHAR(255),
  `password` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `TOPICS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(40),
  `description` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `POSTS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(40),
  `content` VARCHAR(255),
  `user_id` int,
  `topic_id` int,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

ALTER TABLE `POSTS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `POSTS` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPICS` (`id`);

CREATE TABLE `COMMENTS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `post_id` int,
  `content` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE `COMMENTS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `COMMENTS` ADD FOREIGN KEY (`post_id`) REFERENCES `POSTS` (`id`);

CREATE TABLE `SUBSCRIPTIONS` (
  `user_id` int,
  `topic_id` int
);

ALTER TABLE `SUBSCRIPTIONS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `SUBSCRIPTIONS` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPICS` (`id`);

CREATE TABLE `FEEDS` (
  `user_id` int,
  `post_id` int
);

ALTER TABLE `FEEDS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `FEEDS` ADD FOREIGN KEY (`post_id`) REFERENCES `POSTS` (`id`);

-- INSERT INTO `TOPICS` (`title`, `description`) VALUES
-- ('Java', 'Langage de programmation orienté objet utilisé pour développer des applications robustes et évolutives.'),
-- ('Spring Framework', 'Un framework Java puissant pour développer des applications d’entreprise, notamment web et cloud.'),
-- ('Angular', 'Framework front-end basé sur TypeScript pour construire des applications web dynamiques et interactives.'),
-- ('TypeScript', 'Langage de programmation basé sur JavaScript offrant des types statiques pour améliorer la robustesse du code.'),
-- ('Node.js', 'Environnement d’exécution JavaScript côté serveur permettant de développer des applications rapides et évolutives.'),
-- ('React', 'Bibliothèque JavaScript pour créer des interfaces utilisateur performantes et réactives.'),
-- ('Docker', 'Plateforme de conteneurisation permettant de déployer et gérer des applications facilement.'),
-- ('Kubernetes', 'Système de gestion de conteneurs open-source pour l’automatisation du déploiement, du scaling et de la gestion des applications.'),
-- ('SQL', 'Langage standard pour la gestion des bases de données relationnelles.'),
-- ('NoSQL', 'Bases de données non relationnelles, adaptées à la gestion de données massives et non structurées.'),
-- ('Git', 'Système de contrôle de version distribué pour gérer les versions du code source.'),
-- ('DevOps', 'Pratiques combinant développement et opérations pour améliorer la livraison de logiciels.'),
-- ('Microservices', 'Architecture permettant de développer des applications modulaires et scalables.'),
-- ('UX/UI', 'Conception des interfaces utilisateurs pour améliorer l’expérience utilisateur.'),
-- ('Big Data', 'Gestion et analyse de grands volumes de données pour extraire des informations exploitables.'),
-- ('Intelligence Artificielle', 'Création de systèmes capables d’accomplir des tâches nécessitant habituellement une intelligence humaine.'),
-- ('Cybersécurité', 'Protection des systèmes, réseaux et données contre les cyberattaques.'),
-- ('APIs', 'Interface permettant aux logiciels de communiquer entre eux de manière standardisée.'),
-- ('Machine Learning', 'Sous-domaine de l’intelligence artificielle centré sur l’apprentissage à partir de données.'),
-- ('Cloud Computing', 'Utilisation de ressources informatiques via Internet pour le stockage et le traitement des données.');
