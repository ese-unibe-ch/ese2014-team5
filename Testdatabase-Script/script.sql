
# --------- USER ----------

INSERT INTO `User` (`id`, `email`, `enabled`, `firstName`, `lastName`, `password`, `username`, `userRole_userRoleId`) VALUES
(1, 'max@mustermann.ch', 1, 'Max', 'Mustermann', '$2a$10$sB62/vt7RV7EG/GmpR6ct.Y2rGK5JUY5Rm6hiw/sgFhm.LdNMe99a' /* password is: asdf1234 */, 'max@mustermann.ch', 1),
(2, 'jenny@harper.com', 1, 'Jenny', 'Harper', '$2a$10$QW8coPISVAWleqCWT47zwuQWYQqx8XB4mgMpqjFiBXfyQ3Er9APOK' /* password is: qwertz */, 'jenny@harper.com', 2),
(3, 'ben@harper.com', 1, 'Ben', 'Harper', '$2a$10$QW8coPISVAWleqCWT47zwuQWYQqx8XB4mgMpqjFiBXfyQ3Er9APOK' /* password is: qwertz */, 'ben@harper.com', 3);


# --------- USER-ROLES ----------

INSERT INTO `UserRole` (`userRoleId`, `role`) VALUES
(1, 1), (2, 1), (3, 1);

# --------- ADDRESS ----------

INSERT INTO `testBDB`.`Address` (`id`, `city`, `plz`, `street`) VALUES 
(NULL, 'Bern', '3011', 'Bahnhofplatz 11'),
(NULL, 'Zollikofen', '3052', 'Aarhaldenstrasse 18'),
(NULL, 'Bern', '3014', 'Wankdorffeldstrasse 97');

# --------- ADVERT ----------

INSERT INTO `testBDB`.`Advert` (`id`, `fromDate`, `peopleDesc`, `roomDesc`, `roomPrice`, `roomSize`, `title`, `toDate`, `address_id`, `user_id`) VALUES (NULL, '2015-01-01 00:00:00', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ip', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ip', '250', '20', '2-Room Appartment near Center of Bern', NULL /* toDate */ , 1 /* address */, 1 /* user */);

INSERT INTO `testBDB`.`Advert` (`id`, `fromDate`, `peopleDesc`, `roomDesc`, `roomPrice`, `roomSize`, `title`, `toDate`, `address_id`, `user_id`) VALUES (NULL, '2015-01-01 00:00:00', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ip', 'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ip', '390', '42', 'Bright cozy room', '2018-01-01 00:00:00' /* toDate */ , 2 /* address */, 2 /* user */);