-- Solo limpia identidad. Los usuarios se crean a mano con POST /api/v1/users.
-- Los roles los vuelve a sembrar Spring al arrancar si la tabla roles queda vacía.

USE `allpatek-os`;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE `user_roles`;
TRUNCATE TABLE `users`;
TRUNCATE TABLE `profiles`;
SET FOREIGN_KEY_CHECKS = 1;
